package utils;

import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Answer;
import play.libs.Json;

/**
 * Created by yin on 15-5-10.
 */
public class AnswerUtil {

    public static String getAnswerJson(Answer a){
        ObjectNode result = Json.newObject();
        result.put("aId", a.getaId());
        result.put("content", a.getContent());
        result.put("isBest", a.isBest());
        result.put("likes", a.getLikes());
        result.put("views", a.getViews());
        result.put("q", a.getQ().getqId());
        result.put("u", a.getU().getuId());
        return result.toString();
    }
}
