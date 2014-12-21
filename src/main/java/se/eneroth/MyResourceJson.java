package se.eneroth;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Path("json")
public class MyResourceJson {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getIt(@QueryParam("name") String name) {
        Gson gson = new GsonBuilder().create();
        JsonGreeeting greeting = new JsonGreeeting();
        greeting.setGreeting("Hello Json world!!");
        greeting.setVersion("1.0");
        greeting.setName(name);
        String jsonString = gson.toJson(greeting);

        return jsonString;
    }
}