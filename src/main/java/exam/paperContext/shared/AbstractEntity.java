package exam.paperContext.shared;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractEntity<T> implements Entity<T> {
    protected LocalDateTime createdTime;
    protected LocalDateTime updatedTime;
}
