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
public class Flight implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String flightNumber;
    private int seats;
    private Airline airline;
    private int flightTime;
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<FlightInstance> instances = new ArrayList();
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Airport from;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Airport to;

    public Airline getAirline() {
        return airline;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }

    public Flight() {
    }

    public Airport getFrom() {
        return from;
    }

    public void setFrom(Airport from) {
        this.from = from;
    }

    public Airport getTo() {
        return to;
    }

    public void setTo(Airport to) {
        this.to = to;
    }

    public void addInstances(FlightInstance fi) {
        instances.add(fi);
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public int getFlightTime() {
        return flightTime;
    }

    public void setFlightTime(int flightTime) {
        this.flightTime = flightTime;
    }

    public List<FlightInstance> getInstances() {
        return instances;
    }

    public void setInstances(List<FlightInstance> instances) {
        this.instances = instances;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

}
