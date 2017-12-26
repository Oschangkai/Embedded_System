package tw.kaiyeee.triptao.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import tw.kaiyeee.triptao.R;

/**
 * Created by Home on 2017/9/24.
 */

public class demo01_mission_detail_before extends AppCompatActivity {
    Button button;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo01_mission_detail_before);
        button = (Button) findViewById(R.id.attend);
        button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v2) {
                Intent intent = new Intent(demo01_mission_detail_before.this, demo01_mission_detail_after.class);
                startActivity(intent);
                finish();
            }
        });
    }
    @Override
    public void onBackPressed () {
        Intent intent = new Intent(demo01_mission_detail_before.this, demo01_mission_page.class);
        startActivity(intent);
        finish();
    }
}

