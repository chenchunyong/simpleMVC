package server.aop;

import com.simplemvc.seaserver.annotation.ioc.Component;
import lombok.extern.slf4j.Slf4j;

@Component(name = "StudentServiceImpl")
@Slf4j
public class StudentServiceImpl implements StudentService {
    @Override
    public String getSummary(String id) {
        log.info("i am a good student!");
        return "i am a good student!";
    }
}
