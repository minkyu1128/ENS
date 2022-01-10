package cokr.xit.modules.kkomydoc.model.child;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Document {

	private String title;               //발송할 문서의 제목
	private String hash;                //문서 원문(열람정보)에 대한 hash값. 공인전자문서 유통정보 등록 시 필수
	private String[] common_categories; //문서의 메타정보
	private Long read_expired_at;       //처리마감시간(절대시간)
	private Integer read_expired_sec;   //처리마감시간(상대시간). 권장값: 30일(2592000 sec)
	private Receiver receiver;          //받는이에 대한 정보
	private Property property;          //문서 속성 정보
	private Bridge bridge;              //문서 안내 추가 정보
	
}
