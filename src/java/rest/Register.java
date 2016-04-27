/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;

/**
 * REST Web Service
 *
 * @author David
 */
@Path("newuser")
public class Register {

    @Context
    private UriInfo context;
    private Gson gson;

    /**
     * Creates a new instance of Register
     */
//    public Register() {
//        gson = new GsonBuilder().setPrettyPrinting().create();
//    }
//
//    @POST
//    @Consumes("application/json")
//    public void newUser(String userJson) {
//        JsonObject newUser = new JsonParser().parse(userJson).getAsJsonObject();
//        entity.User user = new entity.User();
//        user.setUserName(newUser.get("username").getAsString());
//        user.setPassword(newUser.get("password").getAsString());
//        UserFacade.registerNewUser(user);
//    }

//    @GET
//    @Path("/{id}")
//    @Produces("application/json") //"application/json"
//    public String getBook(@PathParam("id") String userName) {
//        entity.User user = UserFacade.getUser(userName);
//        return gson.toJson(user);
//    }
}