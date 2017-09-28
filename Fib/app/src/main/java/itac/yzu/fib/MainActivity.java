package itac.yzu.fib;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    public int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void INC(View v) {
        i++;
        setLabel(i, 1);
    }
    public void DEC(View v) {
        if(i > 0) i--;
        setLabel(i, 1);
    }
    public void COMP(View v) {
        setLabel(fib(i), 2);
    }
    public int fib(int f) {
        if (f <= 1) return f;
        else return fib(f-1) + fib(f-2);
    }
    public void RES(View v) {
        setLabel(0, 3);
    }
    public void setLabel(int i, int j) {
        TextView tv1 = (TextView)findViewById(R.id.iTV);
        TextView tv2 = (TextView)findViewById(R.id.valTV);
        switch (j) {
            case 1:
                tv1.setText(Integer.toString(i));
                break;
            case 2:
                tv2.setText(Integer.toString(i));
                break;
            case 3:
                tv1.setText(Integer.toString(i));
                tv2.setText(Integer.toString(i));
                this.i = 0;
        }
    }
}
