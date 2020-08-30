package exam.paperContext.application;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BlankQuizCommand {
    private String description;
    private String referenceAnswer;
}