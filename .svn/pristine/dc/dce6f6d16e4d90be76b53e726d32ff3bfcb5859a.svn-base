package cokr.xit.modules.kkomydoc.model.child;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import cokr.xit.modules.domain.common.Error;
import cokr.xit.modules.kkomydoc.model.StatusRespDTO;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class BulkStatusDocument extends Error{
	@NotEmpty
	@Length(max = 40)
	private String document_binder_uuid;  //문서 식별 번호
	private StatusRespDTO status_data;    //문서의 현재 상태 정보
}
