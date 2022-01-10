//package cokr.xit.modules.post.domains.bill.presentation;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//
//import cokr.xit.modules.domain.RequestLog;
//import cokr.xit.modules.domain.RequestLogRepository;
//import cokr.xit.modules.post.domains.bill.dto.kko.KkoSendnNotiReqDTO;
//import cokr.xit.modules.post.domains.bill.service.KkoBillService;
//import cokr.xit.modules.utils.CmmnUtil;
//import lombok.RequiredArgsConstructor;
//
//@RestController
//@RequiredArgsConstructor
//public class NvBillController {
//
//	private final RequestLogRepository requestLogRepository;
//	private final KkoBillService kkoBillService;
//
//	
//	/**
//	 * <pre>메소드 설명: 청구서 링크 생성
//	 * 	-.청구서 링크를 생성 한다.
//	 * 	-.시스템은 청구서URL을.. 사용자는 인증톡 알림 메시지를 수신 받게 된다.
//	 * 	-.청구서 내용은 사용자가 열람을 했을때 카카오(더즌)에서 우리측으로 Data 요청 후 화면에 출력 한다.
//	 * </pre>
//	 * @param req
//	 * @param reqDTO
//	 * @return
//	 * @throws JsonProcessingException Map<String,Object> 요청처리 후 응답객체
//	 * @author: 박민규
//	 * @date: 2021. 8. 17.
//	 */
//	@PostMapping("/naver/send/{biller_code}")
//	public Map<String, Object> send(HttpServletRequest req, @RequestBody KkoSendnNotiReqDTO reqDTO, @PathVariable String billerCode) throws JsonProcessingException{
//		/**
//		 * 시작로그
//		 */
//		RequestLog requstLog = RequestLog.builder()
//				.apiKey(req.getHeader("X-Xit-Api-Key"))        //api Key
////				.reqSystemId(req.getHeader("X-Xit-Client-Id")) //요청 시스템 ID
//				.reqSystemId(billerCode)                       //요청 시스템 ID
//				.reqUrl(req.getRequestURL().toString())        //요청 URL
//				.reqParam(CmmnUtil.toJsonString(reqDTO))       //요청 Parameter
//				.build();
//		requestLogRepository.save(requstLog);
//		
//		
//		/** 
//		 * 처리
//		 */
//		String respMsg = null;
//		boolean isError = false;
//		String errorMsg = null;
//		try {
////			ObjectMapper mapper = new ObjectMapper();
////			respMsg = mapper.writeValueAsString(reqDTO);
////			throw new RuntimeException("에러야 에러");
//			kkoBillService.saveSendData(billerCode, reqDTO);
//		} catch (Exception e) {
//			isError = true;
//			errorMsg = e.getMessage();
//		} finally {
//			/**
//			 * 종료로그
//			 */
//			requstLog.setRespMsg(respMsg);
//			requstLog.setResult(isError?"FAIL":"OK");
//			requstLog.setErrorMsg(errorMsg);
//			requestLogRepository.save(requstLog);
//		}
//		
//		
//		/**
//		 * 응답
//		 */
//		Map<String ,Object> resultMap = new HashMap<>();
//		return resultMap;
//	}
//	
//	
//	
//		
//}
