package cf.qwikcheck.qwikcheck;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.widget.Toast;

import cf.qwikcheck.qwikcheck.helper.SessionHelper;
import cf.qwikcheck.qwikcheck.utils.ConnectivityUtils;

public class SplashScreenActivity extends Activity {

    private final int DELAY_TIME = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(DELAY_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {

                    if(!ConnectivityUtils.isAppConnected(SplashScreenActivity.this)) {
                        Toast.makeText(SplashScreenActivity.this,"No Internet. The app will exit now.",Toast.LENGTH_LONG);
                    } else {
                        Intent intent;
                        if(new SessionHelper(SplashScreenActivity.this).isLoggedIn()) {
                            intent = new Intent(SplashScreenActivity.this,MainActivity.class);
                        } else {
                            intent = new Intent(SplashScreenActivity.this,LoginActivity.class);
                        }
                        finish();
                        SplashScreenActivity.this.startActivity(intent);
                    }

                }
            }
        });

        t.start();

    }

}
