package cokr.xit.modules.post.domains.sign.dto.kko.item;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

/**
 * 카카오 인증톡전송 요청데이터
 * @author 박민규
 */
@Data
public class KkoSignSendReqData {
	
	@NotEmpty(message = "phone_no(은)는 필수조건 입니다.")
	@Length(max = 11, message = "phone_no(은)는 최대 11자 입니다.")
	private String phone_no;                           //휴대폰 번호
	
	@NotEmpty(message = "name(은)는 필수조건 입니다.")
	@Length(max = 20, message = "name(은)는 최대 20자 입니다.")
	private String name;                               //사용자 성명
	
	@NotEmpty(message = "birthday(은)는 필수조건 입니다.")
	@Length(max = 8, message = "birthday(은)는 최대 8자 입니다.")
	private String birthday;                           //생년월일(YYYYMMDD)
	
	@NotEmpty(message = "expires_in(은)는 필수조건 입니다.")
	@Length(max = 8, message = "expires_in(은)는 최대 8자 입니다.")
	private int expires_in;                         //처리마감시간(초 단위) => 권장값: 24시간(86400 sec), MAX: 6개월(약 15700000 sec)
	
	@NotEmpty(message = "call_center_no(은)는 필수조건 입니다.")
	@Length(max = 20, message = "call_center_no(은)는 최대 20자 입니다.")
	private String call_center_no;                     //고객센터 전화번호
	
	@NotEmpty(message = "title(은)는 필수조건 입니다.")
	@Length(max = 40, message = "title(은)는 최대 40자 입니다.")
	private String title;                              //메시지 명칭
	
	@Length(max = 20, message = "message_type(은)는 최대 20자 입니다.")
	private String message_type;                       //메시지 타입
	
	@Length(max = 500, message = "message(은)는 최대 500자 입니다.")
	private String message;                            //메시지
	
	@Length(max = 99, message = "data_hash(은)는 최대 99자 입니다.")
	private String data_hash;                          //열람정보 해시. 공인전자문서 유통정보 등록 시 필수
	
	@Length(max = 1, message = "allow_simple_registration(은)는 최대 1자 입니다.")
	private String allow_simple_registration;          //간편등록회원 허용여부(Y/N)
	
	@Length(max = 1, message = "verify_auth_name(은)는 최대 1자 입니다.")
	private String verify_auth_name;                   //사용자 성명 체크 여부(Y/N)
	
	@NotEmpty(message = "publish_certified_electronic_doc(은)는 필수조건 입니다.")
	@Length(max = 1, message = "publish_certified_electronic_doc(은)는 최대 1자 입니다.")
	private String publish_certified_electronic_doc;   //공인전자문서 유통정보 등록 여부(Y/N)
	
	@NotEmpty(message = "redirect_url(은)는 필수조건 입니다.")
	@Length(max = 1000, message = "redirect_url(은)는 최대 1000자 입니다.")
	private String redirect_url;                       //서명 완료한 사용자에게 보여줄 웹페이지 주소
	
	@Length(max = 200, message = "payload(은)는 최대 200자 입니다.")
	private String payload;                            //이용기관이 생성한 payload 값
	
	private int sub_org_id;                         //하위 이용기관명
	
	@Length(max = 10, message = "call_center_label(은)는 최대 10자 입니다.")
	private String call_center_label;                  //콜센터명
	
	@Length(max = 39, message = "tx_id(은)는 최대 39자 입니다.")
	private String tx_id;  //bulk 전송용 
	
	
	public String toQueryString() {
		return new StringBuffer()
				.append("phone_no=").append(this.phone_no)
				.append("&name=").append(this.name)
				.append("&birthday=").append(this.birthday)
				.append("&expires_in=").append(this.expires_in)
				.append("&call_center_no=").append(this.call_center_no)
				.append("&title=").append(this.title)
				.append("&message_type=").append(this.message_type)
				.append("&message=").append(this.message)
				.append("&data_hash=").append(this.data_hash)
				.append("&allow_simple_registration=").append(this.allow_simple_registration)
				.append("&verify_auth_name=").append(this.verify_auth_name)
				.append("&publish_certified_electronic_doc=").append(this.publish_certified_electronic_doc)
				.append("&redirect_url=").append(this.redirect_url)
				.append("&payload=").append(this.payload)
				.append("&sub_org_id=").append(this.sub_org_id)
				.append("&call_center_label=").append(this.call_center_label)
				.append("&tx_id=").append(this.tx_id)
				.toString();
	}
}
