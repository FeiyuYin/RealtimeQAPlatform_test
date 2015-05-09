package models;

import play.db.ebean.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
/**
 * Created by yin on 15-4-5.
 */
@Entity
public class User extends Model {
    @Id
    private long uId;

    @ManyToMany(mappedBy = "")
    Set<Category> expertises = new HashSet<Category>();

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    public Set<Category> getExpertises() {
        return expertises;
    }

    public void setExpertises(Set<Category> expertises) {
        this.expertises = expertises;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public long getuId() {
        return uId;
    }

    public void setuId(long uId) {
        this.uId = uId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
