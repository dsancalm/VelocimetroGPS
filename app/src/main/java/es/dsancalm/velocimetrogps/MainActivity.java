package es.dsancalm.velocimetrogps;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Location;
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
    final Location[] location1 = {null, null};
    float distancia = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textVelocidad = (TextView) findViewById(R.id.valueVelocidad);
        textVelocidad.setText("24,5 km/h");
        GPSTracker mGPS = new GPSTracker(this);
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

                if(mGPS.canGetLocation() ){
                    if (location1[0] == null){
                        location1[0] = mGPS.getLocation();
                    }
                    else {
                        location1[1] = mGPS.getLocation();
                        float distanciaLocal = location1[0].distanceTo(location1[1]);
                        float velocidad = (distanciaLocal*3600)/1000;
                        distancia += distanciaLocal;
                        location1[0] = location1[1];
                        textVelocidad.setText(String.valueOf(distanciaLocal));
                        textDistancia.setText(String.valueOf(distancia));
                    }
                }else{
                    textDistancia.setText("Unabletofind");
                }
            }
        });
    }


}