package cokr.xit.modules.kkomydoc.domain;

import java.util.List;

public interface SendResultKkoMydocRepositoryCustom {

	public List<String> findDocumentBinderUuidBySendMastId(Long sendMastId);

}
