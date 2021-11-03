package com.fateme.tictocgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int grid_size;
    TableLayout game_board;
    TextView txt_turn;
    char [][] my_board;
    char turn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        grid_size = Integer.parseInt("3");
        my_board = new char[grid_size][grid_size];

        game_board = findViewById(R.id.mainBoard);
        txt_turn = findViewById(R.id.turn);

        //resetBoard
        txt_turn.setText("Turn: " + turn);

        for (int i = 0; i < game_board.getChildCount(); i++) {
            TableRow row = (TableRow) game_board.getChildAt(i);
        }

    }

}