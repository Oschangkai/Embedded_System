package com.sappy5678.lab3;

import android.os.Message;

/**
 * Created by sappy5678 on 12/22/17.
 */

public class Timer implements Runnable {
    int time;
    public Timer(int t)
    {
        time = t;
    }

    public void run()
    {
        Message m = new Message();
        m.arg1 = time+1;
        m.what = 1;
        Runnable r = new Timer(time+1);
        System.out.println("hello ");
    }

}
