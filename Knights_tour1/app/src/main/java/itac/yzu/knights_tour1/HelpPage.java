package itac.yzu.knights_tour1;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class HelpPage extends AppCompatActivity {

    public boolean started;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_page);
        started = false;
    }

    public void startNewGame(View v) {
        Intent newGame = new Intent(this, MainActivity.class);
        newGame.putExtra("RESUME", "FALSE");
        started = true;
        startActivity(newGame);
    }

    public void continueGame(View v) {
        if(!started)
            return;
        Intent continueGame = new Intent(this, MainActivity.class);
        continueGame.putExtra("RESUME", "TRUE");
        startActivity(continueGame);
    }
    public void endGame(View v) {
       /* Intent killtheKnight = new Intent();
        killtheKnight.putExtra("result",1);
        setResult(MainActivity.RESULT_OK, killtheKnight);*/
        MainActivity.kt.finish();
        finish();
    }
}
