package com.example.textfileapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {
    //custom function to disable animation
    private void setSystemAnimationScale(float animationScale) {
        try {
            Class windowManagerStubClazz = Class.forName("android.view.IWindowManager$Stub");
            Method asInterface = windowManagerStubClazz.getDeclaredMethod("asInterface", IBinder.class);
            Class serviceManagerClazz = Class.forName("android.os.ServiceManager");
            Method getService = serviceManagerClazz.getDeclaredMethod("getService", String.class);
            Class windowManagerClazz = Class.forName("android.view.IWindowManager");
            Method setAnimationScales = windowManagerClazz.getDeclaredMethod("setAnimationScales", float[].class);
            Method getAnimationScales = windowManagerClazz.getDeclaredMethod("getAnimationScales");

            IBinder windowManagerBinder = (IBinder) getService.invoke(null, "window");
            Object windowManagerObj = asInterface.invoke(null, windowManagerBinder);
            float[] currentScales = (float[]) getAnimationScales.invoke(windowManagerObj);
            for (int i = 0; i < currentScales.length; i++) {
                currentScales[i] = animationScale;
            }
            setAnimationScales.invoke(windowManagerObj, new Object[]{currentScales});
            Log.d("CustomTestRunner", "Changed permissions of animations");
        } catch (Exception e) {
            Log.e("CustomTestRunner", "Could not change animation scale to " + animationScale + " :'(");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Context ctx = MainActivity.this.getApplicationContext();
        //emulator unlock
        try {
            KeyguardManager mKeyGuardManager = (KeyguardManager)      ctx.getSystemService(Context.KEYGUARD_SERVICE);
            KeyguardManager.KeyguardLock mLock = mKeyGuardManager.newKeyguardLock(MainActivity.class.getSimpleName());
            mLock.disableKeyguard();
        } catch (Exception e) {
            e.printStackTrace();
        }

       //keeping screen awake
        try{
            PowerManager power = (PowerManager) ctx.getSystemService(Context.POWER_SERVICE);
            power.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.ON_AFTER_RELEASE, MainActivity.class.getSimpleName()).acquire();
        }catch (Exception e){
            e.printStackTrace();
        }

        //disabling animations
        try{
            int permStatus = ctx.checkCallingOrSelfPermission(Manifest.permission.SET_ANIMATION_SCALE);
            if (permStatus == PackageManager.PERMISSION_GRANTED){
                setSystemAnimationScale(0.0f);
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        final myFileHandler fileHandler = new myFileHandler();
        final EditText edtFileName = findViewById(R.id.edtFileName);
        final EditText edtFileContent = findViewById(R.id.edtFileContent);
        Button btnSave = findViewById(R.id.btnSave);
        Button btnRead = findViewById(R.id.btnRead);
        Button btnDelete = findViewById(R.id.btnDelete);
        final TextView txtDisplay = findViewById(R.id.txtDisplay);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fileContent = edtFileContent.getText().toString().trim();
                String fileName=edtFileName.getText().toString().trim();
                if (fileName.equals("")){
                    txtDisplay.setText("Please input file name!");
                    return;
                }
                for (int i=0;i<fileName.length();i++){
                    char c = fileName.charAt(i);
                    if (!((64<c&&c<91)||(96<c&&c<123))){
                        txtDisplay.setText("No special characters in file name!");
                        return;
                    }
                }
                if (myFileHandler.saveToFile(fileContent,fileName)){
                    txtDisplay.setText(fileContent);
                }
                else txtDisplay.setText("File not saved!");

            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fileName=edtFileName.getText().toString().trim();
                if (fileName.equals("")){
                    txtDisplay.setText("Please input file name!");
                    return;
                }
                for (int i=0;i<fileName.length();i++){
                    char c = fileName.charAt(i);
                    if (!((64<c&&c<91)||(96<c&&c<123))){
                        txtDisplay.setText("No special characters in file name!");
                        return;
                    }
                }

                if (myFileHandler.deleteFile(fileName)){
                    txtDisplay.setText("File Deleted!");
                }

            }
        });

        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fileName=edtFileName.getText().toString().trim();
                if (fileName.equals("")){
                    txtDisplay.setText("Please input file name!");
                    return;
                }
                for (int i=0;i<fileName.length();i++){
                    char c = fileName.charAt(i);
                    if (!((64<c&&c<91)||(96<c&&c<123))){
                        txtDisplay.setText("No special characters in file name!");
                        return;
                    }
                }

                String output = fileHandler.ReadFile(getApplicationContext(),
                        fileName+".txt");
                if (output.equals("FNF")){
                    txtDisplay.setText("File Does Not Exist!");
                }
                else{
                    txtDisplay.setText(output);
                }
            }
        });


    }


}
