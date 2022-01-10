package cokr.xit.modules.kkomydoc.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import cokr.xit.modules.domain.SendMast;
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
public class NotiDetailsKkoMydoc {


	@Id
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "NotiDetailsKkoMydoc_Generator")
	@TableGenerator(table = "module_post_sequence", name = "NotiDetailsKkoMydoc_Generator"
					,pkColumnName = "seq_name", pkColumnValue = "NotiDetailsKkoMydoc_id"
					,initialValue = 0, allocationSize = 50)
	private Long notiDetailsId;               //ID(PK)
	
	@ManyToOne
	@JoinColumn(name = "SEND_MAST_ID")
	private SendMast sendMast;               //발송마스터(FK)
	
	private String payload;                  //이용기관이 생성한 payload 값
	
	@Lob
	private String details;                  //청구서 페이지 내에 보여줄 상세 정보
	
	@CreationTimestamp
	private LocalDateTime registDt;          //등록일시
	
	@UpdateTimestamp
	private LocalDateTime lastUpdtDt;        //최종수정일시
}
