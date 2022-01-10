package cokr.xit.modules.nicedici.presentation;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import cokr.xit.modules.common.code.InternalErrCd;
import cokr.xit.modules.common.exception.InternalException;
import cokr.xit.modules.common.model.RestResponseVO;
import cokr.xit.modules.nicedici.model.DiCiRespDTO;
import cokr.xit.modules.nicedici.service.NiceDiCiService;

@RestController
public class NiceDiCiController {
	
	@Resource
	private NiceDiCiService niceDiCiService;
	
	@SuppressWarnings("deprecation")
	@GetMapping("/nice/ci")
	public ResponseEntity<RestResponseVO> ci(@RequestBody Map<String, Object> mParam, ModelMap model) throws JsonMappingException, JsonProcessingException {
		String siteCode = String.valueOf(mParam.get("siteCode"));
		String sitePw = String.valueOf(mParam.get("sitePw"));
		String jumin = String.valueOf(mParam.get("jumin"));
		
		RestResponseVO restResponseVO = null;
		try {
			
			DiCiRespDTO diciRespDTO = niceDiCiService.ci(siteCode, sitePw, jumin);
			
			if(StringUtils.isEmpty(diciRespDTO.getError_code())) {	//ci 조회 성공 시..
				Map<String, Object> resultInfo = new HashMap<String, Object>();
				resultInfo.put("ci", diciRespDTO.getDici());
				restResponseVO = RestResponseVO.okBuilder()
						.resultInfo(resultInfo)
						.build();
				
			}else {	//ci 조회 실패 시..
				restResponseVO = RestResponseVO.errBuilder()
						.errCode(InternalErrCd.ERR600)
						.errMsg(diciRespDTO.getError_message())
						.build();
			}
			
		} catch (InternalException e) {
			restResponseVO = RestResponseVO.errBuilder()
					.errCode(e.getErrCd())
					.errMsg(e.getMessage())
					.build();
		} catch (Exception e) {
			restResponseVO = RestResponseVO.errBuilder()
					.errCode(InternalErrCd.ERR999)
					.errMsg(e.getMessage())
					.build();
		}
		
		
		return new ResponseEntity<RestResponseVO>(restResponseVO, HttpStatus.OK);
	}
}
