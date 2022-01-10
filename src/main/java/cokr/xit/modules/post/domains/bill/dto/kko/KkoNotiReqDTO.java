package cokr.xit.modules.post.domains.bill.dto.kko;

import cokr.xit.modules.post.domains.bill.dto.kko.item.KkoNotiReqData;
import lombok.Data;

@Data
public class KkoNotiReqDTO {
	
//	@NotEmpty(message = "data(은)는 필수조건 입니다.")
	KkoNotiReqData data;
}
