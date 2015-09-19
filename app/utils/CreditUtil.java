package utils;

import com.avaje.ebean.Ebean;
import models.User;

/**
 * Created by yin on 15-9-19.
 */
public class CreditUtil {

    public static void changeCredit(User u, int credit, boolean isIncrease){
        u.setCredit(u.getCredit() + (isIncrease ? 1 : (-1)) * credit);
        Ebean.save(u);
    }
}
