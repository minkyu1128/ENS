package cokr.xit.message.sample.service;

import java.io.IOException;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KafkaSampleConsumerService {


	@KafkaListener(topics = "testTopic", groupId = "group-id-ens")
	public void consumer1(String message) throws IOException {
		log.info("[consumer1] receive message : " + message);
	}

	@KafkaListener(topics = "testTopic2", groupId = "group-id-ens")
	public void consumer2(String message) throws IOException {
		log.info("[consumer2] receive message : " + message);
	}

}
