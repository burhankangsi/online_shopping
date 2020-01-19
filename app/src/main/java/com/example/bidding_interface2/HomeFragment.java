package com.example.bidding_interface2;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bidding_interface2.Adapter.CustomProductListAdapter;
import com.example.bidding_interface2.Adapter.Item_Adapter_Home_Frag;
import com.example.bidding_interface2.Model.BargainProduct;
import com.example.bidding_interface2.Model.HomeViewModel;
import com.example.bidding_interface2.Model.ProductClass;
import com.example.bidding_interface2.Model.Single_Bid_Item;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.MutableData;
import com.firebase.client.Transaction;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

      private static final Object TAG = " " ;
//    private HomeViewModel homeViewModel;
//    private TextView textView;
//    private Button btn_home_frag;
      private RecyclerView recyclerView;
      private Item_Adapter_Home_Frag adapter;
//    // private List<Single_Bid_Item> itemList;

    private ProgressDialog progress;
    public static int cartindex = 0;
    public static int bargaincartindex = 0;
    private boolean firstVisit;
    private List<Single_Bid_Item> itemList;
    static TextView cartcounterTV;
    static TextView bargaincounterTV;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

//        homeViewModel =
//                ViewModelProviders.of(this).get(HomeViewModel.class);
        Firebase.setAndroidContext(getActivity());
        View root = inflater.inflate(R.layout.fragment_home, container, false);


