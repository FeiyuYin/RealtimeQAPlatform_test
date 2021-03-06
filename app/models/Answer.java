package models;

import javax.persistence.*;

/**
 * Created by yin on 15-4-1.
 */
@Entity
public class Answer {
    @Id
    private long aId;

    @Column(columnDefinition = "TEXT")
    private String content;

    private boolean isBest;

    private int likes;

    private int views;

    @ManyToOne
    private Question q;

    @ManyToOne
    private User u;

    private boolean hasVoice;

    private boolean hasImage;

    private String createTime;

    private String createDate;

    private String aUUID;

    public String getaUUID() {
        return aUUID;
    }

    public void setaUUID(String aUUID) {
        this.aUUID = aUUID;
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

    public boolean isHasVoice() {
        return hasVoice;
    }

    public void setHasVoice(boolean hasVoice) {
        this.hasVoice = hasVoice;
    }

    public boolean isHasImage() {
        return hasImage;
    }

    public void setHasImage(boolean hasImage) {
        this.hasImage = hasImage;
    }

    public User getU() {
        return u;
    }

    public void setU(User u) {
        this.u = u;
    }

    public Question getQ() {
        return q;
    }

    public void setQ(Question q) {
        this.q = q;
    }

    public long getaId() {
        return aId;
    }

    public void setaId(long aId) {
        this.aId = aId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isBest() {
        return isBest;
    }

    public void setIsBest(boolean isBest) {
        this.isBest = isBest;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }
}
