package com.edu.udea.sistemas.esteban.alertas_meteorologicas;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.edu.udea.sistemas.esteban.alertas_meteorologicas.db.DBAdapter;

/**
 * Created by esteban on 28/04/2015.
 */
public class RegistroAlerta extends Activity {

    DBAdapter db = new DBAdapter(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layaout_gererar_alerta);
    }

    public void registrar (View view){
        db.open();

        db.close();

    }
}
