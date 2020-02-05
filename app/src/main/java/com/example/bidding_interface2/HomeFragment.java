package com.example.bidding_interface2;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.bidding_interface2.Adapter.Item_Adapter_Home_Frag;
import com.example.bidding_interface2.Model.ImageUploadInfo;
//import com.firebase.client.ValueEventListener;
import com.firebase.client.Firebase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

@SuppressWarnings("unchecked")
public class HomeFragment extends Fragment {

    //https://firebasestorage.googleapis.com/v0/b/signupemail-50215.appspot.com/o/All_Image_Uploads%2F1579288631452.jpg?alt=media&token=42450e77-8ba3-49b4-a5bd-3f29ed28dca9
    //
    //https://firebasestorage.googleapis.com/v0/b/signupemail-50215.appspot.com/o?All_Image_Uploads%2F1579288631452.jpg&uploadType=resumable&upload_id=AEnB2Uq6RES8FGz5rPmYVU4kqeot6OaMil5PrYE5GxKYEJcEnREIc5VBYbE6x5jTQqwHjiiuW203SQeE_a7til5BCF05z06y9Q&upload_protocol=resumable&token=42450e77-8ba3-49b4-a5bd-3f29ed28dca9

    // Creating DatabaseReference.
    DatabaseReference databaseReference;

    // Creating RecyclerView.
    RecyclerView recyclerView;

    // Creating RecyclerView.Adapter.
    RecyclerView.Adapter adapter;

    // Creating Progress dialog
    ProgressDialog progressDialog;

    Context mContext;

    // Creating List of ImageUploadInfo class.
    List<ImageUploadInfo> list = new List<ImageUploadInfo>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(Object o) {
            return false;
        }

        @androidx.annotation.NonNull
        @Override
        public Iterator<ImageUploadInfo> iterator() {
            return null;
        }

        @androidx.annotation.NonNull
        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @androidx.annotation.NonNull
        @Override
        public <T> T[] toArray(@androidx.annotation.NonNull T[] ts) {
            return null;
        }

        @Override
        public boolean add(ImageUploadInfo imageUploadInfo) {
            return false;
        }

        @Override
        public boolean remove(Object o) {
            return false;
        }

        @Override
        public boolean containsAll(@androidx.annotation.NonNull Collection<?> collection) {
            return false;
        }

        @Override
        public boolean addAll(@androidx.annotation.NonNull Collection<? extends ImageUploadInfo> collection) {
            return false;
        }

        @Override
        public boolean addAll(int i, @androidx.annotation.NonNull Collection<? extends ImageUploadInfo> collection) {
            return false;
        }

        @Override
        public boolean removeAll(@androidx.annotation.NonNull Collection<?> collection) {
            return false;
        }

        @Override
        public boolean retainAll(@androidx.annotation.NonNull Collection<?> collection) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public ImageUploadInfo get(int i) {
            return null;
        }

        @Override
        public ImageUploadInfo set(int i, ImageUploadInfo imageUploadInfo) {
            return null;
        }

        @Override
        public void add(int i, ImageUploadInfo imageUploadInfo) {

        }

        @Override
        public ImageUploadInfo remove(int i) {
            return null;
        }

        @Override
        public int indexOf(Object o) {
            return 0;
        }

        @Override
        public int lastIndexOf(Object o) {
            return 0;
        }

        @androidx.annotation.NonNull
        @Override
        public ListIterator<ImageUploadInfo> listIterator() {
            return null;
        }

        @androidx.annotation.NonNull
        @Override
        public ListIterator<ImageUploadInfo> listIterator(int i) {
            return null;
        }

        @androidx.annotation.NonNull
        @Override
        public List<ImageUploadInfo> subList(int i, int i1) {
            return null;
        }
    };


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        Firebase.setAndroidContext(getActivity());

        recyclerView = (RecyclerView) root.findViewById(R.id.recycler_view_home_frag);

