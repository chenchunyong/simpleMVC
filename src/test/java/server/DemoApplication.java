package server;

import com.simplemvc.seaserver.annotation.boot.ComponentScan;
import com.simplemvc.seaserver.annotation.boot.StartApplication;
import com.simplemvc.seaserver.core.ApplicationContext;
import com.simplemvc.seaserver.core.ioc.BeanFactory;
import server.ioc.CircularDependencyA;

@StartApplication
@ComponentScan({"server.ioc"})
public class DemoApplication {
    public static void main(String[] args) {
        ApplicationContext applicationContext = ApplicationContext.getApplicationContext();
        applicationContext.run(DemoApplication.class);

        CircularDependencyA bean = BeanFactory.getBean(CircularDependencyA.class);
        bean.testA();
    }

}