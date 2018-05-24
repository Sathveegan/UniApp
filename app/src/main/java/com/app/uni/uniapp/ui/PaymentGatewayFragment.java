package com.app.uni.uniapp.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.app.uni.uniapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentGatewayFragment extends Fragment {


    public PaymentGatewayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_payment_gateway, container, false);

        String cardnumber = getArguments().getString("cardnumber");
        String cardName = getArguments().getString("cardName");
        String cardType = getArguments().getString("cardType");
        String bankName = getArguments().getString("bankName");
        double amount = getArguments().getDouble("amount");

        TextView icardnumber = (TextView) view.findViewById(R.id.cardNumber);
        TextView icardName = (TextView) view.findViewById(R.id.cardName);
        TextView icardType = (TextView) view.findViewById(R.id.cardType);
        TextView ibankName = (TextView) view.findViewById(R.id.bankName);
        TextView iamount = (TextView) view.findViewById(R.id.amount);

        icardnumber.setText(cardnumber);
        icardName.setText(cardName);
        icardType.setText(cardType);
        ibankName.setText(bankName);
        iamount.setText(amount+"");

        Button button = (Button) view.findViewById(R.id.back);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_main, new PaymentFragment());
                fragmentTransaction.commit();
            }
        });

        return view;
    }

}
