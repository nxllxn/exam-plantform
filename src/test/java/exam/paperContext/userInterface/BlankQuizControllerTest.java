package exam.paperContext.userInterface;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import exam.exception.GlobalControllerAdvice;
import exam.paperContext.application.BlankQuizApplicationService;
import exam.paperContext.application.BlankQuizCommand;
import exam.paperContext.application.CreateBlankQuizCommand;
import exam.paperContext.application.CreateBlankQuizCommand.CreateBlankQuizCommandBuilder;
import exam.paperContext.application.UpdateBlankQuizCommand;
import exam.paperContext.application.UpdateBlankQuizCommand.UpdateBlankQuizCommandBuilder;
import exam.paperContext.domain.model.blankquiz.BlankQuiz;
import exam.paperContext.domain.model.blankquiz.BlankQuizId;
import java.time.LocalDateTime;
import java.util.stream.Stream;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class BlankQuizControllerTest {

    @Mock
    private BlankQuizApplicationService blankQuizApplicationService;

    @InjectMocks
    private BlankQuizController blankQuizController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(blankQuizController).setControllerAdvice(new GlobalControllerAdvice())
            .build();

        objectMapper = new ObjectMapper();
    }

    @ParameterizedTest
    @MethodSource("invalidBlankQuizCreationSource")
    void shouldGot400ErrorGivenAnInvalidBlankQuizInfo(BlankQuizCommand createBlankQuizCommand, String errorMsg)
        throws Exception {
        mockMvc.perform(post("/blank-quizzes").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(createBlankQuizCommand)))
            .andExpect(status().isBadRequest())
            .andExpect(content().string(errorMsg));

        verify(blankQuizApplicationService, times(0)).create(anyString(), anyString());
    }

    public static Stream<Arguments> invalidBlankQuizCreationSource() {
        CreateBlankQuizCommandBuilder createBlankQuizCommandBuilder = CreateBlankQuizCommand.builder()
            .description(RandomStringUtils.randomAlphabetic(32))
            .referenceAnswer(RandomStringUtils.randomAlphabetic(32));

        return Stream.of(
            Arguments.arguments(
                createBlankQuizCommandBuilder.description(null).build(), "Description cannot be empty"
            ),
            Arguments.arguments(
                createBlankQuizCommandBuilder.description(RandomStringUtils.randomAlphabetic(101)).build(),
                "Description length cannot be greater than 100"
            ),
            Arguments.arguments(
                createBlankQuizCommandBuilder.description(RandomStringUtils.randomAlphabetic(32)).referenceAnswer(null)
                    .build(), "Reference answer cannot be empty"
            ),
            Arguments.arguments(
                createBlankQuizCommandBuilder.referenceAnswer(RandomStringUtils.randomAlphabetic(101)).build(),
                "Reference answer length cannot be greater than 100"
            )
        );
    }

    @Test
    void shouldCreateNewBlankQuizGivenValidBlankQuizInfo() throws Exception {
        CreateBlankQuizCommand createBlankQuizCommand = CreateBlankQuizCommand.builder()
            .description(RandomStringUtils.randomAlphabetic(32))
            .referenceAnswer(RandomStringUtils.randomAlphabetic(32))
            .build();

        when(blankQuizApplicationService.create(anyString(), anyString()))
            .thenReturn(
                BlankQuiz.builder()
                    .blankQuizId(BlankQuizId.nextId())
                    .description(createBlankQuizCommand.getDescription())
                    .referenceAnswer(createBlankQuizCommand.getReferenceAnswer())
                    .createdTime(LocalDateTime.now())
                    .updatedTime(LocalDateTime.now())
                    .deleted(Boolean.FALSE)
                    .build()
            );

        mockMvc.perform(post("/blank-quizzes").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(createBlankQuizCommand)))
            .andExpect(jsonPath("description").value(createBlankQuizCommand.getDescription()))
            .andExpect(jsonPath("referenceAnswer").value(createBlankQuizCommand.getReferenceAnswer()));

        verify(blankQuizApplicationService, times(1)).create(anyString(), anyString());
    }

    @ParameterizedTest
    @MethodSource("invalidBlankQuizUpdateSource")
    void shouldGot400ErrorGivenAnInvalidBlankQuizInfoWhileUpdating(String blankQuizId,
        BlankQuizCommand updateBlankQuizCommand,
        String errorMsg)
        throws Exception {
        mockMvc.perform(put("/blank-quizzes/" + blankQuizId).contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updateBlankQuizCommand)))
            .andExpect(status().isBadRequest())
            .andExpect(content().string(errorMsg));

        verify(blankQuizApplicationService, times(0)).create(anyString(), anyString());
    }

    public static Stream<Arguments> invalidBlankQuizUpdateSource() {
        UpdateBlankQuizCommandBuilder updateBlankQuizCommandBuilder = UpdateBlankQuizCommand.builder()
            .description(RandomStringUtils.randomAlphabetic(32))
            .referenceAnswer(RandomStringUtils.randomAlphabetic(32));

        return Stream.of(
            Arguments.arguments(
                RandomStringUtils.randomNumeric(8),
                updateBlankQuizCommandBuilder.description(null).build(), "Description cannot be empty"
            ),
            Arguments.arguments(
                RandomStringUtils.randomNumeric(8),
                updateBlankQuizCommandBuilder.description(RandomStringUtils.randomAlphabetic(101)).build(),
                "Description length cannot be greater than 100"
            ),
            Arguments.arguments(
                RandomStringUtils.randomNumeric(8),
                updateBlankQuizCommandBuilder.description(RandomStringUtils.randomAlphabetic(32)).referenceAnswer(null)
                    .build(), "Reference answer cannot be empty"
            ),
            Arguments.arguments(
                RandomStringUtils.randomNumeric(8),
                updateBlankQuizCommandBuilder.referenceAnswer(RandomStringUtils.randomAlphabetic(101)).build(),
                "Reference answer length cannot be greater than 100"
            )
        );
    }

    @Test
    void shouldUpdateBlankQuizGivenValidBlankQuizInfo() throws Exception {
        String blankQuizId = RandomStringUtils.randomNumeric(8);

        UpdateBlankQuizCommand updateBlankQuizCommand = UpdateBlankQuizCommand.builder()
            .description(RandomStringUtils.randomAlphabetic(32))
            .referenceAnswer(RandomStringUtils.randomAlphabetic(32))
            .build();

        doNothing().when(blankQuizApplicationService).update(any(BlankQuizId.class), anyString(), anyString());

        mockMvc.perform(put("/blank-quizzes/" + blankQuizId).contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updateBlankQuizCommand)))
            .andExpect(status().isNoContent());

        verify(blankQuizApplicationService, times(1))
            .update(BlankQuizId.builder().id(blankQuizId).build(), updateBlankQuizCommand.getDescription(),
                updateBlankQuizCommand.getReferenceAnswer());
    }

    @Test
    void shouldDeleteBlankQuizGivenValidBlankQuizInfo() throws Exception {
        String blankQuizId = RandomStringUtils.randomNumeric(8);

        doNothing().when(blankQuizApplicationService).delete(any(BlankQuizId.class));

        mockMvc.perform(delete("/blank-quizzes/" + blankQuizId).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        verify(blankQuizApplicationService, times(1))
            .delete(BlankQuizId.builder().id(blankQuizId).build());
    }
}