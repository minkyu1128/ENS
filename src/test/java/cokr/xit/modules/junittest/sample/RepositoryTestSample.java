package cokr.xit.modules.junittest.sample;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import cokr.xit.code.CodeMapperFactory;
import cokr.xit.code.CodeMapperValue;
import cokr.xit.modules.common.code.StatCd;
import cokr.xit.modules.domain.SendMast;
import cokr.xit.modules.domain.SendMastRepository;

/**
 * 
 * <ul>
 * <li>업무 그룹명: JPA Repository 테스트</li>
 * <li>설 명: @TestMethodOrder, @Order 애너테이션으로 실행순서를 설정 할 수 있다.</li>
 * <li>참고 사이트: https://memostack.tistory.com/195</li>
 * <li>작성일: 2021. 10. 25. 오후 5:49:07
 * </ul>
 *
 * @author 박민규
 *
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
class RepositoryTestSample {

	@Autowired
	private SendMastRepository sendMastRepository;
	
	@Autowired
	private CodeMapperFactory factory;
	
	@Test
	void testFindByTxId() {

		for(CodeMapperValue row : factory.get("Sample"))
			System.out.println(row.getCode()+"="+row.getCodeNm());
		factory.get("NvRespCd")
		.forEach((row) -> System.out.println(row.getCode()+"="+row.getCodeNm()));
		
		String txId = "abcdefg123456";
		
		final SendMast param = SendMast.builder()
				.vender("엑스아이티")
				.statCd(StatCd.accept)
				.build();
		
		//테스트 데이터 생성
		final SendMast vo = sendMastRepository.save(param);
		
		//검증
		assertEquals("엑스아이티", param.getVender());	//success
		assertTrue(vo != null);
		assertFalse(vo == null);
	}


}
