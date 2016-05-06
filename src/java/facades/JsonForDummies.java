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
import entity.Passenger;
import entity.Reservation;
import java.util.List;

/**
 *
 * @author David
 */
public class JsonForDummies {
    
    private static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").setPrettyPrinting().create();
    
    
    public static JsonObject Response1(List<FlightInstance> f, int tickets){
        JsonObject res = new JsonObject();
        JsonArray flights = new JsonArray();
        res.addProperty("airline", f.get(0).getFlight().getAirline().getName());
        for (FlightInstance fi : f) {
            JsonObject flight = new JsonObject();
            flight.addProperty("flightID", fi.getId());
            flight.addProperty("flightNumber", fi.getFlight().getFlightNumber());
            flight.addProperty("date", gson.toJson(fi.getDateTime()));
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
    
    public static JsonObject reservationResponse(FlightInstance f, JsonObject r){
        String reserveeName = r.get("reserveeName").getAsString();
        int seats = r.get("numberOfSeats").getAsInt();
        
        
        
        JsonObject res = new JsonObject();
        JsonArray passengers = new JsonArray();
        res.addProperty("flightNumber", f.getFlight().getFlightNumber());
        res.addProperty("origin", f.getFlight().getFrom().getName()+"("+f.getFlight().getFrom().getIATACode()+")");
        res.addProperty("destination", f.getFlight().getTo().getIATACode());
        res.addProperty("date", gson.toJson(f.getDateTime()));
        res.addProperty("flightTime", f.getFlight().getFlightTime());
        res.addProperty("numberOfSeats", seats);
        res.addProperty("reserveeName", reserveeName);
        
        for (JsonElement p : r.get("passengers").getAsJsonArray()) {
            passengers.add(p);
        }
        res.add("passengers", passengers);
        return res;
    }
    
    
    
    public static String getJSON(JsonElement voorhees) {

        return gson.toJson(voorhees);
    }
    
    public static Reservation makeReservation(JsonObject r){
        JsonArray passengers = r.get("passengers").getAsJsonArray();
        
        Reservation res = new Reservation();
        for (JsonElement passenger : passengers) {
            JsonObject per = passenger.getAsJsonObject();
            Passenger p = new Passenger();
            p.setFirstName(per.get("firstName").getAsString());
            p.setLastName(per.get("lastName").getAsString());
            res.addPassengers(p);
        }
        return res;
        
    }
    
}
