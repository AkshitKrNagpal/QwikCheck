package cf.qwikcheck.qwikcheck;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cf.qwikcheck.qwikcheck.base.QwikCheckBaseActivity;
import cf.qwikcheck.qwikcheck.helper.SessionHelper;
import cf.qwikcheck.qwikcheck.utils.Constants;

public class ChallanActivity extends QwikCheckBaseActivity {

    public static int challan_amount = 0;

    List<String> description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challan);

        description = new ArrayList<>();

        challan_amount=getIntent().getIntExtra("challan",0);

        if( getIntent().getStringExtra("desc") != null ) {
            description.add(getIntent().getStringExtra("desc"));
        }

        final Map<String,Integer> challan_data_two_wheeler = new HashMap<String, Integer>();
        final Map<String,Integer> challan_data_four_wheeler = new HashMap<String, Integer>();
        final Map<String,Integer> challan_data_common = new HashMap<String, Integer>();

        challan_data_two_wheeler.put("Without Helmet",100);
        challan_data_two_wheeler.put("Triple Riding",100);

        challan_data_four_wheeler.put("Without Seatbelt",300);
        challan_data_four_wheeler.put("Access Passengers",1000);
        challan_data_four_wheeler.put("Non-transparent Windows",300);
        challan_data_four_wheeler.put("Without Viper",100);
        challan_data_four_wheeler.put("Obstructive Driving",100);

        challan_data_common.put("Smoking/Drinking while Driving",100);
        challan_data_common.put("One-way Voilation",1000);
        challan_data_common.put("Blowing of Pressure Horn",500);
        challan_data_common.put("Using Horn in No Honking Zone",100);
        challan_data_common.put("Use of colored Headlights",100);
        challan_data_common.put("Excess Smoke",500);
        challan_data_common.put("Driving without Light after Sunset",100);
        challan_data_common.put("Voilation of Traffic Signal", 500);
        challan_data_common.put("Voilation of Mandatory Roadsigns", 500);
        challan_data_common.put("Not Displaying Number Plate", 300);
        challan_data_common.put("Driving without License", 1000);
        challan_data_common.put("Overspeeding", 500);

        final Map<String,Integer> two_wheeler = challan_data_two_wheeler;
        final Map<String,Integer> four_wheeler = challan_data_four_wheeler;
        final Map<String,Integer> common = challan_data_common;

        final String vehicle_id = getIntent().getStringExtra("vehicle_number");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if( toolbar != null ) {
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setTitle("Challan : "+vehicle_id);
            }
        }

        final TextView amount_textview = (TextView) findViewById(R.id.amount_textview);

        amount_textview.setText(String.valueOf(challan_amount));

        final TextView name_textview = (TextView) findViewById(R.id.name_text_view);

        FloatingActionButton next_fab = (FloatingActionButton) findViewById(R.id.next_button);
        next_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createChallan();
            }
        });

        final ProgressDialog LoadingDialog = ProgressDialog.show(this, "Loading", "Please wait...", true);

        RequestQueue queue = Volley.newRequestQueue(this);

        SessionHelper sessionHelper = new SessionHelper(this);
        final String apiKey = sessionHelper.getAPIKey();

        StringRequest postRequest = new StringRequest(Request.Method.POST, Constants.API_BASE_URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if(success) {


                                JSONObject details = jsonObject.getJSONObject("details");

                                name_textview.setText(details.getString("OwnerName"));

                                LinearLayout container = (LinearLayout) findViewById(R.id.common_reasons);

                                for (Map.Entry<String,Integer> entry : common.entrySet()  ) {
                                    insertEntryToLL(container,entry);
                                }

                                container = (LinearLayout) findViewById(R.id.specific_reasons);

                                if( details.getInt("SeatingCapacity") <= 2 ) {
                                    for (Map.Entry<String,Integer> entry : two_wheeler.entrySet()  ) {
                                        insertEntryToLL(container,entry);
                                    }
                                } else {
                                    for (Map.Entry<String,Integer> entry : four_wheeler.entrySet()  ) {
                                        insertEntryToLL(container,entry);
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
                        new AlertDialog.Builder(ChallanActivity.this)
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
                params.put("details","RC");
                return params;
            }
        };
        queue.add(postRequest);

    }

    public void insertEntryToLL(LinearLayout container, final Map.Entry<String,Integer> entry) {

        //description = new ArrayList<String>();

        final TextView amount_textview = (TextView) findViewById(R.id.amount_textview);

        LinearLayout.LayoutParams rootParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams checkboxParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        LinearLayout root = new LinearLayout(ChallanActivity.this);
        root.setLayoutParams(rootParams);

        final CheckBox checkbox = new CheckBox(ChallanActivity.this);
        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( ((CheckBox)v).isChecked() ) {
                    challan_amount = challan_amount + entry.getValue();
                    description.add(entry.getKey());
                } else {
                    challan_amount = challan_amount - entry.getValue();
                    description.remove(entry.getKey());
                }
                amount_textview.setText(String.valueOf(challan_amount));
            }
        });
        checkbox.setLayoutParams(checkboxParams);

        TextView textview = new TextView(ChallanActivity.this);
        textview.setText(entry.getKey());
        textview.setGravity(Gravity.CENTER_VERTICAL);
        textview.setLayoutParams(textParams);

        root.addView(checkbox);
        root.addView(textview);

        container.addView(root);

    }

    public void createChallan() {

        final String vehicle_id = getIntent().getStringExtra("vehicle_number");

        final ProgressDialog LoadingDialog = ProgressDialog.show(this, "Loading", "Please wait...", true);

        final StringBuilder sb = new StringBuilder();
        for (String s : description)
        {
            sb.append(s);
            sb.append("\n");
        }
        sb.deleteCharAt(sb.length() - 1);

        RequestQueue queue = Volley.newRequestQueue(this);

        SessionHelper sessionHelper = new SessionHelper(this);
        final String apiKey = sessionHelper.getAPIKey();

        StringRequest postRequest = new StringRequest(Request.Method.POST, Constants.API_BASE_URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if(!success) {
                                new AlertDialog.Builder(ChallanActivity.this)
                                        .setTitle("Error")
                                        .setMessage(jsonObject.getString("error"))
                                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                LoadingDialog.dismiss();
                                                finish();
                                            }
                                        })
                                        .show();
                            } else {

                                new AlertDialog.Builder(ChallanActivity.this)
                                        .setTitle("Success")
                                        .setMessage("The Challan was successfully issued. The owner of the vehicle will be notified.\n*Coming Soon")
                                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                finish();
                                            }
                                        })
                                        .show();

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
                        new AlertDialog.Builder(ChallanActivity.this)
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
                params.put("description", sb.toString());
                params.put("payment_amount",String.valueOf(challan_amount));
                return params;
            }
        };
        queue.add(postRequest);
    }

    /*public void markAsPaid() {

        final String vehicle_id = getIntent().getStringExtra("vehicle_number");

        final ProgressDialog LoadingDialog = ProgressDialog.show(this, "Loading", "Please wait...", true);

        /*final StringBuilder sb = new StringBuilder();
        for (String s : description)
        {
            sb.append(s);
            sb.append(",");
        }

        RequestQueue queue = Volley.newRequestQueue(this);

        SessionHelper sessionHelper = new SessionHelper(this);
        final String apiKey = sessionHelper.getAPIKey();

        StringRequest postRequest = new StringRequest(Request.Method.POST, Constants.API_BASE_URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if(!success) {
                                new AlertDialog.Builder(ChallanActivity.this)
                                        .setTitle("Error")
                                        .setMessage(jsonObject.getString("error"))
                                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                LoadingDialog.dismiss();
                                                finish();
                                            }
                                        })
                                        .show();
                            } else {

                                new AlertDialog.Builder(ChallanActivity.this)
                                        .setTitle("Success")
                                        .setMessage("The Challan was successfully issued.")
                                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                finish();
                                            }
                                        })
                                        .show();

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
                        new AlertDialog.Builder(ChallanActivity.this)
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
                params.put("description", sb.toString());
                params.put("payment_amount",String.valueOf(challan_amount));
                return params;
            }
        };
        queue.add(postRequest);
    }*/


}
