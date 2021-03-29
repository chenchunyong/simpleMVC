package demo.server.ioc;


import com.simplemvc.seaserver.annotation.ioc.Autowired;
import com.simplemvc.seaserver.annotation.ioc.Component;

@Component(name = "CircularDependencyAImpl")
public class CircularDependencyAImpl implements CircularDependencyA {

    @Autowired
    private CircularDependencyB testB;

    @Override
    public void testA() {
        System.out.println("A");
    }
}
