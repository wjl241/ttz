package util.datasources;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 动态数据源(default:DataSourceContextHolder.DATA_SOURCE_A)
 * 
 * @author Joey
 * @project:SSM_MultiDataSource_CP
 * @date：2017年4月28日
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface DataSource {

	String value() default DataSourceContextHolder.DATA_SOURCE_A;
	

}