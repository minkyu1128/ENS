package cokr.xit.modules.post.domains.bill.dto.kko.item;

import java.util.Map;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class KkoPayResultReqData {
	
	@NotEmpty(message = "biller_user_key(은)는 필수조건 입니다.")
	@Length(max = 50, message = "biller_user_key(은)는 최대 50자 입니다.")
	private String biller_user_key;	//기관에서 관리하는 해당 고객번호 혹은 계약번호. 전화번호 등 대외적으로도 고객을 식별할 수 있는 개인정보는 고객번호로 사용이 불가합니다.
	
	@Length(max = 6, message = "billed_year_month(은)는 최대 6자 입니다.")
	private String billed_year_month;	//청구 연월(YYYYMM 형식), 없을 시 생략
	
	@Length(max = 24, message = "ordinal(은)는 최대 24자 입니다.")
	private String ordinal;	//동일 고객번호, 청구 연월의 복수 개 청구서 발행 등 몇 번째 청구인지 구분을 위해서 입력하빈다. 영문/숫자 입력 가능
	
	@Length(max = 80, message = "biller_notice_key(은)는 최대 6자 입니다.")
	private String biller_notice_key;	//개별 청구서를 식별하는 키 값. 처리 결과 조회에 사용되므로, 유니크한 값을 가지도록 생성되어야 합니다.
	
	private Map<String, Object> parameters;	//URL 생성 시 전달한 파라미터

	@NotEmpty(message = "pay_by(은)는 필수조건 입니다.")
	private String pay_by;       //결제수단 CARD/MONEY/MONEY_TRANSFER
	
	@NotEmpty(message = "pay_type(은)는 필수조건 입니다.")
	@Length(max = 1, message = "pay_type(은)는 최대 1자 입니다.")
	private String pay_type;     //거래구분(P: 결제, C:취소)
	
	@Length(min = 6, max = 6, message = "pay_detail1(은)는 6자 입니다.")
	private String pay_detail1;  //결제수단 상세 1. 카드인 경우 카드번호 앞 6자리
	
	@Length(max = 20, message = "pay_detail2(은)는 최대 20자 입니다.")
	private String pay_detail2;  //결제수단 상세 2. 카드사 구분 코드
	
	private String pay_detail3;  //결제수단 상세 3. 공백
	
	@NotEmpty(message = "amount(은)는 필수조건 입니다.")
	private Number amount;       //납부요청 금액
	
	@NotEmpty(message = "pay_amount(은)는 필수조건 입니다.")
	private Number pay_amount;   //결제금액(거래구분이 P인 경우 양수(결제금액), C인 경우 음수(-취소금액))
	
	@Length(max = 10, message = "pay_fee_type(은)는 최대 10자 입니다.")
	private String pay_fee_type; //결제 수수료 금액 부과구분(BEFORE: 선취, AFTER: 후취, NONE: 부과 안함)
	
	@NotEmpty(message = "pay_fee(은)는 필수조건 입니다.")
	private Number pay_fee;      //결제 수수료 금액_공급가액
	
	@NotEmpty(message = "pay_fee_tax(은)는 필수조건 입니다.")
	private Number pay_fee_tax;  //결제 수수료 금액_부가세
	
	@Length(max = 8, message = "adjust_date(은)는 최대 8자 입니다.")
	private String adjust_date;  //정산예정일 YYYYMMDD
	
	@NotEmpty(message = "paid_at(은)는 필수조건 입니다.")
	@Length(max = 14, message = "paid_at(은)는 최대 14자 입니다.")
	private String paid_at;      //결제일시 YYYMMDDHH24MISS
	
	@NotEmpty(message = "pay_id(은)는 필수조건 입니다.")
	private Number pay_id;       //카카오페이 결제번호
	
}
