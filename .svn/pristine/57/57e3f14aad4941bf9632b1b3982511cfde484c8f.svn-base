package cokr.xit.modules.usermng.service.cmpnt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class UserElctrnNticProducer {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;


	public void pubBulkSend(String message) {
		final String topicName = "kkomydoc-send-bulk";

		log.info(String.format("[%s] pub message : %s", topicName, message));

		this.kafkaTemplate.send(topicName, message);
	}

	public void pubBulkStatus(String message) {
		final String topicName = "kkomydoc-status-bulk";

		log.info(String.format("[%s] pub message : %s", topicName, message));

		this.kafkaTemplate.send(topicName, message);
	}
}
