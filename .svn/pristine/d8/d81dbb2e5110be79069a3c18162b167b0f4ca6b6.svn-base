package cokr.xit.modules.post.domains.bill.dto.kko.item;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class KkoNotiRespDataResultBankaccounts {

	@NotEmpty(message = "bank_code(은)는 필수조건 입니다.")
	@Length(max = 5, message = "은행코드는 최대 5자 입니다.")
	private String bank_code;     //은행코드
	
	@NotEmpty(message = "account_no(은)는 필수조건 입니다.")
	@Length(max = 50, message = "account_no(은)는 최대 50자 입니다.")
	private String account_no;    //계좌번호
	
	@Length(max = 20, message = "holder_name(은)는 최대 20자 입니다.")
	private String holder_name;   //계좌주명
	
	@Length(max = 20, message = "sender_name(은)는 최대 20자 입니다.")
	private String sender_name;   //입금자명
	
	@NotEmpty(message = "account_type(은)는 필수조건 입니다.")
	@Length(max = 10, message = "accont_type(은)는 최대 20자 입니다.")
	private String account_type;  //계좌 구분 타입 NORMAL: 일반계좌, VIRTUAL: 가상계좌
}
