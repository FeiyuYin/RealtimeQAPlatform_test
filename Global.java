import java.lang.Long;
import java.lang.System;
import java.net.URL;

import com.avaje.ebean.Ebean;
import play.*;
import play.libs.F.Promise;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;
import scala.concurrent.duration.FiniteDuration;
import java.util.concurrent.TimeUnit;
import play.libs.Akka;
import models.User;
import java.util.List;


public class Global extends GlobalSettings {

  // For CORS
  private class ActionWrapper extends Action.Simple {
    public ActionWrapper(Action<?> action) {
      this.delegate = action;
    }

    @Override
    public Promise<Result> call(Http.Context ctx) throws java.lang.Throwable {
      Promise<Result> result = this.delegate.call(ctx);
      Http.Response response = ctx.response();
      response.setHeader("Access-Control-Allow-Origin", "*");
      return result;
    }
  }

  @Override
  public Action<?> onRequest(Http.Request request,
      java.lang.reflect.Method actionMethod) {
    return new ActionWrapper(super.onRequest(request, actionMethod));
  }

  @Override
  public void onStart(Application app) {
    Runnable task = new Runnable() {
      @Override
      public void run() {
        List<User> users = Ebean.find(User.class).findList();
        Long curT = System.currentTimeMillis();
        for (User u : users){
          if(u.isOnLine() && curT - u.getLastActiveTime() > 60000){
            u.setOnLine(false);
            System.out.println("Set offline for user: " + u.getuId());
            Ebean.save(u);
          }
        }
      }
    };
    Akka.system().scheduler().schedule(FiniteDuration.create(0, TimeUnit.SECONDS), FiniteDuration.create(30, TimeUnit.SECONDS), task, Akka.system().dispatcher());
  }

}
