package cokr.xit.modules.junittest.sample;

import java.util.LinkedHashMap;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import cokr.xit.code.CodeMapperFactory;
import cokr.xit.code.CodeMapperType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 
 * <ul>
 * <li>업무 그룹명: Enum클래스 코드 관리</li>
 * <li>설 명: CodeMapperFactory에 Enum Class 등록 및 꺼내는 방법 작성</li>
 * <li>참고 사이트: https://mangkyu.tistory.com/74</li>
 * <li>작성일: 2021. 10. 25. 오후 5:47:48
 * </ul>
 *
 * @author 박민규
 *
 */
@TestMethodOrder(OrderAnnotation.class)
class EnumCodeTestSample {
	
	private static CodeMapperFactory codeMapperFactory;
	

	@BeforeAll
	public static void init() {
		//CodeMapperFactory 초기화
		codeMapperFactory = new CodeMapperFactory(new LinkedHashMap<>());
	}

	
	@Test
	@Order(1)
	public void putTest() {
		//추가방식: Class명 => "TestCode"
		codeMapperFactory.put(TestCode.class);
		
		//추가방식: 사용자지정 => "TestCode2"
		codeMapperFactory.put("TestCode2", TestCode.class);
	}
	
	@Test 
	@Order(2)
	public void getTest() { 

		System.out.println("[GetCode => TestCode, GetType => Class]");
		codeMapperFactory.get(TestCode.class).forEach((row) -> System.out.println(row.getCode()+"="+row.getCodeNm()));
		System.out.println("[GetCode => TestCode, GetType => String]");
		codeMapperFactory.get("TestCode").forEach(row -> System.out.println(row.getCode()+"="+row.getCodeNm()));
		
		
		System.out.println("[GetCode => TestCode2]");
		codeMapperFactory.get("TestCode2").forEach(row -> System.out.println(row.getCode()+"="+row.getCodeNm()));
	}
	
	
	@RequiredArgsConstructor
	public enum TestCode implements CodeMapperType {

		TEST1("코드1")
		,TEST2("코드2");

		
		@Getter
		private final String codeNm;
		
		@Override
		public String getCode() {
			return this.name();
		}
	}

}
