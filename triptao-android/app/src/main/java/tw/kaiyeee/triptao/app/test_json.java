package tw.kaiyeee.triptao.app;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tw.kaiyeee.triptao.R;

import static tw.kaiyeee.triptao.R.layout.content_main;
import static tw.kaiyeee.triptao.R.layout.list_view_layout;


public class test_json extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,SearchView.OnQueryTextListener{
    private final String TAG = "tripTao景點";
    private ListView listView;
    List<Map<String, Object>> SiteList = new ArrayList<Map<String, Object>>();
    private TabLayout mTabs;
    String[] site_Tag = {"景 點", "景 點", "景 點","景 點", "景 點", "景 點","景 點", "景 點", "景 點","景 點",
            "景 點", "景 點", "景 點","景 點", "景 點", "景 點","景 點", "景 點", "景 點","景 點",
            "景 點", "景 點", "景 點","景 點", "景 點", "景 點","景 點", "景 點", "景 點","景 點","景 點"};
    Integer[] site_pic = {R.drawable.d91, R.drawable.home02, R.drawable.home03,R.drawable.home04, R.drawable.home05, R.drawable.home06,
            R.drawable.home07, R.drawable.home08, R.drawable.home09,R.drawable.home10, R.drawable.home11, R.drawable.home12,
            R.drawable.home13, R.drawable.home14,R.drawable.home15, R.drawable.home16, R.drawable.home17,R.drawable.home18, R.drawable.home19,
            R.drawable.home20,R.drawable.home21, R.drawable.home22, R.drawable.home23, R.drawable.home24, R.drawable.home25, R.drawable.home26,R.drawable.home27, R.drawable.home28, R.drawable.home29, R.drawable.home30,R.drawable.home31};
    private ProgressDialog pDialog;
    private final static String CALL = "android.intent.action.CALL";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.list_view) ;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //new GetContacts ().execute();
        //mTabs = (TabLayout) findViewById(R.id.tabs);
        //mTabs.addTab(mTabs.newTab().setText("熱門"));
        //mTabs.addTab(mTabs.newTab().setText("精選"));

        String JsonString = loadJSONFromAsset();
        Gson gson = new Gson();
        Site[] mSite = gson.fromJson(loadJSONFromAsset(), Site[].class);
        //P.S.以上兩行就已經將資訊裝入People物件裡了，完成。
        for (int j = 0; j < mSite.length; j++) {
            //如果想要查看People物件內容，下方用Log印出資訊。
            Map<String, Object> Site_info = new HashMap<String, Object>();
            Site_info.put("Name", mSite[j].getName());
            Site_info.put("description", mSite[j].getdescription());
            Site_info.put("Tag", mSite[j].getCategory().getDistrict());
            Site_info.put("Picture", site_pic[j]);
            SiteList.add(Site_info);
        }
        ListAdapter adapter = new SimpleAdapter(
                test_json.this, SiteList, list_view_layout , new String[]{"Name", "description", "Tag","Picture"},
                new int[]{R.id.Title, R.id.Description, R.id.edt_area, R.id.site_image}) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                final Button btn =(Button)view.findViewById(R.id.Tag_btn);
                btn.setText(site_Tag[position]);
                return view;
            }

            ;
        };
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int itemsposition, long id){
                Intent home = new Intent(test_json.this, demo01_details_home.class);
                home.putExtra("position",Integer.toString(itemsposition));
                startActivity(home);


            }
        });

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
    @Override
    public void onBackPressed () {
        final AlertDialog.Builder builder = new AlertDialog.Builder(test_json.this);
        builder.setMessage("你確定要離開");
        builder.setCancelable(true);
        builder.setNegativeButton("再逛一會兒", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.cancel();
            }
        });
        builder.setPositiveButton("是的", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                finish();

            }
        });
            /*DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }*/
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }



    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as yu specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent call = new Intent(CALL, Uri.parse("tel:" + "0926726822"));
            startActivity(call);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected (MenuItem item){
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch(id) {
            case R.id.nav_site:
                Intent site = new Intent();
                site.setClass(this, test_json.class);
                startActivity(site);
                break;
            case R.id.nav_weather:
                Intent weather = new Intent();
                weather.setClass(this, weatherActivity.class);
                startActivity(weather);
                overridePendingTransition(R.animator.slide_to_right, R.animator.slide_from_left);
                break;
            default:
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);
        return true;
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

}