//        textView = root.findViewById(R.id.txt_frag_home);
//        btn_home_frag = root.findViewById(R.id.btn_frag_home);
//
//        btn_home_frag.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
////                Intent intent = new Intent(getActivity(), MyBidsFragment.class);
////                startActivity(intent);
//                    Fragment fragment = new MyBidsFragment();
//                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                    fragmentTransaction.replace(R.id.content_frame, fragment);
//                    fragmentTransaction.addToBackStack(null);
//                    fragmentTransaction.commit();
//            }
//        });
        recyclerView = (RecyclerView) root.findViewById(R.id.recycler_view_home_frag);

        itemList = new ArrayList<>();
        adapter = new Item_Adapter_Home_Frag(getActivity(), itemList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        //prepareAlbums();
        ((Home_Drawer) getActivity())
                .setActionBarTitle("Home");

//        try {
//            homeViewModel.getText().observe(getActivity(), new Observer<String>() {
//                @Override
//                public void onChanged(@Nullable String s) {
//                    //  textView.setText(s);
//                }
//            });
//        }
//        catch (NullPointerException e)
//        {
//            Log.e((String) TAG, e.toString());
//        }
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        /** INITIALISATION **/
        itemList = new ArrayList<>();

        // MAKING A REFERENCE TO PRODUCT DISPLAY FIREBASE
        Firebase ProductRef = new Firebase("https://signupemail-50215.firebaseio.com/productDisplay");

        // MAKING A REFERENCE TO CART URL IN FIREBASE FOR THE VALUES TO BE PUSHED

        final Firebase cartRef = new Firebase("https://signupemail-50215.firebaseio.com/")
                .child("Carts");

        // MAKING A REFERENCE TO BARGAIN REQUESTS CART

        final Firebase bargaincartRef = new Firebase("https://signupemail-50215.firebaseio.com/")
                .child("BargainCarts");


        /** SETTING THE CART COUNTS IF THERE ARE ITEMS IN THE CART AND BARGAIN REQUESTS REFERENCES ALREADY **/
        FirebaseCartCount(cartRef);
        FirebaseBargainCartCount(bargaincartRef);

        /** CREATING AND STARTING THE PROGRESS BAR **/
        progress = new ProgressDialog(getContext());
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
                    Single_Bid_Item post = postSnapshot.getValue(Single_Bid_Item.class);
                    System.out.println(post.getItem_name() + " - " + post.getDescription() + "-" + post.getId());
                    Single_Bid_Item currentProduct = new Single_Bid_Item(post.getItem_name()
                            , post.getImage()
                            , post.getItem_price()
                            , post.getDescription()
                            , post.getSeller()
                            , post.getId()
                            , post.getCategory());
                    itemList.add(currentProduct);
                }
                if (itemList != null) {
                    /** SETTING UP THE ADAPTER **/
                    try {
                        adapter = new Item_Adapter_Home_Frag(getActivity()
                                , itemList
                                , getActivity().getWindowManager().getDefaultDisplay().getWidth()
                                , /** ADD TO CART BUTTON ON CLICK LISTENER **/
                                new CustomProductListAdapter.ButtonClickListener() {
                                    @Override
                                    public void onButtonClick(int position, View v) {
                                        //Custom Toast Display Function defined below - Displays custom toast message
                                        CustomToastDisplay(getActivity(), getResources().getString(R.string.SuccessfulCart));
                                        /**FUNCTION DEFINED IN THE LAST- PUSHING THE PRODUCT TO CART DATABASE**/
                                        CartButtonClick(position, v, itemList, cartRef);
                                    }
                                }/** BARGAIN BUTTON ON CLICK LISTENER **/
                                , new CustomProductListAdapter.ButtonClickListener() {
                            @Override
                            public void onButtonClick(final int position, View v) {
                                /**FUNCTION DEFINED AT THE END OF THE CLASS, SHOW AN DIALOG ASKING FOR BARGAIN PRICE AND PUSH IT TO FIRBASE*/
                                BargainButtonAlertDialog(getActivity(), position, v, itemList, bargaincartRef);
                            }
                        });
                    }
                    catch (NullPointerException e)
                    {
                        Log.e((String) TAG, e.toString());
                    }
                    /** SET THE ADAPTER IN LISTVIEW AND DISMISS THE PROGRESS BAR**/
                    recyclerView.setAdapter(adapter);
                    progress.dismiss();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
        /**ONCLICKLISTENER ON THE CARDS-PASSING THE DETAILS VIA INTENT TO DESCRIPTION ACTIVITY**/

//        recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                ProductClass CurrentProduct = ProductClassList.get(position);
//                Intent DescriptionIntent = new Intent(getActivity(), ProductDescription.class);
//                DescriptionIntent.putExtra("ProductDetails", CurrentProduct);
//                getActivity().startActivity(DescriptionIntent);
//            }
//        });
    }

    // THIS FUNCTION WILL UPDATE THE TOTAL CART COUNT IN FIREBASE
    private void UpdateCartCounterInFirebase() {
        Firebase counterRef = new Firebase("https://signupemail-50215.firebaseio.com/userData/counts/cartCount");
        counterRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData currentData) {
                if (currentData.getValue() == null) {
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
    private void UpdateBargainCartCounterInFirebase() {
        Firebase counterRef = new Firebase("https://signupemail-50215.firebaseio.com/userData/counts/boxCount");
        counterRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData currentData) {
                if (currentData.getValue() == null) {
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
    public void onResume() {
        // CONFIRMING THAT CORRECT NUMBER OF PRODUCTS ARE DISPLAYED EVEN ON RESTART
        bargaincartindex = BargainCart.getBargainCartListSize();
        cartindex = CartActivity.getCartListSize();
        getActivity().invalidateOptionsMenu();
        firstVisit = false;
        super.onResume();
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
    public  void UpdateCartCount(final int new_number){
        cartindex = new_number;
        if (cartcounterTV == null) return;
        getActivity().runOnUiThread(new Runnable() {
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
    public  void UpdateBargainCartCount(final int new_number){
        bargaincartindex = new_number;
        if (bargaincounterTV == null) return;
        getActivity().runOnUiThread(new Runnable() {
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

    public void CartButtonClick(int position, View v, List<Single_Bid_Item> ProductList , Firebase reference){
        TextView addedInfo = (TextView) v.findViewById(R.id.addedInfo_prod_list);
        addedInfo.setVisibility(View.VISIBLE);
        addedInfo.setText("This product is in your cart");
        UpdateCartCount(++cartindex);
        Single_Bid_Item Product = itemList.get(position);
        // WE HAVE DEFINED NEW VARIABLE FOR CART PRODUCT AS WE ALSO HAVE TO ADD QUANTITY TO CART
        Single_Bid_Item CartProduct = new Single_Bid_Item(Product.getItem_name()
                ,Product.getImage()
                ,Product.getItem_price()
                ,Product.getDescription()
                ,Product.getSeller()
                ,Product.getId()
                ,1);
        /** PUSHING THE PRODUCT TO THE CART DATABASE **/
        reference.child(CartProduct.getId()).setValue(CartProduct);
        //THIS FUNCTION WILL UPDATE THE CART COUNTER NODE IN OUR DATABASE
        UpdateCartCounterInFirebase();
    }

    public void BargainButtonAlertDialog(Context mContext, final int position, View v, final List<Single_Bid_Item>
            ProductList , final Firebase ref){
        final TextView addedInfo = (TextView) v.findViewById(R.id.addedInfo_prod_list);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
                Single_Bid_Item bargainproduct = ProductList.get(position);
                // A CHECK IF THE BARGAIN PRICE REQUESTED IS NOT LESS THAN 40% OR TWICE OF PRODUCT PRICE
                if((Integer.parseInt(input.getText().toString()))< (0.4*bargainproduct.getItem_price()) ||
                        (Integer.parseInt(input.getText().toString()))> (2*bargainproduct.getItem_price())){
                    CustomToastDisplay(getActivity(),getResources().getString(R.string.bargainError));
                }
                else{
                    BargainProduct BargainProduct = new BargainProduct(bargainproduct.getItem_name()
                            ,bargainproduct.getImage()
                            ,bargainproduct.getItem_price()
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



//    private void prepareAlbums() {
//        int[] covers = new int[]{
//                R.drawable.album1,
//                R.drawable.album2,
//                R.drawable.album3,
//                R.drawable.album4,
//                R.drawable.album5,
//                R.drawable.album6,
//                R.drawable.album7,
//                R.drawable.album8,
//                R.drawable.album9,
//                R.drawable.album10,
//                R.drawable.album11};
//
//        Single_Bid_Item item = new Single_Bid_Item("True Romance", 430, covers[0], 4);
//        itemList.add(item);
//
//        item = new Single_Bid_Item("Xscpae", 3, covers[1], 2);
//        itemList.add(item);
//
//        item = new Single_Bid_Item("Maroon 5", 520, covers[2], 11);
//        itemList.add(item);
//
//        item = new Single_Bid_Item("Born to Die", 950, covers[3], 10);
//        itemList.add(item);
//
//        item = new Single_Bid_Item("Ecstatic", 145, covers[4], 2);
//        itemList.add(item);
//
//        item = new Single_Bid_Item("I Need a Doctor", 600, covers[5], 3);
//        itemList.add(item);
//
//        item = new Single_Bid_Item("Loud", 460, covers[6], 4);
//        itemList.add(item);
//
//        item = new Single_Bid_Item("Legend", 550, covers[7], 7);
//        itemList.add(item);
//
//        item = new Single_Bid_Item("Hello", 1100, covers[8], 4);
//        itemList.add(item);
//
//        item = new Single_Bid_Item("Greatest Hits", 180, covers[9], 1);
//        itemList.add(item);
//    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view);
            int column = position % spanCount;

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount;
                outRect.right = (column + 1) * spacing / spanCount;

                if (position < spanCount) {
                    outRect.top = spacing;
                }
                outRect.bottom = spacing;
            } else {
                outRect.left = column * spacing / spanCount;
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing;
                }
            }
        }

    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    // Latest App Items display

}