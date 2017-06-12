package push.util;

public class StrUtil {

	/**
	 * 首字母大写
	 * @param name
	 * @return
	 */
	public static String captureName(String name) {
		name = name.substring(0, 1).toUpperCase() + name.substring(1);
		return name;

	}
}
