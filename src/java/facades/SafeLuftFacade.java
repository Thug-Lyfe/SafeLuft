/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entity.FlightInstance;
import entity.Reservation;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
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

                if (SafeLuftFacade.compare(f.getCurrentDate(), date) && f.getAvailabelSeats() >= tickets) {
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
                if (SafeLuftFacade.compare(fi.getCurrentDate(), date) && fi.getAvailabelSeats() >= tickets) {
                    flightinstances.add(fi);
                }
            }

            return flightinstances;
        } finally {
            em.close();
        }
    }

    public static void makeReservation(String i, int s, Reservation r) {
        EntityManager em = emf.createEntityManager();
        try {
            FlightInstance fi = em.find(FlightInstance.class, i);
            em.getTransaction().begin();
            fi.addReservations(r);
            fi.setAvailabelSeats(fi.getAvailabelSeats()-s);
            em.persist(fi);
            em.getTransaction().commit();
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

    public static boolean compare(Calendar c1, String c2) {
        String[] split = c2.split("T");
        String[] date = split[0].split("-");
        if (c1.get(Calendar.YEAR) != Integer.parseInt(date[0])) {
            return false;
        }
        if (c1.get(Calendar.MONTH) != Integer.parseInt(date[1])) {
            return false;
        }
        if (c1.get(Calendar.DAY_OF_MONTH) != Integer.parseInt(date[2])) {
            return false;
        }
        return true;
    }

}
