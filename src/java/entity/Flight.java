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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int flightNumber;
    
    private int seats;
    
    @Temporal(javax.persistence.TemporalType.TIME)
    Date flightTime;
    
    @OneToMany
    private List<FlightInstance> instances = new ArrayList();
    @ManyToOne
    private Airport from;
    @ManyToOne
    private Airport to;

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
    
    public void addInstances(FlightInstance fi){
        instances.add(fi);
    }
    
    public int getFlightNumber() {
        return flightNumber;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public Date getFlightTime() {
        return flightTime;
    }

    public void setFlightTime(Date flightTime) {
        this.flightTime = flightTime;
    }

    public List<FlightInstance> getInstances() {
        return instances;
    }

    public void setInstances(List<FlightInstance> instances) {
        this.instances = instances;
    }

    public void setFlightNumber(int flightNumber) {
        this.flightNumber = flightNumber;
    }

}
