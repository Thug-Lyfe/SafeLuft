/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import entity.FlightInstance;
import facades.JsonForDummies;
import facades.SafeLuftFacade;
import httpErrors.FlightException;
import java.util.List;
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
 * @author David
 */
@Path("flights")
public class Flights {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of FlightsResource
     */
    public Flights() {
    }

    @GET
    @Produces("application/json")
    @Path("{from}/{date}/{tickets}")
    public String getAnyFlight(@PathParam("from") String from, @PathParam("date") String date, @PathParam("tickets") int tickets) throws FlightException {
        if (from == null || date == null || tickets <= 0) {
            throw new FlightException(3, 400);
        }
        List<FlightInstance> list = SafeLuftFacade.getAnyFlight(from, date, tickets);
        return JsonForDummies.getJSON(JsonForDummies.Response1(list, tickets));
    }

    @GET
    @Produces("application/json")
    @Path("{from}/{to}/{date}/{tickets}")
    public String getSpecificFlight(@PathParam("from") String from, @PathParam("to") String to, @PathParam("date") String date, @PathParam("tickets") int tickets) throws FlightException {
        if (from == null || date == null || tickets <= 0 || to == null) {
            throw new FlightException(3, 400);
        }
        List<FlightInstance> list = SafeLuftFacade.getSpecificFlight(from, to, date, tickets);
        return JsonForDummies.getJSON(JsonForDummies.Response1(list, tickets));
    }
}
