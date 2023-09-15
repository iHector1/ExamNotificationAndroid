package com.androiddesdecero.notificationandroid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Aquí puedes mostrar una notificación, reproducir un sonido, etc.
        Toast.makeText(context, "¡Es hora de tu cita!", Toast.LENGTH_LONG).show();
    }
}
