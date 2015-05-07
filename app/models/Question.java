package models;

import play.db.ebean.Model;

import javax.persistence.*;
import java.util.ArrayList;
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

    @ManyToOne
    private User answer;

    @ManyToMany(mappedBy = "questions",cascade={CascadeType.ALL})
    private List<Category> cs = new ArrayList<Category>();

    public User getAnswer() {
        return answer;
    }

    public void setAnswer(User answer) {
        this.answer = answer;
    }
    //    @OneToMany(cascade= CascadeType.ALL)
//    List<Answer> as;

//    public List<Answer> getAs() {
//        return as;
//    }
//
//    public void setAs(List<Answer> as) {
//        this.as = as;
//    }

    public List<Category> getCs() {
        return cs;
    }

    public void setCs(List<Category> cs) {
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


}
