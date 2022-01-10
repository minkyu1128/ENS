package cokr.xit.modules.nicedici.domain;

import static cokr.xit.modules.nicedici.domain.QNiceCiMng.niceCiMng;
import static cokr.xit.modules.nicedici.domain.QNiceJidMng.niceJidMng;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NiceCiMngRepositoryImpl implements NiceCiMngRepositoryCustom{

	private final JPAQueryFactory query;

	@Override
	public String findCiByJid(String jid) {
		
		return query.select(niceCiMng.ci)
				.from(niceCiMng)
				.innerJoin(niceJidMng).on(niceCiMng.niceJidMng.jidMngId.eq(niceJidMng.jidMngId))
				.where(
						niceJidMng.Jid.eq(jid)
						.and(niceCiMng.ci.isNotNull())
						.and(niceJidMng.error.isNull())
						)
				.fetchOne();
	}

}
