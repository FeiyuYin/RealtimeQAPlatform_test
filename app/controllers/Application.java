package controllers;

import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Answer;
import models.Person;
import models.Question;
import models.User;
import play.*;
import play.api.mvc.*;
import play.libs.Json;
import play.mvc.Result;
import play.data.Form;
import play.db.ebean.Model;
import play.mvc.*;

import play.mvc.Controller;
import play.mvc.Results;
import views.html.*;

import java.util.Arrays;
import java.util.List;

import static play.libs.Json.toJson;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }

    public static Result addPerson(){
        Person person = Form.form(Person.class).bindFromRequest().get();
        person.save();
        return redirect(routes.Application.index());
    }

    public static Result getPersons(){
        List<Person> persons = new Model.Finder(String.class, Person.class).all();
        return ok(toJson(persons));
    }

    public static Result test(){
        JsonNode json = request().body().asJson();
        ObjectNode result = Json.newObject();
        if(json == null){
            return badRequest("Expecting a Json object");
        }
        else{
            String name = json.findPath("name").textValue();
            if(name == null){
                return badRequest("Expecting name keyword");
            }
            else{
                Arrays.asList();
                result.put("name", name);
                return ok(result);
            }
        }
    }







}


