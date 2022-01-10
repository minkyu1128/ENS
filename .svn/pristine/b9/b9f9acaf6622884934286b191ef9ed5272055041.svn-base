package cokr.xit.modules.usermng.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ElctrnNticSndngRepositoryTest {

	@Autowired
	private ElctrnNticSndngRepository repository;
	
	@Test
	void testFindAllBySndngProcessSttusAndSndngDtBetween() {
		//given
		String processSttus = "01";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
		String yyyyMMddHHmm = dateFormat.format(new Date());
		String beginSndngDt = String.format("%s00", yyyyMMddHHmm);
		String endSndngDt = String.format("%s59", yyyyMMddHHmm);
		
		//when
		List<ElctrnNticSndng> list = repository.findAllBySndngProcessSttusAndSndngDtBetween(processSttus, beginSndngDt, endSndngDt);
		
		//than
		assertThat(list.size()).isGreaterThanOrEqualTo(0);
		
	}

}
