package cokr.xit.modules.kkomydoc.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;

import cokr.xit.modules.common.code.InternalErrCd;
import cokr.xit.modules.common.code.PostSeCd;
import cokr.xit.modules.common.code.StatCd;
import cokr.xit.modules.common.exception.InternalException;
import cokr.xit.modules.domain.SendMast;
import cokr.xit.modules.domain.SendMastRepository;
import cokr.xit.modules.domain.common.Error;
import cokr.xit.modules.domain.common.FieldError;
import cokr.xit.modules.kkomydoc.domain.NotiDetailsKkoMydoc;
import cokr.xit.modules.kkomydoc.domain.NotiDetailsKkoMydocRepository;
import cokr.xit.modules.kkomydoc.domain.OttVerifyKkoMydoc;
import cokr.xit.modules.kkomydoc.domain.OttVerifyKkoMydocRepository;
import cokr.xit.modules.kkomydoc.domain.ReadCompletedKkoMydoc;
import cokr.xit.modules.kkomydoc.domain.ReadCompletedKkoMydocRepository;
import cokr.xit.modules.kkomydoc.domain.SendDetailKkoMydoc;
import cokr.xit.modules.kkomydoc.domain.SendDetailKkoMydocRepository;
import cokr.xit.modules.kkomydoc.domain.SendResultKkoMydoc;
import cokr.xit.modules.kkomydoc.domain.SendResultKkoMydocRepository;
import cokr.xit.modules.kkomydoc.domain.StatusKkoMydoc;
import cokr.xit.modules.kkomydoc.domain.StatusKkoMydocRepository;
import cokr.xit.modules.kkomydoc.model.BulkSendReqDTO;
import cokr.xit.modules.kkomydoc.model.BulkSendRespDTO;
import cokr.xit.modules.kkomydoc.model.BulkStatusReqDTO;
import cokr.xit.modules.kkomydoc.model.BulkStatusRespDTO;
import cokr.xit.modules.kkomydoc.model.OttRespDTO;
import cokr.xit.modules.kkomydoc.model.SendReqDTO;
import cokr.xit.modules.kkomydoc.model.SendRespDTO;
import cokr.xit.modules.kkomydoc.model.StatusRespDTO;
import cokr.xit.modules.kkomydoc.model.child.BulkStatusDocument;
import cokr.xit.modules.kkomydoc.model.child.Document;
import cokr.xit.modules.kkomydoc.model.child.Documents;
import cokr.xit.modules.kkomydoc.model.child.NotiDocument;
import cokr.xit.utils.CmmnUtil;
import cokr.xit.utils.RequireValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;

@Slf4j
@Service
@RequiredArgsConstructor
public class KkoMyDocService {

	private final KkoMyDocApi kkoMyDocApi;
	private final SendMastRepository sendMastRepository;
	private final SendDetailKkoMydocRepository sendDetailKkoMydocRepository;
	private final SendResultKkoMydocRepository sendResultKkoMydocRepository;
	private final OttVerifyKkoMydocRepository ottVerifyKkoMydocRepository;
	private final ReadCompletedKkoMydocRepository readCompletedKkoMydocRepository;
	private final StatusKkoMydocRepository statusKkoMydocRepository;
	private final NotiDetailsKkoMydocRepository notiDetailsKkoMydocRepository;



