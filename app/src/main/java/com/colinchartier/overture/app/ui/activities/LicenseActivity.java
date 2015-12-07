package com.colinchartier.overture.app.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.colinchartier.overture.app.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LicenseActivity extends AppCompatActivity {
    @Bind(R.id.app_bar)
    Toolbar toolbar;

    @Bind(R.id.license_text)
    TextView licenseText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true); //Don't show title on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        StringBuilder textBuilder = new StringBuilder();
        BufferedReader licenseReader = new BufferedReader(new InputStreamReader(getResources().openRawResource(R.raw.license_info)));
        try {
            while (licenseReader.ready()) {
                textBuilder.append(licenseReader.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                licenseReader.close();
            } catch (IOException e) {
            }
        }
        licenseText.setText(Html.fromHtml(textBuilder.toString()));
    }
}
