package com.unisinos.ubiquitouscomputingtgb;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;


public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener  {

    public static final String LATITUDE= "com.unisinos.ubiquitouscomputingtgb.LATITUDE";
    public static final String LONGITUDE = "com.unisinos.ubiquitouscomputingtgb.LONGITUDE";
    public static final String RANGE = "com.unisinos.ubiquitouscomputingtgb.RANGE";

    private static final String[] LOCATION_PERMS={
            android.Manifest.permission.ACCESS_FINE_LOCATION
    };
    private static final int INITIAL_REQUEST=1337;
    private static final int LOCATION_REQUEST=INITIAL_REQUEST+3;

    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;
    private String mLatitude;
    private String mLongitude;
    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        dbHandler = new DBHandler(this);
        dbHandler.resetDatabase();
        insertEntriesToDB();
    }

    public void getLocationAndShowOfferList(View view) {

        Intent intent = new Intent(this, DisplayOffersActivity.class);

        if(canAccessLocation()) {
            mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

            if (mLocation != null) {
                mLatitude = String.valueOf(mLocation.getLatitude());
                mLongitude = String.valueOf(mLocation.getLongitude());
            }

            intent.putExtra(LATITUDE, mLatitude);
            intent.putExtra(LONGITUDE, mLongitude);

            EditText range = (EditText) findViewById(R.id.rangeText);
            intent.putExtra(RANGE, range.getText().toString());

            startActivity(intent);
        } else {
            Toast.makeText(this, "No permission granted to get location", Toast.LENGTH_LONG).show();
        }


    }

    @Override
    public void onConnected(Bundle bundle) {
        if(!canAccessLocation()) {
            requestPermissions(LOCATION_PERMS, LOCATION_REQUEST);
            insertEntriesToDB();
        }
    }

    private boolean canAccessLocation() {
        return PackageManager.PERMISSION_GRANTED == checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION);
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    private void insertEntriesToDB() {
        dbHandler.addOffer(new Offer("Pizza com borda", "Pizza de vários sabores com borda de graça e refrigerante", "piemonte.com.br", -29.7848386, -51.1533123, 32.45, "Piemonte", "Food"));
        dbHandler.addOffer(new Offer("Pizza sem borda", "Pizza de vários sabores sem borda de graça e refrigerante", "piemonte.com.br", -29.7848386, -51.1533123, 28.79, "Piemonte", "Food"));
        dbHandler.addOffer(new Offer("Troca de pneus", "Traga seus pneus e nós realizamos a troca de graça", "zedaborracharia.com.br", -29.7767882, -51.1741269, 0.0, "Ze da Borracharia", "Services"));
        dbHandler.addOffer(new Offer("Futebol sete hora", "Venha jogar na Sun7, de segunda a quinta, das 17h as 23h", "sun7premium.com", -29.8710627, -51.1684239, 110.0, "Sun7", "Entertainment"));
        dbHandler.addOffer(new Offer("Bicicleta aro 20", "Bicicleta Caloi aro 20 pouco usada", "", -30.0427254, -51.2292825, 635.0, "João Alberto", "Used Goods"));
    }

}
