package com.example.myapplicationlpu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;


public class MainActivity3 extends AppCompatActivity {




    String mealtype;
    ImageView proimg;
    TextView name, father, mother, hostel, programme;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main3);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.primary));
        }

        AppCompatButton breakfast = findViewById(R.id.breakfast);
        AppCompatButton lunch = findViewById(R.id.lunch);
        AppCompatButton tea = findViewById(R.id.tea);
        AppCompatButton dinner = findViewById(R.id.dinner);
        proimg= findViewById(R.id.profile_image);
        name = findViewById(R.id.user_name);
        father = findViewById(R.id.father);
        mother = findViewById(R.id.mother);
        programme = findViewById(R.id.programme);
        hostel = findViewById(R.id.hostel);
        back = findViewById(R.id.back_button);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity3.this, MainActivity4.class);
                startActivity(intent);
                finish();
            }
        });

        SharedPreferences sharedPref = getSharedPreferences("MyAppPreferences",Context.MODE_PRIVATE);




        breakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mealtype = "Breakfast";
                if(sharedPref.getString("hexcode", "xxxxxx").equals("xxxxxx")){
                    Toast.makeText(MainActivity3.this, "Please Upload a HEX code", Toast.LENGTH_SHORT).show();
                }
                else{
                    scan();
                }
            }
        });
        lunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mealtype = "Lunch";
                if(sharedPref.getString("hexcode", "xxxxxx").equals("xxxxxx")){
                    Toast.makeText(MainActivity3.this, "Please Upload a HEX code", Toast.LENGTH_SHORT).show();
                }
                else{
                    scan();
                }
            }
        });
        tea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mealtype = "Tea";
                if(sharedPref.getString("hexcode", "xxxxxx").equals("xxxxxx")){
                    Toast.makeText(MainActivity3.this, "Please Upload a HEX code", Toast.LENGTH_SHORT).show();
                }
                else{
                    scan();
                }
            }
        });
        dinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mealtype = "Dinner";
                if(sharedPref.getString("hexcode", "xxxxxx").equals("xxxxxx")){
                    Toast.makeText(MainActivity3.this, "Please Upload a HEX code", Toast.LENGTH_SHORT).show();
                }
                else{
                    scan();
                }
            }
        });


        loaddata();

        proimg.setImageBitmap(loadImage());




    }

    public void loaddata() {


        SharedPreferences sharedPref = getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE);



        // Retrieve values from SharedPreferences
        String lname = sharedPref.getString("name", "");
        String lfather = sharedPref.getString("father", "");
        String lmother = sharedPref.getString("mother", "");
        String lfnum = sharedPref.getString("fnum", "");
        String lmnum = sharedPref.getString("mnum", "");
        String lcourse = sharedPref.getString("course", "");
        String lhostel = sharedPref.getString("hostel", "");


        // Check if any value is empty
        if (!lname.isEmpty() && !lfather.isEmpty() &&
                !lmother.isEmpty() && !lfnum.isEmpty() && !lmnum.isEmpty() && !lcourse.isEmpty() && !lhostel.isEmpty()) {
            // All required values are present and non-empty
            name.setText(lname);
            father.setText(lfather+" ("+lfnum+")");
            mother.setText(lmother+" ("+lmnum+")");
            programme.setText(lcourse);
            hostel.setText(lhostel);

        } else {
            // Some values are missing or empty
            Log.d("SharedPreferences", "Some values are empty or missing.");
        }



    }


    public Bitmap loadImage(){
        Bitmap bit=null;
        StorageManager storageManager = (StorageManager) getSystemService(STORAGE_SERVICE);
        StorageVolume storageVolume = storageManager.getStorageVolumes().get(0);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            File fileinput = new File(storageVolume.getDirectory().getPath()+"/Download/myLPUTouch/ProfileImg/profileimg.jpg");
            bit = BitmapFactory.decodeFile(fileinput.getPath());

        }
        return bit;
    }




    public void scan(){
        ScanOptions options = new ScanOptions();
        options.setBeepEnabled(true);
        options.setPrompt("Scan the mess QR");
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barlauncher.launch(options);
    }

    ActivityResultLauncher<ScanOptions> barlauncher = registerForActivityResult(new ScanContract(), result->{

        if(result.getContents()!=null){
            Intent intent = new Intent(MainActivity3.this, MainActivity2.class);
            intent.putExtra("mealtype", mealtype);
            intent.putExtra("scanmsg", result.getContents().toString());
            startActivity(intent);
            finish();

        }

    });
}