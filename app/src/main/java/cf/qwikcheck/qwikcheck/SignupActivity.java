package cf.qwikcheck.qwikcheck;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
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

import cf.qwikcheck.qwikcheck.base.QwikCheckBaseActivity;
import cf.qwikcheck.qwikcheck.utils.Constants;

public class SignupActivity extends QwikCheckBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        final EditText username = (EditText) findViewById(R.id.username);
        final EditText password = (EditText) findViewById(R.id.password);
        final EditText c_password = (EditText) findViewById(R.id.c_password);

        password.setTransformationMethod(new PasswordTransformationMethod());
        c_password.setTransformationMethod(new PasswordTransformationMethod());

        // Login Button
        Button login_button = (Button) findViewById(R.id.login_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Sign up Button
        Button signup_button = (Button) findViewById(R.id.signup_button);
        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup(username.getText().toString().trim(), password.getText().toString().trim(), c_password.getText().toString().trim());
            }
        });
    }

    public void signup(final String username, final String password, final String c_password) {

        final ProgressDialog LoadingDialog = ProgressDialog.show(this, "Signing Up", "Please wait...", true);
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest postRequest = new StringRequest(Request.Method.POST, Constants.USER_REG_URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.getBoolean("success")) {

                                new AlertDialog.Builder(SignupActivity.this)
                                        .setTitle("Successful")
                                        .setMessage("You were successfully signed up. Please login to continue registration.")
                                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {

                                                LoadingDialog.dismiss();
                                                Intent intent = new Intent(SignupActivity.this,LoginActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        })
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .show();
                            } else {

                                String error_message = jsonObject.getString("error");

                                new AlertDialog.Builder(SignupActivity.this)
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
                        new AlertDialog.Builder(SignupActivity.this)
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
                params.put("username", username);
                params.put("password", password);
                params.put("c_password", c_password);

                return params;
            }
        };
        queue.add(postRequest);

    }
}
