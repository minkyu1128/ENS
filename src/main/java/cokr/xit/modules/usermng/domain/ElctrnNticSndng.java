package cokr.xit.modules.usermng.domain;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.DynamicUpdate;

import cokr.xit.modules.usermng.domain.embeded.FieldCreate;
import cokr.xit.modules.usermng.domain.embeded.FieldUpdate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * <ul>
 * <li>업무 그룹명: 전자고지발송 테이블</li>
 * <li>설 명: </li>
 * <li>작성일: 2021. 12. 22. 오후 5:44:32
 * </ul>
 *
 * @author 박민규
 *
 */
@Entity(name = "TB_ELCTRN_NTIC_SNDNG")
@Getter @ToString
@Builder
@DynamicUpdate//null 필드는 update 쿼리에서 제외
/*
 * @NoArgsConstructor, @AllArgsConstructor 추가
 * 	-.@Builder만 사용할 경우 queryDsl Select 시 기본생성자 오류("No default constructor for entity") 발생
 */
@NoArgsConstructor
@AllArgsConstructor
public class ElctrnNticSndng {


	@Id
	@Column(length = 20)
	private String elctrnNticSndngId; //전자 고지 발송 Id
	@Column(columnDefinition = "char(5)")
	private String signguCode         ; //시군구 코드
//	@Column(length = 5)
//	private String NHT_TMPLAT_ID       ; //고지서 템플릿 ID
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nhtTmplatId")
	private NhtTmplatManage nhtTmplatManage; //고지서 템플릿
	@Column(length = 5)
	private String sndngTyCode        ; //발송 유형 코드
	@Column(length = 10)
	private Integer sndngCo           ; //발송 건수
	@Column(length = 2)
	@Setter
	private String sndngProcessSttus  ; //발송 처리 상태(01:요청, 02:발송완료, 09: 마감, 99:오류)
	@Column(length = 14)
	private String sndngDt            ; //발송 일시
	@Column(length = 14)
	private String closDt             ; //마감 일시
	@Embedded
	private FieldCreate create;
	@Embedded
	private FieldUpdate update;
	@Column(length = 3000)
	@Setter
	private String errorCn;           ; //에러 내용


}
