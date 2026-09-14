package com.example.arenagym1;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class ElevacaoLateralActivity extends AppCompatActivity {

    private AutoCompleteTextView etPeso1, etPeso2, etPeso3;
    private AutoCompleteTextView etReps1, etReps2, etReps3;
    private final TextView[] tvTempos = new TextView[3];
    private boolean isUpdating = false;

    // Lógica do Cronômetro
    private final long[] startTimeMillis = new long[3];
    private final long[] totalElapsedMillis = new long[3];
    private boolean isRunning = false;
    private int activeRowIndex = 0;
    private final Handler timerHandler = new Handler(Looper.getMainLooper());
    private Button btnPlayStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_elevacao_lateral);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.elevacao_lateral_main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setupComponents();
        setupTimers();
        setupCascadeListeners();
    }

    private void setupComponents() {
        // Peso
        String[] pesos = getResources().getStringArray(R.array.pesos_padrao);
        ArrayAdapter<String> pesoAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, pesos);

        etPeso1 = findViewById(R.id.etPeso1);
        etPeso2 = findViewById(R.id.etPeso2);
        etPeso3 = findViewById(R.id.etPeso3);

        etPeso1.setAdapter(pesoAdapter);
        etPeso2.setAdapter(pesoAdapter);
        etPeso3.setAdapter(pesoAdapter);

        etPeso1.setOnClickListener(v -> etPeso1.showDropDown());
        etPeso2.setOnClickListener(v -> etPeso2.showDropDown());
        etPeso3.setOnClickListener(v -> etPeso3.showDropDown());

        // Repetições
        String[] reps = getResources().getStringArray(R.array.repeticoes_padrao);
        ArrayAdapter<String> repsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, reps);

        etReps1 = findViewById(R.id.etReps1);
        etReps2 = findViewById(R.id.etReps2);
        etReps3 = findViewById(R.id.etReps3);

        etReps1.setAdapter(repsAdapter);
        etReps2.setAdapter(repsAdapter);
        etReps3.setAdapter(repsAdapter);

        etReps1.setOnClickListener(v -> etReps1.showDropDown());
        etReps2.setOnClickListener(v -> etReps2.showDropDown());
        etReps3.setOnClickListener(v -> etReps3.showDropDown());

        // Botão Play/Stop
        btnPlayStop = findViewById(R.id.button);
        btnPlayStop.setOnClickListener(v -> toggleTimer());
    }

    private void setupTimers() {
        tvTempos[0] = findViewById(R.id.tvTempo1);
        tvTempos[1] = findViewById(R.id.tvTempo2);
        tvTempos[2] = findViewById(R.id.tvTempo3);

        for (int i = 0; i < 3; i++) {
            final int index = i;
            tvTempos[i].setOnClickListener(v -> {
                if (!isRunning) {
                    activeRowIndex = index;
                    updateVisualHighlight();
                }
            });
        }
        updateVisualHighlight();
    }

    private void toggleTimer() {
        if (isRunning) {
            stopCurrentTimer();
            activeRowIndex = (activeRowIndex + 1) % 3;
            updateVisualHighlight();
        } else {
            startCurrentTimer();
        }
    }

    private void startCurrentTimer() {
        isRunning = true;
        btnPlayStop.setText("Stop");
        startTimeMillis[activeRowIndex] = SystemClock.uptimeMillis();
        tvTempos[activeRowIndex].setTextColor(Color.RED);
        timerHandler.postDelayed(updateTimeRunnable, 0);
    }

    private void stopCurrentTimer() {
        isRunning = false;
        btnPlayStop.setText("Play");
        totalElapsedMillis[activeRowIndex] += SystemClock.uptimeMillis() - startTimeMillis[activeRowIndex];
        tvTempos[activeRowIndex].setTextColor(Color.BLACK);
        timerHandler.removeCallbacks(updateTimeRunnable);
    }

    private final Runnable updateTimeRunnable = new Runnable() {
        @Override
        public void run() {
            long currentElapsed = SystemClock.uptimeMillis() - startTimeMillis[activeRowIndex];
            long totalTime = totalElapsedMillis[activeRowIndex] + currentElapsed;

            int seconds = (int) (totalTime / 1000);
            int minutes = seconds / 60;
            seconds %= 60;

            tvTempos[activeRowIndex].setText(String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds));
            timerHandler.postDelayed(this, 500);
        }
    };

    private void updateVisualHighlight() {
        for (int i = 0; i < 3; i++) {
            if (i == activeRowIndex) {
                tvTempos[i].setBackgroundColor(Color.parseColor("#EEEEEE"));
            } else {
                tvTempos[i].setBackgroundColor(Color.TRANSPARENT);
            }
        }
    }

    private void setupCascadeListeners() {
        // Peso Cascata
        etPeso1.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (!isUpdating) {
                    isUpdating = true;
                    etPeso2.setText(s.toString(), false);
                    etPeso3.setText(s.toString(), false);
                    isUpdating = false;
                }
            }
        });

        etPeso2.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (!isUpdating) {
                    isUpdating = true;
                    etPeso3.setText(s.toString(), false);
                    isUpdating = false;
                }
            }
        });

        // Repetições Cascata
        etReps1.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (!isUpdating) {
                    isUpdating = true;
                    etReps2.setText(s.toString(), false);
                    etReps3.setText(s.toString(), false);
                    isUpdating = false;
                }
            }
        });

        etReps2.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (!isUpdating) {
                    isUpdating = true;
                    etReps3.setText(s.toString(), false);
                    isUpdating = false;
                }
            }
        });
    }
}
