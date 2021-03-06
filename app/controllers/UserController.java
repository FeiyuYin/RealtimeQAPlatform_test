package controllers;

import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Category;
import models.User;
import org.json.JSONArray;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import utils.UserUtil;

import java.util.ArrayList;
import java.util.List;

import java.util.HashSet;
import java.util.Set;


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
        if(json.findValuesAsText("uMId") == null){
            return badRequest("No uMId field");
        }

        String firstName = json.findPath("firstName").textValue();
        String lastName = json.findPath("lastName").textValue();
        String email = json.findPath("email").textValue();
        String uMId = json.findPath("uMId").textValue();
        String password = json.findPath("password").textValue();
        List<String> cNames =  json.findValuesAsText("cIds");

        Set<Category> cs = new HashSet<Category>();
        for(int i = 0; i < cNames.size(); i ++){
            Category c = Ebean.find(Category.class, Long.parseLong(cNames.get(i)));
            if (c != null){
                cs.add(c);
            }
        }
        User u = new User();
        u.setEmail(email);
        u.setuMId(uMId);
        u.setFirstName(firstName);
        u.setLastName(lastName);
        u.setPassword(password);
        u.setExpertises(cs);
        Ebean.save(u);
        return ok(UserUtil.getUserJson(u));
    }

    public static Result getUser(Long id){
        User u = Ebean.find(User.class, id);
        if(u == null){
            return badRequest("Id does not exist");
        }
        return ok(UserUtil.getUserJson(u));
    }

    public static Result getUserByUMId(String uMId){
        List<User> users = Ebean.find(User.class).findList();
        for (User u : users){
            if (u.getuMId().equals(uMId)){
                return ok(UserUtil.getUserJson(u));
            }
        }
        return badRequest("uMId does not exist");
    }

    public static Result getUsers(){
        List<User> users = Ebean.find(User.class).findList();
        ArrayList<ObjectNode> nodeArray = new ArrayList<>();
        for (User u : users){
            nodeArray.add(UserUtil.getUserJson(u));
        }
        ObjectNode result = Json.newObject();
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode array = mapper.valueToTree(nodeArray);
        result.putArray("results").addAll(array);
        return ok(result);
    }

    public static Result updateUser(Long id){
        User u = Ebean.find(User.class, id);
        if(u == null){
            return badRequest("Id does not exist");
        }
        JsonNode json = request().body().asJson();
        if(json == null){
            return badRequest("Expecting a Json input");
        }

        List<String> cNames =  json.findValuesAsText("cIds");

        Set<Category> cs = new HashSet<Category>();

        if(json.findValue("cIds") != null){
            JSONArray ja = new JSONArray(json.findPath("cIds").toString());
            for(int i = 0; i < ja.length(); i ++){
                Long cId = Long.parseLong(ja.get(i).toString());
                Category c = Ebean.find(Category.class, cId);
                if(c != null){
                    cs.add(c);
                }
            }
        }

        if(json.findPath("firstName").textValue() != null){
            u.setFirstName(json.findPath("firstName").textValue());
        }
        if(json.findPath("lastName").textValue() != null){
            u.setLastName(json.findPath("lastName").textValue());
        }
        if(json.findPath("email").textValue() != null){
            u.setEmail(json.findPath("email").textValue());
        }
        if(json.findPath("password").textValue() != null){
            u.setPassword(json.findPath("password").textValue());
        }
        if(cs.size() != 0){
            u.setExpertises(cs);
        }
        Ebean.save(u);
        return ok(UserUtil.getUserJson(u));
    }

    public static Result deleteUser(Long id){
        User u = Ebean.find(User.class, id);
        if(u == null){
            return badRequest("Id does not exist");
        }
        Ebean.delete(u);
        return ok("{'result' : 'delete successfully'}");
    }
}
