package com.example.osman.orders;

import java.io.Serializable;

public class Tip implements Serializable {

    private String text;
    private boolean open;


    //constructors
    public Tip(String text){
        this.text = text;
        this.open = false;
    }

    public Tip(String text, boolean open) {
        this.text = text;
        this.open = open;
    }


    //get and set
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    @Override
    public String toString() {
        return "Tip{" +
                "text='" + text + '\'' +
                ", open=" + open +
                '}';
    }
}
