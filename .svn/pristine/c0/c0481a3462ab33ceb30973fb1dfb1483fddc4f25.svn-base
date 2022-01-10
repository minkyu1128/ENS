package cokr.xit.modules.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import cokr.xit.modules.common.code.PostSeCd;
import cokr.xit.modules.common.code.StatCd;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
//@Data
@Getter @ToString
@Builder
@DynamicUpdate //null 필드는 update 쿼리에서 제외
/*
 * @NoArgsConstructor, @AllArgsConstructor 추가
 * 	-.@Builder만 사용할 경우 queryDsl Select 시 기본생성자 오류("No default constructor for entity") 발생
 */
@NoArgsConstructor
@AllArgsConstructor
public class SendMast {

	@Id
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "SendMast_Generator")
	@TableGenerator(table = "module_post_sequence", name = "SendMast_Generator"
					,pkColumnName = "seq_name", pkColumnValue = "SendMast_id"
					,initialValue = 0, allocationSize = 50)
	private Long sendMastId;               //ID(PK)
	
	@Column(nullable = false)
	private String vender;                 //중계사업자(kakao, naver, kt, Etc...)
	
	@Enumerated(EnumType.STRING)
	private PostSeCd postSe;               //통지서구분(mydoc: 내문서함(구 인증톡), bill: 고지서, Etc...)
	
	private String postTitle;              //통지서명
	
	@CreationTimestamp   
	private LocalDateTime acceptDt;        //접수일시
	
	@Enumerated(EnumType.STRING)
	private StatCd statCd;                 //상태(accept: 접수완료, idle:발송대기(큐 사용 시..), success:발송성공/fail:발송실패, open:열람중, close:조회기간마감, Etc...)
	
	private LocalDateTime sendDt;          //발송일시
	
	@Column(columnDefinition = "integer default 0")
	private Integer sendCnt;                //발송건수
	
	@Column(columnDefinition = "integer default 0")
	private Integer readCnt;                //열람건수(발송한 전체 메시지 중 수신자가 열람한 건수)
	
	private LocalDateTime closeDt;          //마감일시
	
	

	
//	//발송요청정보 설정
//	@Builder(builderMethodName = "setReqInfo", setterPrefix = "set")
//	public SendMast(String vender, PostSeCd postSe, String postTitle, int sendCnt) {
//		this.vender = vender;                //중계사업자                                                                   
//		this.postSe = postSe;                //통지서구분(mydoc: 내문서함(구 인증톡), bill: 고지서, Etc...)                                 
//		this.postTitle = postTitle;          //통지서명                                                                    
//		this.statCd = StatCd.accept;         //상태(accept: 접수완료, idle:발송대기, success:발송성공/fail:발송실패, open:열람중, close:조회기간마감, Etc...)        
//		this.sendCnt = sendCnt;              //발송건수                                                                    
////		this.acceptDt = LocalDateTime.now();
//		
//	}
//	//발송결과 설정
//	public SendMast(StatCd statCd) {
//		this.statCd = statCd;               //상태(accept: 접수완료, idle:발송대기, success:발송성공/fail:발송실패, open:열람중, close:조회기간마감, Etc...)  
//		this.sendDt = LocalDateTime.now();  //발송일시
//	}
	
	
	
	//발송정보 설정
	public void setSendInfo(String vender, String postSe, String postTitle, int sendCnt) {
		setSendInfo(vender, PostSeCd.valueOf(postSe), postTitle, sendCnt);
	}
	//발송정보 설정
	public void setSendInfo(String vender, PostSeCd postSe, String postTitle, int sendCnt) {
		this.vender = vender;                //중계사업자                                                                   
		this.postSe = postSe;                //통지서구분(mydoc: 내문서함(구 인증톡), bill: 고지서, Etc...)                                 
		this.postTitle = postTitle;          //통지서명                                                                    
		this.statCd = StatCd.accept;         //상태(accept: 접수완료, idle:발송대기, success:발송성공/fail:발송실패, open:열람중, close:조회기간마감, Etc...)        
		this.sendCnt = sendCnt;              //발송건수                                                                    
//		this.acceptDt = LocalDateTime.now();
	}
	//발송상태 설정
	public void setSendStatus(String statCd) {
		setSendStatus(StatCd.valueOf(statCd));
	}
	public void setSendStatus(StatCd statCd) {
		this.statCd = statCd;               //상태(accept: 접수완료, idle:발송대기, success:발송성공/fail:발송실패, open:열람중, close:조회기간마감, Etc...)  
		this.sendDt = LocalDateTime.now();  //발송일시
	}
	//현재상태 설정
	public void setCurStatus(StatCd statCd, Integer readCnt) {
		this.statCd = statCd;    //상태(accept: 접수완료, idle:발송대기, success:발송성공/fail:발송실패, open:열람중, close:조회기간마감, Etc...)  
		this.readCnt = readCnt;  //열람건수
	}
	
	
	
}
