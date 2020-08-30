package exam.paperContext.domain.model.paper;

import exam.paperContext.shared.AbstractUUIDBasedId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaperId extends AbstractUUIDBasedId<PaperId> {

    @Builder
    public PaperId(String id) {
        this.id = id;
    }

    public static PaperId nextId() {
        return PaperId.builder()
            .id(generateNextId())
            .build();
    }
}
