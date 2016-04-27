/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entity.Airline;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author David
 */
public class SafeLufttester {
    public static void main(String[] args) {
        Persistence.generateSchema("PU", null);
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU-Local");
//        EntityManager em = emf.createEntityManager();
//        Airline safeluft = new Airline();
//        safeluft.setName("SafeFuft");
//        em.getTransaction().begin();
//        em.persist(safeluft);
//        em.getTransaction().commit();
    }
    
    
    
}
