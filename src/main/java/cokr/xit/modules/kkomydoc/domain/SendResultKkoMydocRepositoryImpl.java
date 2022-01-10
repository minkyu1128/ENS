package cokr.xit.modules.kkomydoc.domain;

import static cokr.xit.modules.kkomydoc.domain.QSendResultKkoMydoc.sendResultKkoMydoc;

import java.util.List;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class SendResultKkoMydocRepositoryImpl implements SendResultKkoMydocRepositoryCustom{

	private final JPAQueryFactory query;

	@Override
	public List<String> findDocumentBinderUuidBySendMastId(Long sendMastId) {
		return query.from(sendResultKkoMydoc)
				.select(sendResultKkoMydoc.documentBinderUuid)
				.where(
						sendResultKkoMydoc.sendMast.sendMastId.eq(sendMastId)
						.and(sendResultKkoMydoc.documentBinderUuid.isNotNull())
						)
				.fetch();
	}


}
