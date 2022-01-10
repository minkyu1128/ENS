package cokr.xit.modules.kkomydoc.model.child;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Receiver {
	private String ci;                       //받는 이 ci
	private String phone_number;             //받는 이 전화번호
	private String name;                     //받는 이 이름
	private String birthday;                 //받는 이 생년월일(YYYYMMDD 형식)
	private Boolean is_required_verify_name; //성명 검증 옵션. CI 전송 시 생략 가능
}
