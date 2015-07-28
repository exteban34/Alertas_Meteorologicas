package servicios;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;

import android.os.IBinder;

import com.edu.udea.sistemas.esteban.alertas_meteorologicas.R;
import com.edu.udea.sistemas.esteban.alertas_meteorologicas.db.DBAdapter;
import com.edu.udea.sistemas.esteban.alertas_meteorologicas.model.Alerta;
import com.edu.udea.sistemas.esteban.alertas_meteorologicas.model.Medicion;
import com.edu.udea.sistemas.esteban.alertas_meteorologicas.util.LeerJSON;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by SERVIDOR on 20/07/2015.
 */
public class MiServicio extends Service {

    public static String nl = System.getProperty("line.separator");
    private Timer timer = new Timer();
    private static final long UPDATE_INTERVAL = 120*1000;
    private NotificationManager mNM = null;
    private DBAdapter db = null;
    ArrayList<Alerta> alertas;





    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

        db = new DBAdapter(this);
        mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);


    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        pollForUpdates();
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }

    private void showNotification(Medicion medicion,Alerta alerta) {

        CharSequence text ="Alerta: "+ alerta.getLabel()+" Detectada"+nl+
                "se obtubo la siguiente medicion:" + nl +
                "Temperatura: " + medicion.getTemperatura() + nl +
                "Humedad: " + medicion.getHumedad() + nl +
                "Luz: " + medicion.getLuz();
        Notification notification = new Notification(R.drawable.icono3,text,
                System.currentTimeMillis());

        Intent iNotification = new Intent("com.edu.udea.sistemas.esteban.alertas_meteorologicas.ListaAlertas");
        iNotification.putExtra("NOTIFY", true);


        PendingIntent contentIntent = PendingIntent.getActivity(this,0, iNotification,
                PendingIntent.FLAG_UPDATE_CURRENT);

        notification.setLatestEventInfo(this,"Alerta: "+ alerta.getLabel()+" Detectada", text, contentIntent);
        notification.defaults |=Notification.DEFAULT_VIBRATE;
        notification.defaults |=Notification.DEFAULT_SOUND;
        mNM.notify(1, notification);


    }



    private void  pollForUpdates() {

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                comprobar_Alertas();

            }
        }, 0, UPDATE_INTERVAL);
    }



    private Medicion tomarMedicion(String url) throws JSONException {
        String result = LeerJSON.leerJSON(url);
        Medicion medicion;
        JSONObject jsonObject = new JSONObject(result);
        JSONObject jsonObject1;
        JSONArray jsonfeeds = jsonObject.getJSONArray("feeds");
        if(jsonfeeds.length()!=0) {
            jsonObject1 = jsonfeeds.getJSONObject(jsonfeeds.length() - 1);
            medicion = new Medicion(jsonObject1.getInt("entry_id"),
                    jsonObject1.getDouble("field1"),
                    jsonObject1.getDouble("field2"),
                    jsonObject1.getDouble("field3"),
                    jsonObject1.getString("created_at")
            );
            return medicion;
        }
        return null;
    }

    private void comprobar_Alertas() {

        Medicion medicion = null;
        try {
            medicion = tomarMedicion("http://api.thingspeak.com/channels/44075/feed.json");

        } catch (JSONException e) {

            e.printStackTrace();
        }

        db.open();
        alertas = db.getTodasAlertas();
        db.close();


        if (alertas != null && medicion!=null) {
            double t, h, l;
            t = medicion.getTemperatura();
            h = medicion.getHumedad();
            l = medicion.getLuz();

            for (Alerta alerta : alertas) {
                if (alerta.getActiva() == 1) {
                    if (fueraDeRango(alerta.getTemperaturaBajo(), alerta.getTemperaturaAlto(), t)) {
                        showNotification(medicion, alerta);
                    } else {
                        if (fueraDeRango(alerta.getHumedadBajo(), alerta.getHumedadAlto(), h)) {
                            showNotification(medicion, alerta);

                        } else {
                            if (fueraDeRango(alerta.getLuzBajo(), alerta.getLuzAlto(), l)) {
                                showNotification(medicion, alerta);

                            }

                        }
                    }
                }
            }
        }
    }



    private boolean fueraDeRango(double min, double max, double n) {

        boolean bandera = false;


            if ((n < min) || (n > max)) {

                return true;
            }

        return bandera;
    }

}


