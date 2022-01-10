package cokr.xit.modules.junittest.sample;

import static org.junit.jupiter.api.Assertions.assertTimeout;

import java.time.Duration;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

/**
 * 
 * <ul>
 * <li>업무 그룹명: 타임아웃 테스트</li>
 * <li>설 명: assertTimeout() 메서드로 수행시간(timeout)을 제한 할 수 있다.</li>
 * <li>작성일: 2021. 10. 25. 오후 5:49:07
 * </ul>
 *
 * @author 박민규
 *
 */
@TestMethodOrder(OrderAnnotation.class)
class TimeoutTestSample {
	 @Test 
	 public void test_JUnit1() { 
		 System.out.println("@Test – this is test case 1"); 
		 
		 assertTimeout(Duration.ofMillis(1000), () -> Thread.sleep(2000), "제한시간 내 수행실패");	//fail(수행시간 1초 초과)
	 }
	 @Test 
	 public void test_JUnit2() { 
		 System.out.println("@Test – this is test case 2"); 
		 
		 assertTimeout(Duration.ofMillis(1000), () -> Thread.sleep(500) );	//success(수행시간 1초 미만)
	 }

}
