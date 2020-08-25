package com.app.example.osman.orders;

import android.content.Context;
import android.util.Log;

import com.app.example.osman.orders.recipes.Recipe;
import com.app.example.osman.orders.recipes.RecipeData;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

//this class save and return data of files
public class Storage {

    //use singleton pattern
    private static Storage instance;

    private String folderName = "Orders";
    private String dataName = "data.ord";
    private String idName = "id.ord";
    private String tipName = "tip.ord";
    private String lastSessionDate = "last_session_date.ord";
    private String recipes = "recipes.ord";
    private String add = "add.ord";
    private String tipDialog = "tip_dialog.ord";

    private FileInputStream fin;
    private FileOutputStream fos;

    private File folder;
    private File dataFile;
    private File idFile;
    private File tipFile;
    private File lastDateFile;
    private File recipesFile;
    private File addFile;
    private File tipDialogFile;

    static String TAG = "FILETAG";

    private Context context;

    private Storage(Context context) throws IOException, ClassNotFoundException {
        Log.d(TAG,"new Storage Instance");
        this.context = context.getApplicationContext();
        init();
    }

    //singleton constructor
    public static Storage getInstance(Context context) throws IOException, ClassNotFoundException {
        if(instance == null){
            instance = new Storage(context);

        }
        return  instance;
    }




    //initialize dir and all files
    public void init() throws IOException, ClassNotFoundException{
        //get path to device storage
        File root = context.getExternalFilesDir(null);

        //if not exists,create new file
        folder = new File(root,folderName);
        if(!folder.exists()){
            folder.mkdir();
            Log.d(TAG,"folder created: " + folder.getAbsolutePath());
        }else{
            Log.d(TAG,"folder ready");
        }

        idFile = new File(root + "/" + folderName,idName);
        if(!idFile.exists()){
                idFile.createNewFile();
                writeID(1);//initial id
            Log.d(TAG,"id created: " + idFile.getAbsolutePath());
        }else{
            Log.d(TAG,"id ready,num : " + getID());
        }

        dataFile = new File(root + "/" + folderName,dataName);
        if(!dataFile.exists()){
            dataFile.createNewFile();
            Log.d(TAG,"data created: " + dataFile.getAbsolutePath());
        }else{
            Log.d(TAG,"data ready");
        }


        //нужно переписывать проект,но мне лень
        //прости меня Осман если ты сейчас читаешь это и переписываешь проект
        //но я рад,что ты его переписываешь,потому-что если ты это делаешь - значит ты его обновляешь •ᴗ•:))
        tipFile = new File(root + "/" + folderName,tipName);
        if(!tipFile.exists()){
            tipFile.createNewFile();
            ArrayList<Tip> tips = new ArrayList<>();
            for(Tip tip : TipData.tips){
                tips.add(tip);//refresh tiplist
            }
            writeTips(tips);
            Log.d(TAG,"tip created: " + tipFile.getAbsolutePath());
        }else{
            Log.d(TAG,"tip ready");
            ArrayList<Tip> tips = getTips();
            //if have new tips add to tiplist
            if(tips.size() < TipData.tips.length){
                Log.d(TAG,"New Tips");
               for(int i = tips.size();i<TipData.tips.length;i++){
                   tips.add(TipData.tips[i]);
               }

               writeTips(tips);
            }
        }


        lastDateFile = new File(root + "/" + folderName,lastSessionDate);
        if(!lastDateFile.exists()){
            lastDateFile.createNewFile();
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY,DateUtils.WAKE_HOUR);
            calendar.set(Calendar.MINUTE,0);
            Date writeDate = calendar.getTime();
            String strDate = writeDate.getTime()+"";
            try {
                Log.d(TAG, "writeDate: " + strDate);
                writeDate(strDate);
            }catch (Exception e){
                Log.d(TAG,"Fail to write date");
            }

            Log.d(TAG,"lastDateFile created: " + lastDateFile.getAbsolutePath());
            Log.d(TAG,"now date:  " + strDate);
        }else{
            Log.d(TAG,"lastDateFile ready");
            Date date;
            try {
                date = new Date(Long.parseLong(getDate()));
                Log.d(TAG,"parsed date: " + date.toString());
                Log.d(TAG,"minutes diff: " + DateUtils.getDateDiff(date,new Date(), TimeUnit.MINUTES));
            }catch (Exception e){
                Log.d(TAG,"FAIL TO PARSE DATE");
                e.printStackTrace();
            }

        }

