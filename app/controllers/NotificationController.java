package controllers;

import com.avaje.ebean.Ebean;
import models.Notification;
import org.json.JSONArray;
import play.mvc.Controller;
import play.mvc.Result;
import utils.NotificationUtil;

import java.util.List;

/**
 * Created by yin on 15-5-9.
 */
public class NotificationController extends Controller {

    public static Result createNotificaton(){
        return ok();
    }

    public static Result getNotifications(){
        List<Notification> ns = Ebean.find(Notification.class).findList();
        JSONArray ja = new JSONArray();
        for (Notification n : ns){
            ja.put(NotificationUtil.getJson(n));
        }
        return ok(ja.toString());
    }
}
