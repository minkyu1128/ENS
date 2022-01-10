package cokr.xit.modules.post.domains.sign.sender;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Arrays;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <ul>
 * <li>업무 그룹명: 카카오인증톡 발송자</li>
 * <li>설 명: </li>
 * <li>작성일: 2021. 8. 26. 오후 7:17:56
 * </ul>
 * @author 박민규
 */
@Slf4j
@RequiredArgsConstructor
@Builder
public class KKoSignTalkSender {
	
	/**
	 * -./ping
	 * 
	 * [카카오인증톡]
	 * -.모바일웹 연계 전자서명 요청: /api/sign/request/S510   //xxxSender.repeatCallList 호출
	 *   => 카카오페이인증_3.S510.pdf 4page 참조
	 *   
	 * -.토큰 유효성 검증(Redirect URL  접속 허용/불허): /api/sign/token/status
	 *   => 카카오페이인증_3.S510.pdf 10page 참조
	 *   
	 * -.전자서명 상태 조회: /api/v1/sign/status?{tx_id}
	 *   => 카카오페이인증_3.S510.pdf 13page 참조
	 *   
	 *   
	 * [카카오인증톡 Ver.벌크]
	 * -.대량(bulk) 전자서명 요청: /api/sign/request/bulk/{서비스코드}
	 *   => 카카오페이인증_4.Appendix-대량bulk전송.pdf 3page 참조
	 *   
	 * -.이용기관 고유 prefix 조회: /api/sign/request/bulk/prefix
	 *   => 카카오페이인증_4.Appendix-대량bulk전송.pdf 5page 참조
	 *   
	 * -.전자서명 상태 조회: /api/v2/sign/bulk/status
	 *   => 카카오페이인증_4.Appendix-대량bulk전송.pdf 6page 참조
	 *   
	 * -./api/v2/sign/bulk/doc-status
	 */

	/** Access 토큰 */
    private final String ACCESS_TOKEN;	
    /** Host */
    private final String HOST;
    
