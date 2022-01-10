package cokr.xit.modules.kkomydoc.presentation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cokr.xit.modules.common.code.InternalErrCd;
import cokr.xit.modules.common.exception.InternalException;
import cokr.xit.modules.common.model.RestResponseVO;
import cokr.xit.modules.domain.common.Error;
import cokr.xit.modules.kkomydoc.domain.SendDetailKkoMydoc;
import cokr.xit.modules.kkomydoc.domain.SendDetailKkoMydocRepository;
import cokr.xit.modules.kkomydoc.model.BulkSendRespDTO;
import cokr.xit.modules.kkomydoc.model.BulkStatusRespDTO;
import cokr.xit.modules.kkomydoc.model.OttRespDTO;
import cokr.xit.modules.kkomydoc.model.SendRespDTO;
import cokr.xit.modules.kkomydoc.model.StatusRespDTO;
import cokr.xit.modules.kkomydoc.service.KkoMyDocService;

@RestController
public class KkoMyDocController {

	@Resource
	private KkoMyDocService kkoMyDocService;
	@Resource
	private SendDetailKkoMydocRepository sendDetailKkoMydocRepository;


	@SuppressWarnings({ "unchecked" })
	@PostMapping("/kko/mydoc/send")
	public ResponseEntity<RestResponseVO> send(@RequestBody String jsonStr) throws JsonMappingException, JsonProcessingException {
		System.out.println("send() called...");


		//중개사업자 계약정보 설정
		String accessToken = "b40d3c623d1011ecbceae6cf4630da62";
		String contractUuid = "CON-b4126c833d1011ecbceae6cf4630da62";


		RestResponseVO restResponseVO = null;
		try {

			SendRespDTO sendRespDTO = kkoMyDocService.send("unknown중개사업자", accessToken, contractUuid, jsonStr);

			//Set 응답메시지
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> resultInfo = mapper.convertValue(sendRespDTO, Map.class);
			restResponseVO = RestResponseVO.okBuilder()
					.resultInfo(resultInfo)
					.build();

		} catch (InternalException e) {
			//Set 응답메시지
			restResponseVO = RestResponseVO.errBuilder()
					.errCode(e.getErrCd())
					.errMsg(e.getMessage())
					.build();

		} catch (Exception e) {
			//Set 응답메시지
			restResponseVO = RestResponseVO.errBuilder()
					.errCode(InternalErrCd.ERR999)
					.errMsg(e.getMessage())
					.build();
		}

		return new ResponseEntity<RestResponseVO>(restResponseVO, HttpStatus.OK);
	}




	@SuppressWarnings("unchecked")
	@GetMapping("/kko/mydoc/token")
	public ResponseEntity<RestResponseVO> token(@RequestBody Map<String, Object> mParam) {
		System.out.println("token() called...");

		//중개사업자 계약정보 설정
		String accessToken = "b40d3c623d1011ecbceae6cf4630da62";
		String document_binder_uuid   = mParam.containsKey("document_binder_uuid")?(String)mParam.get("document_binder_uuid"):null;
		String external_document_uuid = mParam.containsKey("external_document_uuid")?(String)mParam.get("external_document_uuid"):null;
		String token                  = mParam.containsKey("token")?(String)mParam.get("token"):null;

		RestResponseVO restResponseVO = null;
		try {
			OttRespDTO ottRespDTO = kkoMyDocService.token(accessToken, document_binder_uuid, token);

			//Set 응답메시지
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> resultInfo = mapper.convertValue(ottRespDTO, Map.class);
			restResponseVO = RestResponseVO.okBuilder()
					.resultInfo(resultInfo)
					.build();

		} catch (InternalException e) {
			//Set 응답메시지
			restResponseVO = RestResponseVO.errBuilder()
					.errCode(e.getErrCd())
					.errMsg(e.getMessage())
					.build();

		} catch (Exception e) {
			//Set 응답메시지
			restResponseVO = RestResponseVO.errBuilder()
					.errCode(InternalErrCd.ERR999)
					.errMsg(e.getMessage())
					.build();
		}
		return new ResponseEntity<RestResponseVO>(restResponseVO, HttpStatus.OK);
	}





