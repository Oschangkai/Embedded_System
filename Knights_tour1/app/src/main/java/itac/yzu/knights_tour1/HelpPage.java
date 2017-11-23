package itac.yzu.knights_tour1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class HelpPage extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_page);
    }

    public void startNewGame(View v) {
        Intent newGame = new Intent(this, MainActivity.class);
        startActivity(newGame);
    }

    public void continueGame (View v) {
        Intent continueGame = new Intent(this, MainActivity.class);
        startActivity(continueGame);
    }
    public void endGame(View v) {
        finish();
    }
}
