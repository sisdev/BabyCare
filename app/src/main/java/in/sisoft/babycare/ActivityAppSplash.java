package in.sisoft.babycare;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import in.sisoft.babycare.model.AppConstant;

public class ActivityAppSplash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_splash);

//** Test Call Reminder Broadcast Alarm *//
        AppConstant.setReminderAlarm(getApplicationContext());

        (new Thread() {
            int wait = 0;

            public void run() {
                try {
                    Thread.sleep(3000);
                    Intent intent = new Intent(ActivityAppSplash.this, ActivityAppHome.class);
                    ActivityAppSplash.this.startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

    }


}
