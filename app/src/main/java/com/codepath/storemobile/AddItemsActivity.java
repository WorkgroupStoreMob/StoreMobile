package com.codepath.storemobile;

import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
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

import org.parceler.Parcels;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.NumberFormat;
import java.util.Calendar;

import models.Items;
import models.Store;

import static com.codepath.storemobile.CreateStore.isDownloadsDocument;
import static com.codepath.storemobile.CreateStore.isExternalStorageDocument;
import static com.codepath.storemobile.CreateStore.isGooglePhotosUri;
import static com.codepath.storemobile.CreateStore.isMediaDocument;

public class AddItemsActivity extends AppCompatActivity {


    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    public final static int PICK_PHOTO_CODE = 1046;
    public String photoFileName = "photo.jpg";
    private File photoFile;
    private File photoFileGalerry;

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
    String momo;
    Intent intent;
    String nameStore;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_add_items );

        nameStore = getIntent().getStringExtra("nameStore");
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


                // If category is empty returns a toast message
                if(category.isEmpty() || description.isEmpty() || price.isEmpty() || buyingPrice.isEmpty()){
                    Toast.makeText(AddItemsActivity.this, "All the fields are required!", Toast.LENGTH_LONG).show();
                    return;
                }




                // String storename = store.getName();

//                if (photoFile == null || image_item.getDrawable() == null) {
//                    Log.e( TAG, "No Photo to post" );
//                    Toast.makeText( AddItemsActivity.this, "There is no photo", Toast.LENGTH_SHORT ).show();
//                    return;
//                }
                saveItems( description, buyingPrice, price, category, photoFile, photoFileGalerry, nameStore );
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
         intent = new Intent( Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI );

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity( getPackageManager() ) != null) {
            // Bring up gallery to select a photo
            startActivityForResult( intent, PICK_PHOTO_CODE );

        }
    }

    private void launchCamera() {
        Intent i = new Intent( MediaStore.ACTION_IMAGE_CAPTURE);
        photoFileGalerry = null;
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
        }

        if (requestCode == PICK_PHOTO_CODE){
            if (data != null) {
                Uri photoUri = data.getData();
                // Do something with the photo based on Uri
                Bitmap selectedImage = null;
                try {
                    momo = getFilePath(this, photoUri);
                    photoFile = null;

                    photoFileGalerry = getPhotoFileUri(momo.substring(momo.lastIndexOf('/')+1));
                    Uri fileProvider = FileProvider.getUriForFile(this, "com.codepath.storemobile.fileprovider", photoFileGalerry);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }

                try {
                    selectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);
                    String absolutePath = photoFileGalerry.getAbsolutePath();
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
                String buyingPrice, String price, String category, File photoFile, File photoFileGalerry, String store_name){

            Items items = new Items();
            items.setDescriptionSotre( description );
            items.setBuyingPrice( buyingPrice );
            items.setCategory( category );
            items.setPrice( price );
            items.setPrice( price );
            items.setStoreName( store_name );
           // items.setQuantity( quantity );
            if(photoFileGalerry != null){
                Bitmap bitmap = ((BitmapDrawable)image_item.getDrawable()).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                // Compress image to lower quality scale 1 - 100
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] image = stream.toByteArray();
                items.setImageStore( new ParseFile(momo.substring(momo.lastIndexOf('/')+1), image, "image/*" ) );
            } else{
                items.setImageStore( new ParseFile( photoFile) );
            }


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


    public static String getFilePath(Context context, Uri uri) throws URISyntaxException {
        String selection = null;
        String[] selectionArgs = null;
        // Uri is different in versions after KITKAT (Android 4.4), we need to
        if (Build.VERSION.SDK_INT >= 19 && DocumentsContract.isDocumentUri(context.getApplicationContext(), uri)) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                return Environment.getExternalStorageDirectory() + "/" + split[1];
            } else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                uri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
            } else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("image".equals(type)) {
                    uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                selection = "_id=?";
                selectionArgs = new String[]{
                        split[1]
                };
            }
        }
        if ("content".equalsIgnoreCase(uri.getScheme())) {


            if (isGooglePhotosUri(uri)) {
                return uri.getLastPathSegment();
            }

            String[] projection = {
                    MediaStore.Images.Media.DATA
            };
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver()
                        .query(uri, projection, selection, selectionArgs, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }
    }

