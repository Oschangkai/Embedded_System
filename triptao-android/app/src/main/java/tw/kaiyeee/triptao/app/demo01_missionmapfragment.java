package tw.kaiyeee.triptao.app;

/**
 * Created by Home on 2017/9/23.
 */


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import tw.kaiyeee.triptao.R;

public class demo01_missionmapfragment extends FragmentActivity implements OnMapReadyCallback {
    AlertDialog dialog;
    private GoogleMap mMap;
    FloatingActionButton ticket, mission,message;
    //快訊
    ListView limit_message=null;

    //各點座標
    LatLng demo01_site = new LatLng(25.001814, 121.327562);
    LatLng Dinosaur = new LatLng(25.001882,121.3277573);
    LatLng site_frontdoor = new LatLng(25.0019222, 121.327675);
    LatLng owl = new LatLng(25.001900, 121.3275611);
    LatLng the_ship = new LatLng(25.0018667, 121.3275611);
    LatLng slide = new LatLng(25.0018167, 121.3275972);
    LatLng kangaroo = new LatLng(25.0019028, 121.3276065);
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo01_map);
        ticket  = (FloatingActionButton) findViewById(R.id.floating_ticket);
        mission  = (FloatingActionButton) findViewById(R.id.floating_mission);
        message  = (FloatingActionButton) findViewById(R.id.floating_message);
        //快訊
        limit_message =new ListView(this);
        final String[] message_items={"14:00-16:00豪雨特報","17:00環山步道有蛇出沒","13:00-17:00社務所展覽開放"};
        AlertDialog.Builder dialog_list = new AlertDialog.Builder(demo01_missionmapfragment.this);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.limit_message_listitem,R.id.txtitem,message_items);
        limit_message.setAdapter(adapter);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        ticket.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent ticket_load = new Intent(demo01_missionmapfragment.this,demo01_ticket_page.class);
                startActivity(ticket_load);

            }

        });
        mission.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent mission_load = new Intent(demo01_missionmapfragment.this,demo01_mission_page.class);
                startActivity(mission_load);

            }

        });
        message.setOnClickListener(new android.view.View.OnClickListener() {
            AlertDialog.Builder builder = new AlertDialog.Builder(demo01_missionmapfragment.this);
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog_list = new AlertDialog.Builder(demo01_missionmapfragment.this);
                dialog_list.setTitle("快訊");
                dialog_list.setItems(message_items, new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which) {
                        if(which == 0 ) {
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(owl, 25), 2000, null);
                            mMap.addMarker(new MarkerOptions()
                                    .position(owl)
                                    .title(" 豪雨特報")).showInfoWindow();
                        }
                        if(which == 1 ) {
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(the_ship, 25), 2000, null);
                            mMap.addMarker(new MarkerOptions()
                                    .position(the_ship)
                                    .title("環山步道有蛇出沒")).showInfoWindow();
                        }
                        if(which == 2 ) {
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Dinosaur, 25), 2000, null);
                            mMap.addMarker(new MarkerOptions()
                                    .position(Dinosaur)
                                    .title("社務所展覽開放")).showInfoWindow();
                        }
                    }
                });
                dialog_list.show();
            }

        });


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        //HYBIRD混合
        //NORMAL一般
        //TERRIAN地形

        // Add a marker in Sydney and move the camera


        mMap.addMarker(new MarkerOptions().position(Dinosaur).title("恐龍雕像"));
        mMap.addMarker(new MarkerOptions().position(site_frontdoor).title("遊樂場"));
        mMap.addMarker(new MarkerOptions().position(owl).title("貓頭鷹福寶"));
        mMap.addMarker(new MarkerOptions().position(the_ship).title("烏龜日光浴"));
        mMap.addMarker(new MarkerOptions().position(slide).title("溜滑梯"));
        mMap.addMarker(new MarkerOptions().position(kangaroo).title("袋鼠的母愛"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(demo01_site,20));
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (marker.getTitle().equals("奧爾森林學堂")){

                }
                if(marker.getTitle().equals("恐龍雕像")){
                    LayoutInflater inflater = LayoutInflater.from(demo01_missionmapfragment.this);
                    final View v = inflater.inflate(R.layout.demo01_map_sitepoint_dinosaur, null);
                    ImageButton camera =(ImageButton) v.findViewById(R.id.camera);
                    camera.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v2){
                            Intent dinosaur_launch = getPackageManager().getLaunchIntentForPackage("com.Panshow.DinosaurAR");
                            if (dinosaur_launch != null) {
                                startActivity(dinosaur_launch);//null pointer check in case package name was not found
                            }
                        }
                    });

                    dialog = new AlertDialog.Builder(demo01_missionmapfragment.this).setView(v).show();
                    Window window = dialog.getWindow();
                    window.setWindowAnimations(R.style.mystyle);





                    WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
                    params.alpha = 0.8f;
                    dialog.getWindow().setAttributes(params);

                }
                if(marker.getTitle().equals("貓頭鷹福寶")){
                    LayoutInflater inflater = LayoutInflater.from(demo01_missionmapfragment.this);
                    final View v = inflater.inflate(R.layout.demo01_map_sitepoint_owl, null);
                    ImageButton camera =(ImageButton) v.findViewById(R.id.camera);
                    camera.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v2){
                            Intent dinosaur_launch = getPackageManager().getLaunchIntentForPackage("com.Panshow.OwlAR ");
                            if (dinosaur_launch != null) {
                                startActivity(dinosaur_launch);//null pointer check in case package name was not found
                            }
                        }
                    });

                    dialog = new AlertDialog.Builder(demo01_missionmapfragment.this).setView(v).show();
                    Window window = dialog.getWindow();
                    window.setWindowAnimations(R.style.mystyle);





                    WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
                    params.alpha = 0.8f;
                    dialog.getWindow().setAttributes(params);

                }
                if(marker.getTitle().equals("袋鼠的母愛")){
                    LayoutInflater inflater = LayoutInflater.from(demo01_missionmapfragment.this);
                    final View v = inflater.inflate(R.layout.demo01_map_sitepoint_kangaroo, null);
                    ImageButton camera =(ImageButton) v.findViewById(R.id.camera);
                    camera.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v2){
                            Intent kangroo_launch = getPackageManager().getLaunchIntentForPackage("com.Panshow.KangarooAR");
                            if (kangroo_launch != null) {
                                startActivity(kangroo_launch);//null pointer check in case package name was not found
                            }
                        }
                    });

                    dialog = new AlertDialog.Builder(demo01_missionmapfragment.this).setView(v).show();
                    Window window = dialog.getWindow();
                    window.setWindowAnimations(R.style.mystyle);





                    WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
                    params.alpha = 0.8f;
                    dialog.getWindow().setAttributes(params);

                }
                if(marker.getTitle().equals("遊樂場")){
                    LayoutInflater inflater = LayoutInflater.from(demo01_missionmapfragment.this);
                    final View v = inflater.inflate(R.layout.demo01_map_sitepoint_frontdoor, null);
                    ImageButton camera =(ImageButton) v.findViewById(R.id.camera);
                    camera.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v2){
                            Intent dinosaur_launch = getPackageManager().getLaunchIntentForPackage("com.Panshow.PGAR");
                            if (dinosaur_launch != null) {
                                startActivity(dinosaur_launch);//null pointer check in case package name was not found
                            }
                        }
                    });

                    dialog = new AlertDialog.Builder(demo01_missionmapfragment.this).setView(v).show();
                    Window window = dialog.getWindow();
                    window.setWindowAnimations(R.style.mystyle);





                    WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
                    params.alpha = 0.8f;
                    dialog.getWindow().setAttributes(params);

                }
                if(marker.getTitle().equals("烏龜日光浴")){
                    LayoutInflater inflater = LayoutInflater.from(demo01_missionmapfragment.this);
                    final View v = inflater.inflate(R.layout.demo01_map_sitepoint_turtle, null);
                    ImageButton camera =(ImageButton) v.findViewById(R.id.camera);
                    camera.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v2){
                            Intent dinosaur_launch = getPackageManager().getLaunchIntentForPackage("com.Panshow.PoolAR");
                            if (dinosaur_launch != null) {
                                startActivity(dinosaur_launch);//null pointer check in case package name was not found
                            }
                        }
                    });

                    dialog = new AlertDialog.Builder(demo01_missionmapfragment.this).setView(v).show();
                    Window window = dialog.getWindow();
                    window.setWindowAnimations(R.style.mystyle);





                    WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
                    params.alpha = 0.8f;
                    dialog.getWindow().setAttributes(params);

                }
                if(marker.getTitle().equals("溜滑梯")){
                    LayoutInflater inflater = LayoutInflater.from(demo01_missionmapfragment.this);
                    final View v = inflater.inflate(R.layout.demo01_map_sitepoint_slide, null);
                    ImageButton camera =(ImageButton) v.findViewById(R.id.camera);
                    camera.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v2){
                            Intent dinosaur_launch = getPackageManager().getLaunchIntentForPackage("com.Panshow.SlideAR");
                            if (dinosaur_launch != null) {
                                startActivity(dinosaur_launch);//null pointer check in case package name was not found
                            }
                        }
                    });

                    dialog = new AlertDialog.Builder(demo01_missionmapfragment.this).setView(v).show();
                    Window window = dialog.getWindow();
                    window.setWindowAnimations(R.style.mystyle);





                    WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
                    params.alpha = 0.8f;
                    dialog.getWindow().setAttributes(params);

                }
                return false;

            }
        });
    }
}