package com.example.bidding_interface2.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bidding_interface2.Model.BargainProduct;
import com.example.bidding_interface2.R;
import com.firebase.client.Firebase;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CustomBargainCartAdapter extends BaseAdapter {
    private CustomProductListAdapter.ButtonClickListener mdeleteClickListener = null;
    private CustomProductListAdapter.ButtonClickListener mCartClickListener = null;
    private List<BargainProduct> mBargainCartProductList;
    private ArrayList<String> bidid;
    private LayoutInflater mInflater;
    private Context context;

    public CustomBargainCartAdapter(ArrayList <BargainProduct> BargainCartProductList
                                    ,ArrayList<String> bidid
    ,LayoutInflater inflater
    ,Context context
    ,CustomProductListAdapter.ButtonClickListener mdeleteClickListener
    ,CustomProductListAdapter.ButtonClickListener mCartClickListener){
        mBargainCartProductList = BargainCartProductList;
        mInflater = inflater;
        this.context = context;
        this.bidid = bidid;
        this.mdeleteClickListener = mdeleteClickListener;
        this.mCartClickListener = mCartClickListener;


    }

    @Override
    public int getCount() {
        return mBargainCartProductList.size();
    }

    @Override
    public BargainProduct getItem(int position) {
        return mBargainCartProductList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewItemHolder item;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.bargain_cart_item_layout, null);
            item = new ViewItemHolder();
            item.ProductTitle = (TextView) convertView.findViewById(R.id.productname_barg_cart);
            item.ProductImageView = (ImageView) convertView.findViewById(R.id.pimageview_barg_cart);
            item.PriceTextView = (TextView) convertView.findViewById(R.id.productprice_barg_cart);
            item.BargainPriceTextView = (TextView) convertView.findViewById(R.id.bargainpricetv_barg_cart);
            item.DeleteButton = (ImageButton) convertView.findViewById(R.id.DeleteButton_barg_cart);
            item.AcceptImageView = (ImageView) convertView.findViewById(R.id.acceptImageView_barg_cart);
            item.RejectImageView = (ImageView) convertView.findViewById(R.id.rejectImageView_barg_cart);
            item.ProcessingImageView = (ImageView) convertView.findViewById(R.id.processingImageView_barg_cart);
            item.movetocart = (LinearLayout) convertView.findViewById(R.id.movetocart);
            item.Accepted = (TextView) convertView.findViewById(R.id.accepted_barg_cart);
            item.Processing = (TextView) convertView.findViewById(R.id.processing_barg_cart);
            item.Rejected = (TextView) convertView.findViewById(R.id.rejected_barg_cart);
            item.offer = (TextView) convertView.findViewById(R.id.offer_barg_cart);
            item.ViewOffer = (TextView) convertView.findViewById(R.id.ViewOffer_barg_cart);
            convertView.setTag(item);
        } else {
            item = (ViewItemHolder) convertView.getTag();
        }

        item.AcceptImageView.setVisibility(View.GONE);
        item.ProcessingImageView.setVisibility(View.GONE);
        item.RejectImageView.setVisibility(View.GONE);
        item.movetocart.setVisibility(View.GONE);
        item.Accepted.setVisibility(View.GONE);
        item.Processing.setVisibility(View.GONE);
        item.Rejected.setVisibility(View.GONE);
        item.offer.setVisibility(View.GONE);
        item.ViewOffer.setVisibility(View.GONE);

        final BargainProduct curProduct = mBargainCartProductList.get(position);

