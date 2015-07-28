package com.edu.udea.sistemas.esteban.alertas_meteorologicas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.edu.udea.sistemas.esteban.alertas_meteorologicas.db.DBAdapter;
import com.edu.udea.sistemas.esteban.alertas_meteorologicas.model.Alerta;

/**
 * Created by Esteban on 7/20/15.
 */
public class EditarAlerta extends Activity{
    DBAdapter db = new DBAdapter(this);
    TextView t1;
    int idAlerta;
    Alerta alerta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layaout_editar_alerta);
        idAlerta=getIntent().getIntExtra("idAlerta",0);
        t1 = (TextView) findViewById(R.id.textView10);
        buscarAlertaYLlenarCampos();

    }

    public void buscarAlertaYLlenarCampos(){
        db.open();
        alerta=db.getAlerta(idAlerta);
        db.close();
        t1.setText("Nombre: "+alerta.getLabel()+"\n"+
                   "Temperatura: "+alerta.getTemperaturaBajo()+" - "+alerta.getTemperaturaAlto()+"\n"+
                   "Humedad: "+alerta.getHumedadBajo()+" - "+alerta.getHumedadAlto()+"\n"+
                   "Luz: "+alerta.getLuzBajo()+" - "+alerta.getLuzAlto()+"\n");

    }
    public void eliminar(View view){
        db.open();
        db.eliminarAlerta(idAlerta);
        db.close();
        Toast.makeText(this,"Alerta eliminada correctamente",Toast.LENGTH_SHORT).show();
        Intent i = new Intent("com.edu.udea.sistemas.esteban.alertas_meteorologicas.ListaAlertas");
        startActivity(i);

    }


}
