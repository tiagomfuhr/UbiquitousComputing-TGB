package com.unisinos.ubiquitouscomputingtgb;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class OfferInfoActivity extends AppCompatActivity {

    private static final String OFFER = "OFFER";
    private Offer offer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offer_info);

        Intent intent = getIntent();
        offer = (Offer) intent.getSerializableExtra(OFFER);

        TextView textOffer = (TextView) findViewById(R.id.textOffer);
        TextView textDealer = (TextView) findViewById(R.id.textDealer);
        TextView textPrice = (TextView) findViewById(R.id.textPrice);
        TextView textOfferType = (TextView) findViewById(R.id.textOfferType);
        TextView textDistance = (TextView) findViewById(R.id.textDistance);
        TextView textDescription = (TextView) findViewById(R.id.textDescription);

        textOffer.setText(offer.getOffer());
        textDealer.setText(offer.getDealer());
        textPrice.setText("U$ " + String.valueOf(offer.getPrice()));
        textOfferType.setText(offer.getOfferType());
        textDistance.setText(String.valueOf((int)offer.getDistanceToUser()) + " meters");
        textDescription.setText(offer.getDescription());
    }

    public void GetLocationAndOpenGoogleMaps(View view) {
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
                Toast.makeText(OfferInfoActivity.this, "Please install a maps application", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void OpenWebsite(View view) {
        String uri = offer.getUrl();
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        try
        {
            startActivity(intent);
        }
        catch(ActivityNotFoundException ex) {

        }
    }
}
