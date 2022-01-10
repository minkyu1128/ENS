package cokr.xit.modules.nicedici.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NiceDiCiServiceTest {

	@Autowired
	private NiceDiCiService niceDiCiService;
	
	private final String siteCode = "GA72";          //사이트 코드
	private final String sitePw = "xit5811807!!";    //사이트 패스워드
	private final String jumin  = "8611281018410";   //주민번호
	
	@Test
	void testCi() {
		niceDiCiService.ci(siteCode, sitePw, jumin);
	}
}
