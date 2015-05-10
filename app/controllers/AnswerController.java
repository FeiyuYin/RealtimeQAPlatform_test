package controllers;

import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.databind.JsonNode;
import models.Answer;
import models.Question;
import models.User;
import org.json.JSONArray;
import play.db.ebean.Model;
import play.mvc.Controller;
import play.mvc.Result;
import utils.AnswerUtil;

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

        if(json.findPath("uId") == null){
            return badRequest("No uId field");
        }
        if(json.findPath("qId") == null){
            return badRequest("No qId field");
        }
        if(json.findPath("content") == null){
            return badRequest("No content field");
        }
        if(json.findPath("isBest") == null){
            return badRequest("No isBest field");
        }
        if(json.findPath("views") == null){
            return badRequest("No views field");
        }
        if(json.findPath("likes") == null){
            return badRequest("No likes field");
        }

        Long uId = json.findPath("uId").asLong();
        User u = Ebean.find(User.class, uId);
        if(u == null){
            return badRequest("user Id does not exist");
        }
        Long qId = json.findPath("qId").asLong();
        Question q = Ebean.find(Question.class, qId);
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
        return ok(AnswerUtil.getAnswerJson(a));
    }

    public static Result getAnswer(Long id){
        Answer a = Ebean.find(Answer.class, id);
        if(a == null){
            return badRequest("Id does not exist");
        }
        else{
            return ok(AnswerUtil.getAnswerJson(a));
        }
    }

    public static Result getAnswers(){
        List<Answer> answers = Ebean.find(Answer.class).findList();
        JSONArray ja = new JSONArray();
        for (Answer a : answers){
            ja.put(AnswerUtil.getAnswerJson(a));
        }
        return ok(ja.toString());
    }

    public static Result updateAnswer(Long id){
        Answer a = Ebean.find(Answer.class, id);
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
        return ok(AnswerUtil.getAnswerJson(a));
    }

    public static Result deleteAnswer(Long id){
        Answer a = Ebean.find(Answer.class, id);
        if(a == null){
            return badRequest("Id does not exist");
        }
        Ebean.delete(a);
        return ok("{'result : 'delete successfully''}");
    }
}