    /**
     * <pre>메소드 설명: 모바일웹 연계 전자서명 요청</pre>
     * 	-.문서: 카카오페이인증_3.S510.pdf 4page 참조
     * 	-.URI: /api/sign/request/S510
     * @return String 요청처리 후 응답객체
     * @author: 박민규
     * @date: 2021. 8. 26.
     */
    @SuppressWarnings({ "finally" })
    public ResponseEntity<String> callSend(String param) {
    	String uri = "/api/sign/request/S510";
    	HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);						//ContentType: 현재 전송하는 데이터가 어떤 타입인지에 대한 설명
    	headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON}));	//Accept: 서버로부터 응답받고자 희망하는 데이터 타입
    	
    	ResponseEntity<String> respEtt = null;
    	try {
    		respEtt = this.sendData(HttpMethod.POST, uri, URLEncoder.encode(param, "UTF-8"), headers);
    	} catch (Exception e) {
    		log.error(e.getMessage());
    	}	finally {
    		return respEtt;
    	}
    }
    
    /**
     * <pre>메소드 설명: 토큰 유효성 검증(Redirect URL  접속 허용/불허)</pre>
     * 	-.문서: 카카오페이인증_3.S510.pdf 10page 참조
     * 	-.URI: /api/sign/token/status
     * @return String 요청처리 후 응답객체
     * @author: 박민규
     * @date: 2021. 8. 26.
     */
    @SuppressWarnings({ "finally" })
    public ResponseEntity<String> callTokenStat(String txId, String token) {
    	String uri = "/api/sign/token/status";
    	HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    	
    	StringBuffer param = new StringBuffer()
    			.append("tx_id=").append(txId)
    			.append("&token=").append(token);
    	ResponseEntity<String> respEtt = null;
    	try {
    		respEtt = this.sendData(HttpMethod.POST, uri, param.toString(), headers);
    	} catch (Exception e) {
    		log.error(e.getMessage());
    	}	finally {
    		return respEtt;
    	}
    }
    
    /**
     * <pre>메소드 설명: 전자서명 상태 조회</pre>
     * 	-.문서: 카카오페이인증_3.S510.pdf 13page 참조
     * 	-.URI:/api/v1/sign/status?tx_id={tx_id}
     * @param txId
     * @return String 요청처리 후 응답객체
     * @author: 박민규
     * @date: 2021. 8. 26.
     */
    @SuppressWarnings({ "finally" })
    public ResponseEntity<String> callSignStat(String txId) {
    	String uri = "/api/v1/sign/status";
    	HttpHeaders headers = new HttpHeaders();
//    	headers.setContentType(MediaType.APPLICATION_JSON);
    	
    	StringBuffer param = new StringBuffer()
    			.append("tx_id=").append(txId);
    	ResponseEntity<String> respEtt = null;
    	try {
    		respEtt = this.sendData(HttpMethod.GET, uri, param.toString(), headers);
    	} catch (Exception e) {
    		log.error(e.getMessage());
    	}	finally {
    		return respEtt;
    	}
    }

    
    /**
     * <pre>메소드 설명: 대량(bulk) 전자서명 요청</pre>
     * 	-.문서: 카카오페이인증_4.Appendix-대량bulk전송.pdf 3page 참조
     * 	-.URI: /api/sign/request/bulk/{서비스코드}
     * @return String 요청처리 후 응답객체
     * @author: 박민규
     * @date: 2021. 8. 26.
     */
    @SuppressWarnings({ "finally" })
	public ResponseEntity<String> callBulkSend(String param) {
    	String uri = "/api/sign/request/bulk/S510";
    	HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		ResponseEntity<String> respEtt = null;
    	try {
    		respEtt = this.sendData(HttpMethod.POST, uri, param, headers);
		} catch (Exception e) {
			log.error(e.getMessage());
		}	finally {
			return respEtt;
		}
    }
    
    /**
     * <pre>메소드 설명: 이용기관 고유 prefix 조회
     * 	-.문서: 카카오페이인증_4.Appendix-대량bulk전송.pdf 5page 참조
     * 	-.URI: /api/sign/request/bulk/prefix
     * </pre>
     * @return String 요청처리 후 응답객체
     * @author: 박민규
     * @date: 2021. 8. 26.
     */
    @SuppressWarnings({ "finally" })
	public ResponseEntity<String> callBulkPrefix() {
    	String uri = "/api/sign/request/bulk/prefix";
    	HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", uri);
		
		ResponseEntity<String> respEtt = null;
    	try {
    		respEtt = this.sendData(HttpMethod.GET, uri, null, headers);
		} catch (Exception e) {
			log.error(e.getMessage());
		}	finally {
			return respEtt;
		}
    }

    /**
     * <pre>메소드 설명: 전자서명 상태 조회
     * 	-.문서: 카카오페이인증_4.Appendix-대량bulk전송.pdf 6page 참조
     * 	-.URI: /api/v2/sign/bulk/status
     * </pre>
     * @return String 요청처리 후 응답객체
     * @author: 박민규
     * @date: 2021. 8. 26.
     */
    @SuppressWarnings({ "finally" })
	public ResponseEntity<String> callBulkSignStat(String param) {
    	String uri = "/api/v2/sign/bulk/status";
    	HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		ResponseEntity<String> respEtt = null;
    	try {
    		respEtt = this.sendData(HttpMethod.POST, uri, param, headers);
		} catch (Exception e) {
			log.error(e.getMessage());
		}	finally {
			return respEtt;
		}
    }
    

	/**
	 * 데이터 전송
	 * @param method
	 * @param uri
	 * @param param
	 * @param headers
	 * @param accessToken  엑세스토큰(계약번호 같은 개념)
	 * @return
	 * @throws Exception
	 */
