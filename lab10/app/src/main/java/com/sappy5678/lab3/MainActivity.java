package com.sappy5678.lab3;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;


public class MainActivity extends AppCompatActivity {

    public static Activity m;
    public TableLayout table;
    public ArrayList<TableRow> ros = new ArrayList<TableRow>();
    public TableRow.LayoutParams tablerow_params = new TableRow.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
    public Button help;
    public Button auto;
    public Button reset;
    public TextView local;
    public TextView Score;
    public TextView Hit;
    public TextView Miss;
    TextView timeText;
    ArrayList<Integer> NextPositionX;
    ArrayList<Integer> NextPositionY;
    ArrayList<Integer> tableColorArray=new ArrayList<Integer>();
    Thread mThread ;

    private Handler mHandler = new Handler();
    // static  Handler mHandlerThread ;
    final CalTour CT = new CalTour();
    int x_poiot = 0,y_poiot = 0;
    int x_local = 0,y_local = 0;
    Boolean pauseBool=false;
    Boolean runningState=false;
    Boolean recovery=false;
    int time = 1000;
    public static final int MEG_TIME = 1;



    int green = Color.argb(255/3,52,229,147);
    int yellow = Color.argb(255/3,242,242,125);
    int red = Color.argb(255/3,237,125,125);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences sharedPreferencesread = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor=sharedPreferences.edit();
        if(!sharedPreferencesread.getBoolean("pauseBool", false))
        {
            editor.putBoolean("pauseBool",false);
            editor.apply();
        }
        time=sharedPreferences.getInt("time",1000);



        setContentView(R.layout.activity_main);
        // mThread.start();
        // mHandlerThread = new Handler(mThread.getLooper());

        table = (TableLayout) findViewById(R.id.table);
        help = (Button)findViewById(R.id.Help);
        auto= (Button)findViewById(R.id.Auto);
        reset = (Button)findViewById(R.id.Reset);
        local = (TextView)findViewById(R.id.local);
        Score = (TextView)findViewById(R.id.Score);
        Hit = (TextView)findViewById(R.id.Hit);
        Miss = (TextView)findViewById(R.id.Miss);
        timeText = (TextView)findViewById(R.id.time);
        timeText.setText(String.valueOf(sharedPreferences.getInt("timeText",0)));
        x_poiot = 0;y_poiot = 0;
        x_local = 0;y_local = 0;



        mThread = new Thread(timer);
        mThread.start();


        m = this;






        // 表格初始化
        // init table
        for(int i = 0;i < 8;++i) {
            table.addView(new TableRow(this));
            table.getChildAt(i).setLayoutParams(tablerow_params);
            for(int j =0;j<8;++j)
            {
                ((TableRow) table.getChildAt(i)).addView(new TextView(this));
                ((TextView)((TableRow)table.getChildAt(i)).getChildAt(j)).setWidth(100);
                ((TextView)((TableRow)table.getChildAt(i)).getChildAt(j)).setHeight(60);
                ((TextView)((TableRow)table.getChildAt(i)).getChildAt(j)).setBackgroundColor(Color.argb(255/3 * ((i+j+1)%2),0,0,0));
                ((TextView)((TableRow)table.getChildAt(i)).getChildAt(j)).setOnClickListener(click);
            }
        }
        //目前有在進行走路
        runningState=true;
        // todo 畫紅 + 騎士
        mHandler.postDelayed(autoDraw, time);


