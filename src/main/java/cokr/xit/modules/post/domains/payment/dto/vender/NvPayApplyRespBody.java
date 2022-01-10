package cokr.xit.modules.post.domains.payment.dto.vender;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class NvPayApplyRespBody {

	@NotEmpty
	@Length(max = 50)
	private String paymentId;            //네이버페이 결제 번호
	
	@NotEmpty
	private NvPayApplyRespBodyDetail detail;  //네이버페이 결제결과 상세정보
}
