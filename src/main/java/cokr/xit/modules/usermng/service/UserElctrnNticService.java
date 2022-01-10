package cokr.xit.modules.usermng.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import cokr.xit.modules.common.code.InternalErrCd;
import cokr.xit.modules.common.exception.InternalException;
import cokr.xit.modules.kkomydoc.domain.SendDetailKkoMydocRepository;
import cokr.xit.modules.kkomydoc.model.BulkSendReqDTO;
import cokr.xit.modules.kkomydoc.model.child.Document;
import cokr.xit.modules.kkomydoc.model.child.NotiDocument;
import cokr.xit.modules.kkomydoc.model.child.Property;
import cokr.xit.modules.kkomydoc.model.child.Receiver;
import cokr.xit.modules.nicedici.model.DiCiRespDTO;
import cokr.xit.modules.nicedici.service.NiceDiCiService;
import cokr.xit.modules.usermng.domain.ElctrnNticSndng;
import cokr.xit.modules.usermng.domain.ElctrnNticSndngDetail;
import cokr.xit.modules.usermng.domain.ElctrnNticSndngDetailRepository;
import cokr.xit.modules.usermng.domain.ElctrnNticSndngRepository;
import cokr.xit.modules.usermng.domain.ElctrnNticSndngResult;
import cokr.xit.modules.usermng.domain.ElctrnNticSndngResultRepository;
import cokr.xit.modules.usermng.domain.NhtTmplatManageRepository;
import cokr.xit.modules.usermng.service.cmpnt.UserElctrnNticProducer;
import cokr.xit.utils.CmmnUtil;
import cokr.xit.utils.SHA256;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserElctrnNticService {
	private final ElctrnNticSndngRepository elctrnNticSndngRepository;
	private final ElctrnNticSndngDetailRepository elctrnNticSndngDetailRepository;
	private final ElctrnNticSndngResultRepository elctrnNticSndngResultRepository;
	private final NhtTmplatManageRepository nhtTmplatManageRepository;
	private final SendDetailKkoMydocRepository sendDetailKkoMydocRepository;
	private final NiceDiCiService nicedDiCiService;


	@Autowired
	private UserElctrnNticProducer userElctrnNticProducer;

	/**
	 * 발송
	 */
	@Transactional
	public void send() {

		//발송마스터 목록 조회(1분 단위)
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
		String yyyyMMddHHmm = dateFormat.format(new Date());
		String beginSndngDt = String.format("%s00", yyyyMMddHHmm);
		String endSndngDt = String.format("%s59", yyyyMMddHHmm);
		this.send(beginSndngDt, endSndngDt);
	}
	/**
	 * 발송
	 */
	@Transactional
	public void send(String beginSndngDt, String endSndngDt) {

		//발송마스터 목록 조회
		List<ElctrnNticSndng> list = elctrnNticSndngRepository.findAllBySndngProcessSttusAndSndngDtBetween("01", beginSndngDt, endSndngDt);	//요청(01) 자료 중 발송시간(년월일시분)이 일치하는 자료..
		for(ElctrnNticSndng row : list) {
			//발송상세 목록 조회
			List<ElctrnNticSndngDetail> detailList = elctrnNticSndngDetailRepository.findAllByElctrnNticSndng(row);

			/* --------------------------------------------------------
			 *  bundle Begin
			 *
			 * 2021.12.23. 박민규.
			 * [알림톡 API 서비스 개발완료 이후.. ]
			 * 	1. 발송유형코드(SNDNG_TY_CODE) 유형에 메시지 변환 및 발송요청 분기
			 * 		=> if문으로 대신 factory 패턴으로 메시지 변환 및 발송요청 분기 적용
			 * 	2. accessToken과 contractUuid는 DB에서 관리하도록 수정 요함.
			 * 		=> 이용시스템별 관리
			 -------------------------------------------------------- */
			//메시지변환
			List<String> jsonStrList = new ArrayList<>();
			try {
				jsonStrList = makeMessage(row, detailList);
			} catch (Exception e) {
				row.setSndngProcessSttus("99");	//오류
				row.setErrorCn(e.getMessage()); //오류내용
				elctrnNticSndngRepository.save(row);
				continue;
			}

			//발송요청
			for(String jsonStr : jsonStrList) {
				Map<String, String> m = new HashMap<>();
				m.put("accessToken", "b40d3c623d1011ecbceae6cf4630da62");
				m.put("contractUuid", "CON-b4126c833d1011ecbceae6cf4630da62");
				m.put("jsonStr", jsonStr);
				userElctrnNticProducer.pubBulkSend(CmmnUtil.toJsonString(m));
			}
			/* --------------------------------------------------------
			 *  bundle End
			 -------------------------------------------------------- */


			//발송상세 update
			elctrnNticSndngDetailRepository.saveAll(detailList);
		}


	}


	/**
	 * 메시지포맷 작성
	 */
	@SuppressWarnings("deprecation")
	private List<String> makeMessage(ElctrnNticSndng sndng, List<ElctrnNticSndngDetail> sndngDetails) throws InternalException {
		List<String> jsonStrList = new ArrayList<>();

		List<Document> documents = new ArrayList<>();
		List<NotiDocument> notidocuments = new ArrayList<>();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String prefixExternalDocumentUuid = String.format("B-%s%s", simpleDateFormat.format(new Date()), RandomString.make(5));
		int seq = 0;
		for(ElctrnNticSndngDetail row : sndngDetails) {

			String siteCode = "GA72";        //하드코딩 적용하였으나, 이용시스템별 적용되어야 함.
			String sitePw = "xit5811807!!";  //하드코딩 적용하였으나, 이용시스템별 적용되어야 함.
			String payload = row.getMainCode();
			String hash = null;
			try {
				if(StringUtils.isEmpty(row.getCnDetail()))
					hash = SHA256.encrypt(row.getMainCode());
				else
					hash = SHA256.encrypt(row.getCnDetail());
//			} catch (NoSuchAlgorithmException e) {
			} catch (Exception e) {
				log.error(String.format("Hash 값 생성에 실패 하였습니다. [%s]. ", row.getCnDetail()), e);
			}

			/* -------------------------------
			 * 발송정보 설정
			 ------------------------------- */
			//	CI 조회
			DiCiRespDTO diciRespDTO = nicedDiCiService.ci(siteCode, sitePw, row.getIhidnum());
			String ci = null;
			if(diciRespDTO.getError_code()==null)
				ci = diciRespDTO.getDici();
			//	메시지작성
			Document document = new Document();
			document.setTitle(sndng.getNhtTmplatManage().getNhtSj()); //제목
			document.setCommon_categories(new String[] {"NOTICE"});   //카테고리
			document.setHash(hash);                                   //해시값
			Receiver receiver = new Receiver();
			if(ci==null) {
				receiver.setPhone_number(null);                       //받는 이-전화번호
				receiver.setName(null);                               //받는 이-이름
				receiver.setBirthday(null);                           //받는 이-생년월일(YYYYMMDD 형식)
				receiver.setIs_required_verify_name(true);            //받는 이-성명 검증 옵션. CI 전송 시 생략 가능
			}else {                                                   //
				receiver.setCi(ci);                                   //받는 이-ci(개인식별번호)
			}
			document.setReceiver(receiver);                           //받는 이
			Property property = new Property();
			String externalDocumentUuid = String.format("%s%09d", prefixExternalDocumentUuid, ++seq);
			row.setExternal_document_uuid(externalDocumentUuid);
			property.setLink(sndng.getNhtTmplatManage().getRedirectUrl());             //특성정보-본인인증 후 사용자에게 보여줄 웹페이지 주소(redirectUrl)
			property.setPayload(payload);                                              //특성정보-이용기관이 생성한 payload 값
			property.setMessage(sndng.getNhtTmplatManage().getNhtCn());                //특성정보-메시지
			property.setCs_number(sndng.getNhtTmplatManage().getCstmrCnterTlphonNo()); //특성정보-고객센터 전화번호
			property.setCs_name(StringUtils.isEmpty(sndng.getNhtTmplatManage().getCstmrCnterNm())?"문의처":sndng.getNhtTmplatManage().getCstmrCnterNm());         //특성정보-고객센터 명
			property.setExternal_document_uuid(externalDocumentUuid);
			document.setProperty(property);                                            //특성정보
			documents.add(document);



			/* -------------------------------
			 * 고지정보 설정
		 	 ------------------------------- */
			if(!StringUtils.isEmpty(row.getElctrnNticSndng())) {	//고지 데이터가 있으면...
				if(!StringUtils.isEmpty(row.getMobilePageCn()))
					try {

//						NotiDocument notiDocument = (NotiDocument) CmmnUtil.jsonStringtoObj(NotiDocument.class, row.getMobilePageCn());
						ObjectMapper mapper = new ObjectMapper();
						NotiDocument notiDocument = mapper.readValue(row.getMobilePageCn(), NotiDocument.class);
						notiDocument.setPayload(payload);
						notidocuments.add(notiDocument);
					} catch (Exception e) {
	//					log.error(String.format("고지정보 변환에 실패 했습니다. [%s]", row.getMobilePageCn()), e);
						throw new InternalException(InternalErrCd.ERR405, String.format("고지정보 변환에 실패 했습니다. [%s]. %s", row.getMobilePageCn(), e.getMessage()));
					}
			}



			if(documents.size()==100) {	//1회 최대발송건수를 만족하면..
				/* -------------------------------
				 * JsonString으로 변환
				 ------------------------------- */
				BulkSendReqDTO bulkSendReqDTO = new BulkSendReqDTO();
				bulkSendReqDTO.setDocuments(documents);
				bulkSendReqDTO.setNotidocuments(notidocuments);
				jsonStrList.add(CmmnUtil.toJsonString(bulkSendReqDTO));
				//초기화
				documents = new ArrayList<>();
				notidocuments = new ArrayList<>();
			}

		}



		/* -------------------------------
		 * JsonString으로 변환
		 ------------------------------- */
		BulkSendReqDTO bulkSendReqDTO = new BulkSendReqDTO();
		bulkSendReqDTO.setDocuments(documents);
		bulkSendReqDTO.setNotidocuments(notidocuments);
		jsonStrList.add(CmmnUtil.toJsonString(bulkSendReqDTO));



		return jsonStrList;
	}



	/**
	 * 발송결과 업데이트
	 */
	@Transactional
	public void result() {

		//발송마스터 목록 조회(요청상태이면서 발송시간이 3분이 경과한 자료)
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
		Calendar cal = Calendar.getInstance();
		Date date = new Date();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, -3);
		String beginSndngDt = String.format("%s00", dateFormat.format(cal.getTime()));
		cal.setTime(date);
		cal.add(Calendar.MINUTE, -1);
		String endSndngDt = String.format("%s59", dateFormat.format(new Date()));

		this.result(beginSndngDt, endSndngDt);	//ex) 현재시간이 50분이면 47,48,49 분 데이터 처리
	}
	/**
	 * 발송결과 업데이트
	 */
	@SuppressWarnings("deprecation")
	@Transactional
	public void result(String beginSndngDt, String endSndngDt) {
		//발송마스터 목록 조회
		List<ElctrnNticSndng> list = elctrnNticSndngRepository.findAllBySndngProcessSttusAndSndngDtBetween("01", beginSndngDt, endSndngDt);	//요청(01) 자료 중 발송시간(년월일시분)이 일치하는 자료..


		for(ElctrnNticSndng row : list) {
			//발송결과목록 조회
			List<ElctrnNticSndngResult> sndngResultList = elctrnNticSndngResultRepository.findAllByElctrnNticSndngId(row.getElctrnNticSndngId());
			//발송결과 insert
			elctrnNticSndngResultRepository.saveAll(sndngResultList);
			//발송마스터 update(1건 이상 성공 시 발송완료(02), 그외 오류(99))
			row.setSndngProcessSttus("99");
			for(ElctrnNticSndngResult sndngResult : sndngResultList) {
				if(StringUtils.isEmpty(sndngResult.getErrorCn())) {
					row.setSndngProcessSttus("02");
					break;
				}
			}
			elctrnNticSndngRepository.save(row);

		}

	}

	/**
	 *
	 * 메소드 설명: 상태변경
	 * @author: mk1126sj
	 * @date: 2021. 12. 29.
	 */
	@Transactional
	public void status() {

		//발송마스터 목록 조회
		List<ElctrnNticSndng> list = elctrnNticSndngRepository.findAllBySndngProcessSttus("02");	//정상적으로 발송완료(02)된 자료..

		for(ElctrnNticSndng row : list) {
			//발송상세목록 조회
			List<String> externalDocumentUuidList = elctrnNticSndngDetailRepository.findExternalDocumentUuidByElctrnNticSndng(row);

			//상태업데이트 요청메시지 push
			Map<String, Object> m = new HashMap<>();
			m.put("accessToken", "b40d3c623d1011ecbceae6cf4630da62");
			m.put("contractUuid", "CON-b4126c833d1011ecbceae6cf4630da62");
			m.put("externalDocumentUuidList", externalDocumentUuidList);
			userElctrnNticProducer.pubBulkStatus(CmmnUtil.toJsonString(m));
		}

	}


	/**
	 *
	 * 메소드 설명: 발송마스터 및 발송상세 상태정보 fetch
	 * void 요청처리 후 응답객체
	 * @author: mk1126sj
	 * @date: 2021. 12. 29.
	 */
	public void fetchStatus() {

		//발송마스터 목록 조회
		List<ElctrnNticSndng> list = elctrnNticSndngRepository.findAllBySndngProcessSttus("02");	//정상적으로 발송완료(02)된 자료..

		for(ElctrnNticSndng row : list) {
			//발송상세 목록 update
			elctrnNticSndngResultRepository.saveFetchStatus(row);
		}

	}




}
