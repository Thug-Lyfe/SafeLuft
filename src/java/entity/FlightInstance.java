/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author David
 */
@Entity
@Table
public class FlightInstance implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Calendar currentDate;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Calendar time;

    private int availabelSeats;    
    private double price;
    @OneToMany
    private List<Reservation> reservations = new ArrayList();

    @ManyToOne
    private Flight flight;

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public FlightInstance() {
    }

    public void addReservations(Reservation r) {
        reservations.add(r);
    }

    public String getId() {
        return id;
    }

    public Calendar getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Calendar currentDate) {
        this.currentDate = currentDate;
    }

    public Calendar getTime() {
        return time;
    }

    public void setTime(Calendar time) {
        this.time = time;
    }

    public int getAvailabelSeats() {
        return availabelSeats;
    }

    public void setAvailabelSeats(int availabelSeats) {
        this.availabelSeats = availabelSeats;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getISO8601StringForDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.format(currentDate);
    }

}
