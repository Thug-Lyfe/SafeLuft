/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 *
 * @author Warco
 */
public class FlightData {
    private JsonArray ja = new JsonArray();
    
    public synchronized void addFlights(JsonObject newFlights){
        ja.add(newFlights);
    }
    
    public synchronized JsonObject getFlights(){
        JsonObject Flights = new JsonObject();
        Flights.add("fly", ja);
        return Flights;
    }
}
