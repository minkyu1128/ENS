package cokr.xit.modules.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

//@ExtendWith(SpringExtension.class)
//@DataJpaTest
@SpringBootTest
class SendMastRepositoryImplTest {
	
	@Autowired
	private SendMastRepository sendMastRepository;

	@Test
	void testModifyBySendDetailKkoMydoc() {
		Long sendMastId = 352L;
		
		sendMastRepository.modifyStatCdAndReadCntBySendMastId(sendMastId);
	}

}
