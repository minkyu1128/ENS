package cokr.xit.modules.post.domains.bill.dto.kko.item;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class KkoNotiRespData {

	@NotEmpty
	private KkoNotiRespDataResult result;
}
