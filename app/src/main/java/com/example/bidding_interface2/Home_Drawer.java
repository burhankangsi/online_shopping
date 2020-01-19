package com.example.bidding_interface2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.bidding_interface2.Interface.MenuBargainItemListener;
import com.example.bidding_interface2.Interface.MenuCartItemListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class Home_Drawer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private ImageView imgHeaderBg, img_profile;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private TextView txtName;
    private Toolbar toolbar;
    private FloatingActionButton fab;

    // index to identify current nav menu item
    public static int navItemIndex = 0;

    // urls to load navigation header background image
    // and profile image
    private static final String urlNavHeaderBg = "https://api.androidhive.info/images/nav-menu-header-bg.jpg";
    private static final String urlProfileImg = "https://lh3.googleusercontent.com/eCtE_G34M9ygdkmOpYvCag1vBARCmZwnVS6rS5t4JLzJ6QgQSBquM0nuTsCpLhYbKljoyS-txg";

    // tags used to attach the fragments
    private static final String TAG_HOME = "home";
    private static final String TAG_MY_BIDS = "my_bids";
    private static final String TAG_UPLOAD_ITEMS = "upload_items";
    private static final String TAG_MY_PROFILE = "my_profile";
    private static final String TAG_TRANS_HISTORY = "transaction_history";
    private static final String TAG_SETTINGS = "settings";
    //private static final String TAG_SHARE = "share";
    //private static final String TAG_CONTACT_US = "customer_support";
    public static String CURRENT_TAG = TAG_HOME;
    private static final String TAG_WISHLIST = "wishlist";

    static TextView cartcounterTV;
    static TextView bargaincounterTV;
    public static int cartindex = 0;
    public static int bargaincartindex = 0;

    public final static String PROFILE_URL = "http://www.androiddeft.com/wp-content/uploads/2017/11/abhishek.jpg";
    public final static String BACKGROUND_URL = "http://www.androiddeft.com/wp-content/uploads/2017/11/nav_bg.jpg";
    public final static String SITE_URL = "http://androiddeft.com";
    public final static String SHARE_TITLE = "Android Development Tutorials";
    public final static String SHARE_MESSAGE = "Hey Friend, I have found an awesome website for learning Android Programming: http://androiddeft.com";
    public final static String SHARE_VIA = "Share Via";
    public final static String SHARE_TEXT_TYPE = "text/plain";


    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__drawer);
        toolbar = findViewById(R.id.toolbar_home_drawer);
        setSupportActionBar(toolbar);
      //  initCollapsingToolbar();
//        try {
//            Glide.with(this).load(R.drawable.album3).into((ImageView)
//                    findViewById(R.id.backdrop_toolbar));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
     //   mHandler = new Handler();
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Navigation view header
        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.txt_nav_header);
        // txtWebsite = (TextView) navHeader.findViewById(R.id.website);
//        imgHeaderBg = (ImageView) navHeader.findViewById(R.id.img_header_bg);
//        img_profile = (ImageView) navHeader.findViewById(R.id.img_profile);

        // Loading profile image
        img_profile = navHeader.findViewById(R.id.img_profile);
        Glide.with(this).load(PROFILE_URL)
                .apply(RequestOptions.circleCropTransform())
                .thumbnail(0.5f)
                .into(img_profile);
        //Loading backgrounf image
        imgHeaderBg = navHeader.findViewById(R.id.img_header_bg);
        Glide.with(this).load(BACKGROUND_URL)
                .thumbnail(0.5f)
                .into(imgHeaderBg);

        //Select Home by default
        navigationView.setCheckedItem(R.id.nav_home);
        Fragment fragment = new HomeFragment();
        displaySelectedFragment(fragment);


