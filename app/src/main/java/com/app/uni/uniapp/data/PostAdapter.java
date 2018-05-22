package com.app.uni.uniapp.data;

/**
 * Created by Sathveegan on 23-May-18.
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.app.uni.uniapp.R;
import com.app.uni.uniapp.core.FirebaseClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Sathveegan on 29-Apr-18.
 */

public class PostAdapter extends ArrayAdapter {

    private Context mContext;
    private ArrayList<Post> postsList ;
    private String DB_URL;

    public PostAdapter(Context context, ArrayList<Post> posts, String DB_URL) {
        super(context, R.layout.layout_post_view, posts);
        this.mContext = context;
        this.postsList = posts;
        this.DB_URL = DB_URL;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        final Post post = postsList.get(position);

        if (view == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            view = layoutInflater.inflate(R.layout.layout_post_view, null);
        }

        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");

        final Button post_join = (Button) view.findViewById(R.id.post_join);

        TextView title = (TextView) view.findViewById(R.id.post_view_title);
        TextView date = (TextView) view.findViewById(R.id.post_view_date);
        TextView message = (TextView) view.findViewById(R.id.post_view_message);

        title.setText(": "+post.getTitle());
        date.setText(": "+format.format(post.getDate()));
        message.setText(": "+post.getMessage());

        post_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = currentUser.getEmail().split("@")[0].toString();
                JoinList joinList = new JoinList();
                joinList.setUsername(username);
                joinList.setDate(post.getDate());
                joinList.setTitle(post.getTitle());
                joinList.setEmail(currentUser.getEmail());

                FirebaseClient firebaseClient = new FirebaseClient(DB_URL);
                firebaseClient.joinPost(joinList);

                Toast.makeText(view.getContext(),"Joined!",Toast.LENGTH_SHORT).show();

                post_join.setEnabled(false);
            }
        });

        return view;
    }
}
