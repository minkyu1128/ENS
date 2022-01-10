package cokr.xit.modules.kkomydoc.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import cokr.xit.utils.CmmnUtil;

//@ExtendWith(SpringExtension.class)
//@DataJpaTest
@SpringBootTest
class KkoMyDocServiceTest {

	
	@Autowired
	KkoMyDocService kkoMyDocService;
	
	private final String Access_Token = "b40d3c623d1011ecbceae6cf4630da62";
	private final String Contract_Uuid = "CON-b4126c833d1011ecbceae6cf4630da62";
	private final String Vender = "엑스아이티";
	private final String Rev_Typ1_Ci = "S++biC1tlFMFBCIUqbRajTY5Cvz9xQHR2/PrxyuT4KvdZslLBHopdGGSPx8KzQ3GkopPSOhj2lyfbhjyyRZSTQ==";
	private final String Rev_Typ2_Phone_Number = "01065303480";
	private final String Rev_Typ2_Name = "박민규";
	private final String Rev_Typ2_Birthday = "19861128";

	
	@Test @Disabled
	void testSend() {

		 //given(준비)
		String accessToken = this.Access_Token;
		String contractUuid = this.Contract_Uuid;
		String jsonStr = this.singleSendData();
		String vender = this.Vender;
		
		
		 //when(실행)
		kkoMyDocService.send(vender, accessToken, contractUuid, jsonStr); 
		

		//then(검증)
//		assertThat(resp.getStatusCode(), equalTo(HttpStatus.OK));
	}
	
	
	@Test @Disabled
	void testSendFail() {

		 //given(준비)
		String accessToken = this.Access_Token;
		String contractUuid = "";
		String jsonStr = this.singleSendData();
		String vender = this.Vender;
		
		
		 //when(실행)
		kkoMyDocService.send(vender, accessToken, contractUuid, jsonStr); 
		

		//then(검증)
//		assertThat(resp.getStatusCode(), equalTo(HttpStatus.OK));
	}
	

	@Test @Disabled
	void testToken() {
		 //given(준비)
		String accessToken = this.Access_Token;
		String documentBinderUuid = "BIN-638d5e3e4dd311ecbffd161130206b5e";
		String oneTimeToken = "MjM1NGI5YjE1NDA0MTFlYzg1NzVjNmJjZTA3NDQyMTA%3D";
		
		
		 //when(실행)
		kkoMyDocService.token(accessToken, documentBinderUuid, oneTimeToken); 
	}
	
	
	@Test @Disabled
	void testReadCompleted() {
		//given(준비)
		String accessToken = this.Access_Token;
		String documentBinderUuid = "BIN-bca85eed541511ec8b992a19b213063b";
		
		
		//when(실행)
		kkoMyDocService.readCompleted(accessToken, documentBinderUuid);
		kkoMyDocService.readCompleted(accessToken, "unknownDocumentBinderUuid");
		kkoMyDocService.readCompleted("unknownAccessToken", documentBinderUuid);
		

		
		
	}
	

	@Test @Disabled
	void testStatus() {
		 //given(준비)
		String accessToken = this.Access_Token;
		String contractUuid = this.Contract_Uuid;
		String documentBinderUuid = "BIN-bca85eed541511ec8b992a19b213063b";
		
		
		 //when(실행)
		kkoMyDocService.status(accessToken, contractUuid, documentBinderUuid); 
	}

	@Test @Disabled
	void testBulkSend() {

		 //given(준비)
		String accessToken = this.Access_Token;
		String contractUuid = this.Contract_Uuid;
		String jsonStr = this.bulkSendData();
		String vender = "춘천시(bulk)";
		
		
		 //when(실행)
		kkoMyDocService.bulkSend(vender, accessToken, contractUuid, jsonStr); 
		

		//then(검증)
//		assertThat(resp.getStatusCode(), equalTo(HttpStatus.OK));
	}
	@Test @Disabled
	void testBulkSendAndNotiData() {
		
		//given(준비)
		String accessToken = this.Access_Token;
		String contractUuid = this.Contract_Uuid;
		String jsonStr = this.bulkSendAndNotiData();
		String vender = "춘천시(bulk)";
		
		
		//when(실행)
		kkoMyDocService.bulkSend(vender, accessToken, contractUuid, jsonStr); 
		
		
		//then(검증)
//		assertThat(resp.getStatusCode(), equalTo(HttpStatus.OK));
	}
	

