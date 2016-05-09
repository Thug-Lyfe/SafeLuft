package facades;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import entity.Role;
import entity.Service;
import security.IUserFacade;
import entity.User;
import entity.UserReservation;
import java.util.List;
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

    public static List<User> getAllUser() {
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

    public static void RegisterTicket(UserReservation ticket, String userName) {
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

    public static JsonArray getTickets(String userName) {
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

    public static boolean reserveTickets(JsonObject ticket, String airline) {
        EntityManager em = emf.createEntityManager();
        List<Service> servs = ServiceFacade.getListService();
        String url = "";
        FlightData fd = new FlightData();
        try {
            for (Service serv : servs) {
                if (serv.getName().equals(airline)) {
                    url = serv.getWebsite();
                }
            }

            Thread t = new Thread(new JsonPoster(ticket, url, fd));
            t.start();

            t.join(2000);

        } catch (InterruptedException ex) {
            Logger.getLogger(UserFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fd.isTicketResponse();
    }

    public static JsonArray getAllUserAsJson() {
        List<User> users = getAllUser();
        JsonArray ja = new JsonArray();
        for (User user : users) {
            ja.add(userToJson(user));

        }
        return ja;
    }

    private static JsonObject userToJson(User u) {
        JsonObject users = new JsonObject();
        JsonArray roles = new JsonArray();

        if (u.getUserName() != null) {
            users.addProperty("username", u.getUserName());
        } else {
            users.addProperty("username", "");
        }
        if (u.getRoles() != null || u.getRoles().isEmpty()) {
            for (int i = 0; i < u.getRoles().size(); i++) {
                if (u.getRoles().get(i) != null) {
                    JsonPrimitive role = new JsonPrimitive(u.getRoles().get(i).getRoleName());
                    roles.add(role);
                }

            }
        } else {
            roles.add(new JsonObject());
        }

        users.add("roles", roles);
        return users;
    }

}
