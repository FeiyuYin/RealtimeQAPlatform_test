package controllers;

import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.databind.JsonNode;
import models.User;
import org.springframework.context.support.LiveBeansViewMBean;
import play.db.ebean.Model;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

import static play.libs.Json.toJson;

/**
 * Created by yin on 15-4-9.
 */
public class UserController extends Controller {

    public static Result createUser(){
        JsonNode json = request().body().asJson();
        if(json == null){
            return badRequest("Expecting a Json input");
        }
        String firstName = json.findPath("firstName").textValue();
        String lastName = json.findPath("lastName").textValue();
        String email = json.findPath("email").textValue();
        String password = json.findPath("password").textValue();
        User u = new User();
        u.setEmail(email);
        u.setFirstName(firstName);
        u.setLastName(lastName);
        u.setPassword(password);
        Ebean.save(u);
        return ok(toJson(u));
    }

    public static Result getUser(Long id){
        User u = (User)new Model.Finder(String.class, User.class).byId(id);
        if(u == null){
            return badRequest("Id does not exist");
        }
        return ok(toJson(u));
    }

    public static Result getUsers(){
        List<User> users = new Model.Finder(String.class, User.class).all();
        return ok(toJson(users));
    }

    public static Result updateUser(Long id){
        User u = (User)new Model.Finder(String.class, User.class).byId(id);
        if(u == null){
            return badRequest("Id does not exist");
        }
        JsonNode json = request().body().asJson();
        if(json == null){
            return badRequest("Expecting a Json input");
        }
        String firstName = json.findPath("firstName").textValue();
        String lastName = json.findPath("lastName").textValue();
        String email = json.findPath("email").textValue();
        String password = json.findPath("password").textValue();

        u.setEmail(email);
        u.setFirstName(firstName);
        u.setLastName(lastName);
        u.setPassword(password);
        Ebean.save(u);
        return ok(toJson(u));
    }

    public static Result deleteUser(Long id){
        User u = (User)new Model.Finder(String.class, User.class).byId(id);
        if(u == null){
            return badRequest("Id does not exist");
        }
        Ebean.delete(u);
        return ok();
    }
}
