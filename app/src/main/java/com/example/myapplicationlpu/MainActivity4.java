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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity4 extends AppCompatActivity {

    RelativeLayout spholder, closesidepannel, fsp, mgmess, colorcode;
    Toolbar mtb;
    Boolean isOpen = false;
    ImageView proimg;
    TextView name, regid, course, attendenceview, cgpa, examcount;
    LinearLayout openmess, openslip, mffopener;
    RecyclerView recyclerView;
    CardView cc;
    EditText hexentry;
    AppCompatButton hexsave;

    private static String reg_id = "";
    private static String pass_word = "";


    private static String session_id = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main4);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.primary));
        }

        spholder = findViewById(R.id.sidepholder);
        mtb = findViewById(R.id.mtb);
        closesidepannel = findViewById(R.id.cs);
        spholder.setVisibility(View.GONE);
        fsp = findViewById(R.id.fsp);
        proimg = findViewById(R.id.simg);
        name = findViewById(R.id.sname);
        regid = findViewById(R.id.sid);
        course = findViewById(R.id.scourse);
        openmess = findViewById(R.id.openmess);
        mgmess = findViewById(R.id.mgmess);
        recyclerView = findViewById(R.id.rv1);
        colorcode = findViewById(R.id.relativeLayout2);
        cc = findViewById(R.id.cc);
        hexentry = findViewById(R.id.hexentry);
        hexsave = findViewById(R.id.saavehex);
        openslip = findViewById(R.id.openresidential);
        attendenceview = findViewById(R.id.znumber);
        cgpa = findViewById(R.id.qnumber);
        examcount = findViewById(R.id.rnumber);
        mffopener = findViewById(R.id.mffopener);

        mffopener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity4.this, MainActivity6.class));
            }
        });


        openslip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity4.this, MainActivity5.class));

            }
        });


        SharedPreferences sharedPref = getSharedPreferences("MyAppPreferences",Context.MODE_PRIVATE);

        colorcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spholder.setVisibility(View.GONE);
                isOpen = false;
                cc.setVisibility(View.VISIBLE);
            }
        });

        hexsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPref.edit();
                Toast.makeText(MainActivity4.this, hexentry.getText().toString(), Toast.LENGTH_SHORT).show();
                editor.putString("hexcode", hexentry.getText().toString());
                editor.apply();
                cc.setVisibility(View.GONE);
            }
        });

        ClassAdapter adapter;

        recyclerView.setHasFixedSize(true);

// Set the LinearLayoutManager with horizontal orientation
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        ClassList[] classLists = new ClassList[]{
                new ClassList("MTH302", "09:00-10:00", "34-502A"),
                new ClassList("PEL312", "10:00-11:00", "34-502A"),
                new ClassList("CSE212", "11:00-12:00", "34-502A"),
                new ClassList("CSE205", "07:00-11:00", "34-502A")
        };

        adapter = new ClassAdapter(this, classLists);
        recyclerView.setAdapter(adapter);


        mgmess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity4.this, MainActivity3.class);
                startActivity(intent);
            }
        });

        openmess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity4.this, MainActivity3.class);
                startActivity(intent);
            }
        });

        fsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spholder.setVisibility(View.VISIBLE);
                isOpen = true;
            }
        });
        closesidepannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spholder.setVisibility(View.GONE);
                isOpen = false;
            }
        });

        proimg.setImageBitmap(loadImage());
        loaddata();


//        fetchSessionID();

