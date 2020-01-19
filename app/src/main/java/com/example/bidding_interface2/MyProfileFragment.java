package com.example.bidding_interface2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.bidding_interface2.Home_Drawer;
import com.example.bidding_interface2.Model.SendViewModel;
import com.example.bidding_interface2.R;
import com.example.bidding_interface2.TransactionFrag;

public class MyProfileFragment extends Fragment {

    private SendViewModel sendViewModel;
    private TextView textView;
    private Button btn_profile_frag;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sendViewModel =
                ViewModelProviders.of(this).get(SendViewModel.class);
        View root = inflater.inflate(R.layout.fragment_my_profile, container, false);
        textView = root.findViewById(R.id.txt_frag_profile);
        btn_profile_frag = root.findViewById(R.id.btn_frag_profile);

        btn_profile_frag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), TransactionFrag.class);
//                startActivity(intent);
                Intent intent = new Intent(getActivity(), BargainCart.class);
                getActivity().startActivity(intent);

//                Fragment fragment = new TransactionFrag();
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.content_frame, fragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
            }
        });
        ((Home_Drawer) getActivity())
                .setActionBarTitle("My Profile");


        sendViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}