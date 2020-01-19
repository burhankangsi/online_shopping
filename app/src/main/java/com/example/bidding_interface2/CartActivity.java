package com.example.bidding_interface2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bidding_interface2.Adapter.CustomCartListAdapter;
import com.example.bidding_interface2.Adapter.CustomProductListAdapter;
import com.example.bidding_interface2.Model.ProductClass;
import com.example.bidding_interface2.Model.Single_Bid_Item;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    private static ArrayList<Single_Bid_Item> CartProductList;
    private ArrayList<String> ProductID;
    CustomCartListAdapter adapter;
    private Integer TotalPrice =0;
    @SuppressWarnings("unchecked")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //requestWindowFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Firebase.setAndroidContext(this);
        /** ENABLING THE HOME BUTTON ON ACTION BAR **/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /** FINDING THE RELEVANT ELEMENTS **/
        final ListView cartlistView = (ListView) findViewById(R.id.cartlistView);
        info.hoang8f.widget.FButton CheckoutButton = (info.hoang8f.widget.FButton) findViewById(R.id.Checkoutbutton_cart);
        /** INITIALISATION **/
        CartProductList = new ArrayList<>();
        ProductID = new ArrayList<>();

        /** PROGRESS BAR CODE **/
        final ProgressDialog progress = new ProgressDialog(this);
        progress.setCancelable(false);
        progress.setMessage("LOADING CART...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();

        /** MAKING A REFERENCE TO THE CART URL IN FIREBASE **/
        Firebase CartRef = new Firebase("https://signupemail-50215.firebaseio.com/Carts/");

        CartRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    System.out.println("POST SNAPSHOT IS " + postSnapshot);

                    /** RETRIEVING DETAILS OF EACH PRODUCT IN THE FOR LOOP, ADDING THEM TO LIST AND UPDATING THE TOTAL PRICE **/
                    Single_Bid_Item CartProduct = postSnapshot.getValue(Single_Bid_Item.class);
                    Single_Bid_Item product = new Single_Bid_Item(CartProduct.getItem_name()
                            , CartProduct.getImage()
                            , CartProduct.getItem_price()
                            , CartProduct.getDescription()
                            , CartProduct.getSeller()
                            , CartProduct.getId()
                            , CartProduct.getQuantity());
                    TotalPrice = TotalPrice + CartProduct.getItem_price();
                    CartProductList.add(product);
                    ProductID.add(product.getId());
                }
                /** SETTING THE ADAPTER IF CARTPRODUCTLIST IS NOT NULL **/
                if (CartProductList.isEmpty()) {
                    progress.dismiss();
                    /** CUSTOM TOAST MESSAGE **/
                    Toast toast = Toast.makeText(CartActivity.this, "", Toast.LENGTH_SHORT);
                    View view = toast.getView();
                    view.setBackgroundResource(R.drawable.rounded_square);
                    TextView text = (TextView) view.findViewById(android.R.id.message);
                    text.setText(" YOU GOT TO PUT SOMETHING IN THE CART! ");
                    text.setTextColor(getResources().getColor(R.color.colorWhite));
                    toast.show();
//                    Intent intent = new Intent(CartActivity.this, CustomerCart.class);
//                    CartActivity.this.startActivity(intent);
                    Fragment fragment = new CustomerCartFragment();

                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                    transaction.replace(R.id.content_frame, fragment).commit();
                } else {
                    adapter = new CustomCartListAdapter(CartProductList, getLayoutInflater(), CartActivity.this,
                            new CustomProductListAdapter.ButtonClickListener() {

                                /**
                                 * CODE FOR DELETE BUTTON ON EACH ITEM- DELETING THE PRODUCT FROM FIREBASE AND RELOADING THE ACTIVITY
                                 **/
                                @Override
                                public void onButtonClick(int position, View v) {

                                    /** MAKING A REFERENCE TO DELETE THE PARTICULAR PRODUCT-ID **/
                                    Firebase deleteref = new Firebase("https://signupemail-50215.firebaseio.com/Carts/"
                                            + ProductID.get(position));
                                    deleteref.removeValue();
                                    Snackbar.make(cartlistView, getResources().getString(R.string.Deleted_Cart), Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                    adapter.notifyDataSetChanged();
                                    //RELOAD THE ACTIVITY FOR ANY FUTURE CHANGES
                                    Intent intent = getIntent();
                                    finish();
                                    startActivity(intent);
                                }
                            });
                    /** SETTING THE ADAPTER IN THE LIST VIEW **/
                    cartlistView.setAdapter(adapter);
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());

            }
        });

                   progress.dismiss();

        /**THIS IS THE CODE FOR OPENING UP OF DESCRIPTION ACTIVITY WHEN USER CLICKS THE ITEM IN THE CART**/
        cartlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent DescriptionIntent = new Intent(CartActivity.this, ProductDescription.class);
                DescriptionIntent.putExtra("ProductDetails",CartProductList.get(position));
                CartActivity.this.startActivity(DescriptionIntent);
            }
        });

        /** SETTING ONCLICKLISTENER ON THE CHECKOUT BUTTON **/
        CheckoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /** CUSTOM TOAST MESSAGE **/
                Toast toast = Toast.makeText(CartActivity.this,"", Toast.LENGTH_LONG);
                View view = toast.getView();
                view.setBackgroundResource(R.drawable.rounded_square);
                TextView text = (TextView) view.findViewById(android.R.id.message);
                text.setTextColor(getResources().getColor(R.color.colorWhite));
                text.setText(" TOTAL AMOUNT PAYABLE IS Rs "+TotalPrice+" ");
                toast.show();
            }
        });

    }
    /** FUNCTION TO GET REALTIME CART SIZE IN OFFLINE MODE **/
    public static int getCartListSize(){
        try {
            if (CartProductList.size() != 0) {
                return CartProductList.size();
            }
            else {
                return 0;
            }
        } catch (NullPointerException e) {
            return 0;
        }
    }
   // ** WHEN CLICKED ON BACK BUTTON **/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // BACK CLICKED. GO TO HOME.
//                Intent intent = new Intent(this, CustomerCart.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);

                Fragment fragment = new CustomerCartFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.content_frame, fragment).commit();


                //FINISH THE CURRENT ACTIVITY
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

