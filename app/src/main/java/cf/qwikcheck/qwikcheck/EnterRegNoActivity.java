package cf.qwikcheck.qwikcheck;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EnterRegNoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_reg_no);

        final EditText regno = (EditText) findViewById(R.id.regno);

        // Submit Button
        Button submit_button = (Button) findViewById(R.id.submit_button);
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EnterRegNoActivity.this, DisplayVehicleDetailsActivity.class);
                intent.putExtra("vehicle_number",regno.getText().toString().trim());
                startActivity(intent);
                finish();
            }
        });


    }
}
