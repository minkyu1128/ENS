package cokr.xit.modules.usermng.domain;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NhtTmplatManageRepositoryImpl implements NhtTmplatManageRepositoryCustom{

	private final JPAQueryFactory query;

}
