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
import android.widget.Toast;

import com.app.uni.uniapp.R;
import com.app.uni.uniapp.data.SemiRegister;
import com.app.uni.uniapp.ui.CardFragment;

import java.util.Date;


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
        final DatePicker date = view.findViewById(R.id.semester_date);
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

                try {

                    if (id.getText().toString().trim().equals("")) {
                        id.setError("IT number required!");
                        return;
                    }
                    if (name.getText().toString().trim().equals("")) {
                        name.setError("Name required!");
                        return;
                    }
                    if (contact.getText().toString().trim().equals("")) {
                        contact.setError("Contact No required!");
                        return;
                    }
                    if (email.getText().toString().trim().equals("")) {
                        email.setError("Email required!");
                        return;
                    }
                    if (year.getText().toString().trim().equals("")) {
                        year.setError("Year required!");
                        return;
                    }

                    if (semi.getText().toString().trim().equals("")) {
                        semi.setError("Semi required!");
                        return;
                    }

                    if (amount.getText().toString().trim().equals("")) {
                        amount.setError("Amount required!");
                        return;
                    }


                    SemiRegister semiRegister = new SemiRegister(id.getText().toString().trim(), name.getText().toString().trim(), new Date(date.getYear() - 1900, date.getMonth(), date.getDayOfMonth()), contact.getText().toString().trim(), email.getText().toString().trim(), Integer.parseInt(year.getText().toString().trim()), Integer.parseInt(semi.getText().toString().trim()), Double.parseDouble(amount.getText().toString().trim()));
                    final Bundle bundle = new Bundle();
                    CardFragment cardFragment = new CardFragment();

                    bundle.clear();
                    bundle.putSerializable("semi_register", (SemiRegister) semiRegister);
                    cardFragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.fragment_main, cardFragment);
                    fragmentTransaction.addToBackStack(null).commit();

                } catch (Exception e){
                    Toast.makeText(getActivity(), "Try Again!", Toast.LENGTH_SHORT).show();
                    id.setText("");
                    name.setText("");
                    contact.setText("");
                    email.setText("");
                    year.setText("");
                    semi.setText("");
                    amount.setText("");
                }
            }
        });


        return view;
    }

}
