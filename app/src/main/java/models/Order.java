package models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName( "Order" )
public class Order extends ParseObject {

    public static final String KEY_NAME_ORDER = "user_name";
    public static final String KEY_EMAIL_ORDER = "user_email";
    public static final String KEY_PHONE_ORDER = "user_phone";
    public static final String KEY_STATUS = "status";
    public static final String KEY_CREATEDAT_ORDER = "createdAt";
    public static final String KEY_PRICE_ORDER = "price";
    public static final String KEY_QTY_ORDER = "qty";
    public static final String KEY_ITEM = "item";


    public String getNameOrder(){

        return getString( KEY_NAME_ORDER );
    }

    public  void setNameOrder (String user_name){
        put(KEY_NAME_ORDER, user_name );
    }


    public String getEmailOrder(){

        return getString( KEY_EMAIL_ORDER );
    }

    public  void setEmailOrder (String user_email){
        put(KEY_EMAIL_ORDER, user_email);
    }

    public String getPhoneOrder(){

        return getString( KEY_PHONE_ORDER );
    }

    public  void setPhoneOrder (String user_phone){
        put(KEY_PHONE_ORDER, user_phone);
    }

    public String getStatus(){

        return getString( KEY_STATUS );
    }

    public  void setStatus (String status){
        put(KEY_STATUS, status);
    }

    public String getCreatedAtOrder(){

        return getString( KEY_CREATEDAT_ORDER );
    }

    public  void setCreatedAtOrder (String createdAtOrder){
        put(KEY_CREATEDAT_ORDER, createdAtOrder);
    }

    public String getPriceOrder(){

        return getString( KEY_PRICE_ORDER );
    }

    public  void setPriceOrder (String price){
        put(KEY_PRICE_ORDER, price);
    }

    public String getQuantityOrder(){

        return getString( KEY_QTY_ORDER );
    }

    public  void setQuantityOrder (String qtyOrder){
        put(KEY_QTY_ORDER, qtyOrder);
    }

    public String getItemOrder(){

        return getString( KEY_ITEM );
    }

    public  void setItemOrder (String itemOrder){
        put(KEY_ITEM, itemOrder);
    }
}
