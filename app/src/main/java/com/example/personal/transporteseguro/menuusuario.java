package com.example.personal.transporteseguro;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.personal.transporteseguro.bd.AdminSQliteOpenHelper;
import com.example.personal.transporteseguro.tablas.Contac;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class menuusuario extends AppCompatActivity {

    ImageView btn_panico;//VARIABLE DE TIPO IMAGEVIEW PARA EL BOTON DE PANICO
    String numero="";//VARIABLE DE TIPO STRING  ALMACENARA EL NUMERO DE  LA CONSULTA  A LA BD HECHA EN SQLITE
    String operadora="";//VARIABLE DE TIPO STRING ALMACENARA LA OPERADORA DE LA CONSULTA A LA BD HECHA EN SQLITE
    TextView close;//VARIABLE DE TIPO TEXVIEW PARA CERRAR LA PANTALLA


    //VARIABLES PARA CONSUMIR EL SERVICIO WEB DEL SMS
    private static final String metodo = "EnviarSms";
    private static final String namespace = "http://tempuri.org/";
    private static final String accionSoap = "http://tempuri.org/EnviarSms";
    private static final String url = "http://204.128.252.66/RapperSms/service.asmx";
    HttpTransportSE transporte;
    SoapObject request;
    SoapSerializationEnvelope sobre;
    SoapPrimitive resultado;
    //VARIABLES PARA CONSUMIR EL SERVICIO WEB DEL SMS

    ImageView escoger,carrerEjec, video, ocupac;//VARIABLES DE TIPO IMAGEVIEW DE LAS OPCIONES DEL MENU


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuusuario);
        try {
            //ENLAZANDO LAS VARIABLES DECLARADAS AL PRINCIPIO DE LA CLASE CON LOS ID DE OBJETOS DECLARADOS EN LA INTERFAZ
            btn_panico = (ImageView) findViewById(R.id.boton_panico);
            escoger = (ImageView) findViewById(R.id.carrera);
            carrerEjec = (ImageView) findViewById(R.id.card_ejecutivo);
            ocupac = (ImageView)findViewById(R.id.ocupacion);
            video = (ImageView)findViewById(R.id.vigilar);
            close = (TextView)findViewById(R.id.cerrar);

            escoger.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent intent = new Intent(getApplicationContext(), carreraEjecutiva.class);
                        intent.putExtra("valor", "1");
                        startActivity(intent);
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });

            carrerEjec.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent intent = new Intent(getApplicationContext(), carreraEjecutiva.class);
                        intent.putExtra("valor", "0");
                        startActivity(intent);
                    } catch (Exception e){
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                }
            });


            ocupac.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                    startActivity(intent);
                }
            });


            video.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    alerta();
                }
            });


            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    moveTaskToBack(true);
                }
            });




            //METODO ONCLICK IMPLENTA EL CONSUMO DEL SERVICIO WEB
            btn_panico.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                consulta();

                String cadena = "";
                try{
                    request = new SoapObject(namespace, metodo);				Log.e("JMMC", "SOAP-3");
                    request.addProperty("numero", numero);
                    request.addProperty("operadora", operadora);
                    request.addProperty("mensaje","ALERTA LA UNIDAD GRW1385 PRESIONO EL BOTON DE PANICO, POR FAVOR VERIFICAR !! **LLAME A LOS SIGUIENTES NUMEROS 099552871 - 0985126512 **");

                    //request.getProperty("mensaje");
                    sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11); 	Log.e("JMMC","SOAP-4");
                    sobre.dotNet = true;										Log.e("JMMC","SOAP-5");
                    sobre.setOutputSoapObject(request);						    Log.e("JMMC","SOAP-6");

                    // Habilitar la comunicacion con el
                    // Web Services desde el Activity Principal
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);

                    transporte = new HttpTransportSE(url);						Log.e("JMMC","SOAP-7");
                    transporte.call(accionSoap, sobre);						    Log.e("JMMC", "SOAP-8");
                    resultado = (SoapPrimitive) sobre.getResponse();			Log.e("JMMC","SOAP-9");
                    cadena = resultado.toString();								Log.e("JMMC","SOAP-10");


                    //Log.e("Mensaje: ",cadena);
                    if(cadena.equals("true")){
                        correcto();
                    }


                }catch(Exception e){Log.e("JZH","SOAP-"+e.getMessage());}

                }
            });
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void alerta(){

        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("¡Atención!")
                .setContentText("Trabajando en este módulo")
                .show();

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

    //REALIZANDO LA CONSULTA A LA  BD DE LA OPERADORA Y CELULAR DEL PARIENTE
    public  void  consulta(){
        try {
            AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(this, "pariente", null, 1);
            SQLiteDatabase bd = admin.getWritableDatabase();

            Cursor fila = bd.rawQuery(" SELECT " + Contac.CAMPO_CELULAR+", "+Contac.CAMPO_OPERADORA +"  FROM " + Contac.TABLA_PARIENTE, null);
            if (fila.moveToFirst()) {
                numero = fila.getString(0);
                operadora = fila.getString(1);

            }

        }catch (Exception e){
            Toast.makeText(this, e.getMessage(),Toast.LENGTH_LONG).show();
        }

    }
    //FIN REALIZANDO LA CONSULTA A LA  BD DE LA OPERADORA Y CELULAR DEL PARIENTE***

    //TOAST PERSONALIZADO DE PULGAR ARRIBA AL ENVIAR NOTIFICACION DE SMS
    public void correcto() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View customToast = inflater.inflate(R.layout.toas_personalizado, null);
        TextView txt = (TextView) customToast.findViewById(R.id.txttoas);
        txt.setText("Alerta enviada");
        Toast toast = new Toast(this);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(customToast);
        toast.show();
    }





}
