package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import facades.ServiceFacade;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("admin")
//@RolesAllowed("Admin")
public class Admin {

    private Gson gson;

    public Admin() {

        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @GET
    @Path("/Services")
    @Produces(MediaType.APPLICATION_JSON)
    public String getServices() {
        return gson.toJson(ServiceFacade.getServices());
    }

    @DELETE
    @Path("/Service/{id}")
    @Produces("application/json")
    public String deleteService(@PathParam("id") int id) {
        ServiceFacade.deleteService(id);
        return gson.toJson(ServiceFacade.getServices());
    }
    
    @POST
    @Path("/Service")
    @Consumes("application/json")   
    public String newService(String newService) {
        JsonObject newServ = new JsonParser().parse(newService).getAsJsonObject();
        entity.Service serv = new entity.Service();
        serv.setName(newServ.get("name").getAsString());
        serv.setWebsite(newServ.get("website").getAsString());
        ServiceFacade.addService(serv);
        return gson.toJson(ServiceFacade.getServices());
    }

}
// String now = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss").format(new Date());
//    return "{\"message\" : \"This message was delivered via a REST call accesible by only authenticated ADMINS\",\n"
//            +"\"serverTime\": \""+now +"\"}"; 
