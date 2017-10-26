package itac.yzu.bmi3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class IdealWeightActivity extends AppCompatActivity {
    // Find Elements in View
    TextView bmiTV;
    TextView ideal_weightTV;
    TextView bmi_resultTV;
    TextView ideal_weight_resultTV;
    TextView suggestionTV;

    Intent i = this.getIntent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idealweight);

        bmiTV = (TextView)findViewById(R.id.bmiTV);
        ideal_weightTV = (TextView)findViewById(R.id.ideal_weightTV);
        bmi_resultTV = (TextView)findViewById(R.id.bmi_resultTV);
        ideal_weight_resultTV = (TextView)findViewById(R.id.ideal_weight_resultTV);
        suggestionTV = (TextView)findViewById(R.id.suggestionTV);

        bmiTV.setText(i.getStringExtra("bmi"));
        ideal_weightTV.setText(i.getStringExtra("ideal_weight"));
        bmi_resultTV.setText(i.getStringExtra("bmi_result"));
        ideal_weight_resultTV.setText(i.getStringExtra("ideal_weight_result"));
        suggestionTV.setText(i.getStringExtra("suggestion"));

    }

}