	@SuppressWarnings("unchecked")
	@PostMapping("/kko/mydoc/readCompleted")
	public ResponseEntity<RestResponseVO> readCompleted(@RequestBody Map<String, Object> mParam) {

		//중개사업자 계약정보 설정
		String accessToken = "b40d3c623d1011ecbceae6cf4630da62";
		String document_binder_uuid   = mParam.containsKey("document_binder_uuid")?(String)mParam.get("document_binder_uuid"):null;

		RestResponseVO restResponseVO = null;
		try {
			cokr.xit.modules.domain.common.Error respDTO = kkoMyDocService.readCompleted(accessToken, document_binder_uuid);

			//Set 응답메시지
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> resultInfo = mapper.convertValue(respDTO, Map.class);
			restResponseVO = RestResponseVO.okBuilder()
					.resultInfo(resultInfo)
					.build();

		} catch (InternalException e) {
			//Set 응답메시지
			restResponseVO = RestResponseVO.errBuilder()
					.errCode(e.getErrCd())
					.errMsg(e.getMessage())
					.build();

		} catch (Exception e) {
			//Set 응답메시지
			restResponseVO = RestResponseVO.errBuilder()
					.errCode(InternalErrCd.ERR999)
					.errMsg(e.getMessage())
					.build();
		}
		return new ResponseEntity<RestResponseVO>(restResponseVO, HttpStatus.OK);
	}




	@SuppressWarnings("unchecked")
	@GetMapping("/kko/mydoc/status")
	public ResponseEntity<RestResponseVO> status(@RequestBody Map<String, Object> mParam) {

		//중개사업자 계약정보 설정
		String accessToken = "b40d3c623d1011ecbceae6cf4630da62";
		String contractUuid = "CON-b4126c833d1011ecbceae6cf4630da62";
		String document_binder_uuid   = mParam.containsKey("document_binder_uuid")?(String)mParam.get("document_binder_uuid"):null;

		RestResponseVO restResponseVO = null;
		try {
			StatusRespDTO statusRespDTO = kkoMyDocService.status(accessToken, contractUuid, document_binder_uuid);

			//Set 응답메시지
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> resultInfo = mapper.convertValue(statusRespDTO, Map.class);
			restResponseVO = RestResponseVO.okBuilder()
					.resultInfo(resultInfo)
					.build();

		} catch (InternalException e) {
			//Set 응답메시지
			restResponseVO = RestResponseVO.errBuilder()
					.errCode(e.getErrCd())
					.errMsg(e.getMessage())
					.build();

		} catch (Exception e) {
			//Set 응답메시지
			restResponseVO = RestResponseVO.errBuilder()
					.errCode(InternalErrCd.ERR999)
					.errMsg(e.getMessage())
					.build();
		}
		return new ResponseEntity<RestResponseVO>(restResponseVO, HttpStatus.OK);
	}




	@SuppressWarnings("unchecked")
	@PostMapping("/kko/mydoc/send/bulk")
	public ResponseEntity<RestResponseVO> bulkSend(@RequestBody String jsonStr) {

		//중개사업자 계약정보 설정
		String accessToken = "b40d3c623d1011ecbceae6cf4630da62";
		String contractUuid = "CON-b4126c833d1011ecbceae6cf4630da62";

		RestResponseVO restResponseVO = null;
		try {
			BulkSendRespDTO bulkSendRespDTO = kkoMyDocService.bulkSend("unknown중개사업자", accessToken, contractUuid, jsonStr);
			if(bulkSendRespDTO!=null && bulkSendRespDTO.getError_code()==null) {	//정상응답 이면...
				//bulk전송 응답메시지(documents)에 payload 추가
				SendDetailKkoMydoc sendDetailKkoMydoc = sendDetailKkoMydocRepository.findByExternalDocumentUuid(bulkSendRespDTO.getDocuments().get(0).getExternal_document_uuid());
				bulkSendRespDTO.setDocuments(sendDetailKkoMydocRepository.findSendRespDetailsBySendMastId(sendDetailKkoMydoc.getSendMast().getSendMastId()));
			}
			//Set 응답메시지
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> resultInfo = mapper.convertValue(bulkSendRespDTO, Map.class);
			restResponseVO = RestResponseVO.okBuilder()
					.resultInfo(resultInfo)
					.build();

		} catch (InternalException e) {
			//Set 응답메시지
			restResponseVO = RestResponseVO.errBuilder()
					.errCode(e.getErrCd())
					.errMsg(e.getMessage())
					.build();

		} catch (Exception e) {
			//Set 응답메시지
			restResponseVO = RestResponseVO.errBuilder()
					.errCode(InternalErrCd.ERR999)
					.errMsg(e.getMessage())
					.build();
		}

		return new ResponseEntity<RestResponseVO>(restResponseVO, HttpStatus.OK);
	}




