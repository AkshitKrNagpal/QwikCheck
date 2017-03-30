package cf.qwikcheck.qwikcheck;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cf.qwikcheck.qwikcheck.base.QwikCheckBaseActivity;

public class ViewSingleChallanActivity extends QwikCheckBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_single_challan);
    }
}
