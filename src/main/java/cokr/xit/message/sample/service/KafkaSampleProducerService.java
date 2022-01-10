package cokr.xit.message.sample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KafkaSampleProducerService {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	private final String TOPIC_NAME = "testTopic";

	public void sendMessage(String message) {
		log.info("send message : " + message);
		this.kafkaTemplate.send(TOPIC_NAME, message);
	}
	public void sendMessage(String topicName, String message) {
		log.info(String.format("[%s]send message : %s", topicName, message));
		this.kafkaTemplate.send(topicName, message);
	}

}