	@SuppressWarnings("unchecked")
	@PostMapping("/kko/mydoc/status/bulk")
	public ResponseEntity<RestResponseVO> bulkStatus(@RequestBody String jsonStr) {

		//중개사업자 계약정보 설정
		String accessToken = "b40d3c623d1011ecbceae6cf4630da62";
		String contractUuid = "CON-b4126c833d1011ecbceae6cf4630da62";

		RestResponseVO restResponseVO = null;
		try {
			BulkStatusRespDTO bulkStatusRespDTO = kkoMyDocService.bulkStatus(accessToken, contractUuid, jsonStr);

			//Set 응답메시지
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> resultInfo = mapper.convertValue(bulkStatusRespDTO, Map.class);
			restResponseVO = RestResponseVO.okBuilder()
					.resultInfo(resultInfo)
					.build();

		} catch (InternalException e) {
			//Set 응답메시지
			restResponseVO = RestResponseVO.errBuilder()
					.errCode(e.getErrCd())
					.errMsg(e.getMessage())
					.build();

		} catch (Exception e) {
			//Set 응답메시지
			restResponseVO = RestResponseVO.errBuilder()
					.errCode(InternalErrCd.ERR999)
					.errMsg(e.getMessage())
					.build();
		}
		return new ResponseEntity<RestResponseVO>(restResponseVO, HttpStatus.OK);
	}



	/**
	 * <pre>메소드 설명: 현재상태 갱신(발송마스터 및 모든 발송상세 데이터)</pre>
	 * @return ResponseEntity<RestResponseVO> 요청처리 후 응답객체
	 * @author: 박민규
	 * @date: 2021. 12. 6.
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("/kko/mydoc/cur/status/all")
	public ResponseEntity<RestResponseVO> curStatusAll(@RequestBody Map<String, Object> mParam) {

		//중개사업자 계약정보 설정
		String accessToken = "b40d3c623d1011ecbceae6cf4630da62";
		String contractUuid = "CON-b4126c833d1011ecbceae6cf4630da62";

		RestResponseVO restResponseVO = null;
		try {
			List<String> sendMastIds = mParam.containsKey("sendMastIds")?(List<String>)mParam.get("sendMastIds"):null;
			if(sendMastIds == null)
				throw new InternalException(InternalErrCd.ERR401, "발송마스터ID(은)는 필수조건 입니다.");

			ObjectMapper mapper = new ObjectMapper();
			List<Map<String, Object>> resultInfo = new ArrayList<Map<String,Object>>();
			for(int i=0; i<sendMastIds.size(); i++) {
				Long sendMastId = Long.parseLong (String.valueOf(sendMastIds.get(i)));
				List<Error> respData = kkoMyDocService.modifySendMastCurStatBySendMastId(accessToken, contractUuid, sendMastId);
				Map<String, Object> row = mapper.convertValue(respData, Map.class);
				row.put("sendMastId", sendMastId);
				resultInfo.add(row);
			}

			//Set 응답메시지
			restResponseVO = RestResponseVO.okBuilder()
					.resultInfo(resultInfo)
					.build();

		} catch (InternalException e) {
			//Set 응답메시지
			restResponseVO = RestResponseVO.errBuilder()
					.errCode(e.getErrCd())
					.errMsg(e.getMessage())
					.build();

		} catch (Exception e) {
			//Set 응답메시지
			restResponseVO = RestResponseVO.errBuilder()
					.errCode(InternalErrCd.ERR999)
					.errMsg(e.getMessage())
					.build();
		}
		return new ResponseEntity<RestResponseVO>(restResponseVO, HttpStatus.OK);
	}
}
