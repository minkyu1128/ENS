package cokr.xit.modules.kkomydoc.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cokr.xit.modules.kkomydoc.model.OttRespDTO;
import cokr.xit.utils.SHA256;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class KkoMyDocApiTest {
	
	
	@Autowired
	KkoMyDocApi kkoMyDocApi;
	
	private final String Access_Token = "b40d3c623d1011ecbceae6cf4630da62";
	private final String Contract_Uuid = "CON-b4126c833d1011ecbceae6cf4630da62";
	@Test @Disabled
	void testTimeTrans() throws NoSuchAlgorithmException {
		Long absTime = dateToAbsTime(2021, 4, 1, 0, 0, 0);
		System.out.println(String.format("상대시간(ms): after 30 days..? %s", daysToRelTime(30)));
		System.out.println(String.format("상대시간(s): after 30 days..? %s", daysToRelTime(30)/1000));
		System.out.println(String.format("절대시간: %s", absTime));
		System.out.println(String.format("변환시간: %s", absTimeToDate(absTime, null)));
		System.out.println(String.format("변환시간: %s", absTimeToDate(1527590621000L, null)));
		System.out.println(String.format("Sha245 hash hexString: 123 => %s", SHA256.encrypt("123")));
		System.out.println(String.format("Sha245 hash hexString: abcd => %s", SHA256.encrypt("abcd")));
	}
	
	@Test @Disabled
	void testSend() {
		 //given(준비)
		String accessToken = this.Access_Token;
		String contractUuid = this.Contract_Uuid;
		String jsonStr = this.singleSendData();
		
		
		
		
		 //when(실행)
		ResponseEntity<String> resp = kkoMyDocApi.send(accessToken, contractUuid, jsonStr);
		

		//then(검증)
		assertThat(resp.getStatusCode(), equalTo(HttpStatus.OK));
	}
	@Test @Disabled
	void testSendFail() throws JsonMappingException, JsonProcessingException {
		//given(준비)
		String accessToken = "";
		String contractUuid = this.Contract_Uuid;
		String jsonStr = this.singleSendData();
		
		
		//when(실행)
		ResponseEntity<String> resp = kkoMyDocApi.send(accessToken, contractUuid, jsonStr);
		System.out.println("testSend"+" ERROR:::: " + resp.getBody());
		System.out.println("resp.getStatusCodeValue():::: " + resp.getStatusCodeValue());
		ObjectMapper mapper = new ObjectMapper();
		Map data = 	mapper.readValue(resp.getBody(), Map.class);
		
		
		//then(검증)
		assertThat(resp.getStatusCode(), equalTo(HttpStatus.UNAUTHORIZED));
		assertThat("UNAUTHORIZED", equalTo(data.get("error_code")));
		
	}

	@SuppressWarnings("unchecked")
	@Test 
	void testToken() throws JsonMappingException, JsonProcessingException {
		 //given(준비)
		String accessToken = this.Access_Token;
		String documentBinderUuid = "BIN-94d5b1704b6711eca652ca4221b985e0";
		String oneTimeToken = "NGM5YjRjOWU0YzMwMTFlY2ExNDE2ZWMxODY3MDJlNGM%3D";
		
		
		 //when(실행)
		ResponseEntity<String> resp = kkoMyDocApi.token(accessToken, documentBinderUuid, oneTimeToken);
		if(resp.getStatusCode() == HttpStatus.OK)
			System.out.println("testSend"+" Success");
		else
			System.out.println("testSend"+" ERROR:::: " + resp.getBody());
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> data = mapper.readValue(resp.getBody(), Map.class);
		OttRespDTO respDTO = mapper.readValue(resp.getBody(), OttRespDTO.class);
		
		System.out.println("---------- testToken Result.... --------\n"+resp.getBody());
		

		//then(검증)
		assertThat(resp.getStatusCode(), equalTo(HttpStatus.OK));
		assertThat(data.get("token_status"), equalTo("USED"));
	}

	@Test @Disabled
	void testReadCompleted() {
		 //given(준비)
		String accessToken = this.Access_Token;
		String documentBinderUuid = "BIN-b7d8051c484a11ecab5da67e95426de7";
		
		//when(실행)
		ResponseEntity<String> resp = kkoMyDocApi.readCompleted(accessToken, documentBinderUuid);
		if(resp.getStatusCode() == HttpStatus.NO_CONTENT)
			System.out.println("testReadCompleted"+" Success");
		else
			System.out.println("testReadCompleted"+" ERROR:::: " + resp.getBody());
		
		
		//then(검증)
		assertThat(resp.getStatusCode(), equalTo(HttpStatus.NO_CONTENT));
	}

	@Test @Disabled
	void testStatus() {
		 //given(준비)
		String accessToken = this.Access_Token;
		String contractUuid = this.Contract_Uuid;
		String documentBinderUuid = "BIN-b7d8051c484a11ecab5da67e95426de7";
		
		
		 //when(실행)
		ResponseEntity<String> resp = kkoMyDocApi.status(accessToken, contractUuid, documentBinderUuid);
		if(resp.getStatusCode() == HttpStatus.OK)
			System.out.println("testStatus"+" Success");
		else
			System.out.println("testStatus"+" ERROR:::: " + resp.getBody());
		
		System.out.println("---------- testStatus Result.... --------\n"+resp.getBody());

		//then(검증)
		assertThat(resp.getStatusCode(), equalTo(HttpStatus.OK));
	}

	@Test @Disabled
	void testBulkSend() {
		 //given(준비)
		String accessToken = this.Access_Token;
		String contractUuid = this.Contract_Uuid;
		String jsonStr = this.bulkSendData();
		
		
		 //when(실행)
		ResponseEntity<String> resp = kkoMyDocApi.bulkSend(accessToken, contractUuid, jsonStr);
		if(resp.getStatusCode() == HttpStatus.OK)
			System.out.println("testBulkSend"+" Success");
		else
			System.out.println("testBulkSend"+" ERROR:::: " + resp.getBody());
		

		//then(검증)
		assertThat(resp.getStatusCode(), equalTo(HttpStatus.OK));
	}

	@Test @Disabled
	void testBulkStatus() throws JsonProcessingException {
		 //given(준비)
		String accessToken = this.Access_Token;
		String contractUuid = this.Contract_Uuid;
		String jsonStr = "";
		String[] document_binder_uuids = {
		        "BIN-1c256fb9476f11ecb07c1687a914c2e1"
		        , "BIN-b7d8051c484a11ecab5da67e95426de7"
		        , "BIN-c74f4b8c87a811ebbcd4acde48001122"};
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("document_binder_uuids", document_binder_uuids);
		ObjectMapper mapper = new ObjectMapper();
		jsonStr = mapper.writeValueAsString(m);
		
		 //when(실행)
		ResponseEntity<String> resp = kkoMyDocApi.bulkStatus(accessToken, contractUuid, jsonStr);
		if(resp.getStatusCode() == HttpStatus.OK) {
			System.out.println("testBulkStatus"+" Success");
			Map data = mapper.readValue(resp.getBody(), Map.class);
			try{
				List<Map<String, Object>> list =  (List<Map<String, Object>>) data.get("documents");
				int i = 0;
				for(Map<String, Object> row : list) {
					i++;
					Iterator<String> it = row.keySet().iterator();
					while(it.hasNext()) {
						String key = it.next();
						System.out.println(String.format("[%s] %s=%s", i, key, row.get(key)));
					}
				}
			}catch (Exception e) {
				System.out.println("ERROR ===>"+e.getMessage());
			}finally {
				System.out.println("testBulkStatus"+" Success Data Print End");
			}
//			while(it.hasNext()) {
//				String key = (String) it.next();
//				System.out.println("");
//			}
			
		}
		else
			System.out.println("testBulkStatus"+" ERROR:::: " + resp.getBody());
		
 
		//then(검증)
		assertThat(resp.getStatusCode(), equalTo(HttpStatus.OK));
	}
	
	
	/**
	 * <pre>메소드 설명: 절대시간(단위: second)을 현재 시간으로 반환 한다.</pre>
	 * @param sec
	 * @param fmt
	 * @return String 요청처리 후 응답객체
	 * @author: 박민규
	 * @date: 2021. 11. 19.
	 */
	private String absTimeToDate(int sec, String fmt) {
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
	private String absTimeToDate(Long millionSec, String fmt) {
		if(fmt == null || "".equals(fmt)) fmt = "yyyy-MM-dd HH:mm:ss";
		
		Date date = new Date(millionSec);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(fmt);
		
		return simpleDateFormat.format(date);
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
	private Long dateToAbsTime(int year, int month, int day, int hour, int minute, int sec) {
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
	private Long daysToRelTime(int days) {
		if(days<0) days=0;
		return days * 24L * 60L * 60L * 1000L;
	}
	
	
	private String singleSendData() {
		Long read_expired_at = this.dateToAbsTime(2030, 1, 1, 0, 0, 0)/1000;	//처리마감시간(절대시간). 단위: second
		Long read_expired_sec = this.daysToRelTime(365)/1000;	//처리마감시간(상대시간). 단위: second
		
		StringBuilder jsonStr = new StringBuilder();
		jsonStr.append("")
		.append("{")
		.append("    \"document\": {")
		.append("        \"title\": \"불법주정차위반과태료 사전통지서\",")
		.append("        \"common_categories\": [")
		.append("            \"BILL_PAY\"")	//Default: Default, BILL: 고지서, BILL_PAY: 납부가 포함된 고지서, NOTICE: 안내문, CONTRACT: 계약서, REPORT: 리포트
		.append("        ],")
		.append("        \"read_expired_at\": "+read_expired_at+",")
//		.append("        \"read_expired_sec\": "+read_expired_sec+",")
		.append("        \"hash\": \"6EFE827AC88914DE471C621AE\",")
		.append("        \"receiver\": {")
		.append("            \"phone_number\": \"01065303480\",")
		.append("            \"name\": \"박민규\",")
		.append("            \"birthday\": \"19861128\",")
		.append("            \"is_required_verify_name\": false")
		.append("        },")
		.append("        \"property\": {")
		.append("            \"link\": \"http://www.xit.co.kr\",")
		.append("            \"payload\": \"payload 파라미터 입니다.\",")
		.append("            \"message\": \"테스트\",")
		.append("            \"cs_number\": \"02-123-4567\",")
		.append("            \"cs_name\": \"콜센터\"")
		.append("        }")
		.append("    }")
		.append("}");
		
		return jsonStr.toString();
	}
	
	
	private String bulkSendData() {
		Long read_expired_at = this.dateToAbsTime(2030, 1, 1, 0, 0, 0)/1000;	//처리마감시간(절대시간). 단위: second
		Long read_expired_sec = this.daysToRelTime(365)/1000;	//처리마감시간(상대시간). 단위: second
		String message = null;
		String hash = null;
		message = "해당 안내문의 카테고리는 NOTICE와 BILL 입니다.";
		
		StringBuilder jsonStr = new StringBuilder();
		jsonStr.append("")
		.append("{")
		.append("    \"documents\": [")
		.append("        {")
		.append("            \"title\": \"대량문서발송. catgy=NOTICE/BILL\",")
		.append("            \"common_categories\": [")
		.append("                \"NOTICE\", \"BILL\"")	//Default: Default, BILL: 고지서, BILL_PAY: 납부가 포함된 고지서, NOTICE: 안내문, CONTRACT: 계약서, REPORT: 리포트
		.append("            ],")
		.append("            \"read_expired_at\": "+read_expired_at+",")	//처리마감시간(절대시간)
		.append("            \"hash\": \""+hash+"\",")
		.append("            \"receiver\": {")
		.append("                \"phone_number\": \"01065303480\",")
		.append("                \"name\": \"박민규\",")
		.append("                \"birthday\": \"19861128\",")
		.append("                \"is_required_verify_name\": false")
		.append("            },")
		.append("            \"property\": {")
		.append("                \"link\": \"https://www.kakaopay.com\",")
		.append("                \"payload\": \"payload 파라미터 입니다.\",")
		.append("                \"message\": \""+message+"\",")
		.append("                \"cs_number\": \"02-123-4567\",")
		.append("                \"cs_name\": \"콜센터\",")
		.append("                \"external_document_uuid\": \"A0001\"")
		.append("            }")
		.append("        },")
		.append("        {")
		.append("            \"title\": \"대량문서발송 FAIL\",")
		.append("            \"common_categories\": [")
		.append("                \"NOTICE\", \"BILL\", \"BILL_PAY\"")
		.append("            ],")
		.append("            \"read_expired_sec\": "+read_expired_sec+",")	//처리마감시간(상대시간)
		.append("            \"hash\": \"6EFE827AC88914DE471C621AE\",")
		.append("            \"receiver\": {")
		.append("                \"is_required_verify_name\": false")
		.append("            },")
		.append("            \"property\": {")
		.append("                \"link\": \"https://www.kakaopay.com\",")
		.append("                \"payload\": \"payload 파라미터 입니다.\",")
		.append("                \"message\": \"해당 안내문의 카테고리는 NOTICE/BILL/BILL_PAY 입니다.\",")
		.append("                \"cs_number\": \"02-123-4567\",")
		.append("                \"cs_name\": \"콜센터\",")
		.append("                \"external_document_uuid\": \"B0001\"")
		.append("            }")
		.append("        },")
		.append("        {")
		.append("            \"title\": \"대량문서발송. catgy=NOTICE/BILL/BILL_PAY\",")
		.append("            \"common_categories\": [")
		.append("                \"NOTICE\", \"BILL\", \"BILL_PAY\"")
		.append("            ],")
		.append("            \"read_expired_sec\": "+read_expired_sec+",")	//처리마감시간(상대시간)
		.append("            \"hash\": \"6EFE827AC88914DE471C621AE\",")
		.append("            \"receiver\": {")
//		.append("                \"ci\": \"vMtqVxJX56lBgbf9heK3QTc+jVndTfK77i/UJKAzPmBG4n9CazCdd/8YytlFZnN4qofIqgxHpSoiG0yYzgEpJg==\"")
		.append("                \"phone_number\": \"01065303480\",")
		.append("                \"name\": \"박민규\",")
		.append("                \"birthday\": \"19861128\",")
		.append("                \"is_required_verify_name\": false")
		.append("            },")
		.append("            \"property\": {")
		.append("                \"link\": \"https://www.kakaopay.com\",")
		.append("                \"payload\": \"payload 파라미터 입니다.\",")
		.append("                \"message\": \"해당 안내문의 카테고리는 NOTICE/BILL/BILL_PAY 입니다.\",")
		.append("                \"cs_number\": \"02-123-4567\",")
		.append("                \"cs_name\": \"콜센터\",")
		.append("                \"external_document_uuid\": \"B0001\"")
		.append("            }")
		.append("        }")
		.append("    ]")
		.append("}");
		
		return jsonStr.toString();
	}

}
