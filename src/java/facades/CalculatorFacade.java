/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entity.DailyRate;
import entity.Rate;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import openshift_deploy.DeploymentConfiguration;

/**
 *
 * @author Alex
 */
public class CalculatorFacade {

    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);

    public static double getCurrency(String curr) {
        EntityManager em = emf.createEntityManager();
        double r = 0;
        try {
            em.getTransaction().begin();
            Query q = em.createQuery("SELECT c FROM DailyRate c WHERE c.currentDate = :d");
//            q.setParameter("code", curr);
            q.setParameter("d", new Date());
            List<DailyRate> res = q.getResultList();
            em.getTransaction().commit();
            if (!res.isEmpty()) {
                DailyRate dr = res.get(0);
                List<Rate> rates = dr.getRateList();
                for (int i = 0; i < dr.getRateList().size(); i++) {
                    if (rates.get(i).getCode().equalsIgnoreCase(curr)) {
                        r = rates.get(i).getRate();
                    }
                }
                
            }
        } finally {
            em.close();
        }
        return r;
    }

}