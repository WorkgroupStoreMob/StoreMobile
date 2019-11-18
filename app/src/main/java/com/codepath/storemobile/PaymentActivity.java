package com.codepath.storemobile;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v7.app.AlertDialog;

import com.braintreepayments.cardform.view.CardForm;
import com.parse.ParseUser;

import models.Order;

public class PaymentActivity extends AppCompatActivity {

    CardForm cardForm;
    Button buy;
    AlertDialog.Builder alertBuilder;

    Toolbar toolbar;

    private EditText nameOrder;
    private EditText emailOrder;
    private EditText phoneOrder;

    ParseUser userOrder;
    Order addUser;

    private String nameUser, emailUser, phoneUser;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_payment );

        //Add Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbarItemPaymentCard);
        setSupportActionBar(toolbar);
        getSupportActionBar().setLogo( R.drawable.logosmr);
        getSupportActionBar().setTitle("  Payment form");

        //Add current user information to String variable
        userOrder = new ParseUser();
        nameUser =  userOrder.getString( "name" );
        emailUser = userOrder.getEmail();
        phoneUser = userOrder.getString( "phone" );

        //Form user to save

        nameOrder = (EditText) findViewById( R.id.et_name_order_process );
        emailOrder = (EditText) findViewById( R.id.et_email_order_process );
        phoneOrder = (EditText) findViewById( R.id.et_phone_order_process );

        nameOrder.setText( nameUser );
        emailOrder.setText( emailUser );
        phoneOrder.setText( phoneUser );

        String nUser = nameOrder.getText().toString();
        String eUser = emailOrder.getText().toString();
        String pUser = phoneOrder.getText().toString();




        cardForm = findViewById(R.id.card_form);
        buy  = findViewById(R.id.btnBuy);


        // Design card form
        cardForm.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(true)
                .postalCodeRequired(true)
                .mobileNumberRequired(true)
                .mobileNumberExplanation("SMS is required on this number")
                .setup(PaymentActivity.this);

        cardForm.getCvvEditText().setInputType( InputType.TYPE_CLASS_NUMBER |
                InputType.TYPE_NUMBER_VARIATION_PASSWORD);

        //cardForm.setMovementMethod(new ScrollingMovementMethod());



        // buy action
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cardForm.isValid()) {
                    alertBuilder = new AlertDialog.Builder(PaymentActivity.this);
                    alertBuilder.setTitle("Confirm before purchase");
                    alertBuilder.setMessage("Card number: " + cardForm.getCardNumber() + "\n" +
                            "Card expiry date: " + cardForm.getExpirationDateEditText().getText().toString() + "\n" +
                            "Postal code: " + cardForm.getPostalCode() + "\n" +
                            "Phone number: " + cardForm.getMobileNumber());
                    alertBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //TODO: add payment process
                            dialogInterface.dismiss();
                            saveOrder();
                            Toast.makeText(PaymentActivity.this, "Thank you for purchase", Toast.LENGTH_LONG).show();
                            backToStoreList();
                        }
                    });
                    alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog alertDialog = alertBuilder.create();
                    alertDialog.show();

                } else {
                    Toast.makeText(PaymentActivity.this, "Please complete the form", Toast.LENGTH_LONG).show();
                }
            }
        });

    }



    private void saveOrder() {

    }

    private void backToStoreList() {
        Intent intent = new Intent( PaymentActivity.this, StoreActivity.class );
        startActivity( intent );
    }
}
