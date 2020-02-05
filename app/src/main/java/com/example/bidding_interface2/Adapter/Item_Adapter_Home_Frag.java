package com.example.bidding_interface2.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.bumptech.glide.Glide;
import com.example.bidding_interface2.BargainCart;
import com.example.bidding_interface2.CustomerCartFragment;
import com.example.bidding_interface2.Home_Drawer;
import com.example.bidding_interface2.Model.ImageUploadInfo;
import com.example.bidding_interface2.Model.ProductClass;
import com.example.bidding_interface2.Model.Single_Bid_Item;
import com.example.bidding_interface2.ProductDescription;
import com.example.bidding_interface2.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Item_Adapter_Home_Frag extends RecyclerView.Adapter<Item_Adapter_Home_Frag.ViewHolder> {

    private Context mContext;
    private List<ImageUploadInfo> itemList;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView item_name_single_card,  price_single_card, bargain;
        public ImageView thumbnail, overflow;
        public Button btn_add_to_cart_card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_name_single_card = (TextView) itemView.findViewById(R.id.item_name_card);
           // quantity_single_card = (TextView) itemView.findViewById(R.id.quantity_card);
            price_single_card = (TextView)itemView.findViewById(R.id.price_card);
            thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            overflow = (ImageView) itemView.findViewById(R.id.overflow);
            btn_add_to_cart_card = (Button)itemView.findViewById(R.id.btn_item_card);
            bargain = (TextView)itemView.findViewById(R.id.bargain_card);
        }
    }
    public Item_Adapter_Home_Frag(Context mContext, List<ImageUploadInfo> itemList)
    {
        this.mContext = mContext;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
//        return new MyViewHolder(view);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        ImageUploadInfo single_bid_item = itemList.get(position);
        holder.item_name_single_card.setText(single_bid_item.getName());
        holder.price_single_card.setText(single_bid_item.getPrice());
        // holder.quantity_single_card.setText(single_bid_item.getItem_quantity()+"quantity");
        Picasso.get().load(single_bid_item.getImage()).into(holder.thumbnail);

        //Loading Item cover using Glide Library
       //Glide.with(mContext).load(single_bid_item.getImage()).into(holder.thumbnail);

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow);
            }
        });

        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ProductDescription.class);
                mContext.startActivity(intent);
            }
        });
        holder.btn_add_to_cart_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new CustomerCartFragment();
                ((Home_Drawer)mContext).getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,
                        fragment,"Customer Cart").addToBackStack(null).commit();
            }
        });
        holder.bargain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, BargainCart.class);
                mContext.startActivity(intent);
            }
        });

    }
    private void showPopupMenu(View view)
        {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_item_card_home, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();

    }
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener{

        public MyMenuItemClickListener()
        {
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId())
            {
                case R.id.action_add_to_wishlist:
                    Toast.makeText(mContext,"Add to Wishlist", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_add_to_cart :
                    Toast.makeText(mContext,"Add to CartActivity", Toast.LENGTH_SHORT).show();
                    return true;
                 default:
            }
            return false;
        }
    }



    @NonNull
    @Override
    public int getItemCount() {
       // itemList = new ArrayList<>();
        return itemList.size();
    }
}
