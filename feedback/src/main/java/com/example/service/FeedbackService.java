import com.example.model.Feedback;
import com.example.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    public List<Feedback> getAllFeedbacks() {
        return feedbackRepository.findAll();
    }

    public Feedback getFeedbackById(Long id) {
        return feedbackRepository.findById(id).orElse(null);
    }

    public Feedback createFeedback(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    public Feedback updateFeedback(Long id, Feedback feedback) {
        Feedback existingFeedback = getFeedbackById(id);
        if (existingFeedback == null) {
            return null;
        }
        existingFeedback.setName(feedback.getName());
        existingFeedback.setDescription(feedback.getDescription());
        existingFeedback.setCreatedAt(feedback.getCreatedAt());
        existingFeedback.setUpdatedAt(feedback.getUpdatedAt());
        return feedbackRepository.save(existingFeedback);
    }

    public void deleteFeedback(Long id) {
        feedbackRepository.deleteById(id);
    }
}