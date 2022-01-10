package cokr.xit.modules.post.domains.sign.dto.kko.item;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

/**
 * 카카오 인증톡 상태응답 데이터
 * @author 박민규
 */
@Data
public class KkoBulkSignStatRespData {

	@NotEmpty(message = "tx_id(은)는 필수조건 입니다.")
	@Length(max = 40, message = "tx_id(은)는 최대 40자 입니다.")
	private String tx_id;                          //전자서명원문 접수번호
	
	
//	@NotEmpty(message = "signed_data(은)는 필수조건 입니다.")
	private List<KkoBulkSignStatRespDataSignedData> signed_data; //서명 상태. 존재하지 않는 tx_id로 요청한 경우 signed_data는 빈 array로 내려옵니다.
}
