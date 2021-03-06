package cokr.xit.modules.kkomydoc.domain;

import static cokr.xit.modules.kkomydoc.domain.QSendDetailKkoMydoc.sendDetailKkoMydoc;
import static cokr.xit.modules.kkomydoc.domain.QSendResultKkoMydoc.sendResultKkoMydoc;

import java.util.List;

import org.springframework.util.StringUtils;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import cokr.xit.modules.kkomydoc.model.StatusRespDTO;
import cokr.xit.modules.kkomydoc.model.child.Documents;
import cokr.xit.utils.CmmnUtil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SendDetailKkoMydocRepositoryImpl implements SendDetailKkoMydocRepositoryCustom {

	private final JPAQueryFactory query;

	@Override
	public Long countSuccessBySendMastId(Long sendMastId) {

		return query.selectFrom(sendDetailKkoMydoc)
				.innerJoin(sendResultKkoMydoc).on(sendDetailKkoMydoc.externalDocumentUuid.eq(sendResultKkoMydoc.externalDocumentUuid))
				.where(
						sendResultKkoMydoc.error.errorCode.isNull()	//에러코드 값이 비어있고..
						.and(sendResultKkoMydoc.sendMast.sendMastId.eq(sendMastId))	//발송마스터ID가 일치하는..
						)
				.fetchCount();
	}


	@Override
	public Long modifyFrstTokenVerifyDtByDocumentBinderUuid(Long tokenUsedAt, String documentBinderUuid) {
		try {
			//"최초토큰검증일시"가 등록되지 않은 자료 조회
			SendDetailKkoMydoc vo = query.select(sendDetailKkoMydoc)
					.from(sendDetailKkoMydoc)
					.innerJoin(sendResultKkoMydoc).on(sendDetailKkoMydoc.externalDocumentUuid.eq(sendResultKkoMydoc.externalDocumentUuid))
					.where(
							sendDetailKkoMydoc.frstTokenUsedDt.isNull()	//최초토큰검증일시 값이 비어있고..
							.and(sendResultKkoMydoc.documentBinderUuid.eq(documentBinderUuid))	//문서식별번호가 일치하는..
							)
					.fetchOne();

			//"최초토큰검증일시" update
			if(vo!=null) {
				vo.setTokenVerifyDt(tokenUsedAt);
				return 1L;
			}

			return 0L;
		} catch (Exception e) {
			e.printStackTrace();
			return -1L;
		}
	}


	@SuppressWarnings("deprecation")
	@Override
	public Long modifyStatusInfoByDocumentBinderUuid(StatusRespDTO statusRespDTO, String documentBinderUuid) {
		//Error이면 stop..
		if(!StringUtils.isEmpty(statusRespDTO.getError_code()))
			return -1L;

		SendDetailKkoMydoc vo = query.select(sendDetailKkoMydoc)
				.from(sendDetailKkoMydoc)
				.innerJoin(sendResultKkoMydoc).on(sendDetailKkoMydoc.externalDocumentUuid.eq(sendResultKkoMydoc.externalDocumentUuid))
				.where(
						sendResultKkoMydoc.documentBinderUuid.eq(documentBinderUuid)
						)
				.fetchOne();

		//"상태정보" update
		if(vo==null)
			return 0L;
		else
			vo.setStatusInfo(statusRespDTO);

		return 1L;
	}


	@Override
	public Long modifyStatusInfoByDocumentBinderUuid(StatusKkoMydoc statusKkoMydoc) {
		if(statusKkoMydoc == null)
			return -1L;

		Long cnt = query.update(sendDetailKkoMydoc)
			.set(sendDetailKkoMydoc.docBoxStatus       , statusKkoMydoc.getDocBoxStatus       ())
			.set(sendDetailKkoMydoc.docBoxSentDt       , CmmnUtil.absSecTimeToDate(statusKkoMydoc.getDocBoxSentAt()    , "yyyyMMddHHmmss"))
			.set(sendDetailKkoMydoc.docBoxReceivedDt   , CmmnUtil.absSecTimeToDate(statusKkoMydoc.getDocBoxReceivedAt(), "yyyyMMddHHmmss"))
			.set(sendDetailKkoMydoc.frstAuthenticatedDt, CmmnUtil.absSecTimeToDate(statusKkoMydoc.getAuthenticatedAt() , "yyyyMMddHHmmss"))
			.set(sendDetailKkoMydoc.frstTokenUsedDt    , CmmnUtil.absSecTimeToDate(statusKkoMydoc.getTokenUsedAt()     , "yyyyMMddHHmmss"))
			.set(sendDetailKkoMydoc.frstDocBoxReadDt   , CmmnUtil.absSecTimeToDate(statusKkoMydoc.getDocBoxReadAt()    , "yyyyMMddHHmmss"))
			.set(sendDetailKkoMydoc.userNotifiedDt     , CmmnUtil.absSecTimeToDate(statusKkoMydoc.getUserNotifiedAt()  , "yyyyMMddHHmmss"))
			.where(
					//in 표현식
	//				sendDetailKkoMydoc.externalDocumentUuid.in(
	//							query.select(sendResultKkoMydoc.externalDocumentUuid)
	//							.from(sendResultKkoMydoc)
	//							.where(sendResultKkoMydoc.documentBinderUuid.eq(statusKkoMydoc.getDocumentBinderUuid()))
	//						)
					//exists 표현식
					JPAExpressions.selectFrom(sendResultKkoMydoc)
					.where(
							sendDetailKkoMydoc.externalDocumentUuid.eq(sendResultKkoMydoc.externalDocumentUuid)
							.and(sendResultKkoMydoc.documentBinderUuid.eq(statusKkoMydoc.getDocumentBinderUuid())))
							.exists()
					)
			.execute();

		return cnt;
	}


	@Override
	public SendDetailKkoMydoc findByDocumentBinderUuid(String documentBinderUuid) {
		return query.selectFrom(sendDetailKkoMydoc)
				.innerJoin(sendResultKkoMydoc).on(sendDetailKkoMydoc.externalDocumentUuid.eq(sendResultKkoMydoc.externalDocumentUuid))
				.where(sendResultKkoMydoc.documentBinderUuid.eq(documentBinderUuid))
				.fetchOne();
	}


	@Override
	public List<Documents> findSendRespDetailsBySendMastId(Long sendMastId) {
		return query.select(Projections.constructor(Documents.class,
						sendResultKkoMydoc.externalDocumentUuid.as("external_document_uuid")
						,sendResultKkoMydoc.documentBinderUuid.as("document_binder_uuid")
						,sendDetailKkoMydoc.payload
						,sendResultKkoMydoc.error.errorCode.as("error_code")
						,sendResultKkoMydoc.error.errorMessage.as("error_message")
						))

				.from(sendDetailKkoMydoc)
				.innerJoin(sendResultKkoMydoc).on(
						sendDetailKkoMydoc.sendMast.sendMastId.eq(sendResultKkoMydoc.sendMast.sendMastId)
						.and(sendDetailKkoMydoc.externalDocumentUuid.eq(sendResultKkoMydoc.externalDocumentUuid))
						)
				.where(sendDetailKkoMydoc.sendMast.sendMastId.eq(sendMastId))
				.fetch();
	}



	@Override
	public List<Long> findSendMastIdByExternalDocumentUuid(List<String> externalDocumentUuidList) {
		return query.select(sendDetailKkoMydoc.sendMast.sendMastId)
				.from(sendDetailKkoMydoc)
				.where(inExternalDocumentUuid(externalDocumentUuidList))
				.groupBy(sendDetailKkoMydoc.sendMast.sendMastId)
				.fetch();
	}
	private BooleanBuilder inExternalDocumentUuid(List<String> externalDocumentUuidList) {
		BooleanBuilder builder = new BooleanBuilder();

		for(String externalDocumentUuid : externalDocumentUuidList) {
			builder.or(sendDetailKkoMydoc.externalDocumentUuid.eq(externalDocumentUuid));	//required
		}

		return builder;
	}

}
