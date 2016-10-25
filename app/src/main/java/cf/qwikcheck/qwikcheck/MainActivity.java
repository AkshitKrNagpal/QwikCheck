package cf.qwikcheck.qwikcheck;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
        } else {
            (new SessionHelper(this)).logout();
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Error")
                    .setMessage("There was an unexpected error. Please login again. ")
                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(MainActivity.this,SplashScreenActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }
}
