package com.pranjal.wifidata;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Writer extends AsyncTask<Void, Void, Void> {
    List<wifi> l;
    List<Magnetic> l2;
    List<gsm> l3;
    //List<gps> l4;
    Context c;
    String location;
    Writer (List<wifi> w,List<Magnetic> m,List<gsm> g, Context context,String loc){
        l=w;
        l2=m;
        l3=g;
        //l4=gp;
        c=context;
        location=loc;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        File exportDir = new File(c.getExternalFilesDir(""), "");
        if (!exportDir.exists()) {
            exportDir.mkdirs();
        }
        File file = new File(exportDir,"file1" +location+ ".csv");
        File file2 = new File(exportDir,"file2" +location+ ".csv");
        try {
            file2.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        File file3 = new File(exportDir,"file3" +location+ ".csv");
        try {
            file3.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        File file4 = new File(exportDir,"file4" +location+ ".csv");
//        try {
//            file4.createNewFile();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        CSVWriter csvWrite = null;
        try {
            csvWrite = new CSVWriter(new FileWriter(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(wifi w:l){
            String arrStr[] = new String[6];
            arrStr[0] =""+w.id;
            arrStr[1]=w.ssid;
            arrStr[2]=""+w.frequency;
            arrStr[3]=""+w.level;
            arrStr[4]=""+w.time;
            arrStr[5]=""+w.bssid;
            csvWrite.writeNext(arrStr);
        }
        try {
            csvWrite.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        CSVWriter csvWriter2 = null;
        try {
            csvWriter2 = new CSVWriter(new FileWriter(file2));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(gsm g:l3){
            String arrStr[] = new String[4];
            arrStr[0]=""+g.id;
            arrStr[1]=""+g.strength;
            arrStr[2]=""+g.time;
            arrStr[3]=""+g.cid;
            csvWriter2.writeNext(arrStr);
        }
        try {
            csvWriter2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        CSVWriter csvWriter = null;
        try {
            csvWriter = new CSVWriter(new FileWriter(file3));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(Magnetic m:l2){
            String arrStr[] = new String[7];
            arrStr[0]=""+m.id;
            arrStr[1]=""+m.magx;
            arrStr[2]=""+m.magy;
            arrStr[3]=""+m.magz;
            arrStr[4]=""+m.lat;
            arrStr[5]=""+m.lon;
            arrStr[6]=""+m.time;
            csvWriter.writeNext(arrStr);
        }
        try {
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        CSVWriter csvWriter3 = null;
//        try {
//            csvWriter3 = new CSVWriter(new FileWriter(file4));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        for(gps gp:l4){
//            String arrStr[] = new String[4];
//            arrStr[0]=""+gp.id;
//            arrStr[1]=""+gp.lat;
//            arrStr[2]=""+gp.lon;
//            arrStr[3]=""+gp.time;
//            csvWriter2.writeNext(arrStr);
//        }
//        try {
//            csvWriter2.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return null;
    }
}
