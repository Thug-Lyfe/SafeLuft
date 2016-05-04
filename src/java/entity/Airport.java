/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author David
 */
@Entity
@Table
public class Airport implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(unique = true)
    private String IATACode;
    
    private int timeZone;
    private String name;
    private String city;
    private String country;
    public Airport() {
    }

    public Airport(String IATACode, int timeZone, String name, String city, String country) {
        this.IATACode = IATACode;
        this.timeZone = timeZone;
        this.name = name;
        this.city = city;
        this.country = country;
    }
    
    
    
    public String getIATACode() {
        return IATACode;
    }

    public void setIATACode(String IATACode) {
        this.IATACode = IATACode;
    }

    public int getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(int timeZone) {
        this.timeZone = timeZone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

  
}
