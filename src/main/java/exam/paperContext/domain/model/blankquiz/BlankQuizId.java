package exam.paperContext.domain.model.blankquiz;

import exam.paperContext.shared.AbstractUUIDBasedId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public final class BlankQuizId extends AbstractUUIDBasedId<BlankQuizId> {

    @Builder
    public BlankQuizId(String id) {
        this.id = id;
    }

    @Override
    public boolean sameValueAs(BlankQuizId other) {
        return this.equals(other);
    }

    public static BlankQuizId nextId() {
        return BlankQuizId.builder()
            .id(generateNextId())
            .build();
    }

    @Override
    public String toString() {
        return id;
    }
}
