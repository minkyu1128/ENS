package cokr.xit.modules.domain;

import static cokr.xit.modules.domain.QSendMast.sendMast;
import static cokr.xit.modules.kkomydoc.domain.QSendDetailKkoMydoc.sendDetailKkoMydoc;

import java.time.LocalDateTime;

import com.querydsl.jpa.impl.JPAQueryFactory;

import cokr.xit.modules.common.code.StatCd;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SendMastRepositoryImpl implements SendMastRepositoryCustom{

	private final JPAQueryFactory query;

//	@Transactional
	public Long modifyStatCdAndReadCntBySendMastId(Long sendMastId) {
		Long readCnt = query.select()
				.from(sendDetailKkoMydoc)
				.where(
						sendDetailKkoMydoc.sendMast.sendMastId.eq(sendMastId)
						.and(sendDetailKkoMydoc.frstDocBoxReadDt.isNotNull())	//열람한 자료가 있으면..
						)
				.fetchCount();


		//발송마스터 상태 update
		SendMast mast = query.selectFrom(sendMast)
				.where(sendMast.sendMastId.eq(sendMastId))
				.fetchOne();
		if(mast == null)
			return 0L;
		if(mast.getCloseDt() == null || mast.getCloseDt().isBefore(LocalDateTime.now())) {	//마감일시가 경과하지 않았을때..
			if(readCnt.intValue() > 0)	//발송 N건 중 1건 이상 열람 되었으면..
				mast.setCurStatus(StatCd.open, readCnt.intValue());	//"열람중" 상태로 전환
		} else	{ //마감일시가 경과 했으면...
			mast.setCurStatus(StatCd.close, readCnt.intValue());	//"조회마감"상태로 전환
		}

		return 1L;
	}


}
