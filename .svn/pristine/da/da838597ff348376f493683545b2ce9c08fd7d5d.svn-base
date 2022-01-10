package cokr.xit.modules.example.thread;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TaskRunnableReturnSample implements Runnable{

	List<TaskDtoSample> trgetList;
	List<TaskDtoSample> result;
	String taskNm;
	
	TaskRunnableReturnSample(List<TaskDtoSample> result, String taskNm){
		this.result = result;
		this.taskNm = taskNm;
	}
	@Override
	public void run() {
		int scnd = 1000;
//		int scnd = 500;
//		if(taskNm.endsWith("2"))
//			scnd = 100;
		for(TaskDtoSample row : result) {
			try {
//				if(row.getId()>99&&row.getId()<102)
//					throw new RuntimeException("Exception");
				row.setResult(String.format("%s => %05d", taskNm, row.getId()));
				log.info(String.format("%s => %05d", taskNm, row.getId()));
			} catch (Exception e) {
				log.error(String.format("task id is %s trget Id \"%s\". error 발생:::%s", taskNm, row.getId(), e.getMessage()));
			}
			
			try {
				Thread.sleep(scnd);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
	};
}
