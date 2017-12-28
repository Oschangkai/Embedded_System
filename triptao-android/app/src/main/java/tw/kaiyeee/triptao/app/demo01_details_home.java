package tw.kaiyeee.triptao.app;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import tw.kaiyeee.triptao.R;




public class demo01_details_home extends AppCompatActivity {
    private GoogleMap mMap;
    private MapView mapView;
    List<Map<String, Object>> SiteList = new ArrayList<Map<String, Object>>();
    /*FloatingActionMenu materialDesignFAM;*/
    FloatingActionButton Go;
    CardView cd_store_info;
    CardView cd_weather;
    CardView cd_limitactivity;


    String position  = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_scrollview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        position = getIntent().getStringExtra("position");





        //再次開啟json檔案
        final String JsonString = loadJSONFromAsset();





        Gson gson = new Gson();
        final Site[] mSite = gson.fromJson(loadJSONFromAsset(), Site[].class);

        //P.S.以上兩行就已經將資訊裝入People物件裡了，完成。
            //如果想要查看People物件內容，下方用Log印出資訊。
            final String login = mSite[Integer.parseInt(position)].getLogin();
            final String toolbar_Name =mSite[Integer.parseInt(position)].getName();
            final String lat = mSite[Integer.parseInt(position)].getLatlng().getlat();
            final String lng = mSite[Integer.parseInt(position)].getLatlng().getlng();
            final String weather = mSite[Integer.parseInt(position)].getWeather();
            final String img = mSite[Integer.parseInt(position)].getimg();

        //取得完資料後改toolbar標頭
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(toolbar_Name);

        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                // Add a marker in Sydney, Australia, and move the camera.
                LatLng mapview = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
                mMap.addMarker(new MarkerOptions().position(mapview).title(toolbar_Name));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mapview,18));
            }
        });

        Go   = (FloatingActionButton) findViewById(R.id.floating_go);


        Go.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(login) ==  1) {
                    Intent Map_load = new Intent(demo01_details_home.this, demo01_missionmapfragment.class);
                    startActivity(Map_load,
                            ActivityOptions.makeSceneTransitionAnimation(
                                    demo01_details_home.this).toBundle());
                }
                else {

                    final AlertDialog.Builder builder = new AlertDialog.Builder(demo01_details_home.this);
                    builder.setTitle("Oops!非常對不起<(_ _)>");
                    builder.setMessage("阿呀，不好意思"+"但\n"+toolbar_Name+"似乎沒有申請triptao憩桃哦哦:D");
                    builder.setCancelable(true);
                    builder.setPositiveButton("好的我知道了", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {

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
            }

        });

        cd_store_info = (CardView) findViewById(R.id.store_info);
        cd_store_info.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent store_info_load = new Intent(demo01_details_home.this,demo01_storeinfo_material.class);
                store_info_load.putExtra("position",position);
                startActivity(store_info_load,
                        ActivityOptions.makeSceneTransitionAnimation(
                                demo01_details_home.this).toBundle());

            }

        });
        cd_limitactivity = (CardView) findViewById(R.id.limit_activity);
        cd_limitactivity.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent store_activity_load = new Intent(demo01_details_home.this,demo01_activity.class);
                store_activity_load.putExtra("position",position);
                startActivity(store_activity_load);


            }

        });
        cd_weather = (CardView) findViewById(R.id.weather_view);

        cd_weather.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder ad = new AlertDialog.Builder(demo01_details_home.this)
                        .setTitle("天氣資訊")
                        .setPositiveButton("朕知道了", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'");
                Date dateTimeNow = new Date(System.currentTimeMillis());
                String time = formatter.format(dateTimeNow);

                Calendar c = Calendar.getInstance();
                Integer h = c.get(Calendar.HOUR_OF_DAY);
                h = h % 3 == 0 ? h : (h - h%3);
                time += h.toString().length() < 2 ? "0" + h.toString() : h.toString();
                final String API = "https://opendata.cwb.gov.tw/api/v1/rest/datastore/F-D0047-005?&startTime=" + time + ":00:00&Authorization=CWB-0130401E-B5EC-461A-B588-AE3D0A070A01";
                // data.records.locations[0]
                // find: elementName = CI
                // get: elementValue as Temperature, parameter[0].parameterValue as feeling
                final ProgressDialog mProgressDialog = ProgressDialog.show(demo01_details_home.this, "", "正在連線氣象局");
                AndroidNetworking.get(API)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
//                                // 關閉進度對話框
                                mProgressDialog.dismiss();
                                try {
                                    JSONObject ja = response.getJSONObject("records")
                                            .getJSONArray("locations").getJSONObject(0)
                                            .getJSONArray("location").getJSONObject(0)
                                            .getJSONArray("weatherElement").getJSONObject(6)
                                            .getJSONArray("time").getJSONObject(0);
                                    String weather, rainning, tempo, feeling, mos, txtBroadcast;
                                    String[] weatherString;
                                    weatherString = ja.getString("elementValue").split("。");
                                    weather = weatherString[0].trim();
                                    rainning = weatherString[1].trim();
                                    tempo = weatherString[2].trim();
                                    feeling = weatherString[3].trim();
                                    mos = weatherString[5].trim();
                                    txtBroadcast = "現在外面" + weather + "\n"
                                            + rainning + "\n"
                                            + tempo + "\n"
                                            + "感覺" + feeling + "\n"
                                            + mos;
                                    ad.setMessage(txtBroadcast).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                Toast.makeText(demo01_details_home.this, "SUS", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(ANError anError) {
                                mProgressDialog.dismiss();
                                Toast.makeText(demo01_details_home.this, "ERR", Toast.LENGTH_SHORT).show();
                            }
                        });

            }

        });
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
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
