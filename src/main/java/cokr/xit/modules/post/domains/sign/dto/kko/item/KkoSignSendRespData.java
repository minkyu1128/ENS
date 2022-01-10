package cokr.xit.modules.post.domains.sign.dto.kko.item;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

/**
 * 카카오 인증톡발송 응답데이터 
 * @author 박민규
 */
@Data
public class KkoSignSendRespData {
	
	@NotEmpty(message = "tx_id(은)는 필수조건 입니다.")
	@Length(max = 40, message = "tx_id(은)는 최대 40자 입니다.")
	private String tx_id;     //전자서명원문 접수번호. 이용기관 서버에서 카카오페이 인증 서버로 정상 접수된 전자서명의 원문의 식별 값(Transaction ID)
	
	@NotEmpty(message = "result(은)는 필수조건 입니다.")
	@Length(max = 1, message = "result(은)는 최대 1자 입니다.")
	private String result;    //처리결과. 정상 접수처리 시 "Y", 그 외 "N"
	
	private String errcode;   //에러코드
	
	private String errmsg;    //에러메시지
}
