package com.example.personal.transporteseguro;

import android.app.DatePickerDialog;
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
import android.support.v7.widget.CardView;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.personal.transporteseguro.bd.AdminSQliteOpenHelper;
import com.example.personal.transporteseguro.tablas.Conductor;
import com.example.personal.transporteseguro.tablas.Operadora;
import com.example.personal.transporteseguro.tablas.Persona;
import com.example.personal.transporteseguro.tablas.discapacidad;
import com.example.personal.transporteseguro.tablas.licencia;
import com.example.personal.transporteseguro.tablas.usuario;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class registroTaxi extends AppCompatActivity {
    //VARIABLES DE REGISTRO DE TAXISTA
    private EditText nombre;
    private EditText apellido;
    Button botonRegistro, btnFecha;private TextView fecha,textViewEdad;private int edad;
    private  EditText experiencia;
    private EditText correo;
    private EditText clave;
    private EditText celular;
    private int value_operadora;
    private int value_licencia;
    private EditText telefono;
    //FIN VARIABLES DE REGISTRO DE TAXISTA

    private int value_persona;//VARIABLE QUE SIRVE PARA EVALUAR EL SPINNER DE LA OPCION QUE ESCOGE EL USUARIO
    ImageView imgFoto;//VARIABLE DE TIPO IMAGEVIEW SIRVE PARA ESCOGER LA FOTO
    String val_oper="", val_per="",val_lic;//EVALUA LAS OPCIONES DEL SPINNER
    private Spinner spnc1, spnc2,spnc3;//ESPINNER PARA ESCOGER OPCION DE: LICENCIA, DISCAPACIDAD, EXPERIENCIA
    private DatePickerDialog.OnDateSetListener mDateSetListener;//VARIA ACTIONLISTENER PARA ESCOGER LA FECHA




    private static final String CARPETA_PRINCIPAL = "misImagenesApp/";//directorio principal
    private static final String CARPETA_IMAGEN = "imagenes";//carpeta donde se guardan las fotos
    private static final String DIRECTORIO_IMAGEN = CARPETA_PRINCIPAL + CARPETA_IMAGEN;//ruta carpeta de directorios
    private String path;//almacena la ruta de la imagen
    File fileImagen;
    Bitmap bitmap;
    Dialog myDialog;

    private final int MIS_PERMISOS = 100;
    private static final int COD_SELECCIONA = 10;
    private static final int COD_FOTO = 20;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_taxi);
