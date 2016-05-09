/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import httpErrors.FlightException;

/**
 *
 * @author Warco
 */
public class FlightData {
    private JsonArray ja = new JsonArray();
    private boolean ticketResponse = false;
    public synchronized void addFlights(JsonObject newFlights){
        ja.add(newFlights);
    }
    
    public synchronized JsonElement getFlights() throws FlightException{
        if(ja.toString().length() < 10){
            throw new FlightException(1,404);
        }
        return ja;
    }

    public boolean isTicketResponse() {
        return ticketResponse;
    }

    public void setTicketResponse(boolean ticketResponse) {
        this.ticketResponse = ticketResponse;
    }

    
    
}
