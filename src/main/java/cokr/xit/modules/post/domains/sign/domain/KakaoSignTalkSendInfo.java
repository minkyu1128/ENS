package cokr.xit.modules.post.domains.sign.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import cokr.xit.modules.domain.SendMast;
import lombok.Builder;
import lombok.Data;

//@Entity
@Data
@Builder
public class KakaoSignTalkSendInfo {

	/* =============
	 * 요청 데이터 필드
	 ============= */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long signTalkId;               //ID(PK)
	@ManyToOne
	@JoinColumn(name = "SEND_MAST_ID")
	private SendMast sendMast;
	@NotEmpty
	@Length(max = 11)
	private String phone_no;                           //휴대폰 번호
	@NotEmpty
	@Length(max = 20)
	private String name;                               //사용자 성명
	@NotEmpty
	@Length(max = 8)
	private String birthday;                           //생년월일(YYYYMMDD)
//	@NotEmpty
//	@Length(max = 8)
	private int expires_in;                         //처리마감시간(초 단위) => 권장값: 24시간(86400 sec), MAX: 6개월(약 15700000 sec)
	@NotEmpty
	@Length(max = 20)
	private String call_center_no;                     //고객센터 전화번호
	@NotEmpty
	@Length(max = 40)
	private String title;                              //메시지 명칭
	@Length(max = 20)
	private String message_type;                       //메시지 타입
	@Length(max = 500)
	private String message;                            //메시지
	@Length(max = 99)
	private String data_hash;                          //열람정보 해시. 공인전자문서 유통정보 등록 시 필수
	@Length(max = 1)
	private String allow_simple_registration;          //간편등록회원 허용여부(Y/N)
	@Length(max = 1)
	private String verify_auth_name;                   //사용자 성명 체크 여부(Y/N)
	@NotEmpty
	@Length(max = 1)
	private String publish_certified_electronic_doc;   //공인전자문서 유통정보 등록 여부(Y/N)
	@NotEmpty
	@Length(max = 1000)
	private String redirect_url;                       //서명 완료한 사용자에게 보여줄 웹페이지 주소
	@Length(max = 200)
	private String payload;                            //이용기관이 생성한 payload 값(이용기관 자료의 식별자(pk)를 넣어서 사용 가능)
	private int sub_org_id;                            //하위 이용기관명
	@Length(max = 10)
	private String call_center_label;                  //콜센터명

	
	/* =============
	 * 응답 데이터 필드
	 ============= */
	@Length(max = 40)
	private String txId;     //전자서명원문 접수번호. 이용기관 서버에서 카카오페이 인증 서버로 정상 접수된 전자서명의 원문의 식별 값(Transaction ID)
	@NotEmpty
	@Length(max = 1)
	private String result;    //처리결과. 정상접수처리시 "Y", 그외 "N"
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
	
	/* =============
	 * 최종서명확인 필드
	 ============= */
	@Length(min = 14, max = 18) 
	private String frstSignDe; //최초 서명일시(인증상태확인 응답 completed_at )
	@Length(min = 14, max = 18) 
	private String frstReadDe; //최초 열람일시(토큰유효성검증 완료시점)
}
