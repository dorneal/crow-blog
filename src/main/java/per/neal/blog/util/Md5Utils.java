package per.neal.blog.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * MD5工具类
 *
 * @author neal
 */
public final class Md5Utils {
    /**
     * 盐(无特殊需要，请勿更改)
     */
    private static final String SALT = "soiewndfoasdfn";

    /**
     * MD5方法
     *
     * @param text 明文
     * @return 密文
     */
    public static String encryption(String text) {
        // 截取再加密一次
        return md5(md5(text).substring(0, 20));
    }

    /**
     * 加密
     *
     * @param text 明文
     * @return 密文
     */
    private static String md5(String text) {
        //加密后的字符串
        return DigestUtils.md5Hex(text + SALT);
    }
}
