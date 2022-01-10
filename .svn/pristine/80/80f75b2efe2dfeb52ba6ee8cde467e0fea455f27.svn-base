package cokr.xit.modules.kkomydoc.domain;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import cokr.xit.modules.kkomydoc.model.StatusRespDTO;
import cokr.xit.modules.kkomydoc.model.child.Documents;

//@ExtendWith(SpringExtension.class)
//@DataJpaTest
@SpringBootTest
class SendDetailKkoMydocRepositoryImplTest {
	
	@Autowired
	private SendDetailKkoMydocRepository sendDetailKkoMydocRepository;
	
	@Test @Disabled
	void testCountSuccessBySendMastId() {
		//given
		Long sendMastId = 10L; //발송마스터ID
		
		
		//when
		Long cnt = sendDetailKkoMydocRepository.countSuccessBySendMastId(sendMastId);
		System.out.println("발송성공 건수: "+cnt.intValue());
		
		//then
		assertThat(cnt.intValue(), greaterThan(-1));
		
	}
	
	@Test @Disabled
	void testModifyFrstTokenVerifyDtByDocumentBinderUuid() {
		//given
		Long tokenUsedAt =  1638508816L;
		String documentBinderUuid = "BIN-a980da654b5511ec887d0ae16c5cf1fa";
		
		
		//when
		sendDetailKkoMydocRepository.modifyFrstTokenVerifyDtByDocumentBinderUuid(tokenUsedAt, documentBinderUuid);
		
		//then
	}
	
	@Test @Disabled 
	void testModifyStatusInfoDtByDocumentBinderUuid() {
		//given
		StatusRespDTO statusRespDTO = new StatusRespDTO();
//		statusRespDTO.setError_code("Err");
//		statusRespDTO.setError_message("Err Junit Test");
		String documentBinderUuid = "BIN-94d4ef1f4b6711eca652ca4221b985e0";
		
		
		//when
		sendDetailKkoMydocRepository.modifyStatusInfoByDocumentBinderUuid(statusRespDTO, documentBinderUuid);
		
		//then
	}

	@Test @Disabled
	void testModifyStatusInfoByDocumentBinderUuid() {
		//given
		String documentBinderUuid = "BIN-94d4ef1f4b6711eca652ca4221b985e0";
		StatusKkoMydoc statusKkoMydoc = StatusKkoMydoc.builder()
				.documentBinderUuid(documentBinderUuid)
				.build();
		
		//when
		sendDetailKkoMydocRepository.modifyStatusInfoByDocumentBinderUuid(statusKkoMydoc);
		
		
		//then
		
	}

	@Test
	void testFindSendRespDetailsBySendMastId() {
		//given
		Long sendMastId = 452L;
		
		//when
		List<Documents> result = sendDetailKkoMydocRepository.findSendRespDetailsBySendMastId(sendMastId);
		
		
		//then
		System.out.println(result.toString());
		
	}

}
