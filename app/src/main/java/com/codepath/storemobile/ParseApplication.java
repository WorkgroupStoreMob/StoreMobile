package com.codepath.storemobile;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

import models.Items;
import models.Order;
import models.Store;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass( Order.class);
        ParseObject.registerSubclass( Items.class);
        ParseObject.registerSubclass( Store.class);


        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.networkInterceptors().add(httpLoggingInterceptor);

        // set applicationId, and server server based on the values in the Heroku settings.
        // clientKey is not needed unless explicitly configured
        // any network interceptors must be added with the Configuration Builder given this syntax
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("storemob") // should correspond to APP_ID env variable
                .clientKey("IdappStoreMobilexxYYvvs578Workgroup")  // set explicitly unless clientKey is explicitly configured on Parse server
                .clientBuilder(builder)
                .server("https:storemob.herokuapp.com/parse").build());
    }
}
