package com.app.uni.uniapp.ui;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.app.uni.uniapp.R;
import com.app.uni.uniapp.data.MyLocation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;


/**
 * A simple {@link Fragment} subclass.
 */
public class FriendsLocFragment extends Fragment implements OnMapReadyCallback{

    private MyLocation myLocation;
    private GoogleMap mMap;
    private Marker marker;

    public FriendsLocFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_friends_loc, container, false);

        myLocation = (MyLocation) getArguments().getSerializable("location");

        TextView date = view.findViewById(R.id.floaction_date);
        TextView time = view.findViewById(R.id.floaction_time);
        TextView contact = view.findViewById(R.id.floaction_contact);

        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");

        date.setText(format.format(myLocation.getDate()));
        time.setText(myLocation.getTimeHours() + " : "+myLocation.getTimeMinute());
        contact.setText(myLocation.getContact());

        Button call = view.findViewById(R.id.floaction_call);
        Button message = view.findViewById(R.id.floaction_message);

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + myLocation.getContact()));
                startActivity(intent);
            }
        });

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.setData(Uri.parse("smsto:" + myLocation.getContact()));
                startActivity(sendIntent);
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.floaction_map);
        mapFragment.getMapAsync(this);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        LatLng defaultLac = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
        marker = mMap.addMarker(new MarkerOptions()
                .position(defaultLac)
                .title("Destination")
                .draggable(true));

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {

        }

        mMap.moveCamera(CameraUpdateFactory.newLatLng(defaultLac));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(13), 2000, null);
    }
}