	/**
	 * <pre>????????? ??????: ????????? ?????????????????? ?????? ???????????? ??????.
	 * 	-.???????????? ?????? ??? ??????????????? DB??? ????????????.
	 * </pre>
	 * @param vender
	 * @param accessToken
	 * @param contractUuid
	 * @param jsonStr
	 * @return SendMast ???????????? ??? ????????????
	 * @author: ?????????
	 * @date: 2021. 12. 6.
	 */
	@Transactional(noRollbackFor = Exception.class)
	public SendRespDTO send(String vender, String accessToken, String contractUuid, String jsonStr) {
		System.out.println("send() called...");

		//????????????(?????? -> ????????? -> ????????? -> ??????)

		SendReqDTO reqDTO = null;
		SendRespDTO sendRespDTO = null;
		SendMast sendMast = null;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		final String externalDocumentUuid = String.format("S-%s%s%09d", simpleDateFormat.format(new Date()), RandomString.make(5), 1);
		try {
			/* ========================
			 * parse
			 ======================== */
			try {
				ObjectMapper mapper = new ObjectMapper();
				reqDTO = mapper.readValue(jsonStr, SendReqDTO.class);
			} catch (JsonProcessingException e) {
				throw new InternalException(InternalErrCd.ERR504, String.format("??????????????? parse ??????:: %s", e.getMessage()), e);
			}


			/* ========================
			 * validate
			 ======================== */
			try {
				RequireValidator.builder()
				.obj(reqDTO)
				.build()
				.validate()
				.throwableException();
			} catch (Exception e) {
				throw new InternalException(InternalErrCd.ERR401, String.format("????????? ?????? ??????:: %s", e.getMessage()), e);
			}



			/* ========================
			 * insert of request info(??????????????? ??????)
			 ======================== */
			sendMast = SendMast.builder()
					.vender(vender)            //???????????????
					.postSe(PostSeCd.kkoMydoc) //???????????????(mydoc: ????????????(??? ?????????), bill: ?????????, Etc...)
					.postTitle(reqDTO.getDocument().getTitle())        //????????????
					.statCd(StatCd.accept)     //??????(accept: ????????????, idle:????????????, success:????????????/fail:????????????, open:?????????, close:??????????????????, Etc...)
					.sendCnt(1)                //????????????
					.build();
			sendMastRepository.save(sendMast);
			SendDetailKkoMydoc sendDetail = SendDetailKkoMydoc.builder()
					.sendMast(sendMast)
					.title(reqDTO.getDocument().getTitle())
					.message(reqDTO.getDocument().getProperty().getMessage())
					.ci(reqDTO.getDocument().getReceiver().getCi())
					.phoneNumber(reqDTO.getDocument().getReceiver().getPhone_number())
					.name(reqDTO.getDocument().getReceiver().getName())
					.birthday(reqDTO.getDocument().getReceiver().getBirthday())
					.payload(reqDTO.getDocument().getProperty().getPayload())
					.externalDocumentUuid(externalDocumentUuid)
					.link(reqDTO.getDocument().getProperty().getLink())
					.build();
			sendDetailKkoMydocRepository.save(sendDetail);




			/* ========================
			 * call
			 ======================== */
			ResponseEntity<String> resp = kkoMyDocApi.send(accessToken, contractUuid, jsonStr);


			/* ========================
			 * result set
			 ======================== */
			try {
				ObjectMapper mapper = new ObjectMapper();
				sendRespDTO = mapper.readValue(resp.getBody(), SendRespDTO.class);

				if(resp.getStatusCode() != HttpStatus.OK) {	//?????? ?????? ??????..
					sendRespDTO.setError_code(String.format("%s %s", resp.getStatusCodeValue(), sendRespDTO.getError_code()));
				}
			} catch (JsonProcessingException e) {
				sendRespDTO = new SendRespDTO();
				sendRespDTO.setError_code(String.format("%s %s", HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.name()));
				sendRespDTO.setError_message(String.format("api ??????????????? ?????? ??????:: %s", e.getMessage()));
			}

			/* ========================
			 * insert of response info(??????????????? ??????)
			 ======================== */
			SendResultKkoMydoc sendResult = SendResultKkoMydoc.builder()
					.sendMast(sendMast)
					.documentBinderUuid(sendRespDTO.getDocument_binder_uuid())
					.externalDocumentUuid(externalDocumentUuid)
					.error(FieldError.initBuilder()
							.errorCode(sendRespDTO.getError_code())
							.errorMessage(sendRespDTO.getError_message())
							.build())
					.build();
			sendResultKkoMydocRepository.save(sendResult);


			/* ========================
			 * update SendMast of status(???????????? or ????????????)
			 ======================== */
			if(sendDetailKkoMydocRepository.countSuccessBySendMastId(sendMast.getSendMastId())>0)	//1??? ?????? ????????? ???????????????..
				sendMast.setSendStatus(StatCd.success);
			else //?????? ???????????????..
				sendMast.setSendStatus(StatCd.fail);


		} catch (InternalException e) {
			log.error(String.format("\n### Error Message is... ###\n %s \n### request is... ###\n [vender %s accessToken %s contractUuid %s ] \n### jsonStr is... ###\n %s"
					, e.getMessage()
					, vender
					, accessToken
					, contractUuid
					, jsonStr
					), e);
			throw e;
		} catch (Exception e) {
			log.error(String.format("\n### Error Message is... ###\n %s \n### request is... ###\n [vender %s accessToken %s contractUuid %s ] \n### jsonStr is... ###\n %s"
					, e.getMessage()
					, vender
					, accessToken
					, contractUuid
					, jsonStr
					), e);
		} finally {
			/* ========================
			 * insert NotiDetails
			 ======================== */
			if(reqDTO != null)
				if(reqDTO.getNotidocument() != null) {	//????????????????????? ?????????...
					NotiDetailsKkoMydoc notiDetilas = NotiDetailsKkoMydoc.builder()
							.sendMast(sendMast)
							.payload(reqDTO.getNotidocument().getPayload())
							.details(String.format("{\"details\" : %s}", CmmnUtil.toJsonString(reqDTO.getNotidocument().getDetails())))
							.build();

					notiDetailsKkoMydocRepository.save(notiDetilas);
				}
		}


		return sendRespDTO;
	}




