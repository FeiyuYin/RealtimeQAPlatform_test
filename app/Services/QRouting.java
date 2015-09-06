package Services;

import com.avaje.ebean.Ebean;
import models.Category;
import models.Notification;
import models.Question;
import models.User;
import utils.NotificationUtil;
import utils.TimeUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by yin on 15-8-25.
 */
public class QRouting {
    public static void questionRouting(Question q){
        ArrayList<User> cand = getCandidates(q);
        rout(q, cand);
    }

    private static ArrayList<User> getCandidates(Question q){
        Set<Category> cats = (HashSet<Category>)q.getCs();
        ArrayList<User> cand = new ArrayList<User>();
        for (User u : Ebean.find(User.class).findList()){
            for (Category c : cats){
                if (u.getExpertises().contains(c)){
                    cand.add(u);
                }
            }
        }
        return cand;
    }

    private static void rout(Question q, ArrayList<User> cand){
        if (q == null || cand == null || cand.size() == 0){
            return;
        }
        for (User u : cand){
            NotificationUtil.generateNewQuestionN(q, u);
        }
    }
}
