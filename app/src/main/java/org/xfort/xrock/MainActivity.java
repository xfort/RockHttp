package org.xfort.xrock;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    void initView() {
        findViewById(R.id.okhttp_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.okhttp_btn:
                setOkHttpFragment();
                break;
        }
    }

    void setOkHttpFragment() {
        RockHttpFragment fragment = new RockHttpFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, fragment).commit();
    }
}
