package com.app.uni.uniapp.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.app.uni.uniapp.R;
import com.app.uni.uniapp.data.Donation;

import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class DonationFragment extends Fragment {


    public DonationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_donation, container, false);

        final EditText name = (EditText) view.findViewById(R.id.donation_name);
        final DatePicker date = (DatePicker) view.findViewById(R.id.donation_date);
        final EditText amount = (EditText) view.findViewById(R.id.donation_amount);

        Button submit = view.findViewById(R.id.donation_submit);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {

                    if (TextUtils.isEmpty(name.getText())) {
                        name.setError("Name required!");
                        return;
                    }

                    if (TextUtils.isEmpty(amount.getText())) {
                        amount.setError("Amount required!");
                        return;
                    }

                    Donation donation = new Donation(name.getText().toString().trim(), new Date(date.getYear() - 1900, date.getMonth(), date.getDayOfMonth()), Double.parseDouble(amount.getText().toString().trim()));
                    final Bundle bundle = new Bundle();
                    CardFragment cardFragment = new CardFragment();

                    bundle.clear();
                    bundle.putSerializable("donation", (Donation) donation);
                    cardFragment.setArguments(bundle);

                    fragmentTransaction.replace(R.id.fragment_main, cardFragment);
                    fragmentTransaction.addToBackStack(null).commit();

                } catch(Exception e){
                    Toast.makeText(getActivity(), "Try Again!", Toast.LENGTH_SHORT).show();
                    name.setText("");
                    amount.setText("");
                }

            }
        });

        return view;
    }

}
