package cokr.xit.modules.usermng.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ElctrnNticSndngDetailRepositoryImplTest {

	@Autowired
	private ElctrnNticSndngDetailRepository elctrnNticSndngDetailRepository;

	@Test
	void testFindAllByElctrnNticSndng() {

		//given
		ElctrnNticSndng sndng = ElctrnNticSndng.builder().build();

		//when
		List<ElctrnNticSndngDetail> list =  elctrnNticSndngDetailRepository.findAllByElctrnNticSndng(sndng);

		//then
		assertThat(list).isNotNull();
	}

}
