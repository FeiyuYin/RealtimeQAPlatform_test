package utils;

import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Answer;
import play.libs.Json;

/**
 * Created by yin on 15-5-10.
 */
public class AnswerUtil {

    public static ObjectNode getAnswerJson(Answer a){
        ObjectNode result = Json.newObject();
        result.put("aId", a.getaId());
        result.put("content", a.getContent());
        result.put("isBest", a.isBest());
        result.put("likes", a.getLikes());
        result.put("views", a.getViews());
        result.put("hasImage", a.isHasImage());
        result.put("hasVoice", a.isHasVoice());
        result.put("q", a.getQ().getqId());
        result.put("u", a.getU().getuId());
        result.put("uEmail", a.getU().getEmail());
        result.put("createDate", a.getCreateDate());
        result.put("createTime", a.getCreateTime());
        return result;
    }
}
