package cokr.xit.modules.example.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TaskCallableExample implements Callable<List<TaskDtoSample>> {

	private List<TaskDtoSample> trgetList;
	
	TaskCallableExample(List<TaskDtoSample> trgetList){
		this.trgetList = trgetList;
	}
	
	@Override
	public List<TaskDtoSample> call() throws Exception {
		List<TaskDtoSample> rsList = new ArrayList<TaskDtoSample>();
		for(TaskDtoSample row : trgetList) {
//			if(row.getId()>99&&row.getId()<102)
//				throw new RuntimeException("Exception");
			row.setResult(String.format("getId => %05d. current thread name is\"%s\". ", row.getId(), Thread.currentThread().getName()));
			rsList.add(row);
			log.info(String.format("getId => %05d. current thread name is \"%s\". ", row.getId(), Thread.currentThread().getName()));
			Thread.sleep(500);
		}
		
		log.info(String.format("%s ID is \"%s\"", Thread.currentThread().getName(), Thread.currentThread().getId()));
		
		return rsList;
	}

}
