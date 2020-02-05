package com.example.bidding_interface2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bidding_interface2.Model.ShoppingItem;
import com.example.bidding_interface2.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ShoppingListAdapter extends ArrayAdapter<ShoppingItem> {
    Context mContext;
    public ShoppingListAdapter(@NonNull Context context, List<ShoppingItem> items) {
        super(context, 0, items);
        this.mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false
            );
        }

        ShoppingItem currentItem = getItem(position);

        ImageView img = (ImageView) listItemView.findViewById(R.id.itemIcon);
//        Picasso.with(getContext())
//                .load(mContext.getApplicationContext().getString(R.string.ip)
//                        + String.valueOf(currentItem.getProductID())
//                        + ".jpg")
//                .fit().centerCrop()
//                .into(img);

        TextView name = (TextView) listItemView.findViewById(R.id.itemName);
        name.setText(currentItem.getName());

        TextView description = (TextView) listItemView.findViewById(R.id.itemDescription);
        description.setText(currentItem.getDescription());

        TextView cost = (TextView) listItemView.findViewById(R.id.itemPrice);
        cost.setText(currentItem.getPrice());

        return listItemView;
    }
}
