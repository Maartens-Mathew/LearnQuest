package com.example.learnquest.Utils.database;

import com.example.learnquest.model.wrappers.TaggedQuiz;

import java.util.List;
import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizCallback implements Callback<List<TaggedQuiz>> {

    private final Consumer<List<TaggedQuiz>> onSuccess;
    private final Consumer<Throwable> onFailure;

    public QuizCallback(Consumer<List<TaggedQuiz>> onSuccess, Consumer<Throwable> onFailure) {
        this.onSuccess = onSuccess;
        this.onFailure = onFailure;
    }

    @Override
    public void onResponse(Call<List<TaggedQuiz>> call, Response<List<TaggedQuiz>> response) {
        if (response.isSuccessful() && response.body() != null) {
            onSuccess.accept(response.body());
        } else {
            onFailure.accept(new Exception("Something went wrong."));
        }
    }

    @Override
    public void onFailure(Call<List<TaggedQuiz>> call, Throwable t) {
        onFailure.accept(t);
    }
}

