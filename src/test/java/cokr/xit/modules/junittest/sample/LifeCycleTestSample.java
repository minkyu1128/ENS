package cokr.xit.modules.junittest.sample;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * 
 * <ul>
 * <li>업무 그룹명: 라이프사이클</li>
 * <li>설 명: BeforeAll => BeforeEach & Test & AfterEach => AfterAll 순으로 동작</li>
 * <li>참고 사이트: https://ko.myservername.com/list-junit-annotations</li>
 * <li>작성일: 2021. 10. 25. 오후 5:47:48
 * </ul>
 *
 * @author 박민규
 *
 */
class LifeCycleTestSample {
	 @BeforeAll 
	 public static void preClass() { 
		 System.out.println("@BeforeAll – the annotated method runs once before all other methods execute");
	 } 
	 @BeforeEach 
	 public void setUp() { 
		 System.out.println("_______________________________________________________ "); 
		 System.out.println("@BeforeEach – the annotated method executes before each test "); 
	 }
	 @Test 
	 public void test_JUnit1() { 
		 System.out.println("@Test – this is test case 1"); 
	 } 
	 @Test 
	 public void test_JUnit2() { 
		 System.out.println("@Test – this is test case 2"); 
	 } 
	 @Test 
	 public void test_JUnit3() { 
		 System.out.println("@Test – this is test case 3"); 
	 } 
	 @AfterEach 
	 public void tearDown() { 
		 System.out.println("@AfterEach – the annotated method executes after each test executes"); 
		 System.out.println("_______________________________________________________ "); 
	 } 
	 @AfterAll 
	 public static void postClass() { 
		 System.out.println("@AfterAll – the annotated method runs once after all other methods execute"); 
	 }

}
