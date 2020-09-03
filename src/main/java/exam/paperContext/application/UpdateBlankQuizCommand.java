package exam.paperContext.application;

import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UpdateBlankQuizCommand extends BlankQuizCommand {

    @Builder
    public UpdateBlankQuizCommand(String description, String referenceAnswer) {
        super(description, referenceAnswer);
    }
}
