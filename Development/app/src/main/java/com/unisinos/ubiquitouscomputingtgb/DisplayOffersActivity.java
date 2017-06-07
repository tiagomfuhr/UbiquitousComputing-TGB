package com.unisinos.ubiquitouscomputingtgb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayOffersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_offers);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String mLatitude = intent.getStringExtra(MainActivity.LATITUDE);
        String mLongitude = intent.getStringExtra(MainActivity.LONGITUDE);

        // Capture the layout's TextView and set the string as its text
        TextView textLatitudeView = (TextView) findViewById(R.id.textLatitudeView);
        TextView textLongitudeView = (TextView) findViewById(R.id.textLongitudeView);
        textLatitudeView.setText((mLatitude));
        textLongitudeView.setText(mLatitude.toString());
    }
}
