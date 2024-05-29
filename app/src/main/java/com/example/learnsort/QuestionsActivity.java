package com.example.learnsort;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.CountDownTimer;

import androidx.core.content.ContextCompat;


import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Collections;
import java.util.List;

public class QuestionsActivity extends AppCompatActivity {

    Button ansA,ansB,ansC,ansD;
    String timerASD;
    TextView question,questionIndicator;
    List<QuestionModel> questionsList;
    int currentQuestion = 0;
    int correct=0,wrong=0;
    Intent intent;
    LinearProgressIndicator progressIndicator;
    ProgressBar progressBar;
    private TextView timeTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);


        question=findViewById(R.id.question_textview);
        questionIndicator=findViewById(R.id.question_indicator_textview);
        ansA=findViewById(R.id.ansA);
        ansB=findViewById(R.id.ansB);
        ansC=findViewById(R.id.ansC);
        ansD=findViewById(R.id.ansD);
        progressIndicator = findViewById(R.id.question_progress_indicator);
        progressBar = findViewById(R.id.progressBar);

        progressBar.setVisibility(View.VISIBLE);

        intent = getIntent();
        String quizID=intent.getStringExtra("quizID");

        if (quizID == null)
        {
            Toast.makeText(QuestionsActivity.this,"Nu am gasit quiz-ul!",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(QuestionsActivity.this,QuizActivity.class));
            finish();
        }
        retrieveQuizData(quizID);


    }

    private void startTimer() {
        timeTextView=findViewById(R.id.timer_indicator_textview);
        long totalTimeInMillis = Integer.parseInt(timerASD) * 60 * 1000L;
        new CountDownTimer(totalTimeInMillis, 1000L) {
            @Override
            public void onTick(long millisUntilFinished) {
                long seconds = millisUntilFinished / 1000;
                long minutes = seconds / 60;
                long remainingSeconds = seconds % 60;
                String timeText = String.format("%02d:%02d", minutes, remainingSeconds);
                timeTextView.setText(timeText);
            }

            @Override
            public void onFinish() {
                startActivity(new Intent(QuestionsActivity.this,QuizActivity.class));
                finish();
            }
        }.start();
    }

    private void nextQuestion() {
        if (currentQuestion < questionsList.size()) {
            QuestionModel current = questionsList.get(currentQuestion);
            question.setText(current.getQuestion());
            List<String> options = current.getOptions();
            Collections.shuffle(options);

            ansA.setText(options.get(0));
            ansB.setText(options.get(1));
            ansC.setText(options.get(2));
            ansD.setText(options.get(3));

            String questionIndicatorText = "Intrebarea " + (currentQuestion + 1) + "/" + questionsList.size();
            questionIndicator.setText(questionIndicatorText);

            // Update question progress
            progressIndicator.setProgress(currentQuestion + 1, true);


            ansA.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkAnswer(ansA.getText().toString(), currentQuestion);
                }
            });

            ansB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkAnswer(ansB.getText().toString(), currentQuestion);
                }
            });

            ansC.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkAnswer(ansC.getText().toString(), currentQuestion);
                }
            });

            ansD.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkAnswer(ansD.getText().toString(), currentQuestion);
                }
            });

        } else {
            result();
        }
    }

    private void retrieveQuizData(String quizID) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("quizzes").child(quizID);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                QuizModel quizModel = dataSnapshot.getValue(QuizModel.class);
                if (quizModel != null) {
                    questionsList=quizModel.getQuestionList();
                    timerASD=quizModel.getTime();
                    progressIndicator.setMax(questionsList.size()+1);
                    startTimer();
                    nextQuestion();
                    progressBar.setVisibility(View.GONE);
                } else {
                    Toast.makeText(QuestionsActivity.this, "Quiz data is null.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(QuestionsActivity.this, "Failed to load quiz data.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkAnswer(String selectedAnswer, int questionIndex) {
        QuestionModel current = questionsList.get(questionIndex);
        String correctAnswer = current.getCorrectAnswer();

        if (selectedAnswer.equals(correctAnswer)) {
            // Correct answer
            correct++;
            // Change button color to green briefly
            changeButtonColor(ansA, correctAnswer.equals(ansA.getText().toString()));
            changeButtonColor(ansB, correctAnswer.equals(ansB.getText().toString()));
            changeButtonColor(ansC, correctAnswer.equals(ansC.getText().toString()));
            changeButtonColor(ansD, correctAnswer.equals(ansD.getText().toString()));
        } else {
            // Wrong answer
            wrong++;
            // Change button color to red briefly
            changeButtonColor(ansA, correctAnswer.equals(ansA.getText().toString()));
            changeButtonColor(ansB, correctAnswer.equals(ansB.getText().toString()));
            changeButtonColor(ansC, correctAnswer.equals(ansC.getText().toString()));
            changeButtonColor(ansD, correctAnswer.equals(ansD.getText().toString()));
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                currentQuestion++;
                nextQuestion();
            }
        }, 1000); // Delay for 1 second
    }

    private void changeButtonColor(Button button, boolean isCorrectAnswer) {
        int colorResId = isCorrectAnswer ? R.color.green : R.color.red;
        int color = ContextCompat.getColor(this, colorResId);
        button.setBackgroundColor(color);
        // Delay to revert back to the original color after 1 second
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                button.setBackgroundColor(Color.TRANSPARENT); // Set back to original color
            }
        }, 1000);
    }

    private void result() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ai terminat quiz-ul!");
        builder.setMessage("Corect: " + correct + "\nGresit: " + wrong);
        builder.setPositiveButton("OK", (dialog, which) -> {
            startActivity(new Intent(QuestionsActivity.this, QuizActivity.class));
            finish();
        });
        builder.show();
    }

}
