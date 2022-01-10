//package cokr.xit.modules.post.domains.sign.service.biz;
//
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.Callable;
//import java.util.concurrent.CompletionService;
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.ExecutorCompletionService;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.Future;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.util.StringUtils;
//
//import cokr.xit.modules.domain.SendMast;
//import cokr.xit.modules.post.code.InternalErrCd;
//import cokr.xit.modules.post.domains.sign.dto.kko.KkoBulkSignSendReqDTO;
//import cokr.xit.modules.post.domains.sign.dto.kko.item.KkoSignSendReqData;
//import cokr.xit.modules.post.domains.sign.sender.KKoSignTalkSender;
//import cokr.xit.modules.post.domains.sign.service.ApiStrategy;
//import cokr.xit.modules.post.domains.sign.service.KkoBulkSendCallable;
//import cokr.xit.modules.post.domains.sign.service.KkoSignTalkService;
//import cokr.xit.modules.post.domains.sign.service.Response;
//import cokr.xit.modules.post.exception.InternalException;
//import cokr.xit.modules.utils.BeanUtils;
//import cokr.xit.modules.utils.CmmnUtil;
//import cokr.xit.modules.utils.RequireValidator;
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@SuppressWarnings("rawtypes")
//public class KkoBulkSignTalkSend implements ApiStrategy{
//
//	private KkoSignTalkService service;
//	private Response response;
//	
//	//init 필드
//	private KKoSignTalkSender sender;
//	private KkoBulkSignSendReqDTO reqDTO;
//
//	//etc...
//	private SendMast sendMast;
//	
//	
//	public KkoBulkSignTalkSend(KKoSignTalkSender sender, KkoBulkSignSendReqDTO reqDTO){
//		this.sender   = sender;
//		this.reqDTO = reqDTO;
//		this.service = BeanUtils.getBean(KkoSignTalkService.class);
//		this.response = new Response();
////		this.sendMast = SendMast.builder().build();
//	}
//	
//
//	@Override
//	public void execute() {
//		try {
//			//전송
//			List<String> respPlainText = this.validate()
//					.preHandle()
//					.call();
//			response.setOkResult(respPlainText);
//		} catch (InternalException e) {
//			response.setFailResult(e.getErrCd(), e.getMessage());
//		} catch (Exception e) {
//			response.setFailResult(InternalErrCd.ERR001, e.getMessage());
//		} finally {
////			//후처리
//			this.postHandle();
//		}
//		
//	}
//
//	@Override
//	public Response getResponse() {
//		return this.response;
//	}
//	
//	
//
//	
//	/**
//	 * 유효성 검증
//	 * @return
//	 */
//	@SuppressWarnings("deprecation")
//	private KkoBulkSignTalkSend validate() throws Exception{
//		log.info(String.format("called %s.%s", getClass().getName(), new Object() {}.getClass().getEnclosingMethod().getName()));
//		
//		/* ========================
//		 * 유효성 검증
//		 ======================== */
//		for(KkoSignSendReqData row : reqDTO.getData()) {
//			try {
//				RequireValidator.builder()
//				.obj(row)
//				.build()
//				.validate()
//				.throwableException();
//				if(StringUtils.isEmpty(row.getTx_id()))
//					throw new InternalException(InternalErrCd.ERR401, "tx_id(은)는 필수조건 입니다.");
//			} catch (InternalException e) {
//				throw e;
//			} catch (Exception e) {
//				throw new InternalException(InternalErrCd.ERR402, String.format("%s ::: parmeter is %s", e.getMessage(), CmmnUtil.toJsonString(row)));
//			}
//		}
//		
//		return this;
//	}
//	
//	
//	/**
//	 * 선행 작업
//	 * @return
//	 */
//	private KkoBulkSignTalkSend preHandle() throws Exception{
//		log.info(String.format("called %s.%s", getClass().getName(), new Object() {}.getClass().getEnclosingMethod().getName()));
//
//		/* ========================
//		 * prefix 조회
//		 ======================== */
//		ResponseEntity<String> resp = sender.callBulkPrefix();
//		if("E500".equals(resp.getBody().toString()))
//			throw new InternalException(InternalErrCd.ERR602, String.format("카카오페이 서버 에러. %s", resp.getStatusCodeValue()));
//		final String prefix = resp.getBody();
//		
//		
//		
//		/* ========================
//		 * 발송정보 저장
//		 ======================== */
//		this.sendMast = service.addSendInfoByReq("KAKAO", "signtalk", "", reqDTO.getData(), prefix);
////		this.sendMast = service.addSendInfoByReq("KAKAO", "signtalk", "", reqDTO.getData());
//		
//		return this;
//	};
//	
//	
//	/**
//	 * api 호출
//	 * @return
//	 */
//	@SuppressWarnings("unchecked")
//	private List<String> call() throws Exception{
//		log.info(String.format("called %s.%s", getClass().getName(), new Object() {}.getClass().getEnclosingMethod().getName()));
//		
//		
//		/* ========================
//		 * API 호출
//		 ======================== */
//		//Tread 갯수 설정
//		int unitSize = 100;
//		int cntDiv = reqDTO.getData().size()/unitSize;
//		int cntMod = reqDTO.getData().size()%unitSize;
//		int cntThread = cntDiv+(cntMod>0?1:0);
//		ExecutorService executorService = Executors.newFixedThreadPool(cntThread);	//스레드 갯수 설정
//		CompletionService completionService = new ExecutorCompletionService(executorService);
//		List<Future> listFuture = new ArrayList<>();	//future 목록
//		//Task 단위별 실행
//		for(int i=0; i<cntThread; i++) {
//			List<KkoSignSendReqData> sendList = null;
//			if(i==cntThread-1) //마지막 thread 일때...
//				sendList = reqDTO.getData().subList(i*unitSize, cntMod>0?reqDTO.getData().size():(i+1)*unitSize);
//			else
//				sendList = reqDTO.getData().subList(i*unitSize, (i+1)*unitSize);
//			
//
//			Callable<String> task = new KkoBulkSendCallable(sendList, sender, sendMast.getSendMastId());
//			listFuture.add(completionService.submit(task));	//task 작업 실행
////			/* ========================
////			 * 발송결과 저장
////			 ======================== */
////			Future<Map<String, Object>> future = completionService.submit(task);	//task 작업 실행
////			executorService.submit(new Runnable() {
////				@Override
////				public void run() {
////					Map<String, Object> result = future.get();
////					List<KkoSignBulkSendReqData> taskList = (List<KkoSignBulkSendReqData>) result.get("data");
////					ResponseEntity<String> resp = (ResponseEntity<String>) result.get("resp");
////					sendHist.postHandle(taskList, resp, mSignTalkId);
////				}
////			});
//		}
//		
//
//		/* ========================
//		 * future 처리
//		 ======================== */
//		int complete = 0;
//		List<String> arrRespPlainText = new ArrayList<>();
//		while (true) {
//			if(complete == cntThread)	//모든 Thread가 완료되면... 
//				break;
//			try {
//				//완료된 순서대로...
//				Future<String> future = completionService.take();	//완료된 작업의 future를 가져 옴. 없다면 있을때까지 blocking 후 리턴
////				if(future.isDone())	//완료 되었으면.. (사실 이 조건절은 take 사용 시 완료이전까지 blocking 하기 때문에 무의미함..)
//					complete++; 	
//				
//				arrRespPlainText.add(future.get());
//			} catch (InterruptedException e) {
//			} catch (ExecutionException e) {
//			} catch (InternalException e) {
//			}
//		}
//		executorService.shutdown();
//		
//		
//		
//		return arrRespPlainText;
//	}
//	
//	
//	/**
//	 * 후행 작업
//	 * @return
//	 */
//	private KkoBulkSignTalkSend postHandle() {
//		log.info(String.format("called %s.%s", getClass().getName(), new Object() {}.getClass().getEnclosingMethod().getName()));
//		
//		//발송마스터 발송결과 변경(모든 내역이 성공했을 경우 ok, 아닌 경우 fail 로 설정)
//		service.modifySendMastByResult(sendMast.getSendMastId(), response.getErrCode(), response.getErrMsg());
//		return this;
//		
//		
//	}
//	
//	
//	
//
//
//	
//	
//}
