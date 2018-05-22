package com.app.uni.uniapp.core;

import android.content.Context;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.app.uni.uniapp.data.JoinList;
import com.app.uni.uniapp.data.MyLocation;
import com.app.uni.uniapp.data.MyLocationAdapter;
import com.app.uni.uniapp.data.Post;
import com.app.uni.uniapp.data.PostAdapter;
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

    private ArrayList<Post> postsList = new ArrayList<>();
    final ArrayList<String> keyList = new ArrayList<>();
    final ArrayList<Post> searchList = new ArrayList<>();
    private PostAdapter postAdapter;


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

    public void loadPostData(){
        firebase.child("posts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                getPostUpdates(dataSnapshot);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.v("Firebase", firebaseError.toString());
            }
        });
    }

    public void getPostUpdates(DataSnapshot dataSnapshot){
        postsList.clear();
        keyList.clear();

        for(DataSnapshot ds : dataSnapshot.getChildren()){
            Post post = ds.getValue(Post.class);
            keyList.add(ds.getKey());
            postsList.add(post);
        }

        if(postsList.size() > 0){
            postAdapter = new PostAdapter(context, postsList, DB_URL);
            listView.setAdapter(postAdapter);
        }else{
            Toast.makeText(context, "No posts...", Toast.LENGTH_SHORT);
        }
    }

    public void searchPosts(String query){
        searchList.clear();
        for (Post p: postsList) {
            if(p.getTitle().toLowerCase().contains(query.trim().toLowerCase())){
                searchList.add(p);
            }
        }
        postAdapter = new PostAdapter(context, searchList, DB_URL);
        listView.setAdapter(postAdapter);

    }

    public void joinPost(JoinList joinList){
        firebase.child("joinLists").push().setValue(joinList);
    }


}
