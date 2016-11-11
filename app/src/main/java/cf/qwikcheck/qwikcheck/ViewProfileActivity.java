package cf.qwikcheck.qwikcheck;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
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

import cf.qwikcheck.qwikcheck.helper.SessionHelper;
import cf.qwikcheck.qwikcheck.utils.Constants;

public class ViewProfileActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        final TextView fullname = (TextView) findViewById(R.id.fullname);
        final TextView address = (TextView) findViewById(R.id.address);
        final TextView contact_no = (TextView) findViewById(R.id.contact_no);
        final TextView type = (TextView) findViewById(R.id.type);

        SessionHelper sessionHelper = new SessionHelper(this);

        fullname.setText(sessionHelper.getRealname());
        if(sessionHelper.getAddress()!="")
            address.setText(sessionHelper.getAddress());
        else
            address.setText("NULL");
        if(sessionHelper.getContactNo()!="")
            contact_no.setText(sessionHelper.getContactNo());
        else
            contact_no.setText("NULL");
        type.setText(sessionHelper.getUsertype());
    }
}
