package itac.yzu.knights_tour1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class HelpPage extends AppCompatActivity {
    Intent newGame = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_page);
    }

    public void newGame(View v) {
        newGame.setClass(HelpPage.this, MainActivity.class);
        startActivity(newGame);
    }

    public void quit(View v) {
        finish();
    }
}

