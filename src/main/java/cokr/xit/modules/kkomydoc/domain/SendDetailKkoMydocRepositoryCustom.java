package cokr.xit.modules.kkomydoc.domain;

import java.util.List;

import cokr.xit.modules.kkomydoc.model.StatusRespDTO;
import cokr.xit.modules.kkomydoc.model.child.Documents;

public interface SendDetailKkoMydocRepositoryCustom {

	/**
	 * 발송성공 건수 조회
	 * @param sendMastId 발송마스터ID
	 * @return
	 */
	public Long countSuccessBySendMastId(Long sendMastId);

	/**
	 * <pre>메소드 설명: 최초토큰검증시간 update</pre>
	 * @param tokenUsedAt	토큰 사용일시(OTT 검증을 수행 후 성공된 시간)
	 * @param documentBinderUuid	문서식별번호
	 * @return Long 요청처리 후 응답객체
	 * @author: 박민규
	 * @date: 2021. 11. 23.
	 */
	public Long modifyFrstTokenVerifyDtByDocumentBinderUuid(Long tokenUsedAt, String documentBinderUuid);

	/**
	 * <pre>메소드 설명: 상태정보 update</pre>
	 * @param statusRespDTO	상태응답DTO
	 * @param documentBinderUuid	문서식별번호
	 * @return Long 요청처리 후 응답객체
	 * @author: 박민규
	 * @date: 2021. 11. 23.
	 */
	public Long modifyStatusInfoByDocumentBinderUuid(StatusRespDTO statusRespDTO, String documentBinderUuid);

	/**
	 * <pre>메소드 설명: 상태정보 update</pre>
	 * @param statusRespDTO	상태응답DTO
	 * @param documentBinderUuid	문서식별번호
	 * @return Long 요청처리 후 응답객체
	 * @author: 박민규
	 * @date: 2021. 11. 23.
	 */
	public Long modifyStatusInfoByDocumentBinderUuid(StatusKkoMydoc statusKkoMydoc);


	public SendDetailKkoMydoc findByDocumentBinderUuid(String documentBinderUuid);


	public List<Documents> findSendRespDetailsBySendMastId(Long sendMastId);


	public List<Long> findSendMastIdByExternalDocumentUuid(List<String> externalDocumentUuidList);
}
