package com.app.uni.uniapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.uni.uniapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;
    private EditText etCPassword;
    private Button bRegister;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();

        etUsername = (EditText) findViewById(R.id.RusernameText);
        etPassword = (EditText) findViewById(R.id.RpasswordText);
        etCPassword = (EditText) findViewById(R.id.RconfirmPasswordText);
        bRegister = (Button) findViewById(R.id.RregisterButton);

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String cpassword = etCPassword.getText().toString().trim();

                if(TextUtils.isEmpty(username)){
                    Toast.makeText(getApplicationContext(), "Enter username", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Password a adiyoy", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(password.length() < 8){
                    Toast.makeText(getApplicationContext(), "Thambhi.. Password size kaanathu", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(cpassword)) {
                    Toast.makeText(getApplicationContext(), "Confirm password a yaaru adippa ?? ", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!password.equals(cpassword)){
                    Toast.makeText(getApplicationContext(), "Password pila thambhi .. poi maathunga", Toast.LENGTH_SHORT).show();
                    return;
                }

                auth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "Etkanave oruthan irukkan .. illati nee adichathu poora pacha pila", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(RegisterActivity.this, "Nee Register aagitta ... ", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                            finish();
                        }
                    }
                });
            }
        });

    }

}
