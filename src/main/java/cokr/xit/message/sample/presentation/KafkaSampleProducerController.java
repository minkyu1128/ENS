package cokr.xit.message.sample.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cokr.xit.message.sample.service.KafkaSampleProducerService;

@RestController
public class KafkaSampleProducerController {

	@Autowired
	private KafkaSampleProducerService kafkaSampleProducerService;

	@PostMapping(value = "/kafka/sample/push")
	public void push(@RequestBody String message) {
		kafkaSampleProducerService.sendMessage(message);
	}
	@PostMapping(value = "/kafka/sample/push/topic/{topicName}")
	public void push(@PathVariable String topicName, @RequestBody String message) {
		kafkaSampleProducerService.sendMessage(topicName, message);
	}
}
