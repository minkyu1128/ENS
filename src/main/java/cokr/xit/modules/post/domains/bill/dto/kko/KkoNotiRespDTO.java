package cokr.xit.modules.post.domains.bill.dto.kko;

import javax.validation.constraints.NotEmpty;

import cokr.xit.modules.post.code.KkoRespCd;
import cokr.xit.modules.post.domains.bill.dto.kko.item.KkoNotiRespData;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class KkoNotiRespDTO {

	@NotEmpty
	private KkoRespCd res_code;       //청구서 조회 에러코드
	@NotEmpty
	private String message;        //응답메시지
	@NotEmpty
	private KkoNotiRespData data; 
	
}
