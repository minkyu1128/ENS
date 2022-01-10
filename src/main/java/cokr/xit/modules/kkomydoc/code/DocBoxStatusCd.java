package cokr.xit.modules.kkomydoc.code;

import cokr.xit.code.CodeMapperType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 
 * <ul>
 * <li>업무 그룹명: 진행상태코드</li>
 * <li>설 명: </li>
 * <li>작성일: 2021. 11. 23. 오후 5:10:35
 * </ul>
 *
 * @author 박민규
 *
 */
@RequiredArgsConstructor
public enum DocBoxStatusCd implements CodeMapperType {

	SENT("송신")       //문서발송 요청에는 성공했으나 실제 사용자에게 문서가 도달하지 않은 상태. 알림톡은 받았으나 페이 회원이 아니여서 실제 문서가 발송되지 않음
	,RECEIVED("수신")  //실제 사용자에게 문서가 도달된 상태
	,READ("열람")      //OTT 검증 완료 후 문서 상태 변경 API 호출에 성공한 상태
	,EXPIRED("만료")   //미열람 문서에 대해 열람 만료시간이 지난 상태
	,UNKOWN("알수없음") //Enum null safe 처리를 위한 코드
	;

	
	@Getter
	private final String codeNm;
	
	@Override
	public String getCode() {
		return this.name();
	}

}
