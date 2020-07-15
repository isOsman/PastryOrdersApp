package com.example.osman.orders;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private String folderName = "Orders";
    private String dataName = "data.ord";
    private String idName = "id.ord";

    private FileInputStream fin;
    private FileOutputStream fos;

    private File folder;
    private File dataFile;
    private File idFile;

    String TAG = "FILETAG";

    private Context context;

    public Storage(Context context){
        this.context = context;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getListName() {
        return dataName;
    }

    public void setListName(String listName) {
        this.dataName = listName;
    }

    public String getIdName() {
        return idName;
    }

    public void setIdName(String idName) {
        this.idName = idName;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    //initialize dir and files
    public void init() throws IOException, ClassNotFoundException{
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

    //print to debug console all records from list(file)
    public void printList() throws IOException , ClassNotFoundException{
        ArrayList<Order> orders = getList();
            if(orders != null){
                for (Order o : orders){
                    Log.d(TAG,o.toString(context));
                }
            }
    }
}
