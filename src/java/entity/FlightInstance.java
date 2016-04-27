/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    private int id;
    @Temporal(javax.persistence.TemporalType.DATE)
    Date currentDate;
    @Temporal(javax.persistence.TemporalType.TIME)
    Date time;
    
    private int availabelSeats;
    private double price;
    @OneToMany
    private List<Reservation> reservations = new ArrayList();

    public FlightInstance() {
    }
    

    public void addReservations(Reservation r){
        reservations.add(r);
    }
    
    public int getId() {
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

    public void setId(int id) {
        this.id = id;
    }

    
}
