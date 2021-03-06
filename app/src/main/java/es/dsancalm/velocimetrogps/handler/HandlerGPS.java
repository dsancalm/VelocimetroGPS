package es.dsancalm.velocimetrogps.handler;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;


import androidx.annotation.NonNull;

import es.dsancalm.velocimetrogps.controller.Controller;

public class HandlerGPS implements LocationListener {

    Controller controller;

    public HandlerGPS(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        Location ultimaLocalizacion = controller.obtenerUltimaLocalizacion();

        if (ultimaLocalizacion != null) {
            float distancia = ultimaLocalizacion.distanceTo(location);
            controller.actualizarDistancia(distancia);
        }
        else {

            controller.actualizarDistancia(0);
        }
        controller.actualizarLocalizacion(location);

    }
    @Override
    public void onProviderDisabled(String provider){

    }

    @Override
    public void onProviderEnabled(String provider){

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras){

    }

}