package com.codepath.storemobile;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends AppCompatActivity {

    private EditText etUsername, etPassword, etConfirmPassword, etUserPhone, etUserEmail, etNameUser;
    private Button btnSaveUser;

    private static final String TAG = "SignUpActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_sign_up );

        etUsername = findViewById( R.id.et_username );
        etPassword = findViewById( R.id.et_password );
        etConfirmPassword = findViewById( R.id.et_confirm_password);
        etUserPhone = findViewById( R.id.et_user_phone );
        etUserEmail = findViewById( R.id.et_user_email );
        etNameUser = findViewById( R.id.et_name_user );

        btnSaveUser = findViewById( R.id.btn_save_user );

        btnSaveUser.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText( SignUpActivity.this, "Test", Toast.LENGTH_SHORT).show();

                String name = etNameUser.getText().toString();
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                //String confirmPassword = etConfirmPassword.getText().toString();
                String email = etUserEmail.getText().toString();

                ParseUser user = new ParseUser();

                user.setUsername( username );
                user.setPassword( password );
                //user.setPassword( confirmPassword );

                user.setEmail( email );
                user.put( "name", name );

                user.signUpInBackground( new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e!=null){
                        Log.e(TAG, "Error while sending");
                        e.printStackTrace();
                        }

                        Log.d(TAG, "Success");

                        etNameUser.setText("");
                        etUsername.setText("");
                        etPassword.setText("");
                        etUserEmail.setText("");

                    }
                } );
            }
        } );
    }
}
