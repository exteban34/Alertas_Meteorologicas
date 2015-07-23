package com.edu.udea.sistemas.esteban.alertas_meteorologicas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.edu.udea.sistemas.esteban.alertas_meteorologicas.db.DBAdapter;
import com.edu.udea.sistemas.esteban.alertas_meteorologicas.model.Alerta;

/**
 * Created by Esteban on 7/20/15.
 */
public class EditarAlerta extends Activity{
    DBAdapter db = new DBAdapter(this);
    EditText tempBajo;
    EditText tempAlto;
    EditText humedBajo;
    EditText humedAlto;
    EditText luzBajo;
    EditText luzAlto;
    EditText label;
    RadioButton acvtivar;
    int idAlerta;
    Alerta alerta;

    private static final int ENABLE=1;
    private static final int DISABLE=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layaout_editar_alerta);
        idAlerta=getIntent().getIntExtra("idAlerta",0);
        tempBajo = (EditText) findViewById(R.id.tempBj);
        tempAlto = (EditText) findViewById(R.id.tempAL);
        humedBajo = (EditText) findViewById(R.id.humBj);
        humedAlto = (EditText) findViewById(R.id.humAL);
        luzBajo = (EditText) findViewById(R.id.luzBj);
        luzAlto = (EditText) findViewById(R.id.luzAl);
        label = (EditText) findViewById(R.id.label);
        acvtivar = (RadioButton)findViewById(R.id.radioButtonEd);
        buscarAlertaYLlenarCampos();

    }

    public void buscarAlertaYLlenarCampos(){
        db.open();
        alerta=db.getAlerta(idAlerta);
        db.close();
        label.setText(alerta.getLabel());
        /*tempBajo.setText( alerta.getTemperaturaBajo());
        tempAlto.setText(alerta.getTemperaturaAlto());
        humedBajo.setText(alerta.getHumedadBajo());
        humedAlto.setText(alerta.getTemperaturaAlto());
        luzBajo.setText(alerta.getLuzBajo());
        luzAlto.setText(alerta.getLuzAlto());
        */

        if(alerta.getActiva()== ENABLE){
            acvtivar.setChecked(true);
        }else{
            acvtivar.setChecked(false);
        }

    }

    public void actulizar(View view){
        db.open();
        if(acvtivar.isChecked()){
            db.editarAlerta(idAlerta,
                    Integer.valueOf(tempBajo.getText().toString()),
                    Integer.valueOf(tempAlto.getText().toString()),
                    Integer.valueOf(humedBajo.getText().toString()),
                    Integer.valueOf(humedAlto.getText().toString()),
                    Integer.valueOf(luzBajo.getText().toString()),
                    Integer.valueOf(luzAlto.getText().toString()),
                    "dd/mm/yyyy",
                    label.getText().toString(),
                    ENABLE);

        }else {
            db.editarAlerta(idAlerta,
                    Integer.valueOf(tempBajo.getText().toString()),
                    Integer.valueOf(tempAlto.getText().toString()),
                    Integer.valueOf(humedBajo.getText().toString()),
                    Integer.valueOf(humedAlto.getText().toString()),
                    Integer.valueOf(luzBajo.getText().toString()),
                    Integer.valueOf(luzAlto.getText().toString()),
                    "dd/mm/yyyy",
                    label.getText().toString(),
                    DISABLE);

        }
        db.close();
        Toast.makeText(this,"Alerta actualizada correctamente",Toast.LENGTH_SHORT).show();
        onBackPressed();

    }

    public void eliminar(View view){
        db.open();
        db.eliminarAlerta(idAlerta);
        db.close();
        Toast.makeText(this,"Alerta eliminada correctamente",Toast.LENGTH_SHORT).show();
        Intent i = new Intent("com.edu.udea.sistemas.esteban.alertas_meteorologicas.ListaAlertas");
        startActivity(i);

    }

    /*public  void acrivarEd(View view){
        acvtivar.toggle();

    }*/
}
