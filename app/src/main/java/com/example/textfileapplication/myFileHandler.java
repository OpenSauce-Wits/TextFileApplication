package com.example.textfileapplication;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class myFileHandler {

    final static String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/TextFileApplication/" ;
    final static String TAG = myFileHandler.class.getName();

    public myFileHandler(){}

    public static  String ReadFile( Context context,final String fileName){
        //fileName should have the .txt extension
        String line = null;

        try {
            FileInputStream fileInputStream = new FileInputStream (new File(path + fileName));
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();

            while ( (line = bufferedReader.readLine()) != null )
            {
                stringBuilder.append(line + System.getProperty("line.separator"));
            }
            fileInputStream.close();
            line = stringBuilder.toString();

            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            Log.d(TAG, ex.getMessage());
            line="FNF";
        }
        catch(IOException ex) {
            Log.d(TAG, ex.getMessage());
            line="FNF";
        }
        return line;
    }

    public static boolean deleteFile(String fileName){
            new File(path).mkdir();
            File file = new File(path+ fileName);
            if (!file.exists()) {
                return true;
            }
            else return file.delete();
    }

    public static boolean saveToFile( String fileContent,final String fileName){
        try {
            new File(path).mkdir();
            File file = new File(path+ fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file,true);
            fileOutputStream.write((fileContent + System.getProperty("line.separator")).getBytes());

            return true;
        }  catch(FileNotFoundException ex) {
            Log.d(TAG, ex.getMessage());
        }  catch(IOException ex) {
            Log.d(TAG, ex.getMessage());
        }
        return  false;


    }
}
