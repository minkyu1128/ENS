package cokr.xit.modules.post.domains.sign.dto.kko.item;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

/**
 * 카카오 전자서명확인 응답데이터
 * @author 박민규
 */
@Data
public class KkoSignStatRespData {
	
	@NotEmpty
	@Length(max = 40)
	private String tx_id;  //전자서명원문 접수번호

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
	
	@NotEmpty
	@Length(max = 13)
	private Number expired_at;       //서명 만료 날짜/시간
	
	@Length(max = 200)
	private String payload;          //이용기관에서 서명 요청 시 보낸 payload 값
	
	@Length(max = 13)
	private Number org_notified_at;  //서명결과 최초 인지시각
	
	private String errcode;   //에러코드
	
	private String errmsg;    //에러메시지
}