        recipesFile = new File(root + "/" + folderName,recipes);
        RecipeData recipeData = new RecipeData();
        if(!recipesFile.exists()){
            recipesFile.createNewFile();
            ArrayList<Recipe> recipes = new ArrayList<>();
            for(Recipe r : recipeData.recipes){
                recipes.add(r);//refresh list
            }
            writeRecipes(recipes);
            Log.d(TAG,"recipes created: " + recipesFile.getAbsolutePath());
        }else{
            Log.d(TAG,"recipes ready");
            ArrayList<Recipe> recipes = getRecipes();
            if(recipes.size() < recipeData.recipes.length){
                Log.d(Recipe.RTAG,"New recipes");
                for(int i = recipes.size();i<recipeData.recipes.length;i++){
                    recipes.add(recipeData.recipes[i]);
                }
                writeRecipes(recipes);
            }
            for(Recipe r : recipes) {
                Log.d(Recipe.RTAG, r.toString());
            }

        }


        addFile = new File(root + "/" + folderName,add);
        if(!addFile.exists()){
            addFile.createNewFile();
            Log.d(TAG,"addfile created: " + addFile.getAbsolutePath());
            writeAddStatus(Request.ADD_ON);
        }else{
            Log.d(TAG,"addfile ready");
            Log.d(TAG, "addfile status: " + getAddStatus());
        }

