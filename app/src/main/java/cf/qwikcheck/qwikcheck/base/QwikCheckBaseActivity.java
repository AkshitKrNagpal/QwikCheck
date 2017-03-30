package cf.qwikcheck.qwikcheck.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by akshit on 24/10/16.
 */

public class QwikCheckBaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("Activity created ", this.getClass().getSimpleName());

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d("Activity destroyed ", this.getClass().getSimpleName());

    }



}
