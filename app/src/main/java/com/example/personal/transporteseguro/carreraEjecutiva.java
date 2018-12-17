package com.example.personal.transporteseguro;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import com.example.personal.transporteseguro.Modules.DirectionFinder;
import com.example.personal.transporteseguro.Modules.DirectionFinderListener;
import com.example.personal.transporteseguro.Modules.Route;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class carreraEjecutiva extends AppCompatActivity implements OnMapReadyCallback, DirectionFinderListener{
    private GoogleMap mMap;//VARIABLA MMAP USADA PARA VISUALIZAR LOS MARCADORES EN EL MAPA ENTRE OTROS
    private Button btnFindPath;//BOTON QUE TRAZARA LA RUTA EN EL MAPA AL DARLE CLICK
    private EditText etOrigin;//CAMPO DE  INGRESO DEL ORIGEN A LA RUTA A TRAZAR
    private EditText etDestination;//CAMPO DE INGRESO DESTINO ES DECIR A DONDE IRA LA PERSONA PARA SER TRAZADO CON EL ORIGEN EN EL MAPA
    private List<Marker> originMarkers = new ArrayList<>();//LISTA QUE CONTENDRAN LOS MARCADORES DE ORIGEN
    private List<Marker> destinationMarkers = new ArrayList<>();//LISTA QUE CONTENDRA LOS MARCADOES DE DESTINO
    private List<Polyline> polylinePaths = new ArrayList<>();//LISTA QUE CONTENDRA LA RUTA DEL TRASADO
    private ProgressDialog progressDialog;//PANTALLA DE ESPERA
    RatingBar bar;//APARECE LA CALIFICACIÓN
    TextView valor;//EL VALOR SI ES BUENO, MALO ETC.
    Button pagar;//EL BOTON PAGAR, O SOLICITAR AUTO SU ACCION SERA BUSCAR EL TAXI MAS CERCANO A LA RUTA TRASADA POR EL USUARIO
    String car;//VARIABLE QUE RECIVIRA UNO 0 DOS PARA SAVER SI LA CARRERA ES EXPRESS O EJECUTRIVA
    private PlaceAutocompleteFragment placeAutocompleteFragment;//PARA HACEER EL AUTO COMPLETADO AL PONER LA RUTA DE ORIGEN
    private PlaceAutocompleteFragment placeAutocompleteFragment1;//PARA HACEER EL AUTO COMPLETADO AL PONER LA RUTA DE DESTINO

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
        setContentView(R.layout.activity_carrera_ejecutiva);


            //ENLAZANDO LAS VARIABLES DECLARADAS AL PRINCIPIO DE LA CLASE CON LOS ID DE OBJETOS DECLARADOS EN LA INTERFAZ
            etOrigin = (EditText) findViewById(R.id.origen);
            etDestination = (EditText) findViewById(R.id.destino);
            btnFindPath = (Button) findViewById(R.id.btnFindPath);
            pagar = (Button)findViewById(R.id.pagar);


            //OCULTADO EL TEXVIEW DE DESTINO ORIGEN Y DESTINO YA QUE EN SU LUGAR ESTAN LOS FRAGMENT QUE REMPLAZARAN A ESTOS TEXVIEW PERO ELLOS RECIVIRAN LA DIRECCION SELECCIONADA
            etDestination.setVisibility(View.GONE);
            etOrigin.setVisibility(View.GONE);
            //RECIVIENDO EL VALOR PARA SAVER SI ES CARRERA EXPRESS O EJECUTIVA Y CAMBIAR LOS RESPECTIVOS VALORES
            Bundle bundle = getIntent().getExtras();
            car = bundle.getString("valor");


            //ENLAZANDO EL FRAGEMENT DE DIRECCION DE ORIGEN DECLARADO AL PRINCIPIO DE LA CASE CON EL ID DEL FRAGEMENT DECLARADO EN LA INTERFAZ
            placeAutocompleteFragment = (PlaceAutocompleteFragment)getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

            //OCULTADO EL ICONO DE LA LUPA
            placeAutocompleteFragment.getView().findViewById(R.id.place_autocomplete_search_button).setVisibility(View.GONE);

            //DECLARANO QUE LA BUSQUEDA SE VA HACER PARA ECUADOR Y LA PREDICCION TENGA MAS REFERENCIA CON LAS RUTA ECUATORIANAS
            AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                    .setCountry("EC")
                    .build();

            //PONIENTO ESE FILTRO DE BUSQUEDA EL CAMPO DE TIPO FRAGMENTE
            placeAutocompleteFragment.setFilter(typeFilter);


            //EVENTO CLICK DEL FRAGMENT
            placeAutocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                //ESTE  COGERA EL TEXTO ESCOGIDO EN LA PREDICCION Y LO PEGARA EN EDITEXT ESCONDIDO
               etOrigin.setText(place.getAddress());
            }

            @Override
            public void onError(Status status) {
                //MUESTRA UN MSJ DE ERROR
                Toast.makeText(getApplicationContext(),status.toString(),Toast.LENGTH_SHORT).show();

            }
        });



            //ENLAZANDO EL FRAGEMENT DE DIRECCION DE DESTINO DECLARADO AL PRINCIPIO DE LA CASE CON EL ID DEL FRAGEMENT DECLARADO EN LA INTERFAZ
            placeAutocompleteFragment1 = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment1);

            //OCULTADO EL ICONO DE LA LUPA
            placeAutocompleteFragment1.getView().findViewById(R.id.place_autocomplete_search_button).setVisibility(View.GONE);

            //DECLARANO QUE LA BUSQUEDA SE VA HACER PARA ECUADOR Y LA PREDICCION TENGA MAS REFERENCIA CON LAS RUTA ECUATORIANAS
            AutocompleteFilter typeFilter1 = new AutocompleteFilter.Builder()
                    .setCountry("EC")
                    .build();

            //PONIENTO ESE FILTRO DE BUSQUEDA EL CAMPO DE TIPO FRAGMENTE
            placeAutocompleteFragment1.setFilter(typeFilter1);

            //EVENTO CLICK DEL FRAGMENT
            placeAutocompleteFragment1.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                @Override
                public void onPlaceSelected(Place place) {
                    //ESTE  COGERA EL TEXTO ESCOGIDO EN LA PREDICCION Y LO PEGARA EN EDITEXT ESCONDIDO
                    etDestination.setText(place.getAddress());

                }

                @Override
                public void onError(Status status) {
                    //MUESTRA UN MSJ DE ERROR
                    Toast.makeText(getApplicationContext(),status.toString(),Toast.LENGTH_SHORT).show();

                }
            });


            //LLAMANDO AL MAPA
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);

            //OBTENIENDO METODO ASINCRONO DL MAPA
            mapFragment.getMapAsync(this);


            //EVENTO CLICK AL BOTON PAGAR O SOLICITAR AUTO
            pagar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    //MENSAJE DE CORRECTO
                    correcto();


                    //DIALOGO QUE  MUESTRA LAS ESTRELLAS Y E USUARIO PUEDA CALIFICAR EL SERVICIO ACTUALMENTE COMENTADO HASTA PREVIO AVISO
                    /*myDialog.setContentView(R.layout.calificacion);
                    bar = (RatingBar) myDialog.findViewById(R.id.ratingBar);
                    valor = (TextView)myDialog.findViewById(R.id.value);
                    bar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                        @Override
                        public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                            double result = rating;
                            if(result == 0 && result <=1.5){
                                valor.setText("Malo");
                            }
                            if(result >= 1.6 && result <=2.5){
                                valor.setText("Regular");
                            }
                            if(result >= 2.6 && result <=3.5){
                                valor.setText("Bueno");
                            }
                            if(result >= 3.6 && result <=5){
                                valor.setText("Excelente");
                            }
                        }
                    });

                    myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    myDialog.show();*/
                }
            });


            //EVETO CLICK
            btnFindPath.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //LLAMANDO AL METODO QUE TRASARA LA RUTA
                    sendRequest();
                }
            });
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    //METODO QUE GENERA UNA ALERTA SUAVA DE TIPO CORRECTO O SUCCES SOICITANDO EL AUTO
    public void correcto(){

        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("¡Correcto!")
                .setContentText("Tu pedido fue enviado en breve sera atendido por el vehículo mas cercano")
                .show();

    }

    //METODO QUE TRASARA LA RUTA AL USUARIO
    private void sendRequest() {
        try{
            String origin = etOrigin.getText().toString();
            String destination = etDestination.getText().toString();
            if (origin.isEmpty()) {
                Toast.makeText(this, "Please enter origin address!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (destination.isEmpty()) {
                Toast.makeText(this, "Please enter destination address!", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                new DirectionFinder(this, origin, destination).execute();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    //METODO QUE MOSTRARA EL MAPA Y MOSTRARA EL TRAZO DE RUTA EN DESTINO A Y B
    @Override
    public void onMapReady(GoogleMap googleMap) {
        try{
            mMap = googleMap;
            LatLng hcmus = new LatLng(-1.831239 , -78.183406);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hcmus, 6));
            originMarkers.add(mMap.addMarker(new MarkerOptions()
                    .title("ECUADOR")
                    .position(hcmus)));

            //PIDIENDO PERMISO DE LOCALIZACION
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    //METODO SOBRESQUTO QUE MARCA EL DESTINO Y SE LO ENVIA AL METODO SENDREQUEST
    @Override
    public void onDirectionFinderStart() {
        try{
            progressDialog = ProgressDialog.show(this, "Espere un momento.",
                    "Encontrando dirección..!", true);

            if (originMarkers != null) {
                for (Marker marker : originMarkers) {
                    marker.remove();
                }
            }

            if (destinationMarkers != null) {
                for (Marker marker : destinationMarkers) {
                    marker.remove();
                }
            }

            if (polylinePaths != null) {
                for (Polyline polyline:polylinePaths ) {
                    polyline.remove();
                }
            }
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    //METODO QUE OBTIENE EL TIEMPO, CUANTO KILOMETROS HAY Y EL CALCULO DEL TOTAL A PAGAR
    @Override
    public void onDirectionFinderSuccess(List<Route> routes) {
        try {
            progressDialog.dismiss();
            polylinePaths = new ArrayList<>();
            originMarkers = new ArrayList<>();
            destinationMarkers = new ArrayList<>();
            String n = "";

            for (Route route : routes) {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 16));
                ((TextView) findViewById(R.id.tvDuration)).setText(route.duration.text);
                ((TextView) findViewById(R.id.tvDistance)).setText(route.distance.text);
                String distancia = route.distance.text;
                distancia = distancia.replaceAll("[^\\d.]", "");
                double valorPago =0;
                if(car.equals("1")) {
                    valorPago = 0.40 * Double.parseDouble(distancia);
                }else{
                    valorPago = 0.60 * Double.parseDouble(distancia);
                }
                char[] cadena_div = distancia.toCharArray();
                for(int i = 0; i<cadena_div.length; i++){
                    if(Character.isDigit(cadena_div[i])){
                        n+=cadena_div[i];

                    }
                }
                DecimalFormat df = new DecimalFormat("#.00");
                TextView valor = (TextView) findViewById(R.id.txt_valor);

                valor.setText("$" + df.format(valorPago)+ " Dólares");

                originMarkers.add(mMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.start_blue))
                        .title(route.startAddress)
                        .position(route.startLocation)));
                destinationMarkers.add(mMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.end_green))
                        .title(route.endAddress)
                        .position(route.endLocation)));

                PolylineOptions polylineOptions = new PolylineOptions().
                        geodesic(true).
                        color(Color.BLUE).
                        width(10);

                for (int i = 0; i < route.points.size(); i++)
                    polylineOptions.add(route.points.get(i));

                polylinePaths.add(mMap.addPolyline(polylineOptions));
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}
