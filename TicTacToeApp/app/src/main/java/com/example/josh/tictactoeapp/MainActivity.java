package com.example.josh.tictactoeapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    int activePlayer = 0;
    boolean gameIsActive = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    int[] gameState= {2,2,2,2,2,2,2,2,2};
    int[][] winningPositions = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};
    public void dropIn(View view){
        ImageView counter = (ImageView)view;
        Button playAgain = (Button) findViewById(R.id.bttnPlayAgain);
        TextView winnerMsg = (TextView) findViewById(R.id.winnerMsg);
        LinearLayout bannerTop = (LinearLayout) findViewById(R.id.topBannerLL);
        //counter.setTranslationY(-1000f);
        int tappedCounter =  Integer.parseInt(counter.getTag().toString());
        if (gameState[tappedCounter] == 2 && gameIsActive)
        {
            gameState[tappedCounter] = activePlayer;
            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.putin_pic);
                counter.animate().setDuration(300);
                activePlayer = 1;
            }
            else{
                counter.setImageResource(R.drawable.trump_pic);
                counter.animate().setDuration(300);
                activePlayer = 0;
            }
        }
        for (int [] winningPosition: winningPositions)
        {
            String formalPlayerName = "";
            if(gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                    gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                    gameState[winningPosition[0]] != 2) {
                    //System.out.println(gameState[winningPosition[0]]);
                if(activePlayer == 1){
                    formalPlayerName = "Vladimir Putin";
                }
                else{
                    formalPlayerName = "Donald Trump";
                }
                Toast.makeText(this,"Winner is " + formalPlayerName, Toast.LENGTH_SHORT).show();
                winnerMsg.setText("Winner is " + formalPlayerName);
                bannerTop.setVisibility(View.VISIBLE);
                gameIsActive = false;
            }
            else
            {
                boolean gameIsOver = true;
                for(int counterState: gameState)
                {
                    if(counterState == 2)
                    {
                        gameIsOver = false;
                    }
                }
                if(gameIsOver){
                    Toast.makeText(this,"It's a draw!", Toast.LENGTH_SHORT).show();
                    winnerMsg.setText("It's a draw!");
                    bannerTop.setVisibility(View.VISIBLE);
                }
            }
        }

    }
    public void playAgain(View view){
        LinearLayout bannerTop = (LinearLayout) findViewById(R.id.topBannerLL);
        GridLayout boardLayout = (GridLayout) findViewById(R.id.mainGridLayout);

        gameIsActive = true;
        bannerTop.setVisibility(View.INVISIBLE);
        activePlayer = 0;
        for(int i=0; i < gameState.length;i++){
            gameState[i] = 2;
        }
        for(int i=0; i < boardLayout.getChildCount(); i++)
        {
            ((ImageView) boardLayout.getChildAt(i)).setImageResource(0);
        }

    }
}
