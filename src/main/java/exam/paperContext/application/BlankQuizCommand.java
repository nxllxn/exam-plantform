package exam.paperContext.application;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlankQuizCommand {

    @NotBlank(message = "Description cannot be empty")
    @Length(max = 100, message = "Description length cannot be greater than 100")
    private String description;
    @NotBlank(message = "Reference answer cannot be empty")
    @Length(max = 100, message = "Reference answer length cannot be greater than 100")
    private String referenceAnswer;
}