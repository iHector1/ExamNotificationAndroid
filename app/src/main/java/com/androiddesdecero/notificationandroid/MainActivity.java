package com.androiddesdecero.notificationandroid;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private PendingIntent pendingIntent;
    private PendingIntent siPendingIntent;
    private PendingIntent noPendingIntent;
    private final static String CHANNEL_ID = "NOTIFICACION";
    public final static int NOTIFICACION_ID = 0;
    private Solicitud solicitud;

    private EditText etCurp;
    private EditText etNombre;
    private EditText etApellidos;
    private EditText etDomicilio;
    private EditText etIngresoFamiliar;
    private RadioGroup rgTipoCredito;
    private Button btnEnviar, btnLimpiar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        solicitud = new Solicitud();

        etCurp = findViewById(R.id.et_curp);
        etNombre = findViewById(R.id.et_nombre);
        etApellidos = findViewById(R.id.et_apellidos);
        etDomicilio = findViewById(R.id.et_domicilio);
        etIngresoFamiliar = findViewById(R.id.et_ingreso_familiar);
        rgTipoCredito = findViewById(R.id.rg_tipo_credito);



    }
    public void limpiarCampos(View view) {
        etCurp.setText("");
        etNombre.setText("");
        etApellidos.setText("");
        etDomicilio.setText("");
        etIngresoFamiliar.setText("");
        rgTipoCredito.clearCheck();
    }

    private boolean validarIngreso() {
        double ingresoFamiliar = solicitud.getIngresoFamiliar();
        String tipoCredito = solicitud.getTipoCredito();

        switch (tipoCredito) {
            case "Medio":
                return ingresoFamiliar >= 36000;
            case "Parcial":
                return ingresoFamiliar >= 75000;
            case "Total":
                return ingresoFamiliar >= 120000;
            default:
                return false;
        }
    }
    private boolean validarcapos(){
        if (etCurp.getText().toString().isEmpty()) {
            etCurp.setError("Este campo es obligatorio");
            return false;
        }

        if (etNombre.getText().toString().isEmpty()) {
            etNombre.setError("Este campo es obligatorio");
            return false;
        }

        if (etApellidos.getText().toString().isEmpty()) {
            etApellidos.setError("Este campo es obligatorio");
            return false;
        }

        if (etDomicilio.getText().toString().isEmpty()) {
            etDomicilio.setError("Este campo es obligatorio");
            return false;
        }

        if (etIngresoFamiliar.getText().toString().isEmpty()) {
            etIngresoFamiliar.setError("Este campo es obligatorio");
            return false;
        }
        if (rgTipoCredito.getCheckedRadioButtonId() == -1) {
            return false;
        }
        return true;
    }
    public void obtenerInformacion(View view) {
        if(validarcapos()) {
            solicitud.setCurp(etCurp.getText().toString());
            solicitud.setNombre(etNombre.getText().toString());
            solicitud.setApellidos(etApellidos.getText().toString());
            solicitud.setDomicilio(etDomicilio.getText().toString());
            solicitud.setIngresoFamiliar(Double.parseDouble(etIngresoFamiliar.getText().toString()));

            RadioButton radioButton = rgTipoCredito.findViewById(rgTipoCredito.getCheckedRadioButtonId());
            solicitud.setTipoCredito(radioButton.getText().toString());
            if (this.validarIngreso()) {
                setPendingIntent();
                setSiPendingIntent();
                setNoPendingIntent();
                createNotificationChannel();
                createNotification();
            } else {
                Toast.makeText(MainActivity.this, "No eres apto ", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(MainActivity.this, "Llena todos los campos ", Toast.LENGTH_SHORT).show();
        }
    }
    private void setSiPendingIntent(){
        Intent intent = new Intent(this, SiActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(SiActivity.class);
        stackBuilder.addNextIntent(intent);
        siPendingIntent = stackBuilder.getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT);
    }
    private void setNoPendingIntent(){
        Intent intent = new Intent(this, NoActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(NoActivity.class);
        stackBuilder.addNextIntent(intent);
        noPendingIntent = stackBuilder.getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void setPendingIntent(){
        Intent intent = new Intent(this, NotificacionActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(NotificacionActivity.class);
        stackBuilder.addNextIntent(intent);
        pendingIntent = stackBuilder.getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "Noticacion";
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    private void createNotification(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_sms_black_24dp);
        builder.setContentTitle("Notificacion Android");
        builder.setContentText("¿Quieres informacion De la beneficiencia?");
        builder.setColor(Color.BLUE);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setLights(Color.MAGENTA, 1000, 1000);
        builder.setVibrate(new long[]{1000,1000,1000,1000,1000});
        builder.setDefaults(Notification.DEFAULT_SOUND);

        builder.setContentIntent(pendingIntent);
        builder.addAction(R.drawable.ic_sms_black_24dp, "Cita", siPendingIntent);
        builder.addAction(R.drawable.ic_sms_black_24dp, "Beneficios", noPendingIntent);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(NOTIFICACION_ID, builder.build());
    }
}
