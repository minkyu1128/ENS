package cokr.xit.modules.post.domains.bill.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import lombok.Builder;
import lombok.Data;

/**
 * 고지서 발송정보
 * @author 박민규
 *
 */
//@Entity
@Data
@Builder
public class NaverBillSendInfo {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long billId;                       //ID(PK)
	                                           
	@NotEmpty
	@Length(max = 100)                         
	private String clientId;                   //애플리케이션 등록 시 발급받은 client id 값
	
	@NotEmpty                              
	@Length(max = 100)                         
	private String clientDocId;                //이용기관에서 식별가능한 고유값
	                                           
	@NotEmpty                                
	@Length(max = 100)                         
	private String ci;                         //사용자의 ci
	                                           
	@NotEmpty                                
	@Length(max = 40)                          
	private String title;                      //전자문서 제목
	                                           
	@NotEmpty                                
	@Length(max = 1000)                        
	@Lob                                       
	private String message;                    //원문 데이터
	
	@Length(max = 8)
	private Long validDuration;                //고지서 열람 유효시간(초)
	
	@NotEmpty
	@Length(max = 1000)
	private String redirectUrl;                //인증 후 리다이렉트할 URL
	
	@NotEmpty
	@Length(max = 1)
	private String publishDocumentYn;          //공인전자문서 유통정보 등록 필요 여부(y/n)
	
	@Length(max = 99)
	private String documentHash;               //본문 해쉬값(공인전자주소 유통정보 등록 시 필수)
	
	@NotEmpty
	@Length(max = 20)
	private String callCenterNo;               //고객센터 전화번호
	
	@Length(max = 50)
	private String orgId;                      //하위 기관명(필요 시 네이버에서 발급)
	
	@NotEmpty
	@Length(max = 1)
	private String authRequireYn;              //고지서 열람 시 본인인증 사용 여부(y/n)
	
	@Lob
	private String notification;  //알람 관련 설정값
	
	@NotEmpty
	private String result;        //요청 결과(success or fail)
	
	private String docId;         //고지서 고유 식별자
	
	private String errorCode;     //(실패 시) 실패 코드
	
	private String errorMsg;      //(실패 시) 실패 사유
	
	@CreationTimestamp             
	private LocalDateTime RegDt;  //등록 일시
}
