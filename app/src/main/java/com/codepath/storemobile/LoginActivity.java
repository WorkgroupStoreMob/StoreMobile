package com.codepath.storemobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    private Button btnLoginUser, btnSignUpUser, btnMyStoreUserLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );

        btnLoginUser = findViewById( R.id.btn_login_user );
        btnSignUpUser = findViewById( R.id.btn_sign_up_user );
        btnMyStoreUserLogin = findViewById( R.id.btn_my_store_user_login );

        btnLoginUser.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent( LoginActivity.this, StoreActivity.class );
                startActivity( loginIntent );
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
}
