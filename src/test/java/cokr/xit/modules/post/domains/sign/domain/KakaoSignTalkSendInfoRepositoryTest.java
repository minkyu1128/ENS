//package cokr.xit.modules.post.domains.sign.domain;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import cokr.xit.modules.domain.SendMast;
//
//@ExtendWith(SpringExtension.class)
//@DataJpaTest
//class KakaoSignTalkSendInfoRepositoryTest {
//
//	@Autowired
//	private KakaoSignTalkSendInfoRepository kakaoSignTalkSendInfoRepository;
//	
//	@Test
//	void testFindByTxId() {
//		String txId = "abcdefg123456";
//		
//		final KakaoSignTalkSendInfo param = KakaoSignTalkSendInfo.builder()
//				.sendMast(SendMast.builder().vender("kt").build())
//				.phone_no("01012341234")
//				.name("test")
//				.birthday("021025")
//				.expires_in(86400)
//				.call_center_no("070-1234-1234")
//				.title("test")
//				.publish_certified_electronic_doc("N")
//				.redirect_url("http://xxx.xxx.com")
//				.result("N")
//				.txId(txId)
//				.build();
//		
//		final KakaoSignTalkSendInfo vo = kakaoSignTalkSendInfoRepository.save(param);
//		
//		assertEquals(txId, vo.getTxId());
//	}
//
////	@Test
////	void testFindBySendMast() {
////		fail("Not yet implemented");
////	}
////
////	@Test
////	void testBatchUpdateByResult() {
////		fail("Not yet implemented");
////	}
//
//}
