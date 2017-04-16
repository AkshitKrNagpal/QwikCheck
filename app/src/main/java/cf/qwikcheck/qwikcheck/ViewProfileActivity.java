package cf.qwikcheck.qwikcheck;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import cf.qwikcheck.qwikcheck.base.QwikCheckBaseActivity;
import cf.qwikcheck.qwikcheck.helper.SessionHelper;

public class ViewProfileActivity extends QwikCheckBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if( toolbar != null ) {
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setTitle("View Profile");
            }
        }

        final TextView fullname = (TextView) findViewById(R.id.fullname);
        final TextView address = (TextView) findViewById(R.id.address);
        final TextView contact_no = (TextView) findViewById(R.id.contact_no);
        //final TextView type = (TextView) findViewById(R.id.type);

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
        //type.setText(sessionHelper.getUsertype());
    }
}
