package com.example.personal.transporteseguro;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class menuTaxista extends AppCompatActivity {


    ImageView img1, img2,img3,img4;// DECLARANDO VARIABLES DE TIPO IMAGEVIEW
    TextView close;//DECLARANDO VARIABLE DE TIPO TEXVIEW

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_taxista);
        //ENLAZANDO LAS VARIABLES DECLARADAS AL PRINCIPIO DE LA CLASE CON LOS ID DE OBJETOS DECLARADOS EN LA INTERFAZ
        close = (TextView)findViewById(R.id.cerrar);
        img1 = (ImageView)findViewById(R.id.carreras);
        img2 = (ImageView)findViewById(R.id.mantenimiento);
        img3 = (ImageView)findViewById(R.id.auxilio);
        img4 = (ImageView)findViewById(R.id.reportes);
        //FIN ENLAZANDO LAS VARIABLES DECLARADAS AL PRINCIPIO DE LA CLASE CON LOS ID DE OBJETOS DECLARADOS EN LA INTERFAZ***

        //METODO ONCLICK QUE LLAMA ALERTA QUE DICE 'TRABAJANDO EN ESTE MODULO'
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    alerta();
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    alerta();
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    alerta();
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    alerta();
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    moveTaskToBack(true);
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        });
        //FIN METODO ONCLICK QUE LLAMA ALERTA QUE DICE 'TRABAJANDO EN ESTE MODULO'

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //preventing default implementation previous to android.os.Build.VERSION_CODES.ECLAIR

                        moveTaskToBack(true);
                                   return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    //ALERTA SUAVE QUE MANDA UNA ALERTA DICIENTO TRABAJANDO EN ESTE MODULO
    public void alerta(){

        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("¡Atención!")
                .setContentText("Trabajando en este módulo")
                .show();

    }


}
