package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Category;
import models.Question;
import play.libs.Json;

import java.util.ArrayList;

/**
 * Created by yin on 15-5-10.
 */
public class CatUtil {

    public static ObjectNode getCatJson(Category c){
        ObjectNode result = Json.newObject();
        result.put("cId", c.getcId());
        result.put("cName", c.getName());
        result.put("fNumber", c.getFollowerNumber());
        result.put("createDate", c.getCreateDate());
        result.put("createTime", c.getCreateTime());

        ArrayList<Long> qIdArray = new ArrayList<>();
        for(Question q : c.getQuestions()){
            qIdArray.add(q.getqId());
        }

        ObjectMapper mapper = new ObjectMapper();
        ArrayNode array = mapper.valueToTree(qIdArray);
        result.putArray("qIds").addAll(array);

        return result;
    }
}
