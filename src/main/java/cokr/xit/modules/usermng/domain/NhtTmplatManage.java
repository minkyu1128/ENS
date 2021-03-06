package cokr.xit.modules.usermng.domain;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicUpdate;

import cokr.xit.modules.usermng.domain.embeded.FieldCreate;
import cokr.xit.modules.usermng.domain.embeded.FieldUpdate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * <ul>
 * <li>업무 그룹명: 고지서템플릿관리 테이블</li>
 * <li>설 명: </li>
 * <li>작성일: 2021. 12. 22. 오후 5:44:32
 * </ul>
 *
 * @author 박민규
 *
 */
@Entity(name = "TB_NHT_TMPLAT_MANAGE")
@Getter @ToString
@Builder
@DynamicUpdate//null 필드는 update 쿼리에서 제외
/*
 * @NoArgsConstructor, @AllArgsConstructor 추가
 * 	-.@Builder만 사용할 경우 queryDsl Select 시 기본생성자 오류("No default constructor for entity") 발생
 */
@NoArgsConstructor
@AllArgsConstructor
public class NhtTmplatManage {


	@Id
	@Column(length = 5)
	private String nhtTmplatId        ; //고지서 템플릿 ID
	@Column(columnDefinition = "char(5)")
	private String signguCode          ; //시군구 코드
	@Column(length = 1)
	private String sndngTyCode        ; //발송 유형 코드
	@Column(length = 1000)
	private String nhtNm               ; //고지서명
	@Column(length = 1000)
	private String nhtSj               ; //고지서 제목
	@Column(length = 4000)
	private String nhtCn               ; //고지서 내용
	@Column(length = 100)
	private String cstmrCnterNm        ; //고객 센터 명
	@Column(length = 14)
	private String cstmrCnterTlphonNo; //고객 센터 전화 번호
	@Column(length = 30)
	private String redirectUrl         ; //REDIRECT URL
	@Column(length = 1)
	private String useAt               ; //사용 여부
	@Embedded
	private FieldCreate create          ;
	@Embedded
	private FieldUpdate update          ;


}
