package cokr.xit.modules.domain.common;

import lombok.Data;

@Data
public class Error {
	protected String error_code;
	protected String error_message;
}
