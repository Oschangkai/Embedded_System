package tw.kaiyeee.triptao.app;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;

import tw.kaiyeee.triptao.R;


public class demo01_storeinfo_material extends AppCompatActivity {

    private CollapsingToolbarLayout collapsingToolbarLayout = null;
    String position  = null;
    TextView store_title;
    TextView store_address;
    TextView store_tel;
    TextView store_Openingtime;
    TextView store_descrip;
    ImageView profile_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_scrollview_toobar);
        position = getIntent().getStringExtra("position");
        profile_id = (ImageView)findViewById(R.id.profile_id);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("商家資訊");



        toolbarTextAppernce();
        store_title = (TextView) findViewById(R.id.Store_Title);
        store_address = (TextView) findViewById(R.id.edt_address);
        store_tel = (TextView) findViewById(R.id.edt_tel);
        store_Openingtime = (TextView) findViewById(R.id.edt_opentime);
        store_descrip = (TextView) findViewById(R.id.edt_descrip);
        //開啟json解析
        String JsonString = loadJSONFromAsset();
        Gson gson = new Gson();
        Site[] mSite = gson.fromJson(loadJSONFromAsset(), Site[].class);

            //如果想要查看People物件內容，下方用Log印出資訊。
        store_title.setText(mSite[Integer.parseInt(position)].getName());
        store_address.setText(mSite[Integer.parseInt(position)].getaddress());
        store_tel.setText(mSite[Integer.parseInt(position)].gettel());
        store_Openingtime.setText(mSite[Integer.parseInt(position)].getavailable_time());
        store_descrip.setText(mSite[Integer.parseInt(position)].getdescription());
        String img = mSite[Integer.parseInt(position)].getimg();
        int storeimg_Id = getResources().getIdentifier(img, "drawable", getPackageName());
        profile_id.setBackgroundResource(storeimg_Id);






    }


    private void toolbarTextAppernce() {
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expandedappbar);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
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