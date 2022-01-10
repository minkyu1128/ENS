package cokr.xit.modules.kkomydoc.presentation;

import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cokr.xit.utils.CmmnUtil;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class KkoMyDocControllerTest {

	@InjectMocks
	private KkoMyDocController kkoMyDocController;
	
	private MockMvc mockMvc;
	
	public MockHttpSession session;
	public MockHttpServletRequest request;
	
	
	@SuppressWarnings("deprecation")
	@BeforeEach
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		
        
		//mockMvc 초기화
		mockMvc = MockMvcBuilders.standaloneSetup(kkoMyDocController).build();

		//session 초기화
		session = new MockHttpSession();
		
		//request 초기화
		request = new MockHttpServletRequest();
		request.setSession(session);
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
	}
	
	@Test @Disabled
	void testSend() throws Exception {
		mockMvc.perform(post("/kko/mydoc/send").session(session).param("contents", this.singleSendData()))
		.andExpect(status().isOk())
		.andExpect(view().name("/abc/abc.jsp"));
	}

	@Test
	void testToken() throws Exception {
		mockMvc.perform(get("/kko/mydoc/token").session(session))
		.andExpect(status().isOk());
		
	}

	@Test @Disabled
	void testReadCompleted() throws Exception {
		fail("Not yet implemented");
	}

	@Test @Disabled
	void testStatus() throws Exception {
		fail("Not yet implemented");
	}

	@Test @Disabled
	void testBulkSend() throws Exception {
		mockMvc.perform(post("/kko/mydoc/send/bulk").session(session).param("contents", this.bulkSendData()))
		.andExpect(status().isOk());
	}

	@Test @Disabled
	void testBulkStatus() throws Exception {
		fail("Not yet implemented");
	}
	
	
	
	
	
	

	private String singleSendData() {
		Long read_expired_at = CmmnUtil.dateToAbsTime(2030, 1, 1, 0, 0, 0)/1000;	//처리마감시간(절대시간). 단위: second
		Long read_expired_sec = CmmnUtil.daysToRelTime(365)/1000;	//처리마감시간(상대시간). 단위: second
		
		StringBuilder jsonStr = new StringBuilder();
		jsonStr.append("")
		.append("{")
		.append("    \"document\": {")
		.append("        \"title\": \"불법주정차위반과태료 사전통지서\",")
		.append("        \"common_categories\": [")
		.append("            \"BILL_PAY\"")	//Default: Default, BILL: 고지서, BILL_PAY: 납부가 포함된 고지서, NOTICE: 안내문, CONTRACT: 계약서, REPORT: 리포트
		.append("        ],")
		.append("        \"read_expired_at\": "+read_expired_at+",")
//		.append("        \"read_expired_sec\": "+read_expired_sec+",")
		.append("        \"hash\": \"6EFE827AC88914DE471C621AE\",")
		.append("        \"receiver\": {")
		.append("            \"phone_number\": \"01065303480\",")
		.append("            \"name\": \"박민규\",")
		.append("            \"birthday\": \"19861128\",")
		.append("            \"is_required_verify_name\": false")
		.append("        },")
		.append("        \"property\": {")
		.append("            \"link\": \"http://www.xit.co.kr\",")
		.append("            \"payload\": \"payload 파라미터 입니다.\",")
		.append("            \"message\": \"테스트\",")
		.append("            \"cs_number\": \"02-123-4567\",")
		.append("            \"cs_name\": \"콜센터\"")
		.append("        }")
		.append("    }")
		.append("}");
		
		return jsonStr.toString();
	}
	
	
	private String bulkSendData() {
		Long read_expired_at = CmmnUtil.dateToAbsTime(2030, 1, 1, 0, 0, 0)/1000;	//처리마감시간(절대시간). 단위: second
		Long read_expired_sec = CmmnUtil.daysToRelTime(365)/1000;	//처리마감시간(상대시간). 단위: second
		String message = null;
		String hash = null;
		message = "해당 안내문의 카테고리는 NOTICE와 BILL 입니다.";
		
		StringBuilder jsonStr = new StringBuilder();
		jsonStr.append("")
		.append("{")
		.append("    \"documents\": [")
		.append("        {")
		.append("            \"title\": \"대량문서발송. catgy=NOTICE/BILL\",")
		.append("            \"common_categories\": [")
		.append("                \"NOTICE\", \"BILL\"")	//Default: Default, BILL: 고지서, BILL_PAY: 납부가 포함된 고지서, NOTICE: 안내문, CONTRACT: 계약서, REPORT: 리포트
		.append("            ],")
		.append("            \"read_expired_at\": "+read_expired_at+",")	//처리마감시간(절대시간)
		.append("            \"hash\": \""+hash+"\",")
		.append("            \"receiver\": {")
		.append("                \"phone_number\": \"01065303480\",")
		.append("                \"name\": \"박민규\",")
		.append("                \"birthday\": \"19861128\",")
		.append("                \"is_required_verify_name\": false")
		.append("            },")
		.append("            \"property\": {")
		.append("                \"link\": \"https://www.kakaopay.com\",")
		.append("                \"payload\": \"payload 파라미터 입니다.\",")
		.append("                \"message\": \""+message+"\",")
		.append("                \"cs_number\": \"02-123-4567\",")
		.append("                \"cs_name\": \"콜센터\",")
		.append("                \"external_document_uuid\": \"A0001\"")
		.append("            }")
		.append("        },")
		.append("        {")
		.append("            \"title\": \"대량문서발송. catgy=NOTICE/BILL/BILL_PAY\",")
		.append("            \"common_categories\": [")
		.append("                \"NOTICE\", \"BILL\", \"BILL_PAY\"")
		.append("            ],")
		.append("            \"read_expired_sec\": "+read_expired_sec+",")	//처리마감시간(상대시간)
		.append("            \"hash\": \"6EFE827AC88914DE471C621AE\",")
		.append("            \"receiver\": {")
//		.append("                \"ci\": \"vMtqVxJX56lBgbf9heK3QTc+jVndTfK77i/UJKAzPmBG4n9CazCdd/8YytlFZnN4qofIqgxHpSoiG0yYzgEpJg==\"")
		.append("                \"phone_number\": \"01065303480\",")
		.append("                \"name\": \"박민규\",")
		.append("                \"birthday\": \"19861128\",")
		.append("                \"is_required_verify_name\": false")
		.append("            },")
		.append("            \"property\": {")
		.append("                \"link\": \"https://www.kakaopay.com\",")
		.append("                \"payload\": \"payload 파라미터 입니다.\",")
		.append("                \"message\": \"해당 안내문의 카테고리는 NOTICE/BILL/BILL_PAY 입니다.\",")
		.append("                \"cs_number\": \"02-123-4567\",")
		.append("                \"cs_name\": \"콜센터\",")
		.append("                \"external_document_uuid\": \"B0001\"")
		.append("            }")
		.append("        }")
		.append("    ]")
		.append("}");
		
		return jsonStr.toString();
	}

}
