package cokr.xit.modules.kkomydoc.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SendDetailKkoMydocRepository extends JpaRepository<SendDetailKkoMydoc, Long>, SendDetailKkoMydocRepositoryCustom {

	public SendDetailKkoMydoc findByExternalDocumentUuid(String externalDocumentUuid);

}
