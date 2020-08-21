package com.example.osman.orders;


import android.content.Context;
import android.content.res.Resources;
import java.io.Serializable;

public class Order implements Serializable {

    private static final long serialVersionUID = 7882431916747982038L;

    private int id;//номер заказа
    private String customer;//заказщик
    private String cake;//торт
    private String orderDate;//дата заказа
    private String sendDate;//дата отправки
    private double weight;//масса торта
    private double price;//цена
    private boolean made;//выполнен


    //constructors
    public Order(){}

    public Order(int id,String customer, String cake, String orderDate, String sendDate, double weight, double price, boolean made) {
        this.id = id;
        this.customer = customer;
        this.cake = cake;
        this.orderDate = orderDate;
        this.sendDate = sendDate;
        this.weight = weight;
        this.price = price;
        this.made = made;

    }


    //getters and setters
    public int getId(){return id;}

    public void setId(int id){
        this.id = id;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getCake() {
        return cake;
    }

    public void setCake(String kind) {
        this.cake = kind;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isMade() {
        return made;
    }

    public void setMade(boolean made) {
        this.made = made;
    }


    public String toString(Context context){
        String s = getString(R.string.orderNumber,context) + " " + getId() + "\n"
                    + getString(R.string.customer,context)  + " " + getCustomer() + "\n"
                    + getString(R.string.cake,context) + " " + getCake() + "\n"
                    + getString(R.string.orderDate,context)  + " " + getOrderDate()+ "\n"
                    + getString(R.string.sendDate,context)   + " " + getSendDate() + "\n"
                    + getString(R.string.weight,context)     + " " + getWeight() + " " + getString(R.string.weight_unit,context) + "\n"
                    + getString(R.string.price,context)      + " " + getPrice()  + " " + getString(R.string.price_unit,context ) + "\n"
                    + getString(R.string.made,context)       + ": " + (isMade() ? "Да" : "Нет");

        return s;
    }

    private String getString(int res,Context context){
        return context.getResources().getString(res);
    }


}
