package in.sisoft.babycare;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

import in.sisoft.babycare.model.AppConstant;

public class ReceiverReboot extends BroadcastReceiver {
    public ReceiverReboot() {
    }

    public void onReceive(Context ctx, Intent intent) {
        AppConstant.setReminderAlarm(ctx);


    }


}

