package cokr.xit.modules.post.domains.sample.domain;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class SampleDTO {

	private Map<String, Object> data;
//	private List<Map<String, Object>> arrData;
	private List<dataDTO> arrData;
	
}