//        Picasso picasso = Picasso.with(context);
//        picasso.setIndicatorsEnabled(false);
//        picasso.load(curProduct.getImage())
//                .networkPolicy(NetworkPolicy.OFFLINE)
//                .resize(200, 200)
//                .placeholder(R.drawable.album3)
//                .error(R.drawable.album3)
//                .into(item.ProductImageView);

        item.ProductTitle.setText(curProduct.getName());
        item.PriceTextView.setText("Rs. "+curProduct.getPrice());
        item.DeleteButton.setTag(position);
        item.movetocart.setTag(position);
        item.movetocart.setClickable(false);
        final View finalConvertView = convertView;
        item.DeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mdeleteClickListener != null)
                    mdeleteClickListener.onButtonClick((Integer) v.getTag(), finalConvertView);
            }
        });
        item.movetocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCartClickListener != null)
                    mCartClickListener.onButtonClick((Integer) v.getTag(),finalConvertView);
            }
        });


        item.BargainPriceTextView.setText("Requested at Rs. "+ curProduct.getBidValue());
        Integer savings = curProduct.getPrice()-curProduct.getBidValue();

        final Firebase statusref = new Firebase("https://signupemail-50215.firebaseio.com/BargainCarts/"
                +bidid.get(position)+"/"
                +"status");

        switch (curProduct.getStatus())
        {
            case -1:item.RejectImageView.setVisibility(View.VISIBLE);
                item.Rejected.setVisibility(View.VISIBLE);
                break;

            case 0:item.ProcessingImageView.setVisibility(View.VISIBLE);
                item.Processing.setVisibility(View.VISIBLE);
                break;

            case 1:item.PriceTextView.setText("New Price Rs."+curProduct.getBidValue());
                item.BargainPriceTextView.setText("~You Saved Rs. "+savings);
                item.AcceptImageView.setVisibility(View.VISIBLE);
                item.movetocart.setVisibility(View.VISIBLE);
                item.Accepted.setVisibility(View.VISIBLE);
                item.movetocart.setClickable(true);
                break;
            case 2:item.AcceptImageView.setVisibility(View.VISIBLE);
                item.AcceptImageView.setImageResource(R.drawable.ic_call_received_black_24dp);
                item.offer.setVisibility(View.VISIBLE);
                item.ViewOffer.setVisibility(View.VISIBLE);
                break;
            case 3:item.AcceptImageView.setVisibility(View.VISIBLE);
                item.AcceptImageView.setImageResource(R.drawable.ic_call_received_black_24dp);
                item.movetocart.setVisibility(View.VISIBLE);
                item.offer.setVisibility(View.VISIBLE);
                item.offer.setText("Offer Accepted!");
                item.ViewOffer.setVisibility(View.GONE);
                item.movetocart.setClickable(true);
                break;
            case 4:item.RejectImageView.setVisibility(View.VISIBLE);
                item.offer.setText("Offer Rejected!");
                break;
            default:item.ProcessingImageView.setVisibility(View.VISIBLE);
                item.Processing.setVisibility(View.VISIBLE);
                break;
        }
        notifyDataSetChanged();


        Log.e("--->offer",curProduct.getName());
        Log.e("--->offer",curProduct.getAdditionals());
        if (!curProduct.getAdditionals().equals("") && curProduct.getStatus()==2) {

            item.offer.setVisibility(View.VISIBLE);
            item.ViewOffer.setVisibility(View.VISIBLE);
            item.AcceptImageView.setVisibility(View.VISIBLE);
            item.AcceptImageView.setImageResource(R.drawable.album2);
            item.ProcessingImageView.setVisibility(View.GONE);
            item.RejectImageView.setVisibility(View.GONE);
            item.movetocart.setVisibility(View.GONE);
            item.Accepted.setVisibility(View.GONE);
            item.Processing.setVisibility(View.GONE);
            item.Rejected.setVisibility(View.GONE);

//            item.ViewOffer.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    final Dialog dialog = new Dialog(context);
//                    dialog.setTitle("OFFER!");
//                    dialog.setContentView(R.layout.offer_dialog_layout);
//                    TextView offerUsername = (TextView) dialog.findViewById(R.id.offerUsername);
//                    TextView offersellername = (TextView) dialog.findViewById(R.id.offersellername);
//                    final TextView offer = (TextView) dialog.findViewById(R.id.offer);
//                    TextView offerprice = (TextView) dialog.findViewById(R.id.offerprice);
//                    Button acceptoffer = (Button) dialog.findViewById(R.id.acceptofferbutton);
//                    Button rejectoffer = (Button) dialog.findViewById(R.id.rejectofferbutton);
//                    Button offerDialogChatButton = (Button) dialog.findViewById(R.id.offerchatbutton);
//                    final String username = LoginActivity.getDefaults("UserID", context);
//
//                    offerUsername.setText("HELLO " + username + ".");
//                    offersellername.setText("Seller " + curProduct.getSeller() + " gave you an offer!");
//                    offer.setText("Offer-" + curProduct.getAdditionals());
//                    offerprice.setText("Final Price-Rs. " + curProduct.getFinalprice());
//
//
//                            /** CODE FOR ACCEPTOFFER BUTTON ON THE OFFER DIALOG **/
//                            acceptoffer.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//
//
//                                    Firebase bargainpriceref = new Firebase("https://bargainhawk.firebaseio.com/BargainCarts/"
//                                            + bidid.get(position) + "/"
//                                            + "bidValue");
//
//                                    statusref.setValue(3);
//                                    bargainpriceref.setValue(curProduct.getFinalprice());
//                                    dialog.dismiss();
//                                    /** CUSTOM TOAST MESSAGE **/
//                                    Toast toast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
//                                    View view = toast.getView();
//                                    view.setBackgroundResource(R.drawable.rounded_square);
//                                    TextView text = (TextView) view.findViewById(android.R.id.message);
//                                    text.setTextColor(context.getResources().getColor(R.color.colorWhite));
//                                    text.setText("  OFFER ACCEPTED!  ");
//                                    toast.show();
//                                }
//                            });
//                            /** CODE FOR REJECT OFFER BUTTON ON THE OFFER DIALOG **/
//                            rejectoffer.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//
//                                    Firebase statusref = new Firebase("https://bargainhawk.firebaseio.com/BargainCarts/"
//                                            + bidid.get(position) + "/"
//                                            + "status");
//                                    statusref.setValue(4);
//                                    dialog.dismiss();
//                                    /** CUSTOM TOAST MESSAGE **/
//                                    Toast toast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
//                                    View view = toast.getView();
//                                    view.setBackgroundResource(R.drawable.rounded_square);
//                                    TextView text = (TextView) view.findViewById(android.R.id.message);
//                                    text.setTextColor(context.getResources().getColor(R.color.colorWhite));
//                                    text.setText("  OFFER REJECTED!  ");
//                                    toast.show();
//                                }
//                            });
//                            /** CODE FOR CHAT ON THE OFFER DIALOG **/
//                            offerDialogChatButton.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    Intent ChatIntent = new Intent(context, ChatActivity.class);
//                                    ChatIntent.putExtra("ProductName", curProduct.getName());
//                                    ChatIntent.putExtra("SellerName", curProduct.getSeller());
//                                    ChatIntent.putExtra("ProductID", curProduct.getId());
//                                    context.startActivity(ChatIntent);
//                                }
//                            });
//
//                            if (!((Activity) context).isFinishing()) {
//                                dialog.show();
//                            }
//                        }
//                    });
                }

        return convertView;

        }
            private class ViewItemHolder {
                ImageView ProductImageView;
                ImageView ProcessingImageView;
                ImageView AcceptImageView;
                ImageView RejectImageView;
                TextView ProductTitle;
                TextView PriceTextView;
                TextView BargainPriceTextView;
                TextView Accepted;
                TextView Processing;
                TextView Rejected;
                TextView offer;
                TextView ViewOffer;
                ImageButton DeleteButton;
                LinearLayout movetocart;

    }
        }
