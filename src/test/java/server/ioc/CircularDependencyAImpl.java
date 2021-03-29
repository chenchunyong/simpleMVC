package server.ioc;


import com.simplemvc.seaserver.annotation.ioc.Autowired;
import com.simplemvc.seaserver.annotation.ioc.Component;
import com.simplemvc.seaserver.annotation.value.Value;

@Component(name = "CircularDependencyAImpl")
public class CircularDependencyAImpl implements CircularDependencyA {
    @Value("test1.abc")
    public String languages;
    @Autowired
    private CircularDependencyB testB;

    @Override
    public void testA() {
        System.out.println("A");
        System.out.println(languages);
    }
}
