package com.example.personal.transporteseguro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.Toast;

import java.util.Calendar;

public class calendar extends AppCompatActivity {
CalendarView calendarView;
    int edad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        calendarView =(CalendarView) findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                String date = dayOfMonth +"/" +(month + 1) + "/" + year;
                Calendar cal = Calendar.getInstance();
                int año_actual = cal.get(Calendar.YEAR);
                edad = año_actual - year;
                String ed = String.valueOf(edad);
                Intent intent = new Intent(calendar.this, registroTaxi.class);
                intent.putExtra("data", date);
                intent.putExtra("edad", ed);
                startActivity(intent);
            }
        });

    }
}