//        /** CODE FOR SIGN OUT IN NAVIGATION HEADER **/
//        Button SignOut = (Button) header.findViewById(R.id.signout);
//        SignOut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent signoutintent = new Intent(UserActivity.this, LoginActivity.class);
//                LoginActivity.setDefaults("UserID","",UserActivity.this);
//                UserActivity.this.startActivity(signoutintent);
//            }
//        });
//        navigationView.setNavigationItemSelectedListener(this);






    // load toolbar titles from string resources
     //   activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);

        // Load nav menu header data
       // loadNavHeader();

        //Initializing navigation menu
       // setUpNavigationView();

//        if (savedInstanceState == null) {
//            navItemIndex = 0;
//            CURRENT_TAG = TAG_HOME;
//            loadHomeFragment();
//        }


//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        mAppBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.nav_my_home, R.id.nav_my_bids, R.id.nav_upload_item_for_sell,
//                R.id.nav_my_profile, R.id.nav_trans_history, R.id.nav_settings, R.id.nav_wishlist,
//                R.id.nav_share_n_earn, R.id.nav_contact_us)
//                .setDrawerLayout(drawer)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);
    }


  //  private void initCollapsingToolbar() {
//        final CollapsingToolbarLayout collapsingToolbar =
//                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_app_bar);
//        collapsingToolbar.setTitle(" ");
  //      AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
   //     appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
//        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
//            boolean isShow = false;
//            int scrollRange = -1;
//
//            @Override
//            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//                if (scrollRange == -1) {
//                    scrollRange = appBarLayout.getTotalScrollRange();
//                }
//                if (scrollRange + verticalOffset == 0) {
//                    toolbar.setTitle(getString(R.string.app_name));
//                    isShow = true;
//                } else if (isShow) {
//                    toolbar.setTitle(" ");
//                    isShow = false;
//                }
//            }
//        });



//    public void loadNavHeader() {
//        txtName.setText("Burhan's App");

//        // Loading Header background image
//               Glide.with(this).load(urlNavHeaderBg)
//                .transition(new DrawableTransitionOptions().crossFade())
//                .into(imgHeaderBg);
//
//        // Loading profile image
//        Glide.with(this).load(urlProfileImg)
//                .transition(new DrawableTransitionOptions().crossFade())
//                .thumbnail(0.5f)
//                .into(img_profile);

        // showing dot next to notifications label
//        navigationView.getMenu().getItem(3).setActionView(R.layout.menu_dot_notify);
//    }
     // Returns respected fragment that user selected from navigation menu


//    private void loadHomeFragment() {
//        // selecting appropriate nav menu item
//        selectNavMenu();
//
//        // set toolbar title
//        setToolbarTitle();
//
//        // if user select the current navigation menu again, don't do anything
//        // just close the navigation drawer
//        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
//            drawer.closeDrawers();
//
//            // show or hide the fab button
//            toggleFab();
//            return;
//        }
//        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
//        Runnable mPendingRunnable = new Runnable() {
//            @Override
//            public void run() {
                // update the main content by replacing fragments
                //Fragment fragment = getHomeFragment();
