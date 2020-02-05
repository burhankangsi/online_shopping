package com.example.bidding_interface2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bidding_interface2.Model.BargainProduct;
import com.example.bidding_interface2.Model.ProductClass;
import com.firebase.client.Firebase;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

public class ProductDescription extends AppCompatActivity {
    private static final String TAG =" " ;
    ProductClass ProductDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_description);

        /** ENABLING THE HOME BUTTON ON ACTION BAR **/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /**GETTING THE PRODUCT DETAILS FROM THE INTENT**/
        ProductDetails = (ProductClass) getIntent().getSerializableExtra("ProductDetails");
        /**FINDING THE VIEWS FROM THE LAYOUT OF THIS ACTIVITY**/
        ImageView PImage = (ImageView) findViewById(R.id.ProductImageView_prod_desc);
        TextView PName = (TextView) findViewById(R.id.ProductName_prod_desc);
        TextView PPrice = (TextView) findViewById(R.id.ProductPrice_prod_desc);
        TextView PDescription = (TextView) findViewById(R.id.ProductDescription_prod_desc);
        TextView PSeller = (TextView) findViewById(R.id.ProductSeller_prod_desc);
        info.hoang8f.widget.FButton AddTocart = (info.hoang8f.widget.FButton) findViewById(R.id.AddToCart_prof_desc);
        info.hoang8f.widget.FButton BargainButton = (info.hoang8f.widget.FButton) findViewById(R.id.BargainButton_prod_desc);

        // SETTING THE DETAILS IN THE VIEWS
        //Picasso.with(ProductDescription.this).setIndicatorsEnabled(true);  //only for debug tests
        try {
//            Picasso.with(ProductDescription.this)
//                    .load(ProductDetails.getImage())
//                    .placeholder(R.drawable.album3)
//                    .error(R.drawable.album3)
//                    .into(PImage);

        PName.setText(ProductDetails.getName());
        PPrice.setText("Rs. "+ProductDetails.getPrice());
        PDescription.setText(ProductDetails.getDescription());
        PSeller.setText("SOLD BY: "+ProductDetails.getSeller());
        }
        catch (NullPointerException e)
        {
            Log.e(TAG, e.toString());
        }

        // Setting Click Listener on Start Chat Button
        final Button ChatButton = (Button) findViewById(R.id.StartChatButton);
//        ChatButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent ChatIntent = new Intent(ProductDescription.this, ChatActivity.class);
//                ChatIntent.putExtra("ProductName", ProductDetails.getName());
//                ChatIntent.putExtra("SellerName", ProductDetails.getSeller());
//                ChatIntent.putExtra("ProductID",ProductDetails.getId());
//                ProductDescription.this.startActivity(ChatIntent);
//            }
//        });

        AddTocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** MAKING A REFERENCE TO CART URL IN FIREBASE FOR THE VALUES TO BE PUSHED **/

                final Firebase cartRef = new Firebase("https://signupemail-50215.firebaseio.com/")
                        .child("Carts");
                /** PUSHING THE PRODUCT TO THE CART DATABASE **/
                cartRef.child(ProductDetails.getId()).setValue(ProductDetails);

                /** CUSTOM TOAST MESSAGE **/
                Toast toast = Toast.makeText(ProductDescription.this,"", Toast.LENGTH_SHORT);
                view = toast.getView();
                view.setBackgroundResource(R.drawable.cart_image);
                TextView text = (TextView) view.findViewById(android.R.id.message);
                text.setTextColor(getResources().getColor(R.color.colorWhite));
                text.setText(getResources().getString(R.string.SuccessfulCart));
                toast.show();
                /** SNACKBAR **/
                Snackbar.make(view,getResources().getString(R.string.SNACKBAR_CART),Snackbar.LENGTH_LONG).show();
            }
        });
        BargainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /** MAKING A REFERENCE TO BARGAIN REQUESTS CART **/

                final Firebase bargaincartRef = new Firebase("https://signupemail-50215.firebaseio.com/")
                        .child("BargainCarts");
                AlertDialog.Builder builder = new AlertDialog.Builder(ProductDescription.this);
                builder.setTitle(R.string.BargainQuestion);
                // SET UP THE INPUT
                final EditText input = new EditText(ProductDescription.this);
                // Specifying the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setTextColor(getResources().getColor(R.color.colorPrimary));
                input.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                builder.setView(input);

                // SETUP THE BUTTONS
                builder.setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ProductClass bargainproduct = ProductDetails;
                        BargainProduct BargainProduct = new BargainProduct(bargainproduct.getName()
                                ,bargainproduct.getImage()
                                ,bargainproduct.getPrice()
                                ,Integer.parseInt(input.getText().toString())
                                ,bargainproduct.getDescription()
                                ,bargainproduct.getSeller()
                                ,0
                                ,bargainproduct.getId()
                                ,""
                                ,0
                                ,1
                                , System.currentTimeMillis());
                        /** PUSHING THE BARGAINPRODUCT TO THE BARGAINREQUESTS DATABASE **/
                        bargaincartRef.push().setValue(BargainProduct);
                    }
                });

                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();

            }
        });
    }
    /** WHEN CLICKED ON BACK BUTTON **/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // BACK CLICKED. GO TO HOME.
                Intent intent = new Intent(this, CustomerCart.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                //FINISH THE CURRENT ACTIVITY
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

