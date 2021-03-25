package demo.server.ioc;

import com.simplemvc.seaserver.annotation.ioc.Autowired;
import com.simplemvc.seaserver.annotation.ioc.Component;

@Component
public class testComponent {
    @Autowired
    private TestBService service;
}
