package cokr.xit.modules.junittest.jwt;

import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;

import cokr.xit.utils.CmmnUtil;



/* ====================================
 * JWT 참고 사이트
 *  -. JWT란? 
 *    : https://bcp0109.tistory.com/321
 *  -. STS JWT 적용
 *    : https://youngwoon.tistory.com/2
 *  -. Jav JWT 예제
 *    : https://kitty-geno.tistory.com/33
 *    : https://qteveryday.tistory.com/223
 *  -.Jwt토큰 생성 및 복호화
 *    : https://sang12.co.kr/237/spring-boot%28Java%29-Jwt%ED%86%A0%ED%81%B0-%EC%83%9D%EC%84%B1-%EB%B0%8F-%EB%B3%B5%ED%98%B8%ED%99%94-%ED%95%98%EA%B8%B0
 *  -.REST API Access Token 발급, 재발행과 재사용
 *    : https://docs.iamport.kr/tech/access-token
 ==================================== */
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class JwtTestSample {
	
	private final String SECRET_KEY = "TEST";
	
	private static String ACCESS_TOKEN = null;
	

	
	@Test
	@Order(1)
	void create() {
//		System.out.println(String.format("Random Uuid => %s", UUID.randomUUID().toString()));
//		System.out.println(String.format("Str A-B-C-D-E Uuid => %s", UUID.fromString("A-B-C-D-E").toString()));
//		System.out.println(String.format("Str A-B-C-D-E Uuid => %s", UUID.fromString("A-B-C-D-E").toString()));
		System.out.println(CmmnUtil.absSecTimeToDate(1639231881, "yyyy-MM-dd HH:mm:ss"));
		System.out.println(CmmnUtil.absSecTimeToDate(1606533000, "yyyy-MM-dd HH:mm:ss"));
		
		System.out.println("\nJWT Create...");
		// Create JWT Token
		String token = JWT.create()
		    .withIssuer("발급기관")                                             //[payload]토큰 발급자
		    .withSubject("ENS-Token")                                         //[payload]토큰 제목
		    .withAudience("이용기관")                                           //[payload]토큰 대상
		    .withExpiresAt(new Date(System.currentTimeMillis() + 864000000))  //[payload]토큰의 만료시간
//		    .withExpiresAt(new Date(System.currentTimeMillis() - 864000000))  //[payload]토큰의 만료시간
		    .withNotBefore(new Date(CmmnUtil.dateToAbsTime(2021, 12, 28, 10, 30, 0)))  //[payload]Not Before. 토큰을 활성화할 시간
		    .withIssuedAt(new Date(System.currentTimeMillis()))               //[payload]토큰이 발급된 시간
		    .withJWTId("JWT ID")                                              //[payload]JWT의 고유 식별자
		    .withClaim("uUuid", UUID.randomUUID().toString())                 //[payload]위 기본 클레임(속성정보) 외에 key, value 타입으로 설정 가능. url-safe 한 정보만 설정하길 권장 한다.
		    .withClaim("uUrl", "www.test.co.kr")
		    .sign(Algorithm.HMAC512(SECRET_KEY.getBytes()));

		//jwt token header.payload.signature
		System.out.println(String.format("[Token(Header.Payload.Signature)] => %s", token));
		ACCESS_TOKEN = token;

	}
	
	@Test
	@Order(2)
	void decode() {
		System.out.println("\nJWT Decode...");
		
		Decoder decoder = Base64.getDecoder(); 
		final String[] splitJwt = ACCESS_TOKEN.split("\\.");
		final String header = new String(decoder.decode(splitJwt[0].getBytes()));
		final String payload = new String(decoder.decode(splitJwt[1].getBytes()));

		System.out.println(String.format("[Header] => %s", header));
		System.out.println(String.format("[Payload] => %s", payload));
		
//		Mapper
//		Long sec 
//		CmmnUtil.absSecTimeToDate(null, payload)

		
	}
	
	@Test
	@Order(3)
	void verify() {
		System.out.println("\nJWT Verify...");
		
		
		//검증
		try {
			System.out.println(String.format("[Token] => %s", ACCESS_TOKEN));
			Map<String, Claim> result = JWT.require(Algorithm.HMAC512(SECRET_KEY.getBytes()))
			.build()
			.verify(ACCESS_TOKEN.replace("Bearer", ""))
//			.getClaim("UserId");
			.getClaims();
			
			//result
			Iterator<String> it = result.keySet().iterator();
			while(it.hasNext()) {
				String key = it.next();
				Claim claim = result.get(key);
				
				System.out.println(String.format("    %s = %s", key, claim.asString()));
			}
			System.out.println(String.format("    %s = %s", "사용자Uuid ", result.get("uUuid").asString()));
			System.out.println(String.format("    %s = %s", "사용자URL", result.get("uUrl").asString()));
		}catch(Exception e) {
			System.out.println(String.format("Token Verify Fail... %s", e.getMessage()));
		}
		
		//검증(토큰변조)
		try {
			StringBuilder modulateToken = new StringBuilder();
			modulateToken.append(ACCESS_TOKEN.split("\\.")[0])
			.append(".")
			.append("eyJzdWIiOiLtmozsm5BpZCIsImlzcyI6ImdnZ2ciLCJtZW1iZXJJZCI6ImhhaGFoYWgifQ")
			.append(".")
			.append(ACCESS_TOKEN.split("\\.")[2]);
			
			System.out.println(String.format("[Token] => %s", modulateToken));
			Claim result = JWT.require(Algorithm.HMAC512(SECRET_KEY.getBytes()))
					.build()
					.verify(modulateToken.toString().replace("Bearer", ""))
					.getClaim("uUuid");
			
			//result
			System.out.println(result.asString());
		}catch(Exception e) {
			System.out.println(String.format("Token Verify Fail... %s", e.getMessage()));
		}
	}
}
