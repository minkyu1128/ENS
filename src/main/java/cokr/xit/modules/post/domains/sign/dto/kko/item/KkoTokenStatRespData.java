package cokr.xit.modules.post.domains.sign.dto.kko.item;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

/**
 * 카카오 토큰확인 응답데이터
 * @author 박민규
 */
@Data
public class KkoTokenStatRespData {
	
	@NotEmpty
	@Length(max = 40)
	private String tx_id;  //전자서명원문 접수번호

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
}
