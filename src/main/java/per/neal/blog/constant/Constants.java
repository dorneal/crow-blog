package per.neal.blog.constant;

/**
 * 配置常量类
 *
 * @author neal
 */
public final class Constants {
    /**
     * token
     */
    public static final String TOKEN = "token";
    /**
     * 来访宾客角色
     */
    public static final String GUEST = "guest";
    /**
     * 管理员角色
     */
    public static final String ADMIN = "admin";

    /**
     * 成功码
     */
    public static final int OK = 200;
    /**
     * HTTP错误状态码(使用自定义响应码，作为swagger @ApiResponse code 响应码会出错)
     */
    public static final int ERR = 400;

    /**
     * 当前登录用户
     */
    public static final String CURRENT_USER = "currentUser";

    /**
     * 记住密码
     */
    public static final String REMEMBER_ME = "rememberMe";

    /**
     * 最大尝试次数
     */
    public static final int MAX_RETRY_COUNT = 5;

    /**
     * 待定时发布
     */
    public static final int JOB_WORK_WAIT_STATUS = 2;
    /**
     * 已定时发布
     */
    public static final int JOB_WORK_HAS_STATUS = 1;
    /**
     * 发布异常
     */
    public static final int JOB_WORK_ERR_STATUS = 0;

    /**
     * 每小时最多评论三次
     */
    public static final int COMMENT_TIMES = 3;
}
