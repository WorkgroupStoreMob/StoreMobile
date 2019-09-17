package com.codepath.storemobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private Button btnLoginUser, btnSignUpUser, btnMyStoreUserLogin;
    private EditText etUsernameLogin, etPasswordLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );

        btnLoginUser = findViewById( R.id.btn_login_user );
        btnSignUpUser = findViewById( R.id.btn_sign_up_user );
        btnMyStoreUserLogin = findViewById( R.id.btn_my_store_user_login );

        etUsernameLogin = findViewById( R.id.et_username_login );
        etPasswordLogin = findViewById( R.id.et_password_login );

        btnLoginUser.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username1 = etUsernameLogin.getText().toString();
                String password1 = etPasswordLogin.getText().toString();

                login( username1, password1 );

            }
        } );

        btnSignUpUser.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUpIntent = new Intent( LoginActivity.this, SignUpActivity.class );
                startActivity( signUpIntent );
            }
        } );

        btnMyStoreUserLogin.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myStoreIntent = new Intent( LoginActivity.this, OpenStoreActivity.class );
                startActivity( myStoreIntent );
            }
        } );
    }


    private void login(String username, String password) {
        ParseUser.logInInBackground( username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(e != null){
                    // TODO: Better error handling
                    Log.e(TAG, "Issues with login");
                    e.printStackTrace();
                    return;
                }
                // TODO: navigate to new activity if user login property

                goStoreActivity();

            }
        } );
    }

    private void goStoreActivity() {
        Intent loginIntent = new Intent( LoginActivity.this, StoreActivity.class );
        startActivity( loginIntent );
        finish();
    }
}
