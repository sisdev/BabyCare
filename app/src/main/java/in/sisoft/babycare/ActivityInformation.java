package in.sisoft.babycare;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import in.sisoft.babycare.R;

public class ActivityInformation extends AppCompatActivity {
    private static final int REQUEST_CODE = 22;
    Button back;
    Button btn_aboutus;
    Button btn_features;
    Button btn_sislogo;
    Button btn_vaccine;
    Button feedback;
    Button share;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("App Info");

    }



    public void onFeatures(View v) {
        Intent var1 = new Intent(this, ActivityWebView1.class);
        var1.putExtra("a", 11);
        this.startActivity(var1);

    }


    public void onFeedbck(View v) {
        Intent var1 = new Intent("android.intent.action.SENDTO", Uri.parse("mailto:info@sisoft.in"));
        var1.putExtra("android.intent.extra.SUBJECT", "Baby Care App(Android App)");
        var1.putExtra("android.intent.extra.TEXT", "  ");
        this.startActivity(var1);
    }

    public void onShare(View v) {
        Intent var1 = new Intent("android.intent.action.SEND");
        var1.setType("text/plain");
        var1.putExtra("android.intent.extra.SUBJECT", "Baby care app India");
        var1.putExtra("android.intent.extra.TEXT", "http://play.google.com/store/apps/details?id=in.sisoft.babycare");
        this.startActivity(Intent.createChooser(var1, "share via"));
    }
}
