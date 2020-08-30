package exam.paperContext.infrastructure;

import exam.paperContext.shared.ValueObject;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class InMemoryRepository<I, T> {
    private LinkedHashMap<ValueObject<?>, Object> MOCKED_DB = new LinkedHashMap<>();

    public Optional<T> findById(ValueObject<I> id) {
        return Optional.ofNullable((T)MOCKED_DB.get(id));
    }

    public List<T> findAll() {
        return MOCKED_DB.values().stream().map(x -> (T)x).collect(Collectors.toList());
    }

    public void save(ValueObject<I> id, T record) {
        MOCKED_DB.put(id, record);
    }
}
