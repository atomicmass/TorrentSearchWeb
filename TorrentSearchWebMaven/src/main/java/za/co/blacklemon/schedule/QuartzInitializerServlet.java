package za.co.blacklemon.schedule;

import javax.servlet.ServletContext; 
import javax.servlet.ServletContextEvent; 
import javax.servlet.ServletContextListener; 
 
import org.apache.commons.logging.Log; 
import org.apache.commons.logging.LogFactory; 
import org.quartz.Scheduler; 
import org.quartz.impl.StdSchedulerFactory; 


public class QuartzInitializerServlet implements ServletContextListener {

    public static final String QUARTZ_FACTORY_KEY = "org.quartz.impl.StdSchedulerFactory.KEY"; 
 
    private boolean performShutdown = true; 
 
    private Scheduler scheduler = null; 
 
    private final Log log = LogFactory.getLog(getClass()); 
     
    /*     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ 
     * 
     * Interface. 
     * 
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ 
     */ 
 
    public void contextInitialized(ServletContextEvent sce) { 
 
        log.info("Quartz Initializer Servlet loaded, initializing Scheduler..."); 
 
        ServletContext servletContext = sce.getServletContext(); 
        StdSchedulerFactory factory; 
        try { 
 
            String configFile = servletContext.getInitParameter("config-file"); 
            String shutdownPref = servletContext.getInitParameter("shutdown-on-unload"); 
 
            if (shutdownPref != null) { 
                performShutdown = Boolean.valueOf(shutdownPref).booleanValue(); 
            } 
 
            // get Properties 
            if (configFile != null) { 
                factory = new StdSchedulerFactory(configFile); 
            } else { 
                factory = new StdSchedulerFactory(); 
            } 
 
            // Always want to get the scheduler, even if it isn't starting,  
            // to make sure it is both initialized and registered. 
            scheduler = factory.getScheduler(); 
 
            // Should the Scheduler being started now or later 
            String startOnLoad = servletContext 
                    .getInitParameter("start-scheduler-on-load"); 
 
            int startDelay = 0; 
            String startDelayS = servletContext.getInitParameter("start-delay-seconds"); 
            try { 
                if(startDelayS != null && startDelayS.trim().length() > 0) 
                    startDelay = Integer.parseInt(startDelayS); 
            } catch(Exception e) { 
                log.error("Cannot parse value of 'start-delay-seconds' to an integer: " + startDelayS + ", defaulting to 5 seconds."); 
                startDelay = 5; 
            } 
 
            /*             * If the "start-scheduler-on-load" init-parameter is not specified, 
             * the scheduler will be started. This is to maintain backwards 
             * compatability. 
             */ 
            if (startOnLoad == null || (Boolean.valueOf(startOnLoad).booleanValue())) { 
                if(startDelay <= 0) { 
                    // Start now 
                    scheduler.start(); 
                    log.info("Scheduler has been started..."); 
                } 
                else { 
                    // Start delayed 
                    scheduler.startDelayed(startDelay); 
                    log.info("Scheduler will start in " + startDelay + " seconds."); 
                } 
            } else { 
                log.info("Scheduler has not been started. Use scheduler.start()"); 
            } 
 
            String factoryKey =  
                servletContext.getInitParameter("servlet-context-factory-key"); 
            if (factoryKey == null) { 
                factoryKey = QUARTZ_FACTORY_KEY; 
            } 
 
            log.info("Storing the Quartz Scheduler Factory in the servlet context at key: " 
                    + factoryKey); 
            servletContext.setAttribute(factoryKey, factory); 
 
        } catch (Exception e) { 
            log.error("Quartz Scheduler failed to initialize: " + e.toString()); 
            e.printStackTrace(); 
        } 
    } 
 
    public void contextDestroyed(ServletContextEvent sce) { 
 
        if (!performShutdown) { 
            return; 
        } 
 
        try { 
            if (scheduler != null) { 
                scheduler.shutdown(); 
            } 
        } catch (Exception e) { 
            log.error("Quartz Scheduler failed to shutdown cleanly: " + e.toString()); 
            e.printStackTrace(); 
        } 
 
        log.info("Quartz Scheduler successful shutdown."); 
    } 

    
}
