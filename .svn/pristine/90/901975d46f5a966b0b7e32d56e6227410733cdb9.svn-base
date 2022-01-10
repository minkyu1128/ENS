package cokr.xit.aop;

import java.io.BufferedReader;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cokr.xit.modules.common.code.InternalErrCd;
import cokr.xit.modules.common.model.RestResponseVO;
import cokr.xit.modules.domain.AccessLog;
import cokr.xit.modules.domain.AccessLogRepository;
import cokr.xit.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @업무그룹명: 접근 로그 관리
 * @설명: 시스템 접근 로그를 관리 한다.
 * @최초작성일: 2021. 11. 4. 오후 4:32:05
 * @최초작성자: 박민규
 * @author (주)엑스아이티 개발팀
 * @since 2002. 2. 2.
 * @version 1.0 Copyright(c) XIT All rights reserved.
 */
@Slf4j
@Component
@Aspect
@RequiredArgsConstructor
public class AccessLogAspect {

	private final AccessLogRepository accesslogresLogRepository;

	private final String ACCESS_TOKEN_NAME = "Authorization";

	@Pointcut("execution(* cokr.xit..presentation.*Controller.*(..))")
	public void presentationLayer() {
	}

	@Pointcut("execution(* cokr.xit..service.*Impl.*(..))")
	public void serviceLayer() {
	}

	@Pointcut("execution(* cokr.xit..domain.*Repository.*(..))")
	public void persistenceLayer() {
	}




	/**
	 * <pre>메소드 설명: 요청 및 응답에 대한 로그를 생성 한다.</pre>
	 * @param proceedingJoinPoint
	 * @return
	 * @throws Throwable Object 요청처리 후 응답객체
	 * @author: 박민규
	 * @date: 2021. 11. 30.
	 */
	@Around("presentationLayer()")
	public Object addLogOfRequestAndResponse(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		long start = System.currentTimeMillis();

		Object result = null;
		AccessLog accessLog = null;
		try {
			//insert Request Info
			log.info("[BEFORE] : ...");
			accessLog = this.setRequestInfo(request);
			accesslogresLogRepository.save(accessLog);


			//요청권한 체크
			if(this.isCertified(request)) { //승인된 요청이면..
				//process
				result = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());

				//update Response Info
				log.info("[AFTER] : ...");
				this.setResponseInfo(accessLog, result);
				accesslogresLogRepository.save(accessLog);

			}else {	//승인되지 않은 요청이면..
				//process
				String msg = String.format("권한이 없습니다. 엑세스토큰(%s)를 확인하시기 바랍니다.", ACCESS_TOKEN_NAME);
				RestResponseVO restResponseVO = RestResponseVO.errBuilder()
						.errCode(InternalErrCd.ERR901)
						.errMsg(msg)
						.build();
				result = new ResponseEntity<RestResponseVO>(restResponseVO , HttpStatus.UNAUTHORIZED);

				//update Error Info
				log.error(String.format("[NoAuth] : %s => %s", ACCESS_TOKEN_NAME, request.getHeader(ACCESS_TOKEN_NAME)));
				accessLog.setResponseNoAuth(msg);
				accesslogresLogRepository.save(accessLog);

			}

		} catch (Exception e) {
			//update Error Info
			log.error(String.format("[ERROR] : %s", e.getMessage()));
			accessLog.setResponseFail(e.getMessage());
			accesslogresLogRepository.save(accessLog);

		} finally {
			long end = System.currentTimeMillis();
			log.info("Request: {} {}: {}. request Ajax: {} ({}ms)", request.getMethod(), request.getRequestURL(), paramMapToString(request.getParameterMap()), isAjax(request), end - start);

		}

