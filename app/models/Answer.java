package models;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by yin on 15-4-1.
 */
@Entity
public class Answer {
    @Id
    private long aId;

    private String content;

    private boolean isBest;

    private int likes;

    private int views;

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
