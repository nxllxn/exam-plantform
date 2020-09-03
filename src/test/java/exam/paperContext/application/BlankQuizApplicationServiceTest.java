package exam.paperContext.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import exam.paperContext.domain.model.blankquiz.BlankQuiz;
import exam.paperContext.domain.model.blankquiz.BlankQuizId;
import exam.paperContext.domain.model.paper.ResourceNotFoundException;
import exam.paperContext.infrastructure.BlankQuizRepositoryImpl;
import exam.paperContext.infrastructure.Clock;
import java.time.LocalDateTime;
import java.util.Optional;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class BlankQuizApplicationServiceTest {

    @Mock
    private BlankQuizRepositoryImpl repository;

    @Mock
    private Clock clock;

    @InjectMocks
    private BlankQuizApplicationService applicationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldCreateBlankQuizWithCorrectIdAndInformation() {
        LocalDateTime createdTime = LocalDateTime.now();
        BlankQuizId blankQuizId = BlankQuizId.nextId();
        String description = RandomStringUtils.randomAlphabetic(32);
        String referenceAnswer = RandomStringUtils.randomAlphabetic(32);

        when(clock.now()).thenReturn(createdTime);
        when(repository.nextId()).thenReturn(blankQuizId);
        when(repository.findById(any(BlankQuizId.class))).thenReturn(Optional.empty());

        applicationService.create(description, referenceAnswer);

        verify(clock, times(1)).now();
        verify(repository, times(1)).nextId();
        verify(repository, times(1)).save(
            BlankQuiz.builder()
                .blankQuizId(blankQuizId)
                .description(description)
                .referenceAnswer(referenceAnswer)
                .deleted(Boolean.FALSE)
                .createdTime(createdTime)
                .updatedTime(createdTime)
                .build()
        );
    }

    @Test
    void shouldGotResourceNotFoundExceptionGivenBlankQuizWithSpecifiedIdDoNotExists() {
        BlankQuizId blankQuizId = BlankQuizId.nextId();
        String description = RandomStringUtils.randomAlphabetic(32);
        String referenceAnswer = RandomStringUtils.randomAlphabetic(32);

        when(repository.find(any(BlankQuizId.class))).thenReturn(Optional.empty());

        ResourceNotFoundException resourceNotFoundException = assertThrows(ResourceNotFoundException.class,
            () -> applicationService.update(blankQuizId, description, referenceAnswer));
        assertEquals(resourceNotFoundException.getMessage(),
            String.format("Resource [%s] with id [%s] not found", "BlankQuiz", blankQuizId.toString()));

        verify(repository, times(1)).find(blankQuizId);
    }

    @Test
    void shouldSuccessfullyUpdateBlankQuizGivenAnExistedBlankQuizID() {
        BlankQuizId blankQuizId = BlankQuizId.nextId();
        String description = RandomStringUtils.randomAlphabetic(32);
        String referenceAnswer = RandomStringUtils.randomAlphabetic(32);
        LocalDateTime updatedTime = LocalDateTime.now();

        BlankQuiz blankQuiz = BlankQuiz.builder().blankQuizId(blankQuizId).build();

        BlankQuiz updateTo = BlankQuiz.builder().blankQuizId(blankQuizId).description(description)
            .referenceAnswer(referenceAnswer).updatedTime(updatedTime).build();

        when(clock.now()).thenReturn(updatedTime);
        when(repository.find(any(BlankQuizId.class))).thenReturn(Optional.of(blankQuiz));
        doNothing().when(repository).save(blankQuizId, updateTo);

        applicationService.update(blankQuizId, description, referenceAnswer);

        verify(repository, times(1)).find(blankQuizId);
        verify(repository, times(1)).save(blankQuizId, updateTo);
    }
}