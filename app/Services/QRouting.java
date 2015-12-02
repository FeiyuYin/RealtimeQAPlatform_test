package Services;

import com.avaje.ebean.Ebean;
import models.Category;
import models.Question;
import models.User;
import utils.NotificationUtil;

import java.util.*;

/**
 * Created by yin on 15-8-25.
 */
public class QRouting {
    public static int UPLIMIT = 50;
    public static ArrayList<User> questionRouting(Question q, User u){
        ArrayList<User> cand = getCandidates(q, u);
        return rout(q, cand, UPLIMIT);
    }

    private static ArrayList<User> getCandidates(Question q, User poster){
        Set<Category> cats = (HashSet<Category>)q.getCs();
        ArrayList<User> cand = new ArrayList<User>();
        for (User u : Ebean.find(User.class).findList()){
            if (u.getuId() != poster.getuId()){
                for (Category c : cats){
                    if (u.getExpertises().contains(c)){
                        cand.add(u);
                    }
                }
            }
        }
        return cand;
    }

    private static ArrayList<User> rout(Question q, ArrayList<User> cand, int num){
        ArrayList<User> targets = new ArrayList<User>();
        if (q == null || cand == null || cand.size() == 0){
            return targets;
        }
        Collections.sort(cand, new Comparator<User>() {
            @Override
            public int compare(User u1, User u2) {
                return Integer.compare(u2.getExp(), u1.getExp());
            }
        });

        for (int i = 0; i < cand.size() && i < num; i ++){
            NotificationUtil.generateNewQuestionN(q, cand.get(i));
            targets.add(cand.get(i));
        }
        return targets;
    }
}
