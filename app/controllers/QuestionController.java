package controllers;

import Services.QRouting;
import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import models.Answer;
import models.Category;
import models.Question;
import models.User;
import org.json.JSONArray;
import org.json.JSONException;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import utils.*;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

/**
 * Created by yin on 15-4-9.
 */
public class QuestionController extends Controller {

    public static Result createQuestion(){
        JsonNode json = request().body().asJson();
        if(json == null){
            return badRequest("Expecting a Json object");
        }

        if(json.findValue("uId") == null){
            return badRequest("No uId field");
        }

        Long uId = json.findPath("uId").longValue();
        User u = Ebean.find(User.class, uId);
        if(u == null){
            return badRequest("user Id does not exist");
        }
        if(json.findValue("title") == null){
            return badRequest("No title field");
        }
        if(json.findValue("content") == null){
            return badRequest("No content field");
        }
        if(json.findValue("cIds") == null){
            return badRequest("No cIds field");
        }
        if (json.findValue("credit") == null){
            return badRequest("No credit field");
        }
        if(json.findValue("imageUrls") == null){
            return badRequest("No imageUrl field");
        }
        if(json.findValue("UUID") == null){
            return badRequest("No UUID field");
        }
        String qTitle = json.findPath("title").textValue();
        String qContent = json.findPath("content").textValue();
        String UUID = json.findPath("UUID").textValue();
        int qCredit = json.findPath("credit").intValue();
        if (qCredit > u.getCredit()){
            return badRequest("Not enough credit");
        }

        Question q = new Question();
        Set<Category> cs = new HashSet<>();
        JSONArray ja = new JSONArray(json.findPath("cIds").toString());
        for(int i = 0; i < ja.length(); i ++){
            Long cid = Long.parseLong(ja.get(i).toString());
            Category c = Ebean.find(Category.class, cid);
            if(c != null){
               c.getQuestions().add(q);
               cs.add(c);
            }
        }

        ja = new JSONArray(json.findPath("imageUrls").toString());
        String urlsString = "";
        for(int i = 0; i < ja.length(); i ++){
            urlsString += ("###" + ja.get(i).toString());
        }

        q.setCreateTime(TimeUtil.getCurrentTime());
        q.setCreateDate(TimeUtil.getCurrentDate());
        q.setIsOpen(true);
        q.setContent(qContent);
        q.setTitle(qTitle);
        q.setCredit(qCredit);
        q.setU(u);
        q.setCs(cs);
        q.setImageUrlsString(urlsString);
        q.setUUID(UUID);
        Ebean.save(q);
        CreditUtil.changeCredit(u, qCredit, false);
        ExpUtil.changeExp(u, ExpUtil.QUESTIONEXP, true);
        QRouting.questionRouting(q);
        return ok(QuestionUtil.getJson(q));

    }

    public static Result getQuestion(Long id){
        Question q = Ebean.find(Question.class, id);
        if(q == null){
            return badRequest("Id does not exist");
        }
        else{
            return ok(QuestionUtil.getJson(q));
        }
    }

    public static Result getQuestions(){
        List<Question> questions = Ebean.find(Question.class).findList();
        ArrayList<ObjectNode> nodeArray = new ArrayList<>();
        for (Question q : questions){
            nodeArray.add(QuestionUtil.getJson(q));
        }

        ObjectNode result = Json.newObject();
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode array = mapper.valueToTree(nodeArray);
        result.putArray("results").addAll(array);
        return ok(result);
    }

    public static Result getUserQuestions(Long userId){
        User u = Ebean.find(User.class, userId);
        if(u == null){
            return badRequest("Id does not exist");
        }
        List<Question> questions = Ebean.find(Question.class).findList();
        ArrayList<ObjectNode> nodeArray = new ArrayList<>();
        for (Question q : questions){
            if (q.getU().getuId() == userId){
                nodeArray.add(QuestionUtil.getJson(q));
            }
        }
        ObjectNode result = Json.newObject();
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode array = mapper.valueToTree(nodeArray);
        result.putArray("results").addAll(array);
        return ok(result);
    }

    public static Result getAnswerQuestions(Long userId){
        User u = Ebean.find(User.class, userId);
        if(u == null){
            return badRequest("Id does not exist");
        }
        List<Answer> answers = Ebean.find(Answer.class).findList();
        HashSet<ObjectNode> nodeSet = new HashSet();
        for (Answer a : answers){
            if (a.getU().getuId() == userId){
                nodeSet.add(QuestionUtil.getJson(a.getQ()));
            }
        }

        ObjectNode result = Json.newObject();
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode array = mapper.valueToTree(nodeSet);
        result.putArray("results").addAll(array);
        return ok(result);
    }

    public static Result updateQuestion(Long id){
        Question q = Ebean.find(Question.class, id);
        if(q == null){
            return badRequest("Id does not exist");
        }

        JsonNode json = request().body().asJson();
        if(json == null){
            return badRequest("Expecting a Json object");
        }

        if (json.findPath("answerId") != null){
            return setBestAnswer(id);
        }

        Set<Category> cs = new HashSet<Category>();

        if(json.findPath("cIds") != null){
            JSONArray ja = new JSONArray(json.findPath("cIds").toString());
            for(int i = 0; i < ja.length(); i ++){
                try {
                    Long cid = Long.parseLong(ja.get(i).toString());
                    Category c = Ebean.find(Category.class, cid);
                    if (c != null) {
                        cs.add(c);
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
            q.setCs(cs);
        }

        if (json.findPath("title").textValue() != null){
           q.setTitle(json.findPath("title").textValue());
        }
        if (json.findPath("content").textValue() != null){
            q.setContent(json.findPath("content").textValue());
        }
        if (json.findPath("credit").textValue() != null){
            q.setCredit(json.findPath("credit").intValue());
        }
        Ebean.save(q);
        return ok(QuestionUtil.getJson(q));
    }

    public static Result deleteQuestion(Long id){
        Question q = Ebean.find(Question.class, id);
        if(q == null){
            return badRequest("Id does not exist");
        }
        for(Category c : q.getCs()){
            System.out.println(c.getQuestions().size());
        }
        Ebean.delete(q);
        return ok("{'result' : 'delete successfully'}");
    }

    public static Result setBestAnswer(Long id){
        Question q = Ebean.find(Question.class, id);
        if(q == null){
            return badRequest("Id does not exist");
        }
        JsonNode json = request().body().asJson();
        if(json == null){
            return badRequest("Expecting an Json object");
        }
        if(json.findPath("answerId") == null){
            return badRequest("No answerId field");
        }
        Long answerId = json.findPath("answerId").longValue();
        Answer answer = Ebean.find(Answer.class, answerId);
        if(answer == null){
            return badRequest("Answer Id does not exist");
        }
        answer.setIsBest(true);
        Ebean.save(answer);
        q.setBestAnswer(answer);
        q.setIsOpen(false);
        q.setCloseTime(TimeUtil.getCurrentTime());
        q.setCloseDate(TimeUtil.getCurrentDate());
        Ebean.save(q);
        User u = answer.getU();
        CreditUtil.changeCredit(u, q.getCredit(), true);
        ExpUtil.changeExp(u, ExpUtil.BESTANSWEREXP, true);
        Ebean.save(u);
        NotificationUtil.generateBestAnswerN(q, answer.getU());
        return ok(QuestionUtil.getJson(q));
    }
}
