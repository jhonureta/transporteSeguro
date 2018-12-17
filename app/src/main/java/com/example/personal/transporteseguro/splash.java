package com.example.personal.transporteseguro;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.example.personal.transporteseguro.bd.AdminSQliteOpenHelper;
import com.example.personal.transporteseguro.tablas.bienvenida;

public class splash extends AppCompatActivity {
public TextView textView;
    String estado = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //Llamana de la clase para su ejecución
        LogoLauncher logoLauncher = new LogoLauncher();
        overridePendingTransition(R.anim.zoom_back_in,R.anim.zoom_back_in);
        logoLauncher.start();
        Animation myanim = AnimationUtils.loadAnimation(this,R.anim.transaccion);
        textView = (TextView)findViewById(R.id.text);
        textView.setAnimation(myanim);
    }

    /**
     * clase privada  LogoLaunche
     * Descripción: Lo que hace es ejecutar un hilo
     */

    private class LogoLauncher extends Thread{
        public void run(){
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            consulta();
            if(estado.equals("1")){
                Intent intent = new Intent(getApplicationContext(),menuusuario.class);

                startActivity(intent);
            }else{
                if(estado.equals("2")){
                    Intent intent = new Intent(getApplicationContext(),menuTaxista.class);

                    startActivity(intent);
                }else{
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);

                    startActivity(intent);
                }

                 }



            splash.this.finish();

        }
    }

    public  void  consulta(){
        try {
            AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(this, "registro", null, 1);
            SQLiteDatabase bd = admin.getWritableDatabase();

            Cursor fila = bd.rawQuery(" SELECT " + bienvenida.CAMPO_ESTADO+  "  FROM " + bienvenida.TABLA_BIENVENIDA, null);
            if (fila.moveToFirst()) {
                estado = fila.getString(0);

            }

        }catch (Exception e){
            Toast.makeText(this, e.getMessage(),Toast.LENGTH_LONG).show();
        }

    }

}
