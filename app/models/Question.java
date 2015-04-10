package models;

import play.db.ebean.Model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by yin on 15-4-1.
 */

@Entity
public class Question extends Model {

    @Id
    private long qId;

    private String title;

    private String content;

    @ManyToOne
    private User u;

//    @OneToMany(cascade= CascadeType.ALL)
//    List<Answer> as;

//    public List<Answer> getAs() {
//        return as;
//    }
//
//    public void setAs(List<Answer> as) {
//        this.as = as;
//    }

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


}
