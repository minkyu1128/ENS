package cokr.xit.config;

import java.util.Arrays;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import cokr.xit.filter.RequestWrapperFilter;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Bean(name = "jsonView")
	public MappingJackson2JsonView jsonView() {
		return new MappingJackson2JsonView();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean 
	public FilterRegistrationBean requestWrapperFilter() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new RequestWrapperFilter());
		filterRegistrationBean.setUrlPatterns(Arrays.asList("/*"));
		return filterRegistrationBean;
	}
	
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
	 *    
	 *    
	 * OAuth 참고 사이트
	 *  -.OAuth 2.0 개념정리
	 *    : https://blog.naver.com/pjok1122/221583426424
	 *  -.AccessToken 발급 과정
	 *    : https://blog.naver.com/pjok1122/221584346870
	 *  -.구글 로그인 등록 절차
	 *    : https://blog.naver.com/pjok1122/221586183453
	 ==================================== */
}
