package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ai extends AppCompatActivity {

    int no_moves=0;
    int game_won1=0;
    int game_won2=0;
    boolean won = false;
    int cur =-1;
    int [][] game =  new int[3][3];
    TextView tw1,tw2;
    Button reset;
    int player =-1;
    int ai =1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai);
        tw1 = (TextView)findViewById(R.id.score1);
        tw2 = (TextView)findViewById(R.id.score);
        tw1.setText(String.valueOf(game_won1));
        tw2.setText(String.valueOf(game_won2));
        reset = (Button) findViewById(R.id.b_reset);
        reset.setVisibility(View.INVISIBLE);
        reset.setEnabled(false);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                no_moves=0;
                won = false;
                cur =-1;
                for(int i=0;i<3;i++){
                    for(int j=0;j<3;j++){
                        game[i][j] =0;
                    }
                }
                ImageView i1 = (ImageView) findViewById(R.id.imageView1) ;
                ImageView i2 = (ImageView) findViewById(R.id.imageView2) ;
                ImageView i3 = (ImageView) findViewById(R.id.imageView3) ;
                ImageView i4 = (ImageView) findViewById(R.id.imageView4) ;
                ImageView i5 = (ImageView) findViewById(R.id.imageView5) ;
                ImageView i6 = (ImageView) findViewById(R.id.imageView6) ;
                ImageView i7 = (ImageView) findViewById(R.id.imageView7) ;
                ImageView i8 = (ImageView) findViewById(R.id.imageView8) ;
                ImageView i9 = (ImageView) findViewById(R.id.imageView9) ;
                i1.setImageDrawable(null);
                i2.setImageDrawable(null);
                i3.setImageDrawable(null);
                i4.setImageDrawable(null);
                i5.setImageDrawable(null);
                i6.setImageDrawable(null);
                i7.setImageDrawable(null);
                i8.setImageDrawable(null);
                i9.setImageDrawable(null);
                reset.setVisibility(View.INVISIBLE);
                reset.setEnabled(false);

            }
        });
    }

    public void activeCell(View view){
       if(cur==-1){
           ImageView imageView = (ImageView)view;
           String [] temp = imageView.getTag().toString().split(" ");
           int []  cur_image = new int[2];
           cur_image[0] = Integer.parseInt(temp[0])-1;
           cur_image[1] = Integer.parseInt((temp[1]))-1;
           if(game[cur_image[0]][cur_image[1]] ==0 && no_moves<9&& won!=true){
               game[cur_image[0]][cur_image[1]] = cur;
               no_moves++;
               if(cur==-1){
                   imageView.setImageResource(R.drawable.x1);
               }

               cur = 1;
               if(no_moves==9&& won==false){
                   Toast.makeText(getApplicationContext(),"Draw",Toast.LENGTH_LONG).show();
                   reset.setVisibility(View.VISIBLE);
                   reset.setEnabled(true);
               }
               else if(won==false){
                   bestMove(game);
                   no_moves++;
                   // Since it is a solved game that's why it can only lead to Draw if played Optimally
                   // or in favour of AI if Player does not play Optimally
                   if(check()){
                       String res = "Player";
                       String t = cur==-1?"1":"2";
                       won = true;
                       Toast.makeText(getApplicationContext(),"AI won",Toast.LENGTH_LONG).show();
                       int  t1 = Integer.parseInt(t);
                       if(t1==-1)game_won1++;
                       else game_won2++;
                       tw1.setText(String.valueOf(game_won1));
                       tw2.setText(String.valueOf(game_won2));
                       reset.setVisibility(View.VISIBLE);
                       reset.setEnabled(true);
                   }
                   cur =-1;
               }

           }
       }

    }

    public boolean check(){
        int d1=0;int d2=0;
        for(int i=0;i<3;i++){
            int sum=0;
            for(int j=0;j<3;j++){
                if(i==j){
                    d1 += game[i][j];
                }
                if(i+j==2){
                    d2 += game[i][j];
                }
                sum += game[i][j];
            }
            if(sum==3 || sum==-3)return true;
        }
        if(d1 ==3 || d1==-3 ||d2==3||d2==-3)return true;
        for(int i=0;i<3;i++){
            int sum=0;
            for(int j=0;j<3;j++){
                sum += game[j][i];
            }
            if(sum==3 || sum==-3)return true;
        }
        return false;
    }

    public boolean isMovesLeft()
    {
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                if (game[i][j] == 0)
                    return true;
            }
        }

        return false;
    }

    public int evaluate()
    {

        for (int row = 0; row < 3; row++)
        {
            if (game[row][0] == game[row][1] &&
                    game[row][1] == game[row][2])
            {
                if (game[row][0] == player)
                    return -1;
                else if (game[row][0] == ai)
                    return 1;
            }
        }


        for (int col = 0; col < 3; col++)
        {
            if (game[0][col] == game[1][col] &&
                    game[1][col] == game[2][col])
            {
                if (game[0][col] == player)
                    return -1;

                else if (game[0][col] == ai)
                    return 1;
            }
        }


        if (game[0][0] == game[1][1] && game[1][1] == game[2][2])
        {
            if (game[0][0] == player)
                return -1;
            else if (game[0][0] == ai)
                return 1;
        }

        if (game[0][2] == game[1][1] && game[1][1] == game[2][0])
        {
            if (game[0][2] == player)
                return -1;
            else if (game[0][2] == ai)
                return 1;
        }


        return 0;
    }
    public int minimax(int game[][] , Boolean isMax)
    {
        int score = evaluate();


        if (score == 1 || score ==-1)
            return score;

        if (isMovesLeft() == false)
            return 0;


        if (isMax)
        {
            int best = Integer.MIN_VALUE;


            for (int i = 0; i < 3; i++)
            {
                for (int j = 0; j < 3; j++)
                {

                    if (game[i][j]==0)
                    {

                        game[i][j] = ai;


                        best = Math.max(best, minimax(game, false));


                        game[i][j] = 0;
                    }
                }
            }
            return best;
        }


        else
        {
            int best = Integer.MAX_VALUE;


            for (int i = 0; i < 3; i++)
            {
                for (int j = 0; j < 3; j++)
                {

                    if (game[i][j] == 0)
                    {

                        game[i][j] = player;


                        best = Math.min(best, minimax(game, true));


                        game[i][j] = 0;
                    }
                }
            }
            return best;
        }
    }
     public void bestMove(int game[][])
    {
        int bestVal = Integer.MIN_VALUE;
        int [] move = {-1,-1};


        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {

                if (game[i][j] == 0)
                {

                    game[i][j] = ai;


                    int moveVal = minimax(game, false);


                    game[i][j] = 0;


                    if (moveVal > bestVal)
                    {
                        move[0] = i;
                        move[1] = j;
                        bestVal = moveVal;
                    }
                }
            }
        }

        game[move[0]][move[1]] = ai;
        repaint(game);

    }
    public void repaint(int [][] game){
        ImageView i1 = (ImageView) findViewById(R.id.imageView1) ;
        ImageView i2 = (ImageView) findViewById(R.id.imageView2) ;
        ImageView i3 = (ImageView) findViewById(R.id.imageView3) ;
        ImageView i4 = (ImageView) findViewById(R.id.imageView4) ;
        ImageView i5 = (ImageView) findViewById(R.id.imageView5) ;
        ImageView i6 = (ImageView) findViewById(R.id.imageView6) ;
        ImageView i7 = (ImageView) findViewById(R.id.imageView7) ;
        ImageView i8 = (ImageView) findViewById(R.id.imageView8) ;
        ImageView i9 = (ImageView) findViewById(R.id.imageView9) ;
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(game[i][j] ==ai){
                    int num = ((i+1)*10)+j;
                    switch (num){
                        case 10:
                            i1.setImageResource(R.drawable.o1);
                            break;
                        case 11:
                            i2.setImageResource(R.drawable.o1);
                            break;
                        case 12:
                            i3.setImageResource(R.drawable.o1);
                            break;
                        case 20:
                            i4.setImageResource(R.drawable.o1);
                            break;
                        case 21:
                            i5.setImageResource(R.drawable.o1);
                            break;
                        case 22:
                            i6.setImageResource(R.drawable.o1);
                            break;
                        case 30:
                            i7.setImageResource(R.drawable.o1);
                            break;
                        case 31:
                            i8.setImageResource(R.drawable.o1);
                            break;
                        case 32:
                            i9.setImageResource(R.drawable.o1);
                            break;
                    }
                }
            }
        }
    }




}