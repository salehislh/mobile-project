package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class flashlight extends AppCompatActivity {

    private ToggleButton toggle;
    private SeekBar repeater;
    private TextView txtStatus;
    private CameraManager cameraManager;
    private final Handler handler = new Handler();
    private boolean flashlight = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_flash_light);

        toggle = findViewById(R.id.toggle);
        repeater = findViewById(R.id.repeater);
        cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);
        txtStatus = findViewById(R.id.txtStatus);

        repeater.getProgressDrawable().setColorFilter(0xFFFFFFFF, PorterDuff.Mode.MULTIPLY);

        if(!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY) || !getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)){
            Toast.makeText(this, "your device doesn't have a flashlight", Toast.LENGTH_SHORT).show();
            toggle.setEnabled(false);
            repeater.setEnabled(false);

        }
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                try {
                    if (toggle.isChecked()){
                        txtStatus.setText("ON");
                        Flashlight(true);
                        Repeater();
                    } else {
                        txtStatus.setText("OFF");
                        Flashlight(false);
                    }
                } catch (Exception e){
                    Log.e("error", e.toString());

                }

            }
        });
        repeater.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                handler.removeCallbacksAndMessages(null);
                if (toggle.isChecked()) {
                    Repeater();
                } else {
                    toggle.setChecked(true);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void Flashlight(boolean isOn) throws CameraAccessException {
        try {
            if (isOn){
                cameraManager.setTorchMode(String.valueOf(0),true);
            } else {
                cameraManager.setTorchMode(String.valueOf(0),false);
            }
        } catch (CameraAccessException e){
            Log.e("error", e.toString());
        }

    }
    private void Repeater(){
        int repeaterLevel = repeater.getProgress();
        if(repeaterLevel != 0){
            final int delay = (7 - repeaterLevel) * 100;
            handler.postDelayed(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void run() {
                    if (!toggle.isChecked()) return;
                    if (flashlight) {
                        flashlight = false;
                        try {
                            Flashlight(true);
                        } catch (CameraAccessException e) {
                            throw new RuntimeException(e);
                        }

                    } else {
                        toggle.setChecked(true);
                        flashlight = true;
                        try {
                            Flashlight(false);
                        } catch (CameraAccessException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    handler.postDelayed(this,delay);
                }
            }, delay);
        }
    }
}