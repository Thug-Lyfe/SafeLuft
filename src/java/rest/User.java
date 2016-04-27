package rest;

import com.google.gson.Gson;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Path;

@Path("demouser")
@RolesAllowed("User")
public class User {
private Gson gson;
//    public User() {
//        gson = new GsonBuilder().setPrettyPrinting().create();
//    }
//
//    
//    
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public String getSomething() {
//        return "{\"message\" : \"This message was delivered via a REST call accesible by only authenticated USERS\"}";
//    }
//
//    @GET
//    @Path("/firstRun")
//    public void firstRun() {
//        RateFacade.getRates();
//    }
//
//    @GET
//    @Produces("application/json")
//    @Path("/getRates")
//    public String getJson() {
//        return gson.toJson(RateFacade.RatesToday());
//    }
//    
//    @GET
//    @Produces("application/json")
//    @Path("/calculator/{amount}/{fromcurrency}/{tocurrency}")
//    public String getCurrency(@PathParam("amount") double amount, @PathParam("fromcurrency") String fromCur, @PathParam("tocurrency") String toCur){
//        JsonObject jo = new JsonObject();
//        double temp = ((amount*CalculatorFacade.getCurrency(fromCur))/CalculatorFacade.getCurrency(toCur))*100.00;
//        jo.addProperty("amount", (Math.round(temp))/100.00);
//        
//        return gson.toJson(jo);
//    }
}
