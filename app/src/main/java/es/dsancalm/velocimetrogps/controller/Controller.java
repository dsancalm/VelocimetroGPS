package es.dsancalm.velocimetrogps.controller;


import android.location.Location;


import es.dsancalm.velocimetrogps.handler.HandlerGPS;
import es.dsancalm.velocimetrogps.services.ServicioLocalizacion;
import es.dsancalm.velocimetrogps.view.vistaApp;

public class Controller {

    private Location ultimaLocalizacion;
    private float distancia;
    private final vistaApp vista;
    private final ServicioLocalizacion servicioLocalizacion;

    public Controller(vistaApp vistaApp) {

        this.vista = vistaApp;
        HandlerGPS handlerGPS = new HandlerGPS(this);
        this.servicioLocalizacion = new ServicioLocalizacion(vista, handlerGPS);
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
        this.servicioLocalizacion.startGPS();
        if (this.servicioLocalizacion.isActive()) {
            this.vista.startCronometro();
        }
    }



    public void parar() {
        this.distancia = 0;
        this.ultimaLocalizacion = null;
        this.actualizarDistancia(distancia);
        this.vista.pararCronometro();
        this.servicioLocalizacion.pararGPS();


    }

    public void pausar() {
        this.ultimaLocalizacion = null;
        this.vista.setVelocidad(0);
        this.vista.pausarCronometro();
        this.servicioLocalizacion.pararGPS();

    }
}
