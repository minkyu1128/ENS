package cokr.xit.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class RequestWrapperFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		RequestWrapper readableRequestWrapper = new RequestWrapper((HttpServletRequest) request);	//RequestWrapper 클래스로 wrapping
		chain.doFilter(readableRequestWrapper, response);
	}

}
