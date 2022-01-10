package cokr.xit.modules.example.thread;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TaskRunnableSample implements Runnable{

	List<String> result;
	String taskNm;
	
	TaskRunnableSample(List<String> result, String taskNm){
		this.result = result;
		this.taskNm = taskNm;
	}
	@Override
	public void run() {
		int scnd = 500;
		if(taskNm.endsWith("2"))
			scnd = 100;
		for(int i=0; i<50; i++) {
			result.add(String.format("%s => %05d", taskNm, i));
			log.info(String.format("%s => %05d", taskNm, i));
			try {
				Thread.sleep(scnd);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	};
	
}
