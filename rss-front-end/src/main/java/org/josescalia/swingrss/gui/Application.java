package org.josescalia.swingrss.gui;

import org.josescalia.rss.model.Rss;
import org.josescalia.swingrss.service.RssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

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
    private static final String CONFIG_PATH = "classpath*:applicationContext.xml";
    ApplicationContext context;
    Application app = null;

    @Autowired
    RssService service;

    public void init() {
        //
        URLClassLoader classLoader = (URLClassLoader) Application.class.getClassLoader();
        System.out.println("Classpath to load :");
        System.out.println("------------------------------------------------------------------------------");
        for (int i = 0; i < classLoader.getURLs().length; i++) {
            URL url = classLoader.getURLs()[i];
            System.out.println(url);
        }
        System.out.println("=============================================================================\n\n");

        context = new ClassPathXmlApplicationContext(CONFIG_PATH);
        //register class to context to activate Spring-Autowired capability
        app = context.getBean(Application.class);
    }

    public void run() {
        //app.addRss();

        for (Rss person : app.selectAll()) {
            System.out.println(person);
        }
    }

    public void addRss() {
        Rss feed1, feed2, feed3, feed4, feed5, feed6;

        feed1 = new Rss("Inilah Bola","http://www.inilah.com/rss/feed/bola/");
        feed2 = new Rss("Google News","http://news.google.com/?output=rss");
        feed3 = new Rss("Yahoo News and Media","http://dir.yahoo.com/rss/dir/getrss.php?news");
        feed4 = new Rss("Yahoo Movie","http://dir.yahoo.com/rss/dir/getrss.php?ent_mov");
        feed5 = new Rss("CNN","http://rss.cnn.com/rss/edition.rss");
        feed6 = new Rss("Republika Online","http://www.republika.co.id/rss");

        service.save(feed1);
        service.save(feed2);
        service.save(feed3);
        service.save(feed4);
        service.save(feed5);
        service.save(feed6);
    }

    public List<Rss> selectAll() {
        return service.getAll();
    }

    public static void main(String[] args) {
        Application application = new Application();
        application.init();
        application.run();



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
