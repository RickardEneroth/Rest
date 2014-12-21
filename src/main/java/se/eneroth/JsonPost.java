package se.eneroth;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Path("jsonpost")
public class JsonPost {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getIt(JsonGreeeting greeting) {
        JsonGreeeting reply = new JsonGreeeting();
        reply.setName(greeting.getName());
        reply.setGreeting("And this is the reply....");
        Gson gson = new GsonBuilder().create();
        String jsonString = gson.toJson(reply);

        return jsonString;
    }

    @Path("add")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getIt(Adder adder) {
        Adder reply = new Adder();
        reply.setTal1(adder.getTal1());
        reply.setTal2(adder.getTal2());
        reply.setSum(adder.getTal1() + adder.getTal2());
        Gson gson = new GsonBuilder().create();
        String jsonString = gson.toJson(reply);

        return jsonString;
    }
}