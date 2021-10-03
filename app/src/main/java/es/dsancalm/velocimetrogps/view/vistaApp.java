package es.dsancalm.velocimetrogps.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.math.RoundingMode;

import es.dsancalm.velocimetrogps.R;
import es.dsancalm.velocimetrogps.controller.Controller;

public class vistaApp extends AppCompatActivity {

    private long lastPause;
    private boolean isRunning = false;
    private Controller controller;
    private TextView textVelocidad;
    private TextView textDistancia;
    private Chronometer chronoTiempo;
    private Button buttonComenzar;


    private Button buttonParar;
    private Button buttonPausar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textVelocidad = (TextView) findViewById(R.id.valueVelocidad);
        textDistancia = (TextView) findViewById(R.id.valueDistancia);
        chronoTiempo = (Chronometer) findViewById(R.id.valueTiempo);

        buttonComenzar = (Button) findViewById(R.id.buttonStart);
        buttonParar = (Button) findViewById(R.id.buttonStop);
        buttonPausar = (Button) findViewById(R.id.buttonPause);

        this.controller = new Controller(this);

        buttonComenzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.start();
            }
        });
        buttonParar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.parar();
            }
        });
        buttonPausar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.pausar();
            }
        });
    }

    public void pararCronometro(){
        isRunning = false;
        chronoTiempo.stop();
        buttonComenzar.setEnabled(true);
        buttonPausar.setEnabled(false);
        buttonParar.setEnabled(false);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    public void startCronometro(){
        if (isRunning){
            chronoTiempo.setBase(chronoTiempo.getBase() + SystemClock.elapsedRealtime() - lastPause);
        }
        else {
            chronoTiempo.setBase(SystemClock.elapsedRealtime());
            isRunning = true;
        }
        chronoTiempo.start();
        buttonComenzar.setEnabled(false);
        buttonPausar.setEnabled(true);
        buttonParar.setEnabled(true);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    }
    public void pausarCronometro(){
        lastPause = SystemClock.elapsedRealtime();
        buttonComenzar.setEnabled(true);
        buttonPausar.setEnabled(false);
        buttonParar.setEnabled(true);
        chronoTiempo.stop();

    }


    public void setDistancia(float distancia) {
        BigDecimal bigDecimal = new BigDecimal(Float.toString(distancia));
        bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_UP);
        float distanciaRedondeada = bigDecimal.floatValue();
        this.textDistancia.setText(String.valueOf(distanciaRedondeada) + " m");
    }

    public void setVelocidad(float velocidad) {
        BigDecimal bigDecimal = new BigDecimal(Float.toString(velocidad));
        bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_UP);
        float velocidadRedondeada = bigDecimal.floatValue();
        this.textVelocidad.setText(String.valueOf(velocidadRedondeada) + " km/h");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,
                permissions,
                grantResults);


            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                controller.start();
            } else {
                Toast.makeText(this, "La aplicacion no funcionara sin la ubicacion activada", Toast.LENGTH_SHORT).show();
            }

    }


}