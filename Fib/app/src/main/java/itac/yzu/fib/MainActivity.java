package itac.yzu.fib;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    public long i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void INC(View v) {
        i++;
        setLabel(i, "I");
    }
    public void DEC(View v) {
        if(i > 0) i--;
        setLabel(i, "I");
    }
    public void COMP(View v) {
        setLabel(fib(i), "COMP");
    }
    public long fib(long f) {
        if (f <= 1) return f;
        else return fib(f-1) + fib(f-2);
    }
    public void RES(View v) {
        setLabel(0, "RES");
    }
    public void setLabel(long i, String j) {
        TextView tv1 = (TextView)findViewById(R.id.iTV);
        TextView tv2 = (TextView)findViewById(R.id.valTV);
        Button btn1 = (Button)findViewById(R.id.incBTN);
        Button btn2 = (Button)findViewById(R.id.decBTN);

        switch (j) {
            case "I":
                tv1.setText(Long.toString(i));
                break;
            case "COMP":
                tv2.setText(Long.toString(i));
                btn1.setEnabled(false);
                btn2.setEnabled(false);
                break;
            case "RES":
                tv1.setText(Long.toString(i));
                tv2.setText("");
                btn1.setEnabled(true);
                btn2.setEnabled(true);
                this.i = 0;
                break;
        }
    }
}
