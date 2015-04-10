package controllers;

import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Question;
import models.User;
import play.db.ebean.Model;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

import static play.libs.Json.toJson;

/**
 * Created by yin on 15-4-9.
 */
public class QuestionController extends Controller {

    public static Result createQuestion(){
        JsonNode json = request().body().asJson();
        ObjectNode result = Json.newObject();
        if(json == null){
            return badRequest("Expecting a Json object");
        }

        Long uId = json.findPath("uId").longValue();
        User u = (User)new Model.Finder(String.class, User.class).byId(uId);
        if(u == null){
            return badRequest("user Id does not exist");
        }

        String qTitle = json.findPath("title").textValue();
        String qContent = json.findPath("content").textValue();

        Question q = new Question();
        q.setContent(qContent);
        q.setTitle(qTitle);
        q.setU(u);
        Ebean.save(q);
        return ok(toJson(q));

    }

    public static Result getQuestion(Long id){
        Question q = (Question)new Model.Finder(String.class, Question.class).byId(id);
        if(q == null){
            return badRequest("Id does not exist");
        }
        else{
            return ok(toJson(q));
        }
    }

    public static Result getQuestions(){
        List<Question> questions = new Model.Finder(String.class, Question.class).all();
        return ok(toJson(questions));
    }

    public static Result updateQuestion(Long id){
        Question q = (Question)new Model.Finder(String.class, Question.class).byId(id);
        if(q == null){
            return badRequest("Id does not exist");
        }
        JsonNode json = request().body().asJson();
        ObjectNode result = Json.newObject();
        if(json == null){
            return badRequest("Expecting a Json object");
        }
        String qTitle = json.findPath("title").textValue();
        String qContent = json.findPath("content").textValue();
        q.setContent(qContent);
        q.setTitle(qTitle);
        Ebean.save(q);
        return ok(toJson(q));
    }

    public static Result deleteQuestion(Long id){
        Question q = (Question)new Model.Finder(String.class, Question.class).byId(id);
        if(q == null){
            return badRequest("Id does not exist");
        }
        Ebean.delete(q);
        return ok();
    }
}
