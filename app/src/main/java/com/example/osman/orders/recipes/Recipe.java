package com.example.osman.orders.recipes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;


public class Recipe implements Serializable {

    public static final String RTAG = "RTAG";

    public static int DIFF_EASY = 1;
    public static int DIFF_MEDIUM = 2;
    public static int DIFF_HARD = 3;


    private String SKU_ID;
    private String title;
    private int imgId;
    private int difficult; //difficult:easy,medium,hard
    private int cookingTimeMins;//cooking time in minutes
    private String[] ingredients;
    private String description;
    private ArrayList<Step> steps;
    private boolean open;


    public Recipe(){}

    public Recipe(String SKU_ID,String title,int imgId,int difficult,int cookingTimeMins,String[] ingredients,String description,ArrayList<Step> steps,boolean open){
        this.SKU_ID = SKU_ID;
        this.title = title;
        this.imgId = imgId;
        this.difficult = difficult;
        this.cookingTimeMins = cookingTimeMins;
        this.ingredients = ingredients;
        this.description = description;
        this.steps = steps;
        this.open = open;
    }


    //pseudo - builder
    public Recipe build(){
        return new Recipe(this.SKU_ID,this.title,this.imgId,this.difficult,this.cookingTimeMins,this.ingredients,this.description,this.steps,this.open);
    }

    // get - set
    public String getSKU_ID(){return SKU_ID;}

    public void setSKU_ID(String SKU_ID){
        this.SKU_ID = SKU_ID;
    }


    public String getTitle(){return title;}

    public void setTitle(String title){
        this.title = title;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public int getDifficult() {
        return difficult;
    }

    public void setDifficult(int difficult) {
        this.difficult = difficult;
    }

    public int getCookingTimeMins() {
        return cookingTimeMins;
    }

    public void setCookingTimeMins(int cookingTimeMins) {
        this.cookingTimeMins = cookingTimeMins;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSteps(ArrayList<Step> steps){
        this.steps = steps;
    }

    public ArrayList<Step> getSteps(){
        return steps;
    }

    public boolean isOpen(){return open;}

    public void setOpen(boolean open){
        this.open = open;
    }

    //inner class for processing recipe steps;
    public static class Step implements Serializable{
        int imgId;
        String instruction;

        // get - set
        public int getImgId() {
            return imgId;
        }

        public void setImgId(int imgId) {
            this.imgId = imgId;
        }

        public String getInstruction() {
            return instruction;
        }

        public void setInstruction(String instruction) {
            this.instruction = instruction;
        }

        public Step(int imgId){
            this.imgId = imgId;
            this.instruction = null;
        }

        public Step(String instruction){
            this.instruction = instruction;
            this.imgId = -1;
        }

        public Step(int imgId,String instruction){
            this.imgId = imgId;
            this.instruction = instruction;
        }

        @Override
        public String toString() {
            return "Step{" +
                    "imgId=" + imgId +
                    ", instruction='" + instruction + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "SKU_ID=" + SKU_ID +
                "title=" + title +
                ", imgId=" + imgId +
                ", difficult=" + difficult +
                ", cookingTimeMins=" + cookingTimeMins +
                ", ingredients=" + Arrays.toString(ingredients) +
                ", description='" + description + '\'' +
                ", steps=" + steps +
                ", open=" + open +
                '}';
    }
}
