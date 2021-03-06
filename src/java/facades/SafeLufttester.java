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
import httpErrors.FlightException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import openshift_deploy.DeploymentConfiguration;

/**
 *
 * @author David
 */
public class SafeLufttester {

    public static void main(String[] args) throws FlightException {
        initialize();
    }

    public static void initialize() throws FlightException {
        Random nr = new Random();

        EntityManagerFactory emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Airline test = new Airline();
        test.setName("SafeLuft");

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
        f1.setFlightNumber("AIR" + (nr.nextInt(9000) + 1000));
        f1.setFrom(airports.get(0));
        f1.setTo(airports.get(1));
        f1.setSeats(100);
        f1.setFlightTime(60);
        f1.setAirline(test);
        test.addFlights(f1);

        for (int i = 0; i < 50; i++) {
            Random r = new Random();
            Flight f = new Flight();
            f.setFlightNumber("AIR" + (nr.nextInt(9000) + 1000));
            int ran1 = r.nextInt(airports.size());
            f.setFrom(airports.get(ran1));
            int ran2 = r.nextInt(airports.size());
            while (ran1 == ran2) {
                ran2 = r.nextInt(airports.size());
            }
            f.setTo(airports.get(ran2));

            f.setSeats(r.nextInt(600) + 50);
            f.setFlightTime(r.nextInt(721) + 60);
            f.setAirline(test);
            test.addFlights(f);
        }
        try {
            for (Date c : getDaysBetweenDatesIncrement7(new SimpleDateFormat("dd-M-yyyy").parse("04-04-2016"), new SimpleDateFormat("dd-M-yyyy").parse("31-07-2016"))) {
                FlightInstance flightins = new FlightInstance();
                    flightins.setId(nr.nextInt(9000) + 1000 + "-" + (nr.nextInt(90000000) + 10000000) + "0000");
                    flightins.setAvailabelSeats(f1.getSeats());
                    flightins.setCurrentDate(c);

                    flightins.setPrice(420);

                    flightins.setFlight(f1);
                    Random r = new Random();

                    flightins.setTime(new SimpleDateFormat("HH:mm").parse(r.nextInt(24) + ":" + r.nextInt(60)));
                    f1.addInstances(flightins);
            }
            for (Flight f : test.getFlights()) {

                for (Date c : getDaysBetweenDates(new SimpleDateFormat("dd-M-yyyy").parse("01-04-2016"), new SimpleDateFormat("dd-M-yyyy").parse("31-07-2016"))) {

                    FlightInstance flightins = new FlightInstance();
                    flightins.setId(nr.nextInt(9000) + 1000 + "-" + (nr.nextInt(90000000) + 10000000) + "0000");
                    flightins.setAvailabelSeats(f.getSeats());
                    flightins.setCurrentDate(c);

                    flightins.setPrice(420);

                    flightins.setFlight(f);
                    Random r = new Random();

                    flightins.setTime(new SimpleDateFormat("HH:mm").parse(r.nextInt(24) + ":" + r.nextInt(60)));
                    f.addInstances(flightins);

                    FlightInstance flightins2 = new FlightInstance();
                    flightins2.setId(nr.nextInt(9000) + 1000 + "-" + (nr.nextInt(90000000) + 10000000) + "0000");
                    flightins2.setAvailabelSeats(f.getSeats());
                    flightins2.setCurrentDate(c);

                    flightins2.setPrice(420);

                    flightins2.setFlight(f);
                    flightins2.setTime(new SimpleDateFormat("HH:mm").parse(r.nextInt(24) + ":" + r.nextInt(60)));
                    f.addInstances(flightins2);

                    if (c.after(new SimpleDateFormat("dd-M-yyyy").parse("19-06-2016")) && c.before(new SimpleDateFormat("dd-M-yyyy").parse("27-06-2016"))) {

                        FlightInstance flightins3 = new FlightInstance();
                        flightins3.setId(nr.nextInt(9000) + 1000 + "-" + (nr.nextInt(90000000) + 10000000) + "0000");
                        flightins3.setAvailabelSeats(f.getSeats());
                        flightins3.setCurrentDate(c);

                        flightins3.setPrice(420);
                        flightins3.setFlight(f);
                        flightins3.setTime(new SimpleDateFormat("HH:mm").parse(r.nextInt(24) + ":" + r.nextInt(60)));
                        f.addInstances(flightins3);

                        FlightInstance flightins4 = new FlightInstance();
                        flightins4.setId(nr.nextInt(9000) + 1000 + "-" + (nr.nextInt(90000000) + 10000000) + "0000");
                        flightins4.setAvailabelSeats(f.getSeats());
                        flightins4.setCurrentDate(c);

                        flightins4.setPrice(420);
                        flightins4.setFlight(f);
                        flightins4.setTime(new SimpleDateFormat("HH:mm").parse(r.nextInt(24) + ":" + r.nextInt(60)));
                        f.addInstances(flightins4);
                    }

                }
            }

        } catch (ParseException ex) {
            Logger.getLogger(SafeLufttester.class.getName()).log(Level.SEVERE, null, ex);
        }

        em.merge(test);
        em.getTransaction().commit();

    }

    public static List<Date> getDaysBetweenDates(Date startdate, Date enddate) {
        List<Date> dates = new ArrayList<Date>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startdate);

        while (calendar.getTime().before(enddate)) {
            Date c = calendar.getTime();
            dates.add(c);
            calendar.add(Calendar.DATE, 1);

        }
        return dates;
    }

    public static List<Date> getDaysBetweenDatesIncrement7(Date startdate, Date enddate) {
        List<Date> dates = new ArrayList<Date>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startdate);

        while (calendar.getTime().before(enddate)) {
            Date c = calendar.getTime();
            dates.add(c);
            calendar.add(Calendar.DATE, 7);

        }
        return dates;
    }
}
