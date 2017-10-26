package itac.yzu.bmi3;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;



public class MainActivity extends AppCompatActivity {
    // Find Elements in View
    EditText heightET;
    EditText weightET;

    Button resetBTN;

    RadioGroup genderRadioGroup;

    Intent idealWeightIntent = new Intent();
    Intent aboutUsIntent = new Intent();

    // Definitions
    double[] bmiLevel = {18.5, 24, 27, 30, 35};
    String[] suggestion = {
            "「體重過輕」，需要多運動，均衡飲食，\n以增加體能，維持健康！",
            "「健康體重」，要繼續保持喔！",
            "「體重過重」，要小心囉，\n趕快力行健康體重管理吧！",
            "「輕度肥胖」，\n需要立刻力行健康體重管理喔！",
            "「中度肥胖」，\n需要立刻力行健康體重管理喔！",
            "「重度肥胖」，\n需要立刻力行健康體重管理喔！"};
    String[] bmiResult = { "體重過輕", "體重正常", "體重過重", "體重肥胖" };
    String[] idealWeightResult = { "體重正常", "體重過重", "肥胖", "體重過輕", "體重不足" };
    char gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        heightET = (EditText)findViewById(R.id.heightET);
        weightET = (EditText)findViewById(R.id.weightET);

        resetBTN = (Button)findViewById(R.id.resetBTN);

        genderRadioGroup = (RadioGroup)findViewById(R.id.genderRadioGroup);

        gender = 'm';

        // Set Default Value
        View view = findViewById(android.R.id.content);
        resetAll(view);

        genderRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                int id = genderRadioGroup.getCheckedRadioButtonId();
                switch (id) {
                    case R.id.maleRadioBtn:
                        gender = 'm';
                        break;
                    case R.id.femaleRadioBtn:
                        gender = 'f';
                        break;
                }
            }
        });

    }

    // Reset all TextView
    public void resetAll(View v) {
        heightET.setText("0");
        weightET.setText("0");
    }

    public void resetSuggestion() {
        idealWeightIntent.putExtra("bmi", "");
        idealWeightIntent.putExtra("ideal_weight", "");
        idealWeightIntent.putExtra("bmi_result", "");
        idealWeightIntent.putExtra("ideal_weight_result", "");
        idealWeightIntent.putExtra("suggestion", "");
    }


    public boolean isOkToShowText() {
        if(heightET.getText().toString().equals(""))
            return false;
        if(weightET.getText().toString().equals(""))
            return false;
        if(heightET.getText().toString().equals("0"))
            return false;
        if(weightET.getText().toString().equals("0"))
            return false;
        return true;
    }
    // Buttons
    public void gotoIdealWeightIntent(View v) {
        if(isOkToShowText()) {
            idealWeightIntent.setClass(MainActivity.this, IdealWeightActivity.class);
            double height = Double.parseDouble(heightET.getText().toString());
            double weight = Double.parseDouble(weightET.getText().toString());
            double BMI = calculateBMI(height, weight);
            idealWeightIntent.putExtra("bmi", String.valueOf(BMI));
            calculateIdeal(height, weight, gender);
            BMIText(BMI);
            startActivity(idealWeightIntent);
        } else resetSuggestion();
    }
    public void gotoAboutUs(View v) {
        aboutUsIntent.setClass(MainActivity.this, AboutUsActivity.class);
        startActivity(aboutUsIntent);
    }

    // Calculate BMI
    public double calculateBMI(double h, double weight) {
        double height = h / 100;
        return weight / height / height;
    }

    // Calculate ideal weight
    public void calculateIdeal(double height, double weight, char g) {
        switch(g) {
            case 'm':
                idealWeightIntent.putExtra("ideal_weight", String.format("%.1f", (height - 80) * 0.7));
                IdealText(weight, (height - 80) * 0.7);
                break;
            case 'f':
                idealWeightIntent.putExtra("ideal_weight", String.format("%.1f", (height - 70) * 0.6));
                IdealText(weight, (height - 70) * 0.6);
                break;
        }
    }

    public void BMIText(double BMI) {
        if(BMI < bmiLevel[0]) {
            idealWeightIntent.putExtra("bmi_result", bmiResult[0]);
            idealWeightIntent.putExtra("suggestion", suggestion[0]);
        }
        else if(BMI >= bmiLevel[0] && BMI < bmiLevel[1] ) {
            idealWeightIntent.putExtra("bmi_result", bmiResult[1]);
            idealWeightIntent.putExtra("suggestion", suggestion[1]);
        }
        else if(BMI >= bmiLevel[1] && BMI < bmiLevel[2]) {
            idealWeightIntent.putExtra("bmi_result", bmiResult[2]);
            idealWeightIntent.putExtra("suggestion", suggestion[2]);
        }
        else if(BMI >= bmiLevel[2]) {
            idealWeightIntent.putExtra("bmi_result", bmiResult[3]);
            if(BMI < bmiLevel[3]) {
                idealWeightIntent.putExtra("suggestion", suggestion[3]);
            }
            else if (BMI >= bmiLevel[3] && BMI < bmiLevel[4]) {
                idealWeightIntent.putExtra("suggestion", suggestion[4]);
            }
            else if(BMI >= bmiLevel[4]) {
                idealWeightIntent.putExtra("suggestion", suggestion[5]);
            }
        }
    }

    public void IdealText(double w, double iw) {
        if(w <= iw * 1.1 && w >= iw *0.9) {
            idealWeightIntent.putExtra("ideal_weight_result", idealWeightResult[0]);
        }

        else if(w > iw * 1.1 && w <= iw * 1.2) {
            idealWeightIntent.putExtra("ideal_weight_result", idealWeightResult[1]);
        }

        else if(w > iw * 1.2) {
            idealWeightIntent.putExtra("ideal_weight_result", idealWeightResult[2]);
        }
        else if(w < iw * 0.9 && w >= iw * 0.8) {
            idealWeightIntent.putExtra("ideal_weight_result", idealWeightResult[3]);
        }
        else if(w < iw * 0.8) {
            idealWeightIntent.putExtra("ideal_weight_result", idealWeightResult[4]);
        }
    }

}
