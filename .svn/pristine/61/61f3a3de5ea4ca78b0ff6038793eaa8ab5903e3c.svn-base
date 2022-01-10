package cokr.xit.modules.post.domains.sign.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import lombok.Builder;
import lombok.Data;


/**
 * 토큰확인이력
 * @author 박민규
 */
//@Entity
@Data
@Builder
public class KakaoTokenVerifyHist {

	/* =============
	 * 요청 데이터 필드
	 ============= */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long tokenVerifyId;               //ID(PK)
	@JoinColumn(name = "tx_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private KakaoSignTalkSendInfo kakaoSignTalkSendInfo;
	@NotEmpty
	@Length(max = 80)
	private String token;  //카카오페이 인증 서버로부터 받은 one time token
	
	
	/* =============
	 * 응답 데이터 필드
	 ============= */
	@NotEmpty
	@Length(max = 1)
	private String result;           //토큰 검증 결과(Y/N)
	@Length(max = 4096)
	private String signed_data;      //전자서명전문
	@Length(max = 13)
	private Number request_at;       //서명 요청 날짜/시간
	@Length(max = 13)
	private Number viewed_at;        //서명 조회 날짜/시간. 조회 전에는 미제공
	@Length(max = 13)
	private Number completed_at;     //서명 완료 날짜/시간. 서명완료 시 제공
	@Length(max = 13)
	private Number expired_at;       //서명 만료 날짜/시간
	@Length(max = 13)
	private Number org_notified_at;  //서명결과 최초 인지시각
	private String errcode;   //에러코드
	private String errmsg;    //에러메시지
	
	
	
	
	
	@CreationTimestamp             
	private LocalDateTime RegDt; //등록 일시
	/* =============
	 * 내부처리 필드
	 ============= */
	private String sendStatAt;  //발송상태(Y:성공, N:실패)
	private String sendErrCode; //발송에러코드(실패인 경우만)
	private String sendErrMsg;  //발송에러메시지(실패인 경우만)
	
}
