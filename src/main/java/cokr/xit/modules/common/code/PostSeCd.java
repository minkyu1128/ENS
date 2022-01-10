package cokr.xit.modules.common.code;

import cokr.xit.code.CodeMapperType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PostSeCd implements CodeMapperType {

	kkoMydoc("카카오페이 내문서함(구 인증톡)")
	,kkoBill("카카오 고지서");

	
	@Getter
	private final String codeNm;
	
	@Override
	public String getCode() {
		return this.name();
	}

}
