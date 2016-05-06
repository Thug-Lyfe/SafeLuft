/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entity.FlightInstance;
import entity.Reservation;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
 * @author David
 */
public class SafeLuftFacade {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);

    public static List<FlightInstance> getAnyFlight(String from, String date, int tickets) {
        EntityManager em = emf.createEntityManager();
        try {
            List<FlightInstance> flights = getFlightsFrom(from);
            List<FlightInstance> flightinstances = new ArrayList();
            for (FlightInstance f : flights) {

                if (SafeLuftFacade.compare(f.getDateTime(), date) && f.getAvailabelSeats() >= tickets) {
                    flightinstances.add(f);
                }

            }
            return flightinstances;
        } finally {
            em.close();
        }
    }

    public static List<FlightInstance> getSpecificFlight(String from, String to, String date, int tickets) {
        EntityManager em = emf.createEntityManager();
        try {
            List<FlightInstance> flights = getFlightsFromTo(from, to);
            List<FlightInstance> flightinstances = new ArrayList<>();

            for (FlightInstance fi : flights) {
                if (SafeLuftFacade.compare(fi.getDateTime(), date) && fi.getAvailabelSeats() >= tickets) {
                    flightinstances.add(fi);
                }
            }

            return flightinstances;
        } finally {
            em.close();
        }
    }

    public static FlightInstance makeReservation(String i, Reservation r) {
        EntityManager em = emf.createEntityManager();
        try {
            FlightInstance fi = em.find(FlightInstance.class, i);
            em.getTransaction().begin();
            fi.addReservations(r);
            fi.setAvailabelSeats(fi.getAvailabelSeats() - r.getPassengers().size());
            em.persist(fi);
            em.getTransaction().commit();
            return fi;
        } finally {
            em.close();

        }
    }

    public static List<FlightInstance> getFlightsFrom(String from) {
        EntityManager em = emf.createEntityManager();
        try {
            List<FlightInstance> flights;
            Query q = em.createQuery("SELECT f from FlightInstance f WHERE f.flight.from.IATACode = :code");
            q.setParameter("code", from);
            flights = q.getResultList();
            return flights;
        } finally {
            em.close();
        }
    }

    public static List<FlightInstance> getFlightsFromTo(String from, String to) {
        EntityManager em = emf.createEntityManager();
        try {
            List<FlightInstance> flights;
            Query q = em.createQuery("SELECT f from FlightInstance f WHERE f.flight.from.IATACode = :codeFROM AND f.flight.to.IATACode = :codeTO");
            q.setParameter("codeFROM", from);
            q.setParameter("codeTO", to);
            flights = q.getResultList();
            return flights;
        } finally {
            em.close();
        }
    }

    public static boolean compare(Date c1, String c2) {
        boolean sameDay = false;
        try {
            Date c3 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(c2);

            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            cal1.setTime(c1);
            cal2.setTime(c3);
            sameDay = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
                    && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
            return sameDay;

        } catch (ParseException ex) {
            Logger.getLogger(SafeLuftFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sameDay;
    }

}
