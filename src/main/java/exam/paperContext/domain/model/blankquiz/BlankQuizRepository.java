package exam.paperContext.domain.model.blankquiz;

import java.util.List;
import java.util.Optional;

public interface BlankQuizRepository {
    BlankQuizId nextId();

    Optional<BlankQuiz> find(BlankQuizId blankQuizId);

    void save(BlankQuiz blankQuiz);

    List<BlankQuiz> getAll();
}
