package cf.qwikcheck.qwikcheck;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

import java.util.HashMap;
import java.util.Map;

import cf.qwikcheck.qwikcheck.base.QwikCheckBaseActivity;
import cf.qwikcheck.qwikcheck.helper.SessionHelper;
import cf.qwikcheck.qwikcheck.utils.Constants;

public class ChallanHistoryActivity extends QwikCheckBaseActivity {

    LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_challan);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if( toolbar != null ) {
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setTitle("Previous Challans");
            }
        }

        container = (LinearLayout) findViewById(R.id.challan_list_container);

        final SessionHelper sessionHelper = new SessionHelper(this);

        final ProgressDialog LoadingDialog = ProgressDialog.show(this, "Remembering things", "Please wait...", true);
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest postRequest = new StringRequest(Request.Method.POST, Constants.USER_LOGIN_URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.getBoolean("success")) {

                                JSONArray challans = jsonObject.getJSONArray("details");

                                for( int i=0; i < challans.length(); i++) {

                                    JSONObject challan = challans.getJSONObject(i);

                                    addChallanToContainer(challan);

                                }

                                LoadingDialog.dismiss();

                            } else {

                                String error_message = jsonObject.getString("error");

                                new AlertDialog.Builder(ChallanHistoryActivity.this)
                                        .setTitle("Error")
                                        .setMessage(error_message)
                                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                LoadingDialog.dismiss();
                                            }
                                        })
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                        new AlertDialog.Builder(ChallanHistoryActivity.this)
                                .setTitle("Error")
                                .setMessage("There was an error connecting to server. Make sure your internet is connected.")
                                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        LoadingDialog.dismiss();
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
                params.put("api_key", sessionHelper.getAPIKey());
                params.put("get", "my_challans");

                return params;
            }
        };
        queue.add(postRequest);


    }

    public void addChallanToContainer(JSONObject challan) {

        LinearLayout.LayoutParams rootParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rootParams.setMargins(32,32,32,32);

        LinearLayout.LayoutParams llParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        llParams.setMargins(8,8,8,8);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        CardView root = new CardView(this);
        root.setRadius(4);
        root.setLayoutParams(rootParams);
        root.setPadding(16,16,16,16);

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setLayoutParams(llParams);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(16,16,16,16);

        TextView regno = new TextView(this);
        regno.setLayoutParams(layoutParams);
        regno.setTextSize(24);
        try {
            regno.setText(challan.getString("registration_number"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        TextView description = new TextView(this);
        description.setLayoutParams(layoutParams);
        try {
            description.setText(challan.getString("challan_description"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        linearLayout.addView(regno);
        linearLayout.addView(description);

        root.addView(linearLayout);

        container.addView(root);

    }

}
