package com.example.a642;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.a642.utils.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button cache = findViewById(R.id.cache);
        Button files = findViewById(R.id.files);
        Button delete = findViewById(R.id.delete);

        cache.setOnClickListener(view -> {
            saveExternalCache("CACHE");
        });
        files.setOnClickListener(view -> {
            saveExternalFile("FILES");
        });
        delete.setOnClickListener(view -> {
            deleteExternalFile();
        });
        checkPermission();
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Log.d("@@@", "checkSelfPermission");
        } else {
            Log.d("@@@", "requestPermissionLauncher.launch");
            requestPermissionLauncher.launch(
                    Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    Log.d("@@@", "isGranted");
                } else {
                    Log.d("@@@", "Not isGranted");
                }
            });

    private void deleteExternalFile() {
        String fileName = "external.txt";
        File file;
        File cache;
        file = new File(getExternalFilesDir(null), fileName);
        cache = new File(getExternalCacheDir(), fileName);
        file.delete();
        cache.delete();
        Utils.fireToast(this, String.format
                ("File %s has been deleted", fileName));
    }

    private void saveExternalFile(String files) {
        String fileName = "external.txt";
        File file;
        file = new File(getExternalFilesDir(null), fileName);

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(files.getBytes(StandardCharsets.UTF_8));
            Utils.fireToast(this, String.format
                    ("Write to %s successful", fileName));
        } catch (Exception e) {
            e.printStackTrace();
            Utils.fireToast(this, String.format
                    ("Write to file %s failed", fileName));
        }
    }

    private void saveExternalCache(String cache) {
        String fileName = "external.txt";
        File file;
        file = new File(getExternalCacheDir(), fileName);

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(cache.getBytes(StandardCharsets.UTF_8));
            Utils.fireToast(this, String.format
                    ("Write to %s successful", fileName));
        } catch (Exception e) {
            e.printStackTrace();
            Utils.fireToast(this, String.format
                    ("Write to file %s failed", fileName));
        }
    }
}