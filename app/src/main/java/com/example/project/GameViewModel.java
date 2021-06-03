package com.example.project;

import android.os.CountDownTimer;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GameViewModel extends ViewModel {

    private CountDownTimer timer;
    private int correctAnswerIndex;
    private int countAnswers = 0;
    private int countRight = 0;
    private int secondsLeft = 0;
    private List<Question> questions;
    private Random random = new Random();

    public GameViewModel() {
        loadQuestions();
    }

    private MutableLiveData<String> score = new MutableLiveData<>("0 / 0");
    public LiveData<String> getScore() {
        return score;
    }

    private MutableLiveData<String> time = new MutableLiveData<String>("00:00");
    public LiveData<String> getTime() {
        return time;
    }

    private MutableLiveData<Question> current = new MutableLiveData<>();
    public LiveData<Question> getCurrent() {
        return current;
    }

    private MutableLiveData<List<Question>> answers = new MutableLiveData<List<Question>>();
    public LiveData<List<Question>> getAnswers() {
        return answers;
    }

    private MutableLiveData<GameStatus> status = new MutableLiveData<>(GameStatus.LOADING);
    public LiveData<GameStatus> getStatus() {
        return status;
    }

    public int getCountRight() {
        return countRight;
    }

    public void start() {
        if (status.getValue() == GameStatus.GAME) {
            return;
        }
        countAnswers = 0;
        countRight = 0;
        updateScore();
        reset();
        setSecondsLeft(30);
        nextQuestion();
        timer = new CountDownTimer(3600*1000, 1000) {
            @Override
            public void onTick(long l) {
                changeSecondsLeft(-1);
            }

            @Override
            public void onFinish() {
            }
        };
        timer.start();
        status.setValue(GameStatus.GAME);
    }

    private void changeSecondsLeft(int i) {
        int next = secondsLeft + i;
        if (next <= 0) {
            gameOver();
        } else {
            setSecondsLeft(next);
        }
    }

    private void reset() {
        setSecondsLeft(0);
        if (timer != null) {
            timer.cancel();
        }
        timer = null;
    }

    private void gameOver() {
        reset();
        status.setValue(GameStatus.END);
    }

    private void setSecondsLeft(int i) {
        secondsLeft = Math.max(0, i);
        updateTime();
    }

    private void updateTime() {
        int m = secondsLeft / 60;
        int s = secondsLeft % 60;
        time.setValue(String.format("%02d:%02d", m, s));
    }

    void nextQuestion() {
        int randomIndex = random.nextInt(questions.size());
        current.setValue(questions.get(randomIndex));

        generateAnswers(randomIndex);
    }

    private void generateAnswers(int questionIndex) {
        List<Question> list = new ArrayList<Question>();
        correctAnswerIndex = random.nextInt(6);

        for (int i = 0; i < 6; i++) {
            if (i == correctAnswerIndex) {
                list.add(current.getValue());
            } else {
                int randomIndex = questionIndex;
                while (randomIndex == questionIndex) {
                    randomIndex = random.nextInt(questions.size());
                }
                list.add(questions.get(randomIndex));
            }
        }
        answers.setValue(list);
    }

    public void checkAnswer(int index) {
        if (status.getValue() == GameStatus.END) {
            return;
        }

        countAnswers++;
        if (index == correctAnswerIndex) {
            countRight++;
            changeSecondsLeft(5);
        } else {
            changeSecondsLeft(-5);
        }
        updateScore();
        nextQuestion();
    }

    private void updateScore() {
        score.setValue(String.format("%d / %d", countRight, countAnswers));
    }

    public void loadQuestions() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://200-famous-people.deno.dev/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        QuestionService api = retrofit.create((QuestionService.class));
        Call<List<Question>> call = api.getAll();
        call.enqueue(new Callback<List<Question>>() {
            @Override
            public void onResponse(Call<List<Question>> call, Response<List<Question>> response) {
                questions = response.body();
                start();
            }

            @Override
            public void onFailure(Call<List<Question>> call, Throwable t) {
                status.setValue(GameStatus.FAIL);
            }
        });
    }
}
