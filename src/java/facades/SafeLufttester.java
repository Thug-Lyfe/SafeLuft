/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entity.Airline;
import entity.Airport;
import entity.Flight;
import entity.FlightInstance;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author David
 */
public class SafeLufttester {

    public static void main(String[] args) {
        Random nr = new Random();
        Persistence.generateSchema("PU-Local", null);
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU-Local");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Airline test = new Airline();
        test.setName("SafeFuft");

        List<FlightInstance> fi = new ArrayList();

        List<Airport> airports = new ArrayList();
        airports.add(new Airport("CPH", 1, "Copenhagen Airport", "Copenhagen", "Denmark"));
        airports.add(new Airport("STN", 0, "London Stansted Airport", "London", "England"));
        airports.add(new Airport("DXB", 4, "Dubai International Airport", "Dubai", "United Arab Emirates"));
        airports.add(new Airport("AMS", 1, "Amsterdam Airport Schiphol", "Haarlemmermeer", "Netherlands"));
        airports.add(new Airport("HKG", 8, "Hong Kong International Airport", "Chek Lap Kok", "Hong Kong"));
        airports.add(new Airport("ATI", -3, "Artigas International Airport", "Artigas", "Uruguay"));
        airports.add(new Airport("ATL", -5, " Hartsfield–Jackson Atlanta International Airport", "Atlanta, Georgia", "United States"));
        airports.add(new Airport("HND", 9, "Tokyo Haneda Airport", "Tokyo", "Japan"));
        airports.add(new Airport("FRA", 1, "Frankfurt Airport", "Frankfurt", "Germany"));
        airports.add(new Airport("JNB", 1, "O. R. Tambo International Airport", "Johannesburg", "South Africa"));
        airports.add(new Airport("HRI", 6, "Mattala Rajapaksa International Airport", "Hambantota", "Sri Lanka"));
        airports.add(new Airport("GUM", 10, "Antonio B. Won Pat International Airport", "Hagåtña", "Guam"));
        airports.add(new Airport("MBJ", -5, "Sangster International Airport", "Montego Bay", "Jamaica"));
        airports.add(new Airport("HNL", -10, "Honolulu International Airport", "Honolulu, Hawaii", "United States"));
        airports.add(new Airport("LAX", -8, "Los Angeles International Airport", "Los Angeles, California", "United States"));
        airports.add(new Airport("GRZ", 1, "Graz Airport", "Graz", "Austria"));
        airports.add(new Airport("DEN", -7, "Denver International Airport", "Denver, Colorado", "United States"));
        airports.add(new Airport("FUK", 9, "Fukuoka Airport", "Fukuoka", "Japan"));
        airports.add(new Airport("POT", 1, "Ken Jones Aerodrome", "Port Antonio", "Jamaica"));
        airports.add(new Airport("NIG", 12, "Nikunau Airport", "Nikunau", "Kiribati"));
        airports.add(new Airport("GER", -5, "Rafael Cabrera Mustelier Airport", "Nueva Gerona", "Cuba"));
        
        Flight f1 = new Flight();
        f1.setFlightNumber("AIR"+ (nr.nextInt(9000)+1000));
        f1.setFrom(airports.get(0));
        f1.setTo(airports.get(1));
        f1.setSeats(100);
        f1.setFlightTime(60);
        f1.setAirline(test);
        test.addFlights(f1);

        for (int i = 0; i < 40; i++) {
            Random r = new Random();
            Flight f = new Flight();
            f.setFlightNumber("AIR"+ (nr.nextInt(9000)+1000));
            int ran1 = r.nextInt(airports.size());
            f.setFrom(airports.get(ran1));
            int ran2 = r.nextInt(airports.size());
            while (ran1 == ran2) {
                ran2 = r.nextInt(airports.size());
            }
            f.setTo(airports.get(ran2));

            f.setSeats(r.nextInt(600) + 50);
            f.setFlightTime(r.nextInt(721) + 60);
            test.addFlights(f);
        }

        for (Flight f : test.getFlights()) {

            for (Calendar c : getDaysBetweenDates(new Date(2016, 4, 1), new Date(2016, 7, 31))) {
                FlightInstance flightins = new FlightInstance();
                flightins.setId(nr.nextInt(9000)+1000 + "-" + nr.nextInt(90000000)+10000000 + "0000");
                flightins.setAvailabelSeats(f.getSeats());
                flightins.setCurrentDate(c);
                flightins.setPrice(420);
                Random r = new Random();
                Calendar cc = new GregorianCalendar();
                cc.set(0, 0, 0, r.nextInt(24), r.nextInt(60));
                flightins.setTime(cc);
                f.addInstances(flightins);

                FlightInstance flightins2 = new FlightInstance();
                flightins2.setId(nr.nextInt(9000)+1000 + "-" + nr.nextInt(90000000)+10000000 + "0000");
                flightins2.setAvailabelSeats(f.getSeats());
                flightins2.setCurrentDate(c);
                flightins2.setPrice(420);
                Calendar cc2 = new GregorianCalendar();
                cc2.set(0, 0, 0, r.nextInt(24), r.nextInt(60));
                flightins2.setTime(cc2);
                f.addInstances(flightins2);

                if (c.after(new GregorianCalendar(2016, 6, 19)) && c.before(new GregorianCalendar(2016, 6, 27))) {
                    
                    FlightInstance flightins3 = new FlightInstance();
                    flightins3.setId(nr.nextInt(9000)+1000 + "-" + nr.nextInt(90000000)+10000000 + "0000");
                    flightins3.setAvailabelSeats(f.getSeats());
                    flightins3.setCurrentDate(c);
                    flightins3.setPrice(420);
                    Calendar cc3 = new GregorianCalendar();
                    cc3.set(0, 0, 0, r.nextInt(24), r.nextInt(60));
                    flightins3.setTime(cc3);
                    f.addInstances(flightins3);
                    
                    
                    FlightInstance flightins4 = new FlightInstance();
                    flightins4.setId(nr.nextInt(9000)+1000 + "-" + nr.nextInt(90000000)+10000000 + "0000");
                    flightins4.setAvailabelSeats(f.getSeats());
                    flightins4.setCurrentDate(c);
                    flightins4.setPrice(420);
                    Calendar cc4 = new GregorianCalendar();
                    cc4.set(0, 0, 0, r.nextInt(24), r.nextInt(60));
                    flightins4.setTime(cc4);
                    f.addInstances(flightins4);
                }

            }
        }

        
        em.merge(test);
        em.getTransaction().commit();
    }

    public static List<Calendar> getDaysBetweenDates(Date startdate, Date enddate) {
        List<Calendar> dates = new ArrayList<Calendar>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startdate);

        while (calendar.getTime().before(enddate)) {
            Calendar c = calendar;
            dates.add(c);
            calendar.add(Calendar.DATE, 1);

        }
        return dates;
    }

}
