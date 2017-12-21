package itac.yzu.knights_tour1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Random;


public class MainActivity extends AppCompatActivity {

    public static Activity kt;
    Bundle b;
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
                if(i%2 == 0) {
                    if(j%2 == 0)
                        tv.setBackgroundColor(Color.DKGRAY);
                } else {
                    if(j%2 == 1)
                        tv.setBackgroundColor(Color.DKGRAY);
                }
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickTV(v);
                    }
                });
            }
        }
        b = getIntent().getExtras();
        kt = this;
        init();
    }

    boolean hasRunnableR = false;
    int begin;
    int current;
    int next;
    int stopped = 0;
    int last;
    int[] nextPosition = {11, 12, 9, 19, 20, 21, 13, 23,
                        26, 4, 5, 6, 3, 8, 32, 22,
                        2, 1, 29, 30, 15, 7, 40, 14,
                        10, 41, 44, 43, 35, 36, 16, 47,
                        18, 17, 45, 46, 27, 48, 24, 55,
                        58, 25, 37, 38, 28, 31, 64, 63,
                        34, 33, 57, 62, 59, 60, 61, 39,
                        42, 52, 49, 50, 51, 56, 53, 54};
    int _CurrentScore;
    int _CurrentHit;
    int _CurrentMiss;

    final Handler h = new Handler();
    final Runnable r = new Runnable() {
        @Override
        public void run() {
            int dif = b.getInt("DIFFICULTY");
            if(next != begin) {
                step();
                h.postDelayed(this, dif);
            }
            else {
                next = nextPosition[current - 30] + 30 - 1;
                int nextID = getResources().getIdentifier("textView" + next, "id", getPackageName());
                int currentID = getResources().getIdentifier("textView" + current, "id", getPackageName());
                TextView currentTV = (TextView)findViewById(currentID);
                TextView nextTV = (TextView) findViewById(nextID);
                currentTV.setText("");
                nextTV.setText("@");
                h.removeCallbacks(r);
                hasRunnableR = false;
                stopped = 1;
            }
        }
    };


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
        TextView scoreTV = (TextView)findViewById(R.id.scoreTV);
        TextView hitTV = (TextView)findViewById(R.id.hitTV);
        TextView missTV = (TextView)findViewById(R.id.missTV);
        scoreTV.setText("0");
        hitTV.setText("0");
        missTV.setText("0");
        stopped = 0;
        walkall();
    }

    // newGameBtn clicked
    /*public void restart(View v) {
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
                        tv.setBackgroundColor(Color.DKGRAY);
                    else
                        tv.setBackgroundColor(Color.WHITE);
                } else {
                    if(j%2 == 1)
                        tv.setBackgroundColor(Color.DKGRAY);
                    else
                        tv.setBackgroundColor(Color.WHITE);
                }
            }
        }
        TextView row = (TextView)findViewById(R.id.rowTV);
        TextView col = (TextView)findViewById(R.id.colTV);

        row.setText("");
        col.setText("");

        if(hasRunnableR)
            h.removeCallbacks(r);
        init();
    }*/

    public void restart(View v) {
        if(hasRunnableR)
            h.removeCallbacks(r);
        finish();
    }

    public void walkall() {
        hasRunnableR = true;
        h.post(r);
    }

    public void step() {
        next = nextPosition[current - 30] + 30 - 1;
        int nextID = getResources().getIdentifier("textView" + next, "id", getPackageName());
        int currentID = getResources().getIdentifier("textView" + current, "id", getPackageName());
        TextView currentTV = (TextView)findViewById(currentID);
        TextView nextTV = (TextView) findViewById(nextID);
        nextTV.setBackgroundColor(android.graphics.Color.RED);
        currentTV.setText("");
        nextTV.setText("@");
        last = current;
        current = next;
    }


    public void clickTV(View v) {
        if(!hasRunnableR)
            return;
        TextView scoreTV = (TextView) findViewById(R.id.scoreTV);

        int current_score = Integer.parseInt(scoreTV.getText().toString());
        int currentID = getResources().getIdentifier("textView" + current, "id", getPackageName());
        TextView currentTV = (TextView)findViewById(currentID);
        TextView thisTV = (TextView)findViewById(v.getId());
        int colorID = 0;
        if (thisTV.getBackground() instanceof ColorDrawable) {
            ColorDrawable cd = (ColorDrawable) thisTV.getBackground();
            colorID = cd.getColor();
        }

        if(v.getId() == currentID) {
            current_score+=5;
            if(colorID == Color.YELLOW)
                current_score++;
            TextView hitTV = (TextView)findViewById(R.id.hitTV);
            int current_hit = Integer.parseInt(hitTV.getText().toString());
            current_hit = current_hit + 1;
            hitTV.setText(String.valueOf(current_hit));
            _CurrentHit = current_hit;
            currentTV.setBackgroundColor(Color.GREEN);
        }
        else if(v.getId() != currentID) {
            TextView missTV = (TextView)findViewById(R.id.missTV);
            int current_miss = Integer.parseInt(missTV.getText().toString());
            if(colorID != Color.GREEN) {
                current_score--;
                current_miss++;
                missTV.setText(String.valueOf(current_miss));
                _CurrentMiss = current_miss;
                thisTV.setBackgroundColor(Color.YELLOW);
            }
        }
        scoreTV.setText(String.valueOf(current_score));
        _CurrentScore = current_score;
    }
    public void goToHelpPage (View v) {
        boolean paused = true;
        Bundle b = new Bundle();
        b.putBoolean("PAUSED", paused);
        Intent helpPage = new Intent(this, HelpPage.class);
        helpPage.putExtras(b);
        startActivity(helpPage);
        overridePendingTransition(R.animator.slide_from_left, R.animator.slide_to_right);
    }
    @Override
    public void onPause() {
        super.onPause();
        h.removeCallbacks(r);
        hasRunnableR = false;
    }
    @Override
    public void onResume() {
        super.onResume();
        if(!hasRunnableR && stopped != 1) {
            hasRunnableR = true;
            r.run();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        //super.onSaveInstanceState(savedInstanceState);
        // Save the user's current game state
        savedInstanceState.putInt("_CurrentScore", _CurrentScore);
        savedInstanceState.putInt("_CurrentHit", _CurrentScore);
        savedInstanceState.putInt("_CurrentMiss", _CurrentScore);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);

        // Restore state members from saved instance
        _CurrentScore = savedInstanceState.getInt("_CurrentScore");
        _CurrentHit = savedInstanceState.getInt("_CurrentHit");
        _CurrentMiss = savedInstanceState.getInt("_CurrentMiss");

        TextView scoreTV = (TextView) findViewById(R.id.scoreTV);
        TextView hitTV = (TextView) findViewById(R.id.hitTV);
        TextView missTV = (TextView) findViewById(R.id.missTV);

        scoreTV.setText(String.valueOf(_CurrentScore));
        hitTV.setText(String.valueOf(_CurrentHit));
        missTV.setText(String.valueOf(_CurrentMiss));

    }
}

