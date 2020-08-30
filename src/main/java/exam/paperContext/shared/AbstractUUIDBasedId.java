package exam.paperContext.shared;

import java.util.UUID;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public abstract class AbstractUUIDBasedId<T> implements ValueObject<T> {
    protected String id;

    public static String generateNextId() {
        return UUID.randomUUID().toString();
    }

    @Override
    public boolean sameValueAs(T other) {
        return equals(other);
    }
}
