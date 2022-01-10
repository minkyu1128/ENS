package cokr.xit.utils;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class JwtUtilTest {

	private static String ACCESS_TOKEN = null;
	
	@Test
	@Order(1)
	@SuppressWarnings({ "static-access" })
	void testCreate() {
		System.out.println("\n[Create Token]");
//		Map<String, Boolean> roles = new HashMap<>();
//		roles.put("www.test.co.kr/kko/mydoc", true);
//		roles.put("www.test.co.kr/kko/mydoc/noti", true);
		String[] roles = new String[] {"/kko/mydoc/.*$", "/nice/.*$", "/kafka/.*$"};
		this.patternCheck(roles);

		this.ACCESS_TOKEN = JwtUtil.createBuilder()
				.secretKey(JwtUtil.DEFAULT_SECRET_KEY)
				.iss("ENS")
				.sub("전자고지시스템")
				.aud("https://www.test.co.kr")
				.exp(new Date(CmmnUtil.dateToAbsTime("20301128")))
				.nbf(null)
				.iat(new Date())
				.uUuid(UUID.randomUUID().toString())
				.roles(roles)
				.build()
				.getToken();
		
		System.out.println(String.format("  token => %s", ACCESS_TOKEN));
	}

	@Test
	@Order(2)
	void testDecode() throws JsonMappingException, JsonProcessingException {
		System.out.println("\n[Decode Token]");
		JwtUtil jwtUtil = JwtUtil.decodeBuilder()
				.jwt(ACCESS_TOKEN)
				.build();
		System.out.println(String.format("  token => %s", jwtUtil.getToken()));
		System.out.println(String.format("  header => %s", jwtUtil.getHeader()));
		System.out.println(String.format("  payload => %s", jwtUtil.getPayload()));
		
		ObjectMapper mapper = new ObjectMapper();
		System.out.println(String.format("  payload to Map String => %s", mapper.readValue(jwtUtil.getPayload(), Map.class).toString()));
	}

	@Test
	@Order(3)
	void testVerify() {
		System.out.println("\n[Verify Token]");
		JwtUtil jwtUtil = JwtUtil.verifyBuilder()
				.secretKey(JwtUtil.DEFAULT_SECRET_KEY)
				.jwt(ACCESS_TOKEN)
				.build();
		boolean isCertified = jwtUtil.isCertified();
		System.out.println(String.format("  token => %s", ACCESS_TOKEN));
		System.out.println(String.format("  isCertified => %s", isCertified));
		StringBuilder sb = new StringBuilder();
		for(String str : jwtUtil.getClaims().get("roles").asArray(String.class))
			sb.append(",").append(str);
		System.out.println(String.format("  roles => [%s]", sb.toString().substring(1)));
	}
	
	@Test
	@Order(4)
	void testVerifyByModulateToken() {
		System.out.println("\n[Verify ModulateToken]");

		StringBuilder modulateToken = new StringBuilder();
		modulateToken.append(ACCESS_TOKEN.split("\\.")[0])
		.append(".")
		.append("eyJzdWIiOiLtmozsm5BpZCIsImlzcyI6ImdnZ2ciLCJtZW1iZXJJZCI6ImhhaGFoYWgifQ")
		.append(".")
		.append(ACCESS_TOKEN.split("\\.")[2]);
		
		boolean isCertified = JwtUtil.verifyBuilder()
				.secretKey(JwtUtil.DEFAULT_SECRET_KEY)
				.jwt(modulateToken.toString())
				.build()
				.isCertified();
		
		System.out.println(String.format("  token => %s", ACCESS_TOKEN));
		System.out.println(String.format("  isCertified => %s", isCertified));
	}
	

	@Test
	@Order(5)
	void testClaimsToString() {
		System.out.println("\n[Claims to String]");
		JwtUtil.verifyBuilder()
		.secretKey(JwtUtil.DEFAULT_SECRET_KEY)
		.jwt(ACCESS_TOKEN)
		.build()
		.claimsToString();
	}

	
	private void patternCheck(String[] roles) {

		for(String role : roles) {
			Pattern pattern = Pattern.compile("/kko/mydoc/.*/bulk$");
			Matcher matcher = pattern.matcher(role);
			
			System.out.println(String.format("%s is %s", role, matcher.find()));
		}
		
	}
}
