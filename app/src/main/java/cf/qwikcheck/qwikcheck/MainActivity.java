package cf.qwikcheck.qwikcheck;

import android.content.pm.PackageInstaller;
import android.os.Bundle;

import cf.qwikcheck.qwikcheck.Base.QwikCheckBaseActivity;
import cf.qwikcheck.qwikcheck.Helper.SessionHelper;

public class MainActivity extends QwikCheckBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SessionHelper sessionHelper = new SessionHelper(MainActivity.this);
        String usertype = sessionHelper.getUsertype();

        if(usertype.equals("police")) {
            setContentView(R.layout.activity_main_police);
        } else if (usertype.equals("user")) {
            setContentView(R.layout.activity_main_user);
        }
    }
}
