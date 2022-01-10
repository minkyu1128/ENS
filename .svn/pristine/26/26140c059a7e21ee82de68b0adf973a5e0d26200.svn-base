package cokr.xit.code.impl;

import cokr.xit.code.CodeMapperType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Sample implements CodeMapperType {

	CODE1("코드1")
	,CODE2("코드2");

	
	@Getter
	private final String codeNm;
	
	@Override
	public String getCode() {
		return this.name();
	}

}
