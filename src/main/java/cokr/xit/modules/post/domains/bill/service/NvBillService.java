//package cokr.xit.modules.post.domains.bill.service;
//
//import java.util.Arrays;
//
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//
//import cokr.xit.modules.post.constant.ApiAdrsInf;
//import cokr.xit.modules.post.domains.bill.domain.KakaoBillSendInfoRepository;
//import cokr.xit.modules.post.domains.bill.dto.kko.KkoNotiReqDTO;
//import cokr.xit.modules.post.domains.bill.dto.kko.KkoNotiRespDTO;
//import cokr.xit.modules.post.domains.bill.dto.kko.KkoPayResultReqDTO;
//import cokr.xit.modules.post.domains.bill.dto.kko.KkoPayResultRespDTO;
//import cokr.xit.modules.post.domains.bill.dto.kko.KkoPayableStatReqDTO;
//import cokr.xit.modules.post.domains.bill.dto.kko.KkoPayableStatRespDTO;
//import cokr.xit.modules.post.domains.bill.dto.kko.KkoSendnNotiReqDTO;
//import lombok.RequiredArgsConstructor;
//
//@Service
//@RequiredArgsConstructor
//@Transactional
//public class NvBillService {
//
//	private final KakaoBillSendInfoRepository kakaoBillSendInfoRepository;
//	
////	private final EntityManager em;
//	private final JPAQueryFactory query;
//	
//	
//	/**
//	 * <pre>메소드 설명: 고지서 발송 요청
//	 * 	-.네이버 고지서 서버에 1건 이상의 고지서 발송 요청
//	 * 	-.문서참조: "네이버_고지서_연동_가이드_V_0_22.pdf" 10 page
//	 * </pre>
//	 * @param billerCode
//	 * @param dto	KkoSendnNotiReqDTO
//	 * @throws JsonProcessingException void 요청처리 후 응답객체
//	 * @author: 박민규
//	 * @date: 2021. 8. 19.
//	 */
//	public void saveSendData(String billerCode, KkoSendnNotiReqDTO dto) throws JsonProcessingException {
//		
////		KkoSender sender = new KkoSender(billerCode, ApiAdrsInf.KKO_BILL_REQ, dto, kakaoSendMastRepository);
////		sender.execute();
//		KkoSender.builder()
//		.billerCode(billerCode)
//		.apiAdrsInf(ApiAdrsInf.KKO_BILL_REQ)
//		.sendDatalist(Arrays.asList(dto))
//		.kakaoBillSendInfoRepository(kakaoBillSendInfoRepository)
//		.build()
//		.execute();
//	}
//	
//	
//	
//
//	/**
//	 * <pre>메소드 설명: nonce 유효성 검증
//	 * 	-.RedirectUrl로 전달된 nonce 값의 검증을 요청
//	 * 	-.문서참조: "네이버_고지서_연동_가이드_V_0_22.pdf" 18 page
//	 * </pre>
//	 * @param billerCode
//	 * @param reqDTO	KkoNotiReqDTO
//	 * @throws JsonProcessingException void 요청처리 후 응답객체
//	 * @author: 박민규
//	 * @date: 2021. 8. 19.
//	 */
//	public KkoNotiRespDTO getNotiInfo(KkoNotiReqDTO reqDTO) {
//		
//		return KkoNotice.builder()
////				.em(em)
//				.query(query)
//				.build()
//				.get(reqDTO);
//	}
//	
//	
//	/**
//	 * <pre>메소드 설명: 상태 조회
//	 * 	-.네이버Sign 서버에 고지서 상태 조회 요청
//	 * 	-.문서참조: "네이버_고지서_연동_가이드_V_0_22.pdf" 21 page
//	 * </pre>
//	 * @param billerCode
//	 * @param reqDTO	KkoPayableStatReqDTO
//	 * @return ResponseEntity 요청처리 후 응답객체
//	 * @author: 박민규
//	 * @date: 2021. 8. 19.
//	 */
//	public KkoPayableStatRespDTO getPayableStat(KkoPayableStatReqDTO reqDTO){
//		
//		return KkoPayableStat.builder()
////			.em(em)
//			.query(query)
//			.build()
//			.get(reqDTO);
//	}
//	
//	
//	/**
//	 * <pre>메소드 설명: Ping Test Url
//	 * 	-.고지서 서버 ping check url
//	 * 	-.문서참조: "네이버_고지서_연동_가이드_V_0_22.pdf" 25 page
//	 * </pre>
//	 * @param billerCode
//	 * @param reqDTO	KkoPayableStatReqDTO
//	 * @return ResponseEntity 요청처리 후 응답객체
//	 * @author: 박민규
//	 * @date: 2021. 8. 19.
//	 */
//	public KkoPayResultRespDTO savePayResult(KkoPayResultReqDTO reqDTO) {
//		
//		return KkoPayResult.builder()
////				.em(em)
//				.query(query)
//				.build()
//				.save(reqDTO);
//	}
//}
