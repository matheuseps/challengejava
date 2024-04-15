import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.model.Feedback;
import com.example.repository.FeedbackRepository;
import com.example.service.FeedbackService;
import com.example.web.FeedbackController;

import static org.mockito.Mockito.when;

@WebMvcTest(FeedbackController.class)
public class FeedbackControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FeedbackRepository feedbackRepository;

    @MockBean
    private FeedbackService feedbackService;

    @Test
    public void testGetFeedbacks() throws Exception {
        when(feedbackService.findAll()).thenReturn(feedbackRepository.findAll());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/feedbacks")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Feedback 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Feedback 2"));
    }

    @Test
    public void testCreateFeedback() throws Exception {
        Feedback feedback = new Feedback();
        feedback.setDescription("This is a test feedback.");

        when(feedbackService.save(feedback)).thenReturn(feedbackRepository.save(feedback));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/feedbacks")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Test Feedback\",\"description\":\"This is a test feedback.\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Feedback"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("This is a test feedback."));
    }

    @Test
    public void testGetFeedback() throws Exception {
        Feedback feedback = new Feedback();
        feedback.setId(1L);
        feedback.setName("Feedback 1");
        feedback.setDescription("This is feedback 1.");

        when(feedbackRepository.findById(1L)).thenReturn(java.util.Optional.of(feedback));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/feedbacks/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Feedback 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("This is feedback 1."));
    }

    @Test
    public void testUpdateFeedback() throws Exception {
        Feedback feedback = new Feedback();
        feedback.setId(1L);
        feedback.setName("Feedback 1");
        feedback.setDescription("This is feedback 1.");

        when(feedbackRepository.findById(1L)).thenReturn(java.util.Optional.of(feedback));
        when(feedbackService.save(feedback)).thenReturn(feedbackRepository.save(feedback));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/feedbacks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Feedback 1 Updated\",\"description\":\"This is feedback 1 updated.\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Feedback 1 Updated"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("This is feedback 1 updated."));
    }
}