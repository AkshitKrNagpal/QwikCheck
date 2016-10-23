package cf.qwikcheck.qwikcheck;

import android.app.Activity;
import android.os.Bundle;

import cf.qwikcheck.qwikcheck.Base.QwikCheckBaseActivity;

public class MainActivity extends QwikCheckBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
