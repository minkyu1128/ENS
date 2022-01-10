package cokr.xit.modules.kkomydoc.service;

import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * <ul>
 * <li>업무 그룹명: 카카오페이 내문서함 API 서비스</li>
 * <li>설 명: </li>
 * <li>작성일: 2021. 11. 18. 오후 5:01:36
 * </ul>
 *
 * @author 박민규
 *
 */
@Slf4j
@Component
public class KkoMyDocApi {

	private final String HOST = "https://docs-gw.kakaopay.com";
	/* ================================================================
	[이벤트별 문서 상태]
	  -.인증톡 수신 시
	    "doc_box_status": "RECEIVED",
	    "doc_box_sent_at": 1637224629,
	    "doc_box_received_at": 1637224629,
	    "user_notified_at": 1637224629,
	    "payload": "payload 파라미터 입니다."
	  -.인증 시
	    "doc_box_status": "RECEIVED",
	    "doc_box_sent_at": 1637224629,
	    "doc_box_received_at": 1637224629,
	    "authenticated_at": 1637282555,      //최초 인증 시간 등록 됨.
	    "user_notified_at": 1637224629,
	    "payload": "payload 파라미터 입니다."
	  -.토큰검증 API 호출 시
	    "doc_box_status": "RECEIVED",
	    "doc_box_sent_at": 1637224629,
	    "doc_box_received_at": 1637224629,
	    "token_used_at": 1637282683,         //최초 토큰검증 시간 등록 됨.
	    "authenticated_at": 1637282555,
	    "user_notified_at": 1637224629,
	    "payload": "payload 파라미터 입니다."
	  -.문서상태변경 API 호출 시
	    "doc_box_status": "READ",            //상태코드가 "RECEIVED -> READ"로 변경 됨.
	    "doc_box_sent_at": 1637224629,
	    "doc_box_received_at": 1637224629,
	    "token_used_at": 1637282683,
	    "authenticated_at": 1637282555,
	    "doc_box_read_at": 1637283712,       //최초 열람 시간 등록 됨.
	    "user_notified_at": 1637224629,
	    "payload": "payload 파라미터 입니다."
	================================================================ */
	
	/**
	 * 모바일웹 연계 문서발송 요청
	 * 	-.이용기관 서버에서 전자문서 서버로 문서발송 처리를 요청합니다.
	 */
	public ResponseEntity<String> send(String accessToken, String contractUuid, String jsonStr) {
		//header 설정
		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setContentType(new MediaType(MediaType.APPLICATION_JSON, Charset.forName("utf-8")));
		headers.set("Authorization", String.format("Bearer %s", accessToken));	
		headers.set("Contract-Uuid", contractUuid);	

		//url 설정
		StringBuilder url = new StringBuilder();
		url.append(this.HOST)
		.append("/v1/documents");
		
		//api 호출
		ResponseEntity<String> resp = this.callApi(HttpMethod.POST, url.toString(), jsonStr, headers);
		return resp;
	}
	

	

	
	
	/**
	 * 토큰 유효성 검증(Redirect URL  접속 허용/불허)
	 */
	public ResponseEntity<String> token(String accessToken, String documentBinderUuid, String token) {
		//header 설정
		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setContentType(new MediaType(MediaType.APPLICATION_JSON, Charset.forName("utf-8")));
		headers.set("Authorization", String.format("Bearer %s", accessToken));	

		//url 설정
		StringBuilder url = new StringBuilder();
		url.append(this.HOST)
		.append("/v1/")
		.append(documentBinderUuid)
		.append("/tokens/")
		.append(token);

		//api 호출
		ResponseEntity<String> resp = this.callApi(HttpMethod.GET, url.toString(), null, headers);
		return resp;
	}
	

	

	
	
