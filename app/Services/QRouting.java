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
    public static int UPLIMIT = 4;
    public static void questionRouting(Question q){
        ArrayList<User> cand = getCandidates(q);
        rout(q, cand, UPLIMIT);
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

    private static void rout(Question q, ArrayList<User> cand, int num){
        if (q == null || cand == null || cand.size() == 0){
            return;
        }
        Collections.sort(cand, new Comparator<User>() {
            @Override
            public int compare(User u1, User u2) {
                return Integer.compare(u2.getExp(), u1.getExp());
            }
        });

        for (int i = 0; i < cand.size() && i < num; i ++){
            NotificationUtil.generateNewQuestionN(q, cand.get(i));
        }
    }
}
