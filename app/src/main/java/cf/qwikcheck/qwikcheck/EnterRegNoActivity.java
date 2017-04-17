package cf.qwikcheck.qwikcheck;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import cf.qwikcheck.qwikcheck.base.QwikCheckBaseActivity;

public class EnterRegNoActivity extends QwikCheckBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_reg_no);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if( toolbar != null ) {
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setTitle("Enter Vehicle Number");
            }
        }

        //final String next_activity = getIntent().getStringExtra("next_activity");

        final EditText regno = (EditText) findViewById(R.id.regno);

        // Submit Button
        Button submit_button = (Button) findViewById(R.id.submit_button);
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard(EnterRegNoActivity.this);
                Intent intent = new Intent(EnterRegNoActivity.this, DisplayVehicleDetailsActivity.class);
                intent.putExtra("vehicle_number",regno.getText().toString().trim());
                startActivity(intent);
                finish();
            }
        });


    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }
}
