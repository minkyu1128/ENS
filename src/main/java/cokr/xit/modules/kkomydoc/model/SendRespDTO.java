package cokr.xit.modules.kkomydoc.model;

import cokr.xit.modules.domain.common.Error;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SendRespDTO extends Error{
	private String document_binder_uuid;     //문서 식별 번호
	
}
