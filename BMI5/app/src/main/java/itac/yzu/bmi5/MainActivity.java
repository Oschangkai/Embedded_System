package itac.yzu.bmi5;

import android.content.SharedPreferences;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    // Find Elements in View
    EditText heightET;
    EditText weightET;

    Button resetBTN;

    TextView bmiTV;
    TextView ideal_weightTV;
    TextView bmi_resultTV;
    TextView ideal_weight_resultTV;
    TextView suggestionTV;

    RadioGroup genderRadioGroup;

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

        bmiTV = (TextView)findViewById(R.id.bmiTV);
        ideal_weightTV = (TextView)findViewById(R.id.ideal_weightTV);
        bmi_resultTV = (TextView)findViewById(R.id.bmi_resultTV);
        ideal_weight_resultTV = (TextView)findViewById(R.id.ideal_weight_resultTV);
        suggestionTV = (TextView)findViewById(R.id.suggestionTV);

        genderRadioGroup = (RadioGroup)findViewById(R.id.genderRadioGroup);

        gender = 'm';

        // Set Default Value
        View view = findViewById(android.R.id.content);
        resetAll(view);

        heightET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(isOkToShowText()) {
                    double height = Double.parseDouble(heightET.getText().toString());
                    double weight = Double.parseDouble(weightET.getText().toString());
                    double BMI = calculateBMI(height, weight);
                    bmiTV.setText(String.format("%.1f", BMI));
                    calculateIdeal(height, weight, gender);
                    BMIText(BMI);
                } else resetSuggestion();
            }
        });

        weightET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(isOkToShowText()) {
                    double height = Double.parseDouble(heightET.getText().toString());
                    double weight = Double.parseDouble(weightET.getText().toString());
                    double BMI = calculateBMI(height, weight);
                    bmiTV.setText(String.format("%.1f", BMI));
                    calculateIdeal(height, weight, gender);
                    BMIText(BMI);
                } else resetSuggestion();
            }

        });

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
                if(isOkToShowText()) {
                    double height = Double.parseDouble(heightET.getText().toString());
                    double weight = Double.parseDouble(weightET.getText().toString());
                    double BMI = calculateBMI(height, weight);
                    bmiTV.setText(String.format("%.1f", BMI));
                    calculateIdeal(height, weight, gender);
                    BMIText(BMI);
                } else resetSuggestion();
            }
        });

    }

    // Reset all TextView
    public void resetAll(View v) {
        heightET.setText("0");
        weightET.setText("0");

        bmiTV.setText("");
        ideal_weightTV.setText("");
        bmi_resultTV.setText("");
        ideal_weight_resultTV.setText("");
        suggestionTV.setText("");
    }

    public void resetSuggestion() {
        bmiTV.setText("");
        ideal_weightTV.setText("");
        bmi_resultTV.setText("");
        ideal_weight_resultTV.setText("");
        suggestionTV.setText("");
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

    // Calculate BMI
    public double calculateBMI(double h, double weight) {
        double height = h / 100;
        return weight / height / height;
    }

    // Calculate ideal weight
    public void calculateIdeal(double height, double weight, char g) {
        switch(g) {
            case 'm':
                ideal_weightTV.setText(String.format("%.1f", (height - 80) * 0.7));
                IdealText(weight, (height - 80) * 0.7);
                break;
            case 'f':
                ideal_weightTV.setText(String.format("%.1f", (height - 70) * 0.6));
                IdealText(weight, (height - 70) * 0.6);
                break;
        }
    }

    public void BMIText(double BMI) {
        if(BMI < bmiLevel[0]) {
            bmi_resultTV.setText(bmiResult[0]);
            suggestionTV.setText(suggestion[0]);
        }
        else if(BMI >= bmiLevel[0] && BMI < bmiLevel[1] ) {
            bmi_resultTV.setText(bmiResult[1]);
            suggestionTV.setText(suggestion[1]);
        }
        else if(BMI >= bmiLevel[1] && BMI < bmiLevel[2]) {
            bmi_resultTV.setText(bmiResult[2]);
            suggestionTV.setText(suggestion[2]);
        }
        else if(BMI >= bmiLevel[2]) {
            bmi_resultTV.setText(bmiResult[3]);
            if(BMI < bmiLevel[3]) {
                suggestionTV.setText(suggestion[3]);
            }
            else if (BMI >= bmiLevel[3] && BMI < bmiLevel[4]) {
                suggestionTV.setText(suggestion[4]);
            }
            else if(BMI >= bmiLevel[4]) {
                suggestionTV.setText(suggestion[5]);
            }
        }
    }

    public void IdealText(double w, double iw) {
        if(w <= iw * 1.1 && w >= iw *0.9) {
            ideal_weight_resultTV.setText(idealWeightResult[0]);
        }

        else if(w > iw * 1.1 && w <= iw * 1.2) {
            ideal_weight_resultTV.setText(idealWeightResult[1]);
        }

        else if(w > iw * 1.2) {
            ideal_weight_resultTV.setText(idealWeightResult[2]);
        }
        else if(w < iw * 0.9 && w >= iw * 0.8) {
            ideal_weight_resultTV.setText(idealWeightResult[3]);
        }
        else if(w < iw * 0.8) {
            ideal_weight_resultTV.setText(idealWeightResult[4]);
        }
    }
    int save1Btn = R.id.save1BTN;
    int load1Btn = R.id.load1BTN;

    public void saveBtnClicked(View v) {
        if(!isOkToShowText()) {
            Toast.makeText(v.getContext(), "INPUT IS NOT COMPLETE!", Toast.LENGTH_SHORT).show();
            return;
        }
        String User = v.getId() == save1Btn? "User1":"User2";
        SharedPreferences data = getSharedPreferences(User, 0);
        SharedPreferences.Editor editor = data.edit();
        editor.putString("height", heightET.getText().toString());
        editor.putString("weight", weightET.getText().toString());
        editor.putString("bmi", bmiTV.getText().toString());
        editor.putString("ideal_weight", ideal_weightTV.getText().toString());
        editor.putString("bmi_result", bmi_resultTV.getText().toString());
        editor.putString("ideal_weight_result", ideal_weight_resultTV.getText().toString());
        editor.putString("suggestion", suggestionTV.getText().toString());
        editor.putInt("gender", genderRadioGroup.getCheckedRadioButtonId());
        editor.commit();
        Toast.makeText(v.getContext(), "SAVED", Toast.LENGTH_SHORT).show();
    }
    public void loadBtnClicked(View v) {
        String User = v.getId() == load1Btn? "User1":"User2";
        SharedPreferences data = getSharedPreferences(User, 0);
        if(data.getString("height", "0") == "0") {
            Toast.makeText(v.getContext(), "NO SAVED DATA!", Toast.LENGTH_SHORT).show();
            return;
        }
        heightET.setText(data.getString("height", "0"));
        weightET.setText(data.getString("weight", "0"));
        bmiTV.setText(data.getString("bmi", ""));
        ideal_weightTV.setText(data.getString("ideal_weight", ""));
        bmi_resultTV.setText(data.getString("bmi_result", ""));
        ideal_weight_resultTV.setText(data.getString("ideal_weight_result", ""));
        suggestionTV.setText(data.getString("suggestion", ""));
        genderRadioGroup.check(data.getInt("gender", R.id.maleRadioBtn));
        Toast.makeText(v.getContext(), "LOADED", Toast.LENGTH_SHORT).show();
    }

    public void debug(View v) {
        SharedPreferences u1 = getSharedPreferences("User1", 0);
        SharedPreferences u2 = getSharedPreferences("User2", 0);
        SharedPreferences.Editor edit = u1.edit();
        edit.clear();
        edit.commit();
        edit = u2.edit();
        edit.clear();
        edit.commit();
    }
}
