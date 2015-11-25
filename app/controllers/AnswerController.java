package controllers;

import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Answer;
import models.Question;
import models.User;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import utils.AnswerUtil;
import utils.ExpUtil;
import utils.NotificationUtil;
import utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;

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
        if(json.findPath("aUUID") == null){
            return badRequest("No aUUID field");
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
        String aUUID = json.findPath("aUUID").asText();

        boolean hasImage = false;
        if (json.findValue("hasImage") != null){
            hasImage = json.findPath("hasImage").booleanValue();
        }

        boolean hasVoice = false;
        if (json.findValue("hasVoice") != null){
            hasVoice = json.findPath("hasVoice").booleanValue();
        }

        Answer a = new Answer();
        a.setContent(aContent);
        a.setIsBest(isBest);
        a.setLikes(likes);
        a.setViews(views);
        a.setQ(q);
        a.setU(u);
        a.setHasImage(hasImage);
        a.setHasVoice(hasVoice);
        a.setCreateDate(TimeUtil.getCurrentDate());
        a.setCreateTime(TimeUtil.getCurrentTime());
        a.setaUUID(aUUID);
        Ebean.save(a);
        ExpUtil.changeExp(u, ExpUtil.ANSWEREXP, true);
        NotificationUtil.generateNewAnswerN(q, q.getU());
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
        ArrayList<ObjectNode> nodeArray = new ArrayList<>();
        for (Answer a : answers){
            nodeArray.add(AnswerUtil.getAnswerJson(a));
        }

        ObjectNode result = Json.newObject();
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode array = mapper.valueToTree(nodeArray);
        result.putArray("results").addAll(array);
        return ok(result);
    }

    public static Result getQuestionAnswer(Long qId){
        List<Answer> answers = Ebean.find(Answer.class).findList();
        ArrayList<ObjectNode> nodeArray = new ArrayList<>();
        for (Answer a : answers){
            if (a.getQ().getqId() == qId){
                nodeArray.add(AnswerUtil.getAnswerJson(a));
            }
        }

        ObjectNode result = Json.newObject();
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode array = mapper.valueToTree(nodeArray);
        result.putArray("results").addAll(array);
        return ok(result);
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

        if (json.findPath("content").textValue() != null){
            a.setContent(json.findPath("content").textValue());
        }
        if (json.findPath("isBest").textValue() != null){
            a.setIsBest(json.findPath("isBest").booleanValue());
        }
        if (json.findPath("views").textValue() != null){
            a.setViews(json.findPath("views").intValue());
        }
        if (json.findPath("likes").textValue() != null){
            a.setLikes(json.findPath("likes").intValue());
        }
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
