package cokr.xit.modules.example.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cokr.xit.modules.post.domains.sign.dto.kko.KkoSignSendReqDTO;
import cokr.xit.modules.post.domains.sign.dto.kko.KkoSignSendRespDTO;
import cokr.xit.modules.post.domains.sign.dto.kko.item.KkoSignSendRespData;
import cokr.xit.utils.CmmnUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ServerController {


	@PostMapping("/example/server/ok")
	private ResponseEntity<Map<String, Object>> ok(@RequestBody KkoSignSendReqDTO reqDTO ) {
		
		log.info(String.format("called %s.%s", getClass().getName(), new Object() {}.getClass().getEnclosingMethod().getName()));
		log.info(String.format("request parameters... %s", CmmnUtil.toJsonString(reqDTO)));
		
		ResponseEntity<Map<String, Object>> resp = new ResponseEntity<Map<String,Object>>(this.createBody(), HttpStatus.OK);

		return resp;
	}
	@PostMapping("/example/server/err400")
	private ResponseEntity<Map<String, Object>> err400(@RequestBody KkoSignSendReqDTO reqDTO ) {
		
		log.info(String.format("called %s.%s", getClass().getName(), new Object() {}.getClass().getEnclosingMethod().getName()));
		log.info(String.format("request parameters... \n%s", CmmnUtil.toJsonString(reqDTO)));
		
		ResponseEntity<Map<String, Object>> resp = new ResponseEntity<Map<String,Object>>(this.createBody(), HttpStatus.BAD_REQUEST);

		return resp;
		
	}
	@PostMapping("/example/server/err500")
	private ResponseEntity<Map<String, Object>> err500(@RequestBody KkoSignSendReqDTO reqDTO ) {
		
		log.info(String.format("called %s.%s", getClass().getName(), new Object() {}.getClass().getEnclosingMethod().getName()));
		log.info(String.format("request parameters... %s", CmmnUtil.toJsonString(reqDTO)));
		
		ResponseEntity<Map<String, Object>> resp = new ResponseEntity<Map<String,Object>>(this.createBody(), HttpStatus.INTERNAL_SERVER_ERROR);
		
		return resp;
		
	}
	
	private Map<String, Object> createBody() {
		KkoSignSendRespDTO signSendDTO = new KkoSignSendRespDTO();
		KkoSignSendRespData data = new KkoSignSendRespData();
		signSendDTO.setData(data);
		
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("data", signSendDTO);
		return resultMap;
	}
}
