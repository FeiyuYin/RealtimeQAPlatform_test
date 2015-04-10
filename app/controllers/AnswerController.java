package controllers;

import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.databind.JsonNode;
import models.Answer;
import models.Question;
import models.User;
import play.db.ebean.Model;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

import static play.libs.Json.toJson;

/**
 * Created by yin on 15-4-9.
 */
public class AnswerController extends Controller {
    public static Result createAnswer(){
        JsonNode json = request().body().asJson();
        if (json == null){
            return badRequest("Expecting a Json input");
        }

        Long uId = json.findPath("uId").asLong();
        User u = (User)new Model.Finder(String.class, User.class).byId(uId);
        if(u == null){
            return badRequest("user Id does not exist");
        }
        Long qId = json.findPath("qId").asLong();
        Question q = (Question)new Model.Finder(String.class, Question.class).byId(qId);
        if(q == null){
            return badRequest("question Id does not exist");
        }
        String aContent = json.findPath("content").asText();
        boolean isBest = json.findPath("isBest").asBoolean();
        int views = json.findPath("views").asInt();
        int likes = json.findPath("likes").asInt();
        Answer a = new Answer();
        a.setContent(aContent);
        a.setIsBest(isBest);
        a.setLikes(likes);
        a.setViews(views);
        a.setQ(q);
        a.setU(u);
        Ebean.save(a);
        return ok(toJson(a));
    }

    public static Result getAnswer(Long id){
        Answer a = (Answer)new Model.Finder(String.class, Answer.class).byId(id);
        if(a == null){
            return badRequest("Id does not exist");
        }
        else{
            return ok(toJson(a));
        }
    }

    public static Result getAnswers(){
        List<Answer> answers = new Model.Finder(String.class, Answer.class).all();
        return ok(toJson(answers));
    }

    public static Result updateAnswer(Long id){
        Answer a = (Answer)new Model.Finder(String.class, Answer.class).byId(id);
        if(a == null){
            return badRequest("Id does not exist");
        }
        JsonNode json = request().body().asJson();
        if (json == null){
            return badRequest("Expecting a Json input");
        }

        String aContent = json.findPath("content").asText();
        boolean isBest = json.findPath("isBest").asBoolean();
        int views = json.findPath("views").asInt();
        int likes = json.findPath("likes").asInt();
        a.setContent(aContent);
        a.setIsBest(isBest);
        a.setLikes(likes);
        a.setViews(views);
        Ebean.save(a);
        return ok(toJson(a));
    }

    public static Result deleteAnswer(Long id){
        Answer a = (Answer)new Model.Finder(String.class, Answer.class).byId(id);
        if(a == null){
            return badRequest("Id does not exist");
        }
        Ebean.delete(a);
        return ok();
    }
}
