package exam.paperContext.userInterface;

import exam.paperContext.domain.model.blankquiz.BlankQuiz;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlankQuizDTO {
    private String id;
    private String description;
    private String referenceAnswer;
    protected LocalDateTime createdTime;
    protected LocalDateTime updatedTime;

    public static BlankQuizDTO from(BlankQuiz blankQuiz) {
        return BlankQuizDTO.builder()
            .id(blankQuiz.getBlankQuizId().toString())
            .description(blankQuiz.getDescription())
            .referenceAnswer(blankQuiz.getReferenceAnswer())
            .createdTime(blankQuiz.getCreatedTime())
            .updatedTime(blankQuiz.getUpdatedTime())
            .build();
    }
}
