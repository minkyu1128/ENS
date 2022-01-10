package cokr.xit.modules.kkomydoc.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;

import cokr.xit.modules.common.code.InternalErrCd;
import cokr.xit.modules.common.code.PostSeCd;
import cokr.xit.modules.common.code.StatCd;
import cokr.xit.modules.common.exception.InternalException;
import cokr.xit.modules.domain.SendMast;
import cokr.xit.modules.domain.SendMastRepository;
import cokr.xit.modules.domain.common.Error;
import cokr.xit.modules.domain.common.FieldError;
import cokr.xit.modules.kkomydoc.domain.NotiDetailsKkoMydoc;
import cokr.xit.modules.kkomydoc.domain.NotiDetailsKkoMydocRepository;
import cokr.xit.modules.kkomydoc.domain.OttVerifyKkoMydoc;
import cokr.xit.modules.kkomydoc.domain.OttVerifyKkoMydocRepository;
import cokr.xit.modules.kkomydoc.domain.ReadCompletedKkoMydoc;
import cokr.xit.modules.kkomydoc.domain.ReadCompletedKkoMydocRepository;
import cokr.xit.modules.kkomydoc.domain.SendDetailKkoMydoc;
import cokr.xit.modules.kkomydoc.domain.SendDetailKkoMydocRepository;
import cokr.xit.modules.kkomydoc.domain.SendResultKkoMydoc;
import cokr.xit.modules.kkomydoc.domain.SendResultKkoMydocRepository;
import cokr.xit.modules.kkomydoc.domain.StatusKkoMydoc;
import cokr.xit.modules.kkomydoc.domain.StatusKkoMydocRepository;
import cokr.xit.modules.kkomydoc.model.BulkSendReqDTO;
import cokr.xit.modules.kkomydoc.model.BulkSendRespDTO;
import cokr.xit.modules.kkomydoc.model.BulkStatusReqDTO;
import cokr.xit.modules.kkomydoc.model.BulkStatusRespDTO;
import cokr.xit.modules.kkomydoc.model.OttRespDTO;
import cokr.xit.modules.kkomydoc.model.SendReqDTO;
import cokr.xit.modules.kkomydoc.model.SendRespDTO;
import cokr.xit.modules.kkomydoc.model.StatusRespDTO;
import cokr.xit.modules.kkomydoc.model.child.BulkStatusDocument;
import cokr.xit.modules.kkomydoc.model.child.Document;
import cokr.xit.modules.kkomydoc.model.child.Documents;
import cokr.xit.modules.kkomydoc.model.child.NotiDocument;
import cokr.xit.utils.CmmnUtil;
import cokr.xit.utils.RequireValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;

@Slf4j
@Service
@RequiredArgsConstructor
public class KkoMyDocService {

	private final KkoMyDocApi kkoMyDocApi;
	private final SendMastRepository sendMastRepository;
	private final SendDetailKkoMydocRepository sendDetailKkoMydocRepository;
	private final SendResultKkoMydocRepository sendResultKkoMydocRepository;
	private final OttVerifyKkoMydocRepository ottVerifyKkoMydocRepository;
	private final ReadCompletedKkoMydocRepository readCompletedKkoMydocRepository;
	private final StatusKkoMydocRepository statusKkoMydocRepository;
	private final NotiDetailsKkoMydocRepository notiDetailsKkoMydocRepository;