//                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
//                        android.R.anim.fade_out);
//                fragmentTransaction.replace(R.id.content_frame, fragment, CURRENT_TAG);
//                fragmentTransaction.commit();
//                getHomeFragment();
//
//            }
//        };
//        // If mPendingRunnable is not null, then add to the message queue
//        if (mPendingRunnable != null) {
//            mHandler.post(mPendingRunnable);
//        }
//        // show or hide the fab button
//        toggleFab();
//
//        //Closing drawer on item click
//        drawer.closeDrawers();
//
//        // refresh toolbar menu
//        invalidateOptionsMenu();
//    }
//    public Fragment getHomeFragment ()
//    {
//        switch (navItemIndex)
//        {
//            case 0:
//                // home
//                HomeFragment homeFragment = new HomeFragment();
//                 return homeFragment;
////                displaySelectedFragment(homeFragment);
////                break;
//            case 1:
//                // photos
//                MyBidsFragment myBidsFragment = new MyBidsFragment();
//                  return myBidsFragment;
////                displaySelectedFragment(myBidsFragment);
////                break;
//            case 2:
//                // movies fragment
//                UploadFragment uploadFragment = new UploadFragment();
//                return uploadFragment;
////                displaySelectedFragment(uploadFragment);
////                break;
//            case 3:
//                // notifications fragment
//                MyProfileFragment myProfileFragment = new MyProfileFragment();
//                return myProfileFragment;
////                displaySelectedFragment(myProfileFragment);
////                break;
//
//            case 4:
//                // settings freturn getHomeFragment() ;ragment
//                TransactionFrag transFragment = new TransactionFrag();
//                return transFragment;
////                displaySelectedFragment(transFragment);
////                break;
//
//            case 5:
//                // settings freturn getHomeFragment() ;ragment
//                SettingsFragment settingsFragment = new SettingsFragment();
//                return settingsFragment;
////                displaySelectedFragment(settingsFragment);
////                break;
//
//            case 6:
//                // settings freturn getHomeFragment() ;ragment
//                WishlistFragment wishlistFragment = new WishlistFragment();
//                return wishlistFragment;
////                displaySelectedFragment(wishlistFragment);
////                break;
//
//            case 7:
//                // settings freturn getHomeFragment() ;ragment
//                ShareFragment shareFragment = new ShareFragment();
//                return shareFragment;
//                //Display Share Via dialogue
//                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
//                sharingIntent.setType(SHARE_TEXT_TYPE);
//                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, SHARE_TITLE);
//                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, SHARE_MESSAGE);
//                startActivity(Intent.createChooser(sharingIntent, SHARE_VIA));
//                break;
//
//
//            case 8:
//                // settings freturn getHomeFragment() ;ragment
//                ContactsFragment contactsFragment = new ContactsFragment();
//                return contactsFragment;
//                //Open URL on click of Visit Us
//               Intent urlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(SITE_URL));
//                startActivity(urlIntent);
//                break;
//
//
//            default:
//                return new HomeFragment();
//        }
        // Handle navigation view item clicks here.
