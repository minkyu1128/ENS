package cokr.xit.modules.nicedici.service;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import cokr.xit.modules.common.code.InternalErrCd;
import cokr.xit.modules.common.exception.InternalException;
import cokr.xit.modules.domain.common.FieldError;
import cokr.xit.modules.nicedici.domain.NiceCiMng;
import cokr.xit.modules.nicedici.domain.NiceCiMngRepository;
import cokr.xit.modules.nicedici.domain.NiceJidMng;
import cokr.xit.modules.nicedici.domain.NiceJidMngRepository;
import cokr.xit.modules.nicedici.model.DiCiRespDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class NiceDiCiService {

	private final NiceJidMngRepository niceJidMngRepository;
	private final NiceCiMngRepository niceCiMngRepository;
	
	/**
	 * 주민번호로 CI를 취득 한다.
	 * 	-.CI: 연계정보(Connecting Information)
	 * 	-.국가표준 규격에 따라 사용자의 주민번호를 암호화한 개인 식별값.
	 * 	-.서비스에 상관없이 값이 일정 함.
	 * 	-.주민번호 -> 해쉬 -> CI 값 (88 byte)
	 * @param siteCode
	 * @param sitePw
	 * @param jumin
	 */
	@SuppressWarnings("deprecation")
	@Transactional(noRollbackFor = Exception.class)
	public DiCiRespDTO ci(String siteCode, String sitePw, String jumin) {
		
		/* ========================
		 * validate
		 ======================== */
		if(StringUtils.isEmpty(siteCode))
			throw new InternalException(InternalErrCd.ERR401, "사이트코드(은)는 필수조건 입니다.");
		if(StringUtils.isEmpty(sitePw))
			throw new InternalException(InternalErrCd.ERR401, "사이트 패스워드(은)는 필수조건 입니다.");
		if(StringUtils.isEmpty(jumin))
			throw new InternalException(InternalErrCd.ERR401, "서비스 구분값(주민번호:JID)(은)는 필수조건 입니다.");
		
		

		
		/* ========================
		 * find ci
		 ======================== */
		String jid = null;
		try {
			jid = hexSha256(jumin); //단방향 암호화
		} catch (NoSuchAlgorithmException | IOException e) {
			log.error("주민번호 단방향암호화(sha256) 실패:: %e", e.getMessage(), e);
		}	
		String ci = niceCiMngRepository.findCiByJid(jid);
		if(ci!=null)	//등록된 CI가 있으면..
			return DiCiRespDTO.builder()
					.dici(ci)
					.build();
		

		
		/* ========================
		 * api call
		 ======================== */
		DiCiRespDTO diciRespDTO = Interop.getCI(siteCode, sitePw, jumin);
		

		
		/* ========================
		 * insert Jid or Ci
		 * 	-.보안정책상 주민번호와 CI는 분리 저장한다.
		 ======================== */
		NiceJidMng niceJidMng = NiceJidMng.builder()
				.Jid(jid)
				.error(FieldError.initBuilder()
						.errorCode(diciRespDTO.getError_code())
						.errorMessage(diciRespDTO.getError_message())
						.build())
				.build();
		niceJidMngRepository.save(niceJidMng);
		NiceCiMng niceCiMng = NiceCiMng.builder()
				.ci(diciRespDTO.getDici())
				.niceJidMng(niceJidMng)
				.build();
		niceCiMngRepository.save(niceCiMng);
		
		
		return diciRespDTO;
		
	}
	
	
//	/**
//	 * 주민번호로 DI를 취득 한다.
//	 * 	-.DI: 중복가입정보(Duplicate Info)
//	 * 	-.국가표준 규격에 따라 사용자의 주민번호와 CP코드를 암호화한 개인 식별값.
//	 * 	-.여러 인증 서비스 이용 시 CP코드를 동일하게 맞추면 DI값이 같아지므로 동일인임을 인식 할수 있어 중복가입을 방지 할 수 있음.
//	 * 	-.주민번호 + CP코드(계약된 서비스의 12자리 키 값) -> 해쉬 -> DI값 (64 byte) 
//	 * @param siteCode
//	 * @param sitePw
//	 * @param jumin
//	 */
//	@SuppressWarnings("deprecation")
//	@Transactional(noRollbackFor = Exception.class)
//	public void di(String siteCode, String sitePw, String jumin) {
//
//		/* ========================
//		 * validate
//		 ======================== */
//		if(StringUtils.isEmpty(siteCode))
//			throw new RuntimeException("사이트코드(은)는 필수조건 입니다.");
//		if(StringUtils.isEmpty(sitePw))
//			throw new RuntimeException("사이트 패스워드(은)는 필수조건 입니다.");
//		if(StringUtils.isEmpty(jumin))
//			throw new RuntimeException("서비스 구분값(주민번호:JID)(은)는 필수조건 입니다.");
//		
//		
//		DiCiRespDTO diciRespDTO = Interop.getDI(siteCode, sitePw, jumin);
//	}
	
	

	/**
     * sha256 암호화
     * @param text
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    public static String hexSha256(String text) throws IOException, NoSuchAlgorithmException{
  	    StringBuffer sbuf = new StringBuffer();

  	    MessageDigest mDigest = MessageDigest.getInstance("SHA-256");
  	    mDigest.update(text.getBytes());

  	    byte[] msgStr = mDigest.digest() ;

  	    for(int i=0; i < msgStr.length; i++){
  	        byte tmpStrByte = msgStr[i];
  	        String tmpEncTxt = Integer.toString((tmpStrByte & 0xff) + 0x100, 16).substring(1);

  	        sbuf.append(tmpEncTxt) ;
  	    }

  	    return sbuf.toString();
  	}
	
	
}