	/**
	 * <pre>메소드 설명: 단건의 발송요청건에 대해 발송처리 한다.
	 * 	-.발송요청 정보 및 발송결과는 DB에 저장된다.
	 * </pre>
	 * @param vender
	 * @param accessToken
	 * @param contractUuid
	 * @param jsonStr
	 * @return SendMast 요청처리 후 응답객체
	 * @author: 박민규
	 * @date: 2021. 12. 6.
	 */
	@Transactional(noRollbackFor = Exception.class)
	public SendRespDTO send(String vender, String accessToken, String contractUuid, String jsonStr) {
		System.out.println("send() called...");

		//진행상태(접수 -> 대기큐 -> 처리중 -> 완료)

		SendReqDTO reqDTO = null;
		SendRespDTO sendRespDTO = null;
		SendMast sendMast = null;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		final String externalDocumentUuid = String.format("S-%s%s%09d", simpleDateFormat.format(new Date()), RandomString.make(5), 1);
		try {
			/* ========================
			 * parse
			 ======================== */
			try {
				ObjectMapper mapper = new ObjectMapper();
				reqDTO = mapper.readValue(jsonStr, SendReqDTO.class);
			} catch (JsonProcessingException e) {
				throw new InternalException(InternalErrCd.ERR504, String.format("요청메시지 parse 실패:: %s", e.getMessage()), e);
			}


			/* ========================
			 * validate
			 ======================== */
			try {
				RequireValidator.builder()
				.obj(reqDTO)
				.build()
				.validate()
				.throwableException();
			} catch (Exception e) {
				throw new InternalException(InternalErrCd.ERR401, String.format("필수값 검증 실패:: %s", e.getMessage()), e);
			}



			/* ========================
			 * insert of request info(요청메시지 등록)
			 ======================== */
			sendMast = SendMast.builder()
					.vender(vender)            //중계사업자
					.postSe(PostSeCd.kkoMydoc) //통지서구분(mydoc: 내문서함(구 인증톡), bill: 고지서, Etc...)
					.postTitle(reqDTO.getDocument().getTitle())        //통지서명
					.statCd(StatCd.accept)     //상태(accept: 접수완료, idle:발송대기, success:발송성공/fail:발송실패, open:열람중, close:조회기간마감, Etc...)
					.sendCnt(1)                //발송건수
					.build();
			sendMastRepository.save(sendMast);
			SendDetailKkoMydoc sendDetail = SendDetailKkoMydoc.builder()
					.sendMast(sendMast)
					.title(reqDTO.getDocument().getTitle())
					.message(reqDTO.getDocument().getProperty().getMessage())
					.ci(reqDTO.getDocument().getReceiver().getCi())
					.phoneNumber(reqDTO.getDocument().getReceiver().getPhone_number())
					.name(reqDTO.getDocument().getReceiver().getName())
					.birthday(reqDTO.getDocument().getReceiver().getBirthday())
					.payload(reqDTO.getDocument().getProperty().getPayload())
					.externalDocumentUuid(externalDocumentUuid)
					.link(reqDTO.getDocument().getProperty().getLink())
					.build();
			sendDetailKkoMydocRepository.save(sendDetail);




			/* ========================
			 * call
			 ======================== */
			ResponseEntity<String> resp = kkoMyDocApi.send(accessToken, contractUuid, jsonStr);


			/* ========================
			 * result set
			 ======================== */
			try {
				ObjectMapper mapper = new ObjectMapper();
				sendRespDTO = mapper.readValue(resp.getBody(), SendRespDTO.class);

				if(resp.getStatusCode() != HttpStatus.OK) {	//실패 했을 경우..
					sendRespDTO.setError_code(String.format("%s %s", resp.getStatusCodeValue(), sendRespDTO.getError_code()));
				}
			} catch (JsonProcessingException e) {
				sendRespDTO = new SendRespDTO();
				sendRespDTO.setError_code(String.format("%s %s", HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.name()));
				sendRespDTO.setError_message(String.format("api 응답메시지 변환 실패:: %s", e.getMessage()));
			}

			/* ========================
			 * insert of response info(응답메시지 등록)
			 ======================== */
			SendResultKkoMydoc sendResult = SendResultKkoMydoc.builder()
					.sendMast(sendMast)
					.documentBinderUuid(sendRespDTO.getDocument_binder_uuid())
					.externalDocumentUuid(externalDocumentUuid)
					.error(FieldError.initBuilder()
							.errorCode(sendRespDTO.getError_code())
							.errorMessage(sendRespDTO.getError_message())
							.build())
					.build();
			sendResultKkoMydocRepository.save(sendResult);


			/* ========================
			 * update SendMast of status(발송성공 or 발송실패)
			 ======================== */
			if(sendDetailKkoMydocRepository.countSuccessBySendMastId(sendMast.getSendMastId())>0)	//1건 이상 전송에 성공했으면..
				sendMast.setSendStatus(StatCd.success);
			else //모두 실패했으면..
				sendMast.setSendStatus(StatCd.fail);


		} catch (InternalException e) {
			log.error(String.format("\n### Error Message is... ###\n %s \n### request is... ###\n [vender %s accessToken %s contractUuid %s ] \n### jsonStr is... ###\n %s"
					, e.getMessage()
					, vender
					, accessToken
					, contractUuid
					, jsonStr
					), e);
			throw e;
		} catch (Exception e) {
			log.error(String.format("\n### Error Message is... ###\n %s \n### request is... ###\n [vender %s accessToken %s contractUuid %s ] \n### jsonStr is... ###\n %s"
					, e.getMessage()
					, vender
					, accessToken
					, contractUuid
					, jsonStr
					), e);
		} finally {
			/* ========================
			 * insert NotiDetails
			 ======================== */
			if(reqDTO != null)
				if(reqDTO.getNotidocument() != null) {	//고지상세내용이 있으면...
					NotiDetailsKkoMydoc notiDetilas = NotiDetailsKkoMydoc.builder()
							.sendMast(sendMast)
							.payload(reqDTO.getNotidocument().getPayload())
							.details(String.format("{\"details\" : %s}", CmmnUtil.toJsonString(reqDTO.getNotidocument().getDetails())))
							.build();

					notiDetailsKkoMydocRepository.save(notiDetilas);
				}
		}


		return sendRespDTO;
	}




