package in.sisoft.babycare.model;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

import in.sisoft.babycare.ReceiverReminderAlarm;

public class AppConstant {
    public static int hourOfDay = 6 ;
    public static String keyBabyName = "babyName" ;  // used as key in passing baby name
    public static String keyBabyID = "babyID" ;  // used as key in passing baby name

    public static String[] gender_options = new String[]{"Boy", "Girl"};
    public static String[] blood_grp_options = new String[]{"A", "B", "AB", "O","Unknown"};
    public static String[] rh_options = new String[]{"Rh+", "Rh-", "Unknown"};
    public static String[] vaccine_status = new String[]{"Due", "Completed","Not Required"};

    public static String DATE_FORMAT="dd-MMM-yyyy";
    public static String keyBabyOption="BabyManageOption";  // add or update
    public static String AppName ="Vaccination Tracker";

    public static void setReminderAlarm(Context ctx) {
        AlarmManager alarmManager = (AlarmManager)ctx.getSystemService(Context.ALARM_SERVICE);
        Intent intentBrd=new Intent(ctx, ReceiverReminderAlarm.class);
        PendingIntent pending = PendingIntent.getBroadcast(ctx, 0,intentBrd , 0);
        Calendar cal_today = Calendar.getInstance();
        Calendar calAlarm = Calendar.getInstance();
        calAlarm.set(Calendar.HOUR_OF_DAY, AppConstant.hourOfDay);
//        calAlarm.set(Calendar.HOUR_OF_DAY, 12);    // Follows 24 hrs clock

        calAlarm.set(Calendar.MINUTE, 0);
        calAlarm.set(Calendar.SECOND, 0);
        if (calAlarm.before(cal_today)) {
            calAlarm.add(Calendar.DATE, 1);
        }
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calAlarm.getTimeInMillis(), 86400000L, pending);
    }

}
