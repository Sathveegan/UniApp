package com.app.uni.uniapp.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.uni.uniapp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentFragment extends Fragment {


    public PaymentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_payment, container, false);

        Button semester_register = view.findViewById(R.id.reg_semester);
        Button repeat_register = view.findViewById(R.id.reg_repeat);
        Button donation = view.findViewById(R.id.donation);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        semester_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentTransaction.replace(R.id.fragment_main, new SemesterRegisterFragment());
                fragmentTransaction.addToBackStack(null).commit();
            }
        });

        repeat_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentTransaction.replace(R.id.fragment_main, new RepeatRegisterFragment());
                fragmentTransaction.addToBackStack(null).commit();
            }
        });

        donation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentTransaction.replace(R.id.fragment_main, new DonationFragment());
                fragmentTransaction.addToBackStack(null).commit();
            }
        });

        return view;
    }

}
