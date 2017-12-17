package itac.yzu.bmi6;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class DataListActivity extends AppCompatActivity {

    DBHelper db = new DBHelper(this);
    ArrayList<UserProfile> up;
    private UserProfileAdapter adapter;

    ListView lsv;

    boolean mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_list);
        this.setTitle("已儲存的資料");

        // GET MODE: TRUE = LOAD MODE, FALSE = DELETE MODE
        Bundle b = this.getIntent().getExtras();
        mode = b.getString("MODE").equals("LOAD");

        lsv = (ListView)findViewById(R.id.dataList);
        lsv.setEmptyView(findViewById(R.id.nodataTV));

        //
        up = db.getAllUserProfile();
        adapter = new UserProfileAdapter(DataListActivity.this, up, mode);
        lsv.setAdapter(adapter);


        if(mode) {
            Toast.makeText(this, "點選讀取資料", Toast.LENGTH_SHORT).show();
        } else Toast.makeText(this, "點選刪除資料", Toast.LENGTH_SHORT).show();

    }
}
