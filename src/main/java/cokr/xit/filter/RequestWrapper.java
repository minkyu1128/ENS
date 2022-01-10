package cokr.xit.filter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.io.IOUtils;
import org.springframework.util.StringUtils; 

/**
 * 
 * <ul>
 * <li>업무 그룹명: RequestWrapper 클래스</li>
 * <li>설 명: getInputStream() 오버라이딩하여 재사용이 가능하도록 한다.</li>
 * <li>작성일: 2021. 11. 30. 오전 11:22:09
 * </ul>
 *
 * @author 박민규
 *
 */
public class RequestWrapper extends HttpServletRequestWrapper{
	private final Charset encoding;
	private byte[] rawData;

	@SuppressWarnings("deprecation")
	public RequestWrapper(HttpServletRequest request) throws IOException {
		super(request);
		String characterEncoding = request.getCharacterEncoding();
		if (StringUtils.isEmpty(characterEncoding)) {
			characterEncoding = StandardCharsets.UTF_8.name();
		}
		this.encoding = Charset.forName(characterEncoding); 
		
		try {
			InputStream inputStream = request.getInputStream();	//getInputStream()은 한번만 사용 가능. 이후 getInputStream 호출 시 "getInputStream() has already been called for this request" 오류 발생  
			this.rawData = IOUtils.toByteArray(inputStream);  //재사용이 가능하도록 byte[] rawData변수에 저장 후 getInputStream()를 오버라이딩하여 rawData 반환
		} catch (IOException e) {
			throw e;
		}

	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.rawData);
		ServletInputStream servletInputStream = new ServletInputStream() {
			@Override
			public boolean isFinished() {
				return false;
			}

			@Override
			public boolean isReady() {
				return false;
			}

			@Override
			public void setReadListener(ReadListener readListener) {
			}

			public int read() throws IOException {
				return byteArrayInputStream.read();
			}
		};
		return servletInputStream;
	}

	@Override
	public BufferedReader getReader() throws IOException {
		return new BufferedReader(new InputStreamReader(this.getInputStream(), this.encoding));
	}

	@Override
	public ServletRequest getRequest() {
		return super.getRequest();
	}
	
		
		
}
