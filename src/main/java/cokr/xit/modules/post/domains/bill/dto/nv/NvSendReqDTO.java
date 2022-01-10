package cokr.xit.modules.post.domains.bill.dto.nv;

import java.util.List;

import cokr.xit.modules.post.domains.bill.dto.nv.item.NvSendReqData;
import lombok.Data;

@Data
public class NvSendReqDTO {
	
//	@NotEmpty(message = "data(은)는 필수조건 입니다.")
	List<NvSendReqData> data;
}
