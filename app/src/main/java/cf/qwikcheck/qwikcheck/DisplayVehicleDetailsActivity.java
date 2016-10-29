package cf.qwikcheck.qwikcheck;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_vehicle_details);

        final String vehicle_id = getIntent().getStringExtra("vehicle_number");

        final SquareImageView rc_img = (SquareImageView) findViewById(R.id.rc_img);
        final SquareImageView insurance_img = (SquareImageView) findViewById(R.id.insurance_img);
        final SquareImageView poll_img = (SquareImageView) findViewById(R.id.poll_img);

        final TextView error_rc = (TextView) findViewById(R.id.error_rc);
        final TextView error_insurance = (TextView) findViewById(R.id.error_insurance);
        final TextView error_poll = (TextView) findViewById(R.id.error_poll);

        final ProgressBar rc_loading = (ProgressBar) findViewById(R.id.rc_loading);
        final ProgressBar insurance_loading = (ProgressBar) findViewById(R.id.insurance_loading);
        final ProgressBar poll_loading = (ProgressBar) findViewById(R.id.poll_loading);

        rc_img.setVisibility(View.GONE);
        insurance_img.setVisibility(View.GONE);
        poll_img.setVisibility(View.GONE);

        final ProgressDialog LoadingDialog = ProgressDialog.show(this, "Loading", "Please wait...", true);

        RequestQueue queue = Volley.newRequestQueue(this);

        SessionHelper sessionHelper = new SessionHelper(this);
        final String user_id = Integer.toString(sessionHelper.getUserID());

        StringRequest postRequest = new StringRequest(Request.Method.POST, Constants.CHECK_URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if(!success) {
                                new AlertDialog.Builder(DisplayVehicleDetailsActivity.this)
                                        .setTitle("Error")
                                        .setMessage(jsonObject.getString("error"))
                                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                LoadingDialog.dismiss();
                                                finish();
                                            }
                                        })
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .show();
                            } else {

                                if (jsonObject.getBoolean("rc_ok")) {
                                    rc_img.setImageDrawable(getDrawable(R.drawable.right));
                                } else {
                                    rc_img.setImageDrawable(getDrawable(R.drawable.wrong));
                                    error_rc.setText(jsonObject.getString("rc_error"));
                                    error_rc.setVisibility(View.VISIBLE);
                                }
                                rc_img.setVisibility(View.VISIBLE);
                                rc_loading.setVisibility(View.GONE);


                                if (jsonObject.getBoolean("insurance_ok")) {
                                    insurance_img.setImageDrawable(getDrawable(R.drawable.right));
                                } else {
                                    insurance_img.setImageDrawable(getDrawable(R.drawable.wrong));
                                    error_insurance.setText(jsonObject.getString("insurance_error"));
                                    error_insurance.setVisibility(View.VISIBLE);
                                }
                                insurance_img.setVisibility(View.VISIBLE);
                                insurance_loading.setVisibility(View.GONE);


                                if (jsonObject.getBoolean("poll_ok")) {
                                    poll_img.setImageDrawable(getDrawable(R.drawable.right));
                                } else {
                                    poll_img.setImageDrawable(getDrawable(R.drawable.wrong));
                                    error_poll.setText(jsonObject.getString("poll_error"));
                                    error_poll.setVisibility(View.VISIBLE);
                                }
                                poll_img.setVisibility(View.VISIBLE);
                                poll_loading.setVisibility(View.GONE);

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
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", user_id);
                params.put("vehicle_id", vehicle_id);

                return params;
            }
        };
        queue.add(postRequest);

    }
}
