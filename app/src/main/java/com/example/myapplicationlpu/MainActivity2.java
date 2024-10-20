package com.example.myapplicationlpu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.util.Log;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity2 extends AppCompatActivity {

    ImageView image, close;
    TextView mealtype, name, regid, scantext, course, date, time, father, mother, hostel, session, countert;
    private Handler handler = new Handler(Looper.getMainLooper());
    private int counter = 0;
    private final int interval = 1000; // 1000 milliseconds = 1 second

    RelativeLayout nav, accept;

    private GestureDetector gestureDetector;

    private float initialY;
    private float dY;
    private boolean isDragging = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.primary));
        }

        image = findViewById(R.id.image);
        mealtype = findViewById(R.id.mealtype);
        name = findViewById(R.id.studentname);
        regid = findViewById(R.id.regid);
        scantext = findViewById(R.id.hostel);
        course = findViewById(R.id.coursecode);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        father = findViewById(R.id.fatherde);
        mother = findViewById(R.id.motherde);
        session = findViewById(R.id.sessionde);
        hostel = findViewById(R.id.hostelde);
        countert = findViewById(R.id.counter);
        close = findViewById(R.id.cancel);
        nav = findViewById(R.id.nav1);
        accept =findViewById(R.id.accepthldr);

        RelativeLayout imgho = findViewById(R.id.imageholder);

        imgho.setOutlineProvider(ViewOutlineProvider.BACKGROUND);
        imgho.setClipToOutline(true);



        image.setImageBitmap(loadImage());

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.removeCallbacks(runnable);
                Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
                startActivity(intent);
                finish();
            }
        });

        nav.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Capture initial touch position
                        initialY = nav.getY();
                        dY = event.getRawY() - nav.getY();
                        isDragging = true;
                        return true;

                    case MotionEvent.ACTION_MOVE:
                        if (isDragging) {
                            // Update the position of nav1 as the user moves their finger
                            nav.setY(event.getRawY() - dY);
                        }
                        return true;

                    case MotionEvent.ACTION_UP:
                        isDragging = false;
                        // Check if the user dragged nav1 down by a threshold (e.g., 200 pixels)
                        if (nav.getY() - initialY > 200) {
                            // Trigger the Intent to navigate to MainActivity3 when dragged down enough
                            handler.removeCallbacks(runnable);
                            Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // Reset the position if not dragged far enough
                            nav.setY(initialY);
                        }
                        return true;

                    default:
                        return false;
                }
            }
        });



        SharedPreferences sharedPref = getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE);

        // Create a Date object with the current date and time
        Date currentDate = new Date();

        // Format for the date: "Oct 16, 2024"
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
        dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
        String ldate = dateFormat.format(currentDate);

        // Format for the time: "02:41 PM"
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
        timeFormat.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
        String ltime = timeFormat.format(currentDate);

            // Retrieve values from SharedPreferences
            String lname = sharedPref.getString("name", "");
            String lregid = sharedPref.getString("regid", "");
            String lfather = sharedPref.getString("father", "");
            String lmother = sharedPref.getString("mother", "");
            String lfnum = sharedPref.getString("fnum", "");
            String lmnum = sharedPref.getString("mnum", "");
            String lcourse = sharedPref.getString("course", "");
            String lhostel = sharedPref.getString("hostel", "");
            String lhex = sharedPref.getString("hexcode", "");


            Intent intent = getIntent();
            String lmeal = intent.getStringExtra("mealtype");
            String lmsg = intent.getStringExtra("scanmsg");

            // Check if any value is empty
            if (!lname.isEmpty() && !lregid.isEmpty() && !lfather.isEmpty() &&
                    !lmother.isEmpty() && !lfnum.isEmpty() && !lmnum.isEmpty() && !lcourse.isEmpty() && !lhostel.isEmpty()  && !lhex.isEmpty()) {
                // All required values are present and non-empty
                name.setText(lname);
                regid.setText(lregid);
                father.setText(lfather+" ("+lfnum+")");
                mother.setText(lmother+" ("+lmnum+")");
                session.setText(lcourse);
                course.setText(lcourse);
                hostel.setText(lhostel);
                mealtype.setText(lmeal);
                scantext.setText(lmsg);
                date.setText(ldate);
                time.setText(ltime);
                accept.setBackgroundColor(Color.parseColor(lhex));
                Log.d("SharedPreferences", "All values are saved and non-empty.");
                startTask();
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

    private void startTask() {
        handler.postDelayed(runnable, interval);
    }

    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (counter < 30) {
                // Your task to be executed every second
                Log.d("Runnable", "Task executed at second: " + (counter + 1));

                // Increment the counter
                counter++;
                countert.setText(30-counter+"");

                // Schedule the next execution after 1 second
                handler.postDelayed(this, interval);
            } else {
                // Stop the task after 30 seconds
                handler.removeCallbacks(this);
                Log.d("Runnable", "Task stopped after 30 seconds.");
                Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
                startActivity(intent);
                finish();
            }
        }
    };

}