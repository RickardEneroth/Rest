package se.eneroth;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("json")
public class MyResourceJson {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getIt() {
        return "JSON: Hello Jersey!";
    }
}