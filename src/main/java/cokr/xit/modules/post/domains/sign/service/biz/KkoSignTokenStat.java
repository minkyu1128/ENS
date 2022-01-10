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
//import cokr.xit.modules.post.domains.sign.dto.kko.KkoTokenStatRespDTO;
//import cokr.xit.modules.post.domains.sign.sender.KKoSignTalkSender;
//import cokr.xit.modules.post.domains.sign.service.ApiStrategy;
//import cokr.xit.modules.post.domains.sign.service.KkoSignTalkService;
//import cokr.xit.modules.post.domains.sign.service.Response;
//import cokr.xit.modules.post.exception.InternalException;
//import cokr.xit.modules.utils.BeanUtils;
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//public class KkoSignTokenStat implements ApiStrategy {
//
//	private KkoSignTalkService service;
//	private Response response;
//	
//	//init 필드
//	private KKoSignTalkSender sender;
//	private String txId;
//	private String token;
//
//	//etc...
//	private Map<String, Long> mPkIds = new HashMap<String, Long>();
//	
//	
//	public KkoSignTokenStat(KKoSignTalkSender sender, String txId, String token) {
//		this.sender   = sender;
//		this.txId     = txId;
//		this.token    = token;
//		this.service = BeanUtils.getBean(KkoSignTalkService.class);
//		this.response = new Response();
//	}
//	
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
//	
//	/**
//	 * 유효성 검증
//	 * @return
//	 */
//	@SuppressWarnings("deprecation")
//	private KkoSignTokenStat validate() throws Exception{
//		log.info(String.format("called %s.%s", getClass().getName(), new Object() {}.getClass().getEnclosingMethod().getName()));
//		
//		/* ========================
//		 * 유효성 검증
//		 ======================== */
//		if(StringUtils.isEmpty(txId))
//			throw new InternalException(InternalErrCd.ERR401, "tx_id(은)는 필수조건 입니다.");
//		if(StringUtils.isEmpty(token))
//			throw new InternalException(InternalErrCd.ERR401, "token(은)는 필수조건 입니다.");
//		
//		return this;
//	}
//	
//	
//	/**
//	 * 선행 작업
//	 * @return
//	 */
//	private KkoSignTokenStat preHandle() throws Exception{
//		log.info(String.format("called %s.%s", getClass().getName(), new Object() {}.getClass().getEnclosingMethod().getName()));
//
//		/* ========================
//		 * 토큰검증요청 저장
//		 ======================== */
//		mPkIds = service.addTokenVerifyInfoByReq(txId, token);
//		
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
//		ResponseEntity<String> responseEntity = sender.callTokenStat(txId, token);
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
//	private KkoSignTokenStat postHandle() {
//		log.info(String.format("called %s.%s", getClass().getName(), new Object() {}.getClass().getEnclosingMethod().getName()));
//
//		
//		/* ========================
//		 * 토큰검증 요청결과 저장
//		 ======================== */
//		try {
//			if("OK".equals(response.getErrCode())){	//성공 했으면..
//				ObjectMapper mapper = new ObjectMapper();	
//				KkoTokenStatRespDTO respDTO = mapper.readValue(response.getRespPlainText().get(0), KkoTokenStatRespDTO.class);
//				//발송결과 반영
//				service.modifyTokenVerifyInfoByResp(mPkIds, respDTO.getData());
//			} else { //실패 했으면..
//				//발송결과 반영 - 에러
//				service.modifyTokenVerifyInfoByError(mPkIds, txId, token
//						, response.getErrCode()
//						, response.getErrMsg()
//						);
//			}
//		} catch (JsonProcessingException e) {
//			//발송결과 반영 - 에러
//			service.modifyTokenVerifyInfoByError(mPkIds, txId, token
//					, InternalErrCd.ERR505.name()
//					, String.format("[%s] %s. %s", InternalErrCd.ERR505, InternalErrCd.ERR505.getCodeNm(), e.getMessage())
//					);
//		}
//		
//		
//		
//		return this;
//	}
//	
//	
//	
//	
//}
