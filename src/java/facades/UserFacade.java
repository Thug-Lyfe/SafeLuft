package facades;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entity.Role;
import security.IUserFacade;
import entity.User;
import entity.UserReservation;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import openshift_deploy.DeploymentConfiguration;
import security.IUser;
import security.PasswordStorage;

public class UserFacade implements IUserFacade {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
    private Gson gson;
    public UserFacade() {
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @Override
    public IUser getUserByUserId(String id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(User.class, id);
        } finally {
            em.close();
        }
    }

    public static User getUser(String id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(User.class, id);
        } finally {
            em.close();
        }
    }
    
    public static List<User> getAllUser(){
        EntityManager em = emf.createEntityManager();
        try {
            List<User> users;
            Query q = em.createQuery("SELECT c from User c");
            users = q.getResultList();
            return users;
        } finally {
        }
    }
    
    public static User deleteUser(String id) {
       EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            User u = em.find(User.class, id);
            em.remove(u);
            em.getTransaction().commit();
            return u;
        } finally {
            em.close();
        }
    }
    /*
     Return the Roles if users could be authenticated, otherwise null
     */

    @Override
    /*
     Return the Roles if users could be authenticated, otherwise null
     */
    public List<String> authenticateUser(String userName, String password) {
        EntityManager em = emf.createEntityManager();
        try {
            User user = em.find(User.class, userName);
            if (user == null) {
                return null;
            }

            boolean authenticated;
            try {
                authenticated = PasswordStorage.verifyPassword(password, user.getPassword());
                return authenticated ? user.getRolesAsStrings() : null;
            } catch (PasswordStorage.CannotPerformOperationException | PasswordStorage.InvalidHashException ex) {
                Logger.getLogger(UserFacade.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        } finally {
            em.close();
        }
    }

    public static void registerNewUser(User user) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            user.setPassword(PasswordStorage.createHash(user.getPassword()));
            Role userRole = em.find(Role.class, "User");
            user.AddRole(userRole);
            em.persist(user);
            em.getTransaction().commit();
        } catch (PasswordStorage.CannotPerformOperationException ex) {
            Logger.getLogger(UserFacade.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            em.close();
        }
    }
    public static void RegisterTicket(UserReservation ticket,String userName){
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            User u = em.find(User.class, userName);
            u.addTicket(ticket);
            em.merge(u);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        
    }
    public static JsonArray getTickets(String userName){
        EntityManager em = emf.createEntityManager();
        JsonArray ja = new JsonArray();
        try {
            List<UserReservation> urList = em.find(User.class, userName).getTickets();           
            for (UserReservation ur : urList) {
                ja.add(new JsonParser().parse(ur.getTicket()).getAsJsonObject());
            }
        } finally {
            em.close();
        }
        return ja;
        
    }

}
