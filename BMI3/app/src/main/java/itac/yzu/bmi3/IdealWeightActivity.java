package itac.yzu.bmi3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class IdealWeightActivity extends AppCompatActivity {
    // Find Elements in View
    TextView bmiTV;
    TextView ideal_weightTV;
    TextView bmi_resultTV;
    TextView ideal_weight_resultTV;
    TextView suggestionTV;

    String bmi;
    String ideal_weight;
    String bmi_result;
    String ideal_weight_result;
    String suggestion;

    Bundle b;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idealweight);

        b = this.getIntent().getExtras();

        bmiTV = (TextView)findViewById(R.id.bmiTV);
        ideal_weightTV = (TextView)findViewById(R.id.ideal_weightTV);
        bmi_resultTV = (TextView)findViewById(R.id.bmi_resultTV);
        ideal_weight_resultTV = (TextView)findViewById(R.id.ideal_weight_resultTV);
        suggestionTV = (TextView)findViewById(R.id.suggestionTV);

        if(b != null) {
            bmi = b.getString("bmi");
            ideal_weight = b.getString("ideal_weight");
            bmi_result = b.getString("bmi_result");
            ideal_weight_result = b.getString("ideal_weight_result");
            suggestion = b.getString("suggestion");
        }


        bmiTV.setText(bmi);
        ideal_weightTV.setText(ideal_weight);
        bmi_resultTV.setText(bmi_result);
        ideal_weight_resultTV.setText(ideal_weight_result);
        suggestionTV.setText(suggestion);

    }

    // Buttons
    public void gotoMainPage(View v) {
        Intent MainIntent = new Intent();
        MainIntent.setClass(IdealWeightActivity.this, MainActivity.class);
        startActivity(MainIntent);
    }
    public void gotoAboutUs(View v) {
        Intent aboutUsIntent = new Intent();
        aboutUsIntent.setClass(IdealWeightActivity.this, AboutUsActivity.class);
        aboutUsIntent.putExtras(b);
        startActivity(aboutUsIntent);
    }


}
