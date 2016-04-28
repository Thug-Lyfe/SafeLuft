/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entity.FlightInstance;
import entity.Reservation;
import java.util.List;

/**
 *
 * @author David
 */
public class JsonForDummies {
    
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    
    public static JsonObject Response1(List<FlightInstance> f, int tickets){
        JsonObject res = new JsonObject();
        JsonArray flights = new JsonArray();
        res.addProperty("airline", f.get(0).getFlight().getAirline().getName());
        for (FlightInstance fi : f) {
            JsonObject flight = new JsonObject();
            flight.addProperty("flightID", fi.getId());
            flight.addProperty("flightNumber", fi.getFlight().getFlightNumber());
            flight.addProperty("date", fi.getISO8601StringForDate());
            flight.addProperty("numberOfSeats", tickets);
            flight.addProperty("totalPrice", (fi.getPrice()*tickets));
            flight.addProperty("traveltime", fi.getFlight().getFlightTime());
            flight.addProperty("origin", fi.getFlight().getFrom().getIATACode());
            flight.addProperty("destination", fi.getFlight().getTo().getIATACode());
            
            flights.add(flights);
        }
        res.add("flights", flights);
        return res;
    }
    
    
    
    public static String getJSON(JsonElement voorhees) {

        return gson.toJson(voorhees);
    }
    
    public static void makeReservation(String r){
        JsonObject something = new JsonParser().parse(r).getAsJsonObject();
        
        
    }
    
}
