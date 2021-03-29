package demo.server.ioc;


import com.simplemvc.seaserver.annotation.ioc.Autowired;
import com.simplemvc.seaserver.annotation.ioc.Component;

@Component(name = "CircularDependencyBImpl")
public class CircularDependencyBImpl implements CircularDependencyB {

    @Autowired
    private CircularDependencyC testC;

    @Override
    public void testB() {
        System.out.println("B");
    }
}
