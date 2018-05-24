package com.app.uni.uniapp.ui;


import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.app.uni.uniapp.R;
import com.app.uni.uniapp.data.ChatMessage;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends Fragment {

    private View view;
    private FirebaseListAdapter<ChatMessage> adapter;

    public MessageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_message, container, false);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText input= (EditText) view.findViewById(R.id.input);
                FirebaseDatabase.getInstance().getReference().child("messages").push().setValue(new ChatMessage(input.getText().toString(),
                        FirebaseAuth.getInstance().getCurrentUser().getEmail()));
                input.setText("");
            }
        });

        displayChatMessage();

        return view;
    }

    private void displayChatMessage() {
        final ListView listOfMessage = (ListView) view.findViewById(R.id.listOfMessage);

        Query query = FirebaseDatabase.getInstance().getReference().child("messages");
        FirebaseListOptions<ChatMessage> options = new FirebaseListOptions.Builder<ChatMessage>()
                .setQuery(query, ChatMessage.class)
                .setLayout(R.layout.layout_message_items)
                .build();

        adapter = new FirebaseListAdapter<ChatMessage>(options)
        {
            @Override
            protected void populateView(View v, ChatMessage model, int position) {
                TextView messageText,messageUser,messageTime;
                messageText = (TextView) v.findViewById(R.id.messageText);
                messageUser = (TextView) v.findViewById(R.id.messageUser);
                messageTime = (TextView) v.findViewById(R.id.messageTime);

                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser().split("@")[0].toString());
                messageTime.setText(DateFormat.format("dd-MM-YYYY (HH:mm:ss)",model.getMessageTimeDate()));

            }
        };

        adapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listOfMessage.setSelection(adapter.getCount()-1);
            }
        });

        listOfMessage.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}
