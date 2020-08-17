package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity
{
    final int INITIAL_TIME = 30;
    TextView timeRemaining;
    TextView prompt;
    TextView score;
    TextView response;
    Button[] choices;
    int correctAnswer;
    int totalCorrect = 0;
    int totalQuestions = 0;


    public int getRandomNumber(int lower, int upper)
    {
        Random rand = new Random();
        return (rand.nextInt(upper - lower + 1) + lower);
    }

    public void updateTimeRemaining(int time)
    {
        timeRemaining.setText(String.valueOf((int) (time/1000)) + "s");
    }

    public void choose(View view)
    {
        Button currentSelection = (Button)view;
        if(Integer.parseInt(currentSelection.getText().toString()) == correctAnswer)
        {
            totalCorrect++;
            response.setText("Correct");
        }
        else
        {
            response.setText("Incorrect");
        }
        totalQuestions++;
        update();
    }

    public void update()
    {
        score.setText(totalCorrect + "/" + totalQuestions);
        int leftOperand = getRandomNumber(1, 100);
        int rightOperand = getRandomNumber(1,100);
        correctAnswer = leftOperand + rightOperand;
        prompt.setText(leftOperand + " + " + rightOperand);

        ArrayList<Integer> choiceValues = new ArrayList<Integer>();

        int correctAnswerPosition = getRandomNumber(0,3);
        for(int i = 0; i < 4; i++)
        {
            if(i == correctAnswerPosition)
            {
                choiceValues.add(correctAnswer);
            }
            else
            {
                int wrongAnswer = getRandomNumber(2, 200);

                while(wrongAnswer == correctAnswer)
                    wrongAnswer = getRandomNumber(2, 200);

                choiceValues.add(wrongAnswer);
            }
        }

        choices[0].setText(Integer.toString(choiceValues.get(0)));
        choices[1].setText(Integer.toString(choiceValues.get(1)));
        choices[2].setText(Integer.toString(choiceValues.get(2)));
        choices[3].setText(Integer.toString(choiceValues.get(3)));
    }

    public void stop()
    {
        for(Button b: choices)
            b.setEnabled(false);

        response.setText("Done");
        Button playAgain = (Button) findViewById(R.id.playAgainButton);
        playAgain.setVisibility(View.VISIBLE);
    }

    public void playAgain(View view)
    {
        start(view);
        for(Button b: choices)
            b.setEnabled(true);
        response.setText("");
        totalCorrect = 0;
        totalQuestions = 0;
        score.setText(totalCorrect + "/" + totalQuestions);
    }

    public void start(View view)
    {
        ((Button)view).setVisibility(View.INVISIBLE);
        TableLayout table = (TableLayout) findViewById(R.id.tableLayout);
        table.setVisibility(View.VISIBLE);
        response.setVisibility(View.VISIBLE);
        update();

        new CountDownTimer(INITIAL_TIME * 1000,1000)
        {
            @Override
            public void onTick(long l)
            {
                updateTimeRemaining((int)l);
            }

            @Override
            public void onFinish()
            {
                stop();
            }
        }.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeRemaining = (TextView) findViewById(R.id.timeTextView);
        updateTimeRemaining(INITIAL_TIME * 1000);

        prompt = (TextView) findViewById(R.id.promptTextView);
        score = (TextView) findViewById(R.id.scoreTextView);
        response = (TextView) findViewById(R.id.responseTexView);

        choices = new Button[4];
        choices[0] = findViewById(R.id.topLeftButton);
        choices[1] = findViewById(R.id.topRightButton);
        choices[2] = findViewById(R.id.bottomLeftButton);
        choices[3] = findViewById(R.id.bottomRightButton);
    }


}