package com.example.myapplication;

import static com.example.myapplication.R.id.Reset;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Quiz extends AppCompatActivity {

    private TextView questionShow, scoreTextView;
    private Button result1, result2, result3, result4, Reset;
    private int score = 0;
    private int currentQuestionIndex = -1;

    private List<Question> questions;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        questionShow = findViewById(R.id.QuestionShow);
        scoreTextView = findViewById(R.id.scure);
        result1 = findViewById(R.id.Result1);
        result2 = findViewById(R.id.Result2);
        result3 = findViewById(R.id.Result3);
        result4 = findViewById(R.id.Result4);
        Reset = findViewById(R.id.Reset);

        // تعریف سوالات و گزینه‌ها
        questions = new ArrayList<>();
        questions.add(new Question("اولین بمب اتمی در کدام شهر پرتاب شد؟", "هیروشیما", new String[]{"ناکازاکی", "توکیو", "فوکوشیما"}));
        questions.add(new Question("نسل اول کامپیوتر ها بر پایه ........ کار میکردند.", "لوله خلا", new String[]{"ترانزیستور", "میکروپردازنده\nVLSI", "مدار یکپارچه"}));
        questions.add(new Question("سریع ترین حیوان 2 پا خشکی؟", "شتر مرغ", new String[]{"یوزپلنگ", "شیر", "خرگوش"}));
        questions.add(new Question("پیکاسو اهل کدام کشور بود؟", "اسپانیا", new String[]{"فرانسه", "آمریکا", "ایتالیا"}));
        questions.add(new Question("کدام حیوان به عنوان بهترین دوست انسان شناخته می شود؟", "سگ", new String[]{"خرگوش", "مرغ عشق", "عروس هلندی"}));
        questions.add(new Question("بزرگترین پستاندار جهان کدام حیوان است؟", "نهنگ آبی", new String[]{"فیل", "زرافه", "اسب آبی"}));

        Collections.shuffle(questions);
        showNextQuestion();
        setupAnswerButtons();
        Reset.setOnClickListener(v -> resetGame());
    }

    private void showNextQuestion() {
        currentQuestionIndex++;

        if (currentQuestionIndex < questions.size()) {
            Question currentQuestion = questions.get(currentQuestionIndex);
            questionShow.setText(currentQuestion.getQuestion());
            List<String> answers = new ArrayList<>();
            answers.add(currentQuestion.getAnswer());
            Collections.addAll(answers, currentQuestion.getWrongAnswers());
            Collections.shuffle(answers);

            result1.setText(answers.get(0));
            result2.setText(answers.get(1));
            result3.setText(answers.get(2));
            result4.setText(answers.get(3));

            scoreTextView.setText("امتیاز: " + score);
        } else {
            questionShow.setText("پایان بازی! امتیاز نهایی: " + score);
            disableButtons();
        }
    }

    private void setupAnswerButtons() {
        View.OnClickListener answerListener = v -> {
            Button clickedButton = (Button) v;
            if (clickedButton.getText().equals(questions.get(currentQuestionIndex).getAnswer())) {
                score++;
                scoreTextView.setText("امتیاز: " + score);
            }
            showNextQuestion();
        };

        result1.setOnClickListener(answerListener);
        result2.setOnClickListener(answerListener);
        result3.setOnClickListener(answerListener);
        result4.setOnClickListener(answerListener);
    }

    private void disableButtons() {
        result1.setEnabled(false);
        result2.setEnabled(false);
        result3.setEnabled(false);
        result4.setEnabled(false);
        Reset.setEnabled(false);
    }

    private void resetGame() {
        score = 0;
        currentQuestionIndex = -1;
        Collections.shuffle(questions);
        showNextQuestion();
    }

    static class Question {
        private final String question;
        private final String answer;
        private final String[] wrongAnswers;

        public Question(String question, String answer, String[] wrongAnswers) {
            this.question = question;
            this.answer = answer;
            this.wrongAnswers = wrongAnswers;
        }

        public String getQuestion() {
            return question;
        }

        public String getAnswer() {
            return answer;
        }

        public String[] getWrongAnswers() {
            return wrongAnswers;
        }
    }
}