package cokr.xit.modules.post.domains.bill.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import cokr.xit.modules.post.code.PaymentStatCd;
import lombok.Builder;
import lombok.Data;

/**
 * 청구서 발송정보
 * @author 박민규
 *
 */
//@Entity
@Data
@Builder
public class KakaoBillSendInfo {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long billId;               //ID(PK)
	
	@NotEmpty
	private String billerCode;     //빌러 코드
	
	@NotEmpty
	@Length(max = 50, message = "biller_user_key(은)는 최대 50자(character) 입니다.")
	private String billerUserKey;  //빌러 고객 식별자. 기관에서 관리하는 해당 고객번호 혹은 계약번호. 전화번호 등 대외적으로도 고객을 식별할 수 있는 개인정보는 고객번호로 사용이 불가합니다.
	
	@NotEmpty
	@Length(max = 14)              
	private String expireAt;       //URL 만료일, YYYYMMDDHH24MISS(예: 20200130235959)
	
	@NotEmpty
	@Enumerated(EnumType.STRING)
	private PaymentStatCd paymentStatCd; //납부 상태
	
	@Lob                           
	private String parameters;     //key/value 형태의 청구서 조회 등 API 호출 시 함께 전달할 값
	
	private String resCode;        //응답 코드
	
	private String message;        //응답 메시지
	
	private String returnUrl;      //사용자가 수신된 청구서 열람시 연결될 URL
	
	@Lob
	private String notiInfo;       //청구서 정보(json String) 
	
	private String userBirth;      //사용자 생년월일(yyyyMMdd)
	
	@CreationTimestamp             
	private LocalDateTime RegDt; //등록 일시
}
