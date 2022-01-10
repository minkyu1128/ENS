package cokr.xit.modules.domain.common;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Embeddable
@Getter
@RequiredArgsConstructor
public class FieldError {

	private String errorCode;
	
	@Column(length = 3000)
	private String errorMessage;
	
	
	@Builder(builderMethodName = "initBuilder")
	public FieldError(String errorCode, String errorMessage) {
		this.errorCode    = errorCode;
		this.errorMessage = errorMessage;
	}
}