	/**
	 * 문서 상태 변경 API
	 *  -.문서에 대해서 열람 상태로 변경. 사용자가 문서열람 시(OTT 검증 완료 후 페이지 로딩 완료 시점) 반드시 문서 열람 상태 변경 API를 호출해야 함.
	 *  -.미 호출 시 아래와 같은 문제 발생
	 *    1)유통증명시스템을 사용하는 경우 해당 API를 호출한 시점으로 열람정보가 등록되어 미 호출 시 열람정보가 등록 되지 않음.
	 *    2)문서상태조회 API(/v1/documents/{document_binder_uuid}/status) 호출 시 read_at최초 열람시간) 데이터가 내려가지 않음.
	 */
	public ResponseEntity<String> readCompleted(String accessToken, String documentBinderUuid) {
		//header 설정
		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setContentType(new MediaType(MediaType.APPLICATION_JSON, Charset.forName("utf-8")));
		headers.set("Authorization", String.format("Bearer %s", accessToken));	

		//url 설정
		StringBuilder url = new StringBuilder();
		url.append(this.HOST)
		.append("/v1/documents/")
		.append(documentBinderUuid);

		
		//body 설정
		String jsonStr = "{                                 "
						+"    \"document\" :{               "
						+"        \"is_detail_read\" : true "
						+"    }                             "
						+"}                                 ";
		

		//api 호출
		ResponseEntity<String> resp = this.callApi(HttpMethod.POST, url.toString(), jsonStr, headers);
		return resp;
		
	}
	

	

	
	
	/**
	 * 문서 상태 조회 API
	 *  -.이용기관 서버에서 카카오페이 전자문서 서버로 문서 상태에 대한 조회를 요청 합니다.
	 *    : 발송된 문서의 진행상태를 알고 싶은 경우, flow와 상관없이 요청 가능
	 *    : polling 방식으로 호출할 경우, 호출 간격은 5초를 권장.
	 *  -.doc_box_status 상태변경순서
	 *    : SENT(송신) > RECEIVED(수신) > READ(열람)/EXPIRED(미열람자료의 기한만료)
	 */
	public ResponseEntity<String> status(String accessToken, String contractUuid, String documentBinderUuid) {
		//header 설정
		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setContentType(new MediaType(MediaType.APPLICATION_JSON, Charset.forName("utf-8")));
		headers.set("Authorization", String.format("Bearer %s", accessToken));	
		headers.set("Contract-Uuid", contractUuid);	

		//url 설정
		StringBuilder url = new StringBuilder();
		url.append(this.HOST)
		.append("/v1/documents/")
		.append(documentBinderUuid)
		.append("/status");
		
		//api 호출
		ResponseEntity<String> resp = this.callApi(HttpMethod.GET, url.toString(), null, headers);
		return resp;
		
	}

	

	
	
	
	
