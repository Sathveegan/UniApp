package com.app.uni.uniapp.ui;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.app.uni.uniapp.R;
import com.app.uni.uniapp.core.FirebaseClient;
import com.app.uni.uniapp.data.MyLocation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateLocationFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerDragListener {

    final static private String DB_URL = "https://androidapp-4830e-202705.firebaseio.com/";
    private FirebaseClient firebaseClient;

    private GoogleMap mMap;
    private Marker marker;
    private double locLng;
    private double locLat;

    public CreateLocationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_location, container, false);

        firebaseClient = new FirebaseClient(DB_URL);

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.create_myloaction_map);
        mapFragment.getMapAsync(this);

        final DatePicker date = view.findViewById(R.id.create_myloaction_date);
        final TimePicker time = view.findViewById(R.id.create_myloaction_time);
        Button submit = view.findViewById(R.id.create_myloaction_submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                try {
                        MyLocation myLocation = new MyLocation(new Date(date.getYear()-1900, date.getMonth(), date.getDayOfMonth()), 2, 40, locLat,locLng);

                        firebaseClient.createMyLocation(myLocation);

                        Toast.makeText(getActivity(),"My Location Added!", Toast.LENGTH_SHORT).show();

                    }catch(Exception e){
                    Toast.makeText(getActivity(),"Try Again!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng defaultLac = new LatLng(6.930613, 79.860325);
        marker = mMap.addMarker(new MarkerOptions()
                .position(defaultLac)
                .title("My location")
                .draggable(true));

        locLng = marker.getPosition().longitude;
        locLat = marker.getPosition().latitude;

        mMap.moveCamera(CameraUpdateFactory.newLatLng(defaultLac));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(13), 2000, null);

        mMap.setOnMarkerDragListener(this);

    }


    @Override
    public void onMarkerDragStart(Marker marker) {
    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        locLng = marker.getPosition().longitude;
        locLat = marker.getPosition().latitude;
    }
}
