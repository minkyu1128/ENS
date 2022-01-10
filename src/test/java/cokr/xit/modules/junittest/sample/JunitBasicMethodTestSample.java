package cokr.xit.modules.junittest.sample;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasValue;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.sameInstance;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import cokr.xit.modules.domain.SendMast;

/**
 * 
 * <ul>
 * <li>업무 그룹명: Junit 기본메소드 테스트</li>
 * <li>설 명: </li>
 * <li>작성일: 2021. 10. 25. 오후 5:49:07
 * </ul>
 *
 * @author 박민규
 *
 */
class JunitBasicMethodTestSample {

	/* ==========================================
	 * *.TDD 단위테스트(Unit Test) 작성의 필요성 (사이트: https://mangkyu.tistory.com/143)
	 *   -.단위 테스트(Unit Test): 하나의 모듈을 기준으로 독립적으로 진행되는 가장 작은 단위의 테스트
	 *   -.통합 테스트(Integration Test): 모듈을 통합하는 과정에서 모듈 간의 호환성을 확인하기 위해 수행되는 테스트
	 *   
	 * *.given/when/then 패턴 (사이트: https://mangkyu.tistory.com/144)
	 *  -.given(준비): 어떠한 데이터가 준비되었을 때
	 *  -.when(실행): 어떠한 함수를 실행하면
	 *  -.then(검증): 어떠한 결과가 나와야 한다.
	 * 
	 * *.Junit과 Mockito 기반의 Spring 단위 테스트 코드 작성법 (사이트: https://mangkyu.tistory.com/145)
	 *  -.Mockito: 개발자가 동작을 직접 제어할 수 있는 가짜(Mock) 객체를 지원하는 테스트 프레임워크
	 *  -.Mock 객체 의존성 주입
	 *    @Mock: Mock 객체를 만들어 반환해주는 어노테이션
	 *    @Spy: Stub하지 않은 메소드들은 원본 메소드 그대로 사용하는 어노테이션
	 *    @InjectMocks: @Mock 또는 @Spy로 생성된 가짜 객체를 자동으로 주입시켜주는 어노테이션
	 *  -.Stub 메소드
	 *    doReturn(): Mock 객체가 특정한 값을 반환해야 하는 경우
	 *    doNothing(): Mock 객체가 아무 것도 반환하지 않는 경우(void)
	 *    doThrow(): Mock 객체가 예외를 발생시키는 경우
	 *  -.Mockito와 Junit의 결합
	 *    : Junit4에서 Mockito를 활용하려면 @RunWith(MockitoJUnitRunner.class)를 붙여주어야 연동이 가능했으나
	 *      Junit5부터는 @ExtendWith(MockitoExtension.class)를 사용해야 결합이 가능하다.
	 ========================================== */
	
