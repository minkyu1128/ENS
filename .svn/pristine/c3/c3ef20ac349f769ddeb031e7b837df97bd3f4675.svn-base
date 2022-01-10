package cokr.xit.modules.post.code;

import cokr.xit.code.CodeMapperType;

/**
 * <ul>
 * <li>업무 그룹명: 카카오페이 응답 코드</li>
 * <li>설 명: </li>
 * <li>작성일: 2021. 8. 4. 오후 3:51:43
 * </ul>
 * @author 박민규
 */
public enum KkoRespCd implements CodeMapperType {

	OK("정상응답")
	,E001("필수 파라미터가 없습니다.")
	,E003("잘못된 빌러 식별 코드입니다.")
	,E005("청구서 식별 코드가 필요합니다.")
	,E006("파라미터의 최대 길이가 초과했습니다.")
	,E007("파라미터의 자릿수가 맞지 않습니다.")
	,E008("JSON 파싱 에러")
	,E101("parameter  저장이 실패했습니다. key, value의 길이 및 타입을 확인해주세요.")
	,E402("청구서 조회 결과가 없습니다.")
	,E403("납부 가능 금액 없음")
	,E404("생년월일이 일치하지 않습니다.")
	,E501("납부불가 - 납부 가능금액 초과")
	,E502("납부불가 - 납부 대상 없음")
	,E506("납부불가 - 납부기한이 경과했습니다.")
	,E601("수납처리실패 - 납부요청 정보 조회 실패")
	
	//에이전시 응답 코드
	,E801("[에이전시] 내부 에러")
	,E806("[에이전시] 카카오페이에 요청 중 타임아웃 발생")
	,E807("[에이전시] 요청 데이터 JSON 파싱 에러")
	,E808("[에이전시] 응답 데이터 JSON 파싱 에러")
	,E809("[에이전시] 요청에 대한 응답 데이터를 받지 못했습니다.")
	,E811("[에이전시] 청구서 식별 코드가 중복됩니다.")
	,E812("[에이전시] 등록되지 않은 청구서입니다.")
	,E817("[에이전시] 오류 건으로 분류된 청구서 입니다.")
	,E818("[에이전시] 이미 링크 생성된 청구서입니다.")
	
	
	//시스템 응답 코드
	,E901("[시스템] 내부 에러")
	,E902("[시스템] 데이터 등록 중 에러 발생")
	;
	


	private final String code;     //코드
	private final String codeNm;   //코드명
	KkoRespCd(String codeNm) {
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
