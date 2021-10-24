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
    private int paused = 0;

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
        float velocidad = distancia; //metros/ cada 1 s
        velocidad = velocidad * 3600; // metros / cada hora
        velocidad = velocidad / 1000; // km/h
        if (velocidad < 1.0f)
            velocidad = 0.0f;
        this.vista.setVelocidad(velocidad);
    }

    public void actualizarLocalizacion(Location ultimaLocalizacion) {
        this.ultimaLocalizacion = ultimaLocalizacion;

    }

    public void start() {
        if(this.distancia != 0 && this.paused == 0){
            this.distancia = 0;
            this.actualizarDistancia(distancia);
        }
        paused = 0;
        this.servicioLocalizacion.startGPS();
        if (this.servicioLocalizacion.isActive()) {
            this.vista.startCronometro();
        }
    }



    public void parar() {
        this.ultimaLocalizacion = null;
        this.vista.setVelocidad(0.0f);
        this.vista.pararCronometro();
        this.servicioLocalizacion.pararGPS();
    }

    public void pausar() {
        this.ultimaLocalizacion = null;
        this.vista.setVelocidad(0);
        this.vista.pausarCronometro();
        this.servicioLocalizacion.pararGPS();
        paused = 1;

    }
}
