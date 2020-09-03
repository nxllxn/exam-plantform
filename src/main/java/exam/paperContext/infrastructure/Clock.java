package exam.paperContext.infrastructure;

import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class Clock {

    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}
