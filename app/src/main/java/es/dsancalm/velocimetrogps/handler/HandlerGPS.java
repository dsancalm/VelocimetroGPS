package es.dsancalm.velocimetrogps.handler;

import android.location.Location;
import android.location.LocationListener;


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

}