//package cokr.xit.modules.post.domains.sign.service.biz;
//
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.util.StringUtils;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import cokr.xit.modules.post.code.InternalErrCd;
//import cokr.xit.modules.post.domains.sign.dto.kko.KkoSignStatRespDTO;
//import cokr.xit.modules.post.domains.sign.sender.KKoSignTalkSender;
//import cokr.xit.modules.post.domains.sign.service.ApiStrategy;
//import cokr.xit.modules.post.domains.sign.service.KkoSignTalkService;
//import cokr.xit.modules.post.domains.sign.service.Response;
//import cokr.xit.modules.post.exception.InternalException;
//import cokr.xit.modules.utils.BeanUtils;
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//public class KkoSignStatVerify implements ApiStrategy {
//
//	private KkoSignTalkService service;
//	private Response response = null;
//	
//	
//	//init 필드
//	private KKoSignTalkSender sender;
//	private String txId;
//
//	//etc...
//	private Map<String, Long> mPkIds = new HashMap<String, Long>();
//	
//	
//
//	public KkoSignStatVerify(KKoSignTalkSender sender, String txId){
//		this.sender   = sender;
//		this.txId     = txId;
//		this.service = BeanUtils.getBean(KkoSignTalkService.class);
//		this.response = new Response();
//		
//
////		private KakaoSignTalkSendInfoRepository kakaoSignTalkSendInfoRepository;
////		private KakaoSignStatVerifyHistRepository kakaoSignStatVerifyHistRepository;
//	}
//	
//
//	@Override
//	public void execute() {
//		try {
//			//전송
//			String respPlainText = this.validate()
//					.preHandle()
//					.call();
//			response.setOkResult(respPlainText);
//		} catch (InternalException e) {
//			response.setFailResult(e.getErrCd(), e.getMessage());
//		} catch (Exception e) {
//			response.setFailResult(InternalErrCd.ERR001, e.getMessage());
//		} finally {
//			//후처리
//			this.postHandle();
//		}
//		
//	}
//
//	@Override
//	public Response getResponse() {
//		return this.response;
//	}
//	
//	
//	
//	/**
//	 * 유효성 검증
//	 * @return
//	 */
//	@SuppressWarnings("deprecation")
//	private KkoSignStatVerify validate() throws Exception{
//		log.info(String.format("called %s.%s", getClass().getName(), new Object() {}.getClass().getEnclosingMethod().getName()));
//		
//		/* ========================
//		 * 유효성 검증
//		 ======================== */
//		if(StringUtils.isEmpty(txId))
//			throw new InternalException(InternalErrCd.ERR401, "tx_id(은)는 필수조건 입니다.");
//		
//		return this;
//	}
//	
//	
//	/**
//	 * 선행 작업
//	 * @return
//	 */
//	private KkoSignStatVerify preHandle() throws Exception {
//		log.info(String.format("called %s.%s", getClass().getName(), new Object() {}.getClass().getEnclosingMethod().getName()));
//
//		/* ========================
//		 * 전자서명상태조회 요청 저장
//	 	======================== */
//		mPkIds = service.addSignStatVerifyInfoByReq(txId);
//		
//		return this;
//	};
//	
//	
//	/**
//	 * api 호출
//	 * @return
//	 */
//	private String call() throws Exception{
//		log.info(String.format("called %s.%s", getClass().getName(), new Object() {}.getClass().getEnclosingMethod().getName()));
//		
//		
//		/* ========================
//		 * API 호출
//		 ======================== */
//		String respPlainText = null;
//		ResponseEntity<String> responseEntity = sender.callSignStat(txId);
//		if(responseEntity.getStatusCodeValue() != 200) {	//네트워크 통신에 실패했을 경우..
//			throw new InternalException(InternalErrCd.ERR600, String.format("[%s] %s", responseEntity.getStatusCode().toString(), responseEntity.getStatusCodeValue()));
//		}
//		respPlainText = responseEntity.getBody();
//		
//		
//		return respPlainText;
//	}
//	
//	
//	/**
//	 * 후행 작업
//	 * @return
//	 */
//	public KkoSignStatVerify postHandle() {
//		log.info(String.format("called %s.%s", getClass().getName(), new Object() {}.getClass().getEnclosingMethod().getName()));
//
//
//		/* ========================
//		 * 전자서명확인결과 저장
//		 ======================== */
//		try {
//			if("OK".equals(response.getErrCode())){	//성공 했으면..
//				ObjectMapper mapper = new ObjectMapper();	
//				KkoSignStatRespDTO respDTO = mapper.readValue(response.getRespPlainText().get(0), KkoSignStatRespDTO.class);
//				//발송결과 반영
//				service.modifySignStatVerifyInfoByResp(mPkIds, respDTO.getData());
//			} else { //실패 했으면..
//				//발송결과 반영 - 에러
//				service.modifySignStatVerifyInfoByError(mPkIds, txId
//						, response.getErrCode()
//						, response.getErrMsg()
//						);
//			}
//		} catch (JsonProcessingException e) {
//			//발송결과 반영 - 에러
//			service.modifySignStatVerifyInfoByError(mPkIds, txId
//					, InternalErrCd.ERR505.name()
//					, String.format("[%s] %s. %s", InternalErrCd.ERR505, InternalErrCd.ERR505.getCodeNm(), e.getMessage())
//					);
//		}
//		
//		
//		
//		return this;
//		
//	}
//	
//	
//	
//	
//}
