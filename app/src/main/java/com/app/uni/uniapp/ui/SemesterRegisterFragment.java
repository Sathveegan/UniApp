package com.app.uni.uniapp.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
        EditText id = view.findViewById(R.id.semester_id);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentTransaction.replace(R.id.fragment_main, new CardFragment());
                fragmentTransaction.addToBackStack(null).commit();
            }
        });


        return view;
    }

}