	/**
	 * <pre>메소드 설명: 일회성토큰(One Time Token) 검증을 수행 한다.
	 * 	-.검증요청 정보 및 검증결과는 DB에 저장된다.
	 * </pre>
	 * @param accessToken
	 * @param documentBinderUuid
	 * @param token void 요청처리 후 응답객체
	 * @author: 박민규
	 * @date: 2021. 12. 6.
	 */
	@SuppressWarnings("deprecation")
	@Transactional(noRollbackFor = Exception.class)
	public OttRespDTO token(String accessToken, String documentBinderUuid, String token) {

		OttRespDTO ottRespDTO = null;
		try {
			/* ========================
			 * validate
		 	 ======================== */
			if(StringUtils.isEmpty(accessToken))
				throw new InternalException(InternalErrCd.ERR401, "엑세스토큰(은)는 필수조건 입니다.");
			if(StringUtils.isEmpty(documentBinderUuid))
				throw new InternalException(InternalErrCd.ERR401, "문서식별번호(은)는 필수조건 입니다.");
			if(StringUtils.isEmpty(token))
				throw new InternalException(InternalErrCd.ERR401, "토큰(은)는 필수조건 입니다.");



			/* ========================
			 * insert of request info(요청메시지 등록)
		 	 ======================== */
			OttVerifyKkoMydoc ottVerifyKkoMydoc = OttVerifyKkoMydoc.builder()
					.documentBinderUuid(documentBinderUuid)
					.token(token)
					.build();
			ottVerifyKkoMydocRepository.save(ottVerifyKkoMydoc);


			/* ========================
			 * call
		 	 ======================== */
			ResponseEntity<String> resp = kkoMyDocApi.token(accessToken, documentBinderUuid, token);


			/* ========================
			 * result set
		 	 ======================== */
			try {
				ObjectMapper mapper = new ObjectMapper();
				ottRespDTO = mapper.readValue(resp.getBody(), OttRespDTO.class);
				if(resp.getStatusCode() != HttpStatus.OK) {	//실패 했을 경우..
					ottRespDTO.setError_code(String.format("%s %s", resp.getStatusCodeValue(), ottRespDTO.getError_code()));
				}
			} catch (JsonProcessingException e) {
				ottRespDTO = new OttRespDTO();
				ottRespDTO.setError_code(String.format("%s %s", HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.name()));
				ottRespDTO.setError_message(String.format("api 응답메시지 변환 실패:: %s", e.getMessage()));
			}


			/* ========================
			 * update of response info(응답메시지 등록)
		 	 ======================== */
			ottVerifyKkoMydoc.setResponse(ottRespDTO);


			/* ========================
			 * update SendDetail of first TokenVerifyTime
			 ======================== */
			if("USED".equals(ottRespDTO.getToken_status())) {	//토큰상태가 정상(USED) 이면..
				sendDetailKkoMydocRepository.modifyFrstTokenVerifyDtByDocumentBinderUuid(ottRespDTO.getToken_used_at(), documentBinderUuid);
			}


		} catch (InternalException e) {
			log.error(String.format("\n### Error Message is... ###\n %s \n### request is... ###\n [ accessToken %s documentBinderUuid %s token %s ]"
					, e.getMessage()
					, accessToken
					, documentBinderUuid
					, token
					), e);
			throw e;

		} catch (Exception e) {
			log.error(String.format("\n### Error Message is... ###\n %s \n### request is... ###\n [ accessToken %s documentBinderUuid %s token %s ]"
					, e.getMessage()
					, accessToken
					, documentBinderUuid
					, token
					), e);
		}


		return ottRespDTO;

	}




