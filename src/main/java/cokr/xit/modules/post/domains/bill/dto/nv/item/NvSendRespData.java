package cokr.xit.modules.post.domains.bill.dto.nv.item;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class NvSendRespData {

	@NotEmpty(message = "result(은)는 필수조건 입니다.")
	@Length(max = 10, message = "result(은)는 최대 10자 입니다.")
	private String result;       //요청 결과(success or fail)
	
	@Length(max = 100, message = "docId(은)는 최대 100자 입니다.")
	private String docId;        //고지서 고유 식별자
	
	@NotEmpty(message = "clientDocId(은)는 필수조건 입니다.")
	@Length(max = 100, message = "clientDocId(은)는 최대 100자 입니다.")
	private String clientDocId;  //이용기관에서 식별가능한 고유값
	
	@Length(max = 10, message = "errorCode(은)는 최대 10자 입니다.")
	private String errorCode;    //(실패 시) 실패 코드
	
	@Length(max = 30, message = "errorMsg(은)는 최대 30자 입니다.")
	private String errorMsg;     //(실패 시) 실패 사유
}
