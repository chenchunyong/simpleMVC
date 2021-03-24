package demo.server;

import com.simplemvc.seaserver.annotation.boot.ComponentScan;
import com.simplemvc.seaserver.annotation.boot.StartApplication;
import com.simplemvc.seaserver.core.ApplicationContext;

@StartApplication
@ComponentScan({"demo.server.ioc"})
public class DemoApplication {
    public static void main(String[] args) {
        ApplicationContext applicationContext = ApplicationContext.getApplicationContext();
        applicationContext.run(DemoApplication.class);
    }

}