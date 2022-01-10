package cokr.xit.modules.domain;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ApiManage {

	private String apiKey;            //API KEY
	                                  
	private String api;               //API 명
	                                  
	private String status;            //상태코드(req, active, inactive)
	                                  
	private String system;            //시스템명
	                                  
	private String systemId;          //시스템 URL
	
	private LocalDateTime reqDt;      //신청일시
	                                  
	private LocalDateTime lastUpdtDt; //최종수정일시
}


