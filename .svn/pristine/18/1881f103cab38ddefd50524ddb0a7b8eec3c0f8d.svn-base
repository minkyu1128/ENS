package cokr.xit.modules.common.model;

import cokr.xit.modules.common.code.InternalErrCd;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter @ToString
public class RestResponseVO {

	private InternalErrCd errCode = null;
	private String errMsg  = null;
//	private Map<String, Object> resultInfo;
	private Object resultInfo;
	
	@Builder(builderClassName = "okBuilder" ,builderMethodName = "okBuilder")
//	RestResponseVO(Map<String, Object> resultInfo) {
	RestResponseVO(Object resultInfo) {
		this.resultInfo = resultInfo;
	}
	
	@Builder(builderClassName = "errBuilder" ,builderMethodName = "errBuilder")
	RestResponseVO(InternalErrCd errCode, String errMsg) {
		this.errCode = errCode;
		this.errMsg = errMsg;
	}
}
