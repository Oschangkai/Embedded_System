package itac.yzu.bmi6;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    // Find Elements in View
    EditText nameET;
    EditText heightET;
    EditText weightET;

    Button resetBTN;

    TextView bmiTV;
    TextView ideal_weightTV;
    TextView bmi_resultTV;
    TextView ideal_weight_resultTV;
    TextView suggestionTV;

    RadioGroup genderRadioGroup;

    String n;
    String g;
    Double h;
    Double w;
    DBHelper db = new DBHelper(this);

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

        nameET = (EditText)findViewById(R.id.nameET);
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

        nameET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                showSuggestion();
            }
        });

        heightET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                showSuggestion();
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
                showSuggestion();
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
                showSuggestion();
            }
        });

        // Show data if bundle exists (it means, loaded button clicked)
        Bundle b;
        b = this.getIntent().getExtras();
        try {
            n = b.getString("name");
            g = b.getString("gender");
            h = b.getDouble("height");
            w = b.getDouble("weight");
            nameET.setText(n);
            genderRadioGroup.check( g.equals("M") ? R.id.maleRadioBtn: R.id.femaleRadioBtn);
            heightET.setText(String.valueOf(h));
            weightET.setText(String.valueOf(w));
            Toast.makeText(MainActivity.this, "LOADED", Toast.LENGTH_SHORT).show();
        } catch (Exception e) { }

    }

    // Reset all TextView
    public void resetAll(View v) {
        nameET.setText("");
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

    // Show Suggestion
    public boolean isOkToShowSuggestion() {
        if(nameET.getText().toString().equals(""))
            return false;
        if(heightET.getText().toString().equals(""))
            return false;
        if(weightET.getText().toString().equals(""))
            return false;
        if(heightET.getText().toString().equals("0"))
            return false;
        if(weightET.getText().toString().equals("0"))
            return false;
        setValues();
        return true;
    }

    public void showSuggestion() {
        if(isOkToShowSuggestion()) {
            double height = Double.parseDouble(heightET.getText().toString());
            double weight = Double.parseDouble(weightET.getText().toString());
            double BMI = calculateBMI(height, weight);
            bmiTV.setText(String.format("%.1f", BMI));
            calculateIdeal(height, weight, gender);
            BMIText(BMI);
        } else resetSuggestion();
    }

    public void setValues() {
        n = nameET.getText().toString();
        g = gender == 'm' ? "M": "F";
        h = Double.parseDouble(heightET.getText().toString());
        w = Double.parseDouble(weightET.getText().toString());
    }

    // Calculation
    public double calculateBMI(double h, double weight) {
        double height = h / 100;
        return weight / height / height;
    }

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

    // Button Clicked
    public void saveBtnClicked(View v) {
        if(!isOkToShowSuggestion()) {
            Toast.makeText(v.getContext(), "INPUT IS NOT COMPLETE!", Toast.LENGTH_SHORT).show();
            return;
        }
        final UserProfile u = new UserProfile(n, g, h, w);
        if(db.UserProfileExists(n)) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
            dialog.setTitle("資料已存在");
            dialog.setMessage("是否要覆蓋資料");
            dialog.setNegativeButton("不要", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(MainActivity.this, "資料將不會被覆蓋", Toast.LENGTH_SHORT).show();
                }
            });
            dialog.setPositiveButton("好的", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    db.updateUserProfile(u);
                    Toast.makeText(MainActivity.this, "資料已更新", Toast.LENGTH_SHORT).show();
                }
            });
            dialog.show();

        } else {
            db.addUserProfile(u);
            Toast.makeText(MainActivity.this, "資料已儲存", Toast.LENGTH_SHORT).show();
        }
    }

    public void loadBtnClicked(View v) {
        Intent i = new Intent(this, DataListActivity.class);
        Bundle b = new Bundle();
        b.putString("MODE", "LOAD");
        i.putExtras(b);
        startActivity(i);
    }

    public void deleteBtnClicked(View v) {
        Intent i = new Intent(this, DataListActivity.class);
        Bundle b = new Bundle();
        b.putString("MODE", "DELETE");
        i.putExtras(b);
        startActivity(i);
    }

    public void debugBtnClicked(View v) {
        db.clearDB();
    }
}
