package cokr.xit.modules.post.domains.bill.dto.nv.item;

import java.util.Map;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class NvSendReqData {

	@NotEmpty(message = "clientDocId(은)는 필수조건 입니다.")
	@Length(max = 100, message = "clientDocId(은)는 최대 100자 입니다.")
	private String clientDocId;                //이용기관에서 식별가능한 고유값
	
	@NotEmpty(message = "ci(은)는 필수조건 입니다.")
	@Length(max = 100, message = "ci(은)는 최대 100자 입니다.")
	private String ci;                         //사용자의 ci
	
	@NotEmpty(message = "title(은)는 필수조건 입니다.")
	@Length(max = 40, message = "title(은)는 최대 40자 입니다.")
	private String title;                      //전자문서 제목
	
	@NotEmpty(message = "message(은)는 필수조건 입니다.")
//	@Length(max = 1000, message = "message(은)는 최대 1000자 입니다.")
	private NvSendReqDataMessage message;      //원문 데이터
	
	@Length(max = 8, message = "validDuration(은)는 최대 8자 입니다.")
	private Long validDuration;                //고지서 열람 유효시간(초)
	
	@NotEmpty(message = "redirectUrl(은)는 필수조건 입니다.")
	@Length(max = 1000, message = "redirectUrl(은)는 최대 1000자 입니다.")
	private String redirectUrl;                //인증 후 리다이렉트할 URL
	
	@NotEmpty(message = "publishDocumentYn(은)는 필수조건 입니다.")
	@Length(max = 1, message = "publishDocumentYn(은)는 최대 1자 입니다.")
	private String publishDocumentYn;          //공인전자문서 유통정보 등록 필요 여부(y/n)
	
	@Length(max = 99, message = "documentHash(은)는 최대 99자 입니다.")
	private String documentHash;               //본문 해쉬값(공인전자주소 유통정보 등록 시 필수)
	
	@NotEmpty(message = "callCenterNo(은)는 필수조건 입니다.")
	@Length(max = 20, message = "callCenterNo(은)는 최대 20자 입니다.")
	private String callCenterNo;               //고객센터 전화번호
	
	@Length(max = 50, message = "orgId(은)는 최대 50자 입니다.")
	private String orgId;                      //하위 기관명(필요 시 네이버에서 발급)
	
	@NotEmpty(message = "authRequireYn(은)는 필수조건 입니다.")
	@Length(max = 1, message = "authRequireYn(은)는 최대 1자 입니다.")
	private String authRequireYn;              //고지서 열람 시 본인인증 사용 여부(y/n)
	
	private Map<String, Object> notification;  //알람 관련 설정값
}
