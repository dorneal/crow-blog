package per.neal.blog.entity;

import java.io.Serializable;

/**
 * token 实体
 *
 * @author neal
 */
public class Token implements Serializable {
    private static final long serialVersionUID = 8910384187021467929L;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密文密码
     */
    private String passKey;
    /**
     * 是否记住密码
     */
    private boolean rememberMe;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassKey() {
        return passKey;
    }

    public void setPassKey(String passKey) {
        this.passKey = passKey;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    @Override
    public String toString() {
        return "Token{" +
                "username='" + username + '\'' +
                ", passKey='" + passKey + '\'' +
                ", rememberMe=" + rememberMe +
                '}';
    }
}
