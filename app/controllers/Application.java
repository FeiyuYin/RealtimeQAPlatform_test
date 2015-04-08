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


    public static Result postUser(){
        JsonNode json = request().body().asJson();
        if(json == null){
            return badRequest("Expecting a Json input");
        }
        String firstName = json.findPath("firstname").textValue();
        String lastName = json.findPath("lastname").textValue();
        String email = json.findPath("email").textValue();
        String password = json.findPath("password").textValue();
        User u = new User();
        u.setEmail(email);
        u.setFirstName(firstName);
        u.setLastName(lastName);
        u.setPassword(password);
        Ebean.save(u);
        return ok(toJson(u));
    }

    public static Result getUser(Long id){
        User u = (User)new Model.Finder(String.class, User.class).byId(id);
        if(u == null){
            return badRequest("Id does not exist");
        }
        return ok(toJson(u));
    }

    public static Result getUsers(){
        List<User> users = new Model.Finder(String.class, User.class).all();
        return ok(toJson(users));
    }

    public static Result postQuestion(){
        JsonNode json = request().body().asJson();
        ObjectNode result = Json.newObject();
        if(json == null){
            return badRequest("Expecting a Json object");
        }

            String qTitle = json.findPath("title").textValue();
            String qContent = json.findPath("content").textValue();
//            System.out.println(qTitle + qContent);
            Question q = new Question();
            q.setContent(qContent);
            q.setTitle(qTitle);
            Ebean.save(q);
//            List<Question> questions = new Model.Finder(String.class, Question.class).all();
            return ok(toJson(q));

    }

    public static Result getQuestion(Long id){
        Question q = (Question)new Model.Finder(String.class, Question.class).byId(id);
        if(q == null){
            return badRequest("Id does not exist");
        }
        else{
            return ok(toJson(q));
        }
    }

    public static Result getQuestions(){
        List<Question> questions = new Model.Finder(String.class, Question.class).all();
        return ok(toJson(questions));
    }

    public static Result postAnswer(){
        JsonNode json = request().body().asJson();
        if (json == null){
            return badRequest("Expecting a Json input");
        }
        String aContent = json.findPath("content").asText();
        boolean isBest = json.findPath("isbest").asBoolean();
        int views = json.findPath("views").asInt();
        int likes = json.findPath("likes").asInt();
        Answer a = new Answer();
        a.setContent(aContent);
        a.setIsBest(isBest);
        a.setLikes(likes);
        a.setViews(views);
        Ebean.save(a);
        return ok(toJson(a));
    }

    public static Result getAnswer(Long id){
        Answer a = (Answer)new Model.Finder(String.class, Answer.class).byId(id);
        if(a == null){
            return badRequest("Id does not exist");
        }
        else{
            return ok(toJson(a));
        }
    }

    public static Result getAnswers(){
        List<Answer> answers = new Model.Finder(String.class, Answer.class).all();
        return ok(toJson(answers));
    }
}


