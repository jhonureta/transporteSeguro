package com.example.personal.transporteseguro;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.personal.transporteseguro.bd.AdminSQliteOpenHelper;
import com.example.personal.transporteseguro.tablas.Contac;
import com.example.personal.transporteseguro.tablas.Operadora;
import com.example.personal.transporteseguro.tablas.Parentesco;
import com.example.personal.transporteseguro.tablas.bienvenida;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class contacto extends AppCompatActivity {
    Spinner spnc1, spnc2;//SPINER ALMACENA LA OPERADORA Y TIPO DE PERSONA
    //VARIABLES DATOS DE PERSONALES
    EditText nombre;
    EditText apellido;
    EditText correo;
    EditText celular;
    EditText telefono;
    //FIN VARIABLES DATOS PERSONALES****

    //VARIABLES PARA OBTENER LA POSICION DEL LOS DATOS QUE TENDRAN LOS SPINNER
    int value_operadora;
    int value_persona;
    //FIN VARIABLES PARA OBTENER LA POSICION DEL LOS DATOS QUE TENDRAN LOS SPINNER******


    //VARIABLES ESTATICAS LA CUAL CONTENDRAN LA RUTA DE LA IMAGEN
    private static final String CARPETA_PRINCIPAL = "misImagenesSecureTaxi/";//directorio principal
    private static final String CARPETA_IMAGEN = "imagenes";//carpeta donde se guardan las fotos
    private static final String DIRECTORIO_IMAGEN = CARPETA_PRINCIPAL + CARPETA_IMAGEN;//ruta carpeta de directorios
    //FIN VARIABLES ESTATICAS LA CUAL CONTENDRAN LA RUTA DE LA IMAGEN*********


    private String path;//almacena la ruta de la imagen
    File fileImagen;//VARIABLE DE TIPO FILE  PARA BUSCAR LA IMAGEN DE LA GALRIA
    Bitmap bitmap;//VARIABLE DE TIPO BITMAP

    private final int MIS_PERMISOS = 100;//VARIABLE PARA PEDIR PERMISO

    //VARIABLE CON LOS CODIGO PARA OBTENER LA FOTO TOMADA O LA IMAGEN SELECCIONADA
    private static final int COD_SELECCIONA = 10;
    private static final int COD_FOTO = 20;
    //FIN VARIABLE CON LOS CODIGO PARA OBTENER LA FOTO TOMADA O LA IMAGEN SELECCIONADA


    Button botonRegistro;//VARIABLE DE TIPO BOTON PARA REGISTRAR LOS DATOS DEL USUARIO
    ImageView imgFoto;//VARIABLE DE TIPO IMAGEN PARA PONER UNA FOTO
    String val_oper = "",val_per="";//VARIABLE QUE AMACENARAN EL TIPO DE OPERADORA Y TIPO DE PARENTESCO


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto);

        //ENLANSANDO LAS  VARIABLES DECLARADAS AL PRINCIPIO DE LA CLASE CON EL ID DE LOS OBJETOS DECLARADOS EN LA INTERFAZ
        spnc1 = (Spinner)findViewById(R.id.spinner1);
        spnc2 = (Spinner)findViewById(R.id.spinner2);
        nombre = (EditText)findViewById(R.id.txt_nombre);
        apellido = (EditText)findViewById(R.id.txt_apellido);
        correo = (EditText)findViewById(R.id.txt_email);
        botonRegistro = (Button)findViewById(R.id.btnRegistrar);
        celular = (EditText)findViewById(R.id.txt_celular);
        telefono = (EditText)findViewById(R.id.txt_telefono);
        imgFoto = (ImageView)findViewById(R.id.imagenusuario);
        //FIN ENLANSANDO LAS  VARIABLES DECLARADAS AL PRINCIPIO DE LA CLASE CON EL ID DE LOS OBJETOS DECLARADOS EN LA INTERFAZ***


        //SOLICITADO PERMISOS PARA VERSIONES SUPERIORES ALAS 5.2.1 ANDROID
        if(solicitaPermisosVersionesSuperiores()){
            imgFoto.setEnabled(true);
        }else{
            imgFoto.setEnabled(false);
        }
        //FIN SOLICITADO PERMISOS PARA VERSIONES SUPERIORES ALAS 5.2.1 ANDROID***

        try {


            //LLENANDO EL SPINER CON LAS OPERADORAS GUARDADAS EN SQLITE
            AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(this, "operadora", null, 1);
            SQLiteDatabase bd = admin.getWritableDatabase();

            Cursor cur = bd.rawQuery(" SELECT " + Operadora.CAMPO_NOMBRE + " AS '_id'  FROM " + Operadora.TABLA_OPERADORA, null);
            int[] ceduDoc = new int[]{android.R.id.text1};
            String[] nomDoc = new String[]{"_id"};
            SimpleCursorAdapter scAdap = new SimpleCursorAdapter(
                    this,
                    android.R.layout.simple_spinner_item,
                    cur,
                    nomDoc,
                    ceduDoc,
                    0);
            scAdap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnc1.setAdapter(scAdap);
            spnc1.getAdapter();

            //METODO ONCLICK PARA OBTENER LA POCISIÓN DEL SPINNER
            spnc1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {

                        SQLiteCursor item = (SQLiteCursor) parent.getItemAtPosition(position);

                        value_operadora = item.getPosition();
                        val_oper= String.valueOf(item.getString(0));
                    }catch (Exception e){

                    }


                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });



            //LLENANDO EL SPINER CON  EL PARENTESCO DE LAS OPERADORAS GUARDADAS EN SQLITE
            AdminSQliteOpenHelper admin_ = new AdminSQliteOpenHelper(this, "parentesco", null, 1);
            SQLiteDatabase bd_ = admin_.getWritableDatabase();

            Cursor cur_ = bd_.rawQuery(" SELECT " + Parentesco.CAMPO_NOMBRE + " AS '_id'  FROM " + Parentesco.TABLA_PARENTESCO, null);
            int[] ceduDoc_ = new int[]{android.R.id.text1};
            String[] nomDoc_ = new String[]{"_id"};
            SimpleCursorAdapter scAdap_ = new SimpleCursorAdapter(
                    this,
                    android.R.layout.simple_spinner_item,
                    cur_,
                    nomDoc_,
                    ceduDoc_,
                    0);
            scAdap_.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnc2.setAdapter(scAdap_);
            spnc2.getAdapter();

            //METODO ONCLICK PARA OBTENER LA POCISIÓN DEL SPINNER
            spnc2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {

                        SQLiteCursor item = (SQLiteCursor) parent.getItemAtPosition(position);

                        value_persona = item.getPosition();
                        val_per= String.valueOf(item.getString(0));
                    }catch (Exception e){
                        //Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }


                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });






            //METODO ONCLICK PARA ESCOGER EL TIPO DE FOTO
            imgFoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                mostrarDialogOpciones();


                }
            });


            //METODO ONCLICK PARA HACER EL REGISTRO DEL CONTACTO
            botonRegistro.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (nombre.getText().toString().equals("") || apellido.getText().toString().equals("") ||
                            correo.getText().toString().equals("") ||
                            celular.getText().toString().equals("") || value_operadora == 0 ||
                            telefono.getText().toString().equals("") || value_persona == 0) {


                            alerta_vacio();

                    } else {

                        if (isEmailValid(correo.getText().toString())==false) {

                                alerta();

                        } else {

                              if(celular.getText().toString().length()<=9){

                                  celular();
                              }else{



                                      AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(getApplicationContext(), "pariente", null, 1);
                                      SQLiteDatabase db = admin.getWritableDatabase();

                                      ContentValues values = new ContentValues();

                                      values.put(Contac.CAMPO_NOMBRE, nombre.getText().toString());
                                      values.put(Contac.CAMPO_APELLIDO, apellido.getText().toString());
                                      values.put(Contac.CAMPO_CORREO, correo.getText().toString());
                                      values.put(Contac.CAMPO_CELULAR, celular.getText().toString());
                                      values.put(Contac.CAMPO_OPERADORA, val_oper);
                                      values.put(Contac.CAMPO_TELEFONO_FIJO, telefono.getText().toString());
                                      values.put(Contac.CAMPO_PARENTESCO, val_per);


                                      Long idResultante = db.insert(Contac.TABLA_PARIENTE, null, values);
                                      if (idResultante > 0) {

                                          registrar();
                                          Intent intent = new Intent(getApplicationContext(), menuusuario.class);
                                          startActivity(intent);
                /**/
                                      }

                                  }
                              }


                    }


                }
            });


        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    //METODO ONCLICK PARA HACER EL REGISTRO DEL CONTACTO******




    //METODO PARA REGISTRAR LA PRIMERA VEZ QUE EL USUARIO INICIA LA APP Y NO APARESCA REGISTRAR 2 VECES
    public void registrar(){
        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(this, "registro", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(bienvenida.CAMPO_ESTADO, "1");

        Long idResultante = db.insert(bienvenida.TABLA_BIENVENIDA, null, values);
        if (idResultante > 0) {

            Intent intent = new Intent(this, contacto.class);
            startActivity(intent);

        }
    }
    //FIN METODO PARA REGISTRAR LA PRIMERA VEZ QUE EL USUARIO INICIA LA APP Y NO APARESCA REGISTRAR 2 VECES****



    //ALERTA SUAVE NOS AVISA SIN UN CAMPO ESTA VACIO
    public void alerta_vacio(){
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("¡Alerta!")
                .setContentText("Al menos un campo vacio")
                .show();
    }
    //ALERTA SUAVE NOS AVISA SIN UN CAMPO ESTA VACIO**


    //ALERTA SUAVE NOS AVISA SI EL EMAIL ES INVALIDO
    public void alerta(){

        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("¡Alerta!")
                .setContentText("Email Invalido")
                .show();
    }

    //FIN ALERTA SUAVE NOS AVISA SI EL EMAIL ES INVALIDO****


    //ALERTA SUAVE NOS AVISA SI EL CELULAR ES INVALIDO
    public void celular(){

        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("¡Alerta!")
                .setContentText("Celular Invalido")
                .show();
    }
    //FIN ALERTA SUAVE NOS AVISA SI EL EMAIL ES INVALIDO***


    //METODO ESTATICO PARA VALIDAR EL EMAIL
    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }
    //FIN METODO ESTATICO PARA VALIDAR EL EMAIL**



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //preventing default implementation previous to android.os.Build.VERSION_CODES.ECLAIR

            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    private void mostrarDialogOpciones() {
        final CharSequence[] opciones={"Tomar Foto","Elegir de Galeria","Cancelar"};
        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Elige una Opción");
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("Tomar Foto")){
                    abriCamara();
                }else{
                    if (opciones[i].equals("Elegir de Galeria")){
                        Intent intent=new Intent(Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/");
                        startActivityForResult(intent.createChooser(intent,"Seleccione"),COD_SELECCIONA);
                    }else{
                        dialogInterface.dismiss();
                    }
                }
            }
        });
        builder.show();
    }

    private void abriCamara() {
        try {
            File miFile = new File(Environment.getExternalStorageDirectory(), DIRECTORIO_IMAGEN);
            boolean isCreada = miFile.exists();

            if (isCreada == false) {
                isCreada = miFile.mkdirs();
            }

            if (isCreada == true) {
                Long consecutivo = System.currentTimeMillis() / 1000;
                String nombre = consecutivo.toString() + ".jpg";

                path = Environment.getExternalStorageDirectory() + File.separator + DIRECTORIO_IMAGEN
                        + File.separator + nombre;//indicamos la ruta de almacenamiento

                fileImagen = new File(path);

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fileImagen));

                ////
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    String authorities =getPackageName() + ".provider";
                    Uri imageUri = FileProvider.getUriForFile(this, authorities, fileImagen);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                } else {
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fileImagen));
                }
                startActivityForResult(intent, COD_FOTO);

                ////

            }
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case COD_SELECCIONA:
                Uri miPath=data.getData();
                imgFoto.setImageURI(miPath);


                try {
                    bitmap=MediaStore.Images.Media.getBitmap(getContentResolver(),miPath);

                    //imgFoto.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
            case COD_FOTO:
                MediaScannerConnection.scanFile(this, new String[]{path}, null,
                        new MediaScannerConnection.OnScanCompletedListener() {
                            @Override
                            public void onScanCompleted(String path, Uri uri) {
                                Log.i("Path",""+path);
                            }
                        });

                bitmap= BitmapFactory.decodeFile(path);
                imgFoto.setImageBitmap(bitmap);

                break;
        }
        bitmap=redimensionarImagen(bitmap,500,500);
    }


    private Bitmap redimensionarImagen(Bitmap bitmap, float anchoNuevo, float altoNuevo) {

        int ancho=bitmap.getWidth();
        int alto=bitmap.getHeight();

        if(ancho>anchoNuevo || alto>altoNuevo){
            float escalaAncho=anchoNuevo/ancho;
            float escalaAlto= altoNuevo/alto;

            Matrix matrix=new Matrix();
            matrix.postScale(escalaAncho,escalaAlto);

            return Bitmap.createBitmap(bitmap,0,0,ancho,alto,matrix,false);

        }else{
            return bitmap;
        }


    }


    //permisos
    private boolean solicitaPermisosVersionesSuperiores() {
        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.M){//validamos si estamos en android menor a 6 para no buscar los permisos
            return true;
        }

        //validamos si los permisos ya fueron aceptados
        if((checkSelfPermission(WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)&&checkSelfPermission(CAMERA)==PackageManager.PERMISSION_GRANTED){
            return true;
        }


        if ((shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)||(shouldShowRequestPermissionRationale(CAMERA)))){
            cargarDialogoRecomendacion();
        }else{
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, MIS_PERMISOS);
        }

        return false;//implementamos el que procesa el evento dependiendo de lo que se defina aqui
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==MIS_PERMISOS){
            if(grantResults.length==2 && grantResults[0]==PackageManager.PERMISSION_GRANTED && grantResults[1]==PackageManager.PERMISSION_GRANTED){//el dos representa los 2 permisos
                Toast.makeText(this,"Permisos aceptados",Toast.LENGTH_SHORT);
                imgFoto.setEnabled(true);
            }
        }else{
            solicitarPermisosManual();
        }
    }

    //HACER EL PERMISO MANUAL SI EL USUARIO LOS CANCELA
    private void solicitarPermisosManual() {
        final CharSequence[] opciones={"si","no"};
        final AlertDialog.Builder alertOpciones=new AlertDialog.Builder(this);//estamos en fragment
        alertOpciones.setTitle("¿Desea configurar los permisos de forma manual?");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("si")){
                    Intent intent=new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri=Uri.fromParts("package",getPackageName(),null);
                    intent.setData(uri);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"Los permisos no fueron aceptados",Toast.LENGTH_SHORT).show();
                    dialogInterface.dismiss();
                }
            }
        });
        alertOpciones.show();
    }



    //METODO DE RECOMENDACION DE ACEPTAR LOS PERMISOS SI EL USUARIO LOS RECHASA
    private void cargarDialogoRecomendacion() {
        AlertDialog.Builder dialogo=new AlertDialog.Builder(this);
        dialogo.setTitle("Permisos Desactivados");
        dialogo.setMessage("Debe aceptar los permisos para el correcto funcionamiento de la App");

        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA},100);
            }
        });
        dialogo.show();
    }

    ///////////////



    //CONVERTIR IMAGEN A STRING PARA PORTERIORMENTE SER SUBIDA AL SERVIDOR
    private String convertirImgString(Bitmap bitmap) {

        ByteArrayOutputStream array=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,array);
        byte[] imagenByte=array.toByteArray();
        String imagenString= Base64.encodeToString(imagenByte,Base64.DEFAULT);

        return imagenString;
    }



}
