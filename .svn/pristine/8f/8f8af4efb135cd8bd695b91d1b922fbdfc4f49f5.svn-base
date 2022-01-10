package cokr.xit.modules.nicedici.service;



import org.springframework.http.HttpStatus;

import KISINFO.VNO.VNOInterop;
import cokr.xit.modules.nicedici.model.DiCiRespDTO;

public class Interop
{	
    public Interop()
	{
	    
	}
    
	public static DiCiRespDTO getCI(String siteCode, String sitePw, String jumin) {
		
		String sSiteCode	= siteCode;		// NICE평가정보에서 발급한 서비스 사이트코드
		String sSitePw		= sitePw;		// NICE평가정보에서 발급한 서비스 사이트패스워드
		
		String sJumin		= jumin;		// 주민등록번호 13자리
		String sFlag		= "JID";	// 서비스 구분값 (JID:주민번호 이용)
		
		
		// 모듈 객체 생성
		VNOInterop vnoInterop = new VNOInterop();		
		
		DiCiRespDTO diCiRespDTO = new DiCiRespDTO();
	    try {
			
	    	/* ──── CI 값을 추출하기 위한 부분 Start */
	    	// 인증요청처리
	    	int iRtnCI = vnoInterop.fnRequestConnInfo(sSiteCode, sSitePw, sJumin, sFlag);
	    	System.out.println("iRtnCI=" + iRtnCI);
	    	
	    	// 인증결과코드에 따른 처리
	    	if (iRtnCI == 1) {	    	
	    		// CI 값 추출 (연계정보 확인값, 88Byte)
	    		String sConnInfo = vnoInterop.getConnInfo();
	    		System.out.println("CONNINFO=[" + sConnInfo + "]");
				
				// CI 설정
				diCiRespDTO.setDici(sConnInfo);
	    	} else if (iRtnCI == 3) {
	    		System.out.println("[사용자 정보와 서비스 구분값 매핑 오류]");
	    		System.out.println("사용자 정보와 서비스 구분값이 서로 일치하도록 매핑하여 주시기 바랍니다.");
				
				// Error 설정
				diCiRespDTO.setError_code(iRtnCI+"");
				diCiRespDTO.setError_message("[사용자 정보와 서비스 구분값 매핑 오류] 사용자 정보와 서비스 구분값이 서로 일치하도록 매핑하여 주시기 바랍니다.");
	    	} else if (iRtnCI == -9) {
	    		System.out.println("[입력값 오류]");
	    		System.out.println("fnRequestConnInfo 함수 처리시, 필요한 4개의 파라미터값의 정보를 정확하게 입력해 주시기 바랍니다.");
	    		
	    		// Error 설정
	    		diCiRespDTO.setError_code(iRtnCI+"");
	    		diCiRespDTO.setError_message("[입력값 오류] fnRequestConnInfo 함수 처리시, 필요한 4개의 파라미터값의 정보를 정확하게 입력해 주시기 바랍니다.");
	    	} else if (iRtnCI == -21 || iRtnCI == -31 || iRtnCI == -34) {
	    		System.out.println("[통신오류]");
	    		System.out.println("방화벽 이용 시 아래 IP와 Port(총 5개)를 등록해주셔야 합니다.");
	    		System.out.println("IP : 203.234.219.72 / Port : 81, 82, 83, 84, 85");
	    		
	    		// Error 설정
	    		diCiRespDTO.setError_code(iRtnCI+"");
	    		diCiRespDTO.setError_message("[통신오류] 방화벽 이용 시 아래 IP와 Port(총 5개)를 등록해주셔야 합니다. IP : 203.234.219.72 / Port : 81, 82, 83, 84, 85");
	    	} else {
	    		System.out.println("[기타오류]");
	    		System.out.println("iRtnCI 값 확인 후 NICE평가정보 전산 담당자에게 문의");
	    		
	    		// Error 설정
	    		diCiRespDTO.setError_code(iRtnCI+"");
	    		diCiRespDTO.setError_message("[기타오류] iRtnCI 값 확인 후 NICE평가정보 전산 담당자에게 문의");
	    	}
	    	/* ──── CI 값을 추출하기 위한 부분 End */
		} catch (Exception e) {
			diCiRespDTO.setError_code(String.format("%s %s", HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.name()));
			diCiRespDTO.setError_message(e.getMessage());
		}
	    
	    return diCiRespDTO;
	}
	
	
	
	
	
