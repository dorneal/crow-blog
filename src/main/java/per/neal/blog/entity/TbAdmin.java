package per.neal.blog.entity;


import java.io.Serializable;

/**
 * 管理员实体类
 *
 * @author neal
 */
public class TbAdmin implements Serializable {
    private static final long serialVersionUID = -2004760298209200183L;
    private long id;
    /**
     * 头像地址
     */
    private String avatar;
    /**
     * 登录用户名
     */
    private String username;
    /**
     * 博客名
     */
    private String blogName;
    /**
     * 密文密码(不参与序列化)
     */
    private transient String passKey;
    /**
     * 最后登录时间
     */
    private java.sql.Timestamp lastTime;
    /**
     * 最后登录IP
     */
    private String lastIp;
    /**
     * 个性签名
     */
    private String perSign;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getBlogName() {
        return blogName;
    }

    public void setBlogName(String blogName) {
        this.blogName = blogName;
    }


    public String getPassKey() {
        return passKey;
    }

    public void setPassKey(String passKey) {
        this.passKey = passKey;
    }


    public java.sql.Timestamp getLastTime() {
        return lastTime;
    }

    public void setLastTime(java.sql.Timestamp lastTime) {
        this.lastTime = lastTime;
    }


    public String getLastIp() {
        return lastIp;
    }

    public void setLastIp(String lastIp) {
        this.lastIp = lastIp;
    }


    public String getPerSign() {
        return perSign;
    }

    public void setPerSign(String perSign) {
        this.perSign = perSign;
    }


    @Override
    public String toString() {
        return "TbAdmin{" +
                "id=" + id +
                ", avatar='" + avatar + '\'' +
                ", username='" + username + '\'' +
                ", blogName='" + blogName + '\'' +
                ", passKey='" + passKey + '\'' +
                ", lastTime=" + lastTime +
                ", lastIp='" + lastIp + '\'' +
                ", perSign='" + perSign + '\'' +
                '}';
    }
}
