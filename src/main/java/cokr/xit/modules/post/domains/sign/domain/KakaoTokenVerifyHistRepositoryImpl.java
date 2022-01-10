package cokr.xit.modules.post.domains.sign.domain;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class KakaoTokenVerifyHistRepositoryImpl implements KakaoTokenVerifyHistRepositoryCustom {

	private final JPAQueryFactory query;
	

}
