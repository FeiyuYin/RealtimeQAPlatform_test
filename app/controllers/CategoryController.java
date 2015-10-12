package controllers;

import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Category;
import play.libs.Json;
import play.mvc.Result;
import play.mvc.Controller;
import utils.CatUtil;
import utils.TimeUtil;

import java.util.ArrayList;
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
        ArrayList<ObjectNode> nodeArray = new ArrayList<>();
        for (Category c : cs){
            nodeArray.add(CatUtil.getCatJson(c));
        }

        ObjectNode result = Json.newObject();
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode array = mapper.valueToTree(nodeArray);
        result.putArray("results").addAll(array);
        return ok(result);
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
