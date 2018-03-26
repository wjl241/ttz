package util.datasources;

/**
 * 数据库切换的工具类
 * 
 * @author Joey
 * @project:SSM_MultiDataSource_CP
 * @date：2017年4月27日
 * 
 */
public class DataSourceContextHolder {

	public static final String DATA_SOURCE_A = "dataSource1";
	public static final String DATA_SOURCE_B = "dataSource2";

	/** 数据源类型 */
	private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

	/**
	 * 设置数据源类型
	 * 
	 * @param dbType
	 *            数据源类型
	 */
	public static void setDbType(String dbType) {
		contextHolder.set(dbType);
	}

	/**
	 * 获取数据源类型
	 * 
	 * @return String
	 */
	public static String getDbType() {
		return ((String) contextHolder.get());
	}

	/**
	 * 清除数据源类型
	 */
	public static void clearDbType() {
		contextHolder.remove();
	}

}
