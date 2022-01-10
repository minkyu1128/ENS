package cokr.xit.modules.post.domains.bill.dto.nv.item;

import java.util.Map;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class NvSendReqDataMessage {

	@NotEmpty(message = "message의 data(은)는 필수조건 입니다.")
	private Map<String, Object> data;  
}