	/**
	 * <pre>????????? ??????: ???????????????(One Time Token) ????????? ?????? ??????.
	 * 	-.???????????? ?????? ??? ??????????????? DB??? ????????????.
	 * </pre>
	 * @param accessToken
	 * @param documentBinderUuid
	 * @param token void ???????????? ??? ????????????
	 * @author: ?????????
	 * @date: 2021. 12. 6.
	 */
	@SuppressWarnings("deprecation")
	@Transactional(noRollbackFor = Exception.class)
	public OttRespDTO token(String accessToken, String documentBinderUuid, String token) {

		OttRespDTO ottRespDTO = null;
		try {
			/* ========================
			 * validate
		 	 ======================== */
			if(StringUtils.isEmpty(accessToken))
				throw new InternalException(InternalErrCd.ERR401, "???????????????(???)??? ???????????? ?????????.");
			if(StringUtils.isEmpty(documentBinderUuid))
				throw new InternalException(InternalErrCd.ERR401, "??????????????????(???)??? ???????????? ?????????.");
			if(StringUtils.isEmpty(token))
				throw new InternalException(InternalErrCd.ERR401, "??????(???)??? ???????????? ?????????.");



			/* ========================
			 * insert of request info(??????????????? ??????)
		 	 ======================== */
			OttVerifyKkoMydoc ottVerifyKkoMydoc = OttVerifyKkoMydoc.builder()
					.documentBinderUuid(documentBinderUuid)
					.token(token)
					.build();
			ottVerifyKkoMydocRepository.save(ottVerifyKkoMydoc);


			/* ========================
			 * call
		 	 ======================== */
			ResponseEntity<String> resp = kkoMyDocApi.token(accessToken, documentBinderUuid, token);


			/* ========================
			 * result set
		 	 ======================== */
			try {
				ObjectMapper mapper = new ObjectMapper();
				ottRespDTO = mapper.readValue(resp.getBody(), OttRespDTO.class);
				if(resp.getStatusCode() != HttpStatus.OK) {	//?????? ?????? ??????..
					ottRespDTO.setError_code(String.format("%s %s", resp.getStatusCodeValue(), ottRespDTO.getError_code()));
				}
			} catch (JsonProcessingException e) {
				ottRespDTO = new OttRespDTO();
				ottRespDTO.setError_code(String.format("%s %s", HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.name()));
				ottRespDTO.setError_message(String.format("api ??????????????? ?????? ??????:: %s", e.getMessage()));
			}


			/* ========================
			 * update of response info(??????????????? ??????)
		 	 ======================== */
			ottVerifyKkoMydoc.setResponse(ottRespDTO);


			/* ========================
			 * update SendDetail of first TokenVerifyTime
			 ======================== */
			if("USED".equals(ottRespDTO.getToken_status())) {	//??????????????? ??????(USED) ??????..
				sendDetailKkoMydocRepository.modifyFrstTokenVerifyDtByDocumentBinderUuid(ottRespDTO.getToken_used_at(), documentBinderUuid);
			}


		} catch (InternalException e) {
			log.error(String.format("\n### Error Message is... ###\n %s \n### request is... ###\n [ accessToken %s documentBinderUuid %s token %s ]"
					, e.getMessage()
					, accessToken
					, documentBinderUuid
					, token
					), e);
			throw e;

		} catch (Exception e) {
			log.error(String.format("\n### Error Message is... ###\n %s \n### request is... ###\n [ accessToken %s documentBinderUuid %s token %s ]"
					, e.getMessage()
					, accessToken
					, documentBinderUuid
					, token
					), e);
		}


		return ottRespDTO;

	}




