package itac.yzu.knights_tour1;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Random;
import android.os.Handler;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set textView background color
        TableLayout tb = (TableLayout)findViewById(R.id.tb);
        for(int i = 0; i < 8; i++) {
            TableRow tr = (TableRow)tb.getChildAt(i);
            for(int j = 0; j < 8; j++) {
                TextView tv = (TextView)tr.getChildAt(j);
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickTV(v);
                    }
                });
                if(i%2 == 0) {
                    if(j%2 == 0)
                        tv.setBackgroundResource(android.R.color.darker_gray);
                } else {
                    if(j%2 == 1)
                        tv.setBackgroundResource(android.R.color.darker_gray);
                }
            }
        }
        init();
    }
    int begin;
    int current;
    int next;
    int[] nextPosition = {11, 12, 9, 19, 20, 21, 13, 23,
                        26, 4, 5, 6, 3, 8, 32, 22,
                        2, 1, 29, 30, 15, 7, 40, 14,
                        10, 41, 44, 43, 35, 36, 16, 47,
                        18, 17, 45, 46, 27, 48, 24, 55,
                        58, 25, 37, 38, 28, 31, 64, 63,
                        34, 33, 57, 62, 59, 60, 61, 39,
                        42, 52, 49, 50, 51, 56, 53, 54};


    public void init() {
        Random r = new Random();
        begin = r.nextInt(94-30) + 30;
        current = begin;
        next = nextPosition[current - 30] + 30 - 1;
        int idBegin = getResources().getIdentifier("textView"+begin, "id",getPackageName());
        TextView start = (TextView)findViewById(idBegin);
        start.setText("@");
        Button jumpOne = (Button)findViewById(R.id.helpBtn);
        jumpOne.setEnabled(true);
        walkall();
    }

    public void restart(View v) {
        for(int i = 30; i < 94; ++i ) {
            int id = getResources().getIdentifier("textView"+i, "id",getPackageName());
            TextView current = (TextView) findViewById(id);
            current.setText("");
        }
        TableLayout tb = (TableLayout)findViewById(R.id.tb);
        for(int i = 0; i < 8; i++) {
            TableRow tr = (TableRow)tb.getChildAt(i);
            for(int j = 0; j < 8; j++) {
                TextView tv = (TextView)tr.getChildAt(j);
                if(i%2 == 0) {
                    if(j%2 == 0)
                        tv.setBackgroundResource(android.R.color.darker_gray);
                    else
                        tv.setBackgroundResource(android.R.color.white);
                } else {
                    if(j%2 == 1)
                        tv.setBackgroundResource(android.R.color.darker_gray);
                    else
                        tv.setBackgroundResource(android.R.color.white);
                }
            }
        }
        TextView row = (TextView)findViewById(R.id.rowTV);
        TextView col = (TextView)findViewById(R.id.colTV);

        row.setText("");
        col.setText("");
        init();
    }


    public void walkall() {
        final Handler h = new Handler();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                Button jumpOne = (Button)findViewById(R.id.helpBtn);
                Button restart = (Button)findViewById(R.id.newGameBtn);
                jumpOne.setEnabled(false);
                restart.setEnabled(false);
                if(next != begin) {
                    step();
                    h.postDelayed(this, 1000);
                } else {
                    restart.setEnabled(true);
                }
            }
        };
        r.run();
    }

    public void step() {
        next = nextPosition[current - 30] + 30 - 1;
       /*if(next == begin) {
            Button jumpOne = (Button)findViewById(R.id.nextBtn);
            Button contin = (Button)findViewById(R.id.continuousBtn);
            jumpOne.setEnabled(false);
            contin.setEnabled(false);
        }
        else {
            int nextID = getResources().getIdentifier("textView" + next, "id", getPackageName());
            int currentID = getResources().getIdentifier("textView" + current, "id", getPackageName());
            TextView currentTV = (TextView)findViewById(currentID);
            TextView nextTV = (TextView) findViewById(nextID);
            nextTV.setBackgroundColor(android.graphics.Color.RED);
            currentTV.setText("");
            nextTV.setText("@");
            current = next;
        }*/
        int nextID = getResources().getIdentifier("textView" + next, "id", getPackageName());
        int currentID = getResources().getIdentifier("textView" + current, "id", getPackageName());
        TextView currentTV = (TextView)findViewById(currentID);
        TextView nextTV = (TextView) findViewById(nextID);
        nextTV.setBackgroundColor(android.graphics.Color.RED);
        currentTV.setText("");
        nextTV.setText("@");
        current = next;
        if(next == begin) {
            Button jumpOne = (Button)findViewById(R.id.helpBtn);
            jumpOne.setEnabled(false);
        }
        current = next;
    }


    public void clickTV(View v) {
        TextView scoreTV = (TextView) findViewById(R.id.scoreTV);

        int current_score = Integer.parseInt(scoreTV.getText().toString());
        int currentID = getResources().getIdentifier("textView" + current, "id", getPackageName());
        TextView currentTV = (TextView)findViewById(currentID);
        ColorDrawable clickedTVColor = (ColorDrawable) v.getBackground();
        if(v.getId() == currentID) {
            current_score+=5;
            if(clickedTVColor.getColor() == Color.YELLOW)
                current_score++;
            currentTV.setBackgroundColor(Color.GREEN);
        }
        else if(v.getId() != currentID) {
            if(clickedTVColor.getColor() != Color.YELLOW) {
                if(current_score > 0)
                    current_score--;
                currentTV.setBackgroundColor(Color.YELLOW);
            }
        }
        scoreTV.setText(String.valueOf(current_score));
    }
}

