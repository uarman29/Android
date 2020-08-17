package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity
{
    int activePlayer = 1;
    int[] gameState = {0, 0, 0, 0, 0, 0, 0, 0, 0};
    int[][] winBoxes = {{1,2,3}, {4,5,6}, {7,8,9}, {1,4,7}, {2,5,8}, {3,6,9},{1,5,9}, {3,5,7}};
    boolean gameActive = true;

    public void fill(View view)
    {
        ImageView currentBox = (ImageView) view;
        if(gameState[Integer.parseInt(currentBox.getTag().toString()) - 1] != 0 || !gameActive)
            return;

        gameState[Integer.parseInt(currentBox.getTag().toString()) - 1] = activePlayer;
        if(activePlayer == 1)
        {
            activePlayer = 2;
            currentBox.setImageResource(R.drawable.x);
        }
        else
        {
            activePlayer = 1;
            currentBox.setImageResource(R.drawable.o);
        }
        if(won())
        {
            gameActive = false;
            Button playAgainButton = (Button) findViewById(R.id.playAgainButton);
            TextView winnerText = (TextView) findViewById(R.id.winnerText);
            if(activePlayer == 1)
            {
                winnerText.setText("O has won");
            }
            else
            {
                winnerText.setText("X has won");
            }
            winnerText.setVisibility(View.VISIBLE);
            playAgainButton.setVisibility(View.VISIBLE);
            return;
        }

        for(int i = 0;i < gameState.length;i++)
        {
            if(gameState[i] == 0)
                return;
        }
        gameActive = false;
        Button playAgainButton = (Button) findViewById(R.id.playAgainButton);
        TextView winnerText = (TextView) findViewById(R.id.winnerText);
        winnerText.setText("TIE");
        winnerText.setVisibility(View.VISIBLE);
        playAgainButton.setVisibility(View.VISIBLE);

    }

    public void playAgain(View view)
    {
        activePlayer = 1;
        Arrays.fill(gameState, 0);
        gameActive = true;
        Button playAgainButton = (Button) findViewById(R.id.playAgainButton);
        TextView winnerText = (TextView) findViewById(R.id.winnerText);
        winnerText.setVisibility(View.INVISIBLE);
        playAgainButton.setVisibility(View.INVISIBLE);

        TableLayout board = (TableLayout) findViewById(R.id.board);
        for(int i = 0; i < board.getChildCount(); i++)
        {
            TableRow row = (TableRow) board.getChildAt(i);
            for(int j = 0; j < row.getChildCount(); j++)
            {
                ImageView cell = (ImageView) row.getChildAt(j);
                cell.setImageDrawable(null);
            }
        }
    }
    public boolean won()
    {
        for(int[] winSet: winBoxes)
        {
            if(gameState[winSet[0] - 1] != 0 && gameState[winSet[0] - 1] == gameState[winSet[1] - 1] && gameState[winSet[1] -1] == gameState[winSet[2] - 1])
            {
                return true;
            }
        }
        return false;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}