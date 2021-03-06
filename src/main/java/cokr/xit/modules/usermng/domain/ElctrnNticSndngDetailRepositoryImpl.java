package cokr.xit.modules.usermng.domain;

import static cokr.xit.modules.usermng.domain.QElctrnNticSndngDetail.elctrnNticSndngDetail;

import java.util.List;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ElctrnNticSndngDetailRepositoryImpl implements ElctrnNticSndngDetailRepositoryCustom{

	private final JPAQueryFactory query;

	@Override
	public List<ElctrnNticSndngDetail> findAllByElctrnNticSndng(ElctrnNticSndng elctrnNticSndng) {

		return query.select(Projections.fields(ElctrnNticSndngDetail.class
				, elctrnNticSndngDetail.elctrnNticSndngDetailId
				, elctrnNticSndngDetail.elctrnNticSndng
				, elctrnNticSndngDetail.signguCode
				, elctrnNticSndngDetail.mainCode
				, Expressions.stringTemplate("ECL_DECRYPT({0})", elctrnNticSndngDetail.ihidnum).as("ihidnum")
				, elctrnNticSndngDetail.cnDetail
				, elctrnNticSndngDetail.mobilePageCn
				, elctrnNticSndngDetail.create
				, elctrnNticSndngDetail.update
				, elctrnNticSndngDetail.external_document_uuid))
				.from(elctrnNticSndngDetail)
				.where(elctrnNticSndngDetail.elctrnNticSndng.eq(elctrnNticSndng))
				.fetch();
	}

	@Override
	public List<String> findExternalDocumentUuidByElctrnNticSndng(ElctrnNticSndng sndng) {
		return query.select(elctrnNticSndngDetail.external_document_uuid)
				.from(elctrnNticSndngDetail)
				.where(elctrnNticSndngDetail.elctrnNticSndng.eq(sndng))
				.fetch();
	}

}