	/**
	 * <pre>메소드 설명: 열람완료API를 호출하여 "열람" 상태 및 최초열람일시를 갱신 한다.
	 * 	-.열람완료요청 정보 및 처리결과는 DB에 저장된다.
	 * 	-.열람완료API를 호출 후 상태조회API를 호출하면 진행상태코드 및 최초열람시간이 갱신되어 반환 된다.
	 * </pre>
	 * @param accessToken
	 * @param documentBinderUuid void 요청처리 후 응답객체
	 * @author: 박민규
	 * @date: 2021. 12. 6.
	 */
	@SuppressWarnings("deprecation")
	@Transactional(noRollbackFor = Exception.class)
	public Error readCompleted(String accessToken, String documentBinderUuid) {

		Error respData = new Error();
		try {
			/* ========================
			 * validate
		 	 ======================== */
			if(StringUtils.isEmpty(documentBinderUuid))
				throw new InternalException(InternalErrCd.ERR401, "문서식별번호(은)는 필수조건 입니다.");



			/* ========================
			 * insert of request info(요청메시지 등록)
		 	 ======================== */
			ReadCompletedKkoMydoc readCompletedKkoMydoc = ReadCompletedKkoMydoc.builder()
					.documentBinderUuid(documentBinderUuid)
					.build();
			readCompletedKkoMydocRepository.save(readCompletedKkoMydoc);


			/* ========================
			 * call
		 	 ======================== */
			ResponseEntity<String> resp = kkoMyDocApi.readCompleted(accessToken, documentBinderUuid);


			/* ========================
			 * result set
		 	 ======================== */
			try {
				ObjectMapper mapper = new ObjectMapper();
				if(resp.getStatusCode() != HttpStatus.NO_CONTENT) {	//실패 했을 경우..
					respData = mapper.readValue(resp.getBody(), Error.class);
					respData.setError_code(String.format("%s %s", resp.getStatusCodeValue(), respData.getError_code()));
				}
			} catch (JsonProcessingException e) {
				respData = new Error();
				respData.setError_code(String.format("%s %s", HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.name()));
				respData.setError_message(String.format("api 응답메시지 변환 실패:: %s", e.getMessage()));
			}


			/* ========================
			 * update of response info(응답메시지 등록)
		 	 ======================== */
			readCompletedKkoMydoc.setResponse(respData);


		} catch (InternalException e) {
			log.error(String.format("\n### Error Message is... ###\n %s \n### request is... ###\n [ accessToken %s documentBinderUuid %s ]"
					, e.getMessage()
					, accessToken
					, documentBinderUuid
					), e);
			throw e;

		} catch (Exception e) {
			log.error(String.format("\n### Error Message is... ###\n %s \n### request is... ###\n [ accessToken %s documentBinderUuid %s ]"
					, e.getMessage()
					, accessToken
					, documentBinderUuid
					), e);
		}

		return respData;
	}



