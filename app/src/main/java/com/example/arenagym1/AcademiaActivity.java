package com.example.arenagym1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AcademiaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.academia_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.academiamain), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageView imgAbdominal = findViewById(R.id.imgAbdominal);
        imgAbdominal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AcademiaActivity.this, AbdominalActivity.class);
                startActivity(intent);
            }
        });

        ImageView imgElevacaoLateral = findViewById(R.id.imgElevacaoLateral);
        imgElevacaoLateral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AcademiaActivity.this, ElevacaoLateralActivity.class);
                startActivity(intent);
            }
        });
    }
}