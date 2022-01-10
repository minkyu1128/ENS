package cokr.xit.modules.usermng.domain;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ElctrnNticSndngRepositoryImpl implements ElctrnNticSndngRepositoryCustom{

	private final JPAQueryFactory query;

}
