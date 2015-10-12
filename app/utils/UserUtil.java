package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Category;
import models.User;
import play.libs.Json;
import java.util.ArrayList;

/**
 * Created by yin on 15-5-10.
 */
public class UserUtil {

    public static ObjectNode getUserJson(User u){
        ObjectNode result = Json.newObject();
        result.put("uId", u.getuId());
        result.put("password", u.getPassword());
        result.put("firstName", u.getFirstName());
        result.put("lastName", u.getLastName());
        result.put("email", u.getEmail());
        result.put("credit", u.getCredit());
        result.put("exp", u.getExp());

        ArrayList<Long> cIdArray = new ArrayList<>();
        for (Category c : u.getExpertises()){
            cIdArray.add(c.getcId());
        }

        ObjectMapper mapper = new ObjectMapper();
        ArrayNode array = mapper.valueToTree(cIdArray);
        result.putArray("expertises").addAll(array);

        return result;
    }
}
