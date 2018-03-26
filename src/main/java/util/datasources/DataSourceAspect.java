package util.datasources;

import java.lang.reflect.Method;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;

/**
 * 数据源前置增强
 * 
 * @author Joey
 * @project:SSM_MultiDataSource_CP
 * @date：2017年4月28日
 * 
 */
public class DataSourceAspect implements MethodBeforeAdvice,
		AfterReturningAdvice {

	@Override
	public void afterReturning(Object returnValue, Method method,
			Object[] args, Object target) throws Throwable {
		DataSourceContextHolder.clearDbType();
	}

	@Override
	public void before(Method method, Object[] args, Object target)
			throws Throwable {
		if (method.isAnnotationPresent(DataSource.class)) {
			DataSource datasource = method.getAnnotation(DataSource.class);
			DataSourceContextHolder.setDbType(datasource.value());
		} else {
			DataSourceContextHolder
					.setDbType(DataSourceContextHolder.DATA_SOURCE_A);
		}

	}
}