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

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            if( toolbar != null ) {
                setSupportActionBar(toolbar);
                ActionBar actionBar = getSupportActionBar();
                if (actionBar != null) {
                    //actionBar.setDisplayHomeAsUpEnabled(true);
                    //actionBar.setHomeAsUpIndicator(getResources().getDrawable(R.drawable.logo_square));
                    actionBar.setTitle("QwikCheck - Police");
                }
            }


            // Floating Icon
            //FloatingActionButton scan_qrcode_fab = (FloatingActionButton) findViewById(R.id.fab);
            (findViewById(R.id.scan_qrcode)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    scanBarcode();
                }
            });

            (findViewById(R.id.call_help)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Call For Help")
                            .setMessage("Clicking this will transmit an SOS to all the nearby police stations")
                            .show();
                }
            });

            TextView name = (TextView) findViewById(R.id.name_textview);
            String temp = (new SessionHelper(this)).getRealname();
            name.setText(temp.substring(0,temp.indexOf(' ')));

            (findViewById(R.id.logout)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    (new SessionHelper(MainActivity.this)).logout();
                    Intent intent = new Intent(MainActivity.this,SplashScreenActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

            (findViewById(R.id.history)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this,ChallanHistoryActivity.class);
                    startActivity(intent);
                }
            });

            /*// Scan Barcode Icon
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

            // Logout Icon
            SquareImageView logout_icon = (SquareImageView) findViewById(R.id.logout_icon);
            logout_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    (new SessionHelper(MainActivity.this)).logout();
                    Intent intent = new Intent(MainActivity.this,SplashScreenActivity.class);
                    startActivity(intent);
                    finish();
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
            });*/



        } else if ("user".equals(usertype)) {

            setContentView(R.layout.activity_main_user);

            // View vehicles Icon
            SquareImageView view_vehicles_icon = (SquareImageView) findViewById(R.id.view_vehicles);
            view_vehicles_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this,ViewMyVehicleActivity.class);
                    startActivity(intent);
                }
            });

            // View profile Icon
            SquareImageView view_profile_icon = (SquareImageView) findViewById(R.id.view_profile);
            view_profile_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this,ViewProfileActivity.class);
                    startActivity(intent);
                }
            });

            // Edit profile Icon
            SquareImageView edit_profile_icon = (SquareImageView) findViewById(R.id.edit_profile_icon);
            edit_profile_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this,EditProfileActivity.class);
                    startActivity(intent);
                }
            });

            // Logout Icon
            SquareImageView logout_icon = (SquareImageView) findViewById(R.id.logout_icon);
            logout_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    (new SessionHelper(MainActivity.this)).logout();
                    Intent intent = new Intent(MainActivity.this,SplashScreenActivity.class);
                    startActivity(intent);
                    finish();
                }
            });


        } else if ("admin".equals(usertype)) {

            setContentView(R.layout.activity_main_admin);

            // RC
            SquareImageView rc_icon = (SquareImageView) findViewById(R.id.view_vehicle);
            rc_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this,EnterRegNoActivity.class);
                    intent.putExtra("next_activity","ViewRC");
                    startActivity(intent);
                }
            });

            SquareImageView reg_rc_icon = (SquareImageView) findViewById(R.id.reg_vehicle);
            reg_rc_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this,RegisterRC.class);
                    startActivity(intent);
                }
            });

            // INS
            SquareImageView ins_icon = (SquareImageView) findViewById(R.id.view_insurance);
            ins_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this,EnterRegNoActivity.class);
                    intent.putExtra("next_activity","ViewINS");
                    startActivity(intent);
                }
            });

            SquareImageView reg_ins_icon = (SquareImageView) findViewById(R.id.reg_insurance);
            reg_ins_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this,RegisterRC.class);
                    startActivity(intent);
                }
            });

            // PUCC
            SquareImageView pucc_icon = (SquareImageView) findViewById(R.id.view_poll);
            pucc_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this,EnterRegNoActivity.class);
                    intent.putExtra("next_activity","ViewPUCC");
                    startActivity(intent);
                }
            });

            SquareImageView reg_pucc_icon = (SquareImageView) findViewById(R.id.reg_vehicle);
            reg_pucc_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this,RegisterRC.class);
                    startActivity(intent);
                }
            });

            // Logout Icon
            SquareImageView logout_icon = (SquareImageView) findViewById(R.id.logout_icon);
            logout_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    (new SessionHelper(MainActivity.this)).logout();
                    Intent intent = new Intent(MainActivity.this,SplashScreenActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

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
