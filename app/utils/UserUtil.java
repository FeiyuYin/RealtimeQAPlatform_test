package utils;

import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Category;
import models.User;
import org.json.JSONArray;
import org.omg.PortableServer.POAPackage.ObjectNotActive;
import play.libs.Json;
import sun.org.mozilla.javascript.json.JsonParser;

/**
 * Created by yin on 15-5-10.
 */
public class UserUtil {

    public static String getUserJson(User u){
        ObjectNode result = Json.newObject();
        result.put("uId", u.getuId());
        result.put("password", u.getPassword());
        result.put("firstName", u.getFirstName());
        result.put("lastName", u.getLastName());
        result.put("email", u.getEmail());
        result.put("credit", u.getCredit());
        result.put("exp", u.getExp());

        JSONArray ja = new JSONArray();
        for (Category c : u.getExpertises()){
            ja.put(c.getcId());
        }
        result.put("expertises", ja.toString());
        return result.toString();
    }
}
