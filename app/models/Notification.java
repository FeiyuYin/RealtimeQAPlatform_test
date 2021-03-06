package models;

import play.db.ebean.Model;
import utils.NotificationUtil;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
/**
 * Created by yin on 15-5-9.
 */
@Entity
public class Notification extends Model {

    @Id
    private long nId;

    @ManyToOne
    private Question q;

    @ManyToOne
    private User u;

    private String status;

    private String createTime;

    private String createDate;

    private NotificationUtil.NotificationType type;

    private String comment;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getnId() {
        return nId;
    }

    public void setnId(long nId) {
        this.nId = nId;
    }

    public Question getQ() {
        return q;
    }

    public void setQ(Question q) {
        this.q = q;
    }

    public User getU() {
        return u;
    }

    public void setU(User u) {
        this.u = u;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getType(){
        return type.toString();
    }

    public void setType(NotificationUtil.NotificationType type){
        this.type = type;
    }
}
