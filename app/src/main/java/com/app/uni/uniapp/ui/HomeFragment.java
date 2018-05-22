package com.app.uni.uniapp.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.app.uni.uniapp.R;
import com.app.uni.uniapp.core.FirebaseClient;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    final static private String DB_URL = "https://androidapp-4830e-202705.firebaseio.com/";
    private FirebaseClient firebaseClient;
    private ListView post_list_view;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        post_list_view = view.findViewById(R.id.post_view);

        firebaseClient = new FirebaseClient(getActivity(), DB_URL, post_list_view);
        firebaseClient.loadPostData();

        SearchView searchPosts = (SearchView) view.findViewById(R.id.post_category);
        searchPosts.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String text) {
                firebaseClient.searchPosts(text);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                firebaseClient.searchPosts(text);
                return true;
            }
        });

        return view;
    }

}
