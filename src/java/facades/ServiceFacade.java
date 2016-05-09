/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entity.Service;
import entity.User;
import entity.UserReservation;
import httpErrors.FlightException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import openshift_deploy.DeploymentConfiguration;


/**
 *
 * @author Warco
 */
public class ServiceFacade {
    private static boolean updated = false;
    private static List<Service> servs;
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
    
    
    public static JsonElement getFlights(String from, String to, String date, String tickets) throws FlightException{
        if(updated == false){
            update();
        }
        FlightData fd = new FlightData();
        List<Thread> list = new ArrayList();
        for (int i = 0; i < servs.size(); i++) {
            list.add(new JsonReader(servs.get(i).getWebsite(),date,from,to,tickets,fd));
            list.get(i).start();
        }
        for (int i = 0; i < list.size(); i++) {
            try {
                list.get(i).join();
            } catch (InterruptedException ex) {
                Logger.getLogger(ServiceFacade.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
        return fd.getFlights();
    }
    
    public static void update(){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
            Query q = em.createQuery("SELECT a FROM Service a");
            servs = q.getResultList();
            em.getTransaction().commit();
            em.close();
            updated = true;
        }
    
    public static void main(String[] args) throws FlightException{
        System.out.println(getFlights("CPH","","2016-04-30","3").toString());
        
    }
    public static List<Service> getListService(){
        return servs;
    }
    public static JsonElement getServices(){
        if(updated == false){
            update();
        }
        JsonArray ja = new JsonArray();
        for (Service ap : servs) {
            JsonObject jo = new JsonObject();
            jo.addProperty("id", ap.getId());
            jo.addProperty("name", ap.getName());
            jo.addProperty("website", ap.getWebsite());
            ja.add(jo);
        }
        return ja;
    }
    public static void deleteService(int id){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(em.find(Service.class, id));
        em.getTransaction().commit();
        em.close();
        update();
    }
    public static void addService(Service serv){
        EntityManager em = emf.createEntityManager();
        
        em.getTransaction().begin();
        em.persist(serv);
        em.getTransaction().commit();
        em.close();
        update();
    }
    
    public static JsonArray getAllTickets(){
        EntityManager em = emf.createEntityManager();
        JsonObject tickets = new JsonObject();
        try {
            List<User> users = UserFacade.getAllUser();
            JsonArray ja = new JsonArray();
            for (User user : users) {
                for (UserReservation reserv : user.getTickets()) {
                    ja.add(new JsonParser().parse(reserv.getTicket()).getAsJsonObject());
                }
                
            }
            return ja;
        } finally {
            em.close();
        }
    }
    
    
    
    }


