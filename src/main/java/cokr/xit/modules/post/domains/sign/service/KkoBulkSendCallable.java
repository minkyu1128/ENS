//package cokr.xit.modules.post.domains.sign.service;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.Callable;
//
//import org.springframework.http.ResponseEntity;
//
//import com.fasterxml.jackson.annotation.JsonInclude.Include;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import cokr.xit.modules.post.code.InternalErrCd;
//import cokr.xit.modules.post.domains.sign.dto.kko.KkoBulkSignSendRespDTO;
//import cokr.xit.modules.post.domains.sign.dto.kko.item.KkoSignSendReqData;
//import cokr.xit.modules.post.domains.sign.sender.KKoSignTalkSender;
//import cokr.xit.modules.utils.BeanUtils;
//import cokr.xit.modules.utils.CmmnUtil;
//
//public class KkoBulkSendCallable implements Callable<String>{
//
//	private List<KkoSignSendReqData> sendList;
//	private KKoSignTalkSender sender;
//	private Long sendMastId;
//	private KkoSignTalkService service;
//	private ObjectMapper mapper;
//	
//	public KkoBulkSendCallable(List<KkoSignSendReqData> sendList, KKoSignTalkSender sender, Long sendMastId){
//		this.sendList   = sendList;
//		this.sender     = sender; 
//		this.sendMastId = sendMastId;
//		this.service    = BeanUtils.getBean(KkoSignTalkService.class);
//		this.mapper     = new ObjectMapper();
//	}
//	
//	@Override
//	public String call() throws Exception {
//		/* ========================
//		 * API 호출
//	 	======================== */
//		Map<String, Object> param = new HashMap<String, Object>();
//		param.put("data", sendList);
//		ResponseEntity<String> responseEntity = sender.callBulkSend(CmmnUtil.toJsonString(param, Include.NON_NULL));
//		if(responseEntity.getStatusCodeValue() != 200) {	//네트워크 통신에 실패했을 경우..
//			//발송결과 반영 - 에러
//			service.modifySendInfoResultByError(sendMastId, sendList
//					, InternalErrCd.ERR600.name()
//					, String.format("[%s] %s", responseEntity.getStatusCode().toString(), responseEntity.getStatusCodeValue())
//					);
//			return responseEntity.getBody();
//		}
//		
//		/* ========================
//		 * 응답결과 처리
//	 	======================== */
//		try {
//			KkoBulkSignSendRespDTO respDTO = mapper.readValue(responseEntity.getBody(), KkoBulkSignSendRespDTO.class);
//			//발송결과 반영
//			service.modifySendInfoResultByResp(sendMastId, respDTO.getData());	
//			
//		} catch (JsonProcessingException e) {
//			//발송결과 반영 - 에러
//			service.modifySendInfoResultByError(sendMastId, sendList
//					, InternalErrCd.ERR505.name()
//					, String.format("[%s] %s. %s", InternalErrCd.ERR505, InternalErrCd.ERR505.getCodeNm(), e.getMessage())
//					);
//
//			return responseEntity.getBody();
//		}
//		
//		
//
//		return responseEntity.getBody();
//	}
//	
//	
//
//
//}
