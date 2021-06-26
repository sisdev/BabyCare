package in.sisoft.babycare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import in.sisoft.babycare.R;

public class ActivityFeeding extends AppCompatActivity {
    private static final int REQUEST_CODE = 14;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeding);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Feeding Info");

    }

    public void twentyFourClick(View v) {
        Intent var1 = new Intent(this, ActivityWebView1.class);
        var1.putExtra("a", 6);
        this.startActivity(var1);
    }

    public void eighteenClick(View v) {
        Intent var1 = new Intent(this, ActivityWebView1.class);
        var1.putExtra("a", 5);
        this.startActivity(var1);
    }


    public void twelveclick(View v) {
        Intent var1 = new Intent(this, ActivityWebView1.class);
        var1.putExtra("a", 4);
        this.startActivity(var1);
    }

    public void nineclick(View v) {
        Intent var1 = new Intent(this, ActivityWebView1.class);
        var1.putExtra("a", 3);
        this.startActivity(var1);
    }

    public void sixclick(View v) {
        Intent var1 = new Intent(this, ActivityWebView1.class);
        var1.putExtra("a", 2);
        this.startActivity(var1);
    }

    public void fourclick(View v) {
        Intent var1 = new Intent(this, ActivityWebView1.class);
        var1.putExtra("a", 1);
        this.startActivity(var1);
    }

    public void zeroclick(View v) {
        Intent var1 = new Intent(this, ActivityWebView1.class);
        var1.putExtra("a", 0);
        this.startActivity(var1);
    }

}
