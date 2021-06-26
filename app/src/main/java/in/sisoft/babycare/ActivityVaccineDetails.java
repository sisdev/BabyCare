package in.sisoft.babycare;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import in.sisoft.babycare.R;

public class ActivityVaccineDetails extends AppCompatActivity {
    Button btn_back;

    public ActivityVaccineDetails() {
    }

    public void onBack() {
        this.setResult(-1, new Intent());
        super.finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view1);
        WebView wv = findViewById(R.id.wv);
        WebSettings webSettings = wv.getSettings();
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Vaccine Info - More");

        try {
            wv.loadUrl("https://www.sisoft.in/app-baby-care/baby-care-vaccines-details.html");
        } catch (Exception e) {
            Log.d("Vaccine Info - More", e.getMessage());
        }


//        Typeface var2 = Typeface.createFromAsset(this.getAssets(), "algi.ttf");
 //       ((TextView)this.findViewById(R.id.txt_vaccine_information)).setTypeface(var2);
 //       this.btn_back = (Button)this.findViewById(R.id.btn_back_vaccinedetails);
    }
}

