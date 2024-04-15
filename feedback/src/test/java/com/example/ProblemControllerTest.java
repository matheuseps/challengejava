import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.model.Problem;
import com.example.repository.ProblemRepository;
import com.example.service.ProblemService;
import com.example.web.ProblemController;

import static org.mockito.Mockito.when;

@WebMvcTest(ProblemController.class)
public class ProblemControllerTest {

    @Autowired
private MockMvc mockMvc;

    @MockBean
    private ProblemRepository problemRepository;

    @MockBean
    private ProblemService problemService;

    @Test
    public void testGetProblems() throws Exception {
        when(problemService.findAll()).thenReturn(problemRepository.findAll());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/problems")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Problem 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Problem 2"));
    }

    @Test
    public void testCreateProblem() throws Exception {
        Problem problem = new Problem();
        problem.setDescription("This is a test problem.");

        when(problemService.save(problem)).thenReturn(problemRepository.save(problem));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/problems")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Test Problem\",\"description\":\"This is a test problem.\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Problem"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("This is a test problem."));
    }

    @Test
    public void testGetProblem() throws Exception {
        Problem problem = new Problem();
        problem.setId(1L);
        problem.setName("Problem 1");
        problem.setDescription("This is problem 1.");

        when(problemRepository.findById(1L)).thenReturn(java.util.Optional.of(problem));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/problems/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Problem 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("This is problem 1."));
    }

    @Test
    public void testUpdateProblem() throws Exception {
        Problem problem = new Problem();
        problem.setId(1L);
        problem.setName("Problem 1");
        problem.setDescription("This is problem 1.");

        when(problemRepository.findById(1L)).thenReturn(java.util.Optional.of(problem));
        when(problemService.save(problem)).thenReturn(problemRepository.save(problem));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/problems/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Problem 1 Updated\",\"description\":\"This is problem 1 updated.\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Problem 1 Updated"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("This is problem 1 updated."));
    }
}