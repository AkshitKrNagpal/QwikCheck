package cf.qwikcheck.qwikcheck;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import cf.qwikcheck.qwikcheck.base.QwikCheckBaseActivity;
import cf.qwikcheck.qwikcheck.customclasses.SquareImageView;
import cf.qwikcheck.qwikcheck.helper.SessionHelper;

public class MainActivity extends QwikCheckBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SessionHelper sessionHelper = new SessionHelper(MainActivity.this);
        String usertype = sessionHelper.getUsertype();

        if("police".equals(usertype)) {

            setContentView(R.layout.activity_main_police);

            // Scan QR Code
            (findViewById(R.id.scan_qrcode)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    scanBarcode();
                }
            });

            // Call Help
            (findViewById(R.id.call_help)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Call For Help")
                            .setMessage("Clicking this will transmit an SOS to all the nearby police stations")
                            .show();
                }
            });

        } else if ("user".equals(usertype)) {

            setContentView(R.layout.activity_main_user);

            // View vehicles Icon
            (findViewById(R.id.view_my_vehicle)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this,ViewMyVehicleActivity.class);
                    startActivity(intent);
                }
            });

            // View profile Icon
            (findViewById(R.id.view_profile)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this,ViewProfileActivity.class);
                    startActivity(intent);
                }
            });

        } else if ("admin".equals(usertype)) {

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

        TextView name = (TextView) findViewById(R.id.name_textview);
        String temp = (new SessionHelper(this)).getRealname();
        name.setText(temp.substring(0,temp.indexOf(' ')));

        // View Previous Challan History
        (findViewById(R.id.history)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this,ChallanHistoryActivity.class);
            startActivity(intent);
            }
        });

        // Logout
        (findViewById(R.id.logout)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                (new SessionHelper(MainActivity.this)).logout();
                Intent intent = new Intent(MainActivity.this,SplashScreenActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void scanBarcode() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setOrientationLocked(false);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Intent intent = new Intent(MainActivity.this,DisplayVehicleDetailsActivity.class);
                intent.putExtra("vehicle_number",result.getContents());
                startActivity(intent);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
