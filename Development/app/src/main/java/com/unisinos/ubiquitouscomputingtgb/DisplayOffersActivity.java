package com.unisinos.ubiquitouscomputingtgb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class DisplayOffersActivity extends AppCompatActivity {

    private double latitude;
    private double longitude;
    private int range;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_offers);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();

        String latitudeText = intent.getStringExtra(MainActivity.LATITUDE);
        String longitudeText = intent.getStringExtra(MainActivity.LONGITUDE);
        String rangeText = intent.getStringExtra(MainActivity.RANGE);

        if(latitudeText != null && longitudeText != null) {
            latitude = Double.parseDouble(latitudeText);
            longitude = Double.parseDouble(longitudeText);
        } else {

        }

        if(rangeText != null) {
            range = Integer.parseInt(rangeText);
        }

        // Capture the layout's TextView and set the string as its text
        TextView textLatitudeView = (TextView) findViewById(R.id.textLatitudeView);
        TextView textLongitudeView = (TextView) findViewById(R.id.textLongitudeView);

        textLatitudeView.setText(String.valueOf(latitude));
        textLongitudeView.setText(String.valueOf(longitude));

        DBHandler dbHandler = new DBHandler(this);
        List<Offer> offers = dbHandler.getAllOffers();

        ListView listView = (ListView) findViewById(R.id.listView);

        OfferAdapter offerAdapter = new OfferAdapter(this, offers);
        listView.setAdapter(offerAdapter);

    }
}



















