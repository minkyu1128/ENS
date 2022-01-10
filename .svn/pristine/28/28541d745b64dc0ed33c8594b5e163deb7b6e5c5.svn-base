//package cokr.xit.modules.post.domains.bill.service;
//
//import java.util.Arrays;
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
//import cokr.xit.modules.post.code.PaymentStatCd;
//import cokr.xit.modules.post.constant.ApiAdrsInf;
//import cokr.xit.modules.post.constant.PostConst;
//import cokr.xit.modules.post.domains.bill.domain.KakaoBillSendInfo;
//import cokr.xit.modules.post.domains.bill.domain.KakaoBillSendInfoRepository;
//import cokr.xit.modules.post.domains.bill.dto.kko.KkoSendnNotiReqDTO;
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
//public class KkoSender {
//
//	private final String billerCode;
//	private final ApiAdrsInf apiAdrsInf;
//	private final List<KkoSendnNotiReqDTO> sendDatalist;
//	private final KakaoBillSendInfoRepository kakaoBillSendInfoRepository;
//	
//	KkoSender(String billerCode, ApiAdrsInf apiAdrsInf, KkoSendnNotiReqDTO dto, KakaoBillSendInfoRepository kakaoBillSendInfoRepository){
//		this.billerCode = billerCode;
//		this.apiAdrsInf = apiAdrsInf;
//		this.sendDatalist = Arrays.asList(dto);
//		this.kakaoBillSendInfoRepository = kakaoBillSendInfoRepository;
//	}
//	
//	
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	public void execute(){
////		list.parallelStream()
////		.map(dto -> validate(dto))
////		.flatMap(feature -> feature.join().parallelStream())
////		.map(dto -> makeSendData(dto))
////		;
//		HttpHeaders headers = this.makeHeaders();
//		ObjectMapper mapper = new ObjectMapper();
//		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);	//null 필드 제외
//		sendDatalist.parallelStream().forEach(dto -> {
//			
//			String jsonStrSend = null;
//			String jsonStrNoti = null;
//			String resCode = null;
//			String message = null;
//			String url = null;	
//			
//			log.debug(String.format("발송시작:::: %s", Thread.currentThread().getName()));
//			try {
//				/* ==================
//				 * 유효성 검증
//				 ================== */
//				try {
//					this.validate(dto);
//				} catch (Exception e) {
//					throw new InternalException(InternalErrCd.ERR402, e.getMessage()); 
//				}
//				/* ==================
//				 * 요청데이터 json 파싱
//				 ================== */
//				try {
//					dto.getSend().getData().getParameters().put(PostConst.KkoParamBillerCode, billerCode);
//					jsonStrSend = CmmnUtil.toJsonString(dto.getSend(), JsonInclude.Include.NON_EMPTY);  //고지서링크생성 요청 데이터
//					jsonStrNoti = CmmnUtil.toJsonString(dto.getNoti(), JsonInclude.Include.NON_EMPTY);  //고지서확인 요청에 대한 응답 데이터
//				} catch (Exception e) {
//					throw new InternalException(InternalErrCd.ERR504, e.getMessage()); 
//				}
//				
//				/* ==================
//				 * 전송
//				 ================== */
//				try {
//					ResponseEntity<Map> result = this.sendData(jsonStrSend, headers);
//					resCode = String.valueOf(result.getBody().get("res_code"));
//					message = String.valueOf(result.getBody().get("message"));
//					
//					if(!"OK".equals(resCode))	//링크 생성에 실패하면..
//						throw new InternalException(InternalErrCd.ERR601, String.format("[%s]%s", resCode, message));
//					
//					//get 데이터
//					Map<String, String> data = mapper.readValue((String)result.getBody().get("data"), Map.class);
//					url = data.get("url");	//청구서URL
//				} catch (InternalException e) {
//					throw e;
//				}
//				
//				
//			} catch (InternalException e) {
//				resCode = e.getErrCd().getCode();
//				message = e.getErrCd().getCodeNm();
//			} catch (Exception e) {
//				resCode = InternalErrCd.ERR001.getCode();
//				message = InternalErrCd.ERR001.getCodeNm();
//			} finally {
//				/* ==================
//				 * 전송정보&결과 저장
//				 ================== */
//				KakaoBillSendInfo kakaoBillSendInfo = KakaoBillSendInfo.builder()
//						.billerCode(this.billerCode)                                 //빌러 코드
//						.billerUserKey(dto.getSend().getData().getBiller_user_key()) //빌러 고객 key
//						.expireAt(dto.getSend().getData().getExpire_at())            //url 만료 일자
//						.paymentStatCd(PaymentStatCd.unpaid)                         //납부 상태
//						.parameters(jsonStrSend)                                     //청구서링크생성 파라미터
//						
//						.resCode(resCode)                                            //전송 결과코드
//						.message(message)                                            //전송 결과메시지
//						.returnUrl(url)                                              //청구서 URL
//						.notiInfo(jsonStrNoti)                                       //청구서 정보
//						.userBirth(dto.getUserBirth())                               //사용자 생년월일(yyyyMMdd)
//						
//						.build();
//				kakaoBillSendInfoRepository.save(kakaoBillSendInfo);
//			}
//			
//
//		});
//		
//	}
//	
//	/**
//	 * 유효성 체크
//	 * @return
//	 */
//	@SuppressWarnings("deprecation")
//	protected void validate(KkoSendnNotiReqDTO dto) throws Exception{
//		//청구서 링크 생성 param
//		RequireValidator.builder()
//		.obj(dto.getSend().getData())
//		.build()
//		.validate()
//		.throwableException();
//		
//		//청구서 확인 param
//		if(!StringUtils.isEmpty(dto.getNoti().getData())) {
//			RequireValidator.builder()
//			.obj(dto.getNoti().getData())
//			.build()
//			.validate()
//			.throwableException();
//			
//			if(dto.getSend().getData().getBiller_user_key().equals(dto.getNoti().getData().getResult().getBiller_user_key()))
//				throw new RuntimeException(String.format("두개의 biller_user_key 값이 일치하지 않습니다.(%s/%s)"
//							,dto.getSend().getData().getBiller_user_key()
//							,dto.getNoti().getData().getResult().getBiller_user_key()
//							));
//		}
//	}
//	
//	/**
//	 * 헤더생성
//	 * @return
//	 */
//	protected HttpHeaders makeHeaders() {
//		HttpHeaders header = new HttpHeaders();
////		header.add("X-Naver-Client_Id"    , billerCode);
////		header.add("X-Naver-Client-Secret", clientSecret);
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
////		ApiAdrsInf apiAdrsInf = ApiAdrsInf.KKO_BILL_REQ;
//		Map<String, Object> resp = CmmnUtil.callApi(apiAdrsInf.getMethod(), apiAdrsInf.getUrl()+"/"+billerCode, param, headers);
//		
//
//		//내부 호출결과 확인
//		if(!"OK".equals(resp.get("resCd"))) //호출에 실패하면..
//			throw new InternalException(InternalErrCd.valueOf((String) resp.get("resCd")), String.format("발송처리 도중 API 호출 실패. %s", resp.get("resMsg")));
//		
//		return (ResponseEntity<Map>) resp.get("responseEntity");
//	}
//	
//}
