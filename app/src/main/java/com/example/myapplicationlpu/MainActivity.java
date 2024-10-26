package com.example.myapplicationlpu;

import static android.Manifest.permission.READ_MEDIA_IMAGES;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.app.PendingIntent.getActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Properties;
import java.util.Random;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class MainActivity extends AppCompatActivity {

    byte[] bitmapData;
    EditText name, regid, fathername, mothername, fathernum, mothernum, course, hostel, school, pgm, address, hostelname, seater, room, messname;
    ProgressBar pb;
    ImageView view;
    Boolean isImage = false;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    SharedPreferences sharedPref;

    RelativeLayout loadingscreen, request;

    AppCompatButton refresh, close;
    AppCompatButton savebtn;

    int device_count = 0;

    int request_count = 0;

    HashMap<String, Object> data = new HashMap<>();

    Dialog dialog;

    private final String APP_VERSION = "1.0.7";
    private static final long THIRTY_MINUTES_IN_MILLIS = 30 * 60 * 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        view = findViewById(R.id.image);


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("TouchLPU");


        sharedPref = getSharedPreferences("MyAppPreferences",Context.MODE_PRIVATE);


        boolean isSaved = sharedPref.getBoolean("isSaved", false); // Default is false if not found

        loadingscreen = findViewById(R.id.loadingscreen);
        request = findViewById(R.id.request);
        refresh = findViewById(R.id.refresh);
        close = findViewById(R.id.close);

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MainActivity.class));
                finish();
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (isSaved) {
            // Retrieve values from SharedPreferences

            loadingscreen.setVisibility(View.VISIBLE);


            String name = sharedPref.getString("name", "");
            String regid = sharedPref.getString("regid", "");
            String father = sharedPref.getString("father", "");
            String mother = sharedPref.getString("mother", "");
            String fnum = sharedPref.getString("fnum", "");
            String mnum = sharedPref.getString("mnum", "");
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
            if (!name.isEmpty() && !regid.isEmpty() && !father.isEmpty() &&
                    !mother.isEmpty() && !fnum.isEmpty() && !mnum.isEmpty() && !lcourse.isEmpty() && !lhostel.isEmpty() &&
                    !lschool.isEmpty() && !lpgm.isEmpty() && !laddress.isEmpty() && !lhostelname.isEmpty() && !lseater.isEmpty() && !lroom.isEmpty() && !lmess.isEmpty()) {
                // All required values are present and non-empty

                long lastSavedTime = sharedPref.getLong("LAST_TIME_KEY", -1);

                // If there's no saved time, assume 30 minutes have not passed (first time usage)
                if (lastSavedTime != -1) {

                    long currentTime = System.currentTimeMillis();
                    long timeDifference = currentTime - lastSavedTime;
                    if(timeDifference>=THIRTY_MINUTES_IN_MILLIS){
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("hexcode", "xxxxxx");
                        editor.apply();
                    }
                }


                deviceVerification();

                Log.d("SharedPreferences", "All values are saved and non-empty.");
            } else {
                // Some values are missing or empty
                Log.d("SharedPreferences", "Some values are empty or missing.");
            }
        } else {
            // No data saved or "isSaved" is false
            Log.d("SharedPreferences", "No saved data found or 'isSaved' is false.");
        }





        name = findViewById(R.id.nameentry);
        regid = findViewById(R.id.regentry);
        fathername = findViewById(R.id.father);
        mothername = findViewById(R.id.mother);
        fathernum = findViewById(R.id.fatherno);
        mothernum = findViewById(R.id.motherno);
        pb = findViewById(R.id.pb1);
        course = findViewById(R.id.coursename);
        hostel = findViewById(R.id.hosteldetail);
        school = findViewById(R.id.schoolname);
        pgm = findViewById(R.id.programmename);
        address = findViewById(R.id.address);
        hostelname = findViewById(R.id.hostelname);
        seater = findViewById(R.id.seater);
        room = findViewById(R.id.roomno);
        messname = findViewById(R.id.messname);

        dialog = new Dialog(MainActivity.this);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialogbox_update);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        MaterialButton skip = dialog.findViewById(R.id.button_skip);
        AppCompatButton update = dialog.findViewById(R.id.button_update);

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(MainActivity.this, MainActivity4.class);
                startActivity(intent);
                finish();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLink(MainActivity.this, "https://drive.google.com/drive/folders/1e_tXCDaam8NEaAfGlGzB1lnFZDCACVvO?usp=sharing");
            }
        });





        ActivityCompat.requestPermissions(this, new String[]{READ_MEDIA_IMAGES, WRITE_EXTERNAL_STORAGE},
                PackageManager.PERMISSION_GRANTED);



        savebtn = findViewById(R.id.saved);
        AppCompatButton sel = findViewById(R.id.selimg);
        sel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(MainActivity.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start(100);
            }
        });
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pb.setVisibility(View.VISIBLE);


                if(isImage){

                    writeImage();
                }
                deviceRegistration();



            }
        });





    }


    private void deviceRegistration(){

        String ipPrefix = "192.163.15";

        // Create a Random object
        Random random = new Random();

        // Generate a random number between 0 and 255 for the last octet
        int lastOctet = random.nextInt(256);

        // Construct the full IP address
        String randomIP = ipPrefix + "." + lastOctet;

        databaseReference.child("device_ids").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists() && snapshot.hasChild(randomIP)){

                    deviceRegistration();

                }

                saveDataToLocal(randomIP);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void deviceVerification(){

        databaseReference.child("registered_devices").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){

                    for (DataSnapshot snapshot1 : snapshot.getChildren()){
                        if(snapshot1.child("device_id").getValue().toString().equals(sharedPref.getString("device_id", "mmm"))){

                            getAppVersionInfo();
                            return;
                        }


                    }
                    Toast.makeText(MainActivity.this, "Device registration request is still not approved", Toast.LENGTH_SHORT).show();
                    request.setVisibility(View.VISIBLE);
                    savebtn.setClickable(false);



                }else{
                    Toast.makeText(MainActivity.this, "Something Error in Database connection Please refresh again or try again later.", Toast.LENGTH_SHORT).show();
                    request.setVisibility(View.VISIBLE);
                    savebtn.setClickable(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void saveDataToLocal(String device_ip){

        if(sharedPref.getBoolean("isSaved", false)){
            return;
        }

        String mname = name.getText().toString();
        String reg = regid.getText().toString();
        String father = fathername.getText().toString();
        String mother = mothername.getText().toString();
        String fanum = fathernum.getText().toString();
        String manum = mothernum.getText().toString();
        String mcourse = course.getText().toString();
        String mhostel = hostel.getText().toString();
        String mschool = school.getText().toString();
        String mpgm = pgm.getText().toString();
        String maddress = address.getText().toString();
        String mhostelname = hostelname.getText().toString();
        String mseater = seater.getText().toString();
        String mroom = room.getText().toString();
        String mmess = messname.getText().toString();

        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString("name", mname);
        editor.putString("regid", reg);
        editor.putString("father", father);
        editor.putString("mother", mother);
        editor.putString("fnum", fanum);
        editor.putString("mnum", manum);
        editor.putString("course", mcourse);
        editor.putString("hostel", mhostel);
        editor.putString("school", mschool);
        editor.putString("address", maddress);
        editor.putString("pgm", mpgm);
        editor.putString("hostelname", mhostelname);
        editor.putString("seater", mseater);
        editor.putString("room", mroom);
        editor.putString("messname", mmess);
        editor.putString("device_id", device_ip);
        editor.putBoolean("isSaved", true);
        editor.apply();
        databaseReference.child("device_ids").child(device_ip).setValue(device_ip);

        data.put("name", mname);
        data.put("regid", reg);
        data.put("device_id", device_ip);



        databaseReference.child("requests").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    request_count = (int) snapshot.getChildrenCount();
                }


            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(MainActivity.this, "error try again letter", Toast.LENGTH_SHORT).show();
            }
        });
        databaseReference.child("requests").child(String.format("request%02d", request_count + 1))
                .setValue(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(MainActivity.this, "Request Saved Successfully", Toast.LENGTH_SHORT).show();

                        sendEmail(new EmailSentListener() {
                            @Override
                            public void onEmailSent() {
//                Toast.makeText(MainActivity.this, "email sent successfully", Toast.LENGTH_SHORT).show();
                                Log.v("EMAILERROR", "email send");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(MainActivity.this, "Request Email send successfully", Toast.LENGTH_SHORT).show();
                                        pb.setVisibility(View.GONE);
                                    }
                                });
                            }

                            @Override
                            public void onEmailFailed(Exception e) {

                                Log.v("EMAILERROR", e.toString());
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(MainActivity.this, "Request Email was not send", Toast.LENGTH_SHORT).show();
                                        pb.setVisibility(View.GONE);
                                    }
                                });

                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Failed try again later", Toast.LENGTH_SHORT).show();
                    }
                });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == 100) {
            if (data != null && data.getData() != null) {
                Uri uri = data.getData();
                try {
                    // Get the Bitmap from the Uri
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    view.setImageBitmap(bitmap);
                    // Convert the Bitmap to a byte array
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    bitmapData = byteArrayOutputStream.toByteArray();

                    Toast.makeText(this, "Image collected succefully", Toast.LENGTH_SHORT).show();
                    isImage = true;

                    // Now you have the bitmapData array, which you can use as needed.
                    // For example, you can save it, upload it, or display it.

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void writeImage() {
        StorageManager storageManager = (StorageManager) getSystemService(STORAGE_SERVICE);
        StorageVolume storageVolume = storageManager.getStorageVolumes().get(0);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            // Define the directory where the image will be saved
            File fileOutputDir = new File(storageVolume.getDirectory().getPath() + "/Download/myLPUTouch/ProfileImg/");

            // Create the directory if it does not exist
            if (!fileOutputDir.exists()) {
                fileOutputDir.mkdirs();
                // Create the image file path
                File file = new File(fileOutputDir, "profileimg.jpg");

                try {
                    // Create a FileOutputStream; this will overwrite the file if it already exists
                    FileOutputStream fileOutputStream = new FileOutputStream(file);

                    // Write the image data to the file
                    fileOutputStream.write(bitmapData);
                    fileOutputStream.close();

                    // Notify the user that the image has been saved successfully
                    Toast.makeText(MainActivity.this, "Image Saved Successfully", Toast.LENGTH_SHORT).show();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException("File not found: " + e.getMessage(), e);
                } catch (IOException e) {
                    throw new RuntimeException("Error while writing the image: " + e.getMessage(), e);
                }
            }


        }
    }



    private void sendEmail(EmailSentListener listener){
        String senderemail = "bokaisnotboka12345@gmail.com";
        String reciever = "souvikguptabusiness@gmail.com";
        String pass = "wsub kpzj atvd yhnc";
        String stringMost = "smtp.gmail.com";
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", stringMost);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderemail, pass);
            }
        });

        MimeMessage mimeMessage = new MimeMessage(session);
        try {
            mimeMessage.addRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress(reciever)});
            mimeMessage.setSubject("Subject: Approval Request for TouchLPU");
            mimeMessage.setText(new JSONObject(data).toString());

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {

                        Transport.send(mimeMessage);
                        if (listener != null) {
                            listener.onEmailSent();
                        }
                    }catch (Exception e){
                        if (listener != null) {
                            listener.onEmailFailed(e);
                        }
                        e.printStackTrace();
                    }
                }
            });

            thread.start();


        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }


    public interface EmailSentListener {
        void onEmailSent();
        void onEmailFailed(Exception e);
    }

    public void getAppVersionInfo() {


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists() && !snapshot.child("app_version").getValue().toString().equals(APP_VERSION)){
                    Toast.makeText(MainActivity.this, "It is not Updated", Toast.LENGTH_SHORT).show();
                    dialog.show();


                }
                else{
                    Intent intent = new Intent(MainActivity.this, MainActivity4.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void openLink(AppCompatActivity activity, String url) {
        try {
            // Create an intent with the action to view a URL
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));

            // Check if there is an app that can handle the intent

            startActivity(intent);
        } catch (Exception e) {
            // Catch any exceptions and display a message
            Toast.makeText(activity, "An error occurred while trying to open the link.", Toast.LENGTH_SHORT).show();
        }
    }


}