package cokr.xit.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.springframework.util.StringUtils;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RequireValidator {
	
	private Object obj;
	
	private List<String> message = new ArrayList<>();
	
	
	/**
	 * <pre>메소드 설명: 객체 검증
	 * 	-.검증을 통과하지 못한 필드의 메시지를 message 필드에 설정한다.
	 * </pre>
	 * @return RequireValidator 요청처리 후 응답객체
	 * @author: 박민규
	 * @date: 2021. 8. 6.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public RequireValidator validate() {
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<Object>> validate = validator.validate(this.obj);
		
		if(!validate.isEmpty()) {
			this.message = new ArrayList<String>(); 
			
			Iterator it = validate.iterator();
			while(it.hasNext()) {
				ConstraintViolation<Object> cv = (ConstraintViolation<Object>) it.next();
				this.message.add(cv.getMessage());
			}
		}
		
		
		return this;
	}
	
	
	/**
	 * <pre>메소드 설명: Exception 발생
	 * 	-.message가 null이 아닐 경우 Exception을 발생 시킨다.
	 * </pre>
	 *  void 요청처리 후 응답객체
	 * @author: 박민규
	 * @date: 2021. 8. 6.
	 */
	@SuppressWarnings("deprecation")
	public RequireValidator throwableException() {
		if(!StringUtils.isEmpty(this.message))
//			throw new CustomException(RESP_CODE.INVALID_PARAMETER, message.toString());
			throw new RuntimeException(String.format("유효성 검증 Fail::: %s", message.toString()));
		
		return this;
	}
	
	@Override
	public String toString() {
		if(!StringUtils.isEmpty(this.message))
			return message.toString();
		else
			return null;
	}
	
}
