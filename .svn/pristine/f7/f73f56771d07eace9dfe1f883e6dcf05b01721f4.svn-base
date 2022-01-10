package cokr.xit.modules.post.domains.bill.dto.kko.item;

import java.util.Map;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

/**
 * 청구서 링크 생성
 * 	-.URL: /external/inquiry-payment/one-time/url/{biller_code}
 * @author 박민규
 */
@Data
public class KkoSendReqData {

	@NotEmpty(message = "biller_user_key(은)는 필수조건 입니다.")
	@Length(max = 50, message = "biller_user_key(은)는 최대 50자(character) 입니다.")
	private String biller_user_key;           //기관에서 관리하는 해당 고객번호 혹은 계약번호. 전화번호 등 대외적으로도 고객을 식별할 수 있는 개인정보는 고객번호로 사용이 불가합니다.
	
	@Length(max = 6, message = "billed_year_month(은)는 최대 6자(character) 입니다.")
	private String billed_year_month;         //청구 연월(YYYYMMDD 형식), 없을 시 생략
	
	@Length(max = 24, message = "ordinal(은)는 최대 24자(character) 입니다.")
	private String ordinal;                   //동일 고객번호, 청구 연월의 복수 개 청구서 발행 등 몇 번째 청구인지 구분을 위해서 입력합니다. 영문/숫자 입력 가능
	
	@Length(max = 80, message = "biller_notice_key(은)는 최대 80자(character) 입니다.")
	private String biller_notice_key;         //개별 청구서를 식별하는 키 값. 처리 결과 조회에 사용되므로, 유니크한 값을 가지도록 생성되어야 합니다.
	
	@NotEmpty(message = "expire_at(은)는 필수조건 입니다.")
	@Length(max = 14, message = "expire_at(은)는 최대 14자(character) 입니다.")
	private String expire_at;                 //URL 만료일, YYYYMMDDHH24MISS(예: 20200130235959)
	
	private Map<String, Object> parameters;   //key/value 형태의 청구서 조회 등 API 호출 시 함께 전달할 값

}