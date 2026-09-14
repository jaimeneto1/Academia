package com.example.arenagym1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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

        TextView textoAbdominal = findViewById(R.id.textoabdominal);
        textoAbdominal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //quando apertar no texto, vai de AcademiaActivity pra AbdominalActivity
                Intent intent = new Intent(AcademiaActivity.this, AbdominalActivity.class);
                startActivity(intent);
            }
        });

        TextView textoElevacaoLateral = findViewById(R.id.textoel_lateral);
        textoElevacaoLateral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //quando apertar no texto, vai de AcademiaActivity pra ElevacaoLateralActivity
                Intent intent = new Intent(AcademiaActivity.this, ElevacaoLateralActivity.class);
                startActivity(intent);
            }
        });
    }
}