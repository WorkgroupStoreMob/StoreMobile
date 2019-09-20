package com.codepath.storemobile;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Calendar;

import models.Items;
import models.Store;

public class AddItemsActivity extends AppCompatActivity {


    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    public final static int PICK_PHOTO_CODE = 1046;
    public String photoFileName = "photo.jpg";
    private File photoFile;

    public static final String TAG = "AddItemsActivity";

    private ImageView image_item;
    private EditText etcategory;
    private EditText etprice;
    private EditText etbuyingPrice;
    private EditText etquantity;
    private EditText etdescription;

    private int GALLERY = 1, CAMERA = 2;

    private Button btnAddImage;
    private Button btnSave;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_add_items );

        image_item = findViewById( R.id.iv_image_product );
        etcategory = findViewById( R.id.et_item_category );
        etprice = findViewById( R.id.et_item_selling_price );
        etbuyingPrice = findViewById( R.id.et_item_buying_price );
        etquantity = findViewById( R.id.et_item_quantity );
        etdescription = findViewById( R.id.et_item_description );

        btnAddImage = findViewById( R.id.btn_add_image );
        btnSave = findViewById( R.id.btn_save_item );

        btnAddImage.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               showPictureDialog();
            }
        } );

        btnSave.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category = etcategory.getText().toString();
                String description = etdescription.getText().toString();
                String price = etprice.getText().toString();
                String buyingPrice = etbuyingPrice.getText().toString();



               // String storename = store.getName();

//                if (photoFile == null || image_item.getDrawable() == null) {
//                    Log.e( TAG, "No Photo to post" );
//                    Toast.makeText( AddItemsActivity.this, "There is no photo", Toast.LENGTH_SHORT ).show();
//                    return;
//                }

                saveItems( description, buyingPrice, price, category, photoFile );
            }
        } );
    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder( this );
        pictureDialog.setTitle( "Select Action" );
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera"};
        pictureDialog.setItems( pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                launchCamera();
                                break;
                        }
                    }
                } );
        pictureDialog.show();
    }

    private void choosePhotoFromGallary() {
        // Create intent for picking a photo from the gallery
        Intent intent = new Intent( Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI );
        photoFile = getPhotoFileUri(photoFileName);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity( getPackageManager() ) != null) {
            // Bring up gallery to select a photo
            startActivityForResult( intent, PICK_PHOTO_CODE );

        }
    }

    private void launchCamera() {
        Intent i = new Intent( MediaStore.ACTION_IMAGE_CAPTURE);
        photoFile = getPhotoFileUri(photoFileName);

        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        Uri fileProvider = FileProvider.getUriForFile(this, "com.codepath.storemobile.fileprovider", photoFile);
        i.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (i.resolveActivity(this.getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(i, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // by this point we have the camera photo on disk
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                // RESIZE BITMAP, see section below
                // Load the taken image into a preview
                image_item.setImageBitmap(takenImage);
            } else { // Result was a failure
                Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == PICK_PHOTO_CODE){
            if (data != null) {
                Uri photoUri = data.getData();
                // Do something with the photo based on Uri
                Bitmap selectedImage = null;
                try {
                    selectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);
                    String absolutePath = photoFile.getAbsolutePath();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // Load the selected image into a preview
               image_item.setImageBitmap(selectedImage);
            }
        }

    }

    // Returns the File for a photo stored on disk given the fileName
    public File getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(this.getExternalFilesDir( Environment.DIRECTORY_PICTURES), TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);

        return file;
    }


        private void saveItems ( final String description,
                String buyingPrice, String price, String category, File photoFile){

            Items items = new Items();
            items.setDescriptionSotre( description );
            items.setBuyingPrice( buyingPrice );
            items.setCategory( category );
            items.setPrice( price );
           // items.setQuantity( quantity );
            items.setImageStore( new ParseFile(photoFile ) );
            //items.setStore( store );

            items.saveInBackground( new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e != null) {
                        Log.e( TAG, "Error while saving" );
                        e.printStackTrace();
                        return;
                    }

                    Log.d( TAG, "Success!" );


                    etdescription.setText( "" );
                    etcategory.setText( "" );
                    etbuyingPrice.setText( "" );
                    etprice.setText( "" );
                    etquantity.setText( "" );
                   image_item.setImageResource( 0 );
                }
            } );
        }

    }

