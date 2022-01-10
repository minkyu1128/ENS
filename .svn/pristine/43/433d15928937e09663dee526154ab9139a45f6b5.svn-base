package cokr.xit.utils;

import org.springframework.context.ApplicationContext;

public class BeanUtils {
	
	
	public static<T> T getBean(Class<T> type) {
		ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();
		
		if( applicationContext == null ) {
			throw new NullPointerException("no Initialize \"ApplicationContext\"");
		}
		
		return applicationContext.getBean(type);
	}
}
