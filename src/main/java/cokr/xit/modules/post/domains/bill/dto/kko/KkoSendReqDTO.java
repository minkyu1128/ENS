package cokr.xit.modules.post.domains.bill.dto.kko;

import cokr.xit.modules.post.domains.bill.dto.kko.item.KkoSendReqData;
import lombok.Data;

@Data
public class KkoSendReqDTO {
	
//	@NotEmpty(message = "data(은)는 필수조건 입니다.")
	KkoSendReqData data;
}