        // Reset
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                help.setEnabled(true);
                reset.setEnabled(true);
                auto.setEnabled(true);
                x_poiot = 0;y_poiot = 0;
                x_local = 0;y_local = 0;
                clear();
                //CT.reset();
                x_local = (int)(Math.random()* 8 + 1);
                y_local = (int)(Math.random()* 8 + 1);
//                x_local = 1;y_local=1;
                System.out.println(""+x_local+" , "+y_local);
                CT.calNextSteps(x_local,y_local);
                System.out.println("Everything is OK!!!!!!!!!!!!!!!!!!!!");
                NextPositionX = Calcul_move(CT.getNextPositionX());
                NextPositionY = Calcul_move(CT.getNextPositionY());
                x_local-=1;y_local-=1;
                knight(x_local,y_local);
                mHandlerThread.removeCallbacks(timer);
                mThread.interrupt();
                try {
                    mThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                Intent intent = new Intent(getApplicationContext(),HelpActivity.class);
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putBoolean("pauseBool",false);
                editor.putBoolean("runningState",false);
                editor.putInt("time",time);
                editor.putInt("timeText",0);
                // editor.commit();
                editor.apply();
                timeText.setText("0");
                startActivity(intent);
                finish();
            }
        });

        // next step
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                if(x_poiot == 63)
//                {
//                    help.setEnabled(false);
//                    auto.setEnabled(false);
//                    Toast.makeText(MainActivity.this ,"已經走完了喔^^",Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                red( x_local, y_local);
//                x_local = NextPositionX.get(x_poiot);
//                y_local = NextPositionY.get(y_poiot);
//                ((TextView)((TableRow)table.getChildAt(x_local)).getChildAt(y_local)).setText("");
//                x_poiot++;y_poiot++;
//                x_local = NextPositionX.get(x_poiot);
//                y_local = NextPositionY.get(y_poiot);
//                knight(x_local ,y_local);

                Intent intent = new Intent(getApplicationContext(),HelpActivity.class);
                startActivity(intent);
                onPause();
                //finish();
            }
        });


        // auto move
       /* auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //help.setEnabled(false);
                reset.setEnabled(false);
                auto.setEnabled(false);

                //目前有在進行走路
                runningState=true;
                // todo 畫紅 + 騎士
                mHandler.postDelayed(autoDraw, time);

            }

        });*/







    }

    // Check if knight was checked
    View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            TextView t = (TextView)view;
