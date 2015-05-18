package com.edu.udea.sistemas.esteban.alertas_meteorologicas.db;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.edu.udea.sistemas.esteban.alertas_meteorologicas.model.Alerta;

import java.util.ArrayList;

/**
 * Created by esteban on 18/05/2015.
 */
public class DBAdapter {

    static final String DATABASE_NOMBRE = "alertasdb";
    static final int DATABASE_VERSION = 1;
    static final String TAG = "DBAdapter";

    static final String TABLA_ALERTAS = "Alertas";
    static final String ID_ALERTA = "id";
    static final String TEMP_BAJO = "temp_bajo";
    static final String TEMP_ALTO = "temp_bajo";
    static final String HUMED_BAJO = "humed_bajo";
    static final String HUMED_ALTO = "humed_alto";
    static final String LUZ_BAJO = "luz_bajo";
    static final String LUZ_ALTO = "luz_alto";
    static final String FECHA_ALERTA = "fecha";

    static final String DATABASE_CREATE = "create table(id integer primary key autoincrement, " +
            "temp_bajo text" +
            "temp_alto text" +
            "humed_bajo text" +
            "humed_alto text" +
            "luz_bajo text" +
            "luz_alto text" +
            "fecha text" +
            ")";
    final Context context;
    DatabaseHelper DBHelper;
    SQLiteDatabase db;
    private Alerta alerta=null;

    public DBAdapter(Context context) {
        this.context = context;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                //	db.execSQL(DATABASE_CREATE);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS Alertas");
            onCreate(db);
        }
    }

    // ---opens the database---
    public DBAdapter open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    // ---closes the database---
    public void close() {
        DBHelper.close();
    }

    // ---retornar todas las Alertas---
    public ArrayList<Alerta> getTodasAlertas() {
        Cursor c =db.query(TABLA_ALERTAS, new String[] {TEMP_BAJO,TEMP_ALTO,
                HUMED_BAJO,HUMED_ALTO,LUZ_BAJO,LUZ_ALTO,FECHA_ALERTA,ID_ALERTA
        }, null, null, null, null, null);
        ArrayList<Alerta> alertas = new ArrayList<Alerta>();

        if (c.moveToFirst()) {
            do {
                alerta = new Alerta(Integer.valueOf(c.getString(0)),Integer.valueOf(c.getString(1)),
                        Integer.valueOf(c.getString(2)),Integer.valueOf(c.getString(3)),Integer.valueOf(c.getString(4)),
                        Integer.valueOf(c.getString(5)),c.getString(6));
                alerta.setId(Integer.valueOf(c.getString(7)));
                alertas.add(alerta);

            } while (c.moveToNext());
        }
        return alertas;

    }

    public Alerta getAlerta(int id) throws SQLException{

        Cursor c =db.query(TABLA_ALERTAS, new String[] {TEMP_BAJO,TEMP_ALTO,
                HUMED_BAJO,HUMED_ALTO,LUZ_BAJO,LUZ_ALTO,FECHA_ALERTA,ID_ALERTA
        }, ID_ALERTA + "=" + id, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        alerta = new Alerta(Integer.valueOf(c.getString(0)),Integer.valueOf(c.getString(1)),
                Integer.valueOf(c.getString(2)),Integer.valueOf(c.getString(3)),Integer.valueOf(c.getString(4)),
                Integer.valueOf(c.getString(5)),c.getString(6));
        alerta.setId(Integer.valueOf(c.getString(7)));
        return alerta;
    }



}