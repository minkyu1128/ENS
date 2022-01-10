package cokr.xit.modules.kkomydoc.domain;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StatusKkoMydocRepositoryImpl implements StatusKkoMydocRepositoryCustom{

	private final JPAQueryFactory query;

}