//	@SuppressWarnings({ "unchecked" })
//	protected ResponseEntity<String> sendData(HttpMethod method, String uri, String param, HttpHeaders headers) throws Exception{
//        if((this.ACCESS_TOKEN == null || this.ACCESS_TOKEN.length() < 5) && !uri.endsWith("ping"))	//ping 확인 서비스를 제외한 서비스에 대해 엑세스토큰이 없을 경우.. 
//            throw new IOException("AccessToken is null!");
//		
//        //헤더 설정
//        if(headers==null)
//        	headers = new HttpHeaders();
//        headers.set("Authorization", String.format("Bearer %s", this.ACCESS_TOKEN));	//access token 설정
//		
//        //서비스 호출
//		Map<String, Object> resp = CmmnUtil.callApi(method, this.HOST+uri, param, headers);	//서비스 호출
//		
//
//		//내부 호출결과(OK 또는 etc...) 확인
//		if(!"OK".equals(resp.get("resCd"))) //호출에 실패하면..
//			throw new InternalException(InternalErrCd.valueOf((String) resp.get("resCd")), String.format("발송처리 도중 API 호출 실패. %s", resp.get("resMsg")));
//		
//		return (ResponseEntity<String>) resp.get("responseEntity");
//	}
    protected ResponseEntity<String> sendData(HttpMethod method, String uri, String param, HttpHeaders headers) throws Exception{
        if((this.ACCESS_TOKEN == null || this.ACCESS_TOKEN.length() < 5) && !uri.endsWith("ping"))	//ping 확인 서비스를 제외한 서비스에 대해 엑세스토큰이 없을 경우.. 
            throw new IOException("AccessToken is null!");
		
        //헤더 설정
        if(headers==null)
        	headers = new HttpHeaders();
        headers.set("Authorization", String.format("Bearer %s", this.ACCESS_TOKEN));	//access token 설정
		
        //서비스 호출
        ResponseEntity<String> resp = this.callApi(method, this.HOST+uri, param, headers); 
		

//		if(200!=resp.getStatusCodeValue()) //정상(200)이 아니면..  
		return resp;	//서비스 호출
	}
    
	
	

	/**
	 * <pre>메소드 설명: API 호출
	 * </pre>
	 * @param method
	 * @param url
	 * @param param
	 * @param headers
	 * @return ResponseEntity 요청처리 후 응답객체
	 * @author: 박민규
	 * @date: 2021. 8. 4.
	 * @apiNote: 사이트 참조 https://e2e2e2.tistory.com/15
	 */
	private ResponseEntity<String> callApi(HttpMethod method, String url, String param, HttpHeaders headers) {
		log.debug("param =======================");
		log.debug(param);
		
		
		ResponseEntity<String> responseEntity = null;
		
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
			
			/*
			 * HttpStatus 정보 확인 방법
			 * 	-.코드: responseEntity.getStatusCodeValue()
			 * 	-.메시지: responseEntity.getStatusCode()
			 */
			
		} catch (HttpServerErrorException e) {	
			responseEntity = new ResponseEntity<String>(e.getStatusCode());
			log.error(String.format("call API 서버오류[url =>%s param => %s error => %s]", url, param, e.getMessage()));
        } catch (HttpClientErrorException e) {	
        	responseEntity = new ResponseEntity<String>(e.getStatusCode());
        	log.error(String.format("call API 클라이언트오류[url =>%s param => %s error => %s]", url, param, e.getMessage()));
        } catch (RestClientException e) {	//timeout 발생 또는 기타 오류... 
        	responseEntity = new ResponseEntity<String>(HttpStatus.REQUEST_TIMEOUT);
        	log.error(String.format("RestAPI 호출 오류[url =>%s param => %s error => %s]", url, param, e.getMessage()));
        } catch (Exception e) {
        	responseEntity = new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        	log.error(String.format("call API 기타오류[url =>%s param => %s error => %s]", url, param, e.getMessage()));
		}
		
		
		//결과 응답
		return responseEntity;
	}
}
