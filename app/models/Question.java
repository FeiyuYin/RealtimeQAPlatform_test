package models;

import play.db.ebean.Model;

import javax.annotation.PreDestroy;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

/**
 * Created by yin on 15-4-1.
 */

@Entity
public class Question extends Model {

    @Id
    private long qId;

    private String title;

    private String content;

    private String createTime;

    private String createDate;

    private String closeTime;

    private String closeDate;

    private boolean isOpen;

    @ManyToOne
    private User u;

    @OneToOne
    private Answer bestAnswer;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Category> cs = new HashSet<>();

    private int credit;

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public Set<Category> getCs() {
        return cs;
    }

    public void setCs(Set<Category> cs) {
        this.cs = cs;
    }

    public User getU() {
        return u;
    }

    public void setU(User u) {
        this.u = u;
    }

    public long getqId() {
        return qId;
    }

    public void setqId(long qId) {
        this.qId = qId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }


    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public String getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(String closeDate) {
        this.closeDate = closeDate;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setIsOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }


    public Answer getBestAnswer() {
        return bestAnswer;
    }

    public void setBestAnswer(Answer bestAnswer) {
        this.bestAnswer = bestAnswer;
    }

}
