package com.app.uni.uniapp.ui;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.app.uni.uniapp.R;
import com.app.uni.uniapp.core.FirebaseClient;
import com.app.uni.uniapp.data.MyLocation;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * A simple {@link Fragment} subclass.
 */
public class LocationFragment extends Fragment {

    final static private String DB_URL = "https://androidapp-4830e-202705.firebaseio.com/";
    private FirebaseClient firebaseClient;
    private SwipeMenuListView myLocationlistView;
    private ListView friendsLocationView;

    public LocationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_location, container, false);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        myLocationlistView = view.findViewById(R.id.list_mylocation);
        friendsLocationView = view.findViewById(R.id.list_friendslocation);

        firebaseClient = new FirebaseClient(getActivity(), DB_URL, myLocationlistView);
        firebaseClient.loadMyLocationData("MyLocation", user.getEmail());

        firebaseClient = new FirebaseClient(getActivity(), DB_URL, friendsLocationView);
        firebaseClient.loadMyLocationData("FriendsLocation", user.getEmail());

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        FloatingActionButton fab = view.findViewById(R.id.add_mylocation);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentTransaction.replace(R.id.fragment_main, new CreateLocationFragment());
                fragmentTransaction.addToBackStack(null).commit();
            }
        });

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(getActivity().getApplicationContext());
                deleteItem.setBackground(R.color.red);
                deleteItem.setWidth(250);
                deleteItem.setIcon(R.drawable.ic_delete_white_24dp);
                menu.addMenuItem(deleteItem);
            }
        };

        myLocationlistView.setMenuCreator(creator);

        myLocationlistView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        firebaseClient.deleteMyLocation(position);
                        Toast.makeText(getActivity(), "My Location Deleted!", Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });

        friendsLocationView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MyLocation myLocation = (MyLocation) adapterView.getItemAtPosition(i);

                final Bundle bundle=new Bundle();
                FriendsLocFragment friendsLocFragment = new FriendsLocFragment();

                bundle.clear();
                bundle.putSerializable("location",(MyLocation) myLocation);
                friendsLocFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.fragment_main, friendsLocFragment);
                fragmentTransaction.addToBackStack(null).commit();
            }
        });

        return view;
    }

}
