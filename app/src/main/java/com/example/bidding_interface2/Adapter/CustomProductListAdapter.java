package com.example.bidding_interface2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bidding_interface2.Model.ProductClass;
import com.example.bidding_interface2.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomProductListAdapter extends BaseAdapter {
    private Context mContext;
    private ButtonClickListener mcartClickListener = null;
    private ButtonClickListener mbargainClickListener  = null;
    private ArrayList<ProductClass> ProductList;
    int screenWidth;
    ViewHolder viewHolder;
    public CustomProductListAdapter(Context c, ArrayList<ProductClass> ProductList,int screenWidth, ButtonClickListener cartlistener,
                                    ButtonClickListener bargainlistner) {
        mContext = c;
        this.ProductList = ProductList;
        this.screenWidth = screenWidth;
        mcartClickListener = cartlistener;
        mbargainClickListener = bargainlistner;
    }

    static class ViewHolder {
        ImageView ProductImage;
        TextView ProductName;
        TextView AddedInfo;
        TextView ProductPrice;
        Button AddToCart, Bargain;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.fragment_home, parent, false);
        }
        final ProductClass curProduct = ProductList.get(position);

        viewHolder = new ViewHolder();
        viewHolder.ProductName = (TextView) convertView.findViewById(R.id.productname_prod_list);
        viewHolder.ProductPrice = (TextView) convertView.findViewById(R.id.productprice_prod_list);
        viewHolder.ProductImage = (ImageView) convertView.findViewById(R.id.productimageview_prod_list);
        viewHolder.AddedInfo = (TextView) convertView.findViewById(R.id.addedInfo_prod_list);
        viewHolder.AddedInfo.setVisibility(View.GONE);
        viewHolder.AddToCart = (Button) convertView.findViewById(R.id.btn_add_to_cart_prod_list);
        viewHolder.Bargain = (Button) convertView.findViewById(R.id.btn_bargain_cart_prod_list);

        viewHolder.AddToCart.setTag(position);
        viewHolder.Bargain.setTag(position);

        final View finalConvertView1 = convertView;
        viewHolder.AddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mcartClickListener != null)
                    mcartClickListener.onButtonClick((Integer) v.getTag(), finalConvertView1);

            }
        });
        final View finalConvertView = convertView;
        viewHolder.Bargain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mbargainClickListener != null)
                    mbargainClickListener.onButtonClick((Integer) v.getTag(), finalConvertView);
            }
        });
        viewHolder.ProductImage.getLayoutParams().width = screenWidth;
        viewHolder.ProductImage.getLayoutParams().height = Math.round(screenWidth / 2);
        viewHolder.ProductImage.requestLayout();

        viewHolder.ProductName.setText(curProduct.getName());
        viewHolder.ProductPrice.setText("Rs. "+curProduct.getPrice());

//        Picasso.with(mContext)
//                .setIndicatorsEnabled(false);
//        Picasso.with(mContext)
//                .load(curProduct.getImage())
//                .placeholder(R.drawable.album3)
//                .error(R.drawable.album3)
//                .into(viewHolder.ProductImage);

        return convertView;
    }
    public interface ButtonClickListener {
        public abstract void onButtonClick(int position, View view);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return ProductList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }


}