	/**
	 * <pre>????????? ??????: ????????????API??? ???????????? "??????" ?????? ??? ????????????????????? ?????? ??????.
	 * 	-.?????????????????? ?????? ??? ??????????????? DB??? ????????????.
	 * 	-.????????????API??? ?????? ??? ????????????API??? ???????????? ?????????????????? ??? ????????????????????? ???????????? ?????? ??????.
	 * </pre>
	 * @param accessToken
	 * @param documentBinderUuid void ???????????? ??? ????????????
	 * @author: ?????????
	 * @date: 2021. 12. 6.
	 */
	@SuppressWarnings("deprecation")
	@Transactional(noRollbackFor = Exception.class)
	public Error readCompleted(String accessToken, String documentBinderUuid) {

		Error respData = new Error();
		try {
			/* ========================
			 * validate
		 	 ======================== */
			if(StringUtils.isEmpty(documentBinderUuid))
				throw new InternalException(InternalErrCd.ERR401, "??????????????????(???)??? ???????????? ?????????.");



			/* ========================
			 * insert of request info(??????????????? ??????)
		 	 ======================== */
			ReadCompletedKkoMydoc readCompletedKkoMydoc = ReadCompletedKkoMydoc.builder()
					.documentBinderUuid(documentBinderUuid)
					.build();
			readCompletedKkoMydocRepository.save(readCompletedKkoMydoc);


			/* ========================
			 * call
		 	 ======================== */
			ResponseEntity<String> resp = kkoMyDocApi.readCompleted(accessToken, documentBinderUuid);


			/* ========================
			 * result set
		 	 ======================== */
			try {
				ObjectMapper mapper = new ObjectMapper();
				if(resp.getStatusCode() != HttpStatus.NO_CONTENT) {	//?????? ?????? ??????..
					respData = mapper.readValue(resp.getBody(), Error.class);
					respData.setError_code(String.format("%s %s", resp.getStatusCodeValue(), respData.getError_code()));
				}
			} catch (JsonProcessingException e) {
				respData = new Error();
				respData.setError_code(String.format("%s %s", HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.name()));
				respData.setError_message(String.format("api ??????????????? ?????? ??????:: %s", e.getMessage()));
			}


			/* ========================
			 * update of response info(??????????????? ??????)
		 	 ======================== */
			readCompletedKkoMydoc.setResponse(respData);


		} catch (InternalException e) {
			log.error(String.format("\n### Error Message is... ###\n %s \n### request is... ###\n [ accessToken %s documentBinderUuid %s ]"
					, e.getMessage()
					, accessToken
					, documentBinderUuid
					), e);
			throw e;

		} catch (Exception e) {
			log.error(String.format("\n### Error Message is... ###\n %s \n### request is... ###\n [ accessToken %s documentBinderUuid %s ]"
					, e.getMessage()
					, accessToken
					, documentBinderUuid
					), e);
		}

		return respData;
	}



