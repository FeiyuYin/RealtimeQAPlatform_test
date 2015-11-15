package models;

import play.db.ebean.Model;

import javax.persistence.*;
import java.util.Set;
import java.util.HashSet;
/**
 * Created by yin on 15-4-5.
 */
@Entity
public class User extends Model {
    @Id
    private long uId;

    @ManyToMany(cascade = CascadeType.ALL)
    Set<Category> expertises = new HashSet<Category>();

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private int credit;

    private int exp;

    private String uMId;

    private boolean onLine;

    private long lastActiveTime;

    public long getLastActiveTime() {
        return lastActiveTime;
    }

    public void setLastActiveTime(long lastActiveTime) {
        this.lastActiveTime = lastActiveTime;
    }

    public boolean isOnLine() {
        return onLine;
    }

    public void setOnLine(boolean onLine) {
        this.onLine = onLine;
    }

    public String getuMId() {
        return uMId;
    }

    public void setuMId(String uMId) {
        this.uMId = uMId;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

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
