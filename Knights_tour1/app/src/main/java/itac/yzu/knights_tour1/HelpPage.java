package itac.yzu.knights_tour1;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class HelpPage extends AppCompatActivity {


    public static Activity hp;

    public boolean started;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_page);
        started = false;
        hp = this;
        Button con = (Button)findViewById(R.id.button2);
        con.setEnabled(false);
    }

    public void startNewGame(View v) {
        if(MainActivity.kt != null)
            MainActivity.kt.finish();
        Intent newGame = new Intent(this, MainActivity.class);
        //newGame.putExtra("RESUME", "FALSE");
        started = true;
        Button con = (Button)findViewById(R.id.button2);
        con.setEnabled(true);

        startActivity(newGame);
        overridePendingTransition(R.animator.slide_from_right, R.animator.slide_to_left);
    }

    public void continueGame(View v) {
        if(!started)
            return;
       // Bundle b = new Bundle();
        //b.putString("RESUME", "TRUE");
        Intent continueGame = new Intent(this, MainActivity.class);
       // continueGame.putExtras(b);
        //continueGame.putExtra("RESUME", "TRUE");
        startActivity(continueGame);
        overridePendingTransition(R.animator.slide_from_right, R.animator.slide_to_left);
    }
    public void endGame(View v) {
       /* Intent killtheKnight = new Intent();
        killtheKnight.putExtra("result",1);
        setResult(MainActivity.RESULT_OK, killtheKnight);*/
        if(MainActivity.kt != null)
            MainActivity.kt.finish();
        finish();
    }
}
