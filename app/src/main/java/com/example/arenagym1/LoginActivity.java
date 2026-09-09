package com.example.arenagym1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login_main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

//até agora, sem sistema de Login real, só apertar no botão que o login é feito e vai para a   main (AcademiaActivity)
        Button btnLogin = findViewById(R.id.btnLogin);
                btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {              //quando apertar no btn, vai de LoginActivity pra AcademiaActivity
                Intent intent = new Intent(LoginActivity.this, AcademiaActivity.class);
                startActivity(intent);
            }
        });
    }
}