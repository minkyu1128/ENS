package cokr.xit.modules.kkomydoc.domain;

import java.time.LocalDateTime;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import cokr.xit.modules.domain.SendMast;
import cokr.xit.modules.domain.common.FieldError;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter @ToString
@Builder
@DynamicUpdate //null 필드는 update 쿼리에서 제외
/*
 * @NoArgsConstructor, @AllArgsConstructor 추가
 * 	-.@Builder만 사용할 경우 queryDsl Select 시 기본생성자 오류("No default constructor for entity") 발생
 */
@NoArgsConstructor
@AllArgsConstructor
public class SendResultKkoMydoc {

	@Id
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "SendResultKkoMydoc_Generator")
	@TableGenerator(table = "module_post_sequence", name = "SendResultKkoMydoc_Generator"
					,pkColumnName = "seq_name", pkColumnValue = "SendResultKkoMydoc_id"
					,initialValue = 0, allocationSize = 50)
	private Long sendResultId;             //ID(PK)
	
	@ManyToOne
	@JoinColumn(name = "SEND_MAST_ID")
	private SendMast sendMast;             //발송마스터(FK)
	
	private String documentBinderUuid;     //문서식별번호
	
	private String externalDocumentUuid;   //문서발송 대상자와 문서식별번호를 매칭하는 값으로 사용. 반드시 Unique 하게 생성 후 전송. 하나의 Request에 대해서만 Unique 체크가 가능.
	
	@Embedded
	private FieldError error;
	
	@CreationTimestamp   
	private LocalDateTime registDt;        //등록일시
	
}
