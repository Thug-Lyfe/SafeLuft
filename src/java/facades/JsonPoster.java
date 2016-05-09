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
        InputStreamReader is = null;
        try {
            ticket.remove("user");
            ticket.remove("airline");
            String json = ticket.toString();
            HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
            con.setRequestProperty("Content-Type", "application/json;");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Method", "POST");
            con.setDoOutput(true);
            PrintWriter pw = new PrintWriter(con.getOutputStream());
            try (OutputStream os = con.getOutputStream()) {
                os.write(json.getBytes("UTF-8"));
            }
            int HttpResult = con.getResponseCode();
            is = HttpResult < 400 ? new InputStreamReader(con.getInputStream(), "utf-8") : new InputStreamReader(con.getErrorStream(), "utf-8");
            Scanner responseReader = new Scanner(is);
            String response = "";
            while (responseReader.hasNext()) {
                response += responseReader.nextLine() + System.getProperty("line.separator");
            }
            System.out.println(response);
            System.out.println(con.getResponseCode());
            System.out.println(con.getResponseMessage());
        } catch (IOException ex) {
            Logger.getLogger(JsonPoster.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                is.close();
            } catch (IOException ex) {
                Logger.getLogger(JsonPoster.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
