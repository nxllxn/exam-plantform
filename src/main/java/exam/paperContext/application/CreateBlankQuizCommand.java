package exam.paperContext.application;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CreateBlankQuizCommand extends BlankQuizCommand {
    @Builder
    public CreateBlankQuizCommand(String description, String referenceAnswer) {
        super(description, referenceAnswer);
    }
}
