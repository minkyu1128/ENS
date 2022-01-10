package cokr.xit.modules.post.domains.payment.dto;

import lombok.Data;

/**
 * 
 * <ul>
 * <li>업무 그룹명: 네이버페이 결제승인 응답 데이터</li>
 * <li>설 명: </li>
 * <li>작성일: 2021. 8. 17. 오후 3:31:21
 * </ul>
 *
 * @author 박민규
 *
 */
@Data
public class NvPayApplyRespDTO{
	/* ====================================
	 * 결과코드
	 * 	-.Success: 성공
	 * 	-.Fail: PG, 은행 및 기타 오류 발생 시 오류 상세 원인이 message 필드로 전달됩니다. 결제 실패 사유를 인지할 수 있도록 Alert 등을 통한 안내가 필요 합니다.
	 * 	-.InvalidMerchant: 유효하지 않은 가맹점인 경우
	 * 	-.TimeExpired: 결제 승인 간으 시간 초과 시 (10분 초과시)
	 * 	-.AlreadyOnGoing: 해당 결제번호로 결제가 이미 진행 중일 때
	 * 	-.AlreadyComplete: 해당 결제번호로 이미 결제가 완료되었을 때
	 * 	-.OwnerAuthFail: 본인 카드 인증 오류 시
	 ==================================== */
	
	private String code;       //결과코드
	
	private String message;    //메시지
	
}
