package cokr.xit.modules.post.constant;

import org.springframework.http.HttpMethod;

/**
 * <ul>
 * <li>업무 그룹명: 연계API 주소 정보</li>
 * <li>설 명: </li>
 * <li>작성일: 2021. 8. 4. 오후 3:51:43
 * </ul>
 * @author 박민규
 */
public enum ApiAdrsInf {

	 NV_BILL_REQ  ("https://nsign-gw.naver.com/invoice/v1/request"     , HttpMethod.POST) //고지서 발송 요청
	,NV_BILL_NONCE("https://nsign-gw.naver.com/invoice/v1/nonce/status", HttpMethod.POST) //nonce 검증 요청
	,NV_BILL_STAT ("https://nsign-gw.naver.com/invoice/v1/status"      , HttpMethod.POST) //고지서 상태 조회
	,NV_BILL_PING ("https://nsign-gw.naver.com/invoice/v1/ping"        , HttpMethod.GET ) //고지서 서버 ping check
	
	,KKO_BILL_REQ    ("https://bill.dozn.co.kr/external/inquiry-payment/one-time/url"   , HttpMethod.POST)  //청구서 링크 생성
	,KKO_BILL_REREQ  ("https://bill.dozn.co.kr/external/inquiry-payment/one-time/url/re", HttpMethod.POST)  //청구서 링크 재생성
	,KKO_BILL_NOTI   ("https://#MySelfHostAdrs#/kakao/notice"                           , HttpMethod.POST)  //청구서 조회
	,KKO_BILL_PREPAY ("https://#MySelfHostAdrs#/kakao/prepay"                           , HttpMethod.POST)  //납부 가능 조회
	,KKO_BILL_RSLT   ("https://#MySelfHostAdrs#/kakao/pay-result"                       , HttpMethod.POST)  //납부 결과 전달
	;
	final String url;
	final HttpMethod method;
	ApiAdrsInf(String url, HttpMethod method){
		this.url = url;
		this.method = method;
	}
	public String getUrl() {
		return url;
	}
	public HttpMethod getMethod() {
		return method;
	}
}
