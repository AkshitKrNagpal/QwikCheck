package cf.qwikcheck.qwikcheck.base;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by akshit on 24/10/16.
 */

public class QwikCheckBaseActivity extends Activity {

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
