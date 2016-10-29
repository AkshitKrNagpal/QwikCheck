package cf.qwikcheck.qwikcheck;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import cf.qwikcheck.qwikcheck.Base.QwikCheckBaseActivity;
import cf.qwikcheck.qwikcheck.CustomClasses.SquareImageView;
import cf.qwikcheck.qwikcheck.Helper.SessionHelper;

public class MainActivity extends QwikCheckBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        SessionHelper sessionHelper = new SessionHelper(MainActivity.this);
        String usertype = sessionHelper.getUsertype();

        if("police".equals(usertype)) {
            setContentView(R.layout.activity_main_police);
        } else if ("user".equals(usertype)) {
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

        // For Icons

        // Scan Barcode Icon
        SquareImageView scan_barcode_icon = (SquareImageView) findViewById(R.id.scan_barcode_icon);
        scan_barcode_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanBarcode();
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
