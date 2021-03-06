//package cokr.xit.modules.post.domains.bill.service;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.util.StringUtils;
//
//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import cokr.xit.modules.post.code.InternalErrCd;
//import cokr.xit.modules.post.code.PaymentStatCd;
//import cokr.xit.modules.post.constant.ApiAdrsInf;
//import cokr.xit.modules.post.constant.PostConst;
//import cokr.xit.modules.post.domains.bill.domain.KakaoBillSendInfo;
//import cokr.xit.modules.post.domains.bill.domain.KakaoBillSendInfoRepository;
//import cokr.xit.modules.post.domains.bill.dto.kko.KkoSendnNotiReqDTO;
//import cokr.xit.modules.post.exception.InternalException;
//import cokr.xit.modules.utils.CmmnUtil;
//import cokr.xit.modules.utils.RequireValidator;
//import lombok.Builder;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@RequiredArgsConstructor
//@Builder
//public class KkoSender {
//
//	private final String billerCode;
//	private final ApiAdrsInf apiAdrsInf;
//	private final List<KkoSendnNotiReqDTO> sendDatalist;
//	private final KakaoBillSendInfoRepository kakaoBillSendInfoRepository;
//	
//	KkoSender(String billerCode, ApiAdrsInf apiAdrsInf, KkoSendnNotiReqDTO dto, KakaoBillSendInfoRepository kakaoBillSendInfoRepository){
//		this.billerCode = billerCode;
//		this.apiAdrsInf = apiAdrsInf;
//		this.sendDatalist = Arrays.asList(dto);
//		this.kakaoBillSendInfoRepository = kakaoBillSendInfoRepository;
//	}
//	
//	
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	public void execute(){
////		list.parallelStream()
////		.map(dto -> validate(dto))
////		.flatMap(feature -> feature.join().parallelStream())
////		.map(dto -> makeSendData(dto))
////		;
//		HttpHeaders headers = this.makeHeaders();
//		ObjectMapper mapper = new ObjectMapper();
//		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);	//null ?????? ??????
//		sendDatalist.parallelStream().forEach(dto -> {
//			
//			String jsonStrSend = null;
//			String jsonStrNoti = null;
//			String resCode = null;
//			String message = null;
//			String url = null;	
//			
//			log.debug(String.format("????????????:::: %s", Thread.currentThread().getName()));
//			try {
//				/* ==================
//				 * ????????? ??????
//				 ================== */
//				try {
//					this.validate(dto);
//				} catch (Exception e) {
//					throw new InternalException(InternalErrCd.ERR402, e.getMessage()); 
//				}
//				/* ==================
//				 * ??????????????? json ??????
//				 ================== */
//				try {
//					dto.getSend().getData().getParameters().put(PostConst.KkoParamBillerCode, billerCode);
//					jsonStrSend = CmmnUtil.toJsonString(dto.getSend(), JsonInclude.Include.NON_EMPTY);  //????????????????????? ?????? ?????????
//					jsonStrNoti = CmmnUtil.toJsonString(dto.getNoti(), JsonInclude.Include.NON_EMPTY);  //??????????????? ????????? ?????? ?????? ?????????
//				} catch (Exception e) {
//					throw new InternalException(InternalErrCd.ERR504, e.getMessage()); 
//				}
//				
//				/* ==================
//				 * ??????
//				 ================== */
//				try {
//					ResponseEntity<Map> result = this.sendData(jsonStrSend, headers);
//					resCode = String.valueOf(result.getBody().get("res_code"));
//					message = String.valueOf(result.getBody().get("message"));
//					
//					if(!"OK".equals(resCode))	//?????? ????????? ????????????..
//						throw new InternalException(InternalErrCd.ERR601, String.format("[%s]%s", resCode, message));
//					
//					//get ?????????
//					Map<String, String> data = mapper.readValue((String)result.getBody().get("data"), Map.class);
//					url = data.get("url");	//?????????URL
//				} catch (InternalException e) {
//					throw e;
//				}
//				
//				
//			} catch (InternalException e) {
//				resCode = e.getErrCd().getCode();
//				message = e.getErrCd().getCodeNm();
//			} catch (Exception e) {
//				resCode = InternalErrCd.ERR001.getCode();
//				message = InternalErrCd.ERR001.getCodeNm();
//			} finally {
//				/* ==================
//				 * ????????????&?????? ??????
//				 ================== */
//				KakaoBillSendInfo kakaoBillSendInfo = KakaoBillSendInfo.builder()
//						.billerCode(this.billerCode)                                 //?????? ??????
//						.billerUserKey(dto.getSend().getData().getBiller_user_key()) //?????? ?????? key
//						.expireAt(dto.getSend().getData().getExpire_at())            //url ?????? ??????
//						.paymentStatCd(PaymentStatCd.unpaid)                         //?????? ??????
//						.parameters(jsonStrSend)                                     //????????????????????? ????????????
//						
//						.resCode(resCode)                                            //?????? ????????????
//						.message(message)                                            //?????? ???????????????
//						.returnUrl(url)                                              //????????? URL
//						.notiInfo(jsonStrNoti)                                       //????????? ??????
//						.userBirth(dto.getUserBirth())                               //????????? ????????????(yyyyMMdd)
//						
//						.build();
//				kakaoBillSendInfoRepository.save(kakaoBillSendInfo);
//			}
//			
//
//		});
//		
//	}
//	
//	/**
//	 * ????????? ??????
//	 * @return
//	 */
//	@SuppressWarnings("deprecation")
//	protected void validate(KkoSendnNotiReqDTO dto) throws Exception{
//		//????????? ?????? ?????? param
//		RequireValidator.builder()
//		.obj(dto.getSend().getData())
//		.build()
//		.validate()
//		.throwableException();
//		
//		//????????? ?????? param
//		if(!StringUtils.isEmpty(dto.getNoti().getData())) {
//			RequireValidator.builder()
//			.obj(dto.getNoti().getData())
//			.build()
//			.validate()
//			.throwableException();
//			
//			if(dto.getSend().getData().getBiller_user_key().equals(dto.getNoti().getData().getResult().getBiller_user_key()))
//				throw new RuntimeException(String.format("????????? biller_user_key ?????? ???????????? ????????????.(%s/%s)"
//							,dto.getSend().getData().getBiller_user_key()
//							,dto.getNoti().getData().getResult().getBiller_user_key()
//							));
//		}
//	}
//	
//	/**
//	 * ????????????
//	 * @return
//	 */
//	protected HttpHeaders makeHeaders() {
//		HttpHeaders header = new HttpHeaders();
////		header.add("X-Naver-Client_Id"    , billerCode);
////		header.add("X-Naver-Client-Secret", clientSecret);
//		header.setContentType(MediaType.APPLICATION_JSON);
//		
//		return header;
//		
//	}
//	
//	/**
//	 * ????????? ??????
//	 * @param param
//	 * @param headers
//	 * @return
//	 * @throws Exception
//	 */
//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	protected ResponseEntity<Map> sendData(String param, HttpHeaders headers) throws Exception{
////		ApiAdrsInf apiAdrsInf = ApiAdrsInf.KKO_BILL_REQ;
//		Map<String, Object> resp = CmmnUtil.callApi(apiAdrsInf.getMethod(), apiAdrsInf.getUrl()+"/"+billerCode, param, headers);
//		
//
//		//?????? ???????????? ??????
//		if(!"OK".equals(resp.get("resCd"))) //????????? ????????????..
//			throw new InternalException(InternalErrCd.valueOf((String) resp.get("resCd")), String.format("???????????? ?????? API ?????? ??????. %s", resp.get("resMsg")));
//		
//		return (ResponseEntity<Map>) resp.get("responseEntity");
//	}
//	
//}
