/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import facades.ServiceFacade;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;

/**
 * REST Web Service
 *
 * @author Warco
 */
@Path("Service")
public class Service {

    @Context
    private UriInfo context;
    private Gson gson;
    /**
     * Creates a new instance of ServiceResource
     */
    public Service() {
        gson = new GsonBuilder().setPrettyPrinting().create();
        
    }

    /**
     * Retrieves representation of an instance of rest.ServiceResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    @Path("/{from}/{to}/{date}/{tickets}")
    public String getFlightsFrom(@PathParam("from") String from, @PathParam("to") String to, @PathParam("date") String date, @PathParam("tickets") String tickets){
        return gson.toJson(ServiceFacade.getFlights(from, to, date, tickets));
    }
    
    @GET
    @Produces("application/json")
    @Path("/{from}/{date}/{tickets}")
    public String getFlights(@PathParam("from") String from, @PathParam("date") String date, @PathParam("tickets") String tickets){
        
        return gson.toJson(ServiceFacade.getFlights(from, "", date, tickets));
    }

    /**
     * PUT method for updating or creating an instance of ServiceResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    }
}