	/**
	 * <pre>????????? ??????: ???????????? ?????? ???????????? ?????? ??? ????????? ?????? ??????.
	 * 	-.???????????? ?????? ??? ??????????????? DB??? ????????????.
	 * 	-.???????????? ???????????? ???????????? ????????????.
	 * </pre>
	 * @param accessToken
	 * @param contractUuid
	 * @param documentBinderUuid void ???????????? ??? ????????????
	 * @author: ?????????
	 * @date: 2021. 12. 6.
	 */
	@SuppressWarnings("deprecation")
	@Transactional(noRollbackFor = Exception.class)
	public StatusRespDTO status(String accessToken, String contractUuid, String documentBinderUuid) {

		StatusRespDTO respData = null;
		try {
			/* ========================
			 * validate
		 	 ======================== */
			if(StringUtils.isEmpty(documentBinderUuid))
				throw new InternalException(InternalErrCd.ERR401, "??????????????????(???)??? ???????????? ?????????.");



			/* ========================
			 * insert of request info(??????????????? ??????)
		 	 ======================== */
			StatusKkoMydoc statusKkoMydoc = StatusKkoMydoc.builder()
					.documentBinderUuid(documentBinderUuid)
					.build();
			statusKkoMydocRepository.save(statusKkoMydoc);


			/* ========================
			 * call
		 	 ======================== */
			ResponseEntity<String> resp = kkoMyDocApi.status(accessToken, contractUuid, documentBinderUuid);


			/* ========================
			 * result set
		 	 ======================== */
			try {
				ObjectMapper mapper = new ObjectMapper();
				respData = mapper.readValue(resp.getBody(), StatusRespDTO.class);
				if(resp.getStatusCode() != HttpStatus.OK) {	//?????? ?????? ??????..
					respData.setError_code(String.format("%s %s", resp.getStatusCodeValue(), respData.getError_code()));
				}
			} catch (JsonProcessingException e) {
				respData = new StatusRespDTO();
				respData.setError_code(String.format("%s %s", HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.name()));
				respData.setError_message(String.format("api ??????????????? ?????? ??????:: %s", e.getMessage()));
			}


			/* ========================
			 * update of response info(??????????????? ??????)
		 	 ======================== */
			statusKkoMydoc.setResponse(respData);


			/* ========================
			 * update SendDetail of StatusInfo
			 ======================== */
			sendDetailKkoMydocRepository.modifyStatusInfoByDocumentBinderUuid(respData, documentBinderUuid);

		} catch (InternalException e) {
			log.error(String.format("\n### Error Message is... ###\n %s \n### request is... ###\n [ accessToken %s contractUuid %s documentBinderUuid %s ]"
					, e.getMessage()
					, accessToken
					, contractUuid
					, documentBinderUuid
					), e);
			throw e;

		} catch (Exception e) {
			log.error(String.format("\n### Error Message is... ###\n %s \n### request is... ###\n [ accessToken %s contractUuid %s documentBinderUuid %s ]"
					, e.getMessage()
					, accessToken
					, contractUuid
					, documentBinderUuid
					), e);
		}


		return respData;
	}