try {
    //ENLAZANDO LAS VARIABLES DECLARADAS AL PRINCIPIO DE LA CLASE CON LOS ID DE OBJETOS DECLARADOS EN LA INTERFAZ
    nombre = (EditText) findViewById(R.id.txt_nombre);
    apellido = (EditText) findViewById(R.id.txt_apellido);
    correo = (EditText) findViewById(R.id.txt_email);
    fecha = (TextView) findViewById(R.id.txt_fecha);
    btnFecha = (Button) findViewById(R.id.btn_fecha);
    experiencia = (EditText) findViewById(R.id.experiencia);
    textViewEdad = (TextView)findViewById(R.id.txt_edad);
    botonRegistro = (Button)findViewById(R.id.btnRegistrar);
    clave = (EditText) findViewById(R.id.txt_password);
    celular = (EditText) findViewById(R.id.txt_celular);
    telefono = (EditText) findViewById(R.id.txt_telefono);
    spnc1 = (Spinner) findViewById(R.id.spinner1);
    spnc2 = (Spinner) findViewById(R.id.spinner2);
    spnc3 = (Spinner) findViewById(R.id.spinner3);
    imgFoto = (ImageView)findViewById(R.id.imgFoto);
    myDialog = new Dialog(this);
    //FIN ENLAZANDO LAS VARIABLES DECLARADAS AL PRINCIPIO DE LA CLASE CON LOS ID DE OBJETOS DECLARADOS EN LA INTERFAZ

    spinnerLicencia();
    llenarOperadora();
    spinnerDiscapacidad();




    btnFecha.setOnClickListener(new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onClick(View view) {

            try {

                    btnMostrarFecha();

            }catch (Exception e){
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    });


    //METODO QUE OBTENDRA LA FECHA ESCOGIDA POR EL USUARIO
    mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            try {
                month = month + 1;
                //Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = day + "/" + month + "/" + year;

                fecha.setText(date);
                Calendar cal = Calendar.getInstance();
                int año_actual = cal.get(Calendar.YEAR);
                int  edad = año_actual - year;
                textViewEdad.setText(edad  + " AÑOS DE EDAD");

            }catch (Exception e){
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    };
    //FIN METODO QUE OBTENDRA LA FECHA ESCOGIDA POR EL USUARIO***

    //METODO LISTENER QUE MOSTRARA EL DIALOGO DE OPCIONES: ESCOGER GALERIA, TOMAR FOTO, CANCELAR
            imgFoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mostrarDialogOpciones();
                }
            });
    //FIN METODO LISTENER QUE MOSTRARA EL DIALOGO DE OPCIONES: ESCOGER GALERIA, TOMAR FOTO, CANCELAR**

    //SOLICITAR PERMISOS DE USUARIO PARA ANDROID SUPERIOR A 5.1
            if(solicitaPermisosVersionesSuperiores()){
                imgFoto.setEnabled(true);
            }else{
                imgFoto.setEnabled(false);
            }
    //FIN SOLICITAR PERMISOS DE USUARIO PARA ANDROID SUPERIOR A 5.1***



    //ACCION AL PRESIONAR EL BOTON  LLAMA AL METODO PARA GUARDAR EL REGISTRO
            botonRegistro.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                  guardarRegistro();
                }
            });
    //FIN ACCION AL PRESIONAR EL BOTON  LLAMA AL METODO PARA GUARDAR EL REGISTRO**

    }catch (Exception e){
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
    }



    }



    public void alerta_vacio(){
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("¡Alerta!")
                .setContentText("Al menos un campo vacio")
                .show();
    }

    public void alerta(){

        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("¡Alerta!")
                .setContentText("Email Invalido")
                .show();
    }

    public void alerta_telefono(){

        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("¡Alerta!")
                .setContentText("Teléfono Invalido")
                .show();
    }

    public void alerta_celular(){

        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("¡Alerta!")
                .setContentText("Celular Invalido")
                .show();
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


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //preventing default implementation previous to android.os.Build.VERSION_CODES.ECLAIR


                moveTaskToBack(true);
                 return true;
        }
        return super.onKeyDown(keyCode, event);
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

                    imgFoto.setImageBitmap(bitmap);

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
    ////////////////

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






    /*public void ruta(){

        progreso=new ProgressDialog(this);
        progreso.setMessage("Cargando...");
        progreso.show();


        if(rut.getText().toString().equals("")){

            String imagen=convertirImgString(bitmap);
            rut.setText(imagen);
        }else{
            progreso.hide();
        }
    }*/

    private String convertirImgString(Bitmap bitmap) {

        ByteArrayOutputStream array=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,array);
        byte[] imagenByte=array.toByteArray();
        String imagenString= Base64.encodeToString(imagenByte,Base64.DEFAULT);

        return imagenString;
    }


    public void spinnerLicencia(){

        //LLENANDO EL SPINNER LICENCIA
        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(this, "licencia", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        Cursor cur = bd.rawQuery(" SELECT " + licencia.CAMPO_NOMBRE + " AS '_id'  FROM " + licencia.TABLA_LICENCIA, null);
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
                    value_licencia = item.getPosition();
                    val_lic = String.valueOf(item.getString(0));

                } catch (Exception e) {
                    //Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    public void llenarOperadora(){


        //LLENANDO OPERADORA PERSONA
        AdminSQliteOpenHelper admin_ = new AdminSQliteOpenHelper(this, "operadora", null, 1);
        SQLiteDatabase bd_ = admin_.getWritableDatabase();

        Cursor cur_ = bd_.rawQuery(" SELECT " + Operadora.CAMPO_NOMBRE + " AS '_id'  FROM " + Operadora.TABLA_OPERADORA, null);
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

                    value_operadora = item.getPosition();
                    val_oper = String.valueOf(item.getString(0));
                } catch (Exception e) {
                    //Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }


    public void spinnerDiscapacidad(){


        //LLENANDO DISCAPACIDAD PERSONA
        AdminSQliteOpenHelper admin__ = new AdminSQliteOpenHelper(this, "discapacidad", null, 1);
        SQLiteDatabase bd__ = admin__.getWritableDatabase();

        Cursor cur__ = bd__.rawQuery(" SELECT " + discapacidad.CAMPO_NOMBRE + " AS '_id'  FROM " + discapacidad.TABLA_DISCAPACIDAD, null);
        int[] ceduDoc__ = new int[]{android.R.id.text1};
        String[] nomDoc__ = new String[]{"_id"};
        SimpleCursorAdapter scAdap__ = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_spinner_item,
                cur__,
                nomDoc__,
                ceduDoc__,
                0);
        scAdap__.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnc3.setAdapter(scAdap__);
        spnc3.getAdapter();
        //METODO ONCLICK PARA OBTENER LA POCISIÓN DEL SPINNER
        spnc3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {

                    SQLiteCursor item = (SQLiteCursor) parent.getItemAtPosition(position);

                    value_persona = item.getPosition();
                    val_per = String.valueOf(item.getString(0));
                } catch (Exception e) {
                    //Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }


        });


    }

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

    public void guardarRegistro(){
        if (nombre.getText().toString().equals("") || apellido.getText().toString().equals("") ||
                correo.getText().toString().equals("") || clave.getText().toString().equals("") ||
                celular.getText().toString().equals("") || value_operadora == 0 ||
                telefono.getText().toString().equals("") || value_persona == 0 || value_licencia == 0) {

            alerta_vacio();


        } else {
            if (!isEmailValid(correo.getText().toString())==true) {

                alerta();

            } else {

                if(celular.getText().toString().length()<=9){
                    alerta_celular();
                }else{

                    AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(this, "conductor", null, 1);
                    SQLiteDatabase db = admin.getWritableDatabase();

                    ContentValues values = new ContentValues();

                    values.put(Conductor.CAMPO_NOMBRE, nombre.getText().toString());
                    values.put(Conductor.CAMPO_APELLIDO, apellido.getText().toString());
                    values.put(Conductor.CAMPO_FECHA, apellido.getText().toString());
                    values.put(Conductor.CAMPO_EDAD, edad);
                    values.put(Conductor.CAMPO_LICENCIA, val_lic);
                    values.put(Conductor.CAMPO_EXPERENCIA, experiencia.getText().toString());
                    values.put(Conductor.CAMPO_CORREO, correo.getText().toString());
                    values.put(Conductor.CAMPO_CLAVE, clave.getText().toString());
                    values.put(Conductor.CAMPO_CELULAR, celular.getText().toString());
                    values.put(Conductor.CAMPO_OPERADORA, val_oper);
                    values.put(Conductor.CAMPO_TELEFONO_FIJO, telefono.getText().toString());
                    values.put(Conductor.CAMPO_DISCAPACIDAD, val_per);


                    Long idResultante = db.insert(Conductor.TABLA_CONDUCTOR, null, values);
                    if (idResultante > 0) {

                        //Toast.makeText(getApplicationContext(), "Correcto", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(this, regitro2_taxi.class);
                        startActivity(intent);
                        //overridePendingTransition(R.anim.fade_in,R.anim.fade_out);

                    }



                }
            }



        }


    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public  void btnMostrarFecha(){
        android.icu.util.Calendar cal = null;


            cal = android.icu.util.Calendar.getInstance();


        int year = cal.get(android.icu.util.Calendar.YEAR);
        int month = cal.get(android.icu.util.Calendar.MONTH);
        int day = cal.get(android.icu.util.Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                registroTaxi.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                mDateSetListener,
                year, month, day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

}
