package cokr.xit.modules.usermng.domain;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.DynamicUpdate;

import cokr.xit.modules.usermng.domain.embeded.FieldCreate;
import cokr.xit.modules.usermng.domain.embeded.FieldUpdate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * <ul>
 * <li>업무 그룹명: 전자고지발송상세 테이블</li>
 * <li>설 명: </li>
 * <li>작성일: 2021. 12. 22. 오후 5:45:01
 * </ul>
 *
 * @author mk1126sj
 *
 */
@Entity(name = "TB_ELCTRN_NTIC_SNDNG_DETAIL")
@Getter @ToString
@Builder
@DynamicUpdate //null 필드는 update 쿼리에서 제외
/*
 * @NoArgsConstructor, @AllArgsConstructor 추가
 * 	-.@Builder만 사용할 경우 queryDsl Select 시 기본생성자 오류("No default constructor for entity") 발생
 */
@NoArgsConstructor
@AllArgsConstructor
public class ElctrnNticSndngDetail {


	@Id
	@Column(length = 20)
	private String elctrnNticSndngDetailId; //전자 고지 발송 상세 ID
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "elctrnNticSndngId")
	private ElctrnNticSndng elctrnNticSndng; //전자 고지 발송
	
	@Column(length = 5, columnDefinition = "char")
	private String signguCode                ; //시군구 코드
	
	@Column(length = 20)
	private String mainCode                  ; //메인 코드
	
	@Column(length = 100, updatable = false)
	private String ihidnum                   ; //주민등록번호
	
	@Lob
	private String cnDetail                  ; //내용 상세
	
	@Lob
	private String mobilePageCn              ; //모바일 페이지 내용
	
	@Embedded
	private FieldCreate create;
	
	@Embedded
	private FieldUpdate update;
	
	@Column(length = 40)
	@Setter
	private String external_document_uuid    ; //문서발송 대상자와 문서식별번호를 매칭하는 값으로 사용. 반드시 Unique 하게 생성 후 전송. 하나의 Request에 대해서만 Unique 체크가 가능.
}
