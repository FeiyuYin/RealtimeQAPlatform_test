package utils;

import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Category;
import models.Question;
import org.json.JSONArray;
import play.libs.Json;

/**
 * Created by yin on 15-5-10.
 */
public class QuestionUtil {

    public static String getJson(Question q){
        ObjectNode result = Json.newObject();
        result.put("qId", q.getqId());
        result.put("uId", q.getU().getuId());
        result.put("title", q.getTitle());
        result.put("content", q.getContent());
        result.put("aerId", q.getAnswerer() == null ? null :q.getAnswerer().getuId());
        result.put("createTime", q.getCreateTime());
        result.put("createDate", q.getCreateDate());

        JSONArray ja = new JSONArray();
        for(Category c : q.getCs()){
            ja.put(c.getcId());
        }
        result.put("cIds", ja.toString());
        return result.toString();
    }
}
