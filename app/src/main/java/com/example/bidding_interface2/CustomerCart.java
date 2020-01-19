package com.example.bidding_interface2;

        import androidx.appcompat.app.AlertDialog;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.appcompat.widget.Toolbar;
        import androidx.recyclerview.widget.RecyclerView;

        import android.app.ProgressDialog;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.content.res.Resources;
        import android.graphics.PorterDuff;
        import android.os.Bundle;
        import android.text.InputType;
        import android.view.Menu;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.EditText;
        import android.widget.ListView;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.example.bidding_interface2.Adapter.CustomProductListAdapter;
        import com.example.bidding_interface2.Interface.MenuBargainItemListener;
        import com.example.bidding_interface2.Interface.MenuCartItemListener;
        import com.example.bidding_interface2.Model.BargainProduct;
        import com.example.bidding_interface2.Model.ProductClass;
        import com.firebase.client.DataSnapshot;
        import com.firebase.client.Firebase;
        import com.firebase.client.FirebaseError;
        import com.firebase.client.MutableData;
        import com.firebase.client.Transaction;
        import com.firebase.client.ValueEventListener;

        import java.util.ArrayList;
        import java.util.Random;

public class CustomerCart extends AppCompatActivity {
    private ProgressDialog progress;
    private int cartindex = 0;
    private int bargaincartindex = 0;
    ListView ProductList;
    private static ArrayList<ProductClass> ProductClassList;
    CustomProductListAdapter adapter;
    TextView cartcounterTV;
    TextView bargaincounterTV;
    /** MAKING A REFERENCE TO PRODUCT DISPLAY FIREBASE **/
    Firebase ProductRef = new Firebase("https://signupemail-50215.firebaseio.com/productDisplay");
    /** MAKING A REFERENCE TO CART URL IN FIREBASE FOR THE VALUES TO BE PUSHED **/
    Firebase cartRef = new Firebase("https://signupemail-50215.firebaseio.com/")
            .child("Carts");
    /** MAKING A REFERENCE TO BARGAIN REQUESTS CART **/
    Firebase bargaincartRef = new Firebase("https://signupemail-50215.firebaseio.com/")
            .child("BargainCarts");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_cart);

        Firebase.setAndroidContext(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ProductList=(ListView)findViewById(R.id.ProductList_cust_cart);
        /**CHECK IF IN ANY CASE USERNAME STRING IS NULL OR EMPTY GO BACK TO LOGIN ACTIVITY**/
//        if(LoginActivity.getDefaults("UserID",this)==null || LoginActivity.getDefaults("UserID",this).equals(""))
//        {
//            Intent intent = new Intent(this,LoginActivity.class);
//            this.startActivity(intent);
//        }
        /**RANDOM TIPS GENERATION**/
        TextView UserTips = (TextView) findViewById(R.id.UserTips);
        Resources res = getResources();

        String[] userTips = res.getStringArray(R.array.UserTips);
        Random random = new Random();
        String tip = userTips[random.nextInt(userTips.length)];
        UserTips.setText(tip);
    }

    @Override
    protected void onStart() {
        super.onStart();
        /** INITIALISATION **/
        ProductClassList = new ArrayList<>();

        /** SETTING THE CART COUNTS IF THERE ARE ITEMS IN THE CART AND BARGAIN REQUESTS REFERENCES ALREADY **/
        FirebaseCartCount(cartRef);
        FirebaseBargainCartCount(bargaincartRef);

        /** CREATING AND STARTING THE PROGRESS BAR **/
        progress = new ProgressDialog(this);
        progress.setMessage(getResources().getString(R.string.Loading1));
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setCancelable(false);
        progress.show();

        /** ADDING THE VALUE EVENT LISTENER TO PRODUCTREF FOR DISPLAYING THE LIST OF PRODUCTS **/
        ProductRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    ProductClass post = postSnapshot.getValue(ProductClass.class);
                    System.out.println(post.getName() + " - " + post.getDescription() + "-" + post.getId());
                    ProductClass currentProduct = new ProductClass(post.getName()
                            , post.getImage()
                            , post.getPrice()
                            , post.getDescription()
                            , post.getSeller()
                            , post.getId()
                            , post.getCategory());
                    ProductClassList.add(currentProduct);
                }
                if (ProductClassList != null) {
                    /** SETTING UP THE ADAPTER **/
                    adapter = new CustomProductListAdapter(CustomerCart.this
                            , ProductClassList
                            , getWindowManager().getDefaultDisplay().getWidth()
                            , /** ADD TO CART BUTTON ON CLICK LISTENER **/
                            new CustomProductListAdapter.ButtonClickListener() {
                                @Override
                                public void onButtonClick(int position, View v) {
                                    //Custom Toast Display Function defined below - Displays custom toast message
                                    CustomToastDisplay(CustomerCart.this, getResources().getString(R.string.SuccessfulCart));
                                    /**FUNCTION DEFINED IN THE LAST- PUSHING THE PRODUCT TO CART DATABASE**/
                                    CartButtonClick(position, v, ProductClassList, cartRef);
                                }
                            }/** BARGAIN BUTTON ON CLICK LISTENER **/
                            , new CustomProductListAdapter.ButtonClickListener() {
                        @Override
                        public void onButtonClick(final int position, View v) {
                            /**FUNCTION DEFINED AT THE END OF THE CLASS, SHOW AN DIALOG ASKING FOR BARGAIN PRICE AND PUSH IT TO FIRBASE*/
                            BargainButtonAlertDialog(CustomerCart.this, position, v, ProductClassList, bargaincartRef);
                        }
                    });
                    /** SET THE ADAPTER IN LISTVIEW AND DISMISS THE PROGRESS BAR**/
                    ProductList.setAdapter(adapter);
                    progress.dismiss();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

        /**ONCLICKLISTENER ON THE CARDS-PASSING THE DETAILS VIA INTENT TO DESCRIPTION ACTIVITY**/

        ProductList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProductClass CurrentProduct = ProductClassList.get(position);
                Intent DescriptionIntent = new Intent(CustomerCart.this, ProductDescription.class);
                DescriptionIntent.putExtra("ProductDetails", CurrentProduct);
                CustomerCart.this.startActivity(DescriptionIntent);
            }
        });
    }
    // THIS FUNCTION WILL UPDATE THE TOTAL CART COUNT IN FIREBASE
    private void UpdateCartCounterInFirebase()
    {
        Firebase counterRef = new Firebase("https://signupemail-50215.firebaseio.com/userData/counts/cartCount");
        counterRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData currentData) {
                if(currentData.getValue() == null) {
                    currentData.setValue(1);
                } else {
                    currentData.setValue((Long) currentData.getValue() + 1);
                }
                return Transaction.success(currentData); //we can also abort by calling Transaction.abort()
            }
            @Override
            public void onComplete(FirebaseError firebaseError, boolean committed, DataSnapshot currentData) {
                //This method will be called once with the results of the transaction.
            }
        });
    }

    // THIS FUNCTION WILL UPDATE THE BARGAIN REQUESTS COUNTER IN FIREBASE
    private void UpdateBargainCartCounterInFirebase()
    {
        Firebase counterRef = new Firebase("https://signupemail-50215.firebaseio.com/userData/counts/boxCount");
        counterRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData currentData) {
                if(currentData.getValue() == null) {
                    currentData.setValue(1);
                } else {
                    currentData.setValue((Long) currentData.getValue() + 1);
                }
                return Transaction.success(currentData); //we can also abort by calling Transaction.abort()
            }
            @Override
            public void onComplete(FirebaseError firebaseError, boolean committed, DataSnapshot currentData) {
                //This method will be called once with the results of the transaction.
            }
        });
    }

    @Override
    protected void onRestart() {
        // CONFIRMING THAT CORRECT NUMBER OF PRODUCTS ARE DISPLAYED EVEN ON RESTART
        bargaincartindex = BargainCart.getBargainCartListSize();
        cartindex = CartActivity.getCartListSize();
        invalidateOptionsMenu();
        super.onRestart();
    }

    // Handling the action bar item clicks here

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // INFLATE THE MENU; THIS WILL ADD ITEMS TO ACTION BAR IF THEY ARE PRESENT.
                getMenuInflater().inflate(R.menu.user, menu);
        View menu_item_witty = menu.findItem(R.id.action_witty).getActionView();
        View menu_item_cart = menu.findItem(R.id.action_cart).getActionView();
        cartcounterTV = (TextView) menu_item_cart.findViewById(R.id.cartcounter);
      //  bargaincounterTV = (TextView) menu_item_witty.findViewById(R.id.wi);

        /**UPDATING INITIAL THE CART COUNTS AND ONMENUITEMLISTENER FOR MENU_WITTY ITEM**/

        UpdateCartCount(cartindex);
        new MenuBargainItemListener(menu_item_witty, "Show message") {
            @Override
            public void onClick(View v) {
                Intent Witty_Intent = new Intent(CustomerCart.this,BargainCart.class);
                CustomerCart.this.startActivity(Witty_Intent);
            }
        };
        /**UPDATING INITIAL THE BARGAIN REQUEST COUNTS AND ONMENUITEMLISTENER FOR MENU_CART ITEM**/
        UpdateBargainCartCount(bargaincartindex);
        new MenuCartItemListener(menu_item_cart, "Show message") {
            @Override
            public void onClick(View v) {
                Intent Cart_Intent = new Intent(CustomerCart.this,CartActivity.class);
                CustomerCart.this.startActivity(Cart_Intent);
            }
        };
        return true;
    }

    /**FUNCTION TO UPDATE CART COUNTS IN ACTION BAR USING FIREBASE**/
    public void FirebaseCartCount(Firebase reference){
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UpdateCartCount((int) dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }

    // FUNCTION TO UPDATE BARGAIN REQUESTS COUNTS IN ACTION BAR USING FIREBASE
    public void FirebaseBargainCartCount(Firebase bargaincartRef)
    {
        bargaincartRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UpdateBargainCartCount((int) dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
    // Function to update cart counts in action bar in offline mode
    public void UpdateCartCount(final int new_number){
        cartindex = new_number;
        if (cartcounterTV == null) return;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (new_number == 0)
                    cartcounterTV.setVisibility(View.INVISIBLE);
                else {
                    cartcounterTV.setVisibility(View.VISIBLE);
                    cartcounterTV.setText(Integer.toString(new_number));
                }
            }
        });
    }
    // FUNCTION TO UPDATE BARGAIN REQUESTS COUNTS IN ACTION BAR IN OFFLINE MODE
    public void UpdateBargainCartCount(final int new_number){
        bargaincartindex = new_number;
        if (bargaincounterTV == null) return;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (new_number == 0)
                    bargaincounterTV.setVisibility(View.INVISIBLE);
                else {
                    bargaincounterTV.setVisibility(View.VISIBLE);
                    bargaincounterTV.setText(Integer.toString(new_number));
                }
            }
        });
    }
    // Function to show custom toast message
    public static void CustomToastDisplay(Context mContext, String message){
        Toast toast = Toast.makeText(mContext,"", Toast.LENGTH_SHORT);
        View view = toast.getView();
        view.setBackgroundResource(R.drawable.rounded_square);
        TextView text = (TextView) view.findViewById(android.R.id.message);
        text.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
        text.setText(message);
        toast.show();
    }

    public void CartButtonClick(int position, View v, ArrayList<ProductClass> ProductList , Firebase reference){
        TextView addedInfo = (TextView) v.findViewById(R.id.addedInfo_prod_list);
        addedInfo.setVisibility(View.VISIBLE);
        addedInfo.setText("This product is in your cart");
        UpdateCartCount(++cartindex);
        ProductClass Product = ProductList.get(position);
        // WE HAVE DEFINED NEW VARIABLE FOR CART PRODUCT AS WE ALSO HAVE TO ADD QUANTITY TO CART
        ProductClass CartProduct = new ProductClass(Product.getName()
                ,Product.getImage()
                ,Product.getPrice()
                ,Product.getDescription()
                ,Product.getSeller()
                ,Product.getId()
                ,1);
        /** PUSHING THE PRODUCT TO THE CART DATABASE **/
        reference.child(CartProduct.getId()).setValue(CartProduct);
        //THIS FUNCTION WILL UPDATE THE CART COUNTER NODE IN OUR DATABASE
        UpdateCartCounterInFirebase();
    }
    public void BargainButtonAlertDialog(Context mContext, final int position, View v, final ArrayList<ProductClass>
            ProductList , final Firebase ref){
        final TextView addedInfo = (TextView) v.findViewById(R.id.addedInfo_prod_list);
        AlertDialog.Builder builder = new AlertDialog.Builder(CustomerCart.this);
        builder.setTitle(R.string.BargainQuestion);
        // SET UP THE INPUT
        final EditText input = new EditText(mContext);
        // Specifying the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setTextColor(getResources().getColor(R.color.colorPrimary));
        input.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        builder.setView(input);

        // SETUP THE BUTTONS
        builder.setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ProductClass bargainproduct = ProductList.get(position);
                // A CHECK IF THE BARGAIN PRICE REQUESTED IS NOT LESS THAN 40% OR TWICE OF PRODUCT PRICE
                if((Integer.parseInt(input.getText().toString()))< (0.4*bargainproduct.getPrice()) ||
                        (Integer.parseInt(input.getText().toString()))> (2*bargainproduct.getPrice())){
                    CustomToastDisplay(CustomerCart.this,getResources().getString(R.string.bargainError));
                }
                else{
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
                            ,System.currentTimeMillis());
                    /** PUSHING THE BARGAINPRODUCT TO THE BARGAINREQUESTS DATABASE **/
                    ref.push().setValue(BargainProduct);
                    //THIS FUNCTION WILL UPDATE THE BARGAIN REQUEST COUNTER NODE IN OUR DATABASE
                    UpdateBargainCartCounterInFirebase();
                    bargaincartindex = bargaincartindex + 1;
                    UpdateBargainCartCount(bargaincartindex);
                    //MAKING ADDED INFO TEXT VIEW VISIBLE
                    addedInfo.setVisibility(View.VISIBLE);
                    addedInfo.setText("Bargain Requested. Click 'W' icon on the top for more details");
                }
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
//    public void setActionBarTitle(String title) {
//        getSupportActionBar().setTitle(title);
//
//    }

}
