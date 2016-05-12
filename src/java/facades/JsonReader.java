/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Warco
 */
public class JsonReader extends Thread {

    private String url;
    private FlightData fd;

    public JsonReader(String url, String date, String from, String to, String tickets, FlightData fd) {
        String lars = "/api/flights/";
        if (url.equals("http://angularairline-plaul.rhcloud.com")) {
            lars = "/api/flightinfo/";
        }
        if (to.equals("")) {
            this.url = url + lars + from + "/" + date + "/" + tickets;
        } else {
            this.url = url + lars + from + "/" + to + "/" + date + "/" + tickets;
        }
        this.fd = fd;
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    private static JsonObject readJsonFromUrl(String url) throws IOException {
        InputStream is = null;
        
        try {
            is = new URL(url).openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JsonObject json = new JsonParser().parse(jsonText).getAsJsonObject();
            return json;
        }finally {
            if(is != null){
            is.close();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        JsonObject json = readJsonFromUrl("http://angularairline-plaul.rhcloud.com/api/flightinfo/CPH/2016-04-30T00:00:00.000Z/3");
        System.out.println(json.toString());

    }

    @Override
    public void run() {
        try {
            JsonObject flights = readJsonFromUrl(url);
            fd.addFlights(flights);
        } catch (IOException ex) {
            Logger.getLogger(JsonReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
