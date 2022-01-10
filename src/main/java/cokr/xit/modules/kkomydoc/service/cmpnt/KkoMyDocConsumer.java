package cokr.xit.modules.kkomydoc.service.cmpnt;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import cokr.xit.modules.kkomydoc.domain.SendDetailKkoMydocRepository;
import cokr.xit.modules.kkomydoc.domain.SendResultKkoMydocRepository;
import cokr.xit.modules.kkomydoc.service.KkoMyDocService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class KkoMyDocConsumer {

	@Autowired
	private KkoMyDocService kkoMyDocService;
	@Autowired
	private SendDetailKkoMydocRepository sendDetailKkoMydocRepository;
	@Autowired
	private SendResultKkoMydocRepository sendResultKkoMydocRepository;

	@SuppressWarnings("unchecked")
	@KafkaListener(topics = "kkomydoc-send-bulk", groupId = "group-id-ens")
	public void subBulkSend(String message) throws IOException {
		log.info("[kkomydoc-send-bulk] sub message : " + message);

		Map<String, Object> m = null;
		try {
//			m = (Map<String, Object>) CmmnUtil.jsonStringtoObj(Map.class, message);
			ObjectMapper mapper  = new ObjectMapper();
			m = mapper.readValue(message, Map.class);
		} catch (Exception e) {
			e.printStackTrace();
		}


		try {
			//벌크전송 호출
			kkoMyDocService.bulkSend("", String.valueOf(m.get("accessToken")), String.valueOf(m.get("contractUuid")), String.valueOf(m.get("jsonStr")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@SuppressWarnings("unchecked")
	@KafkaListener(topics = "kkomydoc-status-bulk", groupId = "group-id-ens")
	public void subBulkStatus(String message) throws IOException {
		log.info("[kkomydoc-status-bulk] sub message : " + message);

		Map<String, Object> m = null;
		try {
//			m = (Map<String, Object>) CmmnUtil.jsonStringtoObj(Map.class, message);
			ObjectMapper mapper  = new ObjectMapper();
			m = mapper.readValue(message, Map.class);
		} catch (Exception e) {
			e.printStackTrace();
		}



		try {
			//documentBinderUuid 조회
			List<String> externalDocumentUuidList = (List<String>) m.get("externalDocumentUuidList");
			List<Long> sendMastIdList = sendDetailKkoMydocRepository.findSendMastIdByExternalDocumentUuid(externalDocumentUuidList);

			//상태갱신 호출
			for(Long sendMastId : sendMastIdList)
				kkoMyDocService.modifySendMastCurStatBySendMastId(String.valueOf(m.get("accessToken")), String.valueOf(m.get("contractUuid")), sendMastId);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