	/**
	 * <pre>메소드 설명: 발송건에 대한 현재상태 조회 및 결과를 반환 한다.
	 * 	-.조회요청 정보 및 조회결과는 DB에 저장된다.
	 * 	-.발송상세 데이터의 상태값을 갱신한다.
	 * </pre>
	 * @param accessToken
	 * @param contractUuid
	 * @param documentBinderUuid void 요청처리 후 응답객체
	 * @author: 박민규
	 * @date: 2021. 12. 6.
	 */
	@SuppressWarnings("deprecation")
	@Transactional(noRollbackFor = Exception.class)
	public StatusRespDTO status(String accessToken, String contractUuid, String documentBinderUuid) {

		StatusRespDTO respData = null;
		try {
			/* ========================
			 * validate
		 	 ======================== */
			if(StringUtils.isEmpty(documentBinderUuid))
				throw new InternalException(InternalErrCd.ERR401, "문서식별번호(은)는 필수조건 입니다.");



			/* ========================
			 * insert of request info(요청메시지 등록)
		 	 ======================== */
			StatusKkoMydoc statusKkoMydoc = StatusKkoMydoc.builder()
					.documentBinderUuid(documentBinderUuid)
					.build();
			statusKkoMydocRepository.save(statusKkoMydoc);


			/* ========================
			 * call
		 	 ======================== */
			ResponseEntity<String> resp = kkoMyDocApi.status(accessToken, contractUuid, documentBinderUuid);


			/* ========================
			 * result set
		 	 ======================== */
			try {
				ObjectMapper mapper = new ObjectMapper();
				respData = mapper.readValue(resp.getBody(), StatusRespDTO.class);
				if(resp.getStatusCode() != HttpStatus.OK) {	//실패 했을 경우..
					respData.setError_code(String.format("%s %s", resp.getStatusCodeValue(), respData.getError_code()));
				}
			} catch (JsonProcessingException e) {
				respData = new StatusRespDTO();
				respData.setError_code(String.format("%s %s", HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.name()));
				respData.setError_message(String.format("api 응답메시지 변환 실패:: %s", e.getMessage()));
			}


			/* ========================
			 * update of response info(응답메시지 등록)
		 	 ======================== */
			statusKkoMydoc.setResponse(respData);


			/* ========================
			 * update SendDetail of StatusInfo
			 ======================== */
			sendDetailKkoMydocRepository.modifyStatusInfoByDocumentBinderUuid(respData, documentBinderUuid);

		} catch (InternalException e) {
			log.error(String.format("\n### Error Message is... ###\n %s \n### request is... ###\n [ accessToken %s contractUuid %s documentBinderUuid %s ]"
					, e.getMessage()
					, accessToken
					, contractUuid
					, documentBinderUuid
					), e);
			throw e;

		} catch (Exception e) {
			log.error(String.format("\n### Error Message is... ###\n %s \n### request is... ###\n [ accessToken %s contractUuid %s documentBinderUuid %s ]"
					, e.getMessage()
					, accessToken
					, contractUuid
					, documentBinderUuid
					), e);
		}


		return respData;
	}



