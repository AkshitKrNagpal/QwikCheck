package cf.qwikcheck.qwikcheck;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

import cf.qwikcheck.qwikcheck.helper.SessionHelper;
import cf.qwikcheck.qwikcheck.utils.Constants;

public class RegisterRC extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_rc);

        final EditText regno = (EditText) findViewById(R.id.regno);
        final EditText engno = (EditText) findViewById(R.id.engineno);
        final EditText chassisno = (EditText) findViewById(R.id.chassisno);
        final EditText manufacterer = (EditText) findViewById(R.id.manufacturer);
        final EditText model = (EditText) findViewById(R.id.model);
        final EditText year = (EditText) findViewById(R.id.year);
        final EditText regdate = (EditText) findViewById(R.id.regdate);
        final EditText regupto = (EditText) findViewById(R.id.regupto);
        final EditText fueltype = (EditText) findViewById(R.id.fueltype);
        final EditText fuelcapacity = (EditText) findViewById(R.id.fuelcapacity);
        final EditText seatingcapacity = (EditText) findViewById(R.id.seating);
        final EditText vehiclecat = (EditText) findViewById(R.id.category);
        final EditText weightcategory = (EditText) findViewById(R.id.weight);
        final EditText usagecategory = (EditText) findViewById(R.id.usage);
        final EditText color = (EditText) findViewById(R.id.color);
        final EditText noofcyl = (EditText) findViewById(R.id.no_cyl);
        final EditText cc = (EditText) findViewById(R.id.cc);
        final EditText bodytype = (EditText) findViewById(R.id.bodytype);
        final EditText ownername = (EditText) findViewById(R.id.ownername);
        final EditText ownerid = (EditText) findViewById(R.id.ownerid);

        SessionHelper sessionHelper = new SessionHelper(this);
        final String apiKey = sessionHelper.getAPIKey();

        Button submit_button = (Button) findViewById(R.id.submit_button);
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog LoadingDialog = ProgressDialog.show(RegisterRC.this, "Registering vehicle", "Please wait...", true);
                RequestQueue queue = Volley.newRequestQueue(RegisterRC.this);

                StringRequest postRequest = new StringRequest(Request.Method.POST, Constants.USER_REG_URL,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response) {
                                Log.d("Response", response);

                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    if(jsonObject.getBoolean("success")) {

                                        new AlertDialog.Builder(RegisterRC.this)
                                                .setTitle("Successful")
                                                .setMessage("New RC was successfully added.")
                                                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        LoadingDialog.dismiss();
                                                    }
                                                })
                                                .setIcon(android.R.drawable.ic_dialog_alert)
                                                .show();
                                    } else {

                                        String error_message = jsonObject.getString("error");

                                        new AlertDialog.Builder(RegisterRC.this)
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
                                new AlertDialog.Builder(RegisterRC.this)
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

                        params.put("register", "RC");
                        params.put("api_key",apiKey);

                        params.put("regno", regno.getText().toString().trim());
                        params.put("engineno", engno.getText().toString().trim());
                        params.put("chassisno", chassisno.getText().toString().trim());
                        params.put("manufacturer",manufacterer.getText().toString().trim());
                        params.put("model", model.getText().toString().trim());
                        params.put("year", year.getText().toString().trim());
                        params.put("regdate", regdate.getText().toString().trim());
                        params.put("regupto", regupto.getText().toString().trim());
                        params.put("fueltype", fueltype.getText().toString().trim());
                        params.put("fuelcapacity", fuelcapacity.getText().toString().trim());
                        params.put("seatingcapacity", seatingcapacity.getText().toString().trim());
                        params.put("category", vehiclecat.getText().toString().trim());
                        params.put("weightcategory", weightcategory.getText().toString().trim());
                        params.put("usagecategory", usagecategory.getText().toString().trim());
                        params.put("color", color.getText().toString().trim());
                        params.put("noofcyl", noofcyl.getText().toString().trim());
                        params.put("cc", cc.getText().toString().trim());
                        params.put("bodytype", bodytype.getText().toString().trim());
                        params.put("ownername", ownername.getText().toString().trim());
                        params.put("ownerid", ownerid.getText().toString().trim());
                        return params;
                    }
                };
                queue.add(postRequest);
            }
        });

    }

}
