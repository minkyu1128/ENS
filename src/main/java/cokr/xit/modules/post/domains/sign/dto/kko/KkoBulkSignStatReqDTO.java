package cokr.xit.modules.post.domains.sign.dto.kko;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

/**
 * 카카오 인증톡 상태요청 DTO
 * @author 박민규
 */
@Data
public class KkoBulkSignStatReqDTO {

	@NotEmpty(message = "tx_ids(은)는 필수조건 입니다.")
	List<String> tx_ids;	//상태 조회를 원하는 tx_id의 Array
}
