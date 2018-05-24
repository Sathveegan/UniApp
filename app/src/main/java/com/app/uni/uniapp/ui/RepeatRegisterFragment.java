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
import com.app.uni.uniapp.data.RepeatRegister;

import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class RepeatRegisterFragment extends Fragment {


    public RepeatRegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_repeat_register, container, false);

        final EditText itnumber = (EditText) view.findViewById(R.id.repeat_id);
        final EditText name = (EditText) view.findViewById(R.id.repeat_name);
        final DatePicker date = (DatePicker) view.findViewById(R.id.repeat_date);
        final EditText subject = (EditText) view.findViewById(R.id.repeat_subject);
        final EditText amount = (EditText) view.findViewById(R.id.repeat_amount);

        Button submit = (Button) view.findViewById(R.id.repeat_submit);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (TextUtils.isEmpty(itnumber.getText())) {
                        itnumber.setError("IT number required!");
                        return;
                    }
                    if (TextUtils.isEmpty(name.getText())) {
                        name.setError("Name required!");
                        return;
                    }
                    if (TextUtils.isEmpty(subject.getText())) {
                        subject.setError("Subject required!");
                        return;
                    }
                    if (TextUtils.isEmpty(amount.getText())) {
                        amount.setError("Amount required!");
                        return;
                    }

                    RepeatRegister repeatRegister = new RepeatRegister(itnumber.getText().toString().trim(), name.getText().toString().trim(), new Date(date.getYear() - 1900, date.getMonth(), date.getDayOfMonth()), subject.getText().toString().trim(), Double.parseDouble(amount.getText().toString().trim()));
                    final Bundle bundle = new Bundle();
                    CardFragment cardFragment = new CardFragment();

                    bundle.clear();
                    bundle.putSerializable("repeat_register", (RepeatRegister) repeatRegister);
                    cardFragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.fragment_main, cardFragment);
                    fragmentTransaction.addToBackStack(null).commit();

                } catch(Exception e){
                    Toast.makeText(getActivity(), "Try Again!", Toast.LENGTH_SHORT).show();
                    itnumber.setText("");
                    name.setText("");
                    subject.setText("");
                    amount.setText("");
                }
            }
        });
        return view;
    }

}
