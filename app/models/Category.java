package models;

import play.db.ebean.Model;

import javax.persistence.CascadeType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yin on 15-4-18.
 */
public class Category extends Model {
    @Id
    private Long cId;

    private String name;

    private int followerNumber;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<User> users = new ArrayList<User>();

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Question> questions = new ArrayList<Question>();

    public int getFollowerNumber() {
        return followerNumber;
    }

    public void setFollowerNumber(int followerNumber) {
        this.followerNumber = followerNumber;
    }

    public Long getcId() {
        return cId;
    }

    public void setcId(Long cId) {
        this.cId = cId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
