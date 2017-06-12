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
        dbHandler.addOffer(new Offer("Pizza com borda", "Pizza de vários sabores com borda de graça e refrigerante", "http://piemonte.com.br", -29.773993, -51.153323, 32.45, "Piemonte", "Food"));
        dbHandler.addOffer(new Offer("Pizza sem borda", "Pizza de vários sabores sem borda de graça e refrigerante", "http://piemonte.com.br", -29.773993, -51.153323, 28.79, "Piemonte", "Food"));
        dbHandler.addOffer(new Offer("Troca de pneus", "Traga seus pneus e nós realizamos a troca de graça", "http://zedaborracharia.com.br", -29.771183, -51.135256, 0.0, "Ze da Borracharia", "Services"));
        dbHandler.addOffer(new Offer("Futebol sete hora", "Venha jogar na Sun7, de segunda a quinta, das 17h as 23h", "http://sun7premium.com", -29.844436, -51.150095, 110.0, "Sun7", "Entertainment"));
        dbHandler.addOffer(new Offer("Bicicleta aro 20", "Bicicleta Caloi aro 20 pouco usada", "http://olx.com.br/caloi-aro-20", -30.0427254, -51.2292825, 635.0, "João Alberto", "Used Goods"));
        dbHandler.addOffer(new Offer("Buffer Livre Sushi 1 pessoa", "Buffet livre de sushi no Bamboo para 1 pessoa, valido de terças a sextas", "http://www.bamboosushihouse.com.br/", -30.044002, -51.187666, 39.90, "Bamboo Sushi House", "Food"));
        dbHandler.addOffer(new Offer("Buffet Livre Sushi 2 pessoas", "Buffet livre de sushi no Bamboo para 1 pessoa, valido de terças a sextas", "http://www.bamboosushihouse.com.br/", -30.044002, -51.187666, 39.90, "Bamboo Sushi House", "Food"));
        dbHandler.addOffer(new Offer("Rodizio de churrasco até 4 pessoas", "Rodízio de churrasco, pratos quentes, saladas e sobremesa para 1, 2 ou 4 pessoas na Churrascaria Arizona - Partenon", "https://www.facebook.com/ChurrascariaArizona?rf=200487800048225", -30.065365, -51.154663, 27.00, "Churrascaria Arizona", "Food"));
        dbHandler.addOffer(new Offer("Porção de Tapas + Chope Artesanal", "Porção de tapas (8 unidades) + 2 chopes artesanais de 300 ml", "http://www.lacuevaflamencocaxias.com.br", -29.170016, -51.193267, 24.90, "La Cueva Gastronomia y Arte", "Food"));
        dbHandler.addOffer(new Offer("Visita Design de Sombrancelhas", "Design de sobrancelhas: tem como objetivo corrigir e desenhar o formato das sobrancelhas", "http://www.aluzestetica.com.br/", -30.030923, -51.228354, 29.90, "A luz estetica", "Beauty and Health"));
        dbHandler.addOffer(new Offer("Serra Verde Express", "passeio de trem com pôr do sol turístico para 1 pessoa", "http://serraverdeexpress.com.br/", -25.436594, -49.257297, 129.90, "Serra Verde", "Entertainment"));
        dbHandler.addOffer(new Offer("Paintball hora", "Hora no paintball para 10 pessoas", "http://paintball.com.br", -29.677253, -51.117805, 160.90, "Ruinas paintball", "Entertainment"));

    }

}
