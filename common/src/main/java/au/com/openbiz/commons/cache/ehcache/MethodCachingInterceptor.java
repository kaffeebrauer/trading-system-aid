package au.com.openbiz.commons.cache.ehcache;

import java.io.Serializable;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;

public class MethodCachingInterceptor implements MethodInterceptor {
	
	private static final Logger LOGGER = Logger.getLogger(MethodCachingInterceptor.class);

	private Cache cache;
	
	public MethodCachingInterceptor(Cache cache) {
		this.cache = cache;
	}
	
	public Object invoke(MethodInvocation invocation) throws Throwable {
		String className = invocation.getThis().getClass().getName();
		String methodName = invocation.getMethod().getName();
		Object[] arguments = invocation.getArguments();
		Object result;
		
		String cacheKey = createCacheKey(className, methodName, arguments);
		LOGGER.debug("Looking for [" + cacheKey + "] in the cache.");
		Element element = cache.get(cacheKey);
		if(element == null) {
			LOGGER.debug("Calling intercepted method.");
			result = invocation.proceed();
			
			LOGGER.debug("Caching the result [" + result + "].");
			element = new Element(cacheKey, (Serializable) result);
			cache.put(element);
		}
		
		return element.getValue();
	}
	
	private String createCacheKey(String className, String methodName, Object[] arguments) {
		final String DELIMITER = ".";
		StringBuffer cacheKey = new StringBuffer();
		
		cacheKey.append(className)
			.append(DELIMITER)
			.append(methodName);
		for(int i=0; i<arguments.length; i++) {
			Cacheable cacheableObject = (Cacheable)arguments[i];
			cacheKey.append(DELIMITER).append(cacheableObject.generateCacheKey());
		}
		
		return cacheKey.toString();
	}

}
