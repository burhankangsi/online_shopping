package com.example.bidding_interface2;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.bidding_interface2.Model.ProductClass;
import com.example.bidding_interface2.Model.SlideshowViewModel;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import info.hoang8f.widget.FButton;

import static android.app.Activity.RESULT_OK;

public class UploadFragment extends Fragment {
//    TextView productid, title, type, description, price, quantity;
//    private DatabaseReference myref;
//    private FirebaseAuth mAuth;
//    private Boolean islistempty;
////    private FirebaseAuth.AuthStateListener mAuthListener;
//      FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    String Productname;
    String Productdescription;
    Integer Productprice;
    String ProductimageURL;
    String Productcategory;
    String Productseller;
    public UploadFragment() {
    }
    public static UploadFragment newInstance() {
        UploadFragment fragment = new UploadFragment();
        return fragment;
    }
    // Folder path for Firebase Storage.
    String Storage_Path = "All_Image_Uploads/";

    // Root Database Name for Firebase Database.
    public static final String Database_Path = "All_Image_Uploads_Database";
    // Creating URI.
    Uri FilePathUri;
    ImageView SelectImage;
    Button btn_choose_img;

    // Creating StorageReference and DatabaseReference object.
    DatabaseReference databaseReference;
    StorageReference storageReference;
    // Image request code for onActivityResult() .
    int Image_Request_Code = 7;
    ProgressDialog progressDialog ;


//    private SlideshowViewModel slideshowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        slideshowViewModel =
//                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_upload, container, false);
        // Assign FirebaseStorage instance to storageReference.
        storageReference = FirebaseStorage.getInstance().getReference();

        // Assign FirebaseDatabase instance with root database name.
        databaseReference = FirebaseDatabase.getInstance().getReference(Database_Path);


//        FirebaseUser mFirebaseUser = mAuth.getCurrentUser();
        //Get Firebase auth instance
//        mAuth = FirebaseAuth.getInstance();
//        firebaseDatabase = FirebaseDatabase.getInstance();
//
//        if(mAuth.getCurrentUser() != null) {
//            myref = FirebaseDatabase.getInstance().getReference("sellers/" +
//                    FirebaseAuth.getInstance().getCurrentUser().getUid());
//
//        }
//
//        productid = (TextView) root.findViewById(R.id.addProductId);
//        title = (TextView) root.findViewById(R.id.addProductTitle);
//        type = (TextView) root.findViewById(R.id.addProductType);
//        description = (TextView)root.findViewById(R.id.addProductDescription);
//        price = (TextView) root.findViewById(R.id.addProductPrice);
//        quantity = (TextView)root.findViewById(R.id.addProductQuantity);
//
//      root.findViewById(R.id.addProductSubmit).setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                if (productid.getText().toString().matches("") ||
//                        title.getText().toString().matches("") ||
//                        type.getText().toString().matches("") ||
//                        description.getText().toString().matches("") ||
//                        price.getText().toString().matches("") ||
//                        quantity.getText().toString().matches("")) {
//
//                    Toast.makeText(getActivity(), "Fill everything", Toast.LENGTH_SHORT).show();
//
//                } else {
//                    myref = FirebaseDatabase.getInstance().getReference();
//                    myref.addListenerForSingleValueEvent(new ValueEventListener() {
//                        ArrayList<ShoppingItem> productList = new ArrayList<>();
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//                                islistempty = Boolean.valueOf(dataSnapshot.child("isProdsEmpty").getValue().toString());
//                            if (islistempty) {
//                                myref.child("isProdsEmpty").setValue(Boolean.FALSE.toString());
//                            } else {
//                                for (DataSnapshot snap : dataSnapshot.child("products").getChildren()) {
//                                    int itemPrice = -1;
//                                    try {
//                                        itemPrice = Integer.valueOf(NumberFormat.getCurrencyInstance()
//                                                .parse(String.valueOf(snap.child("price").getValue()))
//                                                .toString());
//                                    } catch (ParseException e) {
//                                        e.printStackTrace();
//                                    }
//                                    String productID = snap.child("productID").getValue().toString();
//
//                                    productList.add(new ShoppingItem(
//                                            productID,
//                                            snap.child("title").getValue().toString(),
//                                            snap.child("type").getValue().toString(),
//                                            snap.child("description").getValue().toString(),
//                                            itemPrice,
//                                            Integer.valueOf(snap.child("quantity").getValue().toString())
//                                    ));
//                                }
//                            }
//
//
//
//                            //                          same product id can be added. note to future devs. remove that feature.
////                            and change the way ids are generated
//                            productList.add(new ShoppingItem(
//                                    productid.getText().toString(),
//                                    title.getText().toString(),
//                                    type.getText().toString(),
//                                    description.getText().toString(),
//                                    Integer.valueOf(price.getText().toString()),
//                                    Integer.valueOf(quantity.getText().toString()))
//                            );
//                            Map<String, Object> cartItemsMap = new HashMap<>();
//                            cartItemsMap.put("products", productList);
//                            myref.updateChildren(cartItemsMap);
//                            getActivity().finish();
//
//                            }
//                            @Override
//                            public void onCancelled (DatabaseError databaseError){
//                                Log.w("", "Failed to read value.", databaseError.toException());
//                            }
//
//                    });
//
//                }
//            }
//        });
//        @Override
//        public void onChanged(@Nullable String s) {
//            //tv_upload_frag.setText(s);
//        }
//    });
//        slideshowViewModel.getText().observe(this, new Observer<String>() {
//        return root; }
//}
        ((Home_Drawer) getActivity())
                .setActionBarTitle("Add New Product");

