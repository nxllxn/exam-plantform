package exam.paperContext.infrastructure;

import exam.paperContext.domain.model.blankquiz.BlankQuiz;
import exam.paperContext.domain.model.blankquiz.BlankQuizId;
import exam.paperContext.domain.model.blankquiz.BlankQuizRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class BlankQuizRepositoryImpl extends InMemoryRepository<BlankQuizId, BlankQuiz> implements BlankQuizRepository {

    @Override
    public BlankQuizId nextId() {
        return BlankQuizId.nextId();
    }

    @Override
    public Optional<BlankQuiz> find(BlankQuizId blankQuizId) {
        return findById(blankQuizId).filter(BlankQuiz::isValid);
    }

    @Override
    public void save(BlankQuiz blankQuiz) {
        save(blankQuiz.getBlankQuizId(), blankQuiz);
    }

    @Override
    public List<BlankQuiz> getAll() {
        return findAll().stream().filter(BlankQuiz::isValid).collect(Collectors.toList());
    }
}