//            System.out.println(t.getText());
            if(t.getText().equals(" @ "))
            {
                // 有按騎士就設成綠色
                t.setBackgroundColor(green);
            }
            else
            {
                if(getBackgroundColor(t) != green)
                    t.setBackgroundColor(yellow);
            }

            updatePoint();

        }

    };

    @Override
    protected void onStart() {
        super.onStart();

        x_poiot = 0;y_poiot = 0;
        x_local = 0;y_local = 0;
        clear();
        CT.reset();
//        x_local = (int)(Math.random()* 8 + 1);
//        y_local = (int)(Math.random()* 8 + 1);
        x_local = (int)(Math.random()* 8 + 1);
        y_local = (int)(Math.random()* 8 + 1);
        System.out.println(""+x_local+" , "+y_local);
        CT.calNextSteps(x_local,y_local);
        System.out.println("Everything is OK!!!!!!!!!!!!!!!!!!!!");
        NextPositionX = Calcul_move(CT.getNextPositionX());
        NextPositionY = Calcul_move(CT.getNextPositionY());
        x_local-=1;y_local-=1;
        knight(x_local,y_local);
    }

    // 清空
    // Clear
    protected void clear()
    {
        for(int i = 0;i < 8;++i) {
            for(int j =0;j<8;++j)
            {
                ((TextView)((TableRow)table.getChildAt(i)).getChildAt(j)).setBackgroundColor(Color.argb(255/3 * ((i+j+1)%2),0,0,0));
                ((TextView)((TableRow)table.getChildAt(i)).getChildAt(j)).setText(" ");
            }

        }
    }

    // 塗紅
    // draw red
    protected void red(int x,int y)
    {
        ((TextView)((TableRow)table.getChildAt(x)).getChildAt(y)).setBackgroundColor(red);
        ((TextView)((TableRow)table.getChildAt(x)).getChildAt(y)).setText(" ");
    }

    // 畫騎士
    protected void knight(int x,int y)
    {
        ((TextView)((TableRow)table.getChildAt(x)).getChildAt(y)).setText(" @ ");
        local.setText(""+(x+1)+" , "+(y+1));

    }

    // 拿到背景色
    protected int  getBackgroundColor(int x,int y)
    {
        ColorDrawable colorDrawableOne = (ColorDrawable) ((TextView)((TableRow)table.getChildAt(x)).getChildAt(y)).getBackground();
        return colorDrawableOne.getColor();
    }
    // 拿到背景色
    protected int  getBackgroundColor(TextView t)
    {
        ColorDrawable colorDrawableOne = (ColorDrawable) t.getBackground();
        return colorDrawableOne.getColor();
    }
    @SuppressLint("HandlerLeak")
    Handler  mHandlerThread = new Handler()
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

    private Runnable autoDraw = new Runnable()
    {
        public void run()
        {

            runningState=true;
            if(pauseBool)
                return;
            //write here whaterver you want to repeat
            if(x_poiot == 63) {
                Toast.makeText(MainActivity.this ,"已經走完了",Toast.LENGTH_SHORT).show();
//                next.setEnabled(true);
                runningState=false;
                reset.setEnabled(true);

//                auto.setEnabled(true);
                return;
            }

            if(getBackgroundColor(x_local,y_local) != yellow && getBackgroundColor(x_local,y_local) != green)
                red( x_local, y_local);
            x_local = NextPositionX.get(x_poiot);
            y_local = NextPositionY.get(y_poiot);
            ((TextView)((TableRow)table.getChildAt(x_local)).getChildAt(y_local)).setText("");
            x_poiot++;y_poiot++;
            x_local = NextPositionX.get(x_poiot);
            y_local = NextPositionY.get(y_poiot);
            knight(x_local ,y_local);
            // timeText.setText(String.valueOf(Integer.valueOf(timeText.getText().toString()) + time/1000));

            mHandler.postDelayed(this, time);
        }
    };


    private ArrayList<Integer> Calcul_move(ArrayList<Integer> x)
    {
        for(int i = 0; i < x.size(); i++)
        {
            x.set(i,x.get(i)-1);
        }

        return x;
    }

    private void updatePoint()
    {
        ArrayList<Integer> p = calculPoint();
        Score.setText(p.get(0).toString());
        Hit.setText(p.get(1).toString());
        Miss.setText(p.get(2).toString());

    }
    private ArrayList<Integer> calculPoint()
    {
        // 0 是 分數
        // 1 是 Hit 數
        // 2 是 Miss 數
        ArrayList<Integer> arr = new ArrayList<Integer>(3);
        for(int i = 0;i < 3;++i)
        {
            arr.add(0);
            arr.set(i,0);
        }
        for(int i = 0;i < 8;++i) {
            for(int j =0;j<8;++j)
            {
                if(getBackgroundColor(i,j) == green) {
                    arr.set(0, arr.get(0) + 5);
                    arr.set(1, arr.get(1) + 1);
                }
                else if(getBackgroundColor(i,j) == yellow) {
                    arr.set(0, arr.get(0) - 1);
                    arr.set(2, arr.get(2) + 1);
                }
            }

        }

        return  arr;
    }
    @Override
    public  void onPause(){
        super.onPause();
        pauseBool=true;
        System.out.println(runningState.toString()+"wbewibweibv");
        copyTableColor();
        //mHandler.postDelayed(null, 1000);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor=sharedPreferences.edit();
        StringBuilder str=new StringBuilder();
        for(int i=0;i<NextPositionX.size();i++)
        {
            str.append(NextPositionX.get(i)).append(",");
        }
        Set<Integer> saver=new HashSet<Integer>(NextPositionX);
        //saver.addAll(NextPositionX);

        editor.putString("NextPositionX",ArraylistIntToStr(str,NextPositionX));
        str.delete(0,str.length()-1);
        editor.putString("NextPositionY",ArraylistIntToStr(str,NextPositionY));
        str.delete(0,str.length()-1);
        editor.putString("tableColorArray",ArraylistIntToStr(str,tableColorArray));
        str.delete(0,str.length()-1);
        editor.putInt("x_poiot", x_poiot);
        editor.putInt("y_poiot", y_poiot);
        editor.putInt("x_local", x_local);
        editor.putInt("y_local", y_local);
        editor.putInt("time",time);
        editor.putBoolean("pauseBool",true);
        editor.putBoolean("runningState",runningState);
        editor.putInt("timeText",Integer.valueOf(timeText.getText().toString()));
        editor.apply();

    }
    public String ArraylistIntToStr(StringBuilder str,ArrayList<Integer> input)
    {
        for(int i=0;i<input.size();i++)
        {
            str.append(input.get(i)).append(",");
        }
        return str.toString();
    }
    public void StrIntToArraylist(String str,ArrayList<Integer> input)
    {
        input.clear();
        StringTokenizer st=new StringTokenizer(str,",");
        while(st.hasMoreTokens())
        {
            input.add(Integer.parseInt(st.nextToken()));
        }
    }



    @Override
    public  void onResume(){
        super.onResume();

        SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        pauseBool=sharedPreferences.getBoolean("pauseBool",false);
        runningState=sharedPreferences.getBoolean("runningState",false);
        if(pauseBool)
        {
            StrIntToArraylist(sharedPreferences.getString("NextPositionX","Not value"),NextPositionX);
            StrIntToArraylist(sharedPreferences.getString("NextPositionY","Not value"),NextPositionY);
            for(int i=0;i<64;i++)
            {
                ((TextView)((TableRow)table.getChildAt(i/8)).getChildAt(i%8)).setText("");
            }
            tableColorArray.clear();
            StrIntToArraylist(sharedPreferences.getString("tableColorArray","Not value"),tableColorArray);
            x_poiot=sharedPreferences.getInt("x_poiot",0);
            y_poiot=sharedPreferences.getInt("y_poiot",0);
            x_local=sharedPreferences.getInt("x_local",0);
            y_local=sharedPreferences.getInt("y_local",0);
            time = sharedPreferences.getInt("time",1000);
            timeText.setText(String.valueOf(sharedPreferences.getInt("timeText",0)));
            pasteTableColor();
            updatePoint();
            reset.setEnabled(false);
            auto.setEnabled(false);
            knight(x_local,y_local);
            //mHandler.postDelayed(autoDraw, 1000);
//            if(runningState==true)
//            {
//                recovery=true;
//                updatePoint();
//                reset.setEnabled(false);
//                auto.setEnabled(false);
//            }
        }

        SharedPreferences sharedPreferencess = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor=sharedPreferencess.edit();
        editor.putBoolean("pauseBool",false);
        editor.apply();
        pauseBool=false;

    }
