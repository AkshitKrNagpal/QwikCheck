package cf.qwikcheck.qwikcheck;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import cf.qwikcheck.qwikcheck.base.QwikCheckBaseActivity;
import cf.qwikcheck.qwikcheck.helper.SessionHelper;
import cf.qwikcheck.qwikcheck.utils.ConnectivityUtils;
import cf.qwikcheck.qwikcheck.utils.DialogFactory;

public class SplashScreenActivity extends QwikCheckBaseActivity {


    public static int SPLASH_TIME_OUT = 2000;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

         new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                if (!ConnectivityUtils.isAppConnected(SplashScreenActivity.this)) {

                    Dialog dialog = DialogFactory.createSimpleOkErrorDialog(SplashScreenActivity.this,"No Internet","This app requires internet to function. The app will exit now.");
                    dialog.show();
                } else {
                    Intent intent;
                    if (new SessionHelper(SplashScreenActivity.this).isLoggedIn()) {
                        intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                    } else {
                        intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    }
                    startActivity(intent);
                    finish();
                }
                finish();
            }
        }, SPLASH_TIME_OUT);

    }
}