	public static DiCiRespDTO getDI(String siteCode, String sitePw, String jumin) {
		
		String sSiteCode	= siteCode;		// NICE평가정보에서 발급한 서비스 사이트코드
		String sSitePw		= sitePw;		// NICE평가정보에서 발급한 서비스 사이트패스워드
		
		String sJumin		= jumin;		// 주민등록번호 13자리
		String sFlag		= "JID";	// 서비스 구분값 (JID:주민번호 이용)
		
		
		// 모듈 객체 생성
		VNOInterop vnoInterop = new VNOInterop();		
		
		
		DiCiRespDTO diCiRespDTO = new DiCiRespDTO();
		try {
			/* ──── DI 값을 추출하기 위한 부분 Start */
			// 인증요청처리
			int iRtnDI = vnoInterop.fnRequestDupInfo(sSiteCode, sSitePw, sJumin, sFlag);
			System.out.println("iRtnDI=" + iRtnDI);
			
			// 인증결과코드에 따른 처리
			if (iRtnDI == 1) {	    	
				// DI 값 추출 (중복가입 확인값, 64Byte)
				String sDupInfo = vnoInterop.getDupInfo();
				System.out.println("DUPINFO=[" + sDupInfo + "]");
				
				// DI 설정
				diCiRespDTO.setDici(sDupInfo);
			} else if (iRtnDI == 3) {
				System.out.println("[사용자 정보와 서비스 구분값 매핑 오류]");
				System.out.println("사용자 정보와 서비스 구분값이 서로 일치하도록 매핑하여 주시기 바랍니다.");
				
				// Error 설정
				diCiRespDTO.setError_code(iRtnDI+"");
				diCiRespDTO.setError_message("[사용자 정보와 서비스 구분값 매핑 오류] 사용자 정보와 서비스 구분값이 서로 일치하도록 매핑하여 주시기 바랍니다.");
			} else if (iRtnDI == -9) {
				System.out.println("[입력값 오류]");
				System.out.println("fnRequestDupInfo 함수 처리 시 필요한 4개의 파라미터값의 정보를 정확하게 입력해 주시기 바랍니다.");
				
				// Error 설정
				diCiRespDTO.setError_code(iRtnDI+"");
				diCiRespDTO.setError_message("[입력값 오류] fnRequestDupInfo 함수 처리 시 필요한 4개의 파라미터값의 정보를 정확하게 입력해 주시기 바랍니다.");
			} else if (iRtnDI == -21 || iRtnDI == -31 || iRtnDI == -34) {
				System.out.println("[통신오류]");
				System.out.println("방화벽 이용 시 아래 IP와 Port(총 5개)를 등록해주셔야 합니다.");
				System.out.println("IP : 203.234.219.72 / Port : 81, 82, 83, 84, 85");
				
				// Error 설정
				diCiRespDTO.setError_code(iRtnDI+"");
				diCiRespDTO.setError_message("[통신오류 오류] 방화벽 이용 시 아래 IP와 Port(총 5개)를 등록해주셔야 합니다. IP : 203.234.219.72 / Port : 81, 82, 83, 84, 85");
			} else {
				System.out.println("[기타오류]");
				System.out.println("iRtnDI 값 확인 후 NICE평가정보 전산 담당자에게 문의");
				
				// Error 설정
				diCiRespDTO.setError_code(iRtnDI+"");
				diCiRespDTO.setError_message("[기타오류] iRtnDI 값 확인 후 NICE평가정보 전산 담당자에게 문의");
			}
			/* ──── DI 값을 추출하기 위한 부분 End */
		} catch (Exception e) {
			diCiRespDTO.setError_code(String.format("%s %s", HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.name()));
			diCiRespDTO.setError_message(e.getMessage());
		}
		
		return diCiRespDTO;
	}


}