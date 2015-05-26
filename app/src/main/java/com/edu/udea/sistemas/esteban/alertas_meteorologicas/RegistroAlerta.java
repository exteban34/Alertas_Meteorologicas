package com.edu.udea.sistemas.esteban.alertas_meteorologicas;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.edu.udea.sistemas.esteban.alertas_meteorologicas.db.DBAdapter;
import com.edu.udea.sistemas.esteban.alertas_meteorologicas.model.Alerta;

/**
 * Created by esteban on 28/04/2015.
 */
public class RegistroAlerta extends Activity {

    DBAdapter db = new DBAdapter(this);
    EditText edTempBajo;
    EditText edTempAlto;
    EditText edHumedBajo;
    EditText edHumedAlto;
    EditText edLuzBajo;
    EditText edLuzAlto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layaout_gererar_alerta);
        edTempBajo = (EditText) findViewById(R.id.edit_text_temp_bajo);
        edTempAlto = (EditText) findViewById(R.id.edit_text_temp_alto);
        edHumedBajo = (EditText) findViewById(R.id.edit_text_humed_bajo);
        edHumedAlto = (EditText) findViewById(R.id.edit_text_humed_alto);
        edLuzBajo = (EditText) findViewById(R.id.edit_text_luz_bajo);
        edLuzAlto = (EditText) findViewById(R.id.edit_text_luz_alto);
    }

    public void registrar (View view){
        db.open();
        /**Alerta alerta = new Alerta(Integer.valueOf(edTempBajo.getText().toString()),
                Integer.valueOf(edTempAlto.getText().toString()),
                Integer.valueOf(edHumedBajo.getText().toString()),
                Integer.valueOf(edHumedAlto.getText().toString()),
                Integer.valueOf(edLuzBajo.getText().toString()),
                Integer.valueOf(edLuzAlto.getText().toString()),
                "26/05/2015");

                **/
        db.insertarAlerta(Integer.valueOf(edTempBajo.getText().toString()),
                Integer.valueOf(edTempAlto.getText().toString()),
                Integer.valueOf(edHumedBajo.getText().toString()),
                Integer.valueOf(edHumedAlto.getText().toString()),
                Integer.valueOf(edLuzBajo.getText().toString()),
                Integer.valueOf(edLuzAlto.getText().toString()),
                "26/05/2015");
        db.close();

    }
}