	@Test
	void testEquals() {
		Map<String, String> param1 = new HashMap<String, String>();
		Map<String, String> param2 = param1;
		assertEquals(param1, param2);	
		

		
		//만료시간(상대시간). [단위: sec]
		int hour = 24*30;
		Long extTime = 60L * 60L * hour;
		System.out.println("만료시간(단위: sec) => "+extTime);
		
		//만료시간(절대시간 -> 1970-01-01 00:00:00 부터 ~ 현재까지). [단위: sec]
		Date frstDate = new Date (1970, 1, 1, 0, 0, 0);	//2030-11-28 11:22:33 까지
		Date curDate = new Date (2021, 4, 2, 15, 0, 0);	//2030-11-28 11:22:33 까지
		Long extTime2 = curDate.getTime()-frstDate.getTime();
		Long extTime3 = curDate.UTC(2021, 4, 2, 15, 0, 0)-frstDate.UTC(1970, 1, 1, 0, 0, 0);
		System.out.println("만료시간(단위: sec) => "+(extTime2/1000));	//2021-04-01 0시0분0초 는 "1617202800"이 나와야 함.
		System.out.println("만료시간(단위: sec) => "+(extTime3/1000));
		Date date = new Date(1637283712000L);
//		date.setTime(1637283712);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		System.out.println("변환 =>"+ format.format(date));
//		1617202800
//		1624340958
	}
	@Test
	void testNotEquals() {
		assertNotEquals("haha", "abc");	
		System.out.println(String.format("%s", HttpStatus.INTERNAL_SERVER_ERROR.toString()));
		System.out.println(String.format("%s", HttpStatus.INTERNAL_SERVER_ERROR.name()));
		System.out.println(String.format("%s", HttpStatus.INTERNAL_SERVER_ERROR.value()));
	}
	@Test
	void testNull() {
		assertNull(null);
	}
	@Test
	void testNotNull() {
		String str = "abcd";
		assertNotNull(str);
	}
	@Test
	void testTrue() {
		Map<String, String> map = null;
		assertTrue(map == null);
	}
	@Test
	void testFalse() {
		Map<String, String> map = null;
		assertFalse(map != null);
	}
	@Test
	void testArrayEquals() {
		String names[] = {"test","haha"};
		String names2[] = {"test","haha"};
		assertArrayEquals(names, names2);
	}
	@Test
	void testTimeout() {
		//수행시간(timeout) 검증
		assertTimeout(Duration.ofMillis(1000), ()->Thread.sleep(500), "제한시간 내 수행실패");
	}
	@Test
	void testAssertThat() {
		String names[] = {"test","haha"};
		String names2[] = {"test","haha"};
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("param1", "value1");
		
		/* =====================================
		 * assertThat()을 이용하면 문장을 읽듯이 작성 할 수 있어 가독성을 높일 수 있다.
		 *  -.사이트참조
		 *    : https://effortguy.tistory.com/124
		 *    : https://blog.naver.com/PostView.naver?blogId=simpolor&logNo=221327833587
		 ===================================== */
		//Core(anything/describedAs/is/...)
		assertThat(2 + 1, is(3));       //2+1 is 3
		assertThat(names, is(names2));	//names is names2
		
		
		//Logical(allOf/anyOf/not)
		assertThat("Sample string.", is(not(startsWith("Test"))));	//"Sample string." is not startsWith "Test"
		assertThat("myValue", allOf(startsWith("my"), containsString("Val")));	//allOf 모두 성공해야 통과. "myValue" 문자열에 "my"로 시작하고 "Val"이 포함되어 있어야 성공
		assertThat("myValue", anyOf(startsWith("foo"), containsString("Val")));	//anyOf 하나 이상 성공해야 통과. "myValue" 문자열에 "foo"로 시작하거나 "Val"이 포함되어 있어야 성공
		
		
		//Object(equalTo/hasToString/instanceOf/isCompatibleType/notNullValue/nullValue/sameInstance/...)
		assertThat(2 + 1, equalTo(3));               //2+1 equal 3
		assertThat(names, equalTo(names2));	         //names equal names2
		assertThat("aaa", instanceOf(String.class)); //"aaa"의 데이터 타입이 String 클래스 타입이면..
		assertThat("aaa", sameInstance("hahaha"));  //"aaa"와 "hahaha"의 데이터 타입이 일치하면..
		
		
		//Beans(hasProperty)
		SendMast sendMast = SendMast.builder().sendMastId(1234L).build();
		assertThat(sendMast, hasProperty("sendMastId"));             //객체에 "sendMastId" 필드가 있으면..
		assertThat(sendMast, hasProperty("sendMastId", is(1234L)));  //객체의 "sendMastId" 필드 값이 1234L 이면..
		
		
		//Collections(array/hasEntry/hashKey/hasValue/hasItem/hasItems/hasItemInArray/...)
		assertThat(map, hasEntry("param1", "value1")); //key/value가 "param1"/"value1"인 엔트리를 가지고 있으면..
		assertThat(map, hasKey("param1"));	           //"param1"인 key를 가지고 있으면..
		assertThat(map, hasValue("value1"));           //"value1"인 value를 가지고 있으면..
		
		
		//Number(closeTo/greaterThan/greaterThanOrEqualTo/lessThan/lessThanOrEqualTo/...)
		assertThat(10, greaterThan(5));	          //10>5 이면..
		assertThat(10, greaterThanOrEqualTo(10)); //10>=10 이면..
		assertThat(10, lessThan(15));	          //10<15 이면..
		assertThat(10, lessThanOrEqualTo(10));	  //10<=10 이면..
		
		
		//Text(equalToIgnoringCase/containsString/endsWith/startsWith/...)
		assertThat("ABcdEF", equalToIgnoringCase("abcdef"));	//대소문자 무시
		assertThat("ABcdEF", containsString("cdE"));	//"cdE"를 포함하면..
		assertThat("ABcdEF", endsWith("dEF"));	//"dEF"로 끝나면..
		assertThat("ABcdEF", startsWith("AB"));	//"AB"로 시작하면..
		
		
	}


}
