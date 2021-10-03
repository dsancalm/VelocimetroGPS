package es.dsancalm.velocimetrogps.handler;

import android.location.Location;
import android.location.LocationListener;
import android.util.Log;

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
            Log.d("distancia", String.valueOf(distancia));
            Log.d("latitud guardada", String.valueOf(ultimaLocalizacion.getLatitude()));
            Log.d("longitud guardada", String.valueOf(ultimaLocalizacion.getLongitude()));
            Log.d("latitud nueva", String.valueOf(location.getLatitude()));
            Log.d("longitud nyueva", String.valueOf(location.getLongitude()));
            controller.actualizarDistancia(distancia);
            controller.actualizarLocalizacion(location);
        }
        else {

            controller.actualizarDistancia(0);
            controller.actualizarLocalizacion(location);
        }

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }
}