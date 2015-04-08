package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by yin on 15-3-28.
 */

@Entity
public class Person extends Model{
    @Id
    private long pId;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String address;
}
