package com.example.myapplicationlpu;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity6 extends AppCompatActivity {

    CardView okholder;
    AppCompatButton submit, ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main6);

        okholder = findViewById(R.id.okholder);
        submit = findViewById(R.id.submit_btn);
        ok = findViewById(R.id.submitted);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                okholder.setVisibility(View.VISIBLE);
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                okholder.setVisibility(View.GONE);
            }
        });


    }
}