package com.unisinos.ubiquitouscomputingtgb;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

class OfferAdapter extends BaseAdapter{

    private List<Offer> offers;
    private Activity activity;
    private LayoutInflater layoutInflater;

    public OfferAdapter(Activity activity, List<Offer> offers) {
        this.activity = activity;
        this.offers = offers;
    }

    @Override
    public int getCount() {
        return offers.size();
    }

    @Override
    public Object getItem(int position) {
        return offers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(layoutInflater == null)
            layoutInflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null)
            convertView = layoutInflater.inflate(R.layout.list_item_offer, null);

        TextView textOffer = (TextView)convertView.findViewById(R.id.textOffer);
        TextView textDealer = (TextView)convertView.findViewById(R.id.textDealer);
        TextView textPrice = (TextView)convertView.findViewById(R.id.textPrice);
        TextView textOfferType = (TextView)convertView.findViewById(R.id.textOfferType);
        TextView textDistance = (TextView)convertView.findViewById(R.id.textDistance);

        Offer offer = offers.get(position);

        textOffer.setText(offer.getOffer());
        textDealer.setText(offer.getDealer());
        textPrice.setText("U$ " + String.valueOf(offer.getPrice()));
        textOfferType.setText(offer.getOfferType());
        textDistance.setText(String.valueOf(offer.getDistanceToUser()) + " meters");

        return convertView;
    }
}
