package itac.yzu.bmi6;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

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

        // Set
        up = db.getAllUserProfile();
        adapter = new UserProfileAdapter(DataListActivity.this, up);
        lsv.setAdapter(adapter);
        lsv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> adapterView, View view, int position, long id) {
                final UserProfile selected = new UserProfile(
                        ((TextView)view.findViewById(R.id.nameTV)).getText().toString(),
                        ((TextView)view.findViewById(R.id.genderTV)).getText().toString(),
                        Double.valueOf(((TextView)view.findViewById(R.id.heightTV)).getText().toString()),
                        Double.valueOf(((TextView)view.findViewById(R.id.weightTV)).getText().toString())
                );
                AlertDialog.Builder dialog = new AlertDialog.Builder(DataListActivity.this);
                if(mode) {
                  // LOAD MODE
                  Bundle b = new Bundle();
                  b.putString("name", selected.getName());
                  b.putString("gender", selected.getGender());
                  b.putDouble("height", selected.getHeight());
                  b.putDouble("weight", selected.getWeight());
                  Intent i = new Intent(DataListActivity.this, MainActivity.class);
                  i.putExtras(b);
                  startActivity(i);
                } else {
                    // DELETE MODE
                    dialog.setTitle("刪除");
                    dialog.setMessage("你確定要刪除「" + selected.getName() + "」?");
                    dialog.setNegativeButton("不要", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(DataListActivity.this, "「" + selected.getName() + "」不會被刪除", Toast.LENGTH_SHORT).show();
                        }
                    });
                    dialog.setPositiveButton("好", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            db.deleteUserProfile(selected);
                            Toast.makeText(DataListActivity.this, "「" + selected.getName() + "」已刪除", Toast.LENGTH_SHORT).show();
                            adapter.updateView();
                        }
                    });
                    dialog.show();
                }
            }
        });


        if(mode) {
            Toast.makeText(this, "點選可以讀取資料", Toast.LENGTH_SHORT).show();
        } else Toast.makeText(this, "點選可以刪除資料", Toast.LENGTH_SHORT).show();

    }
}
