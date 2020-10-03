package ${groupId}.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式工具类
 *
 * @author ：zhang guo dong
 */
public class RegUtil {

    /**
     * 判断是否是数字
     */
    public static final String REG_NUMBER = "^[0-9]*$";

    /**
     * 获取两个参数中间的内容；String.format(RegularUtil.REG_SUB_FORMATTER, "weight=",";");
     */
    public static final String REG_SUB_FORMATTER = "%s(.*?)%s";

    /**
     * 判断str是否为数字
     *
     * @param str 字符串
     * @return 是/否
     */
    public static boolean isNumberStr(String str) {
        if (str == null || "".equals(str)) {
            return false;
        }
        return str.matches(REG_NUMBER);
    }

    /**
     * 正则表达式匹配两个指定字符串中间的内容
     *
     * @param str 原字符串
     * @param reg 正则表达式
     * @return 匹配到的列表
     */
    public static List<String> getSubStr(String str, String reg) {
        List<String> list = new ArrayList<>();
        Pattern pattern = Pattern.compile(reg);
        Matcher m = pattern.matcher(str);
        while (m.find()) {
            list.add(m.group(1));
        }
        return list;
    }

    /**
     * 返回单个字符串，若匹配到多个的话就返回第一个
     *
     * @param str 原字符串
     * @param reg 正则表达式
     * @return 匹配到的第一个
     */
    public static String getSubStrSimple(String str, String reg) {
        Pattern pattern = Pattern.compile(reg);
        Matcher m = pattern.matcher(str);
        if (m.find()) {
            return m.group(1);
        }
        return "";
    }

}
