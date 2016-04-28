/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entity.FlightInstance;
import facades.JsonForDummies;
import facades.SafeLuftFacade;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;

/**
 * REST Web Service
 *
 * @author David
 */
@Path("api")
public class SafeLuftAirlines {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ApiResource
     */
    public SafeLuftAirlines() {
    }

    
    @GET
    @Produces("application/json")
    @Path("flights/{from}/{date}/{tickets}")
    public String getAnyFlight(@PathParam("from") String from, @PathParam("date") String date, @PathParam("tickets") int tickets) {

        List<FlightInstance> list = SafeLuftFacade.getAnyFlight(from, date, tickets);
        return JsonForDummies.getJSON(JsonForDummies.Response1(list, tickets));
    }

    
    @GET
    @Produces("application/json")
    @Path("flights/{from}/{to}/{date}/{tickets}")
    public String getCurrency(@PathParam("from") String from, @PathParam("to") String to, @PathParam("date") String date, @PathParam("tickets") int tickets) {

        List<FlightInstance> list = SafeLuftFacade.getSpecificFlight(from,to, date, tickets);
        return JsonForDummies.getJSON(JsonForDummies.Response1(list, tickets));
    }
    
    @POST
    @Produces("application/json")
    @Consumes("application/json")
    @Path("reservation/{flightId}")
    public String makeReservation(String reservation, @PathParam("flightId") String flightId){
        
        JsonObject reserv = new JsonParser().parse(reservation).getAsJsonObject();
        String reserveeName = reserv.get("reserveeName").getAsString();
        int seats = reserv.get("numberOfSeats").getAsInt();
        JsonArray passengers = reserv.get("passengers").getAsJsonArray();
        
        return "";
    }

    /**
     * PUT method for updating or creating an instance of ApiResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    }
}
