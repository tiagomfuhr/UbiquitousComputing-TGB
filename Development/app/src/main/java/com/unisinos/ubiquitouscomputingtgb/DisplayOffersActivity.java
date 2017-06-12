package com.unisinos.ubiquitouscomputingtgb;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DisplayOffersActivity extends AppCompatActivity {

    private static final String OFFER = "OFFER";

    private double latitude;
    private double longitude;
    private int range;
    private Location currentLocation;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
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
            currentLocation = new Location("");
            currentLocation.setLatitude(latitude);
            currentLocation.setLongitude(longitude);
        } else {

        }

        if(rangeText != null) {
            range = Integer.parseInt(rangeText);
        }

        DBHandler dbHandler = new DBHandler(this);
        List<Offer> offers = dbHandler.getAllOffers();

        ListView listView = (ListView) findViewById(R.id.listView);

        OfferAdapter offerAdapter = new OfferAdapter(this, filterOffersBasedOnCurrentLocationAndGivenRadius(offers));
        listView.setAdapter(offerAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {


                Offer offer = (Offer) arg0.getItemAtPosition(position);


                Intent intent = new Intent(DisplayOffersActivity.this, OfferInfoActivity.class);
                intent.putExtra(OFFER, offer);

                try {

                    startActivity(intent, savedInstanceState);
                } catch (Exception ex) {

                }

                //Offer offer = (Offer) arg0.getItemAtPosition(position);
                /*
                String uri = "http://maps.google.com/maps?daddr=" + offer.getLatitude() + "," + offer.getLongitude();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setPackage("com.google.android.apps.maps");
                try
                {
                    startActivity(intent);
                }
                catch(ActivityNotFoundException ex)
                {
                    try
                    {
                        Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                        startActivity(unrestrictedIntent);
                    }
                    catch(ActivityNotFoundException innerEx)
                    {
                        Toast.makeText(DisplayOffersActivity.this, "Please install a maps application", Toast.LENGTH_LONG).show();
                    }
                }
                */

            }
        });

    }

    private List<Offer> filterOffersBasedOnCurrentLocationAndGivenRadius(List<Offer> offers) {
        List<Offer> filteredOffers = new ArrayList<>();
        for (Offer offer : offers) {
            Location offerLocation = new Location("");
            offerLocation.setLatitude(offer.getLatitude());
            offerLocation.setLongitude(offer.getLongitude());
            float distance = currentLocation.distanceTo(offerLocation);
            if (distance < ( range * 1000 )) {
                offer.setDistanceToUser(distance);
                filteredOffers.add(offer);
            }
        }
        return filteredOffers;
    }
}



















