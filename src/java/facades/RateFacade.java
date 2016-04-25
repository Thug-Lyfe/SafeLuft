/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import entity.DailyRate;
import entity.Rate;
import java.io.IOException;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import java.net.URL;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import openshift_deploy.DeploymentConfiguration;

/**
 *
 * @author butwhole
 */
public class RateFacade extends DefaultHandler{

    DailyRate currency;
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);

    public static JsonObject RatesToday() {
        EntityManager em = emf.createEntityManager();
        JsonObject jo = new JsonObject();
        try {
            em.getTransaction().begin();
            Query q = em.createQuery("SELECT c FROM DailyRate c WHERE c.currentDate = :d");
            q.setParameter("d", new Date());
            List<DailyRate> res = q.getResultList();
            em.getTransaction().commit();
            JsonArray ja = new JsonArray();
            if(!res.isEmpty()){
            DailyRate dr = res.get(0);
            List<Rate> rates = dr.getRateList();
                for (int i = 0; i < dr.getRateList().size(); i++) {
                    JsonObject rate = new JsonObject();
                    rate.addProperty("code", rates.get(i).getCode());
                    rate.addProperty("desc", rates.get(i).getDisc());
                    rate.addProperty("rate", rates.get(i).getRate());
                    ja.add(rate);
                }
            jo.addProperty("date", new Date().toString());
            jo.add("rates", ja);
            }
        } finally {
            em.close();
        }
        return jo;
    }
    @Override
    public void startDocument() throws SAXException {
        System.out.println("Start Document (Sax-event)");
        EntityManager em = emf.createEntityManager();
        try {

            em.getTransaction().begin();
            Query q = em.createQuery("SELECT c FROM DailyRate c WHERE c.currentDate = :d");
            
            q.setParameter("d", new Date());
            List<DailyRate> dr = q.getResultList();
            if(!dr.isEmpty()){
            em.remove(dr.get(0));
                    }
            em.getTransaction().commit();

            currency = new DailyRate();
            currency.setDate(new Date());
        } finally {
            em.close();
        }

        currency = new DailyRate();
        currency.setDate(new Date());
        Rate dkk = new Rate("DKK","Danish Krone",100);
        currency.addRate(dkk);
    }

    @Override
    public void endDocument() throws SAXException {
        System.out.println("End Document (Sax-event)");
        EntityManager em = emf.createEntityManager();
        try {

            em.getTransaction().begin();
            em.persist(currency);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (localName.equals("currency")) {
            Rate r;
            double value;
            if (attributes.getValue(2).equals("-")) {
                value = 0;
            } else {
                value = Double.parseDouble(attributes.getValue(2));
            }

            r = new Rate(attributes.getValue(0), attributes.getValue(1), value);
            currency.addRate(r);

        }

    }

    public static void getRates() {
        try {
            XMLReader xr = XMLReaderFactory.createXMLReader();
            xr.setContentHandler(new RateFacade());
            URL url = new URL("http://www.nationalbanken.dk/_vti_bin/DN/DataService.svc/CurrencyRatesXML?lang=en");
            xr.parse(new InputSource(url.openStream()));
            
        } catch (SAXException | IOException e) {
            e.printStackTrace();

        }
        
    }

    public static void main(String[] args) {
        RateFacade.getRates();
    }

   


    }