//        Log.i("inside", "rv initiated" +mLayoutManager.toString());
        // Assign activity this to progress dialog.
        progressDialog = new ProgressDialog(getActivity());

        // Setting up message in Progress dialog.
        progressDialog.setMessage("Loading Images From Firebase.");
        // Showing progress dialog.
        progressDialog.show();
        // The path is already defined in MainActivity.
        databaseReference = FirebaseDatabase.getInstance().getReference().child("productDisplay");
        Log.i("inside", databaseReference.toString());

        return root;
    }


    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<ImageUploadInfo> item_info =
                new FirebaseRecyclerOptions.Builder<ImageUploadInfo>()
                        .setQuery(databaseReference, ImageUploadInfo.class)
                        .build();
        final FirebaseRecyclerAdapter<ImageUploadInfo, ViewHolder> info_adapter =
                new FirebaseRecyclerAdapter<ImageUploadInfo, ViewHolder>(item_info) {
                    @Override
                    protected void onBindViewHolder(final ViewHolder holder, int position, final ImageUploadInfo model) {
                        mContext = getContext();
                        final DatabaseReference dref = getRef(position).getRef();
                        Log.i("dref", dref.toString());
                        // Adding Add Value Event Listener to databaseReference.
                        dref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(final DataSnapshot snapshot) {

                                holder.item_name_single_card.setText(snapshot.child("name").getValue().toString());
                                holder.price_single_card.setText(snapshot.child("price").getValue().toString());
                                String image_url = snapshot.child("image").getValue().toString();

//                                In case of malformed url, uncomment the following
//                                image_url = image_url.replaceAll("\\?name=", "/");
//                                image_url = image_url.split("&uploadType")[0];
//                                image_url = image_url.concat("?alt=media");

                                Picasso.get().load(image_url).into(holder.thumbnail);

                                Log.i("img_url", image_url);
//                                Picasso.get().load(snapshot.child("image").getValue().toString()).into(holder.thumbnail, new Callback() {
//                                    @Override
//                                    public void onSuccess() {
//
//                                    }
//
//                                    @Override
//                                    public void onError(Exception e) {
//                                        Picasso.get().load(String.valueOf(snapshot.child("image"))).into(holder.thumbnail);
//
//                                    }
//                                });

                                //Loading Item cover using Glide Library
                             //   Glide.with(mContext).load(model.getImage()).into(holder.thumbnail);

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
                                        ((Home_Drawer) mContext).getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,
                                                fragment, "Customer Cart").addToBackStack(null).commit();
                                    }
                                });
                                holder.bargain.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(mContext, BargainCart.class);
                                        mContext.startActivity(intent);
                                    }
                                });

                                // Hiding the progress dialog.
                                progressDialog.dismiss();

                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                                // Hiding the progress dialog.
                                Log.w("failure", "Failed to read value.", databaseError.toException());
                                progressDialog.dismiss();

                            }
                        });

                    }

                    @Override
                    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
                        ViewHolder viewHolder = new ViewHolder(view);

                        return viewHolder;
                    }
                };
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(info_adapter);
        info_adapter.startListening();

    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView item_name_single_card, price_single_card, bargain;
        public ImageView thumbnail, overflow;
        public Button btn_add_to_cart_card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_name_single_card = (TextView) itemView.findViewById(R.id.item_name_card);
            // quantity_single_card = (TextView) itemView.findViewById(R.id.quantity_card);
            price_single_card = (TextView) itemView.findViewById(R.id.price_card);
            thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            overflow = (ImageView) itemView.findViewById(R.id.overflow);
            btn_add_to_cart_card = (Button) itemView.findViewById(R.id.btn_item_card);
            bargain = (TextView) itemView.findViewById(R.id.bargain_card);
        }
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
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }

    }
    public int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_item_card_home, popup.getMenu());
        popup.setOnMenuItemClickListener(new HomeFragment.MyMenuItemClickListener());
        popup.show();

    }

    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_add_to_wishlist:
                    Toast.makeText(mContext, "Add to Wishlist", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_add_to_cart:
                    Toast.makeText(mContext, "Add to CartActivity", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }

}



