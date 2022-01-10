package cokr.xit.modules.post.domains.sign.service;

import java.util.ArrayList;
import java.util.List;

import cokr.xit.modules.common.code.InternalErrCd;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class Response {

	private int httpStatus;
	private String errCode;
	private String errMsg;
	private List<String> respPlainText;
	
	public Response(){
		this.httpStatus = -1;
		this.errCode = null;
		this.errMsg = null;
		this.respPlainText = null;
	}
	

	public void setOkResult(String respPlainText) {
		this.errCode = InternalErrCd.OK.name();
		this.errMsg  = InternalErrCd.OK.getCodeNm();
//		this.respPlainText = respPlainText;
		this.respPlainText = new ArrayList<String>();
		this.respPlainText.add(respPlainText);
	}
	public void setOkResult(List<String> respPlainText) {
		this.errCode = InternalErrCd.OK.name();
		this.errMsg  = InternalErrCd.OK.getCodeNm();
//		this.respPlainText = respPlainText;
		this.respPlainText = respPlainText;
	}
	
	public void setFailResult(InternalErrCd errCd, String message) {
		this.setFailResult(errCd, message, -1);
	}
	
	public void setFailResult(InternalErrCd errCd, String message, int httpStatus) {
		this.httpStatus = httpStatus;
		this.errCode = errCd.name();
		this.errMsg  = String.format("%s :: [ %s ]", errCd.getCodeNm(), message);
	}
}
