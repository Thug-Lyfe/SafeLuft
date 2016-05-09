/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import com.google.gson.JsonObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;





/**
 *
 * @author Warco
 */

public class JsonPoster extends Thread{
    private String url;
    private JsonObject ticket;
    private FlightData fd;
    public JsonPoster(JsonObject ticket, String url,FlightData fd) {
            this.ticket = ticket;
            this.fd = fd;
                    String lars = "/api/reservation";
        if(url.equals("http://angularairline-plaul.rhcloud.com")){
            lars = "/api/flightreservation";
        }
                    
            this.url = url + lars + ticket.get("flightId").getAsString();
        
    }

    @Override
    public void run() {
    HttpClient httpClient = HttpClientBuilder.create().build(); //Use this instead 

    try {
        HttpPost request = new HttpPost(url);
        StringEntity params =new StringEntity(ticket.toString());
        request.addHeader("content-type", "application/x-www-form-urlencoded");
        request.setEntity(params);
        HttpResponse response = httpClient.execute(request);
        if(response.getStatusLine().getStatusCode() > 199 && response.getStatusLine().getStatusCode() < 211){
            fd.setTicketResponse(true);
        }
        
    }catch (Exception ex) {
        // handle exception here
    }
}
}
 
    
   

