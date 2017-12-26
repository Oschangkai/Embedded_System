package tw.kaiyeee.triptao.app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import tw.kaiyeee.triptao.R;

public class permission_page extends AppCompatActivity {
    private static int SPLASH_TIMEOUT = 2000;
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    ImageView bigLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Full Screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.perpmission_page);
       final Animation splash_anim = AnimationUtils.loadAnimation(this,R.anim.splash_anim);

        bigLogo = (ImageView)findViewById(R.id.BigLogo);

        bigLogo.setAnimation(splash_anim);

        //check permission
        if (ContextCompat.checkSelfPermission(permission_page.this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(permission_page.this,
                    Manifest.permission.CAMERA)) {
                ActivityCompat.requestPermissions(permission_page.this,
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                bigLogo.setAnimation(splash_anim);


            } else {  //如果已經拿到相機權限則......
                bigLogo.setAnimation(splash_anim);

                ActivityCompat.requestPermissions(permission_page.this,
                        new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent load_Intent = new Intent(permission_page.this,test_json.class);
                        startActivity(load_Intent);
                        finish();
                    }
                },SPLASH_TIMEOUT);
                bigLogo.setAnimation(splash_anim);



            }
        }
        else{  //一開始進入app就取得權限了.....
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent load_Intent = new Intent(permission_page.this,test_json.class);
                    startActivity(load_Intent);
                    finish();
                }
            },SPLASH_TIMEOUT);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent load_Intent = new Intent(permission_page.this,test_json.class);
                            startActivity(load_Intent);
                            finish();
                        }
                    },SPLASH_TIMEOUT);
                } else {
                    finish();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    @Override
    public void onBackPressed () {
        int pid = android.os.Process.myPid();
        android.os.Process.killProcess(pid);
    }
}