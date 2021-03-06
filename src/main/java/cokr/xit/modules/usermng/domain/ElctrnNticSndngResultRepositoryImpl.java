package cokr.xit.modules.usermng.domain;

import static cokr.xit.modules.kkomydoc.domain.QSendDetailKkoMydoc.sendDetailKkoMydoc;
import static cokr.xit.modules.kkomydoc.domain.QSendResultKkoMydoc.sendResultKkoMydoc;
import static cokr.xit.modules.usermng.domain.QElctrnNticSndng.elctrnNticSndng;
import static cokr.xit.modules.usermng.domain.QElctrnNticSndngDetail.elctrnNticSndngDetail;
import static cokr.xit.modules.usermng.domain.QElctrnNticSndngResult.elctrnNticSndngResult;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import cokr.xit.modules.usermng.domain.embeded.FieldCreate;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ElctrnNticSndngResultRepositoryImpl implements ElctrnNticSndngResultRepositoryCustom{

	private final JPAQueryFactory query;

	@Override
	public List<ElctrnNticSndngResult> findAllByElctrnNticSndngId(String elctrnNticSndngId) {

		//인증톡 발송상세 목록 조회
		List<Tuple> list = query.select(sendDetailKkoMydoc.docBoxSentDt
										,sendDetailKkoMydoc.payload
										,sendDetailKkoMydoc.externalDocumentUuid
										,sendDetailKkoMydoc.frstTokenUsedDt
										,sendDetailKkoMydoc.frstDocBoxReadDt
										,sendResultKkoMydoc.documentBinderUuid
										,sendResultKkoMydoc.error.errorCode
										,sendResultKkoMydoc.error.errorMessage
				)
				.from(sendDetailKkoMydoc)
				.leftJoin(sendResultKkoMydoc)
					.on(sendResultKkoMydoc.externalDocumentUuid.eq(sendDetailKkoMydoc.externalDocumentUuid)
							.and(sendResultKkoMydoc.sendMast.sendMastId.eq(sendDetailKkoMydoc.sendMast.sendMastId))
							)
				.innerJoin(elctrnNticSndngDetail)
					.on(elctrnNticSndngDetail.mainCode.eq(sendDetailKkoMydoc.payload)
							.and(elctrnNticSndngDetail.external_document_uuid.eq(sendDetailKkoMydoc.externalDocumentUuid))
							)
				.where(elctrnNticSndngDetail.elctrnNticSndng.elctrnNticSndngId.eq(elctrnNticSndngId))
				.fetch();

		List<ElctrnNticSndngResult> results = new ArrayList<>();
		for(Tuple row : list) {
			Optional<String> documentBinderUuid = Optional.ofNullable(row.get(sendResultKkoMydoc.documentBinderUuid));
			FieldCreate create = new FieldCreate();
//			create.setREGISTER("");
//			create.setREGIST_DT();
			ElctrnNticSndngResult sndngResult = ElctrnNticSndngResult.builder()
				.elctrnNticSndngDetailId(query.select(elctrnNticSndngDetail.elctrnNticSndngDetailId)
						.from(elctrnNticSndngDetail)
						.where(elctrnNticSndngDetail.mainCode.eq(row.get(sendDetailKkoMydoc.payload))
								.and(elctrnNticSndngDetail.external_document_uuid.eq(row.get(sendDetailKkoMydoc.externalDocumentUuid)))
						).fetchOne())
				.sndngResultCode(documentBinderUuid.isPresent()?"1":"3")  //1:발송성공, 2:열람, 3:발송실패
				.readngDt(row.get(sendDetailKkoMydoc.docBoxSentDt))
				.inqireDt(row.get(sendDetailKkoMydoc.frstTokenUsedDt))
				.readngDt(row.get(sendDetailKkoMydoc.frstDocBoxReadDt))
				.errorCn(row.get(sendResultKkoMydoc.error.errorMessage))
				.create(create)
				.build();
			results.add(sndngResult);
		}


		return results;
	}

	@Transactional
	@Override
	public Long saveFetchStatus(ElctrnNticSndng sndng) {
		//발송상세(sendDetailKkoMydoc) 목록 조횐
		List<Tuple> list = query.select(
										sendResultKkoMydoc.externalDocumentUuid     //외부 식별키
										,sendResultKkoMydoc.documentBinderUuid      //내부 식별키
										,sendDetailKkoMydoc.docBoxStatus            //진행 상태
										,sendDetailKkoMydoc.docBoxSentDt            //송신 시간(yyyyMMddHHmmss)
										,sendDetailKkoMydoc.docBoxReceivedDt        //수신 시간(yyyyMMddHHmmss)
										,sendDetailKkoMydoc.frstAuthenticatedDt     //열람 인증을 성공한 최초 시간(yyyyMMddHHmmss)
										,sendDetailKkoMydoc.frstTokenUsedDt         //OTT 검증을 성공한 최초 시간(yyyyMMddHHmmss)
										,sendDetailKkoMydoc.frstDocBoxReadDt        //최초 열람 시간(yyyyMMddHHmmss)
										,sendDetailKkoMydoc.userNotifiedDt          //알림톡 수신 시간(yyyyMMddHHmmss)
				)
			.from(sendDetailKkoMydoc)
			.innerJoin(sendResultKkoMydoc).on(sendDetailKkoMydoc.externalDocumentUuid.eq(sendResultKkoMydoc.externalDocumentUuid)
					.and(sendDetailKkoMydoc.sendMast.eq(sendResultKkoMydoc.sendMast)))
			.innerJoin(elctrnNticSndngDetail).on(sendResultKkoMydoc.externalDocumentUuid.eq(elctrnNticSndngDetail.external_document_uuid)
					.and(sendResultKkoMydoc.documentBinderUuid.eq(elctrnNticSndngDetail.mainCode))
					)
			.fetch();





		Long result = 0L;

		//details update
		for(Tuple row : list) {
			Optional<String> frstDocBoxReadDt = Optional.ofNullable(row.get(sendDetailKkoMydoc.frstDocBoxReadDt));
			if(!frstDocBoxReadDt.isPresent())
				frstDocBoxReadDt = Optional.ofNullable(row.get(sendDetailKkoMydoc.frstTokenUsedDt));
			String sndngResultCode = frstDocBoxReadDt.isPresent()?frstDocBoxReadDt.get():null;

			result += query.update(elctrnNticSndngResult)
				.set(elctrnNticSndngResult.sndngResultCode, sndngResultCode)                        //발송 결과 코드(1: 발송성공, 2:열람, 3:발송실패)
				.set(elctrnNticSndngResult.requstDt, row.get(sendDetailKkoMydoc.docBoxSentDt))      //요청 일시
				.set(elctrnNticSndngResult.inqireDt, row.get(sendDetailKkoMydoc.docBoxReceivedDt))  //조회 일시
				.set(elctrnNticSndngResult.readngDt, frstDocBoxReadDt.get())                        //열람 일시
				.where(JPAExpressions.selectFrom(elctrnNticSndngDetail)
						.where(
								elctrnNticSndngResult.elctrnNticSndngDetailId.eq(elctrnNticSndngDetail.elctrnNticSndngDetailId)
								.and(elctrnNticSndngDetail.external_document_uuid.eq(row.get(sendResultKkoMydoc.externalDocumentUuid)))
								.and(elctrnNticSndngDetail.mainCode.eq(row.get(sendResultKkoMydoc.documentBinderUuid)))
								).exists()
						)
				.execute();
		}


		//master update
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmm");	//년월일시분
		String closDt = simpleDateFormat.format(new Date())+"59";
		query.update(elctrnNticSndng)
		.set(elctrnNticSndng.sndngProcessSttus, "09")	//발송 처리 상태(01:요청, 02:발송완료, 09: 마감, 99:오류)
		.where(elctrnNticSndng.elctrnNticSndngId.eq(sndng.getElctrnNticSndngId())
				.and(elctrnNticSndng.closDt.loe(closDt))
				)
		.execute();



		return result;
	}

}
