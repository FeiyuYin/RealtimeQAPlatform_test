package controllers;

import com.avaje.ebean.Ebean;
import models.Notification;
import models.User;
import org.json.JSONArray;
import play.mvc.Controller;
import play.mvc.Result;
import utils.NotificationUtil;

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
        JSONArray ja = new JSONArray();
        for (Notification n : ns){
            if (n.getU() == u){
                ja.put(NotificationUtil.getJson(n));
            }
        }
        return ok(ja.toString());
    }

    public static Result getNotification(Long id){
        Notification n = Ebean.find(Notification.class, id);
        if (n == null){
            return badRequest("Notification Id does not exist");
        }
        return ok(NotificationUtil.getJson(n));
    }
}
