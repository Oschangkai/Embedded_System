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

public class demo01_mission_page extends AppCompatActivity {
    Button button ;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo01_mission_design);
        button = (Button)findViewById(R.id.button2);
        button.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v2){
                Intent intent =new Intent(demo01_mission_page.this,demo01_mission_detail_before.class);
                startActivity(intent);
            }
        });


    }
}