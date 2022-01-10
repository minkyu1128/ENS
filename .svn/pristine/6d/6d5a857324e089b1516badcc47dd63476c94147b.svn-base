//package cokr.xit.modules.post.domains.sign.service;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.function.Function;
//import java.util.function.Supplier;
//
//import javax.transaction.Transactional;
//
//import org.springframework.stereotype.Service;
//import org.springframework.util.StringUtils;
//
//import com.querydsl.jpa.impl.JPAQueryFactory;
//
//import cokr.xit.modules.domain.SendMast;
//import cokr.xit.modules.domain.SendMastRepository;
//import cokr.xit.modules.post.code.InternalErrCd;
//import cokr.xit.modules.post.domains.sign.domain.KakaoSignStatVerifyHist;
//import cokr.xit.modules.post.domains.sign.domain.KakaoSignStatVerifyHistRepository;
//import cokr.xit.modules.post.domains.sign.domain.KakaoSignTalkSendInfo;
//import cokr.xit.modules.post.domains.sign.domain.KakaoSignTalkSendInfoRepository;
//import cokr.xit.modules.post.domains.sign.domain.KakaoTokenVerifyHist;
//import cokr.xit.modules.post.domains.sign.domain.KakaoTokenVerifyHistRepository;
//import cokr.xit.modules.post.domains.sign.domain.QKakaoSignTalkSendInfo;
//import cokr.xit.modules.post.domains.sign.dto.kko.item.KkoBulkSignStatRespData;
//import cokr.xit.modules.post.domains.sign.dto.kko.item.KkoBulkSignStatRespDataSignedData;
//import cokr.xit.modules.post.domains.sign.dto.kko.item.KkoSignSendReqData;
//import cokr.xit.modules.post.domains.sign.dto.kko.item.KkoSignSendRespData;
//import cokr.xit.modules.post.domains.sign.dto.kko.item.KkoSignStatRespData;
//import cokr.xit.modules.post.domains.sign.dto.kko.item.KkoTokenStatRespData;
//import lombok.RequiredArgsConstructor;
//
///**
// * <ul>
// * <li>업무 그룹명: 카카오인증톡 서비스</li>
// * <li>설 명: </li>
// * <li>작성일: 2021. 8. 26. 오후 7:15:42
// * </ul>
// * @author 박민규
// */
////@Slf4j
//@RequiredArgsConstructor
//@Service
//public class KkoSignTalkService {
//	private final SendMastRepository sendMastRepository;
//	private final KakaoSignTalkSendInfoRepository kakaoSignTalkSendInfoRepository;
//	private final KakaoSignStatVerifyHistRepository kakaoSignStatVerifyHistRepository;
//	private final KakaoTokenVerifyHistRepository kakaoTokenVerifyHistRepository;
//	private final JPAQueryFactory query;
//	
//
//
//	/**
//	 * <pre>
//	 * 인증톡 발송정보 등록
//	 * </pre>
//	 * @param vender
//	 * @param postSe
//	 * @param postTitle
//	 * @param data
//	 * @return
//	 */
//	@Transactional
//	public SendMast addSendInfoByReq(String vender, String postSe, String postTitle, KkoSignSendReqData data) {
//		List<KkoSignSendReqData> list = new ArrayList<>();
//		list.add(data);
//		return this.addSendInfoByReq(vender, postSe, postTitle, list, null);
////		return this.addSendInfoByReq(vender, postSe, postTitle, list);
//	}
//	/**
//	 * <pre>
//	 * 인증톡 발송정보 등록
//	 * </pre>
//	 * @param vender
//	 * @param postSe
//	 * @param postTitle
//	 * @param list
//	 * @param prefix
//	 * @return
//	 */
//	@Transactional
//	public SendMast addSendInfoByReq(String vender, String postSe, String postTitle, List<KkoSignSendReqData> list, String prefix) {
////	public SendMast addSendInfoByReq(String vender, String postSe, String postTitle, List<KkoSignSendReqData> list) {
//
//		//발송마스터 저장
//		SendMast sendMast = SendMast.builder().build();
//		sendMast.setSendInfo(vender, postSe, postTitle, list.size());
//		sendMastRepository.save(sendMast);
//		
//		
//		//발송상세 저장
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
//		String today = simpleDateFormat.format(new Date());
//		int sn = 1;
//		for(KkoSignSendReqData row : list) {
//			KakaoSignTalkSendInfo kakaoSignTalkSendInfo = KakaoSignTalkSendInfo.builder()
//					//발송정보
//					.sendMast(sendMast)                                                          //발송마스터
//					.phone_no(row.getPhone_no())                                                 //휴대폰 번호
//					.name(row.getName())                                                         //사용자 성명
//					.birthday(row.getBirthday())                                                 //생년월일(YYYYMMDD)
//					.expires_in(row.getExpires_in())                                             //처리마감시간(초 단위)
//					.call_center_no(row.getCall_center_no())                                     //고객센터 전화번호
//					.title(row.getTitle())                                                       //메시지 명칭
//					.message_type(row.getMessage())                                              //메시지 타입
//					.message(row.getMessage())                                                   //메시지
//					.data_hash(row.getData_hash())                                               //열람정보 해시. 공인전자문서유통정보 등록 시 필수
//					.allow_simple_registration(row.getAllow_simple_registration())               //간편등록회원 허용여부(Y/N)
//					.verify_auth_name(row.getVerify_auth_name())                                 //사용자 성명 체크 여부(Y/N)
//					.publish_certified_electronic_doc(row.getPublish_certified_electronic_doc()) //공인전자문서 유통정보 등록 여부(Y/N)
//					.redirect_url(row.getRedirect_url())                                         //서명 완료한 사용자에게 보여줄 웹페이지 주소
//					.payload(row.getPayload())                                                   //이용기관이 생성한 payload 값
//					.sub_org_id(row.getSub_org_id())                                             //하위 이용기관명
//					.call_center_label(row.getCall_center_label())                               //콜센터명
//					.txId(prefix==null ? "" : String.format("%s%s%06d", prefix, today, sn++))    //트랜잭션ID. tx_id 규칙: {prefix}{unique_value} 로 구성되며, 띄어쓰기 없이 붙여써야 함. 최대 39자
////					.txId(row.getTx_id())
//					.build();
//			
//			kakaoSignTalkSendInfoRepository.save(kakaoSignTalkSendInfo);
//		}
//		
//		return sendMast;
//	}
//	
//	
//	
//	
//	
//	/**
//	 * <pre>
//	 * 인증톡발송결과 처리
//	 * 	-.응답된 값을 전송내역 결과에 반영 한다.
//	 * </pre>
//	 * @param sendMastId
//	 */
//	@Transactional
//	public void modifySendInfoResultByResp(Long sendMastId, KkoSignSendRespData data) {
//		List<KkoSignSendRespData> list = new ArrayList<>();
//		list.add(data);
//		this.modifySendInfoResultByResp(sendMastId, list);
//	}
//	/**
//	 * <pre>
//	 * 인증톡발송결과 처리
//	 * 	-.응답된 값을 전송내역 결과에 반영 한다.
//	 * </pre>
//	 * @param sendMastId
//	 */
//	@Transactional
//	public void modifySendInfoResultByResp(Long sendMastId, List<KkoSignSendRespData> list) {
////		EntityManager em = BeanUtils.getBean(EntityManager.class);
////		JPAQueryFactory query = new JPAQueryFactory(em);
//		QKakaoSignTalkSendInfo A = new QKakaoSignTalkSendInfo("A");
//		
//		for(KkoSignSendRespData row : list){
//			KakaoSignTalkSendInfo kakaoSignTalkSendInfo = query.selectFrom(A)
//					.where(
//							A.sendMast.sendMastId.eq(sendMastId)     //발송마스터 그룹 중에서..
//							.and(A.txId.eq(row.getTx_id())) //txId가 일치하는 데이터..
//							)
//					.fetchOne();
//			
//
//			kakaoSignTalkSendInfo.setResult(row.getResult());    //처리결과. 정상접수처리시 "Y", 그외 "N"                    
//			kakaoSignTalkSendInfo.setErrcode(row.getErrcode());  //에러코드                                         
//			kakaoSignTalkSendInfo.setErrmsg(row.getErrmsg());    //에러메시지                                        
//			kakaoSignTalkSendInfo.setSendStatAt("Y");  //발송상태(Y:성공, N:실패)
//			kakaoSignTalkSendInfo.setSendErrCode("");  //발송에러코드(실패인 경우만) 
//			kakaoSignTalkSendInfo.setSendErrMsg("");   //발송에러메시지(실패인 경우만)
//		}
//		
//	}
//	
//	
//	
//	
//	
//	/**
//	 * <pre>
//	 * 인증톡발송결과 error 처리
//	 * 	-.에러사유를 전송내역 결과에 반영 한다.
//	 * </pre>
//	 * @param sendMastId
//	 */
//	@Transactional
//	public void modifySendInfoResultByError(Long sendMastId, KkoSignSendReqData data, String sendErrCode, String sendErrMsg) {
//		List<KkoSignSendReqData> list = new ArrayList<>();
//		list.add(data);
//		this.modifySendInfoResultByError(sendMastId, list, sendErrCode, sendErrMsg);
//	}
//	/**
//	 * <pre>
//	 * 인증톡발송결과 error 처리
//	 * 	-.에러사유를 전송내역 결과에 반영 한다.
//	 * </pre>
//	 * @param sendMastId
//	 */
//	@Transactional
//	public void modifySendInfoResultByError(Long sendMastId, List<KkoSignSendReqData> list, String sendErrCode, String sendErrMsg) {
////		EntityManager em = BeanUtils.getBean(EntityManager.class);
////		JPAQueryFactory query = new JPAQueryFactory(em);
//		QKakaoSignTalkSendInfo A = new QKakaoSignTalkSendInfo("A");
//		
//		for(KkoSignSendReqData row : list){
//			KakaoSignTalkSendInfo kakaoSignTalkSendInfo = query.selectFrom(A)
////					.innerJoin(B).fetchJoin()
//					.where(
////							B.sendMastId.eq(sendMastId)     //발송마스터 그룹 중에서..
//							A.sendMast.sendMastId.eq(sendMastId)     //발송마스터 그룹 중에서..
//							.and(A.txId.eq(row.getTx_id())) //txId가 일치하는 데이터..
//							)
//					.fetchOne();
//			
//
//
//			kakaoSignTalkSendInfo.setResult("N");                //처리결과. 정상접수처리시 "Y", 그외 "N"                    
////			kakaoSignTalkSendInfo.setErrcode(row.getErrcode());  //에러코드                                         
////			kakaoSignTalkSendInfo.setErrmsg(row.getErrmsg());    //에러메시지          
//			kakaoSignTalkSendInfo.setSendStatAt("N");           //발송상태(Y:성공, N:실패)
//			kakaoSignTalkSendInfo.setSendErrCode(sendErrCode);  //발송에러코드(실패인 경우만) 
//			kakaoSignTalkSendInfo.setSendErrMsg(sendErrMsg);    //발송에러메시지(실패인 경우만)
//		}
//		
//	}
//	
//	
//	/**
//	 * <pre>
//	 * 발송마스터 결과 반영
//	 * 	-.인증톡 발송마스터 결과를 반영 한다.
//	 * </pre>
//	 * @param sendMastId
//	 */
//	@Transactional
//	public void modifySendMastByResult(Long sendMastId, String sendErrCode, String sendErrMsg) {
//		//발송내역 중 미처리된 데이터 실패 처리
////		EntityManager em = BeanUtils.getBean(EntityManager.class);
////		JPAQueryFactory query = new JPAQueryFactory(em);
//		QKakaoSignTalkSendInfo A = QKakaoSignTalkSendInfo.kakaoSignTalkSendInfo;
//		List<KakaoSignTalkSendInfo> list = query.selectFrom(A)
//			.where(
//					A.sendMast.sendMastId.eq(sendMastId)  //발송마스터 그룹 중에서..
//					.and(A.sendStatAt.isEmpty().or(A.sendStatAt.eq("idle")))          //발송상태가 입력되지 않은 데이터..
//					)
//			.fetch();
//		for(KakaoSignTalkSendInfo row : list) {
//			row.setResult("N");                //처리결과. 정상접수처리시 "Y", 그외 "N"                    
////			row.setErrcode(row.getErrcode());  //에러코드                                         
////			row.setErrmsg(row.getErrmsg());    //에러메시지          
//			row.setSendStatAt("N");           //발송상태(Y:성공, N:실패)
//			row.setSendErrCode(sendErrCode);  //발송에러코드(실패인 경우만) 
//			row.setSendErrMsg(sendErrMsg);    //발송에러메시지(실패인 경우만)
//		}
//		Long totCnt = query.selectFrom(A)
//				.where(
//						A.sendMast.sendMastId.eq(sendMastId)  //발송마스터 그룹 중에서..
//						.and(A.sendStatAt.isEmpty())          //발송상태가 입력되지 않은 데이터..
//						)
//				.fetchCount();
//		
//		
//		
//		//발송마스터 결과 반영
//		SendMast sendMast = sendMastRepository.findById(sendMastId).get();
//		sendMast.setCurStatus(totCnt.intValue() == list.size() ? "ok":"fail"); //상태(idle:발송준비중, ok:발송성공, fail:발송실패, send:열람중, close:조회마감, Etc...)
//	}
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	/**
//	 * <pre>메소드 설명: 인증상태확인 요청정보 저장</pre>
//	 * @param list void 요청처리 후 응답객체
//	 * @author: 박민규
//	 * @date: 2021. 9. 10.
//	 */
//	@Transactional
//	public Map<String, Long> addSignStatVerifyInfoByReq(String txId) {
//		List<String> list = new ArrayList<String>();
//		list.add(txId);
//		return this.addSignStatVerifyInfoByReq(list);
//	}
//	/**
//	 * <pre>메소드 설명: 인증상태확인 요청정보 저장</pre>
//	 * @param list void 요청처리 후 응답객체
//	 * @author: 박민규
//	 * @date: 2021. 9. 10.
//	 */
//	@Transactional
//	public Map<String, Long> addSignStatVerifyInfoByReq(List<String> list) {
//		Map<String, Long> mPkIds = new HashMap<String, Long>();
//		for(String txId : list) {
//			KakaoSignStatVerifyHist kakaoSignVerifyHist = KakaoSignStatVerifyHist.builder()
//					.kakaoSignTalkSendInfo(kakaoSignTalkSendInfoRepository.findByTxId(txId).orElse(null))
//					.build();
//			kakaoSignStatVerifyHistRepository.save(kakaoSignVerifyHist);
//			
//			mPkIds.put(txId, kakaoSignVerifyHist.getSignVerifyId()); //식별자
//		}
//		return mPkIds;
//	}
//	/**
//	 * <pre>메소드 설명: 인증상태확인 요청결과 저장</pre>
//	 * @param list void 요청처리 후 응답객체
//	 * @author: 박민규
//	 * @date: 2021. 9. 10.
//	 */
//	public void modifySignStatVerifyInfoByResp(Map<String, Long> mPkIds, KkoSignStatRespData data) {
//		KkoBulkSignStatRespDataSignedData kkoBulkSignStatRespDataSignedData = new KkoBulkSignStatRespDataSignedData();
//		kkoBulkSignStatRespDataSignedData.setSigned_data_uuid("unknown");
//		kkoBulkSignStatRespDataSignedData.setStatus(data.getStatus());
//		kkoBulkSignStatRespDataSignedData.setRequest_at(data.getRequest_at());
//		kkoBulkSignStatRespDataSignedData.setViewed_at(data.getViewed_at());
//		kkoBulkSignStatRespDataSignedData.setCompleted_at(data.getCompleted_at());
//		kkoBulkSignStatRespDataSignedData.setExpired_at(data.getExpired_at());
//		kkoBulkSignStatRespDataSignedData.setPayload(data.getPayload());
//		kkoBulkSignStatRespDataSignedData.setOrg_notified_at(data.getOrg_notified_at());
//		List<KkoBulkSignStatRespDataSignedData> signed_data = new ArrayList<KkoBulkSignStatRespDataSignedData>();
//		signed_data.add(kkoBulkSignStatRespDataSignedData);
//		
//		KkoBulkSignStatRespData kkoBulkSignStatRespData = new KkoBulkSignStatRespData();
//		kkoBulkSignStatRespData.setTx_id(data.getTx_id());
//		kkoBulkSignStatRespData.setSigned_data(signed_data);
//		List<KkoBulkSignStatRespData> list = new ArrayList<KkoBulkSignStatRespData>();
//		list.add(kkoBulkSignStatRespData);
//		this.modifySignStatVerifyInfoByResp(mPkIds, list);
//	}
//	/**
//	 * <pre>메소드 설명: 인증상태확인 요청결과 저장</pre>
//	 * @param list void 요청처리 후 응답객체
//	 * @author: 박민규
//	 * @date: 2021. 9. 10.
//	 */
//	@SuppressWarnings("deprecation")
//	public void modifySignStatVerifyInfoByResp(Map<String, Long> mPkIds, List<KkoBulkSignStatRespData> list) {
//		//Declare Function - unix time => date 문자열로 변환
//		Function<Number, String> fnUnixTimeToStrDate = unixTime -> {
//			if(unixTime.toString().isEmpty()) return null;
//			
//			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss.SSS");
//			return simpleDateFormat.format(new Date(unixTime.longValue()));
//		};
//		
//		//set Querydsl Entity 
////		EntityManager em = BeanUtils.getBean(EntityManager.class);
////		JPAQueryFactory query = new JPAQueryFactory(em);
//		QKakaoSignTalkSendInfo A = new QKakaoSignTalkSendInfo("A");
//		
//		
//		
//		for(KkoBulkSignStatRespData row : list) {
//			/* ================
//			 * 인증상태확인-결과 저장
//		 	 ================ */
//			KakaoSignStatVerifyHist kakaoVerifyHist = kakaoSignStatVerifyHistRepository.findById(mPkIds.get(row.getTx_id())).orElse(null);
//			if(StringUtils.isEmpty(row.getSigned_data())) {	//존재하지 않는 tx_id인 경우에 빈(empty) signed_data가 내려옴..
//				kakaoVerifyHist.setStatus(null);    //처리상태(PREPARE: 대기중=서명을 요청한 상태, COMPLETE: 서명완료=비밀번호를 정확히 입력하여 서명을 완료한 상태, EXPIRED: 타임아웃=처리마감시간 동안 서명을 완료하지 않은 상태)                  
//				kakaoVerifyHist.setErrcode(null);  //에러코드                                          
//				kakaoVerifyHist.setErrmsg(null);    //에러메시지
//				
//				kakaoVerifyHist.setSendStatAt("N");  //발송상태(Y:성공, N:실패)
//				kakaoVerifyHist.setSendErrCode(InternalErrCd.ERR403.name());  //발송에러코드(실패인 경우만) 
//				kakaoVerifyHist.setSendErrMsg(String.format("%s :: [ 존재하지 않는 tx_id(%s) 입니다. ]", InternalErrCd.ERR403.getCodeNm(), row.getTx_id()));   //발송에러메시지(실패인 경우만)
//				
//				
//			}else {
//				kakaoVerifyHist.setSigned_data_uuid(row.getSigned_data().get(0).getSigned_data_uuid());
//				kakaoVerifyHist.setRequest_at(row.getSigned_data().get(0).getRequest_at());
//				kakaoVerifyHist.setViewed_at(row.getSigned_data().get(0).getViewed_at());
//				kakaoVerifyHist.setCompleted_at(row.getSigned_data().get(0).getCompleted_at());
//				kakaoVerifyHist.setExpired_at(row.getSigned_data().get(0).getExpired_at());
//				kakaoVerifyHist.setPayload(row.getSigned_data().get(0).getPayload());
//				kakaoVerifyHist.setOrg_notified_at(row.getSigned_data().get(0).getOrg_notified_at());
//				
//				kakaoVerifyHist.setStatus(row.getSigned_data().get(0).getStatus());    //처리상태(PREPARE: 대기중=서명을 요청한 상태, COMPLETE: 서명완료=비밀번호를 정확히 입력하여 서명을 완료한 상태, EXPIRED: 타임아웃=처리마감시간 동안 서명을 완료하지 않은 상태)                  
//				kakaoVerifyHist.setErrcode(null);   //에러코드                                          
//				kakaoVerifyHist.setErrmsg(null);    //에러메시지      
//				
//				kakaoVerifyHist.setSendStatAt("Y");    //발송상태(Y:성공, N:실패)
//				kakaoVerifyHist.setSendErrCode(null);  //발송에러코드(실패인 경우만) 
//				kakaoVerifyHist.setSendErrMsg(null);   //발송에러메시지(실패인 경우만)
//				
//				
//
//				/* ================
//				 * 발송내역-최초서명일시 저장
//				 ================ */
//				KakaoSignTalkSendInfo kakaoSignTalkSendInfo = query.selectFrom(A)
//						.where(
//								A.txId.eq(row.getTx_id()) //txId가 일치하는 데이터..
//								.and(A.frstSignDe.isEmpty())  //최초서명일시가 비어있는 데이터..
//								)
//						.fetchOne();
//				if(kakaoSignTalkSendInfo==null) continue;
//				kakaoSignTalkSendInfo.setFrstSignDe(fnUnixTimeToStrDate.apply(row.getSigned_data().get(0).getCompleted_at()));
//			}
//			
//
//		}
//		
//	}
//	/**
//	 * <pre>메소드 설명: 인증상태확인 요청 error 처리</pre>
//	 * @param list void 요청처리 후 응답객체
//	 * @author: 박민규
//	 * @date: 2021. 9. 10.
//	 */
//	public void modifySignStatVerifyInfoByError(Map<String, Long> mPkIds, String txId, String sendErrCode, String sendErrMsg) {
//		List<String> list = new ArrayList<String>();
//		list.add(txId);
//		this.modifySignStatVerifyInfoByError(mPkIds, list, sendErrCode, sendErrMsg);
//	}
//	/**
//	 * <pre>메소드 설명: 인증상태확인 요청 error 처리</pre>
//	 * @param list void 요청처리 후 응답객체
//	 * @author: 박민규
//	 * @date: 2021. 9. 10.
//	 */
//	public void modifySignStatVerifyInfoByError(Map<String, Long> mPkIds, List<String> list, String sendErrCode, String sendErrMsg) {
//		for(String txId : list) {
//			/* ================
//			 * 인증상태확인-결과 저장
//		 	 ================ */
//			KakaoSignStatVerifyHist kakaoVerifyHist = kakaoSignStatVerifyHistRepository.findById(mPkIds.get(txId)).orElse(null);
//			kakaoVerifyHist.setStatus(null);    //처리상태(PREPARE: 대기중=서명을 요청한 상태, COMPLETE: 서명완료=비밀번호를 정확히 입력하여 서명을 완료한 상태, EXPIRED: 타임아웃=처리마감시간 동안 서명을 완료하지 않은 상태)                  
//			kakaoVerifyHist.setErrcode(null);  //에러코드                                          
//			kakaoVerifyHist.setErrmsg(null);    //에러메시지
//			
//			kakaoVerifyHist.setSendStatAt("N");  //발송상태(Y:성공, N:실패)
//			kakaoVerifyHist.setSendErrCode(sendErrCode);  //발송에러코드(실패인 경우만) 
//			kakaoVerifyHist.setSendErrMsg(sendErrMsg);    //발송에러메시지(실패인 경우만)
//		}
//		
//	}
//	
//	
//	
//	
//	
//	
//	
//	
//	
//
//	/**
//	 * <pre>메소드 설명: 토큰확인 요청정보 저장</pre>
//	 * @param list void 요청처리 후 응답객체
//	 * @author: 박민규
//	 * @date: 2021. 9. 10.
//	 */
//	@Transactional
//	public Map<String, Long> addTokenVerifyInfoByReq(String txId, String token) {
//		KakaoTokenVerifyHist kakaoTokenVerifyHist = KakaoTokenVerifyHist.builder()
//				.kakaoSignTalkSendInfo(kakaoSignTalkSendInfoRepository.findByTxId(txId).orElse(null))
//				.token(token)
//				.build();
//		kakaoTokenVerifyHistRepository.save(kakaoTokenVerifyHist);
//		
//		Map<String, Long> mPkIds = new HashMap<String, Long>();
//		mPkIds.put(txId, kakaoTokenVerifyHist.getTokenVerifyId()); //식별자
//		return mPkIds;
//	}
//	/**
//	 * <pre>메소드 설명: 토큰확인 요청결과 저장</pre>
//	 * @param list void 요청처리 후 응답객체
//	 * @author: 박민규
//	 * @date: 2021. 9. 10.
//	 */
//	@Transactional
//	public void modifyTokenVerifyInfoByResp(Map<String, Long> mPkIds, KkoTokenStatRespData data) {
//		//Declare Function - 현재날짜
//		Supplier<String> fnGetStrDate = () -> { 
//			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss.SSS");
//			return simpleDateFormat.format(new Date());
//		};
//		
//		//set Querydsl Entity 
////		EntityManager em = BeanUtils.getBean(EntityManager.class);
////		JPAQueryFactory query = new JPAQueryFactory(em);
//		QKakaoSignTalkSendInfo A = new QKakaoSignTalkSendInfo("A");
//		
//		
//
//		
//		/* ================
//		 * 토큰확인 결과 저장
//		 ================ */
//		KakaoTokenVerifyHist kakaoVerifyHist = kakaoTokenVerifyHistRepository.findById(mPkIds.get(data.getTx_id())).orElse(null);
//		kakaoVerifyHist.setSigned_data(data.getSigned_data());           
//		kakaoVerifyHist.setRequest_at(data.getRequest_at());          
//		kakaoVerifyHist.setViewed_at(data.getViewed_at());               
//		kakaoVerifyHist.setCompleted_at(data.getCompleted_at());         
//		kakaoVerifyHist.setExpired_at(data.getExpired_at());             
//		kakaoVerifyHist.setOrg_notified_at(data.getOrg_notified_at());   
//		kakaoVerifyHist.setResult(data.getResult());    //처리결과. 정상접수처리시 "Y", 그외 "N"                    
//		kakaoVerifyHist.setErrcode(data.getErrcode());  //에러코드                                         
//		kakaoVerifyHist.setErrmsg(data.getErrmsg());    //에러메시지                                        
//		kakaoVerifyHist.setSendStatAt("Y");  //발송상태(Y:성공, N:실패)
//		kakaoVerifyHist.setSendErrCode("");  //발송에러코드(실패인 경우만) 
//		kakaoVerifyHist.setSendErrMsg("");   //발송에러메시지(실패인 경우만)
//		
//
//		/* ================
//		 * 발송내역-최초열람일시 저장
//		 ================ */
//		if(!"Y".equals(data.getResult())) return;	//검증에 실패하면.. stop
//		KakaoSignTalkSendInfo kakaoSignTalkSendInfo = query.selectFrom(A)
//				.where(
//						A.txId.eq(data.getTx_id()) //txId가 일치하는 데이터..
//						.and(A.frstReadDe.isEmpty())  //최초열람일시가 비어있는 데이터..
//						)
//				.fetchOne();
//		if(kakaoSignTalkSendInfo==null) return; //최초열람일시가 등록되어 있으면.. stop
//		kakaoSignTalkSendInfo.setFrstReadDe(fnGetStrDate.get());
//	}
//	/**
//	 * <pre>메소드 설명: 토큰확인 요청 error 처리</pre>
//	 * @param list void 요청처리 후 응답객체
//	 * @author: 박민규
//	 * @date: 2021. 9. 10.
//	 */
//	@Transactional
//	public void modifyTokenVerifyInfoByError(Map<String, Long> mPkIds, String txId, String token, String sendErrCode, String sendErrMsg) {
//		KakaoTokenVerifyHist kakaoVerifyHist = kakaoTokenVerifyHistRepository.findById(mPkIds.get(txId)).orElse(null);
//		kakaoVerifyHist.setResult("N");    //처리결과. 정상접수처리시 "Y", 그외 "N"                  
//		kakaoVerifyHist.setErrcode(null);  //에러코드                                          
//		kakaoVerifyHist.setErrmsg(null);    //에러메시지      
//		
//		kakaoVerifyHist.setSendStatAt("N");  //발송상태(Y:성공, N:실패)
//		kakaoVerifyHist.setSendErrCode(sendErrCode);  //발송에러코드(실패인 경우만) 
//		kakaoVerifyHist.setSendErrMsg(sendErrMsg);    //발송에러메시지(실패인 경우만)
//	}
//	
//	
//	
//	
//	
//	
//	
//	
//	
//}
