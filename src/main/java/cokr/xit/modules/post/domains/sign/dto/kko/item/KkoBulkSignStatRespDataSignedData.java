package cokr.xit.modules.post.domains.sign.dto.kko.item;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

/**
 * 카카오 인증톡 상태응답 데이터 서명상태
 * @author 박민규
 */
@Data
public class KkoBulkSignStatRespDataSignedData {

	@NotEmpty(message = "signed_data_uuid(은)는 필수조건 입니다.")
	@Length(max = 40, message = "signed_data_uuid(은)는 최대 40자 입니다.")
	private String signed_data_uuid; //서명 데이터의 UUID. 서명 원문에 대한 식별번호. 이용기관에서 본 값을 사용할 용도는 없으며, 무시해도 되는 값
	
	@NotEmpty(message = "status(은)는 필수조건 입니다.")
	@Length(max = 10, message = "status(은)는 최대 10자 입니다.")
	private String status;           //처리상태(PREPARE: 대기중. 서명을 요청한 상태, COMPLETE: 서명완료. 비밀번호를 정확히 입력하여 서명을 완료한 상태, EXPIRED: 타임아웃. 처리마감시간 동안 섬여을 완료하지 않은 상태)
	
	@NotEmpty(message = "request_at(은)는 필수조건 입니다.")
	@Length(max = 13, message = "request_at(은)는 최대 13자 입니다.")
	private Number request_at;       //서명 요청 날짜/시간. 서명 요청이 인증서버에 등록된 시각
	
	@Length(max = 13, message = "viewed_at(은)는 최대 13자 입니다.")
	private Number viewed_at;        //서명 조회 날짜/시간. 조회 전에는 미제공. 사용자가 받은 카카오톡 메시지의 "확인하기" 버튼을 클릭하여 서명 내용을 최초로 확인한 시각
	
	@Length(max = 13, message = "completed_at(은)는 최대 13자 입니다.")
	private Number completed_at;     //서명 완료 날짜/시간. 서명완료 시 제공. 사용자가 서명을 완료한 시각. 서명완료 시에 제공
	
	@NotEmpty(message = "expired_at(은)는 필수조건 입니다.")
	@Length(max = 13, message = "expired_at(은)는 최대 13자 입니다.")
	private Number expired_at;       //서명 만료 날짜/시간. 처리마감시간이 경과되어 서명 요청이 마감된 시각.
	
	@Length(max = 200, message = "payload(은)는 최대 200자 입니다.")
	private String payload;          //이용기관에서 서명 요청 시 보낸 payload 값. 이용기관에서 서명 요청 시 payload 파라미터에 보낸 값. 서명 요청 시 보내지 않았을 경우 내려오지 않음.
	
	@Length(max = 13, message = "org_notiﬁed_at(은)는 최대 13자 입니다.")
	private Number org_notified_at;   //이용기관이 서명 검증한 날짜/시간. 이용기관이 검증 API를 호출한 시간.
}
