/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jayway.restassured.RestAssured;
import entity.User;
import facades.CalculatorFacade;
import facades.UserFacade;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import openshift_deploy.DeploymentConfiguration;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Warco
 */
public class NewEmptyJUnitTest {
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static EntityManager em;
    private static String c1,c2;
    private static Double d1,d2,res;
    public NewEmptyJUnitTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
         
    }
    
    @AfterClass
    public static void tearDownClass() {
        
    }
    
    @Before
    public void setUp() {
        em = emf.createEntityManager();
        RestAssured.basePath = "http://localhost:9090/CA-3";
        // http://www.nationalbanken.dk/_vti_bin/DN/DataService.svc/CurrencyRatesXML?lang=en
        // go there and find 2 currencies to test
        // in this case 'AUD' and 'EUR'
        c1 = "AUD";
        d1 = 506.76;
        c2 = "EUR";
        d2 = 744.17;
        res = Math.round(((100*d1)/d2)*100.00)/100.00;
        
    }
    
    @After
    public void tearDown() {
        em.close();
    }

    @Test
    public void dataBaseTest(){
        //if the database has been updated it will have also added dkk which we can check
        Double d = CalculatorFacade.getCurrency(c1);
        assertEquals(d1,d,0.01);
        
        
        
    }
    
    @Test
    public void calculateTest(){
            //this is copied from the user Rest api @Path("/calculator/{amount}/{fromcurrency}/{tocurrency}")
            Double temp = ((100*CalculatorFacade.getCurrency(c1))/CalculatorFacade.getCurrency(c2))*100.00;
            assertEquals(res, Math.round(temp)/100.00,0.01);
    }
    
    @Test
    public void UserTest(){
        
        UserFacade.registerNewUser(new User("TestUser1234","pass1234"));
        List<User> preDel = UserFacade.getAllUser();
        assertEquals("TestUser1234",UserFacade.getUser("TestUser1234").getUserName());
        UserFacade.deleteUser("TestUser1234");
        List<User> aftDel = UserFacade.getAllUser();
        assertEquals(aftDel.size(), preDel.size()-1);
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
