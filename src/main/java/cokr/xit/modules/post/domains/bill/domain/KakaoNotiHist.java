package cokr.xit.modules.post.domains.bill.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Builder;
import lombok.Data;

/**
 * 청구서열람이력
 * @author 박민규
 *
 */
//@Entity
@Data
@Builder
public class KakaoNotiHist {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long notiHistId;               //ID(PK)
	
//	@NotEmpty(message = "biller_user_key(은)는 필수조건 입니다.")
//	@Length(max = 50, message = "biller_user_key(은)는 최대 50자 입니다.")
//	private String biller_user_key;	//기관에서 관리하는 해당 고객번호 혹은 계약번호. 전화번호 등 대외적으로도 고객을 식별할 수 있는 개인정보는 고객번호로 사용이 불가합니다.
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "billId")
	private KakaoBillSendInfo kakaoBillSendInfo;	//청구서전송정보
	
//	@Length(max = 6, message = "billed_year_month(은)는 최대 6자(character) 입니다.")
//	private String billed_year_month;         //청구 연월(YYYYMMDD 형식), 없을 시 생략
//	
//	@Length(max = 24, message = "ordinal(은)는 최대 24자(character) 입니다.")
//	private String ordinal;                   //동일 고객번호, 청구 연월의 복수 개 청구서 발행 등 몇 번째 청구인지 구분을 위해서 입력합니다. 영문/숫자 입력 가능
//	
//	@Length(max = 80, message = "biller_notice_key(은)는 최대 80자(character) 입니다.")
//	private String biller_notice_key;         //개별 청구서를 식별하는 키 값. 처리 결과 조회에 사용되므로, 유니크한 값을 가지도록 생성되어야 합니다.
//	
//	private Map<String, Object> parameters;   //URL 생성 시 전달한 파라미터
	
	@NotEmpty(message = "user_birth(은)는 필수조건 입니다.")
	private String user_birth;	//고객 생년월일(YYYYMMDD)
	
	@Lob
	private String reqParam;  //요청 본문
	
	@Lob
	private String respParam; //응답 본문
	
	@CreationTimestamp             
	private LocalDateTime RegDt; //등록 일시
	
	
	
}
