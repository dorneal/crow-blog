package per.neal.blog.entity.dto;

import per.neal.blog.constant.Const;

import java.io.Serializable;

/**
 * 响应结果类
 *
 * @author neal
 */
public class R implements Serializable {
    private static final long serialVersionUID = -917041123169941560L;
    /**
     * 数据
     */
    private Object data;
    /**
     * 响应码
     */
    private int code;
    /**
     * 响应信息说明
     */
    private String message;

    public R(Object data) {
        this(data, Const.SUCCESS.getCode(), Const.SUCCESS.getMessage());
    }

    public R(Object data, int code, String message) {
        this.data = data;
        this.code = code;
        this.message = message;
    }

    public static R ok(Object data) {
        return new R(data);
    }

    public static R ok() {
        return ok(null);
    }

    public static R ok(Object data, int code, String message) {
        return new R(data, code, message);
    }

    public static R err(String errMessage) {
        return new R(null, Const.FAILURE.getCode(), errMessage);
    }

    public static R err() {
        return err(Const.FAILURE.getMessage());
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
