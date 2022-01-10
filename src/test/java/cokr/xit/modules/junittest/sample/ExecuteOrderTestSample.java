package cokr.xit.modules.junittest.sample;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

/**
 * 
 * <ul>
 * <li>업무 그룹명: 실행순서 테스트</li>
 * <li>설 명: @TestMethodOrder, @Order 애너테이션으로 실행순서를 설정 할 수 있다.</li>
 * <li>참고 사이트: https://ko.myservername.com/junit-test-execution-order</li>
 * <li>작성일: 2021. 10. 25. 오후 5:49:07
 * </ul>
 *
 * @author 박민규
 *
 */
@TestMethodOrder(OrderAnnotation.class)
class ExecuteOrderTestSample {
	 @Test 
	 public void test_JUnit1() { 
		 System.out.println("@Test – this is test case 1"); 
	 } 
	 @Test 
	 @Order(1)
	 public void test_JUnit2() { 
		 System.out.println("@Test – this is test case 2"); 
	 } 
	 @Test 
	 @Order(2)
	 public void test_JUnit3() { 
		 System.out.println("@Test – this is test case 3"); 
	 } 

}
