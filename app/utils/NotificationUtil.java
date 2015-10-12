package utils;

import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Notification;
import models.Question;
import models.User;
import play.libs.Json;

/**
 * Created by yin on 15-8-26.
 */
public class NotificationUtil {
    public enum NotificationType{
        NEWQUESTION, NEWANSWER, BESTANSWER
    }

    public static ObjectNode getJson(Notification n){
        ObjectNode result = Json.newObject();
        result.put("nId", n.getnId());
        result.put("uId", n.getU().getuId());
        result.put("qId", n.getQ().getqId());
        result.put("createDate", n.getCreateDate());
        result.put("createTime", n.getCreateTime());
        result.put("status", n.getStatus());
        result.put("type", n.getType());

        return result;
    }

    public static void generateNewQuestionN(Question q, User u){
        Notification n = new Notification();
        n.setType(NotificationType.NEWQUESTION);
        n.setQ(q);
        n.setU(u);
        n.setStatus("New");
        n.setCreateDate(TimeUtil.getCurrentDate());
        n.setCreateTime(TimeUtil.getCurrentTime());
        Ebean.save(n);
    }

    public static void generateNewAnswerN(Question q, User u){
        Notification n = new Notification();
        n.setType(NotificationType.NEWANSWER);
        n.setQ(q);
        n.setU(u);
        n.setStatus("New");
        n.setCreateDate(TimeUtil.getCurrentDate());
        n.setCreateTime(TimeUtil.getCurrentTime());
        Ebean.save(n);
    }

    public static void generateBestAnswerN(Question q, User u){
        Notification n = new Notification();
        n.setType(NotificationType.BESTANSWER);
        n.setQ(q);
        n.setU(u);
        n.setStatus("New");
        n.setCreateDate(TimeUtil.getCurrentDate());
        n.setCreateTime(TimeUtil.getCurrentTime());
        Ebean.save(n);
    }
}
