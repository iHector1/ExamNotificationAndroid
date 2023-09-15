package com.androiddesdecero.notificationandroid;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.AlarmClock;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;
import static com.androiddesdecero.notificationandroid.MainActivity.NOTIFICACION_ID;

import android.app.TimePickerDialog;

import android.widget.TimePicker;
import android.widget.Toast;

public class SiActivity extends AppCompatActivity {
    private EditText nom, ape, horario;
    private int diaAlarma = 0;
    private int horaAlarma = 0;
    private int minutoAlarma = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_si);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nom = findViewById(R.id.editTextFirstName);
        ape = findViewById(R.id.editTextLastName);
        horario = findViewById(R.id.editDate);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(SiActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                return (true);
        }
        return super.onOptionsItemSelected(item);
    }

    public void registrarCita(View view) {
        String lexmhi = nom.getText().toString() + " " + ape.getText().toString();
        Toast.makeText(this, "Cita registrada a: " + lexmhi, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Fecha de Cita: " + diaAlarma, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Horario de Cita: " + horaAlarma + ":" + minutoAlarma,
                Toast.LENGTH_SHORT).show();
            programarAlarmaEnSistema("CITA CON Beneficio", horaAlarma, minutoAlarma);
        nom.setText("");
        ape.setText("");
        horario.setText("Horario de Cita: " + horaAlarma + ":" + minutoAlarma);
    }

    public void setFechaAlarma(View view) {
        Calendar horarioHoy = Calendar.getInstance();
        int anioActual = horarioHoy.get(Calendar.YEAR);
        int mesActual = horarioHoy.get(Calendar.MONTH);
        int diaActual = horarioHoy.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(SiActivity.this, new
                DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        diaAlarma = i2;
                    }
                }, anioActual, mesActual, diaActual);
        datePickerDialog.setTitle("Fecha de cita");
        datePickerDialog.show();
    }

    public void setHorarioAlarma(View view) {
        Calendar horarioHoy = Calendar.getInstance();
        int horaActual = horarioHoy.get(Calendar.HOUR_OF_DAY);
        int minutoActual = horarioHoy.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(SiActivity.this, new
                TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        horaAlarma = i;
                        minutoAlarma = i1;
                    }
                }, horaActual, minutoActual, true);
        timePickerDialog.setTitle("Horario de cita");
        timePickerDialog.show();
    }

    public void programarAlarmaEnSistema(String mensaje, int hora, int minuto) {
        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM)
                .putExtra(AlarmClock.EXTRA_MESSAGE, mensaje)
                .putExtra(AlarmClock.EXTRA_HOUR, hora)
                .putExtra(AlarmClock.EXTRA_MINUTES, minuto);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
