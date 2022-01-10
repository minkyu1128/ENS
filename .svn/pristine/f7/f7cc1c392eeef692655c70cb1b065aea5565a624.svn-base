package cokr.xit.modules.post.domains.bill.dto.kko.item;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class KkoNotiRespDataResult {

	@Length(max = 50, message = "settlement_code(은)는 최대 50자 입니다.")
	private String settlement_code;                                  //정산 구분 코드 – 청구서별 정산 정책이 상이한 경우 정산 정책 구분을 위한 코드
	
	@NotEmpty(message = "biller_user_key(은)는 필수조건 입니다.")
	@Length(max = 50, message = "biller_user_key(은)는 최대 50자 입니다.")
	private String biller_user_key;                                  //기관에서 관리하는 해당 고객번호 혹은 계약번호. 전화번호 등 대외적으로도 고객을 식별할 수 있는 개인정보는 고객번호로 사용이 불가합니다.
	
	@Length(max = 50, message = "billed_year_month(은)는 최대 50자 입니다.")
	private String billed_year_month;                                //청구 연월(YYYYMM 형식), 없을 시 생략
	
	@Length(max = 24, message = "ordinal(은)는 최대 24자 입니다.")
	private String ordinal;                                          //동일 고객번호, 청구 연월의 복수 개 청구서 발행 등 몇 번째 청구인지 구분을 위해서 입력합니다.
	
	@Length(max = 80, message = "biller_notice_key(은)는 최대 80자 입니다.")
	private String biller_notice_key;                                //개별 청구서를 식별하는 키 값. 처리 결과 조회에 사용되므로, 유니크한 값을 가지도록 생성되어야 합니다.
	
	@NotEmpty(message = "title(은)는 필수조건 입니다.")
	private String title;                                            //청구서명
	
	@NotEmpty(message = "amount(은)는 필수조건 입니다.")
	private Number amount;                                           //납부 요청금액
	
	private Number tax_free_amount;                                  //amount 중 비과세 금액 (비우거나 또는 0)
	
	private Number vat_amount;                                       //amount 중 부가세 금액 (비우거나 또는 0) 미 입력 시 (amount – tax_free_amount) / 11
	
	@Length(max = 2, message = "expire_type(은)는 최대 2자 입니다.")
	private String expire_type;                                      //기본값 D1, D0 – 납기 무관, D1 – 첫 번째 납기까지 , D2 – 두 번째 납기까지
	
	@NotEmpty(message = "pay_expire_date(은)는 필수조건 입니다.")
	@Length(max = 8, message = "pay_expire_date(은)는 최대 8자 입니다.")
	private String pay_expire_date;                                  //첫 번째 납기일(YYYYMMDD) D1, D2인 경우 필수 *고객 안내용이며, 서비스에서 별도 제어(납부제한)하지 않음
	
	@Length(max = 8, message = "second_pay_expire_date(은)는 최대 8자 입니다.")
	private String second_pay_expire_date;                           //두 번째 납기일(YYYYMMDD) D2인 경우 필수 *고객 안내용이며, 서비스에서 별도 제어(납부제한)하지 않음
	
	private List<KkoNotiRespDataResultBankaccounts> bank_accounts;   //계좌 송금 수단 사용 시 계좌 정보 (하단 bank_accounts 항목 참고) * 송금을 사용하는 경우 카카오페이와 별도 협의 필요
	
	private List<Map<String, Object>> details;              //청구서 페이지 내에 보여줄 상세 정보. 총 4개의 타입(KEY_VALUE/TEXT/TABLE/GRAPH)이 있으며 타입별 필드 구성요소가 다름
	
	
}
