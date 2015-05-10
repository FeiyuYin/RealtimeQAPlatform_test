package controllers;

import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.databind.JsonNode;
import models.Category;
import org.json.JSONArray;
import play.db.ebean.Model;
import play.mvc.Result;
import play.mvc.Controller;
import utils.CatUtil;
import utils.TimeUtil;

import java.lang.reflect.Array;
import java.sql.Time;
import java.util.List;

import static play.libs.Json.toJson;


/**
 * Created by yin on 15-4-18.
 */
public class CategoryController extends Controller {

    public static Result createCategory(){
        JsonNode json = request().body().asJson();
        if(json == null){
            return badRequest("Expecting a Json input");
        }

        if(json.findPath("cName") == null){
            return badRequest("No cName field");
        }
        if(json.findPath("fNumber") == null){
            return badRequest("No fNumber field");
        }
        String name = json.findPath("cName").textValue();
        int fNumber = json.findPath("fNumber").intValue();
        Category c = new Category();
        c.setCreateDate(TimeUtil.getCurrentDate());
        c.setCreateTime(TimeUtil.getCurrentTime());
        c.setName(name);
        c.setFollowerNumber(fNumber);
        Ebean.save(c);
        return ok(toJson(CatUtil.getCatJson(c)));
    }

    public static Result getCategory(Long id){
        Category c = Ebean.find(Category.class, id);
        if(c == null){
            return badRequest("Id does not exist");
        }
        return ok(toJson(CatUtil.getCatJson(c)));
    }

    public static Result getCategories(){
        List<Category> cs = Ebean.find(Category.class).findList();
        JSONArray ja = new JSONArray();
        for (Category c : cs){
            ja.put(CatUtil.getCatJson(c));
        }
        return ok(ja.toString());
    }

    public static Result updateCategory(Long id){
        Category c = Ebean.find(Category.class, id);
        if (c == null){
            return badRequest("Id does not exist");
        }
        JsonNode json = request().body().asJson();

        if(json.findPath("cName") != null){
            c.setName(json.findPath("cName").asText());
        }
        if(json.findPath("fNumber") != null){
            c.setFollowerNumber(json.findPath("fNumber").intValue());
        }
        Ebean.save(c);
        return ok(toJson(c));
    }

    public static Result deleteCategory(Long id){
        Category c = Ebean.find(Category.class, id);
        if(c == null){
            return badRequest("Id does not exist");
        }
        Ebean.delete(c);
        return ok();
    }
}
