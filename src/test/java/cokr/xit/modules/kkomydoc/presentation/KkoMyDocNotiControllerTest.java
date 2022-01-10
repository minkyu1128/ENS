package cokr.xit.modules.kkomydoc.presentation;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import cokr.xit.modules.kkomydoc.model.BulkSendReqDTO;
import cokr.xit.modules.kkomydoc.model.NotiDocDTO;

class KkoMyDocNotiControllerTest {

	@Test
	void testPrnt() {
		fail("Not yet implemented");
	}
	
	@Test
	void parseTest() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			NotiDocDTO notiDTO = mapper.readValue(this.notiDocuments(), NotiDocDTO.class);
			System.out.println("NotiDocDTO -> " + notiDTO.toString());
			
			BulkSendReqDTO bulkSendReqDTO = mapper.readValue(this.notiDocuments(), BulkSendReqDTO.class);
			System.out.println("BulkSendReqDTO -> " + bulkSendReqDTO.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String notiDocuments() {
		String jsonStr = ""
				+"{"
				+"	\"notidocuments\" : [                                                                          "
				+"		{                                                                                          "
				+"			\"payload\" : \"payload 입니다..\"                                                       "
				+"			\"details\" : [                                                                        "
				+"				{                                                                                  "
				+"					\"title\" : \"기본정보\",                                                          "
				+"					\"item_type\" : \"TEXT\",                                                      "
				+"					\"elements\" : [\"test\", \"하하하\"]                                             "
				+"				},                                                                                 "
				+"				{                                                                                  "
				+"					\"title\" : \"전체청구금액\",                                                        "
				+"					\"item_type\" : \"KEY_VALUE\",                                                 "
				+"					\"elements\" : [                                                               "
				+"						{                                                                          "
				+"							\"key\": \"당월 부과 금액\",                                                 "
				+"							\"value\": \"200,203원\",                                               "
				+"							\"level\": 1                                                           "
				+"						},                                                                         "
				+"						{                                                                          "
				+"							\"key\": \"미납액\",                                                      "
				+"							\"value\": \"1,200원\",                                                 "
				+"							\"level\": 1                                                           "
				+"						}                                                                          "
				+"					]                                                                              "
				+"				},                                                                                 "
				+"				{                                                                                  "
				+"					\"title\" : \"항목별부과금액\",                                                       "
				+"					\"item_type\" : \"TABLE\",                                                     "
				+"					\"elements\" : {                                                               "
				+"						\"head\" : [ \"항목\", \"당월금액\", \"전월대비\" ],                                 "
				+"						\"rows\": [                                                                "
				+"							[ \"전기료\", \"1,000,000원\", \"+500,000\" ],                             "
				+"							[ \"수도료\", \"300,000원\", \"-100,000\" ]                                "
				+"						]                                                                          "
				+"					}                                                                              "
				+"				}                                                                                  "
				+"			]	                                                                                   "
				+"		}                                                                                          "
				+"	]                                                                                              "
				+"} 	                                                                                             "
				;
		return jsonStr;
	}

}
