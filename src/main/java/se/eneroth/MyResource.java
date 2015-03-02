package se.eneroth;

import javax.security.auth.login.LoginException;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

@Path("myresource")
public class MyResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Hello Jersey!";
    }

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public String login(@Context HttpHeaders httpHeaders,
                          @QueryParam("username") String username,
                          @QueryParam("password") String password) {
        String str = null;
        String apiKey = httpHeaders.getHeaderString("api-key");
        RestAuthenticator d = RestAuthenticator.getInstance();
        try {
            str = d.login(apiKey, username, password);
        } catch (LoginException e) {
            return e.toString();
        }
        return str;
    }

    @GET
    @Path("/loggedin")
    @Produces(MediaType.TEXT_PLAIN)
    public String getItLoggedIn(@Context HttpHeaders httpHeaders,
                                @QueryParam("token") String token) {
        RestAuthenticator d = RestAuthenticator.getInstance();
        String apiKey = httpHeaders.getHeaderString("api-key");
        if (d.isAuthTokenValid(apiKey, token)) {
            return "Hello Jersey!  You are logged in correct!";
        } else {
            return "Not logged in correct.....";
        }
    }
}
