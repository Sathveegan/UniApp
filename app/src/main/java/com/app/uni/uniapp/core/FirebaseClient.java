package com.app.uni.uniapp.core;

import android.content.Context;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.app.uni.uniapp.data.MyLocation;
import com.app.uni.uniapp.data.MyLocationAdapter;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Sathveegan on 02-May-18.
 */

public class FirebaseClient {

    private Context context;
    private String DB_URL;
    private Firebase firebase ;
    private ListView listView;
    private ArrayList<MyLocation> myLocations = new ArrayList<>();
    final ArrayList<String> myLocationsKeyList = new ArrayList<>();
    private MyLocationAdapter myLocationAdapter;

    public FirebaseClient(String DB_URL){
        firebase = new Firebase(DB_URL);
    }

    public FirebaseClient(Context context, String DB_URL, ListView listView){
        this.context = context;
        this.DB_URL = DB_URL;
        this.listView = listView;

        Firebase.setAndroidContext(context);
        firebase = new Firebase(DB_URL);
    }

    public void loadMyLocationData(){
        firebase.child("myLocation").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                getMyLocationUpdates(dataSnapshot);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.v("Firebase", firebaseError.toString());
            }
        });
    }

    public void getMyLocationUpdates(DataSnapshot dataSnapshot){
        myLocations.clear();
        myLocationsKeyList.clear();

        for(DataSnapshot ds : dataSnapshot.getChildren()){
            MyLocation myLocation = ds.getValue(MyLocation.class);
            myLocations.add(myLocation);
            myLocationsKeyList.add(ds.getKey());
        }

        if(myLocations.size() > 0){
            myLocationAdapter = new MyLocationAdapter(context, myLocations);
            listView.setAdapter(myLocationAdapter);
        }else{
            Toast.makeText(context, "No My Locations...", Toast.LENGTH_SHORT);
        }
    }

    public void createMyLocation(MyLocation myLocation){
        firebase.child("myLocation").push().setValue(myLocation);
    }

    public void deleteMyLocation(int position){
        myLocations.remove(position);
        myLocationAdapter.notifyDataSetChanged();
        firebase.child("myLocation").child(myLocationsKeyList.get(position)).removeValue();
        myLocationsKeyList.remove(position);
    }


}