	/**
	 * <pre>메소드 설명: 다건의 발송요청건에 대해 발송처리 한다.
	 * 	-.발송요청 정보 및 발송결과는 DB에 저장된다.
	 * </pre>
	 * @param vender
	 * @param accessToken
	 * @param contractUuid
	 * @param jsonStr void 요청처리 후 응답객체
	 * @author: 박민규
	 * @date: 2021. 12. 6.
	 */
	@SuppressWarnings("deprecation")
	@Transactional(noRollbackFor = Exception.class)
	public BulkSendRespDTO bulkSend(String vender, String accessToken, String contractUuid, String jsonStr) {
		System.out.println("bulkSend() called...");

		BulkSendReqDTO reqDTO = null;
		BulkSendRespDTO bulkSendRespDTO = null;
		SendMast sendMast = null;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String prefixExternalDocumentUuid = String.format("B-%s%s", simpleDateFormat.format(new Date()), RandomString.make(5));
		try {
			/* ========================
			 * parse
			 ======================== */
			try {
				ObjectMapper mapper = new ObjectMapper();
				reqDTO = mapper.readValue(jsonStr, BulkSendReqDTO.class);
			} catch (JsonProcessingException e) {
				throw new InternalException(InternalErrCd.ERR504, String.format("요청메시지 parse 실패:: %s", e.getMessage()), e);
			}


			/* ========================
			 * validate
			 ======================== */
			try {
				RequireValidator.builder()
				.obj(reqDTO)
				.build()
				.validate()
				.throwableException();
			} catch (Exception e) {
				throw new InternalException(InternalErrCd.ERR401, String.format("필수값 검증 실패:: %s", e.getMessage()), e);
			}



			/* ========================
			 * insert of request info(요청메시지 등록)
			 ======================== */
			sendMast = SendMast.builder()
					.vender(vender)                         //중계사업자
					.postSe(PostSeCd.kkoMydoc)              //통지서구분(mydoc: 내문서함(구 인증톡), bill: 고지서, Etc...)
					.postTitle(reqDTO.getDocuments().get(0).getTitle()+"...")  //통지서명
					.statCd(StatCd.accept)                  //상태(accept: 접수완료, idle:발송대기, success:발송성공/fail:발송실패, open:열람중, close:조회기간마감, Etc...)
					.sendCnt(reqDTO.getDocuments().size())  //발송건수
					.build();
			int seq = 0;
			sendMastRepository.save(sendMast);
			for(Document row : reqDTO.getDocuments()) {
				String externalDocumentUuid = row.getProperty().getExternal_document_uuid();
				if(StringUtils.isEmpty(externalDocumentUuid)) //외부식별키가 없으면..
					externalDocumentUuid = String.format("%s%09d", prefixExternalDocumentUuid, ++seq);
				row.getProperty().setExternal_document_uuid(externalDocumentUuid);	//외부식별번호 설정

				SendDetailKkoMydoc sendDetail = SendDetailKkoMydoc.builder()
						.sendMast(sendMast)
						.title(row.getTitle())
						.message(row.getProperty().getMessage())
						.ci(row.getReceiver().getCi())
						.phoneNumber(row.getReceiver().getPhone_number())
						.name(row.getReceiver().getName())
						.birthday(row.getReceiver().getBirthday())
						.payload(row.getProperty().getPayload())
						.externalDocumentUuid(externalDocumentUuid)	//외부문서식별번호 설정
						.link(row.getProperty().getLink())
						.build();
				sendDetailKkoMydocRepository.save(sendDetail);
			};
			/* ========================
			 * convert to jsonString
			 ======================== */
			try {
				ObjectMapper mapper = new ObjectMapper();
				jsonStr = mapper.writeValueAsString(reqDTO);
			} catch (JsonProcessingException e) {
				throw new InternalException(InternalErrCd.ERR504, String.format("요청메시지 jsonString 변환 실패:: %s", e.getMessage()), e);
			}



			/* ========================
			 * call
			 ======================== */
			ResponseEntity<String> resp = kkoMyDocApi.bulkSend(accessToken, contractUuid, jsonStr);


			/* ========================
			 * result set
			 ======================== */
			try {
				ObjectMapper mapper = new ObjectMapper();
				bulkSendRespDTO = mapper.readValue(resp.getBody(), BulkSendRespDTO.class);
				if(resp.getStatusCode() != HttpStatus.OK) {	//실패 했을 경우..
					bulkSendRespDTO.setError_code(String.format("%s %s", resp.getStatusCodeValue(), bulkSendRespDTO.getError_code()));
				}

			} catch (JsonProcessingException e) {
				bulkSendRespDTO = new BulkSendRespDTO();
				bulkSendRespDTO.setError_code(String.format("%s %s", HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.name()));
				bulkSendRespDTO.setError_message(String.format("api 응답메시지 변환 실패:: %s", e.getMessage()));
			}


			/* ========================
			 * insert of response info(응답메시지 등록)
			 ======================== */
			SendResultKkoMydoc sendResult = null;
			if(bulkSendRespDTO.getDocuments() == null) {	//에러가 발생했으면...
				for(Document row : reqDTO.getDocuments()) {
					sendResult = SendResultKkoMydoc.builder()
							.sendMast(sendMast)
							.documentBinderUuid(null)
							.externalDocumentUuid(row.getProperty().getExternal_document_uuid())
							.error(FieldError.initBuilder()
									.errorCode(bulkSendRespDTO.getError_code())
									.errorMessage(bulkSendRespDTO.getError_message())
									.build()
									)
							.build();
					sendResultKkoMydocRepository.save(sendResult);
				}
			}else {	//api 서버측으로부터 정상적으로 응답을 받았으면..
				for(Documents row : bulkSendRespDTO.getDocuments()) {
					sendResult = SendResultKkoMydoc.builder()
							.sendMast(sendMast)
							.documentBinderUuid(row.getDocument_binder_uuid())
							.externalDocumentUuid(row.getExternal_document_uuid())
							.error(FieldError.initBuilder()
									.errorCode(row.getError_code())
									.errorMessage(row.getError_message())
									.build()
									)
							.build();
					sendResultKkoMydocRepository.save(sendResult);
				}
			}


			/* ========================
			 * update SendMast of status(발송성공 or 발송실패)
			 ======================== */
			if(sendDetailKkoMydocRepository.countSuccessBySendMastId(sendMast.getSendMastId())>0)	//1건 이상 전송에 성공했으면..
				sendMast.setSendStatus(StatCd.success);
			else //모두 실패했으면..
				sendMast.setSendStatus(StatCd.fail);

		} catch (InternalException e) {
			log.error(String.format("\n### Error Message is... ###\n %s \n### request is... ###\n [vender %s accessToken %s contractUuid %s ] \n### jsonStr is... ###\n %s"
					, e.getMessage()
					, vender
					, accessToken
					, contractUuid
					, jsonStr
					), e);
			throw e;

		} catch (Exception e) {
			log.error(String.format("\n### Error Message is... ###\n %s \n### request is... ###\n [vender %s accessToken %s contractUuid %s ] \n### jsonStr is... ###\n %s"
					, e.getMessage()
					, vender
					, accessToken
					, contractUuid
					, jsonStr
					), e);

		} finally {
			/* ========================
			 * insert NotiDetails
			 ======================== */
			if(reqDTO != null)
				if(reqDTO.getNotidocuments() != null)	//고지상세내용이 있으면...
					for(NotiDocument row : reqDTO.getNotidocuments()) {
						NotiDetailsKkoMydoc notiDetilas = NotiDetailsKkoMydoc.builder()
								.sendMast(sendMast)
								.payload(row.getPayload())
								.details(String.format("{\"details\" : %s}", CmmnUtil.toJsonString(row.getDetails())))
								.build();

						notiDetailsKkoMydocRepository.save(notiDetilas);
					}
		}

		return bulkSendRespDTO;

	}




