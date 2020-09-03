package exam.paperContext.application;

import exam.paperContext.domain.model.blankquiz.BlankQuiz;
import exam.paperContext.domain.model.blankquiz.BlankQuizId;
import exam.paperContext.domain.model.blankquiz.BlankQuizRepository;
import exam.paperContext.domain.model.paper.ResourceNotFoundException;
import exam.paperContext.infrastructure.Clock;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlankQuizApplicationService {
    private final Clock clock;
    private final BlankQuizRepository blankQuizRepository;

    public BlankQuiz find(BlankQuizId blankQuizId) {
        Optional<BlankQuiz> blankQuizOpt = blankQuizRepository.find(blankQuizId);

        if (!blankQuizOpt.isPresent()) {
            throw new ResourceNotFoundException("BlankQuiz", blankQuizId.toString());
        }

        return blankQuizOpt.get();
    }

    public BlankQuiz create(String description, String referenceAnswer) {
        BlankQuizId blankQuizId = blankQuizRepository.nextId();

        BlankQuiz blankQuiz = BlankQuiz.create(blankQuizId,description, referenceAnswer, clock.now());

        blankQuizRepository.save(blankQuiz);

        return blankQuiz;
    }

    public void update(BlankQuizId blankQuizId, String description, String referenceAnswer) {
        Optional<BlankQuiz> blankQuizOpt = blankQuizRepository.find(blankQuizId);

        if (!blankQuizOpt.isPresent()) {
            throw new ResourceNotFoundException("BlankQuiz", blankQuizId.toString());
        }

        BlankQuiz blankQuizPersisted = blankQuizOpt.get();

        blankQuizPersisted.update(description, referenceAnswer, clock.now());

        blankQuizRepository.save(blankQuizPersisted);
    }

    public List<BlankQuiz> getAll() {
        return blankQuizRepository.getAll();
    }

    public void delete(BlankQuizId blankQuizId) {
        Optional<BlankQuiz> blankQuizOpt = blankQuizRepository.find(blankQuizId);

        if (!blankQuizOpt.isPresent()) {
            throw new ResourceNotFoundException("BlankQuiz", blankQuizId.toString());
        }

        BlankQuiz blankQuizPersisted = blankQuizOpt.get();

        blankQuizPersisted.delete(clock.now());

        blankQuizRepository.save(blankQuizPersisted);
    }
}
