package com.app.uni.uniapp.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.app.uni.uniapp.R;
import com.app.uni.uniapp.core.Callback;
import com.app.uni.uniapp.core.Executor;
import com.app.uni.uniapp.core.FirebaseClient;
import com.app.uni.uniapp.data.Donation;
import com.app.uni.uniapp.data.RepeatRegister;
import com.app.uni.uniapp.data.SemiRegister;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class CardFragment extends Fragment implements Callback{

    final static private String DB_URL = "https://androidapp-4830e-202705.firebaseio.com/";
    private FirebaseClient firebaseClient;
    private SemiRegister semiRegister ;
    private RepeatRegister repeatRegister;
    private Donation donation;

    private Spinner card_type;
    private EditText card_number;
    private EditText expiry_month;
    private EditText expiry_year;
    private EditText card_cvv;

    public CardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_card, container, false);

        semiRegister = (SemiRegister) getArguments().getSerializable("semi_register");
        repeatRegister = (RepeatRegister) getArguments().getSerializable("repeat_register");
        donation = (Donation) getArguments().getSerializable("donation");

        firebaseClient = new FirebaseClient(DB_URL);
        card_type = (Spinner) view.findViewById(R.id.card_type);
        card_number = (EditText) view.findViewById(R.id.card_number);
        expiry_month = (EditText) view.findViewById(R.id.card_expiry_month);
        expiry_year = (EditText) view.findViewById(R.id.card_expiry_year);
        card_cvv = (EditText) view.findViewById(R.id.card_ccv);

        Button submit = view.findViewById(R.id.card_submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(card_number.getText())) {
                    card_number.setError("Card number required!");
                    return;
                }

                if (TextUtils.isEmpty(card_cvv.getText())) {
                    card_cvv.setError("cvv required!");
                    return;
                }

                if (TextUtils.isEmpty(expiry_month.getText())) {
                    card_cvv.setError("Expiry month required!");
                    return;
                }

                if (TextUtils.isEmpty(expiry_year.getText())) {
                    expiry_year.setError("Expiry year required!");
                    return;
                }

                check();

            }
        });

        return view;
    }

    @Override
    public void onCallbackCompleted(String data) {
        Log.v("Callback","Collback Completed");
        Log.v("Callback",data);

        try {
            JSONObject jsonObj = new JSONObject(data);

            String cardnumber = jsonObj.getString("cardNo");
            String cardName = jsonObj.getString("cardName");
            String cardType = jsonObj.getString("cardType");
            String bankName = jsonObj.getString("bankName");

            if(!cardnumber.equals("null")){
                double amount = 0;
                if(semiRegister != null){
                    amount = semiRegister.getAmount();
                } else if(repeatRegister != null){
                    amount = repeatRegister.getAmount();
                } else if(donation != null){
                    amount = donation.getAmount();
                }

                if(semiRegister != null){
                    firebaseClient.semesterRegister(semiRegister);
                    Toast.makeText(getActivity(), "Semester Registration Successfully", Toast.LENGTH_SHORT).show();
                }

                if(repeatRegister != null){
                    firebaseClient.repeatRegister(repeatRegister);
                    Toast.makeText(getActivity(), "Repeat Registration Successfully", Toast.LENGTH_SHORT).show();
                }

                if(donation != null){
                    firebaseClient.donation(donation);
                    Toast.makeText(getActivity(), "Donation Successfully", Toast.LENGTH_SHORT).show();
                }

                card_number.setText("");
                card_cvv.setText("");
                expiry_month.setText("");
                expiry_year.setText("");

                for(int i=0; i<getActivity().getSupportFragmentManager().getBackStackEntryCount(); i++){
                    getActivity().getSupportFragmentManager().popBackStack();
                }

                final Bundle bundle = new Bundle();
                PaymentGatewayFragment paymentGatewayFragment = new PaymentGatewayFragment();

                bundle.clear();
                bundle.putString("cardnumber", cardnumber);
                bundle.putString("cardName", cardName);
                bundle.putString("cardType", cardType);
                bundle.putString("bankName", bankName);
                bundle.putDouble("amount", amount);

                paymentGatewayFragment.setArguments(bundle);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_main, paymentGatewayFragment);
                fragmentTransaction.commit();
            } else {
                Toast.makeText(getActivity(), "Invalid Card Details", Toast.LENGTH_SHORT).show();
                card_number.setText("");
                card_cvv.setText("");
                expiry_month.setText("");
                expiry_year.setText("");
            }

        } catch (JSONException e) {
            Toast.makeText(getActivity(), "Registration Failed!", Toast.LENGTH_SHORT).show();
        } catch(Exception e){
            Toast.makeText(getActivity(), "Registration Failed!", Toast.LENGTH_SHORT).show();
        }
    }

    public void check(){


        SimpleDateFormat format = new SimpleDateFormat("yy/MM");
        String icard_number = card_number.getText().toString().trim();
        String iexpiry_date = expiry_year.getText().toString().trim() +"/"+expiry_month.getText().toString().trim();
        String icard_cvv = card_cvv.getText().toString().trim();

        Executor executor = new Executor(getResources().getString(R.string.payment_endpoint),icard_number, icard_cvv, iexpiry_date, this);
        executor.execute();
    }
}
