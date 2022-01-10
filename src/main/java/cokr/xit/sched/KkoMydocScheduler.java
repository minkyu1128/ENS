package cokr.xit.sched;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cokr.xit.modules.usermng.service.UserElctrnNticService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class KkoMydocScheduler {

	@Resource
	private UserElctrnNticService service;

	@Scheduled(cron = "0 */1 * * * *")
	public void runSend() {
		log.info("[Schduler]KkoMydoc \"send\" run...");
		service.send();
	}
	@Scheduled(cron = "0 */3 * * * *")
	public void runResult() {
		log.info("[Schduler]KkoMydoc \"result\" run...");
		service.result();
	}
	@Scheduled(cron = "0 0 */1 * * *")
	public void runStatus() {
		log.info("[Schduler]KkoMydoc \"status\" run...");
		service.status();
	}
	@Scheduled(cron = "0 */30 * * * *")
	public void runFetchStatus() {
		log.info("[Schduler]KkoMydoc \"fetchStatus\" run...");
		service.fetchStatus();
	}
}
