package exam.paperContext.application;

public class CreateBlankQuizCommand extends BlankQuizCommand {

    public CreateBlankQuizCommand(String description, String referenceAnswer) {
        super(description, referenceAnswer);
    }
}
