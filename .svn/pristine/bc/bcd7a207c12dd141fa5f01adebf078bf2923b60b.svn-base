package cokr.xit.modules.usermng.service;

import java.util.Map;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
class UserElctrnNticServiceTest {

	@Autowired
	private UserElctrnNticService service;

	@SuppressWarnings("unchecked")
	@Test @Disabled
	void test() {
//		{"contractUuid":"CON-b4126c833d1011ecbceae6cf4630da62","jsonStr":"{\"documents\":[{\"title\":\"불법주정차 사전통지서\",\"hash\":null,\"common_categories\":[\"NOTICE\"],\"read_expired_at\":null,\"read_expired_sec\":null,\"receiver\":{\"ci\":\"S++biC1tlFMFBCIUqbRajTY5Cvz9xQHR2/PrxyuT4KvdZslLBHopdGGSPx8KzQ3GkopPSOhj2lyfbhjyyRZSTQ==\",\"phone_number\":null,\"name\":null,\"birthday\":null,\"is_required_verify_name\":null},\"property\":{\"link\":\"www.xit.co.kr\",\"payload\":null,\"message\":\"귀하의 차량이 불법주정차 차량으로 사전통지서 안내드립니다.\",\"cs_number\":\"02-581-1807\",\"cs_name\":\"문의처\",\"external_document_uuid\":\"B-20211228222859071TJ8ib000000001\"},\"bridge\":null},{\"title\":\"불법주정차 사전통지서\",\"hash\":null,\"common_categories\":[\"NOTICE\"],\"read_expired_at\":null,\"read_expired_sec\":null,\"receiver\":{\"ci\":\"S++biC1tlFMFBCIUqbRajTY5Cvz9xQHR2/PrxyuT4KvdZslLBHopdGGSPx8KzQ3GkopPSOhj2lyfbhjyyRZSTQ==\",\"phone_number\":null,\"name\":null,\"birthday\":null,\"is_required_verify_name\":null},\"property\":{\"link\":\"www.xit.co.kr\",\"payload\":null,\"message\":\"귀하의 차량이 불법주정차 차량으로 사전통지서 안내드립니다.\",\"cs_number\":\"02-581-1807\",\"cs_name\":\"문의처\",\"external_document_uuid\":\"B-20211228222859071TJ8ib000000002\"},\"bridge\":null}],\"notidocuments\":[]}","accessToken":"b40d3c623d1011ecbceae6cf4630da62"}
		String str = "{\"documents\":[{\"title\":\"불법주정차 사전통지서\",\"hash\":null,\"common_categories\":[\"NOTICE\"],\"read_expired_at\":null,\"read_expired_sec\":null,\"receiver\":{\"ci\":\"S++biC1tlFMFBCIUqbRajTY5Cvz9xQHR2/PrxyuT4KvdZslLBHopdGGSPx8KzQ3GkopPSOhj2lyfbhjyyRZSTQ==\",\"phone_number\":null,\"name\":null,\"birthday\":null,\"is_required_verify_name\":null},\"property\":{\"link\":\"www.xit.co.kr\",\"payload\":null,\"message\":\"귀하의 차량이 불법주정차 차량으로 사전통지서 안내드립니다.\",\"cs_number\":\"02-581-1807\",\"cs_name\":\"문의처\",\"external_document_uuid\":\"B-20211228222859071TJ8ib000000001\"},\"bridge\":null},{\"title\":\"불법주정차 사전통지서\",\"hash\":null,\"common_categories\":[\"NOTICE\"],\"read_expired_at\":null,\"read_expired_sec\":null,\"receiver\":{\"ci\":\"S++biC1tlFMFBCIUqbRajTY5Cvz9xQHR2/PrxyuT4KvdZslLBHopdGGSPx8KzQ3GkopPSOhj2lyfbhjyyRZSTQ==\",\"phone_number\":null,\"name\":null,\"birthday\":null,\"is_required_verify_name\":null},\"property\":{\"link\":\"www.xit.co.kr\",\"payload\":null,\"message\":\"귀하의 차량이 불법주정차 차량으로 사전통지서 안내드립니다.\",\"cs_number\":\"02-581-1807\",\"cs_name\":\"문의처\",\"external_document_uuid\":\"B-20211228222859071TJ8ib000000002\"},\"bridge\":null}],\"notidocuments\":[]}";
		Map<String, Object> m = null;
		try {
//			m = (Map<String, Object>) CmmnUtil.jsonStringtoObj(Map.class, str);
			ObjectMapper mapper  = new ObjectMapper();
			m = mapper.readValue(str, Map.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(m.toString());
	}

	@Test @Disabled
	void testSend() {
		//given
		String beginSndngDt = "20211201"+"000000";
		String endSndngDt = "20211231"+"000000";

		//when
		service.send(beginSndngDt, endSndngDt);

		//then
	}

	@Test @Disabled
	void testResult() {
		//given
		String beginSndngDt = "20211201"+"000000";
		String endSndngDt = "20211231"+"000000";

		//when
		service.result(beginSndngDt, endSndngDt);

		//then
	}

	@Test @Disabled
	void testStatus() {
		//given

		//when
		service.status();

		//then

	}

	@Test
	void testFetchStatus() {
		//given

		//when
		service.fetchStatus();

		//then

	}

}
