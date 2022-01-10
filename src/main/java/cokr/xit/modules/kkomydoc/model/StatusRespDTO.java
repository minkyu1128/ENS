package cokr.xit.modules.kkomydoc.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import cokr.xit.modules.domain.common.Error;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class StatusRespDTO extends Error{
	@NotEmpty(message = "진행 상태(은)는 필수조건 입니다.")
	private String doc_box_status;     //진행 상태
	private Long doc_box_sent_at;      //송신 시간(문서발송 요청 성공 시간)
	private Long doc_box_received_at;  //수신 시간(내문서함에 문서 생성시간)
	private Long authenticated_at;     //열람 인증을 성공한 최초 시간
	private Long token_used_at;        //OTT 검증을 성공한 최초 시간
	private Long doc_box_read_at;      //최초 열람 시간
	private Long user_notified_at;     //알림톡 수신 시간
	private Long doc_distribution_received_at;     //???문서에 없는 항목이며... 2021.12.06 발견함.
	@Size(max = 200, message = "payload(은)는 최대 200자 까지 입력 가능 합니다.")
	private String payload;            //이용기관이 생성한 payload 값
	
}
