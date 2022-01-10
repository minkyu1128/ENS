package cokr.xit.modules.post.domains.bill.dto.kko.item;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

/**
 * 청구서 링크 생성
 * 	-.URL: /external/inquiry-payment/one-time/url/{biller_code}
 * @author 박민규
 */
@Data
public class KkoSendRespData {
	
	@NotEmpty(message = "url(은)는 필수값 입니다.")
	private String url;
	
	@Length(max = 80, message = "biller_notice_key(은)는 최대 80자(character) 입니다.")
	private String biller_notice_key;	//전달된 단일 청구서 식별 값
}
