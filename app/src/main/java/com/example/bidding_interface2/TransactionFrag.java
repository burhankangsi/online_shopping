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


public class TransactionFrag extends Fragment {
    private TextView textView;
    private Button btn_trans_frag;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_trans, container, false);


        textView = root.findViewById(R.id.txt_frag_trans_history);
    btn_trans_frag = root.findViewById(R.id.btn_frag_trans_history);

        btn_trans_frag.setOnClickListener(new View.OnClickListener() {
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
                .setActionBarTitle("Transaction History");

        return root;
    }

}
