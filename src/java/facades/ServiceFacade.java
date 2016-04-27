/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import com.google.gson.JsonObject;
import entity.Service;
import java.util.ArrayList;
import java.util.List;
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
    private static List<Service> airlines;
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
    
    public static JsonObject getFlightsFromTo(String from, String to, String date, String tickets) throws InterruptedException{
        if(updated == false){
            update();
        }
        List<Thread> list = new ArrayList();
        for (int i = 0; i < airlines.size(); i++) {
            list.add(new JsonReader(airlines.get(i).getLink(),date,from,to,tickets));
            list.get(i).start();
        }
        for (int i = 0; i < list.size(); i++) {
            list.get(i).join(1000);
            list.get(i).get
        }
        
        
        return new JsonObject();
    }
    
    public static void update(){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
            Query q = em.createQuery("SELECT * FROM Service");
            airlines = q.getResultList();
            em.getTransaction().commit();
        }
    }


