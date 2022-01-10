package cokr.xit.code;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CodeMapperFactory {

	private Map<String, List<CodeMapperValue>> factory;
	
	/**
	 * <pre>메소드 설명: CodeMapperType을 구현한 Enum을 Factory에 추가하는 함수</pre>
	 * @param key
	 * @param e void 요청처리 후 응답객체
	 * @author: 박민규
	 * @date: 2021. 10. 28.
	 */
	public void put(Class<? extends CodeMapperType> e) {
		this.put(e.getSimpleName(), e);
	}
	/**
	 * <pre>메소드 설명: CodeMapperType을 구현한 Enum을 Factory에 추가하는 함수</pre>
	 * @param key
	 * @param e void 요청처리 후 응답객체
	 * @author: 박민규
	 * @date: 2021. 10. 28.
	 */
	public void put(String key, Class<? extends CodeMapperType> e) {
		factory.put(key, this.toEnumValue(e));
	}
	
	/**
	 * <pre>메소드 설명: Factory에 추가된 Enum을 조회하는 함수</pre>
	 * @param e
	 * @return List<CodeMapperValue> 요청처리 후 응답객체
	 * @author: 박민규
	 * @date: 2021. 10. 28.
	 */
	public List<CodeMapperValue> get(Class<? extends CodeMapperType> e){
		return this.get(e.getSimpleName());
	}
	/**
	 * <pre>메소드 설명: Factory에 추가된 Enum을 조회하는 함수</pre>
	 * @param key
	 * @return List<CodeMapperValue> 요청처리 후 응답객체
	 * @author: 박민규
	 * @date: 2021. 10. 28.
	 */
	public List<CodeMapperValue> get(String key){
		return factory.get(key);
	}
	
	
	/**
	 * <pre>메소드 설명: Enum의 내용들을 List로 변환하는 함수</pre>
	 * @param e
	 * @return List<CodeMapperValue> 요청처리 후 응답객체
	 * @author: 박민규
	 * @date: 2021. 10. 28.
	 */
	private List<CodeMapperValue> toEnumValue(Class<? extends CodeMapperType> e){
		return Arrays.stream(e.getEnumConstants())	//CodeMapperType을 구현한 열거형 상수(code/codeNm SET)코드를
				.map(CodeMapperValue::new)			//CodeMapperValue 클래스로 생성하여
				.collect(Collectors.toList());      //List<CodeMapperValue>로 반환
	}
	
}
