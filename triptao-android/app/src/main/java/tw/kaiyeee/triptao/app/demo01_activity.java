package tw.kaiyeee.triptao.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tw.kaiyeee.triptao.R;

import static tw.kaiyeee.triptao.R.layout.demo01_activitylistview;


public class demo01_activity extends AppCompatActivity {
    List<Map<String, Object>> SiteList = new ArrayList<Map<String, Object>>();
    String position  = null;
    TextView Name;
    TextView Time;
    ListView activity_item;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo01_activitylist);
        position = getIntent().getStringExtra("position");
        String JsonString = loadJSONFromAsset();
        activity_item = (ListView)findViewById(R.id.activity_item) ;
        Name =(TextView)findViewById(R.id.name);
        Time =(TextView)findViewById(R.id.time);
        //解析json
        Gson gson = new Gson();
        Site[] mSite = gson.fromJson(loadJSONFromAsset(), Site[].class);
        //P.S.以上兩行就已經將資訊裝入People物件裡了，完成。
        for (int j = 0; j < mSite[Integer.parseInt(position)].getEvent().length; j++) {
            //如果想要查看People物件內容，下方用Log印出資訊。
            Map<String, Object> Event_info = new HashMap<String, Object>();
            Event_info.put("Name", mSite[Integer.parseInt(position)].getEvent()[j].getName());
            Event_info.put("Time", mSite[Integer.parseInt(position)].getEvent()[j].getTime());
            SiteList.add(Event_info);
        }
        ListAdapter adapter = new SimpleAdapter(
                demo01_activity.this, SiteList, demo01_activitylistview , new String[]{"Name", "Time"},
                new int[]{R.id.name, R.id.time});

            //如果想要查看People物件內容，下方用Log印出資訊。

        activity_item.setAdapter(adapter);
    }





    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("30data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }





}
