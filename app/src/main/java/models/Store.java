package models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

@ParseClassName( "Store" )
public class Store extends ParseObject {

    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE = "logo_store";
    public static final String KEY_NAME = "name";


    public String getDescription(){

        return getString( KEY_DESCRIPTION );
    }

    public  void setDescription(String description){
        put(KEY_DESCRIPTION, description);
    }

    public ParseFile getImage(){

        return getParseFile( KEY_IMAGE );
    }

    public void setImage(ParseFile parseFile){
        put(KEY_IMAGE, parseFile);
    }

    public  String getName(){
        return getString( KEY_NAME );
    }

    public void setName(String name){
        put(KEY_NAME, name);
    }

}
