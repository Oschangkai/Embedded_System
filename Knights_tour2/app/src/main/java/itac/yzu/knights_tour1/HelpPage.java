package itac.yzu.knights_tour1;

import android.app.Activity;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class HelpPage extends AppCompatActivity {


    public static Activity hp;
    public int difficulty = 3000;

    public boolean started;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_page);
        started = false;
        hp = this;
        Button con = (Button)findViewById(R.id.button2);
        con.setEnabled(false);

        final RadioGroup diffRG = (RadioGroup) findViewById(R.id.diffRadioGroup);
        diffRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int id = diffRG.getCheckedRadioButtonId();
                switch (id) {
                    case R.id.easyBtn:
                        difficulty = 3000;
                        break;
                    case R.id.mediumBtn:
                        difficulty = 1000;
                        break;
                    case R.id.hardBtn:
                        difficulty = 500;
                        break;
                    default:
                        break;
                }
            }
        });
    }



    public void startNewGame(View v) {
        if(MainActivity.kt != null)
            MainActivity.kt.finish();
        Intent newGame = new Intent(this, MainActivity.class);
        started = true;
        Button con = (Button)findViewById(R.id.button2);
        con.setEnabled(true);
        Bundle b = new Bundle();
        b.putInt("DIFFICULTY", difficulty);
        newGame.putExtras(b);
        startActivity(newGame);
        overridePendingTransition(R.animator.slide_from_right, R.animator.slide_to_left);
    }

    public void continueGame(View v) {
        Intent continueGame = new Intent(this, MainActivity.class);
        startActivity(continueGame);
        overridePendingTransition(R.animator.slide_from_right, R.animator.slide_to_left);
    }
    public void endGame(View v) {
        if(MainActivity.kt != null)
            MainActivity.kt.finish();
        finish();
    }

    @Override
    public void onResume() {
        super.onResume();

        if(getIntent().hasExtra("PAUSED")) {
            Bundle b = getIntent().getExtras();
            boolean p = b.getBoolean("PAUSED");
            Button newGame = (Button) findViewById(R.id.button);
            newGame.setEnabled(false);
            RadioButton rb = (RadioButton) findViewById(R.id.easyBtn);
            rb.setClickable(false);
            rb = (RadioButton) findViewById(R.id.mediumBtn);
            rb.setClickable(false);
            rb = (RadioButton) findViewById(R.id.hardBtn);
            rb.setClickable(false);
            Log.i("BUNDLE:", "not NULL");
        }
        else {
            Log.i("NULL", "NULL");
        }




        /*if(MainActivity.kt != null) {
            Button newGame = (Button) findViewById(R.id.newGameBtn);
            newGame.setEnabled(false);
            RadioButton rb = (RadioButton) findViewById(R.id.easyBtn);
            rb.setClickable(false);
            rb = (RadioButton) findViewById(R.id.mediumBtn);
            rb.setClickable(false);
            rb = (RadioButton) findViewById(R.id.hardBtn);
            rb.setClickable(false);
        }
        else {
            Button newGame = (Button) findViewById(R.id.newGameBtn);
            newGame.setEnabled(true);
            RadioButton rb = (RadioButton) findViewById(R.id.easyBtn);
            rb.setClickable(true);
            rb = (RadioButton) findViewById(R.id.mediumBtn);
            rb.setClickable(true);
            rb = (RadioButton) findViewById(R.id.hardBtn);
            rb.setClickable(true);
        }*/
    }
}
