package com.example.live.problem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/problems")
public class ProblemController {

    @Autowired
    private ProblemService problemService;

    @GetMapping
    public ResponseEntity<List<Problem>> getAllProblems() {
        List<Problem> problems = problemService.getAllProblems();
        return new ResponseEntity<>(problems, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Problem> createProblem(@Valid @RequestBody Problem problem) {
        Problem newProblem = problemService.createProblem(problem);
        return new ResponseEntity<>(newProblem, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Problem> getProblemById(@PathVariable("id") Long problemId) {
        Problem problem = problemService.getProblemById(problemId);
        return new ResponseEntity<>(problem, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Problem> updateProblem(@PathVariable("id") Long problemId, @Valid @RequestBody Problem problemDetails) {
        Problem updatedProblem = problemService.updateProblem(problemId, problemDetails);
        return new ResponseEntity<>(updatedProblem, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProblem(@PathVariable("id") Long problemId) {
        problemService.deleteProblem(problemId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}