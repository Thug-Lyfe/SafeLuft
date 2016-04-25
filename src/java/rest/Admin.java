package rest;

import facades.JsonConverter;
import facades.UserFacade;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("admin")
@RolesAllowed("Admin")
public class Admin {
  
  @GET
  @Path("/users")
  @Produces(MediaType.APPLICATION_JSON)
  public String getUsers(){
    
   return JsonConverter.getJSON(JsonConverter.getAllUsers(UserFacade.getAllUser()));
  }
  
  @DELETE
  @Path("/user/{id}")
  @Produces("application/json")
  public String deletePerson(@PathParam("id") String id) {
      entity.User u = UserFacade.deleteUser(id);
      return JsonConverter.getJSON(JsonConverter.getAllUsers(u));
  }
}
// String now = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss").format(new Date());
//    return "{\"message\" : \"This message was delivered via a REST call accesible by only authenticated ADMINS\",\n"
//            +"\"serverTime\": \""+now +"\"}"; 