package cokr.xit.modules.kkomydoc.domain;

import java.time.LocalDateTime;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import cokr.xit.modules.domain.common.FieldError;
import cokr.xit.modules.kkomydoc.code.DocBoxStatusCd;
import cokr.xit.modules.kkomydoc.model.StatusRespDTO;
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
public class StatusKkoMydoc {

	@Id
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "StatusKkoMydoc_Generator")
	@TableGenerator(table = "module_post_sequence", name = "StatusKkoMydoc_Generator"
					,pkColumnName = "seq_name", pkColumnValue = "StatusKkoMydoc_id"
					,initialValue = 0, allocationSize = 50)
	private Long statusId;                //ID(PK)
	
	private String documentBinderUuid;    //문서식별번호
	
	@CreationTimestamp   
	private LocalDateTime registDt;        //등록일시
	
	@Enumerated(EnumType.STRING)
	private DocBoxStatusCd docBoxStatus;   //진행 상태
	private Long docBoxSentAt;             //송신 시간
	private Long docBoxReceivedAt;         //수신 시간
	private Long authenticatedAt;          //열람 인증을 성공한 최초 시간
	private Long tokenUsedAt;              //OTT 검증을 성공한 최초 시간
	private Long docBoxReadAt;             //최초 열람 시간
	private Long userNotifiedAt;           //알림톡 수신 시간
	private Long docDistributionReceivedAt;//???
	private String payload;                //이용기관이 문서발송 요청 시 보낸 payload 값
	
	@Embedded
	private FieldError error;
	
	
	
	/**
	 * <pre>메소드 설명: 문서상태조회API 결과 설정</pre>
	 * @param statusRespDTO void 요청처리 후 응답객체
	 * @author: 박민규
	 * @date: 2021. 11. 23.
	 */
	public void setResponse(StatusRespDTO statusRespDTO) {
		this.docBoxStatus              = statusRespDTO.getDoc_box_status()==null?DocBoxStatusCd.UNKOWN:DocBoxStatusCd.valueOf(statusRespDTO.getDoc_box_status());
		this.docBoxSentAt              = statusRespDTO.getDoc_box_sent_at();
		this.docBoxReceivedAt          = statusRespDTO.getDoc_box_received_at();
		this.authenticatedAt           = statusRespDTO.getAuthenticated_at();
		this.tokenUsedAt               = statusRespDTO.getToken_used_at(); 
		this.docBoxReadAt              = statusRespDTO.getDoc_box_read_at();
		this.userNotifiedAt            = statusRespDTO.getUser_notified_at();
		this.docDistributionReceivedAt = statusRespDTO.getDoc_distribution_received_at();
		this.payload                   = statusRespDTO.getPayload();
		this.error                     = FieldError.initBuilder()
				.errorCode(statusRespDTO.getError_code())
				.errorMessage(statusRespDTO.getError_message())
				.build();
	}
}
