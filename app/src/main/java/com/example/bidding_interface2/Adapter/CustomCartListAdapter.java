package com.example.bidding_interface2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bidding_interface2.CartActivity;
import com.example.bidding_interface2.Model.ProductClass;
import com.example.bidding_interface2.Model.Single_Bid_Item;
import com.example.bidding_interface2.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CustomCartListAdapter extends BaseAdapter {
    private List<ProductClass> mCartProductList;
    private CustomProductListAdapter.ButtonClickListener mdeleteClickListener = null;
    private LayoutInflater mInflater;
    private Context context;
    public CustomCartListAdapter(ArrayList<ProductClass> CartProductList, LayoutInflater inflater
            , Context context, CustomProductListAdapter.ButtonClickListener mdeleteClickListener) {
        mCartProductList = CartProductList;
        mInflater = inflater;
        this.context = context;
        this.mdeleteClickListener = mdeleteClickListener;
    }

    public CustomCartListAdapter(ArrayList<Single_Bid_Item> cartProductList, LayoutInflater layoutInflater,
                                 CartActivity context, CustomProductListAdapter.ButtonClickListener action) {
        
    }


    @Override
    public int getCount() {
        return mCartProductList.size();
    }

    @Override
    public ProductClass getItem(int position) {
        return mCartProductList.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
     final  ViewItemHolder item;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.cart_item_layout, null);
            item = new ViewItemHolder();
            item.ProductTitle = (TextView) convertView.findViewById(R.id.product_name_cart_item);
            item.ProductImageView = (ImageView) convertView.findViewById(R.id.prod_img_cart_item);
            item.PriceTextView = (TextView) convertView.findViewById(R.id.product_price_cart_item);
            item.SellerTextView = (TextView) convertView.findViewById(R.id.seller_name_cart_item);
            item.DeleteButton = (ImageButton) convertView.findViewById(R.id.DeleteButton_cart_item);

            convertView.setTag(item);
        } else {
            item = (ViewItemHolder) convertView.getTag();
        }

        final ProductClass curProduct = mCartProductList.get(position);

        Picasso.with(context).setIndicatorsEnabled(false);
        Picasso.with(context)
                .load(curProduct.getImage())
                .resize(200, 200)
                .placeholder(R.drawable.album3)
                .error(R.drawable.album3)
                .into(item.ProductImageView);

        item.ProductTitle.setText(curProduct.getName());
        item.PriceTextView.setText("Rs. "+curProduct.getPrice());
        item.SellerTextView.setText(curProduct.getSeller());
        item.DeleteButton.setTag(position);

        final View finalConvertView = convertView;
        item.DeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mdeleteClickListener != null)
                    mdeleteClickListener.onButtonClick((Integer) v.getTag(), finalConvertView);
            }
        });
        return convertView;
    }

    private class ViewItemHolder {
        ImageView ProductImageView;
        TextView ProductTitle;
        TextView PriceTextView;
        TextView SellerTextView;
        ImageButton DeleteButton;
    }
    }

