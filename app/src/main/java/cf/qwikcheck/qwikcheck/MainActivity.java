package cf.qwikcheck.qwikcheck;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

            // Scan Barcode Icon
            SquareImageView scan_barcode_icon = (SquareImageView) findViewById(R.id.scan_barcode_icon);
            scan_barcode_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    scanBarcode();
                }
            });

            // Enter Registration number Icon
            SquareImageView enter_regno_icon = (SquareImageView) findViewById(R.id.enter_regno_icon);
            enter_regno_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this,EnterRegNoActivity.class);
                    startActivity(intent);
                }
            });

            // History Icon
            SquareImageView history_icon = (SquareImageView) findViewById(R.id.history_icon);
            history_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this,ViewHistory.class);
                    startActivity(intent);
                }
            });

            // Contact nearest police station
            SquareImageView contact_nps_icon = (SquareImageView) findViewById(R.id.contact_nps_icon);
            contact_nps_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Feature Coming Soon")
                            .setMessage("App is under development.")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            });



        } else if ("user".equals(usertype)) {

            setContentView(R.layout.activity_main_user);


        } else if ("admin".equals(usertype)) {
            setContentView(R.layout.activity_main_admin);


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

        // For Icons

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
