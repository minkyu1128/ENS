package cokr.xit.modules.common.code;

import cokr.xit.code.CodeMapperType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum StatCd implements CodeMapperType {

	accept("접수완료")
//	,idle("발송대기")
	,success("발송성공")
	,fail("발송실패")
	,open("열람중")
	,close("조회기간마감")
	;
	
	@Getter
	private final String codeNm;
	
	@Override
	public String getCode() {
		return this.name();
	}

}