//    @Override
//    public void onSaveInstanceState(Bundle savedInstanceState) {
//        super.onSaveInstanceState(savedInstanceState);
//        // Save UI state changes to the savedInstanceState.
//        // This bundle will be passed to onCreate if the process is
//        // killed and restarted.
//        savedInstanceState.putIntegerArrayList("NextPositionX",NextPositionX);
//        savedInstanceState.putIntegerArrayList("NextPositionY",NextPositionY);
//        savedInstanceState.putIntegerArrayList("tableColorArray",tableColorArray);
//        savedInstanceState.putInt("x_poiot", x_poiot);
//        savedInstanceState.putInt("y_poiot", y_poiot);
//        savedInstanceState.putInt("x_local", x_local);
//        savedInstanceState.putInt("y_local", y_local);
//        savedInstanceState.putBoolean("pauseBool",pauseBool);
//        savedInstanceState.putBoolean("runningState",runningState);
//        // etc.
//    }
//    @Override
//    public void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        // Restore UI state from the savedInstanceState.
//        // This bundle has also been passed to onCreate.
//        x_poiot=savedInstanceState.getInt("x_poiot");
//        y_poiot=savedInstanceState.getInt("y_poiot");
//        x_local=savedInstanceState.getInt("x_local");
//        y_local=savedInstanceState.getInt("y_local");
//        pauseBool=savedInstanceState.getBoolean("pauseBool");
//        runningState=savedInstanceState.getBoolean("runningState");
//    }
    protected void copyTableColor()
    {
        ColorDrawable colorDrawableOne;
        tableColorArray.clear();
        for(int i=0;i<64;i++)
            tableColorArray.add(0);
        for(int i=0;i<64;i++)
        {
            colorDrawableOne = (ColorDrawable) ((TextView)((TableRow)table.getChildAt(i/8)).getChildAt(i%8)).getBackground();
            if(colorDrawableOne.getColor()==green)
                tableColorArray.set(i,1);
            else if(colorDrawableOne.getColor()==yellow)
            {
                tableColorArray.set(i,2);
            }
            else if(colorDrawableOne.getColor()==red)
            {
                tableColorArray.set(i,3);
            }
        }
    }
    protected void pasteTableColor()
    {
        for(int i=0;i<tableColorArray.size();i++)
        {
            if(tableColorArray.get(i)==1)
                ((TextView)((TableRow)table.getChildAt(i/8)).getChildAt(i%8)).setBackgroundColor(green);
            else if(tableColorArray.get(i)==2)
            {
                ((TextView)((TableRow)table.getChildAt(i/8)).getChildAt(i%8)).setBackgroundColor(yellow);
            }
            else if(tableColorArray.get(i)==3)
            {
                ((TextView)((TableRow)table.getChildAt(i/8)).getChildAt(i%8)).setBackgroundColor(red);
            }
        }
    }
}

