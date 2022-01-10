//package cokr.xit.modules.post.domains.bill.service;
//
//import java.util.Arrays;
//
//import org.springframework.beans.factory.annotation.Autowired;
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
//public class KkoBillService {
//
//	private final KakaoBillSendInfoRepository kakaoBillSendInfoRepository;
//	
////	private final EntityManager em;
//	@Autowired
//	private final JPAQueryFactory query;
//	
//	
//	/**
//	 * <pre>메소드 설명: 청구서 링크 생성
//	 * 	-.문서참조: "카카오페이청구서_조회납부_API가이드_v0.1.3.pdf" 6 page
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
//	/**
//	 * <pre>메소드 설명: 청구서 링크 재생성
//	 * 	-.문서참조: "카카오페이청구서_조회납부_API가이드_v0.1.3.pdf" 11 page
//	 * </pre>
//	 * @param billerCode
//	 * @param dto	KkoSendnNotiReqDTO
//	 * @throws JsonProcessingException void 요청처리 후 응답객체
//	 * @author: 박민규
//	 * @date: 2021. 8. 19.
//	 */
//	public void saveReSendData(String billerCode, KkoSendnNotiReqDTO dto) throws JsonProcessingException {
//		KkoSender.builder()
//		.billerCode(billerCode)
//		.apiAdrsInf(ApiAdrsInf.KKO_BILL_REREQ)
//		.sendDatalist(Arrays.asList(dto))
//		.kakaoBillSendInfoRepository(kakaoBillSendInfoRepository)
//		.build()
//		.execute();
//	}
//	
//
//	/**
//	 * <pre>메소드 설명: 청구서 조회
//	 * 	-.문서참조: "카카오페이청구서_조회납부_API가이드_v0.1.3.pdf" 14 page
//	 * 	[청구서 조회 에러코드 - res_code/case/message 순..]
//	 * 	 -.E402/청구서 조회결과 없음/청구서 조회 결과가 없습니다. 
//	 * 	 -.E404/user_birth 불일치/생년월일이 일치하지 않습니다.
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
//	 * <pre>메소드 설명: 납부 가능 조회
//	 * 	-.문서참조: "카카오페이청구서_조회납부_API가이드_v0.1.3.pdf" 24 page
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
//	 * <pre>메소드 설명: 납부 결과 전달
//	 * 	-.문서참조: "카카오페이청구서_조회납부_API가이드_v0.1.3.pdf" 27 page
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
