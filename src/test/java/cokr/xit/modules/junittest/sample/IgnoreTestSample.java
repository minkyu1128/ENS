package cokr.xit.modules.junittest.sample;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * 
 * <ul>
 * <li>업무 그룹명: 무시하기 테스트</li>
 * <li>설 명: @Disabled 애너테이션으로 Method 및 Class 레벨에 선언하여 실행되지 않도록 할 수 있다.</li>
 * <li>참고 사이트: https://ko.myservername.com/junit-ignore-test-case</li>
 * <li>작성일: 2021. 10. 25. 오후 5:49:07
 * </ul>
 *
 * @author 박민규
 *
 */
//@Disabled
class IgnoreTestSample {
	 @Test 
	 public void test_JUnit1() { 
		 System.out.println("@Test – this is test case 1"); 
	 } 
	 @Test
	 @Disabled(value = "Junit2는 테스트 안할거얌")
	 public void test_JUnit2() { 
		 System.out.println("@Test – this is test case 2"); 
	 } 
	 @Test 
	 public void test_JUnit3() { 
		 System.out.println("@Test – this is test case 3"); 
	 } 

}
