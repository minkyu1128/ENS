package cokr.xit.modules.kkomydoc;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import cokr.xit.modules.kkomydoc.model.OttRespDTO;
import cokr.xit.utils.CmmnUtil;
import cokr.xit.utils.RequireValidator;
import net.bytebuddy.utility.RandomString;

public class TempTest {

	@Test @Disabled
	void case1() {
		OttRespDTO token = new OttRespDTO();


		try {
			RequireValidator.builder().obj(token).build().validate().throwableException();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test @Disabled
	void case2() {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String externalDocumentUuid = String.format("S%s%s%09d", simpleDateFormat.format(new Date()), RandomString.make(5), 1) ;
		System.out.println(externalDocumentUuid);
		System.out.println(externalDocumentUuid.length());
	}

	@Test @Disabled
	void case3() {
		//인코딩 이슈 테스트

		String url = "http://www.xit.co.kr/%adf";

		UriComponents uri = UriComponentsBuilder
//				.fromHttpUrl(String.format("%s?%s", url, body==null?"":body))
				.fromUriString(url)
//				.encode(StandardCharsets.UTF_8)
				.build();

		System.out.println(uri.toString());
		System.out.println(uri.toUriString());
		System.out.println(uri.toUri());
	}

	@Test
	void case4() {
//		String opratDstnc = "6210.55";
//		String lbrctQy = "2352.00";
//
//		String result = null;
//		try {
//			DecimalFormat format = new DecimalFormat("0.###");
//			result = format.format(Double.parseDouble(opratDstnc)/Double.parseDouble(lbrctQy));
//			System.out.println(result);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}



		byte[] enc = Base64.encodeBase64("test".getBytes());
		System.out.println(new String(Base64.decodeBase64("b40d3c623d1011ecbceae6cf4630da62")));


		System.out.println(CmmnUtil.absSecTimeToDate(1638777285, null));
		

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MINUTE, -3);
		String beginSndngDt = String.format("%s00", dateFormat.format(cal.getTime()));
		String endSndngDt = String.format("%s59", dateFormat.format(new Date()));
		System.out.println(beginSndngDt);
		System.out.println(endSndngDt);
	}
}
