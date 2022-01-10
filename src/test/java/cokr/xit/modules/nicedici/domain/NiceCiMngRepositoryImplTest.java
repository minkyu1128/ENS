package cokr.xit.modules.nicedici.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class NiceCiMngRepositoryImplTest {

	@Autowired
	private NiceCiMngRepository niceCiMngRepository;
	
	private final String JID = "8611281018410";
	
	@Test
	void testFindCiByJid() {
		System.out.println(niceCiMngRepository.findCiByJid(JID));
	}

}
