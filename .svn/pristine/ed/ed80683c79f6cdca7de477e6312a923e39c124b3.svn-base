//package cokr.xit.modules.post.domains.bill.service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.util.StringUtils;
//
//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import cokr.xit.modules.post.code.InternalErrCd;
//import cokr.xit.modules.post.code.NvRespCd;
//import cokr.xit.modules.post.constant.ApiAdrsInf;
//import cokr.xit.modules.post.domains.bill.domain.NaverBillSendInfo;
//import cokr.xit.modules.post.domains.bill.domain.NaverBillSendInfoRepository;
//import cokr.xit.modules.post.domains.bill.dto.nv.NvSendReqDTO;
//import cokr.xit.modules.post.domains.bill.dto.nv.item.NvSendReqData;
//import cokr.xit.modules.post.domains.bill.dto.nv.item.NvSendRespData;
//import cokr.xit.modules.post.exception.InternalException;
//import cokr.xit.modules.utils.CmmnUtil;
//import cokr.xit.modules.utils.RequireValidator;
//import lombok.Builder;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@RequiredArgsConstructor
//@Builder
//public class NvSender {
//
//	private final String xNaverClientId;     //애플리케이션 등록 시 발급받은 client id 값
//	private final String xNaverClientSecret; //애플리케이션 등록 시 발급받은 client secret 값
//	private final ApiAdrsInf apiAdrsInf;
//	private final NvSendReqDTO sendDatalist;
//	private final NaverBillSendInfoRepository naverBillSendInfoRepository;
//	
//	
//	
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	public void execute(){
//		
//		HttpHeaders headers = this.makeHeaders();
//		ObjectMapper mapper = new ObjectMapper();
//		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);	//null 필드 제외
//			
//		String jsonStrSend = null;
//		String resCode = null;
//		String message = null;
//		
//		log.debug(String.format("발송시작:::: %s", Thread.currentThread().getName()));
//		try {
//			/* ==================
//			 * 유효성 검증
//			 *  -.Naver에서 처리하므로 검증을 skip 한다.
//			 ================== */
////			try {
////				this.validate(dto);
////			} catch (Exception e) {
////				throw new InternalException(InternalErrCd.ERR402, e.getMessage()); 
////			}
//			/* ==================
//			 * 요청데이터 json 파싱
//			 ================== */
//			try {
//				jsonStrSend = CmmnUtil.toJsonString(sendDatalist, JsonInclude.Include.NON_EMPTY);  //고지서링크생성 요청 데이터
//			} catch (Exception e) {
//				throw new InternalException(InternalErrCd.ERR504, e.getMessage()); 
//			}
//			
//			/* ==================
//			 * 전송
//			 ================== */
//			List<NvSendRespData> data = new ArrayList<NvSendRespData>();
//			try {
//				ResponseEntity<Map> result = this.sendData(jsonStrSend, headers);
//				
//				resCode = String.valueOf(result.getBody().get("res_code"));
//				message = String.valueOf(result.getBody().get("message"));
//				
//				if(!"OK".equals(resCode))	//링크 생성에 실패하면..
//					throw new InternalException(InternalErrCd.ERR601, String.format("[%s]%s", resCode, message));
//				
//				//get 데이터
//				data =  mapper.readValue((String)result.getBody().get("data"), List.class);
//			} catch (InternalException e) {
//				throw e;
//			}
//			
//			/* ==================
//			 * 전송정보&결과 저장
//			 ================== */
//			//전송정보
//			for(NvSendReqData row : sendDatalist.getData()) {
//				NaverBillSendInfo kakaoBillSendInfo = NaverBillSendInfo.builder()
//						.clientId(this.xNaverClientId)                                //애플리케이션 등록 시 발급받은 client id 값
//						.clientDocId(row.getClientDocId())                            //이용기관에서 식별가능한 고유값
//						.ci(row.getCi())                                              //사용자의 ci
//						.title(row.getTitle())                                        //전자문서 제목
//						.message(CmmnUtil.toJsonString(row.getMessage()))             //원문 데이터
//						.validDuration(row.getValidDuration())                        //고지서 열람 유효시간(초)
//						.redirectUrl(row.getRedirectUrl())                            //인증 후 리다이렉트할 URL
//						.publishDocumentYn(row.getPublishDocumentYn())                //공인전자문서 유통정보 등록 필요 여부(y/n)
//						.documentHash(row.getDocumentHash())                          //본문 해쉬값(공인전자주소 유통정보 등록 시 필수)
//						.callCenterNo(row.getCallCenterNo())                          //고객센터 전화번호
//						.orgId(row.getOrgId())                                        //하위 기관명(필요 시 네이버에서 발급)
//						.authRequireYn(row.getAuthRequireYn())                        //고지서 열람 시 본인인증 사용 여부(y/n)
//						.notification(CmmnUtil.toJsonString(row.getNotification()))   //알람 관련 설정값
//						.build();
//				naverBillSendInfoRepository.save(kakaoBillSendInfo);
//			}
//			//전송결과
//			for(NvSendRespData row : data) {
//				NaverBillSendInfo naverBillSendInfo = naverBillSendInfoRepository.findByClientIdAndClientDocId(this.xNaverClientId, row.getClientDocId());
//				naverBillSendInfo.setResult(row.getResult());
//				naverBillSendInfo.setDocId(row.getDocId());
//				naverBillSendInfo.setErrorCode(row.getErrorCode());
//				naverBillSendInfo.setErrorMsg(row.getErrorMsg());
//			}
//			
//			
//		} catch (InternalException e) {
//			resCode = e.getErrCd().getCode();
//			message = e.getErrCd().getCodeNm();
//		} catch (Exception e) {
//			resCode = InternalErrCd.ERR001.getCode();
//			message = InternalErrCd.ERR001.getCodeNm();
//		} finally {
//		}
//			
//
//		
//	}
//	
//	/**
//	 * 유효성 체크
//	 * @return
//	 */
//	@SuppressWarnings("deprecation")
//	protected void validate(NvSendReqData dto) throws Exception{
//		//청구서 링크 생성 param
//		RequireValidator.builder()
//		.obj(dto)
//		.build()
//		.validate()
//		.throwableException();
//		
//		if(StringUtils.isEmpty(this.xNaverClientId))
//			throw new RuntimeException(String.format("유효성 검증 Fail::: %s", "X-Naver-Client-Id(은)는 필수조건 입니다."));
//		if(StringUtils.isEmpty(this.xNaverClientSecret))
//			throw new RuntimeException(String.format("유효성 검증 Fail::: %s", "X-Naver-Client-Secret(은)는 필수조건 입니다."));
//	}
//	
//	/**
//	 * 헤더생성
//	 * @return
//	 */
//	protected HttpHeaders makeHeaders() {
//		HttpHeaders header = new HttpHeaders();
//		header.add("X-Naver-Client_Id"    , this.xNaverClientId);
//		header.add("X-Naver-Client-Secret", this.xNaverClientSecret);
//		header.setContentType(MediaType.APPLICATION_JSON);
//		
//		return header;
//		
//	}
//	
//	/**
//	 * 인증톡 발송
//	 * @param param
//	 * @param headers
//	 * @return
//	 * @throws Exception
//	 */
//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	protected ResponseEntity<Map> sendData(String param, HttpHeaders headers) throws Exception{
//		Map<String, Object> resp = CmmnUtil.callApi(apiAdrsInf.getMethod(), apiAdrsInf.getUrl(), param, headers);
//		
//
//		//내부 호출결과 확인
//		if(!"OK".equals(resp.get("resCd"))) //호출에 실패하면..
//			throw new InternalException(InternalErrCd.valueOf((String) resp.get("resCd")), String.format("발송처리 도중 API 호출 실패. %s", resp.get("resMsg")));
//		//전체 에러 확인
//		ResponseEntity<Map> responseEntity = (ResponseEntity<Map>) resp.get("responseEntity");
//		switch (responseEntity.getStatusCode().value()) {
//			case 400:
//				throw new InternalException(InternalErrCd.ERR600, String.format("[%s]%s", NvRespCd.invalid_param.getCode(), NvRespCd.invalid_param.getCodeNm() ));
//			case 402:
//				throw new InternalException(InternalErrCd.ERR600, String.format("[%s]%s", NvRespCd.invalid_client_info.getCode(), NvRespCd.invalid_client_info.getCodeNm() ));
//			case 500:
//				throw new InternalException(InternalErrCd.ERR602);
//			default:
//				break;
//		}
//		
//		
//		return (ResponseEntity<Map>) resp.get("responseEntity");
//	}
//	
//}
