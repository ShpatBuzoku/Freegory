package com.example.myfridge;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class TwoColumns_ListAdapter extends ArrayAdapter<Product> {

    private LayoutInflater inflater;
    private ArrayList<Product> products;
    private int viewResourceId;

    public TwoColumns_ListAdapter(Context context, int textViewResourceId, ArrayList<Product> products){
        super(context,textViewResourceId,products);
        this.products = products;
        inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        viewResourceId = textViewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parents){
        convertView = inflater.inflate(viewResourceId,null);
        Product product = products.get(position);

        if(product !=null){
            TextView item = (TextView) convertView.findViewById(R.id.items);
            TextView expDate =(TextView) convertView.findViewById(R.id.expirydate);

            if(item != null){
                item.setText(product.getName());
            }
            if(expDate != null){
                expDate.setText(product.getExpiryDate());
            }
        }
        return convertView;
    }
}
