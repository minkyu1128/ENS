//package cokr.xit.modules.post.domains.bill.service;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import org.springframework.util.StringUtils;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JsonMappingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//
//import cokr.xit.modules.post.code.KkoRespCd;
//import cokr.xit.modules.post.constant.PostConst;
//import cokr.xit.modules.post.domains.bill.domain.KakaoBillSendInfo;
//import cokr.xit.modules.post.domains.bill.domain.KakaoPayableHist;
//import cokr.xit.modules.post.domains.bill.domain.KakaoPayableHistRepository;
//import cokr.xit.modules.post.domains.bill.domain.QKakaoBillSendInfo;
//import cokr.xit.modules.post.domains.bill.dto.kko.KkoPayableStatReqDTO;
//import cokr.xit.modules.post.domains.bill.dto.kko.KkoPayableStatRespDTO;
//import cokr.xit.modules.post.domains.bill.dto.kko.item.KkoNotiRespData;
//import cokr.xit.modules.utils.CmmnUtil;
//import cokr.xit.modules.utils.RequireValidator;
//import lombok.Builder;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@RequiredArgsConstructor
//@Builder
//public class KkoPayableStat {
//
////	private final EntityManager em;
//	private final JPAQueryFactory query;
//	private final KakaoPayableHistRepository kakaoPayableHistRepository;
//	
//	
//	@SuppressWarnings("deprecation")
////	public KkoPayableStatRespDTO get(String billerCode, KkoPayableStatReqDTO reqDTO) {
//	public KkoPayableStatRespDTO get(KkoPayableStatReqDTO reqDTO) {
//		KkoRespCd kkoRespCd = KkoRespCd.OK;
//		
//		KkoNotiRespData respDTO = null;
//		try {
//			//조회
////			JPAQueryFactory query = new JPAQueryFactory(em);
//			QKakaoBillSendInfo bill = QKakaoBillSendInfo.kakaoBillSendInfo;
//			KakaoBillSendInfo kakaoBillSendInfo = query.selectFrom(bill)
////					.where(bill.billerCode.eq(billerCode)
//					.where(bill.billerCode.eq(String.valueOf(reqDTO.getData().getParameters().get(PostConst.KkoParamBillerCode)))
//							.and(bill.billerUserKey.eq(reqDTO.getData().getBiller_user_key()))
//							)
//					.fetchOne();
//			if(StringUtils.isEmpty(kakaoBillSendInfo))
//				throw new NullPointerException();
//		
//		
//			//parse(jsonString -> Object)
//			ObjectMapper mapper = new ObjectMapper();
//			respDTO = mapper.readValue(kakaoBillSendInfo.getNotiInfo(), KkoNotiRespData.class);
//			
//			
//			//유효성 검사
//			kkoRespCd = this.validate(reqDTO, respDTO);
//			
//			
//			//납부가능조회 이력 저장
//			KakaoPayableHist kakaoPayableHist = KakaoPayableHist.builder()
//					.kakaoBillSendInfo(kakaoBillSendInfo)
//					.amount(reqDTO.getData().getAmount())
//					.reqParam(CmmnUtil.toJsonString(reqDTO))
//					.respParam(kakaoBillSendInfo.getNotiInfo())
//					.build();
//			kakaoPayableHistRepository.save(kakaoPayableHist);
//			
//		} catch (NullPointerException e) {
//			kkoRespCd = KkoRespCd.E502;	//E502("납부불가 - 납부 대상 없음")
//		} catch (JsonMappingException e) {
//			log.error(String.format("청구서조회 데이터변환 중 매핑 Error::%s", e.getMessage()));
//			kkoRespCd = KkoRespCd.E601;	//E601("수납처리실패 - 납부요청 정보 조회 실패")
//		} catch (JsonProcessingException e) {
//			log.error(String.format("청구서조회 데이터변환 중 처리 Error::%s", e.getMessage()));
//			kkoRespCd = KkoRespCd.E601;	//E601("수납처리실패 - 납부요청 정보 조회 실패")
//		}
//
//		
//		return KkoPayableStatRespDTO.builder()
//				.res_code(kkoRespCd)
//				.message(kkoRespCd.getCodeNm())
//				.build();
//	}
//	
//	
//	
//	/**
//	 * 유효성 체크
//	 * @return
//	 */
//	@SuppressWarnings("deprecation")
//	protected KkoRespCd validate(KkoPayableStatReqDTO reqDTO, KkoNotiRespData respDTO){
//		
//		try {
//			RequireValidator.builder()
//			.obj(reqDTO)
//			.build()
//			.validate()
//			.throwableException();
//		} catch (Exception e) {
//			return KkoRespCd.E001;	//E001("필수 파라미터가 없습니다.")
//		}
//		
//		
//		
//		
//		if(reqDTO.getData().getAmount()==null||reqDTO.getData().getAmount().intValue()==0)	//납부승인요청금액이 null 또는 0 이면..
//			return KkoRespCd.E403;	//E403("납부 가능 금액 없음")
//		if(respDTO.getResult().getAmount()==null||respDTO.getResult().getAmount().intValue()==0)	//납부요청금액이 null 또는 0 이면..
//			return KkoRespCd.E403;	//E403("납부 가능 금액 없음")
//		
//		
//		if(reqDTO.getData().getAmount().intValue() > respDTO.getResult().getAmount().intValue())	//납부승인금액이 요청금액보다 많으면..
//			return KkoRespCd.E501;	//E501("납부불가 - 납부 가능금액 초과")
//		
//		
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
//		int today = Integer.parseInt(simpleDateFormat.format(new Date()));
//		if(today > Integer.parseInt(respDTO.getResult().getPay_expire_date()))	//첫번째 납기일이 경과 했으면..
//			if(StringUtils.isEmpty(respDTO.getResult().getSecond_pay_expire_date()))	//두번째 납기일이 없으면..
//				return KkoRespCd.E506;	//E506("납부불가 - 납부기한이 경과했습니다.")
//			else {
//				if(today > Integer.parseInt(respDTO.getResult().getSecond_pay_expire_date()))	//두번째 납기일이 경과 했으면..
//					return KkoRespCd.E506;	//E506("납부불가 - 납부기한이 경과했습니다.")
//			}
//	
//		
//		return KkoRespCd.OK;
//	}
//	
//	
//}
