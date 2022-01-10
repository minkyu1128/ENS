package cokr.xit.modules.kkomydoc.model.child;

import cokr.xit.modules.domain.common.Error;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Documents extends Error{

	private String external_document_uuid;  //문서 식별 번호(외부)
	private String document_binder_uuid;    //문서 식별 번호(내부)
	private String payload;                 //payload
	
	
	public Documents(String external_document_uuid, String document_binder_uuid, String payload, String error_code, String error_message) {
		this.external_document_uuid = external_document_uuid;
		this.document_binder_uuid = document_binder_uuid;
		this.payload = payload;
		this.error_code = error_code;
		this.error_message = error_message;
	}
}
