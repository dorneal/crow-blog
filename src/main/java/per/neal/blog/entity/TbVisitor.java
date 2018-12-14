package per.neal.blog.entity;


import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 来访者表
 *
 * @author neal
 */
public class TbVisitor implements Serializable {
    private static final long serialVersionUID = -2199150842558317542L;
    private long id;
    /**
     * 来访时间
     */
    private java.sql.Timestamp visitTime;

    /**
     * 来访次数
     */
    private int visitTimes;
    /**
     * 来访ID
     */
    private String visitorIp;
    /**
     * 来访地址
     */
    private String visitorLocation;

    private String visitorName;
    private String visitorEmail;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getVisitorIp() {
        return visitorIp;
    }

    public void setVisitorIp(String visitorIp) {
        this.visitorIp = visitorIp;
    }


    public String getVisitorLocation() {
        return visitorLocation;
    }

    public void setVisitorLocation(String visitorLocation) {
        this.visitorLocation = visitorLocation;
    }

    public Timestamp getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(Timestamp visitTime) {
        this.visitTime = visitTime;
    }

    public int getVisitTimes() {
        return visitTimes;
    }

    public void setVisitTimes(int visitTimes) {
        this.visitTimes = visitTimes;
    }

    public String getVisitorName() {
        return visitorName;
    }

    public void setVisitorName(String visitorName) {
        this.visitorName = visitorName;
    }

    public String getVisitorEmail() {
        return visitorEmail;
    }

    public void setVisitorEmail(String visitorEmail) {
        this.visitorEmail = visitorEmail;
    }
}
