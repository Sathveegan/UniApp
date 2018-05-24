package com.app.uni.uniapp.ui;


import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.app.uni.uniapp.R;
import com.app.uni.uniapp.core.FirebaseClient;
import com.app.uni.uniapp.data.MyLocation;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.location.places.ui.SupportPlaceAutocompleteFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateLocationFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerDragListener, GoogleMap.OnMyLocationButtonClickListener, OnMapClickListener,
        GoogleMap.OnMyLocationClickListener {

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

        SupportPlaceAutocompleteFragment autocompleteFragment = (SupportPlaceAutocompleteFragment) getChildFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i("Map", "Place: " + place);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(place.getLatLng()));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(13), 2000, null);

                if(marker == null) {
                    marker = mMap.addMarker(new MarkerOptions()
                            .position(place.getLatLng())
                            .title("My location")
                            .draggable(true));

                } else{
                    marker.setPosition(place.getLatLng());
                }

                locLng = marker.getPosition().longitude;
                locLat = marker.getPosition().latitude;

            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("Map", "An error occurred: " + status);
            }
        });

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final DatePicker date = view.findViewById(R.id.create_myloaction_date);
        final TimePicker time = view.findViewById(R.id.create_myloaction_time);
        final EditText contact = view.findViewById(R.id.create_myloaction_contact);
        Button submit = view.findViewById(R.id.create_myloaction_submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                try {
                        MyLocation myLocation = new MyLocation(user.getEmail(), new Date(date.getYear()-1900, date.getMonth(), date.getDayOfMonth()), time.getCurrentHour(), time.getCurrentMinute(), locLat,locLng, contact.getText().toString());

                        firebaseClient.createMyLocation(myLocation);

                        Toast.makeText(getActivity(),"My Location Added!", Toast.LENGTH_SHORT).show();

                        contact.setText("");

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
        mMap.getUiSettings().setZoomControlsEnabled(true);
        LatLng defaultLac = new LatLng(6.930613, 79.860325);

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {

        }

        mMap.moveCamera(CameraUpdateFactory.newLatLng(defaultLac));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(13), 2000, null);

        mMap.setOnMarkerDragListener(this);
        mMap.setOnMapClickListener(this);
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);

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

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(getActivity(), "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onMapClick(LatLng latLng) {
        if(marker == null) {
            marker = mMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title("Destination")
                    .draggable(true));

        } else{
            marker.setPosition(latLng);
        }

        locLng = marker.getPosition().longitude;
        locLat = marker.getPosition().latitude;
    }
}
