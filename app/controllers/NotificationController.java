package controllers;

import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Notification;
import models.Question;
import models.User;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import utils.NotificationUtil;
import utils.QuestionUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yin on 15-5-9.
 */
public class NotificationController extends Controller {

    public static Result getNotifications(Long id){
        User u = Ebean.find(User.class, id);
        if (u == null){
            return badRequest("User Id does not exist");
        }
        List<Notification> ns = Ebean.find(Notification.class).findList();
        ArrayList<ObjectNode> nodeArray = new ArrayList<>();
        for (Notification n : ns){
            nodeArray.add(NotificationUtil.getJson(n));
        }

        ObjectNode result = Json.newObject();
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode array = mapper.valueToTree(nodeArray);
        result.putArray("results").addAll(array);
        return ok(result);
    }

    public static Result getNotification(Long id){
        Notification n = Ebean.find(Notification.class, id);
        if (n == null){
            return badRequest("Notification Id does not exist");
        }
        return ok(NotificationUtil.getJson(n));
    }

    public static Result getUserNotification(Long userId){
        User u = Ebean.find(User.class, userId);
        if(u == null){
            return badRequest("Id does not exist");
        }
        List<Notification> ns = Ebean.find(Notification.class).findList();
        ArrayList<ObjectNode> nodeArray = new ArrayList<>();
        for (Notification n : ns){
            if (n.getU().getuId() == userId){
                nodeArray.add(NotificationUtil.getJson(n));
            }
        }
        ObjectNode result = Json.newObject();
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode array = mapper.valueToTree(nodeArray);
        result.putArray("results").addAll(array);
        return ok(result);
    }
}
