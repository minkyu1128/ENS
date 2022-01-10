package cokr.xit.modules.kkomydoc.model.child;

import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class NotiDocument {

	@NotNull
	private String payload;                //이용기관이 생성한 payload 값
	
	@NotNull
	private List<Details> details;         //청구서 페이지 내에 보여줄 상세 정보
}
