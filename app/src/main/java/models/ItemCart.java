package models;


import com.parse.ParseFile;

import org.parceler.Parcel;

import java.io.File;

@Parcel
public class ItemCart {
    String category;
    String price;
    String total;
    String qte;
    ParseFile image;

    public ItemCart() {
    }

    public ItemCart(String category, String price, String total, String qte, ParseFile image) {
        this.category = category;
        this.price = price;
        this.total = total;
        this.qte = qte;
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String allprice) {
        total = total;
    }

    public String getQte() {
        return qte;
    }

    public void setQte(String qte) {
        this.qte = qte;
    }

    public ParseFile getImage() {
        return image;
    }

    public void setImage(ParseFile image) {
        this.image = image;
    }
}
