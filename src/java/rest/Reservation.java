/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entity.FlightInstance;
import facades.JsonForDummies;
import facades.SafeLuftFacade;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;

/**
 * REST Web Service
 *
 * @author David
 */
@Path("reservation")
public class Reservation {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of Reservation
     */
    public Reservation() {
    }

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    @Path("{flightId}")
    public String makeReservation(String reservation, @PathParam("flightId") String flightId){
        JsonObject reserv = new JsonParser().parse(reservation).getAsJsonObject();
        
        entity.Reservation r = JsonForDummies.makeReservation(reserv);
        
        FlightInstance fi = SafeLuftFacade.makeReservation(flightId, r);
        
        return JsonForDummies.getJSON(JsonForDummies.reservationResponse(fi, reserv));
    }
}
