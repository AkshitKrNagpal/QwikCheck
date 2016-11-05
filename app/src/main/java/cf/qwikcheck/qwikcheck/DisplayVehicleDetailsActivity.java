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
        final String apiKey = sessionHelper.getAPIKey();

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

                                updateStatus(rc_img,rc_loading,error_rc,jsonObject.getBoolean("rc_ok"),jsonObject.getString("rc_error"));
                                updateStatus(insurance_img,insurance_loading,error_insurance,jsonObject.getBoolean("insurance_ok"),jsonObject.getString("insurance_error"));
                                updateStatus(poll_img,poll_loading,error_poll,jsonObject.getBoolean("poll_ok"),jsonObject.getString("poll_error"));

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
                params.put("api_key", apiKey);
                params.put("vehicle_id", vehicle_id);

                return params;
            }
        };
        queue.add(postRequest);


    }

    public void updateStatus(SquareImageView img,ProgressBar loading,TextView error,boolean success,String error_text) {
        if (success) {
            img.setImageDrawable(getDrawable(R.drawable.right));
        } else {
            img.setImageDrawable(getDrawable(R.drawable.wrong));
            error.setText(error_text);
            error.setVisibility(View.VISIBLE);
        }
        img.setVisibility(View.VISIBLE);
        loading.setVisibility(View.GONE);
    }
}