	/**
	 * <pre>메소드 설명: 다건의 발송건에 대한 현재상태 조회 및 결과를 반환 한다.
	 * 	-.조회요청 정보 및 조회결과는 DB에 저장된다.
	 * 	-.발송상세 데이터의 상태값을 갱신한다.
	 * </pre>
	 * @param accessToken
	 * @param contractUuid
	 * @param jsonStr void 요청처리 후 응답객체
	 * @author:박민규
	 * @date: 2021. 12. 6.
	 */
	@Transactional(noRollbackFor = Exception.class)
	public BulkStatusRespDTO bulkStatus(String accessToken, String contractUuid, String jsonStr) {

		BulkStatusReqDTO reqDTO = null;
		BulkStatusRespDTO bulkStatusRespDTO = null;
		Map<String, StatusKkoMydoc> dataset = null;
		try {
			/* ========================
			 * parse
			 ======================== */
			try {
				ObjectMapper mapper = new ObjectMapper();
				reqDTO = mapper.readValue(jsonStr, BulkStatusReqDTO.class);
			} catch (JsonProcessingException e) {
				throw new InternalException(InternalErrCd.ERR504, String.format("요청메시지 parse 실패:: %s", e.getMessage()), e);
			}


			/* ========================
			 * validate
		 	 ======================== */
			try {
				RequireValidator.builder()
				.obj(reqDTO)
				.build()
				.validate()
				.throwableException();
			} catch (Exception e) {
				throw new InternalException(InternalErrCd.ERR401, String.format("필수값 검증 실패:: %s", e.getMessage()), e);
			}



			/* ========================
			 * insert of request info(요청메시지 등록)
		 	 ======================== */
			dataset = new LinkedHashMap<String, StatusKkoMydoc>();
			for(String documentBinderUuid : reqDTO.getDocument_binder_uuids())
				dataset.put(documentBinderUuid, StatusKkoMydoc.builder().documentBinderUuid(documentBinderUuid).build());


			/* ========================
			 * call
		 	 ======================== */
			ResponseEntity<String> resp = kkoMyDocApi.bulkStatus(accessToken, contractUuid, jsonStr);


			/* ========================
			 * result set
		 	 ======================== */
			try {
				ObjectMapper mapper = new ObjectMapper();
				bulkStatusRespDTO = mapper.readValue(resp.getBody(), BulkStatusRespDTO.class);
				if(resp.getStatusCode() != HttpStatus.OK) {	//실패 했을 경우..
					Iterator<String> it = dataset.keySet().iterator();
					while(it.hasNext()) {
						//set Result
						StatusRespDTO statusRespDTO = new StatusRespDTO();
						statusRespDTO.setError_code(String.format("%s", resp.getStatusCodeValue()));
						statusRespDTO.setError_message(resp.getBody());
						//set Error
						String key = it.next();
						dataset.get(key).setResponse(statusRespDTO);
					}

				} else {	//성공했을 경우..
					//set StatusInfo
					for(BulkStatusDocument row : bulkStatusRespDTO.getDocuments()) {
						if(row.getError_code()==null) {
							dataset.get(row.getDocument_binder_uuid()).setResponse(row.getStatus_data());
						}else {
							//set Result
							StatusRespDTO statusRespDTO = new StatusRespDTO();
							statusRespDTO.setError_code(String.format("%s %s", resp.getStatusCodeValue(), row.getError_code()));
							statusRespDTO.setError_message(row.getError_message());
							dataset.get(row.getDocument_binder_uuid()).setResponse(statusRespDTO);
						}
					}
				}
			} catch (JsonProcessingException e) {
				Iterator<String> it = dataset.keySet().iterator();
				while(it.hasNext()) {
					//set Result
					StatusRespDTO statusRespDTO = new StatusRespDTO();
					statusRespDTO.setError_code(String.format("%s %s", HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.name()));
					statusRespDTO.setError_message(String.format("api 응답메시지 변환 실패:: %s", e.getMessage()));
					//set Error
					String key = it.next();
					dataset.get(key).setResponse(statusRespDTO);
				}
			}


			/* ========================
			 * update of response info(응답메시지 등록)
		 	 ======================== */
//			statusKkoMydoc.setResponse(respData);


			/* ========================
			 * update SendDetail of StatusInfo
			 ======================== */
//			sendDetailKkoMydocRepository.modifyStatusInfoByDocumentBinderUuid(respData, documentBinderUuid);

		} catch (InternalException e) {
			log.error(String.format("\n### Error Message is... ###\n %s \n### request is... ###\n [ accessToken %s contractUuid %s ] \n### jsonStr is... ###\n %s"
					, e.getMessage()
					, accessToken
					, contractUuid
					, jsonStr
					), e);
			throw e;

		} catch (Exception e) {
			log.error(String.format("\n### Error Message is... ###\n %s \n### request is... ###\n [ accessToken %s contractUuid %s ] \n### jsonStr is... ###\n %s"
					, e.getMessage()
					, accessToken
					, contractUuid
					, jsonStr
					), e);
		} finally {
			/* ========================
			 * Query flush
			 ======================== */
			if(dataset != null) {
				dataset.forEach((key, data) -> {
					//insert STATUS_KKO_MYDOC of Request and Response
					statusKkoMydocRepository.save(data);
					//update SendDetail of StatusInfo
//					sendDetailKkoMydocRepository.modifyStatusInfoByDocumentBinderUuid(data);
					SendDetailKkoMydoc detail = sendDetailKkoMydocRepository.findByDocumentBinderUuid(data.getDocumentBinderUuid());
					if(detail != null)
						detail.setStatusInfo(data);
				});

			}
		}


		return bulkStatusRespDTO;
	}