        tipDialogFile = new File(root + "/" + folderName,tipDialog);
        if(!tipDialogFile.exists()){
            tipDialogFile.createNewFile();
            Log.d(TAG,"tipDialogFile created: " + tipDialogFile.getAbsolutePath());
            writeTipDialogStatus(Request.DIALOG_ON);
        }else{
            Log.d(TAG,"tipDialogFile ready");
            Log.d(TAG, "tipDialogFile status: " + getAddStatus());
        }



    }


    public void writeTipDialogStatus(String status) throws IOException {
        Log.d(TAG,"writeTipDialogtatus()");
        FileWriter fileWriter = new FileWriter(tipDialogFile);
        fileWriter.write(status);
        fileWriter.close();
    }

    public String getTipDialogStatus() throws FileNotFoundException {
        Log.d(TAG,"getTipDialogStatus()");
        Scanner scan = new Scanner(tipDialogFile);
        String date = scan.next();
        scan.close();
        return date;
    }



    public void writeAddStatus(String status) throws IOException {
        Log.d(TAG,"writeAddStatus()");
        FileWriter fileWriter = new FileWriter(addFile);
        fileWriter.write(status);
        fileWriter.close();
    }

    public String getAddStatus() throws FileNotFoundException {
        Log.d(TAG,"getAdd()");
        Scanner scan = new Scanner(addFile);
        String date = scan.next();
        scan.close();
        return date;
    }

    public boolean addIsOn() throws FileNotFoundException {
        Log.d(TAG,"addIsOn()");
        return getAddStatus().equals(Request.ADD_ON);
    }

    public void writeDate(String date) throws IOException {
        Log.d(TAG,"writeID()");
        FileWriter fileWriter = new FileWriter(lastDateFile);
        fileWriter.write(date);
        fileWriter.close();
    }

    public String getDate() throws FileNotFoundException {
        Log.d(TAG,"getDate()");
        Scanner scan = new Scanner(lastDateFile);
        String date = scan.nextLine();
        scan.close();
        return date;
    }

    public void writeTips(ArrayList<Tip> tips) throws IOException{
        Log.d(TAG,"writeTips()");
        FileOutputStream fos = new FileOutputStream(tipFile);
        ObjectOutputStream ois = new ObjectOutputStream(fos);
        ois.writeObject(tips);
        ois.close();
        fos.close();
        Log.d(TAG,"writeTips() END");
    }


    @SuppressWarnings("unchecked")
    public ArrayList<Tip> getTips() throws IOException, ClassNotFoundException {
        Log.d(TAG,"getTips()");
        //if file is emty then return null
        if(tipFile.length() == 0) return null;

        //else...
        ArrayList<Tip> list = null;
        FileInputStream fis = new FileInputStream(tipFile);
        ObjectInputStream ois = new ObjectInputStream(fis);
        list = (ArrayList<Tip>) ois.readObject();

        ois.close();
        fis.close();
        Log.d(TAG,"getTips() END");
        return list;
    }

    public void writeRecipes(ArrayList<Recipe> recipes) throws IOException{
        Log.d(TAG,"writeRecipes()");
        FileOutputStream fos = new FileOutputStream(recipesFile);
        ObjectOutputStream ois = new ObjectOutputStream(fos);
        ois.writeObject(recipes);
        ois.close();
        fos.close();
        Log.d(TAG,"writeRecipes() END");
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Recipe> getRecipes() throws IOException, ClassNotFoundException {
        Log.d(TAG,"getRecipes()");
        //if file is emty then return null
        if(recipesFile.length() == 0) return null;

        //else...
        ArrayList<Recipe> list = null;
        FileInputStream fis = new FileInputStream(recipesFile);
        ObjectInputStream ois = new ObjectInputStream(fis);
        list = (ArrayList<Recipe>) ois.readObject();

        ois.close();
        fis.close();
        Log.d(TAG,"getRecipes() END");
        return list;
    }

    //write id to file,id need for Order obj
    public void writeID(int id) throws IOException{
        Log.d(TAG,"writeID()");
        FileWriter fileWriter = new FileWriter(idFile);
        fileWriter.write(id+"");
        fileWriter.close();
    }

    public void clearId() throws IOException{
        writeID(1);
    }

    //read id num from idFile
    public int getID() throws  IOException{
        Log.d(TAG,"getID()");
        Scanner scan = new Scanner(idFile);
        int i = scan.nextInt();
        scan.close();
        return i;
    }

    //read and write to file incremented ID
    public int getAndIncID()throws IOException{
        int i = getID();
        writeID(i+1);
        return i;
    }

    //write list with orders to file(save orderList)
    public void writeList(ArrayList<Order> orders) throws IOException{
        Log.d(TAG,"writeList()");
        FileOutputStream fos = new FileOutputStream(dataFile);
        ObjectOutputStream ois = new ObjectOutputStream(fos);
        ois.writeObject(orders);
        ois.close();
        fos.close();
        Log.d(TAG,"writeList() END");
    }

    //return saved list
    @SuppressWarnings("unchecked")
    public ArrayList<Order> getList() throws IOException,ClassNotFoundException{
        Log.d(TAG,"getList()");
        //if file is emty then return null
        if(dataFile.length() == 0) return null;

        //else...
        ArrayList<Order> list = null;
        FileInputStream fis = new FileInputStream(dataFile);
        ObjectInputStream ois = new ObjectInputStream(fis);
        list = (ArrayList<Order>) ois.readObject();

        ois.close();
        fis.close();
        Log.d(TAG,"getList() END");
        return list;
    }




//    //print to debug console all records from list(file)
//    public void printList() throws IOException , ClassNotFoundException{
//        ArrayList<Order> orders = getList();
//            if(orders != null){
//                for (Order o : orders){
//                    Log.d(TAG,o.toString(context));
//                }
//            }
//    }
}
