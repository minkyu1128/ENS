
package cokr.xit.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cokr.xit.modules.common.code.InternalErrCd;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CmmnUtil {

//	private final static Logger logger = LoggerFactory.getLogger(CmmnUtil.class);


	/**
	 * <pre>메소드 설명: API 호출
	 * 	-.반환결과
	 * 	:result => 결과 코드(OK: 정상, others: 오류)
	 * 	:resultMsg	=> 결과 메시지
	 * 	:responseEntity => api 응답 메시지
	 * </pre>
	 * @param method
	 * @param url
	 * @param param
	 * @param headers
	 * @return Map<String, Object> 요청처리 후 응답객체
	 * @author: 박민규
	 * @date: 2021. 8. 4.
	 * @apiNote: 사이트 참조 https://e2e2e2.tistory.com/15
	 */
	public static Map<String, Object> callApi(HttpMethod method, String url, String param, HttpHeaders headers) {
		log.debug("param =======================");
		log.debug(param);


		Map<String, Object> resultMap = new HashMap<String, Object>();
		ResponseEntity<String> responseEntity = null;
		String result = InternalErrCd.OK.getCode();
		String resultMsg = InternalErrCd.OK.getCodeNm();

		try {
			//uri 및 entity(param) 설정
			HttpEntity<?> entity = null;
			UriComponents uri = null;
			switch (method) {
				case GET:
					entity = new HttpEntity<>(headers);
					uri = UriComponentsBuilder
							.fromHttpUrl(String.format("%s?%s", url, param))
							.encode()
							.build();
					break;
				case POST:
					entity = new HttpEntity<>(param, headers);
					uri = UriComponentsBuilder
							.fromHttpUrl(url)
							.encode()
							.build();
					break;

				default:
					break;
			}


			//api 호출
			HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
			factory.setConnectTimeout(5000); //커넥션타임아웃 설정 5초
			factory.setReadTimeout(5000);//타임아웃 설정 5초
			RestTemplate restTemplate = new RestTemplate(factory);
			responseEntity = restTemplate.exchange(uri.toString(), method, entity, String.class);  //이 한줄의 코드로 API를 호출해 MAP타입으로 전달 받는다.

			//HttpStatus 확인 가능. 코드: e.getStatusCode().value(), 메시지: e.getStatusCode().toString()
//			코드: responseEntity.getStatusCodeValue()
//			메시지: responseEntity.getStatusCode()
		} catch (HttpServerErrorException e) {	//HttpStatus 확인 가능. 코드: e.getStatusCode().value(), 메시지: e.getStatusCode().toString()
			result    = InternalErrCd.ERR501.getCode();
			resultMsg = String.format("%s [사유: %s]", InternalErrCd.ERR501.getCodeNm(), e.getMessage());
			log.error(String.format("call API 서버오류[url =>%s param => %s error => %s]", url, param, e.getMessage()));
        } catch (HttpClientErrorException e) {	//HttpStatus 확인 가능. 코드: e.getStatusCode().value(), 메시지: e.getStatusCode().toString()
        	result    = InternalErrCd.ERR502.getCode();
        	resultMsg = String.format("%s [사유: %s]", InternalErrCd.ERR502.getCodeNm(), e.getMessage());
        	log.error(String.format("call API 클라이언트오류[url =>%s param => %s error => %s]", url, param, e.getMessage()));
        } catch (RestClientException e) {	//timeout 발생 시
        	result    = InternalErrCd.ERR503.getCode();
        	resultMsg = String.format("%s [사유: %s]", InternalErrCd.ERR503.getCodeNm(), e.getMessage());
        	log.error(String.format("RestAPI 호출 오류[url =>%s param => %s error => %s]", url, param, e.getMessage()));
        } catch (Exception e) {
        	result    = InternalErrCd.ERR999.getCode();
        	resultMsg = String.format("%s [사유: %s]", InternalErrCd.ERR999.getCodeNm(), e.getMessage());
        	log.error(String.format("call API 기타오류[url =>%s param => %s error => %s]", url, param, e.getMessage()));
		}


		//결과 응답
		resultMap.put("resCd"      , result);
		resultMap.put("resMsg"     , resultMsg);
		resultMap.put("responseEntity", responseEntity);
		return resultMap;
	}



	/**
	 * <pre>메소드 설명: 객체를 Json 포맷 String으로 반환 한다.</pre>
	 * @param obj
	 * @return String 요청처리 후 응답객체
	 * @author: 박민규
	 * @date: 2021. 8. 5.
	 */
	public static String toJsonString(Object obj) {
		return toJsonString(obj, JsonInclude.Include.ALWAYS);
	}
	/**
	 * <pre>메소드 설명: 객체를 Json 포맷 String으로 반환 한다.</pre>
	 * @param obj
	 * @param type	JsonInclude.Include
	 * <pre>
	 * [type 종류]
	 * 	ALWAYS: 모든 필드
	 * 	NON_NULL: null제외
	 *  NON_ABSENT: null제외
	 *  NON_EMPTY: null/absent/isEmpty()==true/lenth==0 제외
	 *  NON_DEFAULT: empty는 제외된다.primitive 타입이 디폴트 값이면 제외한다. (int / Integer : 0 , boolean / Boolean : false 등)
	 * </pre>
	 * @return String 요청처리 후 응답객체
	 * @author: 박민규
	 * @date: 2021. 8. 5.
	 */
	public static String toJsonString(Object obj, JsonInclude.Include type) {
		String result = null;

		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion(type);		//직렬화 타입 설정
			result = mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
//			throw new CustomException(RESP_CODE.INTERNAL_SERVER_ERROR, String.format("obj -> jsonString converting fail:: %s", e.getMessage()));
			throw new RuntimeException(String.format("obj -> jsonString converting fail:: %s", e.getMessage()), e);
		}

		return result;
	}
	/**
	 * <pre>메소드 설명: jsonString을 Object로 반환한다</pre>
	 * @param obj
	 * @param jsonStr
	 * @return Object 요청처리 후 응답객체
	 * @author: 박민규
	 * @date: 2021. 12. 23.
	 */
	public static Object jsonStringtoObj(Class clz, String jsonStr) {
		Object result = null;

		try {
			ObjectMapper mapper = new ObjectMapper();
			result = mapper.readValue(jsonStr, clz.getClass());
		} catch (JsonProcessingException e) {
			throw new RuntimeException(String.format("jsonString -> obj converting fail:: %s", e.getMessage()), e);
		}

		return result;
	}





	/**
	 * <pre>메소드 설명: 절대시간(단위: second)을 현재 시간으로 반환 한다.</pre>
	 * @param sec
	 * @param fmt
	 * @return String 요청처리 후 응답객체
	 * @author: 박민규
	 * @date: 2021. 11. 19.
	 */
	public static String absSecTimeToDate(int sec, String fmt) {
		return absSecTimeToDate(sec * 1L, fmt);
	}
	/**
	 * <pre>메소드 설명: 절대시간(단위: second)을 현재 시간으로 반환 한다.</pre>
	 * @param sec
	 * @param fmt
	 * @return String 요청처리 후 응답객체
	 * @author: 박민규
	 * @date: 2021. 11. 19.
	 */
	public static String absSecTimeToDate(Long sec, String fmt) {
		if(sec==null)
			return null;
		return absTimeToDate(sec * 1000L, fmt);
	}
	/**
	 * <pre>메소드 설명: 절대시간(단위: ms)을 현재 시간으로 반환 한다.</pre>
	 * @param millionSec
	 * @param fmt
	 * @return String 요청처리 후 응답객체
	 * @author: 박민규
	 * @date: 2021. 11. 19.
	 */
	public static String absTimeToDate(Long millionSec, String fmt) {
		if(fmt == null || "".equals(fmt)) fmt = "yyyy-MM-dd HH:mm:ss";

		Date date = new Date(millionSec);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(fmt);

		return simpleDateFormat.format(date);
	}

	/**
	 * <pre>메소드 설명: 시간을 절대시간(단위: ms)로 반환 한다.</pre>
	 * @param expiresDt	만료일시(yyyyMMddHHmmss)
	 * @return Long 요청처리 후 응답객체
	 * @author: 박민규
	 * @date: 2021. 11. 19.
	 */
	@SuppressWarnings("finally")
	public static Long dateToAbsTime(String expireDt) {
		if(expireDt==null)
			return 0L;

		expireDt = expireDt.replaceAll("[^0-9]", "");
		if("".equals(expireDt))
			return 0L;

		int year, month, day, hour, minute, sec;
		year = month = day = hour = minute = sec = 0;
		try {
			year   = Integer.parseInt(expireDt.substring(0,4));
			month  = Integer.parseInt(expireDt.substring(4,6));
			day    = Integer.parseInt(expireDt.substring(6,8));
			hour   = Integer.parseInt(expireDt.substring(8,10));
			minute = Integer.parseInt(expireDt.substring(10,12));
			sec    = Integer.parseInt(expireDt.substring(12,14));
		} catch(Exception e) {
			log.error(String.format("dateToAbsTime fail... expireDt is [%s]", expireDt));
		} finally {
			return dateToAbsTime(year, month, day, hour, minute, sec);
		}

	}
	/**
	 * <pre>메소드 설명: 시간을 절대시간(단위: ms)로 반환 한다.</pre>
	 * @param year
	 * @param month
	 * @param day
	 * @param hour
	 * @param minute
	 * @param sec
	 * @return Long 요청처리 후 응답객체
	 * @author: 박민규
	 * @date: 2021. 11. 19.
	 */
	@SuppressWarnings("deprecation")
	public static Long dateToAbsTime(int year, int month, int day, int hour, int minute, int sec) {
		if(month < 0)           month = 1;
		if(month > 11)          month = 12;
		if(day < 1 || day > 31) day = 1;
		if(hour < 0 || hour > 23)     hour = 0;
		if(minute < 0 || minute > 59) minute = 0;
		if(sec < 0 || sec > 59)       sec = 0;

		Date curDate = new Date (year-1900, month-1, day, hour, minute, sec);
		return curDate.getTime();
	}

	/**
	 * <pre>메소드 설명: 일수를 상대시간(단위: ms)로 반환 한다.</pre>
	 * @param days
	 * @return Long 요청처리 후 응답객체
	 * @author: 박민규
	 * @date: 2021. 11. 19.
	 */
	public static Long daysToRelTime(int days) {
		if(days<0) days=0;
		return days * 24L * 60L * 60L * 1000L;
	}

}
