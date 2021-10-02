package es.dsancalm.velocimetrogps;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textVelocidad = (TextView) findViewById(R.id.valueVelocidad);
        textVelocidad.setText("24,5 km/h");
        TextView textDistancia = (TextView) findViewById(R.id.valueDistancia);
        textDistancia.setText("235 metros");
        TextView textTiempo = (TextView) findViewById(R.id.valueTiempo);
        textTiempo.setText("00:02:57");
    }
}