package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import play.Logger;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

public class ApiController extends Controller {

    private static class ApiModel{
        public String firstName;
        public String lastName;

        public ApiModel(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public ApiModel() {
        }

        @Override
        public String toString() {
            return "ApiModel{" +
                    "firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    '}';
        }
    }

    // Example of returning JSON
    public Result getJson(){

        JsonNode json = Json.toJson(new ApiModel("Joe", "Test"));
        return ok(json);
    }

    // Example of accepting api request with JSON
    // Body Parser will ensure that input has content type of application/json or will return 400
    @BodyParser.Of(BodyParser.Json.class)
    public Result postJson(){

        JsonNode json = request().body().asJson();
        ApiModel model = Json.fromJson(json, ApiModel.class);
        Logger.info("Got " + model);
        return ok();

    }


}
