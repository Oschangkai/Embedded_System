package itac.yzu.knights_tour1;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.content.Intent;

import java.util.Random;
import android.os.Handler;

import org.w3c.dom.Text;


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
        init();
    }

    boolean isNewGame = false;
    boolean hasRunnable = false;
    int begin;
    int current;
    int next;
    int last;
    int[] nextPosition = {11, 12, 9, 19, 20, 21, 13, 23,
                        26, 4, 5, 6, 3, 8, 32, 22,
                        2, 1, 29, 30, 15, 7, 40, 14,
                        10, 41, 44, 43, 35, 36, 16, 47,
                        18, 17, 45, 46, 27, 48, 24, 55,
                        58, 25, 37, 38, 28, 31, 64, 63,
                        34, 33, 57, 62, 59, 60, 61, 39,
                        42, 52, 49, 50, 51, 56, 53, 54};

    final Handler h = new Handler();
    final Runnable r = new Runnable() {
        @Override
        public void run() {
            if(next != begin) {
                step();
                h.postDelayed(this, 1000);
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
        if(hasRunnable)
            h.removeCallbacks(r);
        init();
    }


    public void walkall() {
        hasRunnable = true;
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
        TextView scoreTV = (TextView) findViewById(R.id.scoreTV);
        Context context =this;

        int current_score = Integer.parseInt(scoreTV.getText().toString());
        int currentID = getResources().getIdentifier("textView" + current, "id", getPackageName());
        TextView currentTV = (TextView)findViewById(currentID);
        TextView thisTV = (TextView)findViewById(v.getId());
        int colorID = ContextCompat.getColor(context, (thisTV.getBackground()).getC);
        if(v.getId() == currentID) {
            current_score+=5;
            Log.i("before", String.valueOf(colorID));
            Log.i("yEllo", String.valueOf(Color.YELLOW));
            if(colorID == Color.YELLOW)
                current_score++;
            TextView hitTV = (TextView)findViewById(R.id.hitTV);
            int current_hit = Integer.parseInt(hitTV.getText().toString());
            current_hit = current_hit + 1;
            hitTV.setText(String.valueOf(current_hit));
            currentTV.setBackgroundColor(Color.GREEN);
            colorID = ContextCompat.getColor(context, null);
            Log.i("after", String.valueOf(colorID));
        }
        else if(v.getId() != currentID) {
            TextView missTV = (TextView)findViewById(R.id.missTV);
            int current_miss = Integer.parseInt(missTV.getText().toString());
            //colorID = ContextCompat.getColor(context, v.getId());
            if(colorID != Color.GREEN) {
                Log.i("color", String.valueOf(colorID));
                current_score--;
                current_miss++;
                missTV.setText(String.valueOf(current_miss));
                if(colorID!= Color.YELLOW || colorID != Color.GREEN);
                   thisTV.setBackgroundColor(Color.YELLOW);
            }
        }
        scoreTV.setText(String.valueOf(current_score));
    }
    public void goToHelpPage (View v) {
        Intent helpPage = new Intent(this, HelpPage.class);
        startActivity(helpPage);
    }
}

