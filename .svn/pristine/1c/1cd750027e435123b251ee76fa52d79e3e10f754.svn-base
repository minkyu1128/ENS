package cokr.xit.modules.common.exception;

import cokr.xit.modules.common.code.InternalErrCd;

public class InternalException extends RuntimeException{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	private InternalErrCd errCd;
	
	public InternalException(InternalErrCd errCd) {
		super(errCd.getCodeNm());
		this.errCd = errCd;
	}
	public InternalException(InternalErrCd errCd, String message) {
		super(String.format("%s::: %s", errCd.getCodeNm(), message));
		this.errCd = errCd;
	}
	
	public InternalException(InternalErrCd errCd, String message, Throwable cause) {
		super(String.format("%s::: %s", errCd.getCodeNm(), message), cause);
		this.errCd = errCd;
	}

	public InternalErrCd getErrCd() {
		return errCd;
	}


}

