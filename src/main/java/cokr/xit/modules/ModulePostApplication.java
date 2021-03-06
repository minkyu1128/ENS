package cokr.xit.modules;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//@Import({QuerydslConfig.class, CodeMapperConfig.class})
@ComponentScan(basePackages = "cokr.xit")
//@EnableAutoConfiguration
@EnableScheduling	//@Scheduled 활성화
public class ModulePostApplication{

	public static void main(String[] args) {
//		SpringApplication.run(ModulePostApplication.class, args);
		SpringApplication application = new SpringApplication(ModulePostApplication.class);
		application.addListeners(new ApplicationPidFileWriter());	//PID(Process ID 작성)
		application.run(args);

		/* multi-thread test */
//		try {
//			MultiThreadSample.example();
//			MultiThreadSample.runnableExample();	//전체 Task 결과를 return 받는다.
//			MultiThreadSample.callableExample();	//Task 단위별 결과를 return 받는다
//			MultiThreadSample.completionServiceExample();	//
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		} catch (ExecutionException e) {
//			e.printStackTrace();
//		}

		/* multi-thread return result test */
//		ResultByRunnableExample.main(args);
	}


}
