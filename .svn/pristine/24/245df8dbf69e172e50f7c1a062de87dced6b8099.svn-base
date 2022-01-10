package cokr.xit.config;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.querydsl.jpa.impl.JPAQueryFactory;

/**
 * 
 * <ul>
 * <li>업무 그룹명: JpaQueryFactory Bean 등록</li>
 * <li>설 명: 프로젝트 어느 곳에서나 JPAQueryFactory 를 주입 받아 Querydsl을 사용 할 수 있게 함.</li>
 * <li>참조사이트: https://jojoldu.tistory.com/372</li>
 * <li>작성일: 2021. 10. 18. 오후 4:25:17
 * </ul>
 *
 * @author 박민규
 *
 */
@Configuration
public class QuerydslConfig {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Bean
	public JPAQueryFactory jpaQueryFactory() { 
		return new JPAQueryFactory(entityManager); 
	}
}
