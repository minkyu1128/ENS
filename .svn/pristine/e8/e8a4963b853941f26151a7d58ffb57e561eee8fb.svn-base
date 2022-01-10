package cokr.xit.modules.kkomydoc.model.child;

import org.hibernate.validator.constraints.Length;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Property {
	private String link;                   //본인인증 후 사용자에게 보여줄 웹페이지 주소
	private String payload;                //이용기관이 생성한 payload 값
	private String message;                //메시지
	private String cs_number;              //고객센터 전화번호
	private String cs_name;                //고객센터 명
	@Length(max = 40, message = "external_document_uuid(은)는 최대 40자까지 입력 가능 합니다.")
	private String external_document_uuid; //(bulk용필드)문서발송 대상자와 문서식별번호를 매칭하는 값으로 사용. 반드시 Unique 하게 생성 후 전송. 하나의 Request에 대해서만 Unique 체크가 가능.
}
