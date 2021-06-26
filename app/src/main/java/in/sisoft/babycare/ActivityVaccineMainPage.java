package in.sisoft.babycare;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.util.List;

import in.sisoft.babycare.R;
import in.sisoft.babycare.adapter.AdapterVaccine;
import in.sisoft.babycare.model.VaccineParsingData;

public class ActivityVaccineMainPage extends AppCompatActivity {
    Button back;
    Context ctx;
    XmlPullParserFactory factory = null;
    GridView grid_vaccinechart;
    XmlPullParser parser = null;
    String xml_file_name;

    public ActivityVaccineMainPage() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccine_main_page);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Vaccination Info");


        Log.d("VaccineMainPage", "Vaccine Main Page Called");
        this.grid_vaccinechart = (GridView)this.findViewById(R.id.grid_vaccinechartmain);

        try {
            List var3 = (new VaccineParsingData()).parse(this.getAssets().open("vaccine_chart.xml"));
            Log.d("VaccineMainPage:", var3.size() + ":" + var3.toString());
            AdapterVaccine var4 = new AdapterVaccine(this, R.layout.grid_vaccinechart , var3);
            this.grid_vaccinechart.setAdapter(var4);
        } catch (IOException var2) {
            var2.printStackTrace();
        }


    }

    public boolean onCreateOptionsMenu(Menu var1) {
        super.onCreateOptionsMenu(var1);
        this.getMenuInflater().inflate(R.menu.vaccine_option, var1);
        return super.onCreateOptionsMenu(var1);
    }

    public boolean onOptionsItemSelected(MenuItem var1) {
        Intent var2;
        switch(var1.getItemId()) {
            case R.id.more_vaccine :
                this.startActivity(new Intent(this, ActivityVaccineDetails.class));
                return true;
            default:
                return super.onOptionsItemSelected(var1);
        }
    }

}
