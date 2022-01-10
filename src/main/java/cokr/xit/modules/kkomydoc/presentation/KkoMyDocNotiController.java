package cokr.xit.modules.kkomydoc.presentation;

import java.util.Map;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import cokr.xit.modules.common.code.InternalErrCd;
import cokr.xit.modules.common.exception.InternalException;
import cokr.xit.modules.kkomydoc.domain.NotiDetailsKkoMydoc;
import cokr.xit.modules.kkomydoc.domain.NotiDetailsKkoMydocRepository;
import cokr.xit.modules.kkomydoc.model.OttRespDTO;
import cokr.xit.modules.kkomydoc.service.KkoMyDocService;

@Controller
public class KkoMyDocNotiController {

	@Resource
	private KkoMyDocService kkoMyDocService;
	@Resource
	private NotiDetailsKkoMydocRepository notiDetailsKkoMydocRepository;


	@GetMapping("/kko/mydoc/noti/prnt")
	public String prntNoti(	@RequestParam Map<String, String> mParam, ModelMap model) {
		final String token = mParam.get("token");
		final String document_binder_uuid = mParam.get("document_binder_uuid");
		final String external_document_uuid = mParam.get("external_document_uuid");


		String accessToken = "b40d3c623d1011ecbceae6cf4630da62";

		Optional<NotiDetailsKkoMydoc> notiDetailsKkoMydoc = Optional.ofNullable(null);
		try {
			//토큰 검증
			OttRespDTO ottRespDTO = kkoMyDocService.token(accessToken, document_binder_uuid, token);
			if("USED".equals(ottRespDTO.getToken_status())) {	//성공 시...
				notiDetailsKkoMydoc = notiDetailsKkoMydocRepository.findByDocumentBinderUuidAndExternalDocumentUuid(document_binder_uuid, external_document_uuid);
			}else {	//실패 시...
				model.put("errCode", ottRespDTO.getError_code());
				model.put("errMsg", ottRespDTO.getError_message());
			}

		} catch (InternalException e) {
			model.put("errCode", e.getErrCd());
			model.put("errMsg", String.format("%s::: %s", e.getErrCd().getCodeNm(), e.getMessage()));
		} catch (Exception e) {
			model.put("errCode", InternalErrCd.ERR999);
			model.put("errMsg", String.format("%s::: %s", InternalErrCd.ERR999.getCodeNm(), e.getMessage()));
		}

		String details = notiDetailsKkoMydoc.isPresent()?notiDetailsKkoMydoc.get().getDetails().replaceAll("\\r\\n", ""):"{}";
		model.put("details", details);


		return "modules/kkomydoc/notiprnt";
	}

	@GetMapping("/kko/mydoc/noti/{notiDetailsId}/prnt")
	public String prnt(@PathVariable Long notiDetailsId, ModelMap model) throws JsonMappingException, JsonProcessingException {
		Optional<NotiDetailsKkoMydoc> notiDetailsKkoMydoc = notiDetailsKkoMydocRepository.findById(notiDetailsId);

		String details = notiDetailsKkoMydoc.get()==null?"{}":notiDetailsKkoMydoc.get().getDetails().replaceAll("\\r\\n", "");
		model.put("details", details);

		return "modules/kkomydoc/notiprnt";
	}



	/* ==========================================================
	 * -.redirect 페이지 고지서 출력 Sample 소스 => main.jsp 참조
	 * -.item_type: TEXT, KEY_VALUE, TABLE, IMAGE(예정), IMAGE_LINK(예정), GRAPH(예정)
		{
		    "contents": {
		        "documents": [
					{
						...
						"property": {
							"payload": "업무시스템 자료 식별자...",
							...
						}
					},
					{
						...
						"property": {
							"payload": "업무시스템 자료 식별자2...",
							...
						}
					}

		        ],
				"notidocuments" : [
					{
						"payload": "업무시스템 자료 식별자...",
						"details": [
							{
								"title": "기본정보",
								"item_type": "TEXT",
								"elements": [
									"test",
									"하하하"
								]
							},
							{
								...
							}
						]
					},
					{
						"payload": "업무시스템 자료 식별자2...",
						"details": [
							{
								...
							},
							{
								...
							}
						]
					}
				]
		    }
		}
	 ========================================================== */
}
