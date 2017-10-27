package itac.yzu.bmi4;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public class AboutUsActivity extends AppCompatActivity {

    Bundle b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);
        b = this.getIntent().getExtras();
    }
    public void gotoMainPage(View v) {
        Intent MainIntent = new Intent();
        MainIntent.setClass(AboutUsActivity.this, MainActivity.class);
        startActivity(MainIntent);
        overridePendingTransition(R.animator.slide_from_left, R.animator.slide_to_right);
    }
    public void gotoIdealWeight(View v) {
        Intent IdealWeightIntent = new Intent();
        IdealWeightIntent.setClass(AboutUsActivity.this, IdealWeightActivity.class);
        if(b != null) {
            IdealWeightIntent.putExtras(b);
        }
        startActivity(IdealWeightIntent);
        overridePendingTransition(R.animator.slide_from_right, R.animator.slide_to_left);
    }
    public void exit(View v) {
        this.finish();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
