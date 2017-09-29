package itac.yzu.bmi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    // Find Elements on View
    EditText heightET = (EditText)findViewById(R.id.heightET);
    EditText weightET = (EditText)findViewById(R.id.weightET);

    Button resetBTN = (Button)findViewById(R.id.resetBTN);
    Button genderBTN = (Button)findViewById(R.id.genderBTN);

    TextView bmiTV = (TextView)findViewById(R.id.bmiTV);
    TextView ideal_weightTV = (TextView)findViewById(R.id.ideal_weight_resultTV);
    TextView bmi_resultTV = (TextView)findViewById(R.id.bmi_resultTV);
    TextView ideal_weight_resultTV = (TextView)findViewById(R.id.ideal_weight_resultTV);
    TextView suggestionTV = (TextView)findViewById(R.id.suggestionTV);

    // Definitions
    double[] bmiLevel = {18.5, 24, 27, 30, 35};
    String[] suggestion = {
            "「體重過輕」，需要多運動，均衡飲食，以增加體能，維持健康！",
            "「健康體重」，要繼續保持喔！",
            "「體重過重」，要小心囉，趕快力行健康體重管理吧！",
            "「輕度肥胖」，需要立刻力行健康體重管理喔！",
            "「中度肥胖」，需要立刻力行健康體重管理喔！",
            "「重度肥胖」，需要立刻力行健康體重管理喔！"};
    String[] bmiResult = { "體重過輕", "體重正常", "體重過重", "體重肥胖" };
    String[] idealWeightResult = { "為體重正常", "為體重過重或過輕", "以上為肥胖或體重不足" };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
<<<<<<< HEAD

    public void onFocusChanged() {

    }
    public double caculateBMI(double h, double weight) {
        double height = h / 100;
        return weight / height / height;
    }
=======
>>>>>>> e5bccd2288e05db2f4f7d17dc96f1f8fb2e2153f
}
