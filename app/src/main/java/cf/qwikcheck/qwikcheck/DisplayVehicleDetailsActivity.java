package cf.qwikcheck.qwikcheck;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cf.qwikcheck.qwikcheck.base.QwikCheckBaseActivity;
import cf.qwikcheck.qwikcheck.customclasses.SquareImageView;
import cf.qwikcheck.qwikcheck.helper.SessionHelper;
import cf.qwikcheck.qwikcheck.utils.Constants;

public class DisplayVehicleDetailsActivity extends QwikCheckBaseActivity {

    public static int challan = 0;
    public static String desc = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_vehicle_details);


        final String vehicle_id = getIntent().getStringExtra("vehicle_number");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if( toolbar != null ) {
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setTitle("Status : "+vehicle_id);
            }
        }

        final ImageView rc_img = (ImageView) findViewById(R.id.rc_img);
        final ImageView insurance_img = (ImageView) findViewById(R.id.insurance_img);
        final ImageView poll_img = (ImageView) findViewById(R.id.poll_img);

        final TextView status_rc = (TextView) findViewById(R.id.status_rc);
        final TextView status_insurance = (TextView) findViewById(R.id.status_insurance);
        final TextView status_poll = (TextView) findViewById(R.id.status_poll);

        rc_img.setVisibility(View.INVISIBLE);
        insurance_img.setVisibility(View.INVISIBLE);
        poll_img.setVisibility(View.INVISIBLE);

        Button issueChallanButton = (Button) findViewById(R.id.issue_challan_button);
        issueChallanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayVehicleDetailsActivity.this,ChallanActivity.class);
                intent.putExtra("vehicle_number",vehicle_id);
                intent.putExtra("challan",challan);
                Log.e("desc",desc);
                intent.putExtra("desc",desc);
                startActivity(intent);
                finish();
            }
        });

        Button backButton = (Button) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        final ProgressDialog LoadingDialog = ProgressDialog.show(this, "Loading", "Please wait...", true);

        RequestQueue queue = Volley.newRequestQueue(this);

        SessionHelper sessionHelper = new SessionHelper(this);
        final String apiKey = sessionHelper.getAPIKey();

        StringRequest postRequest = new StringRequest(Request.Method.POST, Constants.CHECK_URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            JSONObject rc = jsonObject.getJSONObject("rc_details");
                            JSONObject ins = jsonObject.getJSONObject("ins_details");
                            JSONObject poll = jsonObject.getJSONObject("poll_details");

                            boolean success = rc.getBoolean("success") && ins.getBoolean("success") && poll.getBoolean("success");
                            if(!success) {
                                new AlertDialog.Builder(DisplayVehicleDetailsActivity.this)
                                        .setTitle("Error")
                                        .setMessage("Wrong")
                                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                LoadingDialog.dismiss();
                                                finish();
                                            }
                                        })
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .show();
                            } else {

                                if(!rc.getBoolean("ok")) {
                                    challan +=1000;
                                    desc+=rc.getString("message")+",";
                                }
                                if(!ins.getBoolean("ok")) {
                                    challan+=1000;
                                    desc+=ins.getString("message")+",";
                                }
                                if(!poll.getBoolean("ok")) {
                                    challan+=1000;
                                    desc+=poll.getString("message")+",";
                                }

                                updateStatus(rc_img,status_rc,rc.getBoolean("ok"),rc.getString("message"));
                                updateStatus(insurance_img,status_insurance,ins.getBoolean("ok"),ins.getString("message"));
                                updateStatus(poll_img,status_poll,poll.getBoolean("ok"),poll.getString("message"));

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        LoadingDialog.dismiss();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                        new AlertDialog.Builder(DisplayVehicleDetailsActivity.this)
                                .setTitle("Error")
                                .setMessage("There was an error connecting to server. Make sure your internet is connected.")
                                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        LoadingDialog.dismiss();
                                        finish();
                                    }
                                })
                                .show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("api_key", apiKey);
                params.put("vehicle_number", vehicle_id);

                return params;
            }
        };
        queue.add(postRequest);


    }

    public void updateStatus(ImageView img,TextView status,boolean success,String status_text) {
        if (success) {
            img.setImageDrawable(getResources().getDrawable(R.drawable.right));
        } else {
            img.setImageDrawable(getResources().getDrawable(R.drawable.wrong));
            status.setText(status_text);
            status.setVisibility(View.VISIBLE);
        }
        img.setVisibility(View.VISIBLE);
    }


}
