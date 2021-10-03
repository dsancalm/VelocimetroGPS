package es.dsancalm.velocimetrogps.controller;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Chronometer;

import androidx.core.app.ActivityCompat;

import es.dsancalm.velocimetrogps.handler.HandlerGPS;
import es.dsancalm.velocimetrogps.services.ServicioLocalizacion;
import es.dsancalm.velocimetrogps.view.vistaApp;

public class Controller {

    private Location ultimaLocalizacion;
    private float distancia;
    private vistaApp vista;
    private ServicioLocalizacion servicioLocalizacion;
    private HandlerGPS handlerGPS;
    public Controller(vistaApp vistaApp) {

        this.vista = vistaApp;
        this.handlerGPS = new HandlerGPS(this);
    }

    public Location obtenerUltimaLocalizacion() {

        return ultimaLocalizacion;
    }

    public void actualizarDistancia(float distancia) {
        this.distancia += distancia;
        this.vista.setDistancia(this.distancia);
        float velocidad = distancia; //metros/ cada 3 s
        velocidad = velocidad * 1200; // metros / cada hora
        velocidad = velocidad / 1000; // km/h
        this.vista.setVelocidad(velocidad);
    }

    public void actualizarLocalizacion(Location ultimaLocalizacion) {
        this.ultimaLocalizacion = ultimaLocalizacion;

    }

    public void start() {

            this.servicioLocalizacion = new ServicioLocalizacion(vista, handlerGPS);
            if (this.servicioLocalizacion.isActive()){
                this.vista.startCronometro();
            }
        }



    public void parar() {
        this.distancia = 0;
        this.actualizarDistancia(distancia);
        this.vista.pararCronometro();
        this.servicioLocalizacion.pararGPS();


    }

    public void pausar() {
        this.vista.pausarCronometro();
        this.servicioLocalizacion.pararGPS();

    }
}
