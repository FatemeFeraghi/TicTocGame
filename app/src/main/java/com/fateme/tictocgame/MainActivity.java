package com.fateme.tictocgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

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

        resetBoard();

        txt_turn.setText("Turn: " + turn);

        for (int i = 0; i < game_board.getChildCount(); i++) {

            TableRow row = (TableRow) game_board.getChildAt(i);
            for (int j = 0; j < row.getVirtualChildCount(); j++){
                TextView tv = (TextView) row.getVirtualChildAt(j);
                tv.setText("");
                tv.setOnClickListener(Move(i,j,tv));
            }
        }


        Button reset_button = (Button) findViewById(R.id.reset);
        reset_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent current = getIntent();
               finish();
               startActivity(current);
            }
        });
    }

    protected void resetBoard() {
        turn = 'X';

        for(int i = 0; i < grid_size; i++){
            for(int j = 0; j < grid_size; j++){
                my_board[i][j] = ' ';
            }
        }
    }

    protected boolean Cell_Set(int r, int c){
        // If the condition is true, means the cell is not set to any data(is empty)
       return !(my_board[r][c] == ' ');
    }

    View.OnClickListener Move(final int r, final int c, final TextView tv){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //If the cells are empty
                if(!Cell_Set(r,c)){
                    my_board[r][c] = turn;

                    if(turn == 'X'){
                        tv.setText(R.string.X);
                        turn = 'O';
                    } else if(turn == 'O'){
                        tv.setText(R.string.O);
                        turn = 'X';
                    }

                    if(gameStatus() == 0) {
                        tv.setText("Turn: Player " + turn);
                    } else if(gameStatus() == -1){
                        tv.setText("This is a draw match ");
                        stopMatch();
                    } else {
                        txt_turn.setText(turn + "Loses!");
                        stopMatch();
                    }
                } else {
                    //txt_turn.setText(txt_turn.getText() + " Choose an empty cell");
                    Toast.makeText(MainActivity.this,"Please, choose an empty cell",Toast.LENGTH_LONG).show();
                }
            }

            private int gameStatus() {
                return 0;
            }

            private void stopMatch() {
            }
        };
    }

}