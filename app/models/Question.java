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

    @ManyToOne
    private User u;

    @ManyToOne
    private User answerer;

    @ManyToMany
    private Set<Category> cs = new HashSet<Category>();

//    @PreRemove
//    protected void preRemove(){
//
//        System.out.println("PreRemove is here!!!!!!!!!!!!!!!!!!!!!");
//        for(Category c : this.cs){
//            c.getQuestions().remove(this);
//            c.save();
//        }
//    }

    public User getAnswerer() {
        return answerer;
    }

    public void setAnswerer(User answerer) {
        this.answerer = answerer;
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


}
