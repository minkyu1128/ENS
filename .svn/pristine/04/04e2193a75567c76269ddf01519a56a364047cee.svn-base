//package cokr.xit.modules.post.domains.bill.service;
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
//import cokr.xit.modules.post.domains.bill.domain.KakaoNotiHist;
//import cokr.xit.modules.post.domains.bill.domain.KakaoNotiHistRepository;
//import cokr.xit.modules.post.domains.bill.domain.QKakaoBillSendInfo;
//import cokr.xit.modules.post.domains.bill.dto.kko.KkoNotiReqDTO;
//import cokr.xit.modules.post.domains.bill.dto.kko.KkoNotiRespDTO;
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
//public class KkoNotice {
//
////	private final EntityManager em;
//	private final JPAQueryFactory query;
//	private final KakaoNotiHistRepository kakaoNotiHistRepository;
//	
//	
//	@SuppressWarnings("deprecation")
////	public KkoNotiRespDTO get(String billerCode, KkoNotiReqDTO reqDTO) {
//	public KkoNotiRespDTO get(KkoNotiReqDTO reqDTO) {
//		KkoRespCd kkoRespCd = KkoRespCd.OK;
//		
//		KkoNotiRespData respDTO = null;
//		try {
//			//조회
////			JPAQueryFactory query = new JPAQueryFactory(em);
//			QKakaoBillSendInfo bill = QKakaoBillSendInfo.kakaoBillSendInfo;
//			KakaoBillSendInfo kakaoBillSendInfo = query.selectFrom(bill)
//					.from(bill)
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
//			kkoRespCd = this.validate(reqDTO, respDTO, kakaoBillSendInfo.getUserBirth());
//
//			
//			
//			//청구서열람이력 저장
//			KakaoNotiHist kakaoNotiHist = KakaoNotiHist.builder()
//					.kakaoBillSendInfo(kakaoBillSendInfo)
//					.user_birth(reqDTO.getData().getUser_birth())
//					.reqParam(CmmnUtil.toJsonString(reqDTO))
//					.respParam(kakaoBillSendInfo.getNotiInfo())
//					.build();
//			kakaoNotiHistRepository.save(kakaoNotiHist);
//			
//		} catch (NullPointerException e) {
//			kkoRespCd = KkoRespCd.E402;
//		} catch (JsonMappingException e) {
//			log.error(String.format("청구서조회 데이터변환 중 매핑 Error::%s", e.getMessage()));
//			kkoRespCd = KkoRespCd.E402;
//		} catch (JsonProcessingException e) {
//			log.error(String.format("청구서조회 데이터변환 중 처리 Error::%s", e.getMessage()));
//			kkoRespCd = KkoRespCd.E402;
//		}
//
//		
//		return KkoNotiRespDTO.builder()
//				.res_code(kkoRespCd)
//				.message(kkoRespCd.getCodeNm())
//				.data(respDTO)
//				.build();
//	}
//	
//	
//	
//	/**
//	 * 유효성 체크
//	 * @return
//	 */
//	protected KkoRespCd validate(KkoNotiReqDTO reqDTO, KkoNotiRespData respDTO, String userBirth){
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
//		/* ==============================
//		 * 2021.08.23. 박민규
//		 *  E403, E506 검증 주석처리
//		 *   -사유: next 단계(납부 가능 조회)에서 확인 
//		 ============================== */
//		//2021.08.23. 주석처리
//		/*
//		if(respDTO.getResult().getAmount()==null||respDTO.getResult().getAmount().intValue()==0)	//납부요청금액이 null 또는 0 이면..
//			return KkoRespCd.E403;	//E403("납부 가능 금액 없음")
//		*/
//	
//		if(!reqDTO.getData().getUser_birth().equals(userBirth))	//생년월일이 일치하지 않으면..
//			return KkoRespCd.E404;	//E404("생년월일이 일치하지 않습니다.")
//		
//		//2021.08.23. 주석처리
//		/*
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
//		int today = Integer.parseInt(simpleDateFormat.format(new Date()));
//		if(today > Integer.parseInt(respDTO.getResult().getPay_expire_date()))	//첫번째 납기일이 경과 했으면..
//			if(StringUtils.isEmpty(respDTO.getResult().getSecond_pay_expire_date()))	//두번째 납기일이 없으면..
//				return KkoRespCd.E506;	//E506("납부불가 - 납부기한이 경과했습니다.")
//			else {
//				if(today > Integer.parseInt(respDTO.getResult().getSecond_pay_expire_date()))	//두번째 납기일이 경과 했으면..
//					return KkoRespCd.E506;	//E506("납부불가 - 납부기한이 경과했습니다.")
//			}
//		*/
//		
//		return KkoRespCd.OK;
//	}
//	
//	
//}