//        fetchData(fetchSessionID());


    }

    public void fetchSessionID(){
        APIInterface apiInterface = RetrofitInstance.getRetrofit().create(APIInterface.class);
        PayloadFormat payloadFormat = new PayloadFormat(reg_id, pass_word);
        Call<SessionCoockie> sessionCoockieCall = apiInterface.getSessionCoockie(payloadFormat);
        sessionCoockieCall.enqueue(new Callback<SessionCoockie>() {
            @Override
            public void onResponse(Call<SessionCoockie> call, Response<SessionCoockie> response) {
                Log.v("API", "OnAPICall: code= "+response.code());
                Log.v("API", "OnAPICall: coockie= "+response.body().cookie);
//                Toast.makeText(MainActivity4.this, response.body().cookie, Toast.LENGTH_SHORT).show();
                session_id = response.body().cookie;
                fetchData(session_id, reg_id, pass_word);
                fetchExams(session_id, reg_id, pass_word);
            }

            @Override
            public void onFailure(Call<SessionCoockie> call, Throwable t) {
                Log.e("API", "OnError: "+t.getMessage());
            }
        });
    }

    public void fetchData(String sessionid, String regid, String password){

        APIInterface apiInterface = RetrofitInstance.getRetrofit().create(APIInterface.class);
        PayloadFormatwithCookie payloadFormatwithCookie = new PayloadFormatwithCookie(regid, password, sessionid);
        Call<UserDetails> userDetailsCall = apiInterface.getUserDetails(payloadFormatwithCookie);
        userDetailsCall.enqueue(new Callback<UserDetails>() {
            @Override
            public void onResponse(Call<UserDetails> call, Response<UserDetails> response) {

                Log.v("API", "OnAPICall: code= "+response.code());
                Log.v("API", "OnAPICall: name= "+response.body().name);

                Log.v("API", "OnAPICall: regid= "+response.body().registration_number);
                Log.v("API", "OnAPICall: section= "+response.body().section);
                Log.v("API", "OnAPICall: attendence= "+response.body().agg_attendance);
//                Toast.makeText(MainActivity4.this, response.body().name, Toast.LENGTH_SHORT).show();
//                Toast.makeText(MainActivity4.this, response.body().registration_number, Toast.LENGTH_SHORT).show();
//                Toast.makeText(MainActivity4.this, response.body().section, Toast.LENGTH_SHORT).show();
//                Toast.makeText(MainActivity4.this, response.body().agg_attendance, Toast.LENGTH_SHORT).show();
                attendenceview.setText(response.body().agg_attendance+"%");
                cgpa.setText(response.body().cgpa);
            }

            @Override
            public void onFailure(Call<UserDetails> call, Throwable t) {


                Log.e("API", "OnError: "+t.getMessage());
            }
        });
    }



    public void fetchExams(String sessionid, String regid, String password){

        APIInterface apiInterface = RetrofitInstance.getRetrofit().create(APIInterface.class);
        PayloadFormatwithCookie payloadFormatwithCookie = new PayloadFormatwithCookie(regid, password, sessionid);
        Call<Exams> userDetailsCall = apiInterface.getExams(payloadFormatwithCookie);
        userDetailsCall.enqueue(new Callback<Exams>() {
            @Override
            public void onResponse(Call<Exams> call, Response<Exams> response) {

                Log.v("API", "OnAPICall: code= "+response.code());

                List<ExamDetails> examDetailsList = response.body().exams;

                Toast.makeText(MainActivity4.this, examDetailsList.size()+"", Toast.LENGTH_SHORT).show();
                examcount.setText(examDetailsList.size()+"");

                for(int i = 0; i< examDetailsList.size(); i++){
                    ExamDetails examDetails = examDetailsList.get(i);
                    Log.v("APIExams", examDetails.course_code);
                    Log.v("APIExams", examDetails.exam_type);
                    Log.v("APIExams", examDetails.course_name);
                    Log.v("APIExams", examDetails.date);
                    Log.v("APIExams", examDetails.room_no);
                }

            }

            @Override
            public void onFailure(Call<Exams> call, Throwable t) {


                Log.e("API", "OnError: "+t.getMessage());
            }
        });
    }


    public void loaddata() {


        SharedPreferences sharedPref = getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE);



        // Retrieve values from SharedPreferences
        String lname = sharedPref.getString("name", "");
        String lcourse = sharedPref.getString("course", "");
        String lregid = sharedPref.getString("regid", "");


        // Check if any value is empty
        if (!lname.isEmpty() && !lcourse.isEmpty() && !lregid.isEmpty()) {
            // All required values are present and non-empty
            name.setText(lname);
            course.setText(lcourse);
            regid.setText(lregid);

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


    @Override
    public void onBackPressed() {
        if(isOpen){
            spholder.setVisibility(View.GONE);
            isOpen = false;
        }else{

            super.onBackPressed();
        }
    }
}