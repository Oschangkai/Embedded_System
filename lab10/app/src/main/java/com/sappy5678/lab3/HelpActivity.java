package com.sappy5678.lab3;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


public class HelpActivity extends AppCompatActivity {

    Button j2New;
    Button j2Continue;
    Button exit;
    RadioGroup level;
    RadioButton level_selected;
    TextView timeText;
    int time = 1000;
    Thread mThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        j2New = (Button)findViewById(R.id.j2New);
        j2Continue = (Button)findViewById(R.id.j2Continue);
        exit = (Button)findViewById(R.id.EXIT_btn);
        level = (RadioGroup) findViewById(R.id.level);
        level.check(R.id.level_1);
        timeText = (TextView) findViewById(R.id.time);

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.clear();
                editor.commit();
                finishAffinity();
            }
        });
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        j2Continue.setEnabled(sharedPreferences.getBoolean("pauseBool",false));
        timeText.setText(String.valueOf(sharedPreferences.getInt("timeText",0)));
        j2New.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mThread.interrupt();
                try {
                    mThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putBoolean("pauseBool",false);
                editor.putBoolean("runningState",false);
                editor.putInt("time",time);
                editor.putInt("timeText",0);
                // editor.commit();
                editor.apply();
                startActivity(intent);
                finish();
            }
        });

        j2Continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putInt("timeText",Integer.valueOf(timeText.getText().toString()));
                editor.apply();
                startActivity(intent);
                finish();
            }
        });




        View.OnClickListener level_selected = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RadioButton level = (RadioButton)view;
                if(level.isChecked())
                {
                    if(level.getId() == R.id.level_0)
                    {
                        time = 3000;
                    }
                    else if(level.getId() == R.id.level_1)
                    {
                        time = 1000;
                    }
                    else if(level.getId() == R.id.level_2)
                    {
                        time = 500;
                    }

                }

//                Log.e("AAA","RRR");
//                Log.e("Selected",String.valueOf(second));


            }

        };

        for(int i = 0 ;i < 3; ++i)
        {
            level.getChildAt(i).setOnClickListener(level_selected);
        }


        mThread = new Thread(timer);
        mThread.start();
    }

    public static final int MEG_TIME = 1;
    @SuppressLint("HandlerLeak")
    Handler mHandlerThread = new Handler()
    {

        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                // 當收到的Message的代號為我們剛剛訂的代號就做下面的動作。
                case MEG_TIME:
                    // 重繪UI
                    // Log.i("Thread","reDraw");
                    //SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    //timeText.setText(String.valueOf(sharedPreferences.getInt("timeText",0)+1));
                    if(Integer.valueOf(timeText.getText().toString()) == 0)
                        break;
                    timeText.setText(String.valueOf(Integer.valueOf(timeText.getText().toString()) +1));
                    break;

            }
            super.handleMessage(msg);
        }

    };
    public Runnable timer = new Runnable() {
        @Override
        public void run()
        {
            // Log.i("Thread","run");
            Message m = new Message();
            // 定義 Message的代號，handler才知道這個號碼是不是自己該處理的。
            m.what = MEG_TIME;
            // timeText.setText(String.valueOf(Integer.valueOf(timeText.getText().toString()) + time/1000));

            mHandlerThread.sendMessage(m);
            mHandlerThread.postDelayed(this, 1000);
        }
    };

}