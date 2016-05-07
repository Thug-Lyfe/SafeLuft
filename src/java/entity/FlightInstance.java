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
import javax.persistence.CascadeType;
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
    private String id;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date currentDate;
    @Temporal(javax.persistence.TemporalType.TIME)
    private Date time;

    private int availabelSeats;
    private double price;
    @OneToMany(cascade = CascadeType.PERSIST)
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

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
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

    public Date getDateTime() {
        Calendar calendarA = Calendar.getInstance();
        calendarA.setTime(currentDate);
        
        Calendar calendarB = Calendar.getInstance();
        calendarB.setTime(time);
        
        calendarA.set(Calendar.HOUR_OF_DAY, calendarB.get(Calendar.HOUR_OF_DAY));
        calendarA.set(Calendar.MINUTE, calendarB.get(Calendar.MINUTE));
        calendarA.set(Calendar.SECOND, calendarB.get(Calendar.SECOND));
        calendarA.set(Calendar.MILLISECOND, calendarB.get(Calendar.MILLISECOND));

        return calendarA.getTime();
    }
}
