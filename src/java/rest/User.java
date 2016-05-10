package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import facades.ServiceFacade;
import facades.UserFacade;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("demouser")
//@RolesAllowed("User")
public class User {
private Gson gson;
    public User() {
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getSomething() {
        return "{\"message\" : \"This message was delivered via a REST call accesible by only authenticated USERS\"}";
    }

    @GET
    @Produces("application/json")
    @Path("/ticket/{userName}")
    public String getJson(@PathParam("userName") String userName) {
        return gson.toJson(UserFacade.getTickets(userName));
    }
    
    @POST
    @Path("/ticket")
    @Consumes("application/json")
    @Produces("application/json")
    public String newTicket(String newTicket) {
        JsonObject ticket = new JsonParser().parse(newTicket).getAsJsonObject();
        entity.UserReservation ur = new entity.UserReservation();
        String user = ticket.remove("user").getAsString();
        String airline = ticket.get("airline").getAsString();
        String flightID = ticket.get("flightID").getAsString();
        ur.setTicket(ticket.toString());
        try {
            UserFacade.ReserveTicketAirline(ticket.toString(),airline,flightID);
            UserFacade.RegisterTicket(ur, user);
        return gson.toJson(UserFacade.getTickets(user));
        } catch (Exception ex) {
            System.out.println(ex.getStackTrace());
                return null;
        }
    }
}
