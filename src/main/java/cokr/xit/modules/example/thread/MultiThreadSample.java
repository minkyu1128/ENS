package cokr.xit.modules.example.thread;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MultiThreadSample {

	/**
	 * <pre>메소드 설명: Multi Thread 예제</pre>
	 * @throws InterruptedException
	 * @throws ExecutionException void 요청처리 후 응답객체
	 * @author: 박민규
	 * @date: 2021. 8. 30.
	 */
	public static void example() throws InterruptedException, ExecutionException {
		
		long startTme = System.currentTimeMillis();
		
		Calculator calculrator = new Calculator(0);
		HashSet<Integer> set1 = new HashSet<Integer>();
		HashSet<Integer> set2 = new HashSet<Integer>();
		
		Callable<Void> callable1 = () -> {
			for(int i=0; i<50; i++) {
				set1.add(calculrator.getNextIndex());
				log.info(String.format("callable1 => %05d", i));
//				Thread.sleep(500);
			}
			return null;
		};
		Callable<Void> callable2 = () -> {
			for(int i=0; i<100; i++) {
				set2.add(calculrator.getNextIndex());
				log.info(String.format("callable2 => %05d", i));
				Thread.sleep(300);
			}
			return null;
		};
		
		
		ExecutorService executorService = Executors.newFixedThreadPool(3);
		Future<Void> future1 = executorService.submit(callable1);     //callable1 작업 실행
		Future<Void> future2 = executorService.submit(callable2);     //callable2 작업 실행
		future1.get();   //callable1 스레드가 완료될때까지 blocking
		future2.get();   //callable2 스레드가 완료될때까지 blocking
		
		
		if(future1.isDone() && future2.isDone()) {
			System.out.println(set1.size());
			System.out.println(set2.size());
		}
		
		executorService.shutdown();	//executor 종료
		log.info(String.format("Executor Shutdown => %s", executorService.isShutdown()));
		
		long endTme = System.currentTimeMillis();
		log.info(String.format("수행시간::: %s seconds...", (endTme-startTme)/1000) );
	} 
	
	
	
	
	
	


	/**
	 * <pre>메소드 설명: Multi Thread 결과 리턴 예제
	 * 	-.runable로 작성한 Task를 호출하여 공유객체에 결과를 return 받는다.
	 * </pre>
	 * @throws InterruptedException
	 * @throws ExecutionException void 요청처리 후 응답객체
	 * @author: 박민규
	 * @date: 2021. 8. 30.
	 */
	@SuppressWarnings("unchecked")
	public static void runnableExample() throws InterruptedException, ExecutionException {
		
		long startTme = System.currentTimeMillis();

		//테스트 데이터 생성
		List<TaskDtoSample> result = new ArrayList<TaskDtoSample>();
		for(int i=0; i<102; i++) {
			TaskDtoSample dto = new TaskDtoSample();
			dto.setId(i);
			result.add(dto);
		}
		
		//thread 갯수 설정
		int unitSize = Runtime.getRuntime().availableProcessors();
		int cntDiv = result.size()/unitSize;
		int cntMod = result.size()%unitSize;
		int cntThread = cntDiv+(cntMod>0?1:0);
		ExecutorService executorService = Executors.newFixedThreadPool(cntThread);	//실행자 스레드 갯수 설정
		
		//Task 단위별 실행
		List<Future> listFuture = new ArrayList<>();	//future 목록
		for(int i=0; i<cntThread; i++) {
			List<TaskDtoSample> taskList = null;
			if(i==cntThread-1) //마지막 thread 일때...
				taskList = result.subList(i*unitSize, cntMod>0?result.size():(i+1)*unitSize);
			else
				taskList = result.subList(i*unitSize, (i+1)*unitSize);
			
			Runnable task = new TaskRunnableReturnSample(taskList, "task"+i);	
			listFuture.add(executorService.submit(task, result));	//task 작업 실행
		}
		
		//task block 처리
		Iterator<Future> it = listFuture.iterator();
		while(it.hasNext()) {
			Future<List<TaskDtoSample>> future = it.next();
			List<TaskDtoSample> list = future.get();   //task 스레드가 완료될때까지 blocking
			
			System.out.println(String.format("결과건수=%s", list.size()));
			System.out.println(String.format("결과=%s", list.toString()));
			
//			long endTme = System.currentTimeMillis();
//			log.info(String.format("수행시간::: %s seconds...", (endTme-startTme)/1000) );
		}
		
		 
//		for(TaskReturnDtoSample row : result)
//			log.info(String.format("수행결과::: %s ", row.getResult()));
			
		

//		boolean isShutdown = executorService.isShutdown();                       //Executor가 셧다운 되었는 지의 여부를 확인한다.
//		List<Runnable> listRunnable = executorService.shutdownNow() ;            //현재 실행중인 모든 작업을 중지한다. 대기중인 작업을 멈추고, 현재 실행되기 위해 대기중인 작업 목록을 리턴 한다.
//		executorService.shutdown();                                              //셧다운 한다. 이미 Executor에 제공된 작업은 실행되지만 새로운 작업은 수용하지 않는다.
//		isShutdown = executorService.isShutdown();                               //Executor가 셧다운 되었는 지의 여부를 확인한다.
//		boolean isTerminated = executorService.isTerminated();                   //셧다운 실행 후 모든 작업이 종료되었는 지의 여부를 확인한다.
//		boolean isIdle = executorService.awaitTermination(30, TimeUnit.SECONDS); //셧다운 실행. 지정시간동안 모든 작업이 종료될때까지 대기. 지정시간내 모든 작업이 종료된 true, 아니면 false 반환.
		
		executorService.shutdown();	//executor 종료
		log.info(String.format("Executor Shutdown => %s", executorService.isShutdown()));
		
		long endTme = System.currentTimeMillis();
		log.info(String.format("수행시간::: %s seconds...", (endTme-startTme)/1000) );
	} 
	
	

	/**
	 * <pre>메소드 설명: Multi Thread 예제
	 * 	-.callable로 작성한 Task를 호출하여 Task 처리단위별 결과를 return 받는다.
	 * </pre>
	 * @throws InterruptedException
	 * @throws ExecutionException void 요청처리 후 응답객체
	 * @author: 박민규
	 * @date: 2021. 8. 30.
	 */
	public static void callableExample() throws InterruptedException, ExecutionException {
		
		long startTme = System.currentTimeMillis();

		//테스트 데이터 생성
		List<TaskDtoSample> result = new ArrayList<TaskDtoSample>();
		for(int i=0; i<102; i++) {
			TaskDtoSample dto = new TaskDtoSample();
			dto.setId(i);
			result.add(dto);
		}
		
		//thread 갯수 설정
//		int unitSize = Runtime.getRuntime().availableProcessors();
		int unitSize = 10;
		int cntDiv = result.size()/unitSize;
		int cntMod = result.size()%unitSize;
		int cntThread = cntDiv+(cntMod>0?1:0);
		ExecutorService executorService = Executors.newFixedThreadPool(cntThread);	//실행자 스레드 갯수 설정
		List<Future> listFuture = new ArrayList<>();	//future 목록
		
		//Task 단위별 실행
		for(int i=0; i<cntThread; i++) {
			List<TaskDtoSample> taskList = null;
			if(i==cntThread-1) //마지막 thread 일때...
				taskList = result.subList(i*unitSize, cntMod>0?result.size():(i+1)*unitSize);
			else
				taskList = result.subList(i*unitSize, (i+1)*unitSize);
			
			Callable<List<TaskDtoSample>> task = new TaskCallableExample(taskList);
			listFuture.add(executorService.submit(task));	//task 작업 실행
		}
		
		//task block 처리
		Iterator<Future> it = listFuture.iterator();
		while(it.hasNext()) {
			Future<List<TaskDtoSample>> future = it.next();
			List<TaskDtoSample> list = future.get();   //task 스레드가 완료될때까지 blocking
			System.out.println(String.format("결과건수=%s", list.size()));
			System.out.println(String.format("결과=%s", list.toString()));
		}
		
		executorService.shutdown();	//executor 종료
		log.info(String.format("Executor Shutdown => %s", executorService.isShutdown()));
		
		long endTme = System.currentTimeMillis();
		log.info(String.format("수행시간::: %s seconds...", (endTme-startTme)/1000) );
	} 
	
	/**
	 * <pre>메소드 설명: Multi Thread 예제
	 * 	-.CompletionService로 Task를 호출 한다.
	 * </pre>
	 * @throws InterruptedException
	 * @throws ExecutionException void 요청처리 후 응답객체
	 * @author: 박민규
	 * @date: 2021. 8. 30.
	 */
	public static void completionServiceExample() throws InterruptedException, ExecutionException {
		
		long startTme = System.currentTimeMillis();
		
		//테스트 데이터 생성
		List<TaskDtoSample> result = new ArrayList<TaskDtoSample>();
		for(int i=0; i<102; i++) {
			TaskDtoSample dto = new TaskDtoSample();
			dto.setId(i);
			result.add(dto);
		}
		
		//thread 갯수 설정
//		int unitSize = Runtime.getRuntime().availableProcessors();
		int unitSize = 10;
		int cntDiv = result.size()/unitSize;
		int cntMod = result.size()%unitSize;
		int cntThread = cntDiv+(cntMod>0?1:0);
		ExecutorService executorService = Executors.newFixedThreadPool(cntThread);	//스레드 갯수 설정
		CompletionService completionService = new ExecutorCompletionService(executorService);
		List<Future> listFuture = new ArrayList<>();	//future 목록
		
		//Task 단위별 실행
		for(int i=0; i<cntThread; i++) {
			List<TaskDtoSample> taskList = null;
			if(i==cntThread-1) //마지막 thread 일때...
				taskList = result.subList(i*unitSize, cntMod>0?result.size():(i+1)*unitSize);
			else
				taskList = result.subList(i*unitSize, (i+1)*unitSize);
			
			Callable<List<TaskDtoSample>> task = new TaskCallableExample(taskList);
			listFuture.add(completionService.submit(task));	//task 작업 실행
		}
		 
		//task block 처리-Case1. 순차 처리
//		Iterator<Future> it = listFuture.iterator();
//		while(it.hasNext()) {
//			Future<List<TaskReturnDtoSample>> future = it.next();
//			List<TaskReturnDtoSample> list = future.get();   //task 스레드가 완료될때까지 blocking
//			System.out.println(String.format("결과건수=%s", list.size()));
//			System.out.println(String.format("결과=%s", list.toString()));
//		}
		//task block 처리-Case2. 비순차 처리
		int complete = 0;
		while (true) {
			if(complete == cntThread)	//모든 Thread가 완료되면... 
				break; 
//			Future future = completionService.poll();	                    //완료된 작업의 future를 가져 옴. 없다면 즉시 null을 리턴
//			Future future = completionService.poll(30, TimeUnit.SECONDS);	//완료된 작업의 future를 가져 옴. 없다면 timeout까지 blocking 후 리턴
			Future future = completionService.take();	                    //완료된 작업의 future를 가져 옴. 없다면 있을때까지 blocking 후 리턴
			if(future.isDone())	//완료 되었으면.. (사실 이 조건절은 take 사용 시 완료이전까지 blocking 하기 때문에 무의미함..)
				complete++; 	
			List<TaskDtoSample> list = (List<TaskDtoSample>) future.get();   //task 스레드가 완료될때까지 blocking
			System.out.println(String.format("결과건수=%s", list.size()));
			System.out.println(String.format("결과=%s", list.toString()));
		}
		
		executorService.shutdown();	//executor 종료
		log.info(String.format("Executor Shutdown => %s", executorService.isShutdown()));
		
		
		long endTme = System.currentTimeMillis();
		log.info(String.format("수행시간::: %s seconds...", (endTme-startTme)/1000) );
	} 
}

