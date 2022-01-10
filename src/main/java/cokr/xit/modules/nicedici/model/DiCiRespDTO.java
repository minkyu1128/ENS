package cokr.xit.modules.nicedici.model;

import cokr.xit.modules.domain.common.Error;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class DiCiRespDTO extends Error{

	private String dici;
	
	
	@Builder
	public DiCiRespDTO(String dici) {
		this.dici = dici;
	}
}
