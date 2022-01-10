package cokr.xit.modules.domain;

public interface SendMastRepositoryCustom {

	/**
	 * <pre>메소드 설명: 상태코드 및 열람건수를 갱신한다. </pre>
	 * @param sendMastId
	 * @return Long 요청처리 후 응답객체
	 * @author: 박민규
	 * @date: 2021. 12. 6.
	 */
	public Long modifyStatCdAndReadCntBySendMastId(Long sendMastId);


}
