package es.dsancalm.velocimetrogps;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Chronometer chronoTiempo;
    private long lastPause;
    private boolean isRunning = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textVelocidad = (TextView) findViewById(R.id.valueVelocidad);
        textVelocidad.setText("24,5 km/h");
        TextView textDistancia = (TextView) findViewById(R.id.valueDistancia);
        textDistancia.setText("235 metros");
        chronoTiempo = (Chronometer) findViewById(R.id.valueTiempo);

        Button botonComenzar = (Button) findViewById(R.id.buttonStart);
        botonComenzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isRunning){
                    chronoTiempo.setBase(chronoTiempo.getBase() + SystemClock.elapsedRealtime() - lastPause);
                }
                else {
                    chronoTiempo.setBase(SystemClock.elapsedRealtime());
                    isRunning = true;
                }
                chronoTiempo.start();

            }
        });
        Button buttonPausar = (Button) findViewById(R.id.buttonPause);
        buttonPausar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lastPause = SystemClock.elapsedRealtime();
                chronoTiempo.stop();

            }
        });
        Button buttonParar = (Button) findViewById(R.id.buttonStop);
        buttonParar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isRunning = false;
                chronoTiempo.stop();

            }
        });
        chronoTiempo.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                long timeElapsed = SystemClock.elapsedRealtime() - chronometer.getBase();
                textDistancia.setText(String.valueOf(timeElapsed));
            }
        });
    }


}