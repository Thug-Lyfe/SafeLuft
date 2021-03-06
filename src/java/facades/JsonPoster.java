/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.ws.rs.client.Entity.json;

/**
 *
 * @author Warco
 */
public class JsonPoster extends Thread {

    private String url;
    private JsonObject ticket;
    private FlightData fd;

    public JsonPoster(JsonObject ticket, String url, FlightData fd) {
        this.ticket = ticket;
        this.fd = fd;
        String lars = "/api/reservation/";
        if (url.equals("http://angularairline-plaul.rhcloud.com")) {
            lars = "/api/flightreservation/";
        }

        this.url = url + lars + ticket.get("flightId").getAsString();

    }

    @Override
    public void run() {
        
        }
    }

