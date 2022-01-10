package cokr.xit.modules.post.domains.payment.dto;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

/**
 * 
 * <ul>
 * <li>업무 그룹명: 네이버페이 결제취소 응답 데이터</li>
 * <li>설 명: </li>
 * <li>작성일: 2021. 8. 17. 오후 3:40:06
 * </ul>
 *
 * @author 박민규
 *
 */
@Data
public class NvPayCancleRespDTO{

	@NotEmpty
	@Length(max = 50)
	private String paymentId         ; //네이버페이 결제번호
	
	private String merchantPayKey    ; //가맹점의 결제 번호
	
	@NotEmpty
	private Number cancelAmount      ; //취소 요청 금액
	
	@NotEmpty
	@Length(max = 256)
	private String cancelReason      ; //취소 사유
	
	@NotEmpty
	@Length(max = 1)
	private String cancelRequester   ; //취소 요청자(1: 구매자, 2:가맹점 관리자). 구분이 애매한 경우 가맹점 관리자로 입력합니다.
	
	@NotEmpty
	private Number taxScopeAmount    ; //과세 대상 금액. 과세 대상 금액 + 면세 대상 금액 = 총 결제 금액
	
	@NotEmpty
	private Number taxExScopeAmount  ; //면세 대상 금액. 과세 대상 금액 + 면세 대상 금액 = 총 결제 금액
	
	private Number doCompareRest     ; //가맹점의 남은 금액과 네이버페이의 남은 금액이 일치하는지 체크하는 기능을 수행할지 여부(1: 수행, 0: 미수행)
	
	private Number expectedRestAmount; //이번 취소가 수행되고 난 후에 납ㅁ을 가맹점의 예상 금액. 옵션 파라미터인 doCompareRest값이 1일 때에만 동작합니다. Ex)결제금액 1000원 중 200원ㅇ늘 취소하고 싶을 때 => expectedRestAmount = 800원, cancelAmount=200원으로 요청
}
