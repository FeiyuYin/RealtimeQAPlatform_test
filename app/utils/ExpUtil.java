package utils;

import com.avaje.ebean.Ebean;
import models.User;

/**
 * Created by yin on 15-9-19.
 */
public class ExpUtil {

    public static int BESTANSWEREXP = 100;
    public static int ANSWEREXP = BESTANSWEREXP / 5;
    public static int QUESTIONEXP = ANSWEREXP / 2;


    public static void changeExp(User u, int exp, boolean isIncrease){
        u.setExp(u.getExp() + (isIncrease ? 1 : (-1)) * exp);
        Ebean.save(u);
    }
}
