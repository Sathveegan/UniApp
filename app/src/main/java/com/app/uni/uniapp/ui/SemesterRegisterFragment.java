package com.app.uni.uniapp.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.app.uni.uniapp.R;
import com.app.uni.uniapp.ui.CardFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class SemesterRegisterFragment extends Fragment {


    public SemesterRegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_semester_register, container, false);

        Button submit = view.findViewById(R.id.semester_pay);
        final EditText id = view.findViewById(R.id.semester_id);
        final EditText name = view.findViewById(R.id.semester_name);
        final EditText contact = view.findViewById(R.id.semester_contact);
        final EditText email = view.findViewById(R.id.semester_mail);
        final EditText year = view.findViewById(R.id.semester_year);
        final EditText semi = view.findViewById(R.id.semester_semi);
        final EditText amount = view.findViewById(R.id.semester_amount);


        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (id.getText().toString().trim().equals("")) {
                    id.setError("ID required!");
                }
                if (name.getText().toString().trim().equals("")) {
                    name.setError("Name required!");
                }
                if (contact.getText().toString().trim().equals("")) {
                    contact.setError("Contact No required!");
                }
                if (email.getText().toString().trim().equals("")) {
                    email.setError("Email required!");
                }
                if (year.getText().toString().trim().equals("")) {
                    year.setError("Year required!");
                }

                if (semi.getText().toString().trim().equals("")) {
                    semi.setError("Semi required!");
                }

                if (amount.getText().toString().trim().equals("")) {
                    amount.setError("Amount required!");
                }

                else{
                    fragmentTransaction.replace(R.id.fragment_main, new CardFragment());
                    fragmentTransaction.addToBackStack(null).commit();
                }

            }
        });


        return view;
    }

}
