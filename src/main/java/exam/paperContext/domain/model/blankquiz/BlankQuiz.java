package exam.paperContext.domain.model.blankquiz;

import exam.paperContext.shared.AbstractEntity;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class BlankQuiz extends AbstractEntity<BlankQuiz> {

    private BlankQuizId blankQuizId;
    private String description;
    private String referenceAnswer;
    private Boolean deleted;

    @Builder
    public BlankQuiz(LocalDateTime createdTime, LocalDateTime updatedTime,
        BlankQuizId blankQuizId, String description, String referenceAnswer, Boolean deleted) {
        super(createdTime, updatedTime);
        this.blankQuizId = blankQuizId;
        this.description = description;
        this.referenceAnswer = referenceAnswer;
        this.deleted = deleted;
    }

    @Override
    public boolean sameIdentityAs(BlankQuiz other) {
        return this.blankQuizId.equals(other.blankQuizId);
    }

    public static BlankQuiz create(String description, String referenceAnswer, LocalDateTime createdTime) {
        return BlankQuiz.builder()
            .blankQuizId(BlankQuizId.nextId())
            .description(description)
            .referenceAnswer(referenceAnswer)
            .createdTime(createdTime)
            .updatedTime(createdTime)
            .deleted(Boolean.FALSE)
            .build();
    }

    public void update(String description, String referenceAnswer, LocalDateTime updatedTime) {
        this.description = description;
        this.referenceAnswer = referenceAnswer;
        this.updatedTime = updatedTime;
    }

    public void delete(LocalDateTime deletedTime) {
        this.deleted = Boolean.TRUE;

        this.updatedTime = deletedTime;
    }

    public boolean isValid() {
        return !this.deleted;
    }
}
