package com.codepath.storemobile;

import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import models.Store;

public class CreateStore extends AppCompatActivity {

    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1035;
    public final static int PICK_PHOTO_CODE = 1047;
    public String photoFileName = "photo.jpg";
    public String photoFileNameGallery = "download.jpeg";
    private File photoFile;
    private File photoFileGalerry;

    public static final String TAG = "CreateStore";

    private ImageView logoStore;
    private EditText storeName;
    private EditText storePassword;
    private EditText storePasswordConfirmation;
    private EditText storePhone;
    private EditText storeEmail;
    private EditText storeDescription;

    private int GALLERY = 3, CAMERA = 4;

    private Button btnAddLogo;
    private Button btnSave;
    String ab;
    String momo;
    String store_Name;
    Intent intent;
    boolean VerifyStoreNameBoolean = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_create_store );

        logoStore = findViewById( R.id.iv_logo_store );
        storeName = findViewById( R.id.et_store_name );
        storePassword = findViewById( R.id.et_store_password );
        storePasswordConfirmation = findViewById( R.id.et_store_confirm_password );
        storePhone = findViewById( R.id.et_store_phone );
        storeEmail = findViewById(R.id.et_store_email);
        storeDescription = findViewById( R.id.et_store_description );

        btnAddLogo = findViewById( R.id.btn_add_logo );
        btnSave = findViewById( R.id.btn_save_store );

        setupFloatingLabelError();

        btnAddLogo.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        } );

        btnSave.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    String store_Description = storeDescription.getText().toString();
                    String store_Password = storePassword.getText().toString();
                    String store_Password_Confirm = storePasswordConfirmation.getText().toString();
                    String store_Email = storeEmail.getText().toString();
                    String store_Phone = storePhone.getText().toString();

                    // If category is empty returns a toast message
                    if(store_Name.isEmpty() || store_Description.isEmpty() || store_Password.isEmpty() || store_Password_Confirm.isEmpty()
                            ||store_Email.isEmpty() || store_Phone.isEmpty()){
                        Toast.makeText(CreateStore.this, "All the fields are required!", Toast.LENGTH_LONG).show();
                        return;
                    }

                    saveStores(store_Name, store_Description, store_Password, store_Email, store_Phone, photoFile, photoFileGalerry );
                    Toast.makeText(CreateStore.this, "Store successfully saved! ", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent( CreateStore.this, ManageStoreActivity.class );
                    startActivity( intent );

            }
        } );
    }

    // Method showPictureDialog()
    // Method for choose type of picture entry
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
//        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
//        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity( getPackageManager() ) != null) {
            // Bring up gallery to select a photo
            startActivityForResult( intent, PICK_PHOTO_CODE );
        }
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

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }


    private void launchCamera() {
        Intent i = new Intent( MediaStore.ACTION_IMAGE_CAPTURE);
        photoFileGalerry = null;
        photoFile = getPhotoFileUri(photoFileName);

        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        Uri fileProvider = FileProvider.getUriForFile(CreateStore.this, "com.codepath.storemobile.fileprovider", photoFile);
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
                logoStore.setImageBitmap(takenImage);
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
                    Uri fileProvider = FileProvider.getUriForFile(CreateStore.this, "com.codepath.storemobile.fileprovider", photoFileGalerry);
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
                logoStore.setImageBitmap(selectedImage);
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

    private void saveStores (String name, final String description,
                             String password, String email, String phone, File photoFile, File photoFileGalerry){
        Store stores = new Store();
        if(photoFile != null){
            stores.setImage( new ParseFile( photoFile) );
        } else{
            Bitmap bitmap = ((BitmapDrawable)logoStore.getDrawable()).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            // Compress image to lower quality scale 1 - 100
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] image = stream.toByteArray();
            stores.setImage( new ParseFile( momo.substring(momo.lastIndexOf('/')+1), image, "image/*" ) );
        }

        stores.setDescription( description );
        stores.setName( name );
        stores.setPassword( password );
        stores.setEmail( email );
        stores.setPhone( phone );

        stores.saveInBackground( new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e( TAG, "Error while saving: "+e.getMessage() );
                    e.printStackTrace();
                    return;
                }

                Log.d( TAG, "Success!" );

                storeDescription.setText( "" );
                storeName.setText( "" );
                storeEmail.setText( "" );
                storePhone.setText( "" );
                logoStore.setImageResource( 0 );
            }
        } );
    }

    private void setupFloatingLabelError() {
        final TextInputLayout floatingUsernameLabel = (TextInputLayout) findViewById(R.id.store_name_text_input_layout);
        floatingUsernameLabel.getEditText().addTextChangedListener(new TextWatcher() {
            // ...
            @Override
            public void onTextChanged(CharSequence text, int start, int count, int after) {
                store_Name = storeName.getText().toString();
                store_Name = store_Name.replaceAll("\\s+", "");
                if (!(text.toString().equals(store_Name.replaceAll("\\s+", "")))){
                    storeName.setText(store_Name);
                }

                if (text.length() > 0 && text.length() <= 2) {
                    floatingUsernameLabel.setError("Avoid Putting Space");
                    floatingUsernameLabel.setErrorEnabled(true);
                } else {
                    floatingUsernameLabel.setErrorEnabled(false);
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void  VerifyStoreName(final String name){
        ParseQuery<Store> query = new ParseQuery<Store>( Store.class );
        query.include( Store.KEY_NAME );
        query.findInBackground( new FindCallback<Store>() {

            @Override
            public void done(List<Store> objects, ParseException e) {

                if (e != null) {
                    Log.e( TAG, "Error with query: "+e.getMessage() );
                    e.printStackTrace();
                    return;
                }

                for (int i = 0; i < objects.size(); i++) {
                    if (name.equals(objects.get(i).getName()) ){
                        VerifyStoreNameBoolean = true;
                    }
                }

            }

        } );
    }



}