	/**
	 * <pre>????????? ??????: ????????? ?????????????????? ?????? ???????????? ??????.
	 * 	-.???????????? ?????? ??? ??????????????? DB??? ????????????.
	 * </pre>
	 * @param vender
	 * @param accessToken
	 * @param contractUuid
	 * @param jsonStr void ???????????? ??? ????????????
	 * @author: ?????????
	 * @date: 2021. 12. 6.
	 */
	@SuppressWarnings("deprecation")
	@Transactional(noRollbackFor = Exception.class)
	public BulkSendRespDTO bulkSend(String vender, String accessToken, String contractUuid, String jsonStr) {
		System.out.println("bulkSend() called...");

		BulkSendReqDTO reqDTO = null;
		BulkSendRespDTO bulkSendRespDTO = null;
		SendMast sendMast = null;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String prefixExternalDocumentUuid = String.format("B-%s%s", simpleDateFormat.format(new Date()), RandomString.make(5));
		try {
			/* ========================
			 * parse
			 ======================== */
			try {
				ObjectMapper mapper = new ObjectMapper();
				reqDTO = mapper.readValue(jsonStr, BulkSendReqDTO.class);
			} catch (JsonProcessingException e) {
				throw new InternalException(InternalErrCd.ERR504, String.format("??????????????? parse ??????:: %s", e.getMessage()), e);
			}


			/* ========================
			 * validate
			 ======================== */
			try {
				RequireValidator.builder()
				.obj(reqDTO)
				.build()
				.validate()
				.throwableException();
			} catch (Exception e) {
				throw new InternalException(InternalErrCd.ERR401, String.format("????????? ?????? ??????:: %s", e.getMessage()), e);
			}



			/* ========================
			 * insert of request info(??????????????? ??????)
			 ======================== */
			sendMast = SendMast.builder()
					.vender(vender)                         //???????????????
					.postSe(PostSeCd.kkoMydoc)              //???????????????(mydoc: ????????????(??? ?????????), bill: ?????????, Etc...)
					.postTitle(reqDTO.getDocuments().get(0).getTitle()+"...")  //????????????
					.statCd(StatCd.accept)                  //??????(accept: ????????????, idle:????????????, success:????????????/fail:????????????, open:?????????, close:??????????????????, Etc...)
					.sendCnt(reqDTO.getDocuments().size())  //????????????
					.build();
			int seq = 0;
			sendMastRepository.save(sendMast);
			for(Document row : reqDTO.getDocuments()) {
				String externalDocumentUuid = row.getProperty().getExternal_document_uuid();
				if(StringUtils.isEmpty(externalDocumentUuid)) //?????????????????? ?????????..
					externalDocumentUuid = String.format("%s%09d", prefixExternalDocumentUuid, ++seq);
				row.getProperty().setExternal_document_uuid(externalDocumentUuid);	//?????????????????? ??????

				SendDetailKkoMydoc sendDetail = SendDetailKkoMydoc.builder()
						.sendMast(sendMast)
						.title(row.getTitle())
						.message(row.getProperty().getMessage())
						.ci(row.getReceiver().getCi())
						.phoneNumber(row.getReceiver().getPhone_number())
						.name(row.getReceiver().getName())
						.birthday(row.getReceiver().getBirthday())
						.payload(row.getProperty().getPayload())
						.externalDocumentUuid(externalDocumentUuid)	//???????????????????????? ??????
						.link(row.getProperty().getLink())
						.build();
				sendDetailKkoMydocRepository.save(sendDetail);
			};
			/* ========================
			 * convert to jsonString
			 ======================== */
			try {
				ObjectMapper mapper = new ObjectMapper();
				jsonStr = mapper.writeValueAsString(reqDTO);
			} catch (JsonProcessingException e) {
				throw new InternalException(InternalErrCd.ERR504, String.format("??????????????? jsonString ?????? ??????:: %s", e.getMessage()), e);
			}



			/* ========================
			 * call
			 ======================== */
			ResponseEntity<String> resp = kkoMyDocApi.bulkSend(accessToken, contractUuid, jsonStr);


			/* ========================
			 * result set
			 ======================== */
			try {
				ObjectMapper mapper = new ObjectMapper();
				bulkSendRespDTO = mapper.readValue(resp.getBody(), BulkSendRespDTO.class);
				if(resp.getStatusCode() != HttpStatus.OK) {	//?????? ?????? ??????..
					bulkSendRespDTO.setError_code(String.format("%s %s", resp.getStatusCodeValue(), bulkSendRespDTO.getError_code()));
				}

			} catch (JsonProcessingException e) {
				bulkSendRespDTO = new BulkSendRespDTO();
				bulkSendRespDTO.setError_code(String.format("%s %s", HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.name()));
				bulkSendRespDTO.setError_message(String.format("api ??????????????? ?????? ??????:: %s", e.getMessage()));
			}


			/* ========================
			 * insert of response info(??????????????? ??????)
			 ======================== */
			SendResultKkoMydoc sendResult = null;
			if(bulkSendRespDTO.getDocuments() == null) {	//????????? ???????????????...
				for(Document row : reqDTO.getDocuments()) {
					sendResult = SendResultKkoMydoc.builder()
							.sendMast(sendMast)
							.documentBinderUuid(null)
							.externalDocumentUuid(row.getProperty().getExternal_document_uuid())
							.error(FieldError.initBuilder()
									.errorCode(bulkSendRespDTO.getError_code())
									.errorMessage(bulkSendRespDTO.getError_message())
									.build()
									)
							.build();
					sendResultKkoMydocRepository.save(sendResult);
				}
			}else {	//api ????????????????????? ??????????????? ????????? ????????????..
				for(Documents row : bulkSendRespDTO.getDocuments()) {
					sendResult = SendResultKkoMydoc.builder()
							.sendMast(sendMast)
							.documentBinderUuid(row.getDocument_binder_uuid())
							.externalDocumentUuid(row.getExternal_document_uuid())
							.error(FieldError.initBuilder()
									.errorCode(row.getError_code())
									.errorMessage(row.getError_message())
									.build()
									)
							.build();
					sendResultKkoMydocRepository.save(sendResult);
				}
			}


			/* ========================
			 * update SendMast of status(???????????? or ????????????)
			 ======================== */
			if(sendDetailKkoMydocRepository.countSuccessBySendMastId(sendMast.getSendMastId())>0)	//1??? ?????? ????????? ???????????????..
				sendMast.setSendStatus(StatCd.success);
			else //?????? ???????????????..
				sendMast.setSendStatus(StatCd.fail);

		} catch (InternalException e) {
			log.error(String.format("\n### Error Message is... ###\n %s \n### request is... ###\n [vender %s accessToken %s contractUuid %s ] \n### jsonStr is... ###\n %s"
					, e.getMessage()
					, vender
					, accessToken
					, contractUuid
					, jsonStr
					), e);
			throw e;

		} catch (Exception e) {
			log.error(String.format("\n### Error Message is... ###\n %s \n### request is... ###\n [vender %s accessToken %s contractUuid %s ] \n### jsonStr is... ###\n %s"
					, e.getMessage()
					, vender
					, accessToken
					, contractUuid
					, jsonStr
					), e);

		} finally {
			/* ========================
			 * insert NotiDetails
			 ======================== */
			if(reqDTO != null)
				if(reqDTO.getNotidocuments() != null)	//????????????????????? ?????????...
					for(NotiDocument row : reqDTO.getNotidocuments()) {
						NotiDetailsKkoMydoc notiDetilas = NotiDetailsKkoMydoc.builder()
								.sendMast(sendMast)
								.payload(row.getPayload())
								.details(String.format("{\"details\" : %s}", CmmnUtil.toJsonString(row.getDetails())))
								.build();

						notiDetailsKkoMydocRepository.save(notiDetilas);
					}
		}

		return bulkSendRespDTO;

	}




