//package cokr.xit.modules.post.domains.sign.service.biz;
//
//
//import org.springframework.http.ResponseEntity;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import cokr.xit.modules.domain.SendMast;
//import cokr.xit.modules.post.code.InternalErrCd;
//import cokr.xit.modules.post.domains.sign.dto.kko.KkoSignSendReqDTO;
//import cokr.xit.modules.post.domains.sign.dto.kko.KkoSignSendRespDTO;
//import cokr.xit.modules.post.domains.sign.sender.KKoSignTalkSender;
//import cokr.xit.modules.post.domains.sign.service.ApiStrategy;
//import cokr.xit.modules.post.domains.sign.service.KkoSignTalkService;
//import cokr.xit.modules.post.domains.sign.service.Response;
//import cokr.xit.modules.post.exception.InternalException;
//import cokr.xit.modules.utils.BeanUtils;
//import cokr.xit.modules.utils.RequireValidator;
//import javassist.NotFoundException;
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//public class KkoSignTalkSend implements ApiStrategy {
////public class KkoSignTalk implements ApiCallerStruct<KkoSignTalk>, ApiStrategy{
//
//	private KkoSignTalkService service;
//	private Response response;
//	
//	//init 필드
//	private KKoSignTalkSender sender;
//	private KkoSignSendReqDTO reqDTO;
//
//	//etc...
//	private SendMast sendMast;
//
//	
//	public KkoSignTalkSend(KKoSignTalkSender sender, KkoSignSendReqDTO reqDTO){
//		this.sender   = sender;
//		this.reqDTO = reqDTO;
//		this.service = BeanUtils.getBean(KkoSignTalkService.class);
//		this.response = new Response();
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
//	private KkoSignTalkSend validate() throws Exception{
//		log.info(String.format("called %s.%s", getClass().getName(), new Object() {}.getClass().getEnclosingMethod().getName()));
//		
//		/* ========================
//		 * 유효성 검증
//		 ======================== */
//		try {
//			RequireValidator.builder()
//			.obj(reqDTO.getData())
//			.build()
//			.validate()
//			.throwableException();
//		} catch (Exception e) {
//			throw new InternalException(InternalErrCd.ERR402, e.getMessage());
//		}
//		
//		return this;
//	}
//	
//	
//	/**
//	 * 선행 작업
//	 * @return
//	 */
//	private KkoSignTalkSend preHandle() throws Exception{
//		log.info(String.format("called %s.%s", getClass().getName(), new Object() {}.getClass().getEnclosingMethod().getName()));
//		
//		/* ========================
//		 * 발송정보 저장
//	 	======================== */
//		this.sendMast = service.addSendInfoByReq("KAKAO", "signtalk", "", reqDTO.getData());
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
//		ResponseEntity<String> responseEntity = sender.callSend(reqDTO.getData().toQueryString());
//		if(responseEntity.getStatusCodeValue() != 200) {	//네트워크 통신에 실패했을 경우..
//			throw new InternalException(InternalErrCd.ERR600, String.format("[%s] %s", responseEntity.getStatusCode().toString(), responseEntity.getStatusCodeValue()));
//		}
//		String respPlainText = responseEntity.getBody();
//		
//		
//		return respPlainText;
//	}
//	
//	
//	/**
//	 * 후행 작업
//	 * @return
//	 * @throws NotFoundException 
//	 */
//	private KkoSignTalkSend postHandle() {
//		log.info(String.format("called %s.%s", getClass().getName(), new Object() {}.getClass().getEnclosingMethod().getName()));
//		
//		
//		/* ========================
//		 * 발송결과 저장
//		 ======================== */
//		try {
//			if("OK".equals(response.getErrCode())){	//성공 했으면..
//				ObjectMapper mapper = new ObjectMapper();	
//				KkoSignSendRespDTO respDTO = mapper.readValue(response.getRespPlainText().get(0), KkoSignSendRespDTO.class);
//				//발송결과 반영
//				service.modifySendInfoResultByResp(sendMast.getSendMastId(), respDTO.getData());
//				
//			} else { //실패 했으면..
//				//발송결과 반영 - 에러
//				service.modifySendInfoResultByError(sendMast.getSendMastId(), reqDTO.getData()
//						, response.getErrCode()
//						, response.getErrMsg()
//						);
//			}
//		} catch (JsonProcessingException e) {
//			//발송결과 반영 - 에러
//			service.modifySendInfoResultByError(sendMast.getSendMastId(), reqDTO.getData()
//					, InternalErrCd.ERR505.name()
//					, String.format("[%s] %s. %s", InternalErrCd.ERR505, InternalErrCd.ERR505.getCodeNm(), e.getMessage())
//					);
//		}
//				
//
//		//발송마스터 발송결과 변경(모든 내역이 성공했을 경우 ok, 아닌 경우 fail 로 설정)
//		service.modifySendMastByResult(sendMast.getSendMastId(), response.getErrCode(), response.getErrMsg());
//		
//		return this;
//	}
////
//
//	
//	
//}
