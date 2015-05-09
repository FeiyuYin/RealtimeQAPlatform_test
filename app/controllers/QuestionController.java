package controllers;

import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Category;
import models.Question;
import models.User;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
        if(json == null){
            return badRequest("Expecting a Json object");
        }

        if(json.findPath("uId") == null){
            return badRequest("No uId field");
        }

        Long uId = json.findPath("uId").longValue();
        User u = Ebean.find(User.class, uId);
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

        List<Category> cs = new ArrayList<Category>();
        if(json.findPath("cIds") != null){
            JSONArray ja = new JSONArray(json.findPath("cIds").toString());
            for(int i = 0; i < ja.length(); i ++){
                Long cid = Long.parseLong(ja.get(i).toString());
                Category c = Ebean.find(Category.class, cid);
                if(c != null){
                    cs.add(c);
                }
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
        Question q = Ebean.find(Question.class, id);
        if(q == null){
            return badRequest("Id does not exist");
        }
        else{
            return ok(toJson(q));
        }
    }

    public static Result getQuestions(){
        List<Question> questions = Ebean.find(Question.class).findList();
        return ok(toJson(questions));
    }

    public static Result updateQuestion(Long id){
        setAnswerer(id);
        Question q = Ebean.find(Question.class, id);
        if(q == null){
            return badRequest("Id does not exist");
        }

        JsonNode json = request().body().asJson();
        if(json == null){
            return badRequest("Expecting a Json object");
        }

        List<Category> cs = new ArrayList<Category>();

        if(json.findPath("cIds") != null){
            JSONArray ja = new JSONArray(json.findPath("cIds").toString());
            for(int i = 0; i < ja.length(); i ++){
                try {
                    System.out.println("cId: " + ja.get(i).toString());
                    Long cid = Long.parseLong(ja.get(i).toString());
                    Category c = Ebean.find(Category.class, cid);
                    if (c != null) {
                        cs.add(c);
                    }
                }catch (JSONException e){
                    e.printStackTrace();
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
        Question q = Ebean.find(Question.class, id);
        if(q == null){
            return badRequest("Id does not exist");
        }
        for(Category c : q.getCs()){
            System.out.println("!!!!!!!!!!!!!!!!!!!!");
            c.getQuestions().remove(q);
            Ebean.save(c);
        }
        q.setCs(null);
        q.save();
        Ebean.delete(q);
        return ok();
    }

    public static Result setAnswerer(Long id){
        Question q = Ebean.find(Question.class, id);
        if(q == null){
            return badRequest("Id does not exist");
        }
        JsonNode json = request().body().asJson();
        if(json == null){
            return badRequest("Expecting an Json object");
        }
        if(json.findPath("aerId") == null){
            return badRequest("No answererId field");
        }
        Long answererId = json.findPath("aerId").longValue();
        User answerer = Ebean.find(User.class, answererId);
        if(answerer == null){
            return badRequest("user Id does not exist");
        }
        q.setAnswerer(answerer);
        Ebean.save(q);
        return ok();
    }
}
