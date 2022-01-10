package cokr.xit.modules.kkomydoc.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import cokr.xit.modules.domain.common.Error;
import lombok.Data;

@Data
public class OttRespDTO extends Error{
	@NotEmpty(message = "토큰 상태값(은)는 필수입력 입니다.")
	private String token_status;       //토큰 상태값(성공시 USED를 반환)
	@NotEmpty(message = "토큰만료일시(은)는 필수입력 입니다.")
	private Long token_expires_at;     //토큰 만료일시
	private Long token_used_at;        //토큰 사용일시. OTT검증을 수행 후 성공된 시간
	private Long doc_box_sent_at;      //송신 시간(문서발송 요청 성공 시간)
	private Long doc_box_received_at;  //수신 시간(내문서함에 문서 생성시간)
	private Long authenticated_at;     //열람 인증을 성공한 최초 시간
	private Long user_notified_at;     //알림톡 수신 시간
	@Size(max = 200, message = "payload(은)는 최대 200자 까지 입력 가능 합니다.")
	private String payload;            //이용기관이 생성한 payload 값
	private Long token_signed_at;      //토큰 서명일시. 유통정보 이용상품에 한해 제공
//	protected String error_code;       //에러 코드
//	protected String error_message;    //에러 메시지
}
