package es.dsancalm.velocimetrogps.services;


import static android.content.Context.LOCATION_SERVICE;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import es.dsancalm.velocimetrogps.R;

public class ServicioLocalizacion {

    private final Context mContext;

    boolean isGPSEnabled;


    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1; // 1 meter

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000; // 1 second

    // Declaring a Location Manager
    protected LocationManager locationManager;

    private final LocationListener handlerGPS;


    private boolean isActive = false;

    public ServicioLocalizacion(Context context, LocationListener handlerGPS) {
        this.mContext = context;
        this.locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
        this.handlerGPS = handlerGPS;



    }
    public void startGPS() {
        this.isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (isGPSEnabled){
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) mContext, new String[] { Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION }, 1);
            }
            else {
                locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES, handlerGPS);

                isActive = true;
            }
        }
        else {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

            alertDialog.setTitle(R.string.AjustesGPS);

            alertDialog.setMessage(R.string.GPSNoAjustes);

            alertDialog.setPositiveButton(R.string.Ajustes, (dialog, which) -> {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            });

            alertDialog.setNegativeButton(R.string.Cancelar, (dialog, which) -> {
                Toast.makeText(mContext, R.string.GPSNoActivo, Toast.LENGTH_SHORT).show();
                dialog.cancel();
            });

            // Showing Alert Message
            alertDialog.show();
        }
    }


    public void pararGPS() {
        if (locationManager != null) {
            locationManager.removeUpdates(handlerGPS);
            isActive = false;
        }
    }

    public boolean isActive() {

        return isActive;
    }



}