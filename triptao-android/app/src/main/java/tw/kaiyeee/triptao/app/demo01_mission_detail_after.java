package tw.kaiyeee.triptao.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import tw.kaiyeee.triptao.R;

/**
 * Created by Home on 2017/9/24.
 */

public class demo01_mission_detail_after extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo01_mission_design_after);
    }
    @Override
    public void onBackPressed () {
        Intent intent = new Intent(demo01_mission_detail_after.this, demo01_missionmapfragment.class);
        startActivity(intent);
        finish();
    }
}
