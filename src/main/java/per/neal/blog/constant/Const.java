package per.neal.blog.constant;

/**
 * 枚举常量类
 *
 * @author neal
 */
public enum Const {
    /**
     * 操作成功
     */
    SUCCESS(Constants.OK, "操作成功"),
    /**
     * 操作失败
     */
    FAILURE(Constants.ERR, "操作失败");


    /**
     * 响应码
     */
    private int code;
    /**
     * 响应信息
     */
    private String message;

    Const(int code, String message) {
        this.code = code;
        this.message = message;
    }


    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Const{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
