package com.edu.udea.sistemas.esteban.alertas_meteorologicas;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            /**
             * Proceso para copiar la base de datos
             */
            String destPath = "/data/data/" + getPackageName() + "/databases";
            File f = new File(destPath);
            if (!f.exists()) {
                f.mkdirs();
                f.createNewFile();

                // ---copy the db from the assets folder into
                // the databases folder---

                CopyDB(getBaseContext().getAssets().open("databaseAlertas.sqlite"),
                        new FileOutputStream(destPath + "/alertasbd"));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clickRegistrarAlerta(View view){
        Intent intent=new Intent("com.edu.udea.sistemas.esteban.alertas_meteorologicas.RegistroAlerta");
        startActivity(intent);
    }
    public void clickVerAlertas(View view){
        Intent intent=new Intent("com.edu.udea.sistemas.esteban.alertas_meteorologicas.ListaAlertas");
        startActivity(intent);
    }



    public void CopyDB(InputStream inputStream, OutputStream outputStream)
            throws IOException {
        // ---copy 1K bytes at a time---
        byte[] buffer = new byte[1024];

        int length;

        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);

        }

        inputStream.close();
        outputStream.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
