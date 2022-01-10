package cokr.xit.modules.nicedici.domain;

import java.time.LocalDateTime;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

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
public class NiceJidMng {

	@Id
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "NiceJidMng_Generator")
	@TableGenerator(table = "module_post_sequence", name = "NiceJidMng_Generator"
					,pkColumnName = "seq_name", pkColumnValue = "NiceJidMng_id"
					,initialValue = 0, allocationSize = 50)
	private Long jidMngId;             //ID(PK)
	
	private String Jid;    			   //주민번호 13자리
	
	@CreationTimestamp   
	private LocalDateTime registDt;        //등록일시
	
	@Embedded
	private FieldError error;
	
}
