package in.sisoft.babycare;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import in.sisoft.babycare.R;

public class ActivityWebView1 extends AppCompatActivity {
    int a;
    WebView wv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view1);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Feeding Info");

        this.wv = (WebView)this.findViewById(R.id.wv);
        this.a = this.getIntent().getIntExtra("a", this.a);
        if (this.a == 0) {
            getSupportActionBar().setTitle("0 to 4 Months");
            this.wv.loadUrl("file:///android_asset/zero.html");
        }

        if (this.a == 1) {
            getSupportActionBar().setTitle("4 to 6 Months");
            this.wv.loadUrl("file:///android_asset/fourtosix.html");
        }

        if (this.a == 2) {
            getSupportActionBar().setTitle("6 to 9 Months");
            this.wv.loadUrl("file:///android_asset/sixtonine.html");
        }

        if (this.a == 3) {
            getSupportActionBar().setTitle("9 to 12 Months");
            this.wv.loadUrl("file:///android_asset/ninetotwelve.html");
        }

        if (this.a == 4) {
            getSupportActionBar().setTitle("12 to 18 Months ");
            this.wv.loadUrl("file:///android_asset/twelvetoeighteen.html");
        }

        if (this.a == 5) {
            getSupportActionBar().setTitle("18 to 24 Months");
            this.wv.loadUrl("file:///android_asset/eighteentotwentyfour.html");
        }

        if (this.a == 6) {
            getSupportActionBar().setTitle("24 Months");
            this.wv.loadUrl("file:///android_asset/twentyfour.html");
        }

        if (this.a == 11) {
            getSupportActionBar().setTitle("App Features");
            this.wv.loadUrl("file:///android_asset/app_features.html");
        }

    }
}
