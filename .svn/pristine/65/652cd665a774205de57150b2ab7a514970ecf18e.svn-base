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
 * 전자서명상태 확인이력
 * @author 박민규
 */
//@Entity
@Data
@Builder
public class KakaoSignStatVerifyHist {

	/* =============
	 * 요청 데이터 필드
	 ============= */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long signVerifyId;               //ID(PK)
	@JoinColumn(name = "tx_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private KakaoSignTalkSendInfo kakaoSignTalkSendInfo;
	

	/* =============
	 * 응답 데이터 필드
	 ============= */
//	@NotEmpty
	@Length(max = 40)
	private String signed_data_uuid; //[bulk용 필드]서명 데이터의 UUID
	@NotEmpty
	@Length(max = 10)
	private String status;           //처리상태(PREPARE: 대기중=서명을 요청한 상태, COMPLETE: 서명완료=비밀번호를 정확히 입력하여 서명을 완료한 상태, EXPIRED: 타임아웃=처리마감시간 동안 서명을 완료하지 않은 상태)
	@NotEmpty
	@Length(max = 13)
	private Number request_at;       //서명 요청 날짜/시간
	@Length(max = 13)
	private Number viewed_at;        //서명 조회 날짜/시간. 조회 전에는 미제공
	@Length(max = 13)
	private Number completed_at;     //서명 완료 날짜/시간. 서명완료 시 제공
//	@NotEmpty
	@Length(max = 13)
	private Number expired_at;       //서명 만료 날짜/시간
	@Length(max = 200)
	private String payload;          //이용기관에서 서명 요청 시 보낸 payload 값
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
