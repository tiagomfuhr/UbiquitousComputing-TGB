package com.unisinos.ubiquitouscomputingtgb;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "TGBUbiquitous";
    private static final String TABLE_OFFERS = "Offers";
    private static final String KEY_ID = "id";
    private static final String KEY_OFFER = "offer";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_URL = "url";
    private static final String KEY_LATITUDE = "latitude";
    private static final String KEY_LONGITUDE = "longitude";
    private static final String KEY_PRICE = "price";
    private static final String KEY_DEALER = "dealer";
    private static final String KEY_OFFERTYPE = "offertype";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_OFFERS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_OFFER + " TEXT,"
                + KEY_DESCRIPTION + " TEXT,"
                + KEY_URL + " VARCHAR,"
                + KEY_LATITUDE + " TEXT,"
                + KEY_LONGITUDE + " TEXT,"
                + KEY_PRICE + " DOUBLE,"
                + KEY_DEALER + " TEXT,"
                + KEY_OFFERTYPE + " TEXT" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OFFERS);
        onCreate(db);
    }

    public void addOffer(Offer offer) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_OFFER, offer.getOffer());
        values.put(KEY_DESCRIPTION, offer.getDescription());
        values.put(KEY_URL, offer.getUrl());
        values.put(KEY_LATITUDE, offer.getLatitude());
        values.put(KEY_LONGITUDE, offer.getLongitude());
        values.put(KEY_PRICE, offer.getPrice());
        values.put(KEY_DEALER, offer.getDealer());
        values.put(KEY_OFFERTYPE, offer.getOfferType());

        db.insert(TABLE_OFFERS, null, values);
        db.close();
    }

    public Offer getOffer(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_OFFERS, new String[]{KEY_ID, KEY_OFFER, KEY_DESCRIPTION, KEY_URL, KEY_LATITUDE, KEY_LONGITUDE, KEY_PRICE, KEY_DEALER, KEY_OFFERTYPE},
                KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);

        if(cursor != null) {
            cursor.moveToFirst();
        }

        Offer offer = new Offer(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3),
                Double.parseDouble(cursor.getString(4)), Double.parseDouble(cursor.getString(5)), Double.parseDouble(cursor.getString(6)),
                cursor.getString(7), cursor.getString(8));

        return offer;
    }

    public List<Offer> getAllOffers() {
        List<Offer> offers = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_OFFERS, null);

        if(cursor.moveToFirst()) {
            do {
                offers.add(new Offer(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3),
                        Double.parseDouble(cursor.getString(4)), Double.parseDouble(cursor.getString(5)), Double.parseDouble(cursor.getString(6)),
                        cursor.getString(7), cursor.getString(8)));
            } while (cursor.moveToNext());
        }

        return offers;
    }

    public void resetDatabase() {
        SQLiteDatabase db = this.getWritableDatabase();
        onUpgrade(db, DATABASE_VERSION, DATABASE_VERSION);

    }
}
