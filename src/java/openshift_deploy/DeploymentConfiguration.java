package openshift_deploy;

import facades.AutoUpdate;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import security.PasswordStorage;

@WebListener
public class DeploymentConfiguration implements ServletContextListener {

    public static String PU_NAME = "PU-Local";
    private ScheduledExecutorService scheduler;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        
        //If we are testing, then this:
        if (sce.getServletContext().getInitParameter("testEnv") != null) {
            PU_NAME = "PU_TEST";
        }
        Map<String, String> env = System.getenv();
        //If we are running in the OPENSHIFT environment change the pu-name 
        if (env.keySet().contains("OPENSHIFT_MYSQL_DB_HOST")) {
            PU_NAME = "PU_OPENSHIFT";
        }
        try {
            ServletContext context = sce.getServletContext();
            EntityManagerFactory emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
            EntityManager em = emf.createEntityManager();
            scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new AutoUpdate(), 0, 2, TimeUnit.HOURS);

            //This flag is set in Web.xml -- Make sure to disable for a REAL system
            boolean makeTestUsers = context.getInitParameter("makeTestUsers").toLowerCase().equals("true");
            if (!makeTestUsers
                    || (em.find(User.class, "user") != null && em.find(User.class, "admin") != null && em.find(User.class, "user_admin") != null)) {
                return;
            }
            Role userRole = new Role("User");
            Role adminRole = new Role("Admin");

            User user = new User("user", PasswordStorage.createHash("test"));
            User admin = new User("admin", PasswordStorage.createHash("test"));
            User both = new User("user_admin", PasswordStorage.createHash("test"));
            user.AddRole(userRole);
            admin.AddRole(adminRole);
            both.AddRole(userRole);
            both.AddRole(adminRole);

            try {
                em.getTransaction().begin();
                em.persist(userRole);
                em.persist(adminRole);

                em.persist(user);
                em.persist(admin);
                em.persist(both);
                em.getTransaction().commit();
            } finally {
                em.close();
            }

        } catch (PasswordStorage.CannotPerformOperationException ex) {
            Logger.getLogger(DeploymentConfiguration.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        scheduler.shutdownNow();
    }
}