	/**
	 * <pre>????????? ??????: ????????? ???????????? ?????? ???????????? ?????? ??? ????????? ?????? ??????.
	 * 	-.???????????? ?????? ??? ??????????????? DB??? ????????????.
	 * 	-.???????????? ???????????? ???????????? ????????????.
	 * </pre>
	 * @param accessToken
	 * @param contractUuid
	 * @param jsonStr void ???????????? ??? ????????????
	 * @author:?????????
	 * @date: 2021. 12. 6.
	 */
	@Transactional(noRollbackFor = Exception.class)
	public BulkStatusRespDTO bulkStatus(String accessToken, String contractUuid, String jsonStr) {

		BulkStatusReqDTO reqDTO = null;
		BulkStatusRespDTO bulkStatusRespDTO = null;
		Map<String, StatusKkoMydoc> dataset = null;
		try {
			/* ========================
			 * parse
			 ======================== */
			try {
				ObjectMapper mapper = new ObjectMapper();
				reqDTO = mapper.readValue(jsonStr, BulkStatusReqDTO.class);
			} catch (JsonProcessingException e) {
				throw new InternalException(InternalErrCd.ERR504, String.format("??????????????? parse ??????:: %s", e.getMessage()), e);
			}


			/* ========================
			 * validate
		 	 ======================== */
			try {
				RequireValidator.builder()
				.obj(reqDTO)
				.build()
				.validate()
				.throwableException();
			} catch (Exception e) {
				throw new InternalException(InternalErrCd.ERR401, String.format("????????? ?????? ??????:: %s", e.getMessage()), e);
			}



			/* ========================
			 * insert of request info(??????????????? ??????)
		 	 ======================== */
			dataset = new LinkedHashMap<String, StatusKkoMydoc>();
			for(String documentBinderUuid : reqDTO.getDocument_binder_uuids())
				dataset.put(documentBinderUuid, StatusKkoMydoc.builder().documentBinderUuid(documentBinderUuid).build());


			/* ========================
			 * call
		 	 ======================== */
			ResponseEntity<String> resp = kkoMyDocApi.bulkStatus(accessToken, contractUuid, jsonStr);


			/* ========================
			 * result set
		 	 ======================== */
			try {
				ObjectMapper mapper = new ObjectMapper();
				bulkStatusRespDTO = mapper.readValue(resp.getBody(), BulkStatusRespDTO.class);
				if(resp.getStatusCode() != HttpStatus.OK) {	//?????? ?????? ??????..
					Iterator<String> it = dataset.keySet().iterator();
					while(it.hasNext()) {
						//set Result
						StatusRespDTO statusRespDTO = new StatusRespDTO();
						statusRespDTO.setError_code(String.format("%s", resp.getStatusCodeValue()));
						statusRespDTO.setError_message(resp.getBody());
						//set Error
						String key = it.next();
						dataset.get(key).setResponse(statusRespDTO);
					}

				} else {	//???????????? ??????..
					//set StatusInfo
					for(BulkStatusDocument row : bulkStatusRespDTO.getDocuments()) {
						if(row.getError_code()==null) {
							dataset.get(row.getDocument_binder_uuid()).setResponse(row.getStatus_data());
						}else {
							//set Result
							StatusRespDTO statusRespDTO = new StatusRespDTO();
							statusRespDTO.setError_code(String.format("%s %s", resp.getStatusCodeValue(), row.getError_code()));
							statusRespDTO.setError_message(row.getError_message());
							dataset.get(row.getDocument_binder_uuid()).setResponse(statusRespDTO);
						}
					}
				}
			} catch (JsonProcessingException e) {
				Iterator<String> it = dataset.keySet().iterator();
				while(it.hasNext()) {
					//set Result
					StatusRespDTO statusRespDTO = new StatusRespDTO();
					statusRespDTO.setError_code(String.format("%s %s", HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.name()));
					statusRespDTO.setError_message(String.format("api ??????????????? ?????? ??????:: %s", e.getMessage()));
					//set Error
					String key = it.next();
					dataset.get(key).setResponse(statusRespDTO);
				}
			}


			/* ========================
			 * update of response info(??????????????? ??????)
		 	 ======================== */
//			statusKkoMydoc.setResponse(respData);


			/* ========================
			 * update SendDetail of StatusInfo
			 ======================== */
//			sendDetailKkoMydocRepository.modifyStatusInfoByDocumentBinderUuid(respData, documentBinderUuid);

		} catch (InternalException e) {
			log.error(String.format("\n### Error Message is... ###\n %s \n### request is... ###\n [ accessToken %s contractUuid %s ] \n### jsonStr is... ###\n %s"
					, e.getMessage()
					, accessToken
					, contractUuid
					, jsonStr
					), e);
			throw e;

		} catch (Exception e) {
			log.error(String.format("\n### Error Message is... ###\n %s \n### request is... ###\n [ accessToken %s contractUuid %s ] \n### jsonStr is... ###\n %s"
					, e.getMessage()
					, accessToken
					, contractUuid
					, jsonStr
					), e);
		} finally {
			/* ========================
			 * Query flush
			 ======================== */
			if(dataset != null) {
				dataset.forEach((key, data) -> {
					//insert STATUS_KKO_MYDOC of Request and Response
					statusKkoMydocRepository.save(data);
					//update SendDetail of StatusInfo
//					sendDetailKkoMydocRepository.modifyStatusInfoByDocumentBinderUuid(data);
					SendDetailKkoMydoc detail = sendDetailKkoMydocRepository.findByDocumentBinderUuid(data.getDocumentBinderUuid());
					if(detail != null)
						detail.setStatusInfo(data);
				});

			}
		}


		return bulkStatusRespDTO;
	}


