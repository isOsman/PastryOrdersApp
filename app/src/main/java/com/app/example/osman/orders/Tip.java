package com.app.example.osman.orders;

import java.io.Serializable;

public class Tip implements Serializable {

    private String text;
    private boolean open;
    private boolean canOpen;


    //constructors
    public Tip(String text){
        this.text = text;
        this.open = false;
        this.canOpen = false;
    }

    public Tip(String text, boolean open) {
        this.text = text;
        this.open = open;
        this.canOpen = false;
    }

    public Tip(String text, boolean open,boolean canOpen) {
        this.text = text;
        this.open = open;
        this.canOpen = canOpen;
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

    public boolean isCanOpen() {
        return canOpen;
    }

    public void setCanOpen(boolean canOpen) {
        this.canOpen = canOpen;
    }

    @Override
    public String toString() {
        return "Tip{" +
                "text='" + text + '\'' +
                ", open=" + open +
                ", canOpen=" + canOpen +
                '}';
    }
}
