package utils;

import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Category;
import models.Question;
import org.json.JSONArray;
import play.libs.Json;

import javax.management.ObjectName;

/**
 * Created by yin on 15-5-10.
 */
public class CatUtil {

    public static String getCatJson(Category c){
        ObjectNode result = Json.newObject();
        result.put("cId", c.getcId());
        result.put("cName", c.getName());
        result.put("fNumber", c.getFollowerNumber());
        result.put("createDate", c.getCreateDate());
        result.put("createTime", c.getCreateTime());

        JSONArray ja = new JSONArray();
        for(Question q : c.getQuestions()){
            ja.put(q.getqId());
        }
        result.put("questions", ja.toString());
        return result.toString();
    }
}
