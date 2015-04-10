package models;

import play.db.ebean.Model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Created by yin on 15-4-5.
 */
@Entity
public class User extends Model {
    @Id
    private long uId;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

//    @OneToMany(cascade= CascadeType.ALL)
//    List<Question> qs;

//    public List<Question> getQs() {
//        return qs;
//    }
//
//    public void setQs(List<Question> qs) {
//        this.qs = qs;
//    }

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

    public long getpId() {
        return uId;
    }

    public void setpId(long uId) {
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
