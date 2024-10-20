package com.example.myapplicationlpu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;

public class MainActivity5 extends AppCompatActivity {

    TextView verification, regid, name, father, mother, school, pgm, address, hostel, seater, room, mess;
    ImageView proimg, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main5);

        verification = findViewById(R.id.student_id);
        regid = findViewById(R.id.verification_id_in);
        name = findViewById(R.id.student_name_in);
        father = findViewById(R.id.father_name_in);
        mother = findViewById(R.id.mother_name_in);
        school = findViewById(R.id.school_name_in);
        pgm = findViewById(R.id.programme_in);
        address = findViewById(R.id.address_in);
        hostel = findViewById(R.id.hostel_value);
        seater = findViewById(R.id.seater_value);
        room = findViewById(R.id.room_no_value);
        mess = findViewById(R.id.mess_name_value);
        proimg = findViewById(R.id.student_photo);
        back = findViewById(R.id.back_button2);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity5.this, MainActivity4.class));
                finish();
            }
        });


        SharedPreferences sharedPref = getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE);


        boolean isSaved = sharedPref.getBoolean("isSaved", false); // Default is false if not found

        if (isSaved) {
            // Retrieve values from SharedPreferences
            String lname = sharedPref.getString("name", "");
            String lregid = sharedPref.getString("regid", "");
            String lfather = sharedPref.getString("father", "");
            String lmother = sharedPref.getString("mother", "");
            String lfnum = sharedPref.getString("fnum", "");
            String lmnum = sharedPref.getString("mnum", "");
            String lcourse = sharedPref.getString("course", "");
            String lhostel = sharedPref.getString("hostel", "");
            String lschool = sharedPref.getString("school", "");
            String lpgm = sharedPref.getString("pgm", "");
            String laddress = sharedPref.getString("address", "");
            String lhostelname = sharedPref.getString("hostelname", "");
            String lseater = sharedPref.getString("seater", "");
            String lroom = sharedPref.getString("room", "");
            String lmess = sharedPref.getString("messname", "");

            // Check if any value is empty
            if (!lname.isEmpty() && !lregid.isEmpty() && !lfather.isEmpty() &&
                    !lmother.isEmpty() && !lfnum.isEmpty() && !lmnum.isEmpty() && !lcourse.isEmpty() && !lhostel.isEmpty() &&
                    !lschool.isEmpty() && !lpgm.isEmpty() && !laddress.isEmpty() && !lhostelname.isEmpty() && !lseater.isEmpty() && !lroom.isEmpty() && !lmess.isEmpty()) {
                // All required values are present and non-empty


                regid.setText(lregid);
                name.setText(lname);
                father.setText(lfather);
                mother.setText(lmother);
                school.setText(lschool);
                pgm.setText(lpgm);
                address.setText(laddress);
                hostel.setText(lhostelname);
                seater.setText(lseater);
                room.setText(lroom);
                mess.setText(lmess);
                proimg.setImageBitmap(loadImage());

                Log.d("SharedPreferences", "All values are saved and non-empty.");
            } else {
                // Some values are missing or empty
                Log.d("SharedPreferences", "Some values are empty or missing.");
            }
        } else {
            // No data saved or "isSaved" is false
            Log.d("SharedPreferences", "No saved data found or 'isSaved' is false.");
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
}