        Firebase.setAndroidContext(getActivity());
        //FINDING THE VIEWS
        final AutoCompleteTextView ProductName = (AutoCompleteTextView) root.findViewById(R.id.AddProductName);
        final AutoCompleteTextView ProductDescription = (AutoCompleteTextView) root.findViewById(R.id.AddProductDescription);
        // final AutoCompleteTextView ProductImageURL = (AutoCompleteTextView) root.findViewById(R.id.AddProductURL);
        final AutoCompleteTextView ProductPrice = (AutoCompleteTextView) root.findViewById(R.id.AddProductPrice);
        final AutoCompleteTextView ProductSeller = (AutoCompleteTextView) root.findViewById(R.id.AddProductSeller);
        final AutoCompleteTextView ProductCategory = (AutoCompleteTextView) root.findViewById(R.id.AddProductCategory);
         SelectImage = (ImageView)root.findViewById(R.id.iv_add_product);
         btn_choose_img = (Button) root.findViewById(R.id.btn_iv_choose);
        FButton AddProductButton = (FButton) root.findViewById(R.id.AddProductButton_frag_upload);
        FButton ResetButton = (FButton) root.findViewById(R.id.ResetButton_frag_upload);
        // Assigning Id to ProgressDialog.
        progressDialog = new ProgressDialog(getActivity());


        //CODE FOR ADD PRODUCT BUTTON
        Firebase ProductDisplayRef = new Firebase("https://signupemail-50215.firebaseio.com/productDisplay/");
        final Firebase ProductDisplayRefPush = ProductDisplayRef.push();
        // Adding click listener to Choose image button.
        btn_choose_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Creating intent.
                Intent intent = new Intent();
                // Setting intent type as image to select image from phone storage.
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Please Select Image"), Image_Request_Code);

            }
        });


        AddProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Productname = ProductName.getText().toString();
//                Productdescription = ProductDescription.getText().toString();
//                Productprice = Integer.parseInt(ProductPrice.getText().toString());
//                Productseller = ProductSeller.getText().toString();
//                Productcategory = ProductCategory.getText().toString();
//                ProductimageURL = ProductImageURL.getText().toString();

                // Checking whether FilePathUri Is empty or not.
                if (FilePathUri != null) {

                    // Setting progressDialog Title.
                    progressDialog.setTitle("Image is Uploading...");

                    // Showing progressDialog.
                    progressDialog.show();

                    // Creating second StorageReference.
                    StorageReference storageReference2nd = storageReference.child(Storage_Path + System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));

                    // Adding addOnSuccessListener to second StorageReference.
                    storageReference2nd.putFile(FilePathUri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                    // Getting image name from EditText and store into string variable.
                                 //   String TempImageName = ProductImageURL.getText().toString().trim();
                                    Productname = ProductName.getText().toString();
                                    Productdescription = ProductDescription.getText().toString();
                                    Productprice = Integer.parseInt(ProductPrice.getText().toString());
                                    Productseller = ProductSeller.getText().toString();
                                    Productcategory = ProductCategory.getText().toString();
                                   // ProductimageURL = ProductImageURL.getText().toString();


                                    // Hiding the progressDialog after done uploading.
                                    progressDialog.dismiss();

                                    // Showing toast message after done uploading.
                                    Toast.makeText(getActivity(), "Image Uploaded Successfully ", Toast.LENGTH_LONG).show();

                                    @SuppressWarnings("VisibleForTests")
                                   // ProductClass Product = new ProductClass(TempImageName, taskSnapshot.getDownloadUrl().toString());

                                    ProductClass Product = new ProductClass(Productname, taskSnapshot.getUploadSessionUri().toString(),
                                            Productprice, Productdescription, Productseller, ProductDisplayRefPush.getKey(), Productcategory);
                                    ProductDisplayRefPush.setValue(Product);


                                    // Getting image upload ID.
                                    //String ImageUploadId = databaseReference.push().getKey();

                                    // Adding image upload id s child element into databaseReference.
                                   // databaseReference.child(ImageUploadId).setValue(Product);
                                }
                            })
                            // If something goes wrong .
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {

                                    // Hiding the progressDialog.
                                    progressDialog.dismiss();

                                    // Showing exception erro message.
                                    Toast.makeText(getActivity(), exception.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            })
                            // On progress change upload time.
                            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                                    // Setting progressDialog Title.
                                    progressDialog.setTitle("Image is Uploading...");

                                }
                            });
                }
                else {

                    Toast.makeText(getActivity(), "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();

                }


//                    ProductClass Product = new ProductClass(Productname, ProductimageURL, Productprice, Productdescription, Productseller, ProductDisplayRefPush.getKey(), Productcategory);
//                    ProductDisplayRefPush.setValue(Product);

                //Custom Toast Message
//                Toast toast = Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT);
//                View view = toast.getView();
//                view.setBackgroundResource(R.drawable.ic_done_black_24dp);
//                TextView text = (TextView) view.findViewById(android.R.id.message);
//                text.setText(" PRODUCT ADDED SUCCESSFULLY! ");
//                text.setTextColor(getResources().getColor(R.color.colorWhite));
//                toast.show();
            }
        });
        //CODE FOR RESET BUTTON
        ResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductName.setText("");
                ProductDescription.setText("");
                ProductPrice.setText("");
                ProductSeller.setText("");
                ProductCategory.setText("");
               // ProductImageURL.setText("");
            }
        });

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {

            FilePathUri = data.getData();

            try {

                // Getting selected image into Bitmap.
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), FilePathUri);

                // Setting up bitmap selected image into ImageView.
                SelectImage.setImageBitmap(bitmap);

                // After selecting image change choose button above text.
                btn_choose_img.setText("Image Selected");

            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    // Creating Method to get the selected image file Extension from File Path URI.
    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getActivity().getContentResolver();

        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        // Returning the file Extension.
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;

    }

}

