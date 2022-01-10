package cokr.xit.modules.post.domains.sign.service.biz;


import org.springframework.http.ResponseEntity;

import cokr.xit.modules.common.code.InternalErrCd;
import cokr.xit.modules.common.exception.InternalException;
import cokr.xit.modules.post.domains.sign.sender.KKoSignTalkSender;
import cokr.xit.modules.post.domains.sign.service.ApiStrategy;
import cokr.xit.modules.post.domains.sign.service.Response;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KkoBulkSignPrefix implements ApiStrategy{

	private Response response;
	
	//init 필드
	private KKoSignTalkSender sender;

	//etc...
	
	
	public KkoBulkSignPrefix(KKoSignTalkSender sender){
		this.sender   = sender;
		this.response = new Response();
//		this.sendMast = SendMast.builder().build();
	}
	

	@Override
	public void execute() {
		try {
			//전송
			String respPlainText = this.validate()
					.preHandle()
					.call();
			response.setOkResult(respPlainText);
		} catch (InternalException e) {
			response.setFailResult(e.getErrCd(), e.getMessage());
		} catch (Exception e) {
			response.setFailResult(InternalErrCd.ERR999, e.getMessage());
		} finally {
//			//후처리
			this.postHandle();
		}
		
	}

	@Override
	public Response getResponse() {
		return this.response;
	}
	
	

	
	/**
	 * 유효성 검증
	 * @return
	 */
	private KkoBulkSignPrefix validate() throws Exception{
		log.info(String.format("called %s.%s", getClass().getName(), new Object() {}.getClass().getEnclosingMethod().getName()));
		
		
		return this;
	}
	
	
	/**
	 * 선행 작업
	 * @return
	 */
	private KkoBulkSignPrefix preHandle() throws Exception{
		log.info(String.format("called %s.%s", getClass().getName(), new Object() {}.getClass().getEnclosingMethod().getName()));
		
		
		return this;
	};
	
	
	/**
	 * api 호출
	 * @return
	 */
	private String call() throws Exception{
		log.info(String.format("called %s.%s", getClass().getName(), new Object() {}.getClass().getEnclosingMethod().getName()));
		

		/* ========================
		 * prifix 조회
		 ======================== */
		ResponseEntity<String> resp = sender.callBulkPrefix();
		if("E500".equals(resp.getBody().toString()))
			throw new InternalException(InternalErrCd.ERR602, "카카오페이 서버 에러");
		final String prefix = resp.getBody();
		
		
		
		return prefix;
	}
	
	
	/**
	 * 후행 작업
	 * @return
	 */
	private KkoBulkSignPrefix postHandle() {
		log.info(String.format("called %s.%s", getClass().getName(), new Object() {}.getClass().getEnclosingMethod().getName()));
		
		return this;
		
		
	}
	
	
	


	
	
}
