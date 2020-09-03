package exam.paperContext.userInterface;

import exam.paperContext.application.BlankQuizApplicationService;
import exam.paperContext.application.CreateBlankQuizCommand;
import exam.paperContext.application.UpdateBlankQuizCommand;
import exam.paperContext.domain.model.blankquiz.BlankQuiz;
import exam.paperContext.domain.model.blankquiz.BlankQuizId;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/blank-quizzes")
@RestController
@RequiredArgsConstructor
public class BlankQuizController {

    private final BlankQuizApplicationService applicationService;

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    BlankQuizDTO create(@Valid @RequestBody CreateBlankQuizCommand command) {
        BlankQuiz blankQuiz = applicationService.create(command.getDescription(), command.getReferenceAnswer());

        return BlankQuizDTO.from(blankQuiz);
    }

    @PutMapping("/{blankQuizIdStr}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void update(@PathVariable String blankQuizIdStr,@Valid @RequestBody UpdateBlankQuizCommand command) {
        BlankQuizId blankQuizId = BlankQuizId.builder().id(blankQuizIdStr).build();

        applicationService.update(blankQuizId, command.getDescription(), command.getReferenceAnswer());
    }

    @DeleteMapping("/{blankQuizIdStr}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable String blankQuizIdStr) {
        BlankQuizId blankQuizId = BlankQuizId.builder().id(blankQuizIdStr).build();

        applicationService.delete(blankQuizId);
    }

    @GetMapping
    List<BlankQuizDTO> getAll() {
        return applicationService.getAll().stream().map(BlankQuizDTO::from).collect(Collectors.toList());
    }
}