		return result;
	}


	/**
	 * <pre>메소드 설명: 요청정보 설정</pre>
	 * @param request
	 * @return
	 * @throws JsonProcessingException AccessLog 요청처리 후 응답객체
	 * @author: 박민규
	 * @date: 2021. 11. 30.
	 */
	@SuppressWarnings("deprecation")
	private AccessLog setRequestInfo(HttpServletRequest request) throws JsonProcessingException{
		String sessionId = request.getSession().getId();
		String param = null;
		try {
			param = this.requestBodyToString(request);
			if(StringUtils.isEmpty(param))	//request body에 param 이 없으면..
				param = this.paramMapToJsonString(request.getParameterMap());
		} catch (Exception e) {
			param = String.format("[요청 parameter 변환 실패]: %s", e.getMessage());
		}

		return AccessLog.reqBuilder()
				.accessToken(request.getHeader(ACCESS_TOKEN_NAME))
				.sessionId(sessionId)
				.ip(this.getClientIpAddr(request))
				.httpMethod(request.getMethod())
				.url(request.getRequestURL().toString())
				.uri(request.getRequestURI())
				.param(param)
				.build();
	}

	/**
	 * <pre>메소드 설명: 응답정보 설정</pre>
	 * @param accessLog
	 * @param result
	 * @throws JsonProcessingException void 요청처리 후 응답객체
	 * @author: 박민규
	 * @date: 2021. 11. 30.
	 */
	private void setResponseInfo(AccessLog accessLog, Object result) throws JsonProcessingException{
		String response = null;
		if(result instanceof String)
			response = (String) result;
		else if(result instanceof List)
			response = result.toString();
		else if(result instanceof Map)
			response = result.toString();
		else
			response = String.valueOf(result);

		accessLog.setResponseCompleted(response);
	}


	/**
	 * <pre>메소드 설명: Request ParameterMap에 담긴 parameter를 String으로 반환 한다.</pre>
	 * @param paramMap
	 * @return String 요청처리 후 응답객체
	 * @author: 박민규
	 * @date: 2021. 11. 30.
	 */
	private String paramMapToString(Map<String, String[]> paramMap) {
		return paramMap.entrySet()
				.stream()
				.map(entry -> String.format("%s : %s",
						entry.getKey(), Arrays.toString(entry.getValue())))
				.collect(Collectors.joining(", "));
	}
	/**
	 * <pre>메소드 설명: Request ParameterMap에 담긴 parameter를 Json String으로 반환 한다.</pre>
	 * @param paramMap
	 * @return
	 * @throws JsonProcessingException String 요청처리 후 응답객체
	 * @author: 박민규
	 * @date: 2021. 11. 30.
	 */
	private String paramMapToJsonString(Map<String, String[]> paramMap) throws JsonProcessingException {
    	ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(paramMap);
	}


	/**
	 * <pre>메소드 설명: Request Body로 전송된 parameter를 String으로 반환 한다.</pre>
	 * @param request
	 * @return String 요청처리 후 응답객체
	 * @author: 박민규
	 * @date: 2021. 11. 30.
	 */
	private String requestBodyToString(HttpServletRequest request) {
		StringBuffer body = new StringBuffer();
		String line = null;
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null) {
				body.append(line);
			}
		} catch (Exception e) {
			log.info("Error reading JSON string: " + e.toString());
		}
		return body.toString();
	}


	/**
	 * <pre>메소드 설명: 클라이언트의 IP정보를 가져온다.
	 * 	-L4스위치나 프록시 서버 등이 개입되면서 request.getRemoteAdder()의 내용이 변조되기 시작한다.
	 * 	-대신 추가적인 header가 생기면서(X-Forwarded-For, WL-Proxy-Client-IP 등) 원래의 정보는 그곳에 저장 된다.
	 * 	-아래 메서드는 그 헤더를 추출하여 클라이언트의 아이피를 정확하게 얻고자 하는 방법 이다.
	 * 	출처: https://nine01223.tistory.com/302 [스프링연구소(spring-lab)]
	 * </pre>
	 * @param request
	 * @return String 요청처리 후 응답객체
	 * @author: 박민규
	 * @date: 2020. 9. 16.
	 */
	private String getClientIpAddr(HttpServletRequest request) {
	    String ip = request.getHeader("X-Forwarded-For");

	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("HTTP_CLIENT_IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("HTTP_X_FORWARDED_FOR");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getRemoteAddr();
	    }

	    return ip;
	}

	private boolean isAjax(HttpServletRequest request) {
		if("XMLHttpRequest".equals(request.getHeader("X-Requested-With")))
			return true;

		return false;

	}

	/**
	 * <pre>메소드 설명: 승인키로 계약된 서비스 조회하여 요청 uri와 일치하면 true 아니면 false를 반환 한다.</pre>
//	 * @param request
	 * @return boolean 요청처리 후 응답객체
	 * @author: 박민규
	 * @date: 2021. 11. 30.
	 */
	protected boolean isCertified(HttpServletRequest request) {
		//검증 제외대상
		Pattern pattern = Pattern.compile("/kko/mydoc/noti/.*");
		Matcher matcher = pattern.matcher(request.getRequestURI());
		if(matcher.matches())
			return true;



		String accessToken = request.getHeader(ACCESS_TOKEN_NAME);

		JwtUtil jwtUtil = JwtUtil.verifyBuilder()
				.secretKey(JwtUtil.DEFAULT_SECRET_KEY)
				.jwt(accessToken)
				.build();

		//토큰검증
		boolean isCertified = jwtUtil.isCertified();
		if(!isCertified)
			return false;

		//롤 검증
		boolean hasRole = false;
		for(String role : jwtUtil.getClaims().get("roles").asArray(String.class)) {
			pattern = Pattern.compile(role);
			matcher = pattern.matcher(request.getRequestURI());
			if(matcher.matches()) {
				hasRole = true;
				break;
			}
		}
		if(!hasRole)
			return false;

		//호스트 검증
		String ip = this.getClientIpAddr(request);
		String clientHost = jwtUtil.getClaims().get("aud").asString();
		if("127.0.0.1".equals(ip)||"0:0:0:0:0:0:0:1".equals(ip)||ip.equals(clientHost)||request.getRemoteHost().equals(clientHost))
			return true;

		return false;
	}

}
