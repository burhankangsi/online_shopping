package com.example.bidding_interface2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class WishlistFragment extends Fragment {
    private TextView textView;
    private Button btn_wishlist;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_wishlist, container, false);
        textView = root.findViewById(R.id.txt_frag_wishlist);
        btn_wishlist = root.findViewById(R.id.btn_frag_wishlist);

        btn_wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), SettingsFragment.class);
//                startActivity(intent);
                Fragment fragment = new SettingsFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        ((Home_Drawer) getActivity())
                .setActionBarTitle("Wishlist");

        return root;

    }

}

