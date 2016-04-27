/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minidev.json.JSONObject;

/**
 *
 * @author Warco
 */
public class JsonReader extends Thread {

    private String url;
    private JsonObject flights;

    public JsonObject getFlights() {
        return flights;
    }

    public JsonReader(String url, String date, String from, String to, String tickets) {
        if (to.equals("")) {
            this.url = url + "/api/flightinfo/" + from + "/" + date + "T00:00:00.000Z/" + tickets;
        } else {
            this.url = url + "/api/flightinfo/" + from + "/" + to + "/" + date + "T00:00:00.000Z/" + tickets;
        }
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JsonObject readJsonFromUrl(String url) throws IOException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JsonObject json = new JsonParser().parse(jsonText).getAsJsonObject();
            return json;
        } finally {
            is.close();
        }
    }

    public static void main(String[] args) throws IOException {
        JsonObject json = readJsonFromUrl("http://angularairline-plaul.rhcloud.com/api/flightinfo/CPH/2016-04-30T00:00:00.000Z/3");
        System.out.println(json.toString());

    }

    @Override
    public void run() {
        try {
            flights = readJsonFromUrl(url);
        } catch (IOException ex) {
            Logger.getLogger(JsonReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