	/**
	 * <pre>메소드 설명: 발송상태를 갱신 한다.
	 *	-.만료기한이 경과 했을 경우 "만료" 상태로 갱신한다.
	 *	-.전체 건수 중 1건 이상 열람했을 경우 "열람" 상태로 갱신한다.
	 *	-.발송마스터의 모든 발송상세데이터의 상태정보도 갱신 한다.
	 * </pre>
	 * @param accessToken
	 * @param contractUuid
	 * @param sendMastId void 요청처리 후 응답객체
	 * @author: 박민규
	 * @date: 2021. 12. 6.
	 */
	@Transactional(noRollbackFor = Exception.class)
	public List<Error> modifySendMastCurStatBySendMastId(String accessToken, String contractUuid, Long sendMastId) {


		List<Error> respDataList = new ArrayList<>();
		Error respData = new Error();
		try {
			//발송상세 목록 조회
			List<String> list = sendResultKkoMydocRepository.findDocumentBinderUuidBySendMastId(sendMastId);
			if(list.size()==0)
				throw new InternalException(InternalErrCd.ERR404, "정상적으로 발송 처리된 발송상세 자료가 없습니다.");

			//요청단위 분할
			List<List<String>> listByDocumentBinderUuid = Lists.partition(list, 100);	//100건씩 분할
			for(List<String> bundle : listByDocumentBinderUuid) {

				BulkStatusReqDTO reqDTO = new BulkStatusReqDTO();
				reqDTO.setDocument_binder_uuids(bundle);
				String jsonStr = CmmnUtil.toJsonString(reqDTO);

				//상태조회api 호출
				BulkStatusRespDTO bulkStatusRespDTO = this.bulkStatus(accessToken, contractUuid, jsonStr);

				//발송마스터 상태 변경
				sendMastRepository.modifyStatCdAndReadCntBySendMastId(sendMastId);

				respData = bulkStatusRespDTO;

				respDataList.add(respData);
			}



		} catch (InternalException e) {
			respData.setError_code(e.getErrCd().name());
			respData.setError_message(e.getMessage());
			respDataList.add(respData);
		} catch (Exception e) {
			respData.setError_code(InternalErrCd.ERR999.name());
			respData.setError_message(e.getMessage());
			respDataList.add(respData);
		}

		return respDataList;

	}
}
