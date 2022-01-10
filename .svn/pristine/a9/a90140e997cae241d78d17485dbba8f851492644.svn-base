package cokr.xit.modules.post.code;

import cokr.xit.code.CodeMapperType;

/**
 * <ul>
 * <li>업무 그룹명: 네이버페이 응답 코드</li>
 * <li>설 명: 묶음/개별건에 대한 에러코드</li>
 * <li>작성일: 2021. 8. 4. 오후 3:51:43
 * </ul>
 * @author 박민규
 */
public enum NvRespCd implements CodeMapperType {
	
	
	//공통 응답코드(묶음단위에 대한 에러 코드)
	invalid_param(400, "파라미터가 유효하지 않음")
	,invalid_client_info(402, "Client Id 혹은 Client Secret이 유효하지 않음")
	
	//개별건에 대한 응답코드
	,forbidden_user(403, "등록된 관리자 혹은 테스트 아이디가 아님")
	,not_found_user(404, "해당 ci를 가진 회원이 존재하지 않음")
	,duplicated_client_doc_id(406, "해당 서비스 내에 동일한 client_doc_id를 가진 고지서가 존재함")	//이 에러메시지를 수신할 경우에는 기존에 발송된 고지서의 docId 항목이 응답에 추가 됨
	,refused_user(410, "회원이 전자문서 수신을 거부함")
	,internal_server_error(500, "내부 서버 오류")
	;
	


	private final String code;     //코드
	private final String codeNm;   //코드명
	NvRespCd(int code, String codeNm) {
		this.code = this.name();
		this.codeNm = code+" "+codeNm;
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
