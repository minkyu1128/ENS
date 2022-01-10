package cokr.xit.modules.post.domains.payment.dto.vender;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class NvPayApplyRespBodyDetail {

	@NotEmpty
	@Length(max = 50)
	private String paymentId;            //네이버페이 결제번호

	@NotEmpty
	@Length(max = 50)
	private String payHistId;            //네이버페이 결제 이력 번호
	
	@NotEmpty
	@Length(max = 50)
	private String merchantId;           //가맹점 아이디(가맹점센터 로그인 아이디)
	
	@NotEmpty
	@Length(max = 50)
	private String merchantName;         //가맹점명
	
	@NotEmpty
	@Length(max = 64)
	private String merchantPayKey;       //가맹점의 결제번호
	
	@NotEmpty
	@Length(max = 50)
	private String merchantUserKey;      //가맹점의 사용자 키

	@NotEmpty
	@Length(max = 2)
	private String admissionTypeCode;    //결제승인 유형(01:원결제 승인건, 03:전체취소 건, 04:부분취소 건)
	
	@NotEmpty
	@Length(max = 14)
	private String admissionYmdt;        //결제/취소 일시(YYYYMMDDHH24MMSS)

	@NotEmpty
	@Length(max = 50)
	private String tradeConfirmYmdt;     //거래완료 일시(정산기준날짜, YYYYMMDDHH24MMSS) 비에스크로 가맹점은 결제일/취소일시와 같은 값을 가지고, 에스크로 가맹점은 거래완료 API를 호출하여야 값이 반환됩니다.
	
	@NotEmpty
	@Length(max = 10)
	private String admissionState;       //결제/취소 시도에 대한 최종결과(SUCCESS:완료, FAIL:실패)

	@NotEmpty
	private int totalPayAmount;          //총 결제/취소 금액
	
	@NotEmpty
	private int primaryPayAmount;        //주 결제 수단 결제/취소 금액
	
	@NotEmpty
	private int npointPayAmount;         //네이버페이 포인트 결제/취소 금액

	@NotEmpty
	private int giftCardAmount;          //기프트카드 결제/취소 금액
	
	@NotEmpty
	private int taxScopeAmount;          //과세 결제/취소 금액
	
	@NotEmpty
	private int taxExScopeAmount;        //면세 결제/취소 금액
	
	@NotEmpty
	@Length(max = 10)
	private String primaryPayMeans;      //주 결제 수단(CARD:신용카드, BANK:계좌이체)
	
	@NotEmpty
	@Length(max = 10)
	private String cardCorpCode;         //주 결제 수단 카드사. 지원 카드삿 정보를 참고
	
	@NotEmpty
	@Length(max = 50)
	private String cardNo;               //일부 마스킹 된 신용카드 번호
	
	@NotEmpty
	@Length(max = 30)
	private String cardAuthNo;           //카드승인번호. 취소 시에는 승인 번호 개념이 없으므로 원결제 승인 건에 대해서만 이 값이 반환됩니다.
	
	@NotEmpty
	private int cardInstCount;           //할부 개월 수(일시불은 0)
	
	@NotEmpty
	private String usedCardPoint;        //카드사 포인트 사용 유무(true/false), 미리 협의된 가맹점만 사용할 수 있습니다.
	
	@NotEmpty
	@Length(max = 10)
	private String bankCorpCode;         //주 결제 수단 은행. 지원 은행 정보를 참고 바랍니다.
	
	@NotEmpty
	@Length(max = 50)
	private String bankAccountNo;        //일부 마스킹 된 계좌 번호
	
	@NotEmpty
	@Length(max = 128)
	private String productName;          //상품명
	
	@NotEmpty
	private String settleExpected;       //true/false. 정산 예정 금액과 결제 수수료 금액이 계산되었는지 여부를 나타냅니다. 이 값이 false이면 아래 두 필드인 settleExpectAmount, payCommissionAmount 값은 무의미 합니다.	
	
	@NotEmpty
	private int settleExpectAmount;      //정산 예정 금액. 결제 후 약 1시간 후에 데이터가 생성되며, 그 전까지는 0값이 반환됩니다.(settleExpeted=true 이면 0 값도 의미를 갖습니다)
	
	@NotEmpty
	private int payCommissionAmount;     //결제 수수료 금액. 결제 후 약 1시간 후에 데이터가 생성되며, 그 전까지는 0 값이 반환됩니다.(settleExpeted=true 이면 0 값도 의미를 갖습니다)
	
	@NotEmpty
	private String extraDeduction;       //도서/공연 소득공제 여부(true/false)
	
	@NotEmpty
	private String useCfmYmdt;           //이용완료일(yyyymmdd)
}
