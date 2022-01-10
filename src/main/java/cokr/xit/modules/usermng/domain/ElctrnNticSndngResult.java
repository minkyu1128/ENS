package cokr.xit.modules.usermng.domain;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicUpdate;

import cokr.xit.modules.usermng.domain.embeded.FieldCreate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * <ul>
 * <li>업무 그룹명: 전자고지발송결과 테이블</li>
 * <li>설 명: </li>
 * <li>작성일: 2021. 12. 22. 오후 5:45:14
 * </ul>
 *
 * @author mk1126sj
 *
 */
@Entity(name = "TB_ELCTRN_NTIC_SNDNG_RESULT")
@Getter @ToString
@Builder
@DynamicUpdate //null 필드는 update 쿼리에서 제외
/*
 * @NoArgsConstructor, @AllArgsConstructor 추가
 * 	-.@Builder만 사용할 경우 queryDsl Select 시 기본생성자 오류("No default constructor for entity") 발생
 */
@NoArgsConstructor
@AllArgsConstructor
public class ElctrnNticSndngResult {


	@Id
	@Column(length = 20)
	private String elctrnNticSndngDetailId; //전자 고지 발송 상세 ID
	@Column(length = 4)
	private String sndngResultCode          ; //발송 결과 코드(1: 발송성공, 2:열람, 3:발송실패)
	@Column(length = 14)
	private String requstDt                  ; //요청 일시
	@Column(length = 14)
	private String inqireDt                  ; //조회 일시
	@Column(length = 14)
	private String readngDt                  ; //열람 일시
	@Column(length = 1000)
	private String errorCn                   ; //오류 내용
	@Embedded
	private FieldCreate create;
}
