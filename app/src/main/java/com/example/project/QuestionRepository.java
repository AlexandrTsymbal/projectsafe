package com.example.project;

import java.util.List;

import retrofit2.Call;

public class QuestionRepository {
    private QuestionService questionService;

    public QuestionRepository(QuestionService questionService) {
        this.questionService = questionService;
    }

    public Call<List<Question>> getQuestions(String id) {
        return questionService.getAll();
    }
}