	/**
	 * <pre>????????? ??????: ??????????????? ?????? ??????.
	 *	-.??????????????? ?????? ?????? ?????? "??????" ????????? ????????????.
	 *	-.?????? ?????? ??? 1??? ?????? ???????????? ?????? "??????" ????????? ????????????.
	 *	-.?????????????????? ?????? ???????????????????????? ??????????????? ?????? ??????.
	 * </pre>
	 * @param accessToken
	 * @param contractUuid
	 * @param sendMastId void ???????????? ??? ????????????
	 * @author: ?????????
	 * @date: 2021. 12. 6.
	 */
	@Transactional(noRollbackFor = Exception.class)
	public List<Error> modifySendMastCurStatBySendMastId(String accessToken, String contractUuid, Long sendMastId) {


		List<Error> respDataList = new ArrayList<>();
		Error respData = new Error();
		try {
			//???????????? ?????? ??????
			List<String> list = sendResultKkoMydocRepository.findDocumentBinderUuidBySendMastId(sendMastId);
			if(list.size()==0)
				throw new InternalException(InternalErrCd.ERR404, "??????????????? ?????? ????????? ???????????? ????????? ????????????.");

			//???????????? ??????
			List<List<String>> listByDocumentBinderUuid = Lists.partition(list, 100);	//100?????? ??????
			for(List<String> bundle : listByDocumentBinderUuid) {

				BulkStatusReqDTO reqDTO = new BulkStatusReqDTO();
				reqDTO.setDocument_binder_uuids(bundle);
				String jsonStr = CmmnUtil.toJsonString(reqDTO);

				//????????????api ??????
				BulkStatusRespDTO bulkStatusRespDTO = this.bulkStatus(accessToken, contractUuid, jsonStr);

				//??????????????? ?????? ??????
				sendMastRepository.modifyStatCdAndReadCntBySendMastId(sendMastId);

				respData = bulkStatusRespDTO;

				respDataList.add(respData);
			}



		} catch (InternalException e) {
			respData.setError_code(e.getErrCd().name());
			respData.setError_message(e.getMessage());
			respDataList.add(respData);
		} catch (Exception e) {
			respData.setError_code(InternalErrCd.ERR999.name());
			respData.setError_message(e.getMessage());
			respDataList.add(respData);
		}

		return respDataList;

	}
}
