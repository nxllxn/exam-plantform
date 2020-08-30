package exam.paperContext.application;

public class UpdateBlankQuizCommand extends BlankQuizCommand {

    public UpdateBlankQuizCommand(String description, String referenceAnswer) {
        super(description, referenceAnswer);
    }
}
