package cokr.xit.modules.common.code;

import cokr.xit.code.CodeMapperType;

/**
 * <ul>
 * <li>업무 그룹명: 내부 오류 코드</li>
 * <li>설 명: </li>
 * <li>작성일: 2021. 8. 4. 오후 3:51:43
 * </ul>
 * @author 박민규
 */
public enum InternalErrCd implements CodeMapperType{

	OK("정상")

	//기타오류
	,ERR999("시스템 내부 오류")
	,ERR901("권한 없음")

	//클라이언트 요청 오류
	,ERR401("필수 파라미터가 없습니다.")
	,ERR402("파라미터 유효성 검증 오류")
	,ERR403("잘못된 파라미터 입니다.")
	,ERR404("일치하는 자료가 없습니다.")
	,ERR405("잘못된 요청 값.")

	//서버 오류
	,ERR501("HttpServer 오류")
	,ERR502("HttpClient 오류")
	,ERR503("RestClient 오류")
	,ERR504("요청 데이터 Json 파싱 오류")
	,ERR505("응답 데이터 Json 파싱 오류")
	,ERR506("Hash 생성 오류")

	//외부서비스 오류
	,ERR600("API서버 요청 오류")
	,ERR601("API서버 응답 오류")
	,ERR602("API서버 내부 오류")
	,ERR603("유효하지 않은 토큰(OTT) 값")
	;



	private final String code;     //코드
	private final String codeNm;   //코드명
	InternalErrCd(String codeNm) {
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
