package cokr.xit.modules.example.api;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cokr.xit.modules.common.code.InternalErrCd;
import cokr.xit.modules.common.exception.InternalException;
import cokr.xit.modules.post.domains.sign.dto.kko.KkoSignSendReqDTO;
import cokr.xit.modules.post.domains.sign.dto.kko.item.KkoSignSendReqData;
import cokr.xit.utils.CmmnUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ClientController {

	@Value("${kakao.access_token}")
	private String ACCESS_TOKEN;
	private String HOST = "http://localhost:18090" ;
	
	@PostMapping("/example/client/ok")
	private ResponseEntity<String> ok(@RequestBody KkoSignSendReqDTO reqDTO ) throws Exception {
		if(reqDTO.getData()==null)
			reqDTO = createParam();
		
		log.info(String.format("called %s.%s", getClass().getName(), new Object() {}.getClass().getEnclosingMethod().getName()));
		log.info(String.format("request parameters... %s", CmmnUtil.toJsonString(reqDTO)));
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		ResponseEntity<String> resp = this.sendData(HttpMethod.POST, "/example/server/ok", CmmnUtil.toJsonString(reqDTO), headers);

		String body = resp.getBody();
		log.info(String.format("body:::%s", body));
		return resp;
	}
	@PostMapping("/example/client/err400")
	private ResponseEntity<String> err400(@RequestBody KkoSignSendReqDTO reqDTO ) throws Exception {
		
		log.info(String.format("called %s.%s", getClass().getName(), new Object() {}.getClass().getEnclosingMethod().getName()));
		log.info(String.format("request parameters... \n%s", CmmnUtil.toJsonString(reqDTO)));
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		ResponseEntity<String> resp = this.sendData(HttpMethod.POST, "/example/server/err400", CmmnUtil.toJsonString(reqDTO), headers);

		return resp;
		
	}
	@PostMapping("/example/client/err500")
	private ResponseEntity<String> err500(@RequestBody KkoSignSendReqDTO reqDTO ) throws Exception {
		
		log.info(String.format("called %s.%s", getClass().getName(), new Object() {}.getClass().getEnclosingMethod().getName()));
		log.info(String.format("request parameters... %s", CmmnUtil.toJsonString(reqDTO)));
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		ResponseEntity<String> resp = this.sendData(HttpMethod.POST, "/example/server/err500", CmmnUtil.toJsonString(reqDTO), headers);
		
		return resp;
		
	}
	
	private KkoSignSendReqDTO createParam() {
		KkoSignSendReqDTO signSendDTO = new KkoSignSendReqDTO();
		KkoSignSendReqData data = new KkoSignSendReqData();
		signSendDTO.setData(data);
		return signSendDTO;
	}
	
	/**
	 * ????????? ??????
	 * @param method
	 * @param uri
	 * @param param
	 * @param headers
	 * @param accessToken  ???????????????(???????????? ?????? ??????)
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked" })
	protected ResponseEntity<String> sendData(HttpMethod method, String uri, String param, HttpHeaders headers) throws Exception{
        if((this.ACCESS_TOKEN == null || this.ACCESS_TOKEN.length() < 5) && !uri.endsWith("ping"))	//ping ?????? ???????????? ????????? ???????????? ?????? ?????????????????? ?????? ??????.. 
            throw new IOException("AccessToken is null!");
		
        //?????? ??????
        if(headers==null)
        	headers = new HttpHeaders();
        headers.set("Authorization", String.format("Bearer %s", this.ACCESS_TOKEN));	//access token ??????
		
        //????????? ??????
		Map<String, Object> resp = CmmnUtil.callApi(method, this.HOST+uri, param, headers);	//????????? ??????
		

		//?????? ????????????(OK ?????? etc...) ??????
		if(!"OK".equals(resp.get("resCd"))) //????????? ????????????..
			throw new InternalException(InternalErrCd.valueOf((String) resp.get("resCd")), String.format("API ?????? ??????. %s", resp.get("resMsg")));
		
		return (ResponseEntity<String>) resp.get("responseEntity");
	}
}
