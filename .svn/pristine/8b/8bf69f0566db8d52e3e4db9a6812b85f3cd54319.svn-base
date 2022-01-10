//package cokr.xit.modules.post.domains.sign.domain;
//
//import static cokr.xit.modules.post.domains.sign.domain.QKakaoSignTalkSendInfo.kakaoSignTalkSendInfo;
//
//import java.util.List;
//
//import org.springframework.util.StringUtils;
//
//import com.querydsl.core.types.dsl.BooleanExpression;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//
//import cokr.xit.modules.post.domains.sign.dto.kko.item.KkoSignSendRespData;
//import lombok.RequiredArgsConstructor;
//
//@RequiredArgsConstructor
//public class KakaoSignTalkSendInfoRepositoryImpl implements KakaoSignTalkSendInfoRepositoryCustom {
//	
//	private final JPAQueryFactory query;
//	
//	@Override
//	public long batchUpdateByResult(Long sendMastId, List<KkoSignSendRespData> list) {
//		/* 수십억건에서 QUERYDSL 사용하기 */
//		//https://idea-lab.tistory.com/entry/%EC%9A%B0%EC%95%84%EC%BD%982020-%EC%88%98%EC%8B%AD%EC%96%B5%EA%B1%B4%EC%97%90%EC%84%9C-QUERYDSL-%EC%82%AC%EC%9A%A9%ED%95%98%EA%B8%B0
//		/* 일괄 update 최적화하기 */
//		//https://velog.io/@youngerjesus/%EC%9A%B0%EC%95%84%ED%95%9C-%ED%98%95%EC%A0%9C%EB%93%A4%EC%9D%98-Querydsl-%ED%99%9C%EC%9A%A9%EB%B2%95#10-%EC%9D%BC%EA%B4%84-update-%EC%B5%9C%EC%A0%81%ED%99%94%ED%95%98%EA%B8%B0
//		/* batch 성 거래 처리하기 */
//		//https://devkingdom.tistory.com/255?category=761188
//		
//		
//		long updateCnt = 0L;
//		for(KkoSignSendRespData row : list) {
//			updateCnt += query
//					.update(kakaoSignTalkSendInfo)
//					.set(kakaoSignTalkSendInfo.result, row.getResult())   //처리결과. 정상접수처리시 "Y", 그외 "N"     
//					.set(kakaoSignTalkSendInfo.errcode, row.getErrcode()) //에러코드                          
//					.set(kakaoSignTalkSendInfo.errmsg, row.getErrmsg())   //에러메시지                         
//					.set(kakaoSignTalkSendInfo.sendStatAt, "Y")           //발송상태(Y:성공, N:실패)
//					.set(kakaoSignTalkSendInfo.sendErrCode, "")           //발송에러코드(실패인 경우만) 
//					.set(kakaoSignTalkSendInfo.sendErrMsg, "")            //발송에러메시지(실패인 경우만)
////					.where(
////							sendMastIdEq(sendMastId)
////							,txIdEq(row.getTx_id())
////							)
//					.where(
//							kakaoSignTalkSendInfo.sendMast.sendMastId.eq(sendMastId)
//							.and(kakaoSignTalkSendInfo.txId.eq(row.getTx_id()))
//							)
//					.execute();
//		}
//		return updateCnt;
//	}
//	@SuppressWarnings("deprecation")
//	private boolean hasText(String text) {
//		return !StringUtils.isEmpty(text);
//	}
//	private BooleanExpression sendMastIdEq(Long sendMastId) {
//		return kakaoSignTalkSendInfo.sendMast.sendMastId.eq(sendMastId);
//	}
//	private BooleanExpression txIdEq(String txId) {
//		return hasText(txId) ? kakaoSignTalkSendInfo.txId.eq(txId) : null;
//	}
////	private BooleanExpression sendMastIdEq(Long sendMastId) {
////		return kakaoSignTalkSendInfo.sendMast.sendMastId.eq(sendMastId);
////	}
//
//}
