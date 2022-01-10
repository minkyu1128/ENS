package cokr.xit.modules.post.code;

import cokr.xit.code.CodeMapperType;

/**
 * <ul>
 * <li>업무 그룹명: 납부 상태 코드</li>
 * <li>설 명: </li>
 * <li>작성일: 2021. 8. 4. 오후 3:51:43
 * </ul>
 * @author 박민규
 */
public enum PaymentStatCd implements CodeMapperType {

	complete("완납")
	,partial("부분납")
	,unpaid("미납")
	;
	


	private final String code;     //코드
	private final String codeNm;   //코드명
	PaymentStatCd(String codeNm) {
		this.code = this.name();
		this.codeNm = codeNm;
	}
	
	@Override
	public String getCode() {
		return this.code;
	}

	@Override
	public String getCodeNm() {
		return this.codeNm;
	}
}
