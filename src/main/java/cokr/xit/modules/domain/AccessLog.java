package cokr.xit.modules.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import cokr.xit.modules.common.code.AccessStatusCd;
import lombok.Builder;
import lombok.Getter;

@Entity(name = "ENS_ACCESS_LOG")
@Getter
public class AccessLog {


	/* ====================================
	 * @GeneratedValue 어노테이션 전략 4가지
	 *  -.AUTO(default): JPA 구현체가 자동으로 생성 전략 결정.
	 *  -.IDENTITY: 기본키 생성을 데이터베이스에 위임. ex) MySQL의 경우 AUTO INCREMENT를 사용하여 기본키 생성.
	 *  -.SEQUENCE: 데이터베이스의 특별한 오브젝트 시퀀스를 사용하여 기본키를 생성.
	 *  -.TABLE: 데이터베이스에 키 생성 전용 테이블을 하나 만들고 이를 사용하여 기본키를 생성.
	 ==================================== */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long logId;            //로그ID
	
	@Column(length = 2000)
	private String accessToken;    //엑세스토큰
	
	private String reqSystemId;    //요청 시스템 ID(추후 API관리 테이블 적용 시 apiKey로 시스템명을 확인 할 수 있으므로.. 필요 없음..)
	
	private String sessionId;      //세션 ID
	                              
	private String ip;             //접근자 IP
	
	private String httpMethod;     //요청 Method
	
	private String url;            //요청 URL
	                              
	private String uri;            //요청 URI
	                              
	@Lob                             
	private String param;          //요청 parameter(json format String)
	@Lob                           
	private String response;       //응답
	
	@Enumerated(EnumType.STRING)
	private AccessStatusCd status;   //상태
	
	@Lob
	private String errorMsg;       //에러 메시지
	
	@CreationTimestamp             
	private LocalDateTime startDt; //시작 일시
	
	@UpdateTimestamp               
	private LocalDateTime endDt;   //종료 일지
	
	

	@Builder(builderClassName = "reqBuilder", builderMethodName = "reqBuilder")
	public AccessLog(String accessToken, String sessionId, String ip, String httpMethod, String url, String uri, String param) {
		this.accessToken = accessToken;
		this.sessionId = sessionId;
		this.ip = ip;
		this.httpMethod = httpMethod;
		this.uri = uri;
		this.param = param;
		this.status = AccessStatusCd.req;
	}
	
	public void setResponseCompleted(String response) {
		this.status = AccessStatusCd.cmplt;
		this.response = response;
	}
	public void setResponseFail(String errorMsg) {
		this.status = AccessStatusCd.fail;
		this.errorMsg = errorMsg;
	}
	public void setResponseNoAuth(String errorMsg) {
		this.status = AccessStatusCd.noAuth;
		this.errorMsg = errorMsg;
	}
	
}