	@Test @Disabled
	void testBulkSendFail() {

		 //given(준비)
		String accessToken = this.Access_Token;
		String contractUuid = "";
		String jsonStr = this.bulkSendData();
		String vender = "춘천시(bulk)";
		
		
		 //when(실행)
		kkoMyDocService.bulkSend(vender, accessToken, contractUuid, jsonStr); 
		

		//then(검증)
//		assertThat(resp.getStatusCode(), equalTo(HttpStatus.OK));
	}

	@Test @Disabled
	void testBulkStatus() {

		 //given(준비)
		String accessToken = this.Access_Token;
		String contractUuid = this.Contract_Uuid;
		String jsonStr = this.bulkStatusData();
		
		
		 //when(실행)
		kkoMyDocService.bulkStatus(accessToken, contractUuid, jsonStr); 
		

		//then(검증)
//		assertThat(resp.getStatusCode(), equalTo(HttpStatus.OK));
	}

	@Test 
	void testmodifySendMastBySendDetailStatus() {
		//given(준비)
		Long sendMastId = 352L;
		
		//whdn(실행)
		kkoMyDocService.modifySendMastCurStatBySendMastId(Access_Token, Contract_Uuid, sendMastId);
		
		
		//then(검증)
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
//		.append("        {")
//		.append("            \"title\": \"대량문서발송. catgy=NOTICE/BILL\",")
//		.append("            \"common_categories\": [")
//		.append("                \"NOTICE\", \"BILL\"")	//Default: Default, BILL: 고지서, BILL_PAY: 납부가 포함된 고지서, NOTICE: 안내문, CONTRACT: 계약서, REPORT: 리포트
//		.append("            ],")
//		.append("            \"read_expired_at\": "+read_expired_at+",")	//처리마감시간(절대시간)
//		.append("            \"hash\": \""+hash+"\",")
//		.append("            \"receiver\": {")
//		.append("                \"phone_number\": \""+this.Rev_Typ2_Phone_Number+"\",")
//		.append("                \"name\": \""+this.Rev_Typ2_Name+"\",")
//		.append("                \"birthday\": \""+this.Rev_Typ2_Birthday+"\",")
//		.append("                \"is_required_verify_name\": false")
//		.append("            },")
//		.append("            \"property\": {")
//		.append("                \"link\": \"https://www.kakaopay.com\",")
//		.append("                \"payload\": \"payload 파라미터 입니다.\",")
//		.append("                \"message\": \""+message+"\",")
//		.append("                \"cs_number\": \"02-123-4567\",")
//		.append("                \"cs_name\": \"콜센터\",")
//		.append("                \"external_document_uuid\": \"A0001\"")
//		.append("            }")
//		.append("        },")
		.append("        {")
		.append("            \"title\": \"대량문서발송 FAIL 테스트\",")
		.append("            \"common_categories\": [")
		.append("                \"NOTICE\", \"BILL\", \"BILL_PAY\"")
		.append("            ],")
		.append("            \"read_expired_at\": "+read_expired_at+",")	//처리마감시간(절대시간)
		.append("            \"hash\": \"6EFE827AC88914DE471C621AE\",")
		.append("            \"receiver\": {")
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
		.append("        },")
		.append("        {")
		.append("            \"title\": \"대량문서발송. catgy=NOTICE/BILL/BILL_PAY\",")
		.append("            \"common_categories\": [")
		.append("                \"NOTICE\", \"BILL\", \"BILL_PAY\"")
		.append("            ],")
		.append("            \"read_expired_sec\": "+read_expired_sec+",")	//처리마감시간(상대시간)
		.append("            \"hash\": \"6EFE827AC88914DE471C621AE\",")
		.append("            \"receiver\": {")
//		.append("                \"ci\": \""+this.Rev_Typ1_Ci+"\"")
		.append("                \"phone_number\": \""+this.Rev_Typ2_Phone_Number+"\",")
		.append("                \"name\": \""+this.Rev_Typ2_Name+"\",")
		.append("                \"birthday\": \""+this.Rev_Typ2_Birthday+"\",")
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
	private String bulkSendAndNotiData() {
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
		.append("            \"title\": \"대량문서발송 FAIL 테스트\",")
		.append("            \"common_categories\": [")
		.append("                \"NOTICE\", \"BILL\", \"BILL_PAY\"")
		.append("            ],")
		.append("            \"read_expired_at\": "+read_expired_at+",")	//처리마감시간(절대시간)
		.append("            \"hash\": \"6EFE827AC88914DE471C621AE\",")
		.append("            \"receiver\": {")
		.append("                \"is_required_verify_name\": false")
		.append("            },")
		.append("            \"property\": {")
		.append("                \"link\": \"https://www.kakaopay.com\",")
		.append("                \"payload\": \"payload1\",")
		.append("                \"message\": \"해당 안내문의 카테고리는 NOTICE/BILL/BILL_PAY 입니다.\",")
		.append("                \"cs_number\": \"02-123-4567\",")
		.append("                \"cs_name\": \"콜센터\",")
		.append("                \"external_document_uuid\": \"B0001\"")
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
//		.append("                \"ci\": \""+this.Rev_Typ1_Ci+"\"")
		.append("                \"phone_number\": \""+this.Rev_Typ2_Phone_Number+"\",")
		.append("                \"name\": \""+this.Rev_Typ2_Name+"\",")
		.append("                \"birthday\": \""+this.Rev_Typ2_Birthday+"\",")
		.append("                \"is_required_verify_name\": false")
		.append("            },")
		.append("            \"property\": {")
		.append("                \"link\": \"https://www.kakaopay.com\",")
		.append("                \"payload\": \"payload2\",")
		.append("                \"message\": \"해당 안내문의 카테고리는 NOTICE/BILL/BILL_PAY 입니다.\",")
		.append("                \"cs_number\": \"02-123-4567\",")
		.append("                \"cs_name\": \"콜센터\",")
		.append("                \"external_document_uuid\": \"B0001\"")
		.append("            }")
		.append("        }")
		.append("    ],")
		.append("	\"notidocuments\" : [                                                                          ")
		.append("		{                                                                                          ")
		.append("			\"payload\" : \"payload2\",                                                      ")
		.append("			\"details\" : [                                                                        ")
		.append("				{                                                                                  ")
		.append("					\"title\" : \"기본정보\",                                                          ")
		.append("					\"item_type\" : \"TEXT\",                                                      ")
		.append("					\"elements\" : [\"test\", \"하하하\"]                                             ")
		.append("				},                                                                                 ")
		.append("				{                                                                                  ")
		.append("					\"title\" : \"전체청구금액\",                                                        ")
		.append("					\"item_type\" : \"KEY_VALUE\",                                                 ")
		.append("					\"elements\" : [                                                               ")
		.append("						{                                                                          ")
		.append("							\"key\": \"당월 부과 금액\",                                                 ")
		.append("							\"value\": \"200,203원\",                                               ")
		.append("							\"level\": 1                                                           ")
		.append("						},                                                                         ")
		.append("						{                                                                          ")
		.append("							\"key\": \"미납액\",                                                      ")
		.append("							\"value\": \"1,200원\",                                                 ")
		.append("							\"level\": 1                                                           ")
		.append("						}                                                                          ")
		.append("					]                                                                              ")
		.append("				},                                                                                 ")
		.append("				{                                                                                  ")
		.append("					\"title\" : \"항목별부과금액\",                                                       ")
		.append("					\"item_type\" : \"TABLE\",                                                     ")
		.append("					\"elements\" : {                                                               ")
		.append("						\"head\" : [ \"항목\", \"당월금액\", \"전월대비\" ],                                 ")
		.append("						\"rows\": [                                                                ")
		.append("							[ \"전기료\", \"1,000,000원\", \"+500,000\" ],                             ")
		.append("							[ \"수도료\", \"300,000원\", \"-100,000\" ]                                ")
		.append("						]                                                                          ")
		.append("					}                                                                              ")
		.append("				}                                                                                  ")
		.append("			]	                                                                                   ")
		.append("		}                                                                                          ")
		.append("	]	                                                                                          ")
		.append("}	                                                                                          ");
		         	                                                                                              
		return jsonStr.toString();
	}
	
	private String bulkStatusData() {
		StringBuilder jsonStr = new StringBuilder();
		jsonStr.append("")
		.append("{                                            ")
		.append("	\"document_binder_uuids\" : [             ")
		.append("	\"BIN-c74f4b8a87a811ebbcd4acde48001122\", ")
		.append("	\"BIN-94d5b1704b6711eca652ca4221b985e0\", ")
		.append("	\"BIN-b7d8051c484a11ecab5da67e95426de7\", ")
		.append("	\"BIN-c74f4b8c87a811ebbcd4acde48001122\", ")
		.append("	\"BIN-fe8c245548cf11ec887d0ae16c5cf1fa\" ")
		.append("	]                                         ")
		.append("}                                            ")
		;
		
		return jsonStr.toString();
	}
}
