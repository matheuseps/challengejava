import com.example.model.Problem;
import com.example.repository.ProblemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProblemService {

    @Autowired
    private ProblemRepository problemRepository;

    public List<Problem> getAllProblems() {
        return problemRepository.findAll();
    }

    public Problem getProblemById(Long id) {
        return problemRepository.findById(id).orElse(null);
    }

    public Problem createProblem(Problem problem) {
        return problemRepository.save(problem);
    }

    public Problem updateProblem(Long id, Problem problem) {
        Problem existingProblem = getProblemById(id);
        if (existingProblem == null) {
            return null;
        }
        existingProblem.setName(problem.getName());
        existingProblem.setDescription(problem.getDescription());
        existingProblem.setCreatedAt(problem.getCreatedAt());
        existingProblem.setUpdatedAt(problem.getUpdatedAt());
        return problemRepository.save(existingProblem);
    }

    public void deleteProblem(Long id) {
        problemRepository.deleteById(id);
    }
}