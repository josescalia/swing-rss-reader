package org.josescalia.swingrss.gui;

import org.apache.log4j.Logger;
import org.josescalia.rss.model.ApplicationConfig;
import org.josescalia.rss.model.Rss;
import org.josescalia.swingrss.service.impl.ApplicationConfigServiceImpl;
import org.josescalia.swingrss.service.impl.RssServiceImpl;
import org.josescalia.swingrss.util.OperatingSystemUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Josescalia
 * Date: 8/17/13
 * Time: 5:26 PM
 * To change this template use File | Settings | File Templates.
 * existence
 */
@Component
public class Application {
    static Logger logger = Logger.getLogger(Application.class.getName());

    private static final String SPRING_CONTEXT = "rssReaderApplicationContext.xml";
    ApplicationContext context;
    Application app = null;

    @Autowired
    RssServiceImpl service;

    @Autowired
    ApplicationConfigServiceImpl configService;

    public void init() {
        //
        URLClassLoader classLoader = (URLClassLoader) Application.class.getClassLoader();
        System.out.println("Classpath to load :");
        System.out.println("------------------------------------------------------------------------------");
        for (int i = 0; i < classLoader.getURLs().length; i++) {
            //URL url = classLoader.getURLs()[i];
            // System.out.println(url);
        }
        System.out.println("=============================================================================\n\n");

        context = new ClassPathXmlApplicationContext(SPRING_CONTEXT);
        //register class to context to activate Spring-Autowired capability
        app = context.getBean(Application.class);
        //service = context.getBean(RssServiceImpl.class);
        //configService = context.getBean(ApplicationConfigServiceImpl.class);


        //if it is new Installation
        if (getAllAppConfig().size()==0) {
            logger.info("New Installation detected, try adding config values");
            try {
                addConfiguration();
            } catch (Exception e) {
                logger.error("Exception when adding new Data : " + e);
            }
        }

        if (getAllRss().size() ==0) {
            logger.info("New Installation detected, try adding some data");
            try {
                addRss();
            } catch (Exception e) {
                logger.error("Exception when adding new Data : " + e);
            }
        }
        
       
        
    }

    private void addConfiguration() throws Exception{
        ApplicationConfig applicationConfig = new ApplicationConfig("DEFAULT_LF", getDefaultLF());
        try {
            app.configService.save(applicationConfig);
        } catch (Exception e) {
            //logger.error("Exception when setting new Configuration");
            throw new Exception(e);
        }
    }

    private String getDefaultLF() {
        try {
            if (OperatingSystemUtil.getOSInfo().equalsIgnoreCase("Windows")) {
                return "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
            } else {
                for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        return "Nimbus#" + info.getClassName();
                        //break;
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
        return null;

    }

    public void run() {
        MainApp mainApp = new MainApp();
        
        //setDefault look and feel
        Map<String,Object> param = new HashMap<String, Object>();
        param.put("configName","DEFAULT_LF");
        try {
            mainApp.setLookAndFeel(app.configService.findExact(param).get(0).getConfigValue().split("#")[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }

        
    }

    public void addRss() throws Exception {
        //RssServiceImpl service;
        Rss feed1, feed2, feed3, feed4, feed5, feed6;

        feed1 = new Rss("Inilah Bola", "http://www.inilah.com/rss/feed/bola/");
        feed2 = new Rss("Google News", "http://news.google.com/?output=rss");
        feed3 = new Rss("Yahoo News and Media", "http://dir.yahoo.com/rss/dir/getrss.php?news");
        feed4 = new Rss("Yahoo Movie", "http://dir.yahoo.com/rss/dir/getrss.php?ent_mov");
        feed5 = new Rss("CNN", "http://rss.cnn.com/rss/edition.rss");
        feed6 = new Rss("Republika Online", "http://www.republika.co.id/rss");

        app.service.save(feed1);
        app.service.save(feed2);
        app.service.save(feed3);
        app.service.save(feed4);
        app.service.save(feed5);
        app.service.save(feed6);
    }

    public List<Rss> getAllRss() {
        return app.service.getAll();
    }

    public List<ApplicationConfig> getAllAppConfig() {
        return app.configService.getAll();

    }

    public static void main(String[] args) throws Exception {
        Application application = new Application();
        application.init();


        application.run();
        //Application application = new Application();
        //application.init();
        //app.run();



        /*Connection conn = null;
        try {
            Class.forName("org.h2.Driver");
            conn = DriverManager.getConnection("jdbc:h2:file:~/test;IFEXISTS=TRUE", "sa", "");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        if (conn != null) {
            try {
                System.out.println(conn.getClientInfo());
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }*/
    }
}
