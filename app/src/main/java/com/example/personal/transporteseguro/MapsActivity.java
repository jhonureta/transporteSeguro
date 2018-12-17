package com.example.personal.transporteseguro;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.example.personal.transporteseguro.clases.taxistas;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.LocationListener;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {


    private GoogleMap mMap;//VARIABLA MMAP USADA PARA VISUALIZAR  LOS MARCADORES EN EL MAPA ENTRE OTROS
    private Marker marcador;//VARIABLE DE TIPO MARKER PARA HACER LAS RESPECTIVAS  MARCACIONES EN EL MAPA
    double lat = 0.0;//VARIABLE DE TIPO DOUBLE PARA GUARDAR LA LATITUD
    double lng = 0.0;//VARIABLE DE TIPO DOUBLE PARA GUARDAR LA LONGITUD
    String mensaje1;//VARIABLE DE TIPO STRING MENSAJE AVISAR SI EL GPS ES ACTIVADO O DESACTIVADO
    String direccion = "";//VARIABLE DE TIPO STRING ALMACENARA LA DIRECCION
    ArrayList<taxistas>taxilist;//ARRRALIST QUE ALMACENA LA LISTA DE TAXISTAS
    private View popup = null;//PARA VISUALIZAR LA INFORMACION DEL TAXISTA
    TextView  nombre, color;//PARA PONER LA INFORMACION DEL TAXISTA

    //VARIABLES PARA CONSUMIR EL SERVICIO SOAP(INFORMACION DE LOS TAXISTAS)
    private final String NAMESPACE = "http://tempuri.org/";
    private final String URL = "http://204.128.252.66/RapperSms/service.asmx";
    private final String SOAP_ACTION = "http://tempuri.org/getData";
    private final String METHOD_NAME = "getData";
    HttpTransportSE transporte;
    SoapObject request;
    SoapSerializationEnvelope sobre;
    SoapPrimitive resultado;
    //FIN VARIABLES PARA CONSUMIR EL SERVICIO SOAP(INFORMACION DE LOS TAXISTAS)**



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        //LLAMANDO AL FRAGENT DEL ACTIVITY

        try {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
            //CREANDO LISTA DE TAXIS CON SU UBICACIÓN

            taxilist = new ArrayList<>();
            String cadena = "";
            //lista  = new ArrayList<>();
            //ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,lista);
            try {
                request = new SoapObject(NAMESPACE, METHOD_NAME);
                Log.e("JMMC", "SOAP-3");

                //request.getProperty("mensaje");
                sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                Log.e("JMMC", "SOAP-4");
                sobre.dotNet = true;
                Log.e("JMMC", "SOAP-5");
                sobre.setOutputSoapObject(request);
                Log.e("JMMC", "SOAP-6");

                // Habilitar la comunicacion con el
                // Web Services desde el Activity Principal
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);

                transporte = new HttpTransportSE(URL);
                Log.e("JMMC", "SOAP-7");
                transporte.call(SOAP_ACTION, sobre);
                Log.e("JMMC", "SOAP-8");
                resultado = (SoapPrimitive) sobre.getResponse();
                Log.e("JMMC", "SOAP-9");
                cadena = resultado.toString();
                Log.e("JMMC", "SOAP-10");
                JSONArray jsonArray = new JSONArray(resultado.toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String var1 = jsonObject.getString("latitude");
                    //lista.add(jsonObject.getString("nombre"));
                    //list.setAdapter(adapter);
                    String var2 = jsonObject.getString("longitude");
                    String var3 = jsonObject.getString("telefono");
                    String var4 = jsonObject.getString("cedula");
                    String var5 = jsonObject.getString("nombre");
                    String var6 = jsonObject.getString("color");
                    String var7 = jsonObject.getString("placa");
                    String var8 = jsonObject.getString("clase");
                    String var9 = jsonObject.getString("marca");
                    String var10 = jsonObject.getString("direccion");
                    taxilist.add(new taxistas(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10));

                }


            } catch (Exception e) {
                Log.e("JZH", "SOAP-" + e.getMessage());
            }

        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }




    }


    //METODOS DE LA LOCACION

    public void locationStart() {
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingsIntent);
        }

    }


    //activar los servicios del gps cuando esten apagados


    public void setLocation(Location loc) {
        //Obtener la direccion de la calle a partir de la latitud y la longitud
        if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) {
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(
                        loc.getLatitude(), loc.getLongitude(), 1);
                if (!list.isEmpty()) {
                    Address DirCalle = list.get(0);
                    direccion = (DirCalle.getAddressLine(0));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //agregar el marcador en el mapa
    private void AgregarMarcador(double lat, double lng) {
        LatLng coordenadas = new LatLng(lat, lng);
        CameraUpdate MiUbicacion = CameraUpdateFactory.newLatLngZoom(coordenadas, 16);
        if (marcador != null) marcador.remove();
        marcador = mMap.addMarker(new MarkerOptions()
                .position(coordenadas)
                .title("Dirección:" + direccion)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher)));
        mMap.animateCamera(MiUbicacion);
    }

    //actualizar la ubicacion
    private void ActualizarUbicacion(Location location) {
        if (location != null) {
            lat = location.getLatitude();
            lng = location.getLongitude();
            AgregarMarcador(lat, lng);

        }
    }

    //control del gps
    LocationListener locListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {

            ActualizarUbicacion(location);
            setLocation(location);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {
            mensaje1 = ("GPS Activado");
            Mensaje();
        }

        @Override
        public void onProviderDisabled(String s) {
            mensaje1 = ("GPS Desactivado");
            locationStart();
            Mensaje();
        }
    };
    private static int PETICION_PERMISO_LOCALIZACION = 101;

    private void miUbicacion() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PETICION_PERMISO_LOCALIZACION);
            return;
        } else {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            ActualizarUbicacion(location);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1200,0,locListener);
        }

    }

    public void Mensaje() {
        Toast toast = Toast.makeText(this, mensaje1, Toast.LENGTH_LONG);
        //toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();
    }


    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        try {
            mMap = googleMap;

            //ESTABLECIENDO EL MAPA EN GUAYAQUIL - ECUADOR
            LatLng sydney = new LatLng(-2.170998, -79.922359);
            mMap.addMarker(new MarkerOptions()
                    .position(sydney)
                    .icon(bitmapDescriptorFromVector(this, R.drawable.tax))
                    .title("Guayaquil - Ecuador"));
            //FIN ESTABLECIENDO EL MAPA EN GUAYAQUIL - ECUADOR


            //FOR DE TIPO OBJECT PARA OBTENER LAS POSICIONES DE LOS TAXISTAS
            for (taxistas taxis : taxilist) {

                LatLng myPos2 = new LatLng(Double.parseDouble(taxis.getLatitude()), Double.parseDouble(taxis.getLongitude()));
                //DECLARANDO ALGUNAS POSICIONES PARA ESTABLECER EL ESTADO EN QUE SE ENCUENTRA POR EL MOMENO ES ESTATICO POSTERIORMENTE SERA DINAMICO
                LatLng myPos3 = new LatLng(-2.172675, -79.905493);
                LatLng myPos4 = new LatLng(-2.203290, -79.929306);
                LatLng myPos5 = new LatLng(-2.201266, -79.914231);
                LatLng myPos6 = new LatLng(-2.171076, -79.922057);
                LatLng myPos7 = new LatLng(-2.140779, -79.943266);

                LatLng myPos8 = new LatLng(-2.145383, -79.904003);
                LatLng myPos9 = new LatLng(-2.180480, -79.901845);
                LatLng myPos10 = new LatLng(-2.225657, -79.889692);
                //FIN DECLARANDO ALGUNAS POSICIONES PARA ESTABLECER EL ESTADO EN QUE SE ENCUENTRA POR EL MOMENO ES ESTATICO POSTERIORMENTE SERA DINAMICO**


                //AGREGANDO LOS MARCADORES DE LA INFO DE  LOS TAXISTA
                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(Double.parseDouble(taxis.getLatitude()), Double.parseDouble(taxis.getLongitude())))
                        .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.tax))
                        .title("NOMBRE: " + taxis.getNombre()+"\n"+ "CEDULA: " + taxis.getCedula() +"\n"+ "TÉLEFONO: " + taxis.getTelefono()+"\n"+ "DIRECCIÓN: " + taxis.getDireccion()+"\n"+ " COLOR: " + taxis.getColor()+"\n"+ " MARCA: " + taxis.getMarca()+"\n"+ " CLASE: " + taxis.getClase()+"\n"+ " PLACA: " + taxis.getPlaca()) );


                //ESTABLECIENDO COLORES A LOS MARCADORES ROJO, ANARANJADO Y VERDE
                mMap.addMarker(new MarkerOptions().position(myPos3).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                mMap.addMarker(new MarkerOptions().position(myPos4).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                mMap.addMarker(new MarkerOptions().position(myPos5).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                mMap.addMarker(new MarkerOptions().position(myPos6).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                mMap.addMarker(new MarkerOptions().position(myPos7).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                mMap.addMarker(new MarkerOptions().position(myPos2).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                mMap.addMarker(new MarkerOptions().position(myPos8).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                mMap.addMarker(new MarkerOptions().position(myPos9).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                mMap.addMarker(new MarkerOptions().position(myPos10).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                //FIN ESTABLECIENDO COLORES A LOS MARCADORES ROJO, ANARANJADO Y VERDE***

            }

            //EFECTO DE ACERCAMIENTO
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));

            //METODO PARA PONER LA INFO DEL TAXISTA
            mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(Marker marker) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {
                   if(popup == null){
                       popup = getLayoutInflater().inflate(R.layout.informacion,null);


                   }
                        nombre = (TextView) popup.findViewById(R.id.nombre);
                        nombre.setText(marker.getTitle());

                    return popup;
                }
           });

        } catch (Exception e) {

        }
    }
}
