package cokr.xit.modules.kkomydoc.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.DynamicUpdate;

import cokr.xit.modules.domain.SendMast;
import cokr.xit.modules.kkomydoc.code.DocBoxStatusCd;
import cokr.xit.modules.kkomydoc.model.StatusRespDTO;
import cokr.xit.utils.CmmnUtil;
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
public class SendDetailKkoMydoc {

	@Id
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "SendDetailKkoMydoc_Generator")
	@TableGenerator(table = "module_post_sequence", name = "SendDetailKkoMydoc_Generator"
					,pkColumnName = "seq_name", pkColumnValue = "SendDetailKkoMydoc_id"
					,initialValue = 0, allocationSize = 50)
	private Long sendDetailId;               //ID(PK)
	
	@ManyToOne
	@JoinColumn(name = "SEND_MAST_ID")
	private SendMast sendMast;               //발송마스터(FK)
	
	private String title;                    //제목
	
	private String message;                  //내용
	
	private String ci;                       //수신자 ci
	
	private String phoneNumber;              //수신자 연락처
	
	private String name;                     //이름
	
	private String birthday;                 //생년월일
	
	private String payload;                  //이용기관이 생성한 payload 값
	
	private String externalDocumentUuid;     //bulk발송 시 문서발송 대상자와 문서식별번호를 매칭하는 값
	
	private String link;                     //본인인증 후 사용자에게 보여줄 웹페이지 주소
	
	
	/* =============================================== */
	@Enumerated(EnumType.STRING)
	private DocBoxStatusCd docBoxStatus;     //진행 상태
	private String docBoxSentDt;             //송신 시간(yyyyMMddHHmmss)
	private String docBoxReceivedDt;         //수신 시간(yyyyMMddHHmmss)
	private String frstAuthenticatedDt;      //열람 인증을 성공한 최초 시간(yyyyMMddHHmmss)
	private String frstTokenUsedDt;          //OTT 검증을 성공한 최초 시간(yyyyMMddHHmmss)
	private String frstDocBoxReadDt;         //최초 열람 시간(yyyyMMddHHmmss)
	private String userNotifiedDt;           //알림톡 수신 시간(yyyyMMddHHmmss)
	
	/**
	 * <pre>메소드 설명: 문서상태조회API 결과 설정</pre>
	 * @param statusRespDTO void 요청처리 후 응답객체
	 * @author: 박민규
	 * @date: 2021. 11. 23.
	 */
	public void setTokenVerifyDt(Long tokenUsedAt) {
		this.frstTokenUsedDt = CmmnUtil.absSecTimeToDate(tokenUsedAt, "yyyyMMddHHmmss");
	}
	
	/**
	* <pre>메소드 설명: 문서상태조회API 결과 설정</pre>
	* @param statusRespDTO void 요청처리 후 응답객체
	* @author: 박민규
	* @date: 2021. 11. 23.
	*/
	public void setStatusInfo(StatusRespDTO statusRespDTO) {
//		this.docBoxStatus         = DocBoxStatusCd.valueOf(statusRespDTO.getDoc_box_status());
		this.docBoxStatus         = statusRespDTO.getDoc_box_status()==null?DocBoxStatusCd.UNKOWN:DocBoxStatusCd.valueOf(statusRespDTO.getDoc_box_status());
		this.docBoxSentDt         = CmmnUtil.absSecTimeToDate(statusRespDTO.getDoc_box_sent_at()    , "yyyyMMddHHmmss");
		this.docBoxReceivedDt     = CmmnUtil.absSecTimeToDate(statusRespDTO.getDoc_box_received_at(), "yyyyMMddHHmmss");
		this.frstAuthenticatedDt  = CmmnUtil.absSecTimeToDate(statusRespDTO.getAuthenticated_at()   , "yyyyMMddHHmmss");
		this.frstTokenUsedDt      = CmmnUtil.absSecTimeToDate(statusRespDTO.getToken_used_at()      , "yyyyMMddHHmmss"); 
		this.frstDocBoxReadDt     = CmmnUtil.absSecTimeToDate(statusRespDTO.getDoc_box_read_at()    , "yyyyMMddHHmmss");
		this.userNotifiedDt       = CmmnUtil.absSecTimeToDate(statusRespDTO.getUser_notified_at()   , "yyyyMMddHHmmss");
	}
	public void setStatusInfo(StatusKkoMydoc statusKkoMydoc) {
		this.docBoxStatus         = statusKkoMydoc.getDocBoxStatus();
		this.docBoxSentDt         = CmmnUtil.absSecTimeToDate(statusKkoMydoc.getDocBoxSentAt()    , "yyyyMMddHHmmss");
		this.docBoxReceivedDt     = CmmnUtil.absSecTimeToDate(statusKkoMydoc.getDocBoxReceivedAt(), "yyyyMMddHHmmss");
		this.frstAuthenticatedDt  = CmmnUtil.absSecTimeToDate(statusKkoMydoc.getAuthenticatedAt()   , "yyyyMMddHHmmss");
		this.frstTokenUsedDt      = CmmnUtil.absSecTimeToDate(statusKkoMydoc.getTokenUsedAt()      , "yyyyMMddHHmmss"); 
		this.frstDocBoxReadDt     = CmmnUtil.absSecTimeToDate(statusKkoMydoc.getDocBoxReadAt()    , "yyyyMMddHHmmss");
		this.userNotifiedDt       = CmmnUtil.absSecTimeToDate(statusKkoMydoc.getUserNotifiedAt()   , "yyyyMMddHHmmss");
	}
}
