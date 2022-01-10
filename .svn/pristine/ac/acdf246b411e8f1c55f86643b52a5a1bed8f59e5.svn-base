package cokr.xit.modules.post.domains.bill.dto.kko;

import lombok.Data;

/**
 * 청구서생성 & 청구서확인 통합 DTO
 * 	-.청구서확인을 본 시스템에 위임하고자 할 경우 noti정보도 함께 전송한다.
 * @author 
 *
 */
@Data
public class KkoSendnNotiReqDTO {
	
//	@NotEmpty(message = "data(은)는 필수조건 입니다.")
	private KkoSendReqDTO send;	 //청구서링크생성 DTO
	private KkoNotiRespDTO noti; //청구서확인 DTO
	private String userBirth;    //고객 생년월일.(yyyyMMdd 형식) => 청구서확인 DTO 사용 시 필수
}
