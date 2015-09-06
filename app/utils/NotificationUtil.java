package utils;

import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Notification;
import play.libs.Json;

/**
 * Created by yin on 15-8-26.
 */
public class NotificationUtil {
    public enum NotificationType{
        NEWQUESTION, NEWANSWER
    }
    public static String getJson(Notification n){
        ObjectNode result = Json.newObject();
        result.put("nId", n.getnId());
        result.put("uId", n.getU().getuId());
        result.put("qId", n.getQ().getqId());
        result.put("createDate", n.getCreateDate());
        result.put("createTime", n.getCreateTime());
        result.put("status", n.getStatus());
        result.put("type", n.getType());

        return result.toString();
    }
}
