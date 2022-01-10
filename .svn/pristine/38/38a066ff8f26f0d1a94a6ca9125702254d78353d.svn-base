//package cokr.xit.modules.post.domains.sign.presentation;
//
//import java.util.HashMap;
//import java.util.LinkedHashMap;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import cokr.xit.modules.post.domains.sign.dto.kko.KkoBulkSignSendReqDTO;
//import cokr.xit.modules.post.domains.sign.dto.kko.KkoBulkSignStatReqDTO;
//import cokr.xit.modules.post.domains.sign.dto.kko.KkoSignSendReqDTO;
//import cokr.xit.modules.post.domains.sign.sender.KKoSignTalkSender;
//import cokr.xit.modules.post.domains.sign.service.ApiCaller;
//import cokr.xit.modules.post.domains.sign.service.Response;
//import cokr.xit.modules.post.domains.sign.service.biz.KkoBulkSignPrefix;
//import cokr.xit.modules.post.domains.sign.service.biz.KkoBulkSignStatVerify;
//import cokr.xit.modules.post.domains.sign.service.biz.KkoBulkSignTalkSend;
//import cokr.xit.modules.post.domains.sign.service.biz.KkoSignStatVerify;
//import cokr.xit.modules.post.domains.sign.service.biz.KkoSignTalkSend;
//import cokr.xit.modules.post.domains.sign.service.biz.KkoSignTokenStat;
//
//@RestController
//public class KkoSignTalkController {
//
//
//	@Value("${kakao.host}")
//	private String Host;
//	@Value("${kakao.access_token}")
//	private String AccessToken;
//	
//	
//
//	/**
//	 * 인증톡발송
//	 * @param reqDTO
//	 */
//	@GetMapping("/kko/sign/send.do")
//	public ResponseEntity<Map<String, Object>> send(@RequestBody KkoSignSendReqDTO reqDTO) {
//		KKoSignTalkSender sender = KKoSignTalkSender.builder()
//				.HOST(Host)
//				.ACCESS_TOKEN(AccessToken)
//				.build();
//		
//		
//		//인증톡 발송
//		ApiCaller caller = new ApiCaller();
////		caller.setApiStrategy(KkoSignTalk.create().init(sender, reqDTO));
//		caller.setApiStrategy(new KkoSignTalkSend(sender, reqDTO));
//		caller.execute();
//
//		return this.toResponseEntity(caller.getResponse());
//	}
//	
//	/**
//	 * 전자서명상태 조회
//	 * @param txId
//	 */
//	@GetMapping("/kko/sign/stat.do")
//	public ResponseEntity<Map<String, Object>> signStat(String txId) {
//		KKoSignTalkSender sender = KKoSignTalkSender.builder()
//				.HOST(Host)
//				.ACCESS_TOKEN(AccessToken)
//				.build();
//		
//		
//		//전자서명상태 조회
//		ApiCaller caller = new ApiCaller();
//		caller.setApiStrategy(new KkoSignStatVerify(sender, txId));
//		caller.execute();
//
//		return this.toResponseEntity(caller.getResponse());
//	}
//	
//	
//
//	/**
//	 * 토큰검증
//	 * @param txId
//	 * @param token
//	 */
//	@GetMapping("/kko/sign/token/stat.do")
//	public ResponseEntity<Map<String, Object>> tokenStat(String txId, String token) {
//		KKoSignTalkSender sender = KKoSignTalkSender.builder()
//				.HOST(Host)
//				.ACCESS_TOKEN(AccessToken)
//				.build();
//		
//		
//		//토큰검증 요청
//		ApiCaller caller = new ApiCaller();
//		caller.setApiStrategy(new KkoSignTokenStat(sender, txId, token));
//		caller.execute();
//
//		return this.toResponseEntity(caller.getResponse());
//	}
//	
//	
//	
//
//	/**
//	 * 전자서명상태 대량 조회
//	 * @param reqDTO
//	 */
//	@GetMapping("/kko/sign/bulk/stat.do")
//	public ResponseEntity<Map<String, Object>> bulkSignStat(KkoBulkSignStatReqDTO reqDTO) {
//		KKoSignTalkSender sender = KKoSignTalkSender.builder()
//				.HOST(Host)
//				.ACCESS_TOKEN(AccessToken)
//				.build();
//		
//		
//		//전자서명상태 대량 조회
//		ApiCaller caller = new ApiCaller();
//		caller.setApiStrategy(new KkoBulkSignStatVerify(sender, reqDTO));
//		caller.execute();
//
//		return this.toResponseEntity(caller.getResponse());
//	}
//	
//	
//	
//
//	/**
//	 * 인증톡 prefix 문자 채번
//	 * @param reqDTO
//	 */
//	@GetMapping("/kko/sign/bulk/prefix.do")
//	public ResponseEntity<Map<String, Object>> bulkPrefix(KkoBulkSignSendReqDTO reqDTO) {
//		/* ================
//		 * 중계사업자별
//		 * 	-.호스트
//		 * 	-.엑세스토큰
//		 ================ */
//		KKoSignTalkSender sender = KKoSignTalkSender.builder()
//				.HOST(Host)
//				.ACCESS_TOKEN(AccessToken)
//				.build();
//		
//		
//		//인증톡 대량 전송 prefix 문자 채번
//		ApiCaller caller = new ApiCaller();
//		caller.setApiStrategy(new KkoBulkSignPrefix(sender));
//		caller.execute();
//		
//		return this.toResponseEntity(caller.getResponse());
//	}
//	/**
//	 * 인증톡 대량 발송
//	 * @param reqDTO
//	 */
//	@GetMapping("/kko/sign/bulk/send.do")
//	public ResponseEntity<Map<String, Object>> bulkSend(KkoBulkSignSendReqDTO reqDTO) {
//		/* ================
//		 * 중계사업자별
//		 * 	-.호스트
//		 * 	-.엑세스토큰
//		 ================ */
//		KKoSignTalkSender sender = KKoSignTalkSender.builder()
//				.HOST(Host)
//				.ACCESS_TOKEN(AccessToken)
//				.build();
//	
//		
//		//인증톡 대량 전송
//		ApiCaller caller = new ApiCaller();
//		caller.setApiStrategy(new KkoBulkSignTalkSend(sender, reqDTO));
//		caller.execute();
//
//		return this.toResponseEntity(caller.getResponse());
//	}
//	
//	
//	
//	private ResponseEntity<Map<String, Object>>  toResponseEntity(Response response){
//
//		Map<String, Object> resultInfo = new LinkedHashMap<String, Object>();
//		resultInfo.put("errCode", response.getErrCode());
//		resultInfo.put("message", response.getErrMsg());
//		resultInfo.put("data", response.getRespPlainText());
//		Map<String, Object> returnMap = new HashMap<String, Object>();
//		returnMap.put("resultInfo", resultInfo);
//		
//		ResponseEntity<Map<String, Object>> responseEntity = new ResponseEntity<Map<String,Object>>(returnMap, HttpStatus.OK);
//		
//		return responseEntity;
//	}
//}
