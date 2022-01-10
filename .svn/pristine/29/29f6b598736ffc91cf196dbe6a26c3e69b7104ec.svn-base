//package cokr.xit.modules.post.domains.sign.service.biz;
//
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.springframework.http.ResponseEntity;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import cokr.xit.modules.post.code.InternalErrCd;
//import cokr.xit.modules.post.domains.sign.dto.kko.KkoBulkSignStatReqDTO;
//import cokr.xit.modules.post.domains.sign.dto.kko.KkoBulkSignStatRespDTO;
//import cokr.xit.modules.post.domains.sign.sender.KKoSignTalkSender;
//import cokr.xit.modules.post.domains.sign.service.ApiStrategy;
//import cokr.xit.modules.post.domains.sign.service.KkoSignTalkService;
//import cokr.xit.modules.post.domains.sign.service.Response;
//import cokr.xit.modules.post.exception.InternalException;
//import cokr.xit.modules.utils.BeanUtils;
//import cokr.xit.modules.utils.CmmnUtil;
//import cokr.xit.modules.utils.RequireValidator;
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//public class KkoBulkSignStatVerify implements ApiStrategy{
//
//	private KkoSignTalkService service;
//	private Response response = null;
//	
//	//init 필드
//	private KKoSignTalkSender sender;
//	private KkoBulkSignStatReqDTO reqDTO;
//
//	//etc...
//	private Map<String, Long> mPkIds = new HashMap<String, Long>();
//	
//
//	public KkoBulkSignStatVerify(KKoSignTalkSender sender, KkoBulkSignStatReqDTO reqDTO){
//		this.sender   = sender;
//		this.reqDTO   = reqDTO;
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
//	
//	/**
//	 * 유효성 검증
//	 * @return
//	 */
//	private KkoBulkSignStatVerify validate() throws Exception{
//		log.info(String.format("called %s.%s", getClass().getName(), new Object() {}.getClass().getEnclosingMethod().getName()));
//		
//		/* ========================
//		 * 유효성 검증
//		 ======================== */
//		try {
//			RequireValidator.builder()
//			.obj(reqDTO)
//			.build()
//			.validate()
//			.throwableException();
//		} catch (Exception e) {
//			throw new InternalException(InternalErrCd.ERR402, String.format("%s ::: parmeter is %s", e.getMessage(), CmmnUtil.toJsonString(reqDTO)));
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
//	private KkoBulkSignStatVerify preHandle() throws Exception{
//		log.info(String.format("called %s.%s", getClass().getName(), new Object() {}.getClass().getEnclosingMethod().getName()));
//
//		/* ========================
//		 * 전자서명상태조회 요청 저장
//	 	======================== */
//		mPkIds = service.addSignStatVerifyInfoByReq(reqDTO.getTx_ids());
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
//		ResponseEntity<String> responseEntity = sender.callBulkSignStat(CmmnUtil.toJsonString(reqDTO.getTx_ids()));
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
//	 */
//	private KkoBulkSignStatVerify postHandle() {
//		log.info(String.format("called %s.%s", getClass().getName(), new Object() {}.getClass().getEnclosingMethod().getName()));
//
//		
//		/* ========================
//		 * 발송결과 저장
//		 ======================== */
//		try {
//			if("OK".equals(response.getErrCode())){	//성공 했으면..
//				ObjectMapper mapper = new ObjectMapper();	
//				 KkoBulkSignStatRespDTO respDTO = mapper.readValue(response.getRespPlainText().get(0), KkoBulkSignStatRespDTO.class);
//				//발송결과 반영
//				service.modifySignStatVerifyInfoByResp(mPkIds, respDTO.getData());
//			} else { //실패 했으면..
//				//발송결과 반영 - 에러
//				service.modifySignStatVerifyInfoByError(mPkIds, reqDTO.getTx_ids()
//						, response.getErrCode()
//						, response.getErrMsg()
//						);
//			}
//		} catch (JsonProcessingException e) {
//			//발송결과 반영 - 에러
//			service.modifySignStatVerifyInfoByError(mPkIds, reqDTO.getTx_ids()
//					, InternalErrCd.ERR505.name()
//					, String.format("[%s] %s. %s", InternalErrCd.ERR505, InternalErrCd.ERR505.getCodeNm(), e.getMessage())
//					);
//		}
//			
//		
//		return this;
//		
//	}
//	
//	
//}
