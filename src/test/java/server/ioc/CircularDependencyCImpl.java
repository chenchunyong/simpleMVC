package server.ioc;

import com.simplemvc.seaserver.annotation.ioc.Autowired;
import com.simplemvc.seaserver.annotation.ioc.Component;

@Component(name = "CircularDependencyCImpl")
public class CircularDependencyCImpl implements CircularDependencyC {

    @Autowired
    private CircularDependencyA testA;

    @Override
    public void testC() {
        System.out.println("C");
    }
}
