package cokr.xit.utils;

import java.util.Base64;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Base64.Decoder;

import org.springframework.util.StringUtils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;

import lombok.Builder;
import lombok.Getter;

@Getter
public class JwtUtil {

	@SuppressWarnings("unused")
	public final static String DEFAULT_SECRET_KEY = "czovL3d3dy50ZXN0LmNvLmtyIiwicm9sZX";
	
	private String token;
	
	private String header;
	private String payload;
	
	private boolean isCertified = false;
	private Map<String, Claim> claims;
	
	@SuppressWarnings("deprecation")
	@Builder(builderClassName = "createBuilder", builderMethodName = "createBuilder")
	public JwtUtil(String secretKey, String iss, String sub, String aud, Date exp, Date nbf, Date iat, String jti, String uUuid, String[] roles) {
		if(StringUtils.isEmpty(secretKey))
			throw new RuntimeException("SecretKey(은)는 필수조건 입니다.");
		
		this.token = JWT.create()
			    .withIssuer(iss)             //토큰 발급자
			    .withSubject(sub)            //토큰 제목
			    .withAudience(aud)           //토큰 대상자
			    .withExpiresAt(exp)          //토큰의 만료시간
			    .withNotBefore(nbf)          //토큰의 활성화 시작시간
			    .withIssuedAt(iat)           //토큰의 발급시간
			    .withJWTId(jti)              //JWT의 고유식별자로서, 주로 중복적인 처리 방지를 위하여 사용. 일회용 토큰에 사용하면 유용
			    .withClaim("uUuid", uUuid)   //User 고유식별자
			    .withArrayClaim("roles", roles)   //인가 Uri
			    .sign(Algorithm.HMAC512(secretKey.getBytes()));
	}
	
	
	@Builder(builderClassName = "decodeBuilder", builderMethodName = "decodeBuilder")
	public JwtUtil(String jwt) {

		Decoder decoder = Base64.getDecoder(); 
		final String[] splitJwt = jwt.split("\\.");
		this.token = jwt;
		this.header = new String(decoder.decode(splitJwt[0].getBytes()));
		this.payload = new String(decoder.decode(splitJwt[1].getBytes()));
	}
	
	
	@Builder(builderClassName = "verifyBuilder", builderMethodName = "verifyBuilder")
	public JwtUtil(String secretKey, String jwt) {
		this.verify(secretKey, jwt, Algorithm.HMAC512(secretKey.getBytes()));
	}
	@Builder(builderClassName = "verifyBuilderByAlgorithm", builderMethodName = "verifyBuilderByAlgorithm")
	public JwtUtil(String secretKey, String token, Algorithm algorithm) {
		this.verify(secretKey, token, algorithm);
	}
	@SuppressWarnings("deprecation")
	private void verify(String secretKey, String jwt, Algorithm algorithm) {
		if(StringUtils.isEmpty(secretKey))
			throw new RuntimeException("SecretKey(은)는 필수조건 입니다.");

		try {
			System.out.println(String.format("[Token] => %s", jwt));
			this.claims = JWT.require(algorithm)
				.build()
				.verify(jwt.replace("Bearer", "").trim())
				.getClaims();
			
			this.isCertified = true;
		}catch(Exception e) {
			this.isCertified = false;
			System.out.println(String.format("Token Verify Fail... %s", e.getMessage()));
		}
	}
	
	public void claimsToString() {
		System.out.println("[Token Claims]");
		if(this.claims == null)
			return;
		
		Iterator<String> it = this.claims.keySet().iterator();
		while(it.hasNext()) {
			String key = it.next();
			Claim claim = this.claims.get(key);
//			System.out.println(String.format("    %s = %s", key, claim.asString()));
			if(claim.asMap() != null)
				System.out.println(String.format("    %s = %s", key, claim.asMap().toString()));
			else if(claim.asArray(String.class) != null) {
				StringBuilder sb = new StringBuilder();
				for(String str : claim.asArray(String.class))
					sb.append(",").append(str);
				System.out.println(String.format("    %s = [%s]", key, sb.toString().substring(1)));
			} else
				System.out.println(String.format("    %s = %s", key, claim.as(String.class)));
		}
	}
	
}
