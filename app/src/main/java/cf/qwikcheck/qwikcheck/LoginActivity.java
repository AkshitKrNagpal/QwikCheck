package cf.qwikcheck.qwikcheck;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

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

import cf.qwikcheck.qwikcheck.Base.QwikCheckBaseActivity;
import cf.qwikcheck.qwikcheck.Helper.SessionHelper;
import cf.qwikcheck.qwikcheck.Utils.Constants;

public class LoginActivity extends QwikCheckBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText username = (EditText) findViewById(R.id.username);
        final EditText password = (EditText) findViewById(R.id.password);

        // Login Button
        Button login_button = (Button) findViewById(R.id.login_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(username.getText().toString().trim(),password.getText().toString().trim());
            }
        });

        // Sign up Button
        Button signup_button = (Button) findViewById(R.id.signup_button);
        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

    }

    public void login(final String username,final String password) {

        final ProgressDialog LoadingDialog = ProgressDialog.show(this, "Logging in", "Please wait...", true);
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest postRequest = new StringRequest(Request.Method.POST, Constants.USER_LOGIN_URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.getBoolean("success")) {

                                JSONObject user_data = jsonObject.getJSONObject("user_data");

                                SessionHelper sessionHelper = new SessionHelper(LoginActivity.this);

                                sessionHelper.setUserID(user_data.getInt("user_id"));
                                sessionHelper.setUsername(user_data.getString("user_name"));
                                sessionHelper.setRealname(user_data.getString("real_name"));

                                LoadingDialog.dismiss();
                                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();


                            } else {

                                String error_message = jsonObject.getString("error");

                                new AlertDialog.Builder(LoginActivity.this)
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
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("password", password);

                return params;
            }
        };
        queue.add(postRequest);

    }

}
