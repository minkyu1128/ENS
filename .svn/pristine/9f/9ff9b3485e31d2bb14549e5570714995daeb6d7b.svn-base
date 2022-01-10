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
//import cokr.xit.modules.post.constant.PostConst;
//import cokr.xit.modules.post.domains.bill.dto.kko.KkoNotiReqDTO;
//import cokr.xit.modules.post.domains.bill.dto.kko.KkoNotiRespDTO;
//import cokr.xit.modules.post.domains.bill.dto.kko.KkoPayResultReqDTO;
//import cokr.xit.modules.post.domains.bill.dto.kko.KkoPayResultRespDTO;
//import cokr.xit.modules.post.domains.bill.dto.kko.KkoPayableStatReqDTO;
//import cokr.xit.modules.post.domains.bill.dto.kko.KkoPayableStatRespDTO;
//import cokr.xit.modules.post.domains.bill.dto.kko.KkoSendnNotiReqDTO;
//import cokr.xit.modules.post.domains.bill.service.KkoBillService;
//import cokr.xit.modules.utils.CmmnUtil;
//import lombok.RequiredArgsConstructor;
//
//@RestController
//@RequiredArgsConstructor
//public class KkoBillController {
//
//	private final RequestLogRepository requestLogRepository;
//	private final KkoBillService kkoBillService;
//
//	
//	/* =========================
//	 * 해야할 것
//	 *  1.청구서 생성 후 식별자 리턴 필요
//	 *  2.청구서재생성 시 식별자를 param으로 받아서 update로 실행되어야 함?
//	 *  3.청구서확인 시 billerCode, billerUserKey 조합으로 데이터 조회. 실질적인 식별자 임.
//	 *  4.납부가능조회 시 만료일자/납부상태/납부요청금액 체크하여 결과 반환 
//	 ========================= */
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
//	@PostMapping("/kakao/send/{biller_code}")
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
//	/**
//	 * <pre>메소드 설명: 청구서 링크 재생성
//	 * 	-.청구서 링크를 재생성 한다.
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
//	@PostMapping("/kakao/reSend/{biller_code}")
//	public Map<String, Object> reSend(HttpServletRequest req, @RequestBody KkoSendnNotiReqDTO reqDTO, @PathVariable String billerCode) throws JsonProcessingException{
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
//			kkoBillService.saveReSendData(billerCode, reqDTO);
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
//	/**
//	 * <pre>메소드 설명: 청구서 조회
//	 * 	-.청구서 페이지에 출력할 청구정보를 카카오(더즌) => 우리측 API를 호출하여 확인한다.
//	 * </pre>
//	 * @param req
//	 * @param reqDTO
//	 * @return
//	 * @throws JsonProcessingException Map<String,Object> 요청처리 후 응답객체
//	 * @author: 박민규
//	 * @date: 2021. 8. 17.
//	 */
//	@PostMapping("/kakao/notice")
//	public KkoNotiRespDTO notice(HttpServletRequest req, @RequestBody KkoNotiReqDTO reqDTO) throws JsonProcessingException{
//		/**
//		 * 시작로그
//		 */
//		RequestLog requstLog = RequestLog.builder()
////				.apiKey(req.getHeader("X-Xit-Api-Key"))        //api Key
////				.reqSystemId(req.getHeader("X-Xit-Client-Id")) //요청 시스템 ID
//				.reqSystemId(String.valueOf(reqDTO.getData().getParameters().get(PostConst.KkoParamBillerCode))) //요청 시스템 ID
//				.reqUrl(req.getRequestURL().toString())        //요청 URL
//				.reqParam(CmmnUtil.toJsonString(reqDTO))       //요청 Parameter
//				.build();
//		requestLogRepository.save(requstLog);
//		
//		
//		/** 
//		 * 처리
//		 */
//		KkoNotiRespDTO respDTO = null;
//		String respMsg = null;
//		boolean isError = false;
//		String errorMsg = null;
//		try {
//			 respDTO = kkoBillService.getNotiInfo(reqDTO);
//			 respMsg = CmmnUtil.toJsonString(respDTO);
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
//		return respDTO;
//	}
//	
//	
//	
//	
//	/**
//	 * <pre>메소드 설명: 납부 가능 조회
//	 * 	-.납부가능 상태 정보를 카카오(더즌) => 우리측 API를 호출하여 확인한다.
//	 * </pre>
//	 * @param req
//	 * @param reqDTO
//	 * @return
//	 * @throws JsonProcessingException Map<String,Object> 요청처리 후 응답객체
//	 * @author: 박민규
//	 * @date: 2021. 8. 17.
//	 */
//	@PostMapping("/kakao/prepay")
//	public KkoPayableStatRespDTO prepay(HttpServletRequest req, @RequestBody KkoPayableStatReqDTO reqDTO) {
//		/**
//		 * 시작로그
//		 */
//		RequestLog requstLog = RequestLog.builder()
////				.apiKey(req.getHeader("X-Xit-Api-Key"))        //api Key
////				.reqSystemId(req.getHeader("X-Xit-Client-Id")) //요청 시스템 ID
//				.reqSystemId(String.valueOf(reqDTO.getData().getParameters().get(PostConst.KkoParamBillerCode))) //요청 시스템 ID
//				.reqUrl(req.getRequestURL().toString())        //요청 URL
//				.reqParam(CmmnUtil.toJsonString(reqDTO))       //요청 Parameter
//				.build();
//		requestLogRepository.save(requstLog);
//		
//		
//		/** 
//		 * 처리
//		 */
//		KkoPayableStatRespDTO respDTO = null;
//		String respMsg = null;
//		boolean isError = false;
//		String errorMsg = null;
//		try {
//			 respDTO = kkoBillService.getPayableStat(reqDTO);
//			 respMsg = CmmnUtil.toJsonString(respDTO);
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
//		return respDTO;
//	}
//	
//	
//	
//	
//	/**
//	 * <pre>메소드 설명: 납부 결과 전달
//	 * 	-.납부결과 정보를 카카오(더즌) => 우리측 API를 호출하여 전달 한다.
//	 * </pre>
//	 * @param req
//	 * @param reqDTO
//	 * @return
//	 * @throws JsonProcessingException Map<String,Object> 요청처리 후 응답객체
//	 * @author: 박민규
//	 * @date: 2021. 8. 17.
//	 */
//	@PostMapping("/kakao/pay-result")
//	public KkoPayResultRespDTO payResult(HttpServletRequest req, @RequestBody KkoPayResultReqDTO reqDTO) throws JsonProcessingException{
//		/**
//		 * 시작로그
//		 */
//		RequestLog requstLog = RequestLog.builder()
////				.apiKey(req.getHeader("X-Xit-Api-Key"))        //api Key
////				.reqSystemId(req.getHeader("X-Xit-Client-Id")) //요청 시스템 ID
//				.reqSystemId(String.valueOf(reqDTO.getData().getParameters().get(PostConst.KkoParamBillerCode))) //요청 시스템 ID
//				.reqUrl(req.getRequestURL().toString())        //요청 URL
//				.reqParam(CmmnUtil.toJsonString(reqDTO))       //요청 Parameter
//				.build();
//		requestLogRepository.save(requstLog);
//		
//		
//		/** 
//		 * 처리
//		 */
//		KkoPayResultRespDTO respDTO = null;
//		String respMsg = null;
//		boolean isError = false;
//		String errorMsg = null;
//		try {
//			 respDTO = kkoBillService.savePayResult(reqDTO);
//			 respMsg = CmmnUtil.toJsonString(respDTO);
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
//		return respDTO;
//	}
//		
//}
