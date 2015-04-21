package controllers;

import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Category;
import models.Question;
import models.User;
import play.db.ebean.Model;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.ArrayList;
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

        if(json.findPath("uId") == null){
            return badRequest("No uId field");
        }

        Long uId = json.findPath("uId").longValue();
        User u = (User)new Model.Finder(String.class, User.class).byId(uId);
        if(u == null){
            return badRequest("user Id does not exist");
        }
        if(json.findPath("title") == null){
            return badRequest("No title field");
        }
        if(json.findPath("content") == null){
            return badRequest("No content field");
        }
        if(json.findValuesAsText("cIds") == null){
            return badRequest("No cIds field");
        }

        String qTitle = json.findPath("title").textValue();
        String qContent = json.findPath("content").textValue();

        List<String> cNames =  json.findValuesAsText("cIds");

        List<Category> cs = new ArrayList<Category>();
        for(int i = 0; i < cNames.size(); i ++){
            Category c = (Category) new Model.Finder(String.class, Category.class).byId(Long.parseLong(cNames.get(i)));
            if (c != null){
                cs.add(c);
            }
        }

        Question q = new Question();
        q.setContent(qContent);
        q.setTitle(qTitle);
        q.setU(u);
        q.setCs(cs);
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
        if(json == null){
            return badRequest("Expecting a Json object");
        }

        List<Category> cs = new ArrayList<Category>();

        if(json.findValuesAsText("cIds") != null){
            List<String> cNames = json.findValuesAsText("cIds");
            for(int i = 0; i < cNames.size(); i ++){
                Category c = (Category) new Model.Finder(String.class, Category.class).byId(Long.parseLong(cNames.get(i)));
                if (c != null){
                    cs.add(c);
                }
            }
        }

        if (json.findPath("title") != null){
           q.setTitle(json.findPath("title").textValue());
        }
        if (json.findPath("content") != null){
            q.setContent(json.findPath("content").textValue());
        }
        if(cs.size() > 0){
            q.setCs(cs);
        }
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

    public static Result setAnswer(Long id){
        Question q = (Question)new Model.Finder(String.class, Question.class).byId(id);
        if(q == null){
            return badRequest("Id does not exist");
        }
        JsonNode json = request().body().asJson();
        Long answerId = json.findPath("answerId").longValue();
        User u = (User)new Model.Finder(String.class, User.class).byId(answerId);
        if(u == null){
            return badRequest("user Id does not exist");
        }
        return ok();
    }
}
