package cokr.xit.modules;

import java.util.LinkedList;
import java.util.Queue;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cokr.xit.code.CodeMapperFactory;
import cokr.xit.code.impl.Sample;
import cokr.xit.modules.domain.AccessLogRepository;
import cokr.xit.modules.post.domains.bill.dto.kko.KkoSendReqDTO;
import cokr.xit.utils.RequireValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class IndexController {

	private final AccessLogRepository accessLogRepository;
	private final CodeMapperFactory codeMapperFactory;
	
	@GetMapping("/")
	public String index() {
		log.info("Welcome to the world!!");
		codeMapperFactory.get(Sample.class).forEach((row) -> System.out.println(row.getCode()+"="+row.getCodeNm()));
		Queue<Integer> q = new LinkedList<Integer>();
		for(int i=1; i<=100; i++) {
			q.offer(i);
			if(i%10==0)
				System.out.println(String.format("poll => %d", q.poll()));
		}
		q.forEach(i -> System.out.println("value => "+i));
		for(int i=101; i<=200; i++) {
			q.offer(i);
			if(i%10==0)
				System.out.println(String.format("poll => %d", q.poll()));
		}
		q.forEach(i -> System.out.println("value => "+i));
		
		return "index";
	}

	@GetMapping("/sample.do")
	public String sample(HttpServletRequest req, @RequestBody KkoSendReqDTO dto) {
		/**
		 * 시작로그
		 */
//		AccessLog requstLog = AccessLog.builder()
//				.apiKey(null)                           //api Key
//				.reqSystemId(null)                      //요청 시스템 ID
//				.reqUrl(req.getRequestURL().toString()) //요청 URL
//				.reqParam(CmmnUtil.toJsonString(dto))   //요청 Parameter
//				.build();
//		accessLogRepository.save(requstLog);
		
		/** 
		 * 처리
		 */
		String respMsg = null;
		boolean isError = false;
		String errorMsg = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			respMsg = mapper.writeValueAsString(dto);
			throw new RuntimeException("에러야 에러");
		} catch (JsonProcessingException e) {
			isError = true;
			errorMsg = e.getMessage();
		} catch (Exception e) {
			isError = true;
			errorMsg = e.getMessage();
		} finally {
			/**
			 * 종료로그
			 */
//			requstLog.setRespMsg(respMsg);
//			requstLog.setResult(isError?"FAIL":"OK");
//			requstLog.setErrorMsg(errorMsg);
//			accessLogRepository.save(requstLog);
		}
		
		
		return "default";
	}
	
	
	@GetMapping("/kko/send.do")
	public String kkoSendReqDTO(@RequestBody KkoSendReqDTO dto) {
		log.info(String.format("called %s.%s", getClass().getName(), new Object() {}.getClass().getEnclosingMethod().getName() ));
		
		//유효성 테스트
		log.info(String.format("유효성 검증 결과::: %s", RequireValidator.builder()
													.obj(dto)
													.build()
													.validate()
													.getMessage()
													)
		);
		log.info(String.format("유효성 검증 결과::: %s", RequireValidator.builder()
				.obj(dto.getData())
				.build()
				.validate()
				.getMessage()
				)
				);
		
		
		//parse 테스트
		String jsonStr = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			jsonStr = mapper.writeValueAsString(dto);
		} catch (JsonProcessingException e) {
			
			
			e.printStackTrace();
		}
		log.info(String.format("json문자열 변환 결과::: %s", jsonStr));
		
		
		return "default";
	}
	
}
