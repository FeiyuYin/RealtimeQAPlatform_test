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

    public static Result getNotifications(){
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

    public static Result getUserNotifications(Long userId){
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

    public static Result updateNotification(Long nId){
        Notification n = Ebean.find(Notification.class, nId);
        if (n == null){
            return badRequest("Notification Id does not exist");
        }
        n.setStatus("Read");
        Ebean.save(n);
        return ok(NotificationUtil.getJson(n));
    }

    public static Result removeNotification(Long nId){
        Notification n = Ebean.find(Notification.class, nId);
        if (n == null){
            return badRequest("Notification Id does not exist");
        }
        Ebean.delete(n);
        return ok();
    }

    public static Result getUserUnreadNotificationsNum(Long uId){
        User u = Ebean.find(User.class, uId);
        if(u == null){
            return badRequest("Id does not exist");
        }
        int count = 0;
        List<Notification> ns = Ebean.find(Notification.class).findList();
        for (Notification n : ns){
            if (n.getU().getuId() == uId && n.getStatus().equals("New")){
                count ++;
            }
        }

        return ok(Json.newObject().put("count", count));
    }
}
