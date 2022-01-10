package cokr.xit.modules.kkomydoc.domain;

import java.util.Optional;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import static cokr.xit.modules.kkomydoc.domain.QNotiDetailsKkoMydoc.notiDetailsKkoMydoc;
import static cokr.xit.modules.kkomydoc.domain.QSendDetailKkoMydoc.sendDetailKkoMydoc;
import static cokr.xit.modules.kkomydoc.domain.QSendResultKkoMydoc.sendResultKkoMydoc;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NotiDetailsKkoMydocRepositoryImpl implements NotiDetailsKkoMydocRepositoryCustom{

	private final JPAQueryFactory query;

	@Override
	public Optional<NotiDetailsKkoMydoc> findByDocumentBinderUuidAndExternalDocumentUuid(String documentBinderUuid,
			String externalDocumentUuid) {
		
		return Optional.ofNullable(query.selectFrom(notiDetailsKkoMydoc)
				.innerJoin(sendDetailKkoMydoc).on(notiDetailsKkoMydoc.sendMast.sendMastId.eq(sendDetailKkoMydoc.sendMast.sendMastId)
						.and(notiDetailsKkoMydoc.payload.eq(sendDetailKkoMydoc.payload)))
				.innerJoin(sendResultKkoMydoc).on(sendDetailKkoMydoc.sendMast.sendMastId.eq(sendResultKkoMydoc.sendMast.sendMastId)
						.and(sendDetailKkoMydoc.externalDocumentUuid.eq(sendResultKkoMydoc.externalDocumentUuid)))
				.where(hasNotiDetails(documentBinderUuid, externalDocumentUuid))
				.fetchOne());
	}
	
	private BooleanBuilder hasNotiDetails(String documentBinderUuid, String externalDocumentUuid) {
		BooleanBuilder builder = new BooleanBuilder();
		
		builder.and(sendResultKkoMydoc.documentBinderUuid.eq(documentBinderUuid));	//required
		
		if(externalDocumentUuid != null)
			builder.and(sendResultKkoMydoc.externalDocumentUuid.eq(externalDocumentUuid));
		
		
		return builder;
		
	}

}
