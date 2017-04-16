package cf.qwikcheck.qwikcheck;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;

import cf.qwikcheck.qwikcheck.base.QwikCheckBaseActivity;
import cf.qwikcheck.qwikcheck.helper.SessionHelper;
import cf.qwikcheck.qwikcheck.utils.Constants;

public class ViewMyVehicleActivity extends QwikCheckBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_my_vehicle);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if( toolbar != null ) {
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setTitle("My Vehicles");
            }
        }

        final ProgressDialog LoadingDialog = ProgressDialog.show(this, "Loading", "Please wait...", true);

        RequestQueue queue = Volley.newRequestQueue(this);

        SessionHelper sessionHelper = new SessionHelper(this);
        final String apiKey = sessionHelper.getAPIKey();

        StringRequest postRequest = new StringRequest(Request.Method.POST, Constants.API_BASE_URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if(!success) {
                                new AlertDialog.Builder(ViewMyVehicleActivity.this)
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

                                if( jsonObject.getInt("count") > 0 ) {

                                    JSONArray vehicles = jsonObject.getJSONArray("vehicles");

                                    for( int i=0; i < vehicles.length(); i++) {

                                        JSONObject vehicle = vehicles.getJSONObject(i);

                                        addVehicleToContainer(vehicle);

                                    }

                                }

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
                        new AlertDialog.Builder(ViewMyVehicleActivity.this)
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
                params.put("get","my_vehicles");
                return params;
            }
        };
        queue.add(postRequest);

        container = (LinearLayout) findViewById(R.id.my_vehicle_list_container);

    }

    LinearLayout container;

    public void addVehicleToContainer(JSONObject challan) {

        LinearLayout.LayoutParams rootParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rootParams.setMargins(32, 32, 32, 32);

        LinearLayout.LayoutParams llParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        llParams.setMargins(8, 8, 8, 8);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        CardView root = new CardView(this);
        root.setRadius(16);
        root.setLayoutParams(rootParams);
        //root.setPadding(64,64,64,64);

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setLayoutParams(llParams);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(32, 16, 32, 16);

        TextView regno = new TextView(this);
        regno.setLayoutParams(layoutParams);
        regno.setTextSize(24);

        String regnotext = "";
        try {
            regnotext = challan.getString("RegNo");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        regno.setText(regnotext);

        linearLayout.addView(regno);

        root.addView(linearLayout);

        final String finalRegnotext = regnotext;
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewMyVehicleActivity.this,DisplayVehicleDetailsActivity.class);
                intent.putExtra("vehicle_number", finalRegnotext);
                startActivity(intent);
            }
        });

        container.addView(root);

    }
}
