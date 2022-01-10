//package cokr.xit.modules.post.domains.bill.service;
//
//import com.querydsl.jpa.impl.JPAQueryFactory;
//
//import cokr.xit.modules.post.code.KkoRespCd;
//import cokr.xit.modules.post.constant.PostConst;
//import cokr.xit.modules.post.domains.bill.domain.KakaoBillSendInfo;
//import cokr.xit.modules.post.domains.bill.domain.KakaoPaymentInfo;
//import cokr.xit.modules.post.domains.bill.domain.KakaoPaymentInfoRepository;
//import cokr.xit.modules.post.domains.bill.domain.QKakaoBillSendInfo;
//import cokr.xit.modules.post.domains.bill.dto.kko.KkoPayResultReqDTO;
//import cokr.xit.modules.post.domains.bill.dto.kko.KkoPayResultRespDTO;
//import cokr.xit.modules.utils.CmmnUtil;
//import lombok.Builder;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@RequiredArgsConstructor
//@Builder
//public class KkoPayResult {
//
////	private final EntityManager em;
//	private final JPAQueryFactory query;
//	private final KakaoPaymentInfoRepository kakaoPaymentInfoRepository;
//	
//	
////	public KkoPayResultRespDTO save(String billerCode, KkoPayResultReqDTO reqDTO) {
//	public KkoPayResultRespDTO save(KkoPayResultReqDTO reqDTO) {
//		KkoRespCd kkoRespCd = KkoRespCd.OK;
//		
//		try {
//			//청구서전송정보 조회
////			JPAQueryFactory query = new JPAQueryFactory(em);
//			QKakaoBillSendInfo bill = QKakaoBillSendInfo.kakaoBillSendInfo;
//			KakaoBillSendInfo kakaoBillSendInfo = query.selectFrom(bill)
////					.where(bill.billerCode.eq(billerCode)
//					.where(bill.billerCode.eq(String.valueOf(reqDTO.getData().getParameters().get(PostConst.KkoParamBillerCode)))
//							.and(bill.billerUserKey.eq(reqDTO.getData().getBiller_user_key()))
//							)
//					.fetchOne();
//			
//			//납부결과 저장
//			KakaoPaymentInfo kakaoPaymentInfo = KakaoPaymentInfo.builder()
//				.kakaoBillSendInfo(kakaoBillSendInfo)
//				.billed_year_month(reqDTO.getData().getBilled_year_month())
//				.ordinal(reqDTO.getData().getOrdinal())
//				.biller_notice_key(reqDTO.getData().getBiller_notice_key())
//				.parameters(CmmnUtil.toJsonString(reqDTO.getData().getParameters()))
//				.pay_by(reqDTO.getData().getPay_by())
//				.pay_type(reqDTO.getData().getPay_type())
//				.pay_detail1(reqDTO.getData().getPay_detail1())
//				.pay_detail2(reqDTO.getData().getPay_detail2())
//				.pay_detail3(reqDTO.getData().getPay_detail3())
//				.amount(reqDTO.getData().getAmount())
//				.pay_amount(reqDTO.getData().getPay_amount())
//				.pay_fee_type(reqDTO.getData().getPay_fee_type())
//				.pay_fee(reqDTO.getData().getPay_fee())
//				.pay_fee_tax(reqDTO.getData().getPay_fee_tax())
//				.adjust_date(reqDTO.getData().getAdjust_date())
//				.paid_at(reqDTO.getData().getPaid_at())
//				.pay_id(reqDTO.getData().getPay_id())
//				.build();
//			kakaoPaymentInfoRepository.save(kakaoPaymentInfo);			
//
//			
//		} catch (Exception e) {
//			log.error(String.format("납부결과 저장 처리 중 Error 발생::%s", e.getMessage()));
//			kkoRespCd = KkoRespCd.E902;
//		}
//
//		
//		return KkoPayResultRespDTO.builder()
//				.res_code(kkoRespCd)
//				.message(kkoRespCd.getCodeNm())
//				.build();
//	}
//	
//	
//	
//	
//}
