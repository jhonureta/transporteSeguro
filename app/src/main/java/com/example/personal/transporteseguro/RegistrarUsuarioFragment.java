package com.example.personal.transporteseguro;

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
import android.graphics.Matrix;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.personal.transporteseguro.bd.AdminSQliteOpenHelper;
import com.example.personal.transporteseguro.tablas.Operadora;
import com.example.personal.transporteseguro.tablas.Persona;
import com.example.personal.transporteseguro.tablas.discapacidad;
import com.example.personal.transporteseguro.tablas.usuario;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class RegistrarUsuarioFragment extends Fragment {

    TextView rut;
    private static final String CARPETA_PRINCIPAL = "misImagenesApp/";//directorio principal
    private static final String CARPETA_IMAGEN = "imagenes";//carpeta donde se guardan las fotos
    private static final String DIRECTORIO_IMAGEN = CARPETA_PRINCIPAL + CARPETA_IMAGEN;//ruta carpeta de directorios
    private String path;//almacena la ruta de la imagen
    File fileImagen;//PARA ESCOGER FOTO
    Bitmap bitmap;//VARIABLE DE TIPO BITMAP
    private final int MIS_PERMISOS = 100;//VARIABLES PARA LOS RESPECTIVOS PERMISOS
    private static final int COD_SELECCIONA = 10;//VARIABLES USADA PARA LA SELECCION DE FOTOS
    private static final int COD_FOTO = 20;//VARIABLE USADA PARA CUANDO SE TOME UNA FOTO
    Button botonRegistro;//VARIABLE PARA REGISTRAR LOS DATOS
    ImageView imgFoto;//VARIABLE QUE MOSTRARA LA FOTO
    ProgressDialog progreso;//
    RelativeLayout layoutRegistrar;
    //VARIABLES DONDE SE INGRESARAN LOS DATOS PARA EL RESPECTIVO REGISTRO
    private Spinner spnc1, spnc2;
    private EditText nombre;
    private EditText apellido;
    private EditText correo;
    private EditText clave;
    private EditText celular;
    private EditText telefono;
    private int value_operadora;
    private int value_persona, value_discapacidad;
    String val_oper="", val_per="",val_dis;
    Spinner spnc3;
    //FIN VARIABLES DONDE SE INGRESARAN LOS DATOS PARA EL RESPECTIVO REGISTRO***

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista=inflater.inflate(R.layout.fragment_registrar_usuario, container, false);
        //ENLAZANDO LAS VARIABLES DECLARADAS AL PRINCIPIO DE LA CLASE CON LOS ID DE OBJETOS DECLARADOS EN LA INTERFAZ
        botonRegistro= (Button) vista.findViewById(R.id.btnRegistrar);
        rut = (TextView)vista.findViewById(R.id.ruta);
        imgFoto=(ImageView)vista.findViewById(R.id.imgFoto);
        spnc1 = (Spinner)vista.findViewById(R.id.spinner1);
        spnc2 = (Spinner) vista.findViewById(R.id.spinner2);
        spnc3 = (Spinner)vista.findViewById(R.id.spinner3);
        nombre = (EditText)vista.findViewById(R.id.txt_nombre);
        apellido = (EditText)vista.findViewById(R.id.txt_apellido);
        correo = (EditText)vista.findViewById(R.id.txt_email);
        clave = (EditText)vista.findViewById(R.id.txt_password);
        celular = (EditText)vista.findViewById(R.id.txt_celular);
        telefono = (EditText)vista.findViewById(R.id.txt_telefono);
        layoutRegistrar= (RelativeLayout) vista.findViewById(R.id.idLayoutRegistrar);
        //FIN ENLAZANDO LAS VARIABLES DECLARADAS AL PRINCIPIO DE LA CLASE CON LOS ID DE OBJETOS DECLARADOS EN LA INTERFAZ




        //Permisos
        if(solicitaPermisosVersionesSuperiores()){
            imgFoto.setEnabled(true);
        }else{
          imgFoto.setEnabled(false);
        }


        // HACIENDO EL REGISTROS CON SUS RESPECTIVAS VALIDACIONES
        botonRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nombre.getText().toString().equals("") || apellido.getText().toString().equals("") ||
                        correo.getText().toString().equals("") || clave.getText().toString().equals("") ||
                        celular.getText().toString().equals("") || value_operadora == 0 ||
                        telefono.getText().toString().equals("") || value_persona == 0 || value_discapacidad ==0 ) {


                    new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("¡Alerta!")
                            .setContentText("Almenos un campo vacio")
                            .show();

                } else {
                    if (!isEmailValid(correo.getText().toString())) {
                        new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("¡Alerta!")
                                .setContentText("Email Invalido")
                                .show();
                    } else {
                        if (celular.getText().toString().length() <= 9) {
                            new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText("¡Alerta!")
                                    .setContentText("Celular Invalido")
                                    .show();
                        } else {


                                AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(getContext(), "usuario", null, 1);
                                SQLiteDatabase db = admin.getWritableDatabase();

                                ContentValues values = new ContentValues();

                                values.put(usuario.CAMPO_NOMBRE, nombre.getText().toString());
                                values.put(usuario.CAMPO_APELLIDO, apellido.getText().toString());
                                values.put(usuario.CAMPO_CORREO, correo.getText().toString());
                                values.put(usuario.CAMPO_CELULAR, celular.getText().toString());
                                values.put(usuario.CAMPO_OPERADORA, val_oper);
                                values.put(usuario.CAMPO_TELEFONO_FIJO, telefono.getText().toString());
                                values.put(usuario.CAMPO_USUARIO, val_per);
                                values.put(usuario.CAMPO_USUARIO, val_dis);


                                Long idResultante = db.insert(usuario.TABLA_USUARIO, null, values);
                                if (idResultante > 0) {

                                    Intent intent = new Intent(getContext(), contacto.class);
                                    startActivity(intent);
                                    //overridePendingTransition(R.anim.zoom_back_in,R.anim.zoom_back_out);
                                } else {

                                    Toast.makeText(getContext(), "Incorrecto", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getContext(), contacto.class);
                                    startActivity(intent);

                                }



                        }
                    }
                }

              //ruta();
            }
        });
        // HACIENDO EL REGISTROS CON SUS RESPECTIVAS VALIDACIONES

        imgFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarDialogOpciones();
            }
        });

        //LLENANDO EL SPINNER OPERADORA
        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(getContext(), "operadora", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        Cursor cur = bd.rawQuery(" SELECT " + Operadora.CAMPO_NOMBRE + " AS '_id'  FROM " + Operadora.TABLA_OPERADORA, null);
        int[] ceduDoc = new int[]{android.R.id.text1};
        String[] nomDoc = new String[]{"_id"};
        SimpleCursorAdapter scAdap = new SimpleCursorAdapter(
                getContext(),
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
                    //Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



        //LLENANDO OPERADORA PERSONA
        AdminSQliteOpenHelper admin_ = new AdminSQliteOpenHelper(getContext(), "persona", null, 1);
        SQLiteDatabase bd_ = admin_.getWritableDatabase();

        Cursor cur_ = bd_.rawQuery(" SELECT " + Persona.CAMPO_NOMBRE + " AS '_id'  FROM " + Persona.TABLA_PERSONA, null);
        int[] ceduDoc_ = new int[]{android.R.id.text1};
        String[] nomDoc_ = new String[]{"_id"};
        SimpleCursorAdapter scAdap_ = new SimpleCursorAdapter(
                getContext(),
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
                    val_per = String.valueOf(item.getString(0));
                }catch (Exception e){
                    //Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //LLENANDO DISCAPACIDAD PERSONA
        AdminSQliteOpenHelper admin__ = new AdminSQliteOpenHelper(getContext(), "discapacidad", null, 1);
        SQLiteDatabase bd__ = admin__.getWritableDatabase();

        Cursor cur__ = bd__.rawQuery(" SELECT " + discapacidad.CAMPO_NOMBRE + " AS '_id'  FROM " + discapacidad.TABLA_DISCAPACIDAD, null);
        int[] ceduDoc__ = new int[]{android.R.id.text1};
        String[] nomDoc__ = new String[]{"_id"};
        SimpleCursorAdapter scAdap__ = new SimpleCursorAdapter(
                getContext(),
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

                    value_discapacidad = item.getPosition();
                    val_per = String.valueOf(item.getString(0));
                } catch (Exception e) {
                    //Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }


        });

        return vista;
    }


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

    //METODO PARA MOSTRAR EL DIALOGO DE OPCIONES
    private void mostrarDialogOpciones() {
        final CharSequence[] opciones={"Tomar Foto","Elegir de Galeria","Cancelar"};
        final AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
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

    //METODO PARA HACER EL PROCESO DE TOMAR FOTO
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
                    String authorities = getContext().getPackageName() + ".provider";
                    Uri imageUri = FileProvider.getUriForFile(getContext(), authorities, fileImagen);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                } else {
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fileImagen));
                }
                startActivityForResult(intent, COD_FOTO);

                ////

            }
        }catch (Exception e){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }


    //METODO ASINCRONO ESPERANDO RESULTADO SEGUN LA OPCION QUE SE ESCOJA
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case COD_SELECCIONA:
                Uri miPath=data.getData();
                imgFoto.setImageURI(miPath);


                try {
                    bitmap=MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),miPath);

                    //imgFoto.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
            case COD_FOTO:
                MediaScannerConnection.scanFile(getContext(), new String[]{path}, null,
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


    //METODO PARA REDIMENCIONAR FOTO
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
        if((getContext().checkSelfPermission(WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)&&getContext().checkSelfPermission(CAMERA)==PackageManager.PERMISSION_GRANTED){
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
                Toast.makeText(getContext(),"Permisos aceptados",Toast.LENGTH_SHORT);
                imgFoto.setEnabled(true);
            }
        }else{
            solicitarPermisosManual();
        }
    }


    private void solicitarPermisosManual() {
        final CharSequence[] opciones={"si","no"};
        final AlertDialog.Builder alertOpciones=new AlertDialog.Builder(getContext());//estamos en fragment
        alertOpciones.setTitle("¿Desea configurar los permisos de forma manual?");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("si")){
                    Intent intent=new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri=Uri.fromParts("package",getContext().getPackageName(),null);
                    intent.setData(uri);
                    startActivity(intent);
                }else{
                    Toast.makeText(getContext(),"Los permisos no fueron aceptados",Toast.LENGTH_SHORT).show();
                    dialogInterface.dismiss();
                }
            }
        });
        alertOpciones.show();
    }


    private void cargarDialogoRecomendacion() {
        AlertDialog.Builder dialogo=new AlertDialog.Builder(getContext());
        dialogo.setTitle("Permisos Desactivados");
        dialogo.setMessage("Debe aceptar los permisos para el correcto funcionamiento de la App");

        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA},100);
            }
        });
        dialogo.show();
    }
    public void ruta(){

        progreso=new ProgressDialog(getContext());
        progreso.setMessage("Cargando...");
        progreso.show();


        if(rut.getText().toString().equals("")){

            String imagen=convertirImgString(bitmap);
            rut.setText(imagen);
        }else{
            progreso.hide();
        }
    }

    //METODO PARA GUARDAR LA RUTA A FUTURO
    private String convertirImgString(Bitmap bitmap) {

        ByteArrayOutputStream array=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,array);
        byte[] imagenByte=array.toByteArray();
        String imagenString= Base64.encodeToString(imagenByte,Base64.DEFAULT);

        return imagenString;
    }


}
