package com.colinchartier.overture.app.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.colinchartier.overture.app.R;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.app_bar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        assert (getSupportActionBar() != null); //silly compiler, we're setting this already.
        getSupportActionBar().setDisplayShowTitleEnabled(false); //Don't show title on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white);
    }
}
