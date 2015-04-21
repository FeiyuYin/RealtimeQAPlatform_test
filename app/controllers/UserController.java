package controllers;

import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.databind.JsonNode;
import models.Category;
import models.User;
import play.db.ebean.Model;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.ArrayList;
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
        if(json.findPath("firstName") == null){
            return badRequest("No firstName field");
        }
        if(json.findPath("lastName") == null){
            return badRequest("No lastName field");
        }
        if(json.findPath("email") == null){
            return badRequest("No email field");
        }
        if(json.findPath("password") == null){
            return badRequest("No password field");
        }
        if(json.findValuesAsText("cIds") == null){
            return badRequest("No cIds field");
        }

        String firstName = json.findPath("firstName").textValue();
        String lastName = json.findPath("lastName").textValue();
        String email = json.findPath("email").textValue();
        String password = json.findPath("password").textValue();
        List<String> cNames =  json.findValuesAsText("cIds");

        List<Category> cs = new ArrayList<Category>();
        for(int i = 0; i < cNames.size(); i ++){
            Category c = (Category) new Model.Finder(String.class, Category.class).byId(Long.parseLong(cNames.get(i)));
            if (c != null){
                cs.add(c);
            }
        }
        User u = new User();
        u.setEmail(email);
        u.setFirstName(firstName);
        u.setLastName(lastName);
        u.setPassword(password);
        u.setExpertises(cs);
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

        List<String> cNames =  json.findValuesAsText("cIds");

        List<Category> cs = new ArrayList<Category>();
        for(int i = 0; i < cNames.size(); i ++){
            Category c = (Category) new Model.Finder(String.class, Category.class).byId(Long.parseLong(cNames.get(i)));
            if (c != null){
                cs.add(c);
            }
        }

        if(json.findPath("firstName") != null){
            u.setFirstName(json.findPath("firstName").textValue());
        }
        if(json.findPath("lastName") != null){
            u.setLastName(json.findPath("lastName").textValue());
        }
        if(json.findPath("email") != null){
            u.setEmail(json.findPath("email").textValue());
        }
        if(json.findPath("password") != null){
            u.setPassword(json.findPath("password").textValue());
        }
        if(cs.size() != 0){
            u.setExpertises(cs);
        }
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
