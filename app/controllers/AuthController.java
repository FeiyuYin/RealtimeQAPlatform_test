package controllers;

import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Answer;
import models.User;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import utils.NotificationUtil;
import utils.UserUtil;
import java.util.List;
import static play.libs.Json.toJson;

/**
 * Created by yin on 15-5-5.
 */
public class AuthController extends Controller {
   

    public static Result signin(){
        JsonNode json = request().body().asJson();
        if(json == null){
            return badRequest("Expecting a Json input");
        }
        if(json.findPath("email") == null){
            return badRequest("No email field");
        }
        if(json.findPath("password") == null){
            return badRequest("No password field");
        }
        String email = json.findPath("email").textValue();
        String password = json.findPath("password").textValue();

        List<User> us = Ebean.find(User.class).where().eq("email", email).findList();
        if(us != null && us.size() != 0 && us.get(0).getPassword().equals(password)){
            return ok(UserUtil.getUserJson(us.get(0)));
        }
        else{
            return badRequest("Email not exist or wrong password");
        }
    }

    public static Result signup(){
        JsonNode json = request().body().asJson();
        if(json == null){
            return badRequest("Expecting a Json input");
        }
        if(json.findPath("email").textValue() == null){
            return badRequest("No email field");
        }
        if(json.findPath("password").textValue() == null){
            return badRequest("No password field");
        }
        if(json.findValuesAsText("uMId") == null){
            return badRequest("No uMId field");
        }

        String email = json.findPath("email").textValue();
        String password = json.findPath("password").textValue();
        String uMId = json.findPath("uMId").textValue();

        User u = new User();
        u.setEmail(email);
        u.setuMId(uMId);
        u.setPassword(password);
        u.setCredit(100);
        u.setOnLine(true);
        u.setLastActiveTime(System.currentTimeMillis());

        if(json.findPath("firstName").textValue() != null){
            u.setFirstName(json.findPath("firstName").textValue());
        }
        if(json.findPath("lastName").textValue() != null){
            u.setLastName(json.findPath("lastName").textValue());
        }
        Ebean.save(u);
        return ok(toJson(u));
    }

    public static Result heartBeat(Long userId){
        User u = Ebean.find(User.class, userId);
        if(u == null){
            return badRequest("userId does not exist");
        }
        u.setOnLine(true);
        u.setLastActiveTime(System.currentTimeMillis());
        Ebean.save(u);
        return ok();
    }

    public static Result logOff(Long userId){
        User u = Ebean.find(User.class, userId);
        if(u == null){
            return badRequest("userId does not exist");
        }
        u.setOnLine(false);
        u.setLastActiveTime(System.currentTimeMillis());
        Ebean.save(u);
        return ok();
    }

    public static Result isOnLine(Long userId){
        User u = Ebean.find(User.class, userId);
        if(u == null){
            return badRequest("userId does not exist");
        }
        ObjectNode result = Json.newObject();
        return ok(result.put("isOnLine", u.isOnLine()));
    }

    public static Result chat(){
        JsonNode json = request().body().asJson();
        if(json == null){
            return badRequest("Expecting a Json input");
        }
        if(json.findPath("fUId") == null){
            return badRequest("No fUId field");
        }
        if(json.findPath("tUId") == null){
            return badRequest("No tUId field");
        }
        if(json.findPath("uuid") == null){
            return badRequest("No uuid field");
        }
        if(json.findPath("aId") == null){
            return badRequest("No aId field");
        }

        Answer a = Ebean.find(Answer.class, json.findPath("aId").longValue());
        if (a == null){
            return badRequest("answerId does not exist");
        }
        User u = Ebean.find(User.class, json.findPath("tUId").longValue());
        if (u == null){
            return badRequest("target userId does not exist");
        }
        User u2 = Ebean.find(User.class, json.findPath("fUId").longValue());
        if (u == null){
            return badRequest("from userId does not exist");
        }
        String comment = json.findPath("tUId").textValue() + "/" + json.findPath("fUId").textValue() + "/" + json.findPath("uuid").textValue();
        NotificationUtil.generateChatN(u, a.getQ(), comment);

        return ok();
    }
}