	/**
	 * 대량(bulk) 문서발송 요청
	 * 	-.이용기관 서버에서 카카오페이 내문서함 서버로 대량(bulk) 문서발송 처리를 요청합니다.
	 */
//	POST /v1/documents/bulk HTTP/1.1
//	Host: docs-gw.kakaopay.com (전용선/VPN 일 경우 docs-gw-gs.kakaopay.com) 
//	Content-Type: application/json;charset=UTF-8
//	Authorization: Bearer {Access_Token} 
//	Contract-Uuid: {Contract-Uuid}
	public ResponseEntity<String> bulkSend(String accessToken, String contractUuid, String jsonStr) {
		//header 설정
		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setContentType(new MediaType(MediaType.APPLICATION_JSON, Charset.forName("utf-8")));
		headers.set("Authorization", String.format("Bearer %s", accessToken));	
		headers.set("Contract-Uuid", contractUuid);	

		//url 설정
		StringBuilder url = new StringBuilder();
		url.append(this.HOST)
		.append("/v1/documents/bulk");
		
		//api 호출
		ResponseEntity<String> resp = this.callApi(HttpMethod.POST, url.toString(), jsonStr, headers);
		return resp;
		
	}

	

	
	
	
	/**
	 * 대량(bulk) 문서 상태 조회 API
	 *  -.이용기관 서버에서 카카오페이 전자문서 서버로 문서 상태에 대한 조회를 요청 합니다.
	 *    : 발송된 문서의 진행상태를 알고 싶은 경우, flow와 상관없이 요청 가능
	 *    : polling 방식으로 호출할 경우, 호출 간격은 5초를 권장.
	 *  -.doc_box_status 상태변경순서
	 *    : SENT(송신) > RECEIVED(수신) > READ(열람)/EXPIRED(미열람자료의 기한만료)
	 */
//	POST /v1/documents/bulk/status HTTP/1.1
//	Host: docs-gw.kakaopay.com (전용선/VPN 일 경우 docs-gw-gs.kakaopay.com) 
//	Content-Type: application/json;charset=UTF-8
//	Authorization: Bearer {Access_Token} 
//	Contract-Uuid: {Contract-Uuid}
	public ResponseEntity<String> bulkStatus(String accessToken, String contractUuid, String jsonStr) {
		//header 설정
		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setContentType(new MediaType(MediaType.APPLICATION_JSON, Charset.forName("utf-8")));
		headers.set("Authorization", String.format("Bearer %s", accessToken));	
		headers.set("Contract-Uuid", contractUuid);	

		//url 설정
		StringBuilder url = new StringBuilder();
		url.append(this.HOST)
		.append("/v1/documents/bulk/status");
		
		//api 호출
		ResponseEntity<String> resp = this.callApi(HttpMethod.POST, url.toString(), jsonStr, headers);
		return resp;
	}

	

	
	
	
	/**
	 * <pre>메소드 설명: API 호출
	 * </pre>
	 * @param method
	 * @param url
	 * @param body
	 * @param headers
	 * @return ResponseEntity 요청처리 후 응답객체
	 * @author: 박민규
	 * @date: 2021. 8. 4.
	 * @apiNote: 사이트 참조 https://e2e2e2.tistory.com/15
	 */
	private ResponseEntity<String> callApi(HttpMethod method, String url, String body, HttpHeaders headers) {
		log.debug("param =======================");
		log.debug(body);
		
		
		ResponseEntity<String> responseEntity = null;
		try { 
			//uri 및 entity(param) 설정
			HttpEntity<?> entity = null;
			UriComponents uri = null;
			switch (method) {
				case GET:
					entity = new HttpEntity<>(headers);
					uri = UriComponentsBuilder
							.fromHttpUrl(String.format("%s?%s", url, body==null?"":body))
//							.encode(StandardCharsets.UTF_8)	//"%"기호가 "%25"로 인코딩 발생하여 주석처리 함.
							.build(false);
					break;
				case POST:
					entity = new HttpEntity<>(body, headers);
					uri = UriComponentsBuilder
							.fromHttpUrl(url)
							.encode(StandardCharsets.UTF_8)
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
			System.out.println("  url => " + uri.toString());
			System.out.println("  method => " + method);
			System.out.println("  headers => "+ entity.getHeaders().toString());
			System.out.println("  body => "+ entity.getBody());
			responseEntity = restTemplate.exchange(URI.create(uri.toString()), method, entity, String.class);  //이 한줄의 코드로 API를 호출해 String타입으로 전달 받는다.

			/*
			 * HttpStatus 정보 확인 방법
			 * 	-.코드: responseEntity.getStatusCodeValue()
			 * 	-.메시지: responseEntity.getStatusCode()
			 */
			
		} catch (HttpServerErrorException e) {	
			responseEntity = new ResponseEntity<String>(e.getResponseBodyAsString(), e.getStatusCode());
			log.error(String.format("call API 서버오류[url =>%s param => %s error => %s]", url, body, e.getMessage()));
        } catch (HttpClientErrorException e) {	
        	responseEntity = new ResponseEntity<String>(e.getResponseBodyAsString(), e.getStatusCode());
        	log.error(String.format("call API 클라이언트오류[url =>%s param => %s error => %s]", url, body, e.getMessage()));
        } catch (RestClientException e) {	//timeout 발생 또는 기타 오류... 
        	responseEntity = new ResponseEntity<String>(e.getMessage(), HttpStatus.REQUEST_TIMEOUT);
        	log.error(String.format("RestAPI 호출 오류[url =>%s param => %s error => %s]", url, body, e.getMessage()));
        } catch (Exception e) {
        	responseEntity = new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        	log.error(String.format("call API 기타오류[url =>%s param => %s error => %s]", url, body, e.getMessage()));
		}

		//결과 응답
		return responseEntity;
	}
	
}