//        if (navItemIndex == 0) {
//            Fragment fragment = new HomeFragment();
//            displaySelectedFragment(fragment);
//        }
//        else if (navItemIndex == 1) {
//            Fragment fragment = new MyBidsFragment();
//            displaySelectedFragment(fragment);
//
//        } else if (navItemIndex == 2) {
//            Fragment fragment = new UploadFragment();
//            displaySelectedFragment(fragment);
//
//        }
//        else if (navItemIndex == 3) {
//            Fragment fragment = new MyProfileFragment();
//            displaySelectedFragment(fragment);
//
//        }
//        else if (navItemIndex == 4) {
//            Fragment fragment = new TransactionFrag();
//            displaySelectedFragment(fragment);
//
//        }
//        else if (navItemIndex == 5) {
//            Fragment fragment = new SettingsFragment();
//            displaySelectedFragment(fragment);
//
//        }
//        else if (navItemIndex == 6) {
//            Fragment fragment = new WishlistFragment();
//            displaySelectedFragment(fragment);
//
//        } else if (navItemIndex == 7) {
//            //Display Share Via dialogue
//            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
//            sharingIntent.setType(SHARE_TEXT_TYPE);
//            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, SHARE_TITLE);
//            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, SHARE_MESSAGE);
//            startActivity(Intent.createChooser(sharingIntent, SHARE_VIA));
//
//        } else if (navItemIndex == 8) {
//            //Open URL on click of Visit Us
//            Intent urlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(SITE_URL));
//            startActivity(urlIntent);
//        }
//        return new HomeFragment();
//
//    }
//    private void setToolbarTitle() {
//        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
//    }
//
//    private void selectNavMenu() {
//        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
//    }
//
//    private void setUpNavigationView() {
//        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
//        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//
//            // This method will trigger on item Click of navigation menu
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                //Check to see which item was being clicked and perform appropriate action
//                switch (menuItem.getItemId()) {
//                    //Replacing the main content with ContentFragment Which is our Inbox View;
//                    case R.id.nav_home:
//                        navItemIndex = 0;
//                        CURRENT_TAG = TAG_HOME;
//                        break;
//                    case R.id.nav_bids:
//                        navItemIndex = 1;
//                        CURRENT_TAG = TAG_MY_BIDS;
//                        break;
//                    case R.id.nav_upload:
//                        navItemIndex = 2;
//                        CURRENT_TAG = TAG_UPLOAD_ITEMS;
//                        break;
//
//                    case R.id.nav_profile:
//                        navItemIndex = 3;
//                        CURRENT_TAG = TAG_TRANS_HISTORY;
//                        break;
//
//                    case R.id.nav_transaction_history:
//                        navItemIndex = 4;
//                        CURRENT_TAG = TAG_TRANS_HISTORY;
//                        break;
//
//                    case R.id.nav_settings:
//                        navItemIndex = 5;
//                        CURRENT_TAG = TAG_SETTINGS;
//                        break;
//
//                    case R.id.nav_wish:
//                        navItemIndex = 6;
//                        CURRENT_TAG = TAG_WISHLIST;
//                        break;
//
//                    case R.id.nav_share:
//                        // launch new intent instead of loading fragment
//                        startActivity(new Intent(Home_Drawer.this, ShareFragment.class));
//                        drawer.closeDrawers();
//                        return true;
//
//                    case R.id.nav_cust_support:
//                        // launch new intent instead of loading fragment
//                        startActivity(new Intent(Home_Drawer.this, ContactsFragment.class));
//                        drawer.closeDrawers();
//                        return true;
//                    default:
//                        navItemIndex = 0;
//                }
//                //Checking if the item is in checked state or not, if not make it in checked state
//                if (menuItem.isChecked()) {
//                    menuItem.setChecked(false);
//                } else {
//                    menuItem.setChecked(true);
//                }
//                menuItem.setChecked(true);
//
//                loadHomeFragment();
//
//                return true;
//            }
//        });
//        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer,
//                toolbar, R.string.openDrawer, R.string.closeDrawer) {
//
//            @Override
//            public void onDrawerClosed(View drawerView) {
//                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
//                super.onDrawerClosed(drawerView);
//            }
//
//            @Override
//            public void onDrawerOpened(View drawerView) {
//                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
//                super.onDrawerOpened(drawerView);
//            }
//        };
//
//        //Setting the actionbarToggle to drawer layout
//        drawer.setDrawerListener(actionBarDrawerToggle);
//
//        //calling sync state is necessary or else your hamburger icon wont show up
//        actionBarDrawerToggle.syncState();

      private void displaySelectedFragment(Fragment fragment)
        {
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        //fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
        //  android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.content_frame,fragment);
        //fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        }


    public void onBackPressed() {
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawers();
//            return;
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
//        if (shouldLoadHomeFragOnBackPress) {
//            // checking if user is on other navigation menu
//            // rather than home
//            if (navItemIndex != 0) {
//                navItemIndex = 0;
//                CURRENT_TAG = TAG_HOME;
//                loadHomeFragment();
//                return;
//            }
//        }
//        super.onBackPressed();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // Show menu only when home fragment is selected
//        if (navItemIndex == 0) {
//            getMenuInflater().inflate(R.menu.home__drawer, menu);
//        }
////        // when fragment is notifications, load the menu created for notifications
//        if (navItemIndex == 3) {
//            getMenuInflater().inflate(R.menu.notifications, menu);
//        }
//        return true;
        // Inflate the menu; this adds items to the action bar if it is present.

        // Handle navigation view item clicks here.

        if (navItemIndex == 1) {
            getMenuInflater().inflate(R.menu.user, menu);
            View menu_item_witty = menu.findItem(R.id.action_witty).getActionView();
            View menu_item_cart = menu.findItem(R.id.action_cart).getActionView();
            cartcounterTV = (TextView) menu_item_cart.findViewById(R.id.cartcounter);
            bargaincounterTV = (TextView) menu_item_witty.findViewById(R.id.wittycounter);

            /**UPDATING INITIAL THE CART COUNTS AND ONMENUITEMLISTENER FOR MENU_WITTY ITEM**/

            UpdateCartCount(cartindex);
            new MenuBargainItemListener(menu_item_witty, "Show message") {
                @Override
                public void onClick(View v) {
                    Intent Witty_Intent = new Intent(Home_Drawer.this, BargainCart.class);
                    startActivity(Witty_Intent);
                }
            };
            /**UPDATING INITIAL THE BARGAIN REQUEST COUNTS AND ONMENUITEMLISTENER FOR MENU_CART ITEM**/
            UpdateBargainCartCount(bargaincartindex);
            new MenuCartItemListener(menu_item_cart, "Show message") {
                @Override
                public void onClick(View v) {
                    Intent Cart_Intent = new Intent(Home_Drawer.this, CartActivity.class);
                    startActivity(Cart_Intent);
                }
            };

        }
        else {
            getMenuInflater().inflate(R.menu.home__drawer, menu);
        }
        return true;

    }
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            Toast.makeText(getApplicationContext(), "Logout user!", Toast.LENGTH_LONG).show();
            return true;
        }

        // user is in notifications fragment
        // and selected 'Mark all as Read'
        if (id == R.id.action_mark_all_read) {
            Toast.makeText(getApplicationContext(), "All notifications marked as read!", Toast.LENGTH_LONG).show();
        }
        // user is in notifications fragment
        // and selected 'Clear All'
        if (id == R.id.action_clear_notifications) {
            Toast.makeText(getApplicationContext(), "Clear all notifications!", Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }

    // show or hide the fab
//    private void toggleFab() {
//        if (navItemIndex == 0)
//            fab.show();
//        else
//            fab.hide();
//    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        // Handle navigation view item clicks here.
        int id = menuItem.getItemId();
        Fragment fragment = null;

        if (id == R.id.nav_my_home) {
            fragment = new HomeFragment();
            displaySelectedFragment(fragment);
        }
        else if (id == R.id.nav_my_bids) {
            fragment = new MyBidsFragment();
            displaySelectedFragment(fragment);

        } else if (id == R.id.nav_upload_item_for_sell) {
            fragment = new UploadFragment();
            displaySelectedFragment(fragment);

        }
        else if (id == R.id.nav_my_profile) {
            fragment = new MyProfileFragment();
            displaySelectedFragment(fragment);

        }
        else if (id == R.id.nav_trans_history) {
            fragment = new TransactionFrag();
            displaySelectedFragment(fragment);

        } else if (id == R.id.nav_settings) {
            fragment = new SettingsFragment();
            displaySelectedFragment(fragment);

        }
        else if (id == R.id.nav_wishlist) {
            fragment = new WishlistFragment();
            displaySelectedFragment(fragment);

        } else if (id == R.id.nav_share_n_earn) {
            //Display Share Via dialogue
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType(SHARE_TEXT_TYPE);
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, SHARE_TITLE);
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, SHARE_MESSAGE);
            startActivity(Intent.createChooser(sharingIntent, SHARE_VIA));

        } else if (id == R.id.nav_contact_us) {
            //Open URL on click of Visit Us
            Intent urlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(SITE_URL));
            startActivity(urlIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // Function to update cart counts in action bar in offline mode
    public  void UpdateCartCount(final int new_number){
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
    public  void UpdateBargainCartCount(final int new_number){
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


    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }



//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
//                || super.onSupportNavigateUp();
//    }


}
