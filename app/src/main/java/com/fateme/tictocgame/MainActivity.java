package com.fateme.tictocgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

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
                        txt_turn.setText("Turn: Player " + turn);
                    } else if(gameStatus() == -1){
                        txt_turn.setText("This is a draw match ");
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

            protected int gameStatus() {

                // 0: continue match
                // 1 X wins
                // 2 O wins
                // -1: Draw

                int row = 0, colX = 0, rowO = 0, col = 0;

                for(int i = 0; i < grid_size; i++){
                    if(check_Row_Equality(i,'X'))
                        return 1;
                    if(check_Column_Equality(i,'X'))
                        return 1;
                    if(check_Row_Equality(i,'O'))
                        return 2;
                    if(check_Column_Equality(i,'O'))
                        return 2;
                    if(check_Diagonal('X'))
                        return 1;
                    if(check_Diagonal('O'))
                        return 2;
                }

                boolean boardFull = true;
                for(int i = 0; i < grid_size; i++){
                    for(int j = 0; j < grid_size; j++){
                        if(my_board[i][j] == ' ')
                            boardFull = false;
                    }
                }

                if(boardFull)
                    return -1;
                else
                    return  0;
            }

            protected  boolean check_Row_Equality(int r, char player){
                int count_Equal = 0;

                for (int i = 0; i < grid_size; i++){
                    if(my_board[r][i] == player){
                        count_Equal++;
                    }
                }
                if(count_Equal == grid_size)
                    return true;
                else
                    return false;
            }

            protected boolean check_Column_Equality(int c, char player){
                int count_Equal = 0;

                for (int i = 0; i < grid_size; i++){
                    if(my_board[i][c] == player){
                        count_Equal++;
                    }
                }
                if(count_Equal == grid_size)
                    return true;
                else
                    return false;
            }

            protected boolean check_Diagonal(char player){
                int count_Equal = 0, count_Equal2 = 0;

                for(int i = 0; i < grid_size;i++)
                    if(my_board[i][i] == player)
                        count_Equal++;
                for(int i = 0; i < grid_size;i++)
                    if(my_board[i][grid_size - 1-i] == player)
                        count_Equal++;
                if(count_Equal == grid_size || count_Equal2 == grid_size)
                    return true;
                else
                    return false;
            }

            protected void stopMatch() {
                for (int i = 0; i < game_board.getChildCount(); i++) {
                    TableRow row = (TableRow) game_board.getChildAt(i);
                    for (int j = 0; j < row.getChildCount();j++){
                        TextView tv = (TextView) row.getChildAt(j);
                        tv.setOnClickListener(null);
                    }
                }
            }


        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        //Inflate the menu;This add items to the action bar if it is present
        getMenuInflater().inflate(R.menu.menu_board, menu);
        return true;
    }

    //Handle action bar item clicks here.
    //The action bar automatically handle clicks on the home button
    //As you specify a parent activity in Android manifest
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.action_settings){
            return true;
        }
        return  onOptionsItemSelected(item);
    }

}