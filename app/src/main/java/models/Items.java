package models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

@ParseClassName( "Product" )
public class Items extends ParseObject {

    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE_ITEM = "image_product";
    public static final String KEY_PRICE = "price";
    public static final String KEY_BUYING_PRICE = "buying_price";
    public static final String KEY_CATEGORY = "category_product";
    public static final String KEY_STORE = "store";
    public static final String KEY_QUANTITY = "qty_product";


    public String getDescriptionStore(){

        return getString( KEY_DESCRIPTION );
    }

    public  void setDescriptionSotre (String description){
        put(KEY_DESCRIPTION, description);
    }

    public ParseFile getImageItems(){

        return getParseFile( KEY_IMAGE_ITEM );
    }

    public void setImageStore(ParseFile parseFile){
        put(KEY_IMAGE_ITEM, parseFile);
    }

    public String getCategory(){
        return getString( KEY_CATEGORY );
    }

    public void setCategory(String category){
        put(KEY_CATEGORY, category);
    }

    public String getPrice(){
        return getString( KEY_PRICE );
    }

    public  void setPrice(String price){

        put( KEY_PRICE, price );

    }

    public String getBuyingPrice(){
        return getString( KEY_BUYING_PRICE );
    }

    public void setBuyingPrice(String buyingPrice){

        put( KEY_BUYING_PRICE, buyingPrice );

    }

    public  ParseObject getStore(){
        return  getParseObject( KEY_STORE );
    }

    public  void setStore(ParseObject parseObject){
        put(KEY_STORE, parseObject);
    }

    public Number getQuantity(){
        return getNumber( KEY_QUANTITY );
    }

    public void setQuantity(Number quantity){
        put(KEY_QUANTITY, quantity);

    }
}
