/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

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

    /**
     * Creates a new instance of ServiceResource
     */
    public Service() {
    }

    /**
     * Retrieves representation of an instance of rest.ServiceResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    @Path("/Service/{amount}/{fromcurrency}/{tocurrency}")
    public String getFlights(@PathParam("from") String from, @PathParam("to") String to, @PathParam("date") String date, @PathParam("tickets") String tickets){
        
        throw new UnsupportedOperationException();
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
