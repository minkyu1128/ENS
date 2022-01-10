package cokr.xit.modules.post.domains.bill.dto.kko;

import javax.validation.constraints.NotEmpty;

import cokr.xit.modules.post.domains.bill.dto.kko.item.KkoSendRespData;
import lombok.Data;

@Data
public class KkoSendRespDTO {

	@NotEmpty(message = "res_code(은)는 필수조건 입니다.")
	private String res_code;	//청구서 링크 생성 에러코드 참고
	
	@NotEmpty(message = "message(은)는 필수조건 입니다.")
	private String message;	//응답메시지 참고
	
	@NotEmpty(message = "data(은)는 필수조건 입니다.")
	private KkoSendRespData data;
}
