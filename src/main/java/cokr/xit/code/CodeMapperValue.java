package cokr.xit.code;

import lombok.Getter;

@Getter
public class CodeMapperValue {
	private String code;
	private String codeNm;
	
	public CodeMapperValue(CodeMapperType codeMapperType) {
		this.code = codeMapperType.getCode();
		this.codeNm = codeMapperType.getCodeNm();
	}
	
}
