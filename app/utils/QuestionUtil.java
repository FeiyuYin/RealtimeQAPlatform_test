package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Category;
import models.Question;
import org.json.JSONArray;
import play.libs.Json;

import java.util.ArrayList;

/**
 * Created by yin on 15-5-10.
 */
public class QuestionUtil {

    public static ObjectNode getJson(Question q){
        ObjectNode result = Json.newObject();
        result.put("qId", q.getqId());
        result.put("uId", q.getU().getuId());
        result.put("title", q.getTitle());
        result.put("credit", q.getCredit());
        result.put("content", q.getContent());
        result.put("bestAnswer", q.getBestAnswer() == null ? null :q.getBestAnswer().getaId());
        result.put("isOpen", q.isOpen());
        result.put("createTime", q.getCreateTime());
        result.put("createDate", q.getCreateDate());
        result.put("closeTime", q.getCloseTime());
        result.put("closeDate", q.getCloseDate());

        ObjectMapper mapper = new ObjectMapper();

        ArrayList<Long> cIdArray = new ArrayList<>();
        for(Category c : q.getCs()){
            cIdArray.add(c.getcId());
        }

        ArrayNode array = mapper.valueToTree(cIdArray);
        result.putArray("cIds").addAll(array);

        return result;
    }
}
