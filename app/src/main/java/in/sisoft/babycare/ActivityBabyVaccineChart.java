package in.sisoft.babycare;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import in.sisoft.babycare.model.AppConstant;
import in.sisoft.babycare.adapter.AdapterBabyVaccineChart;
import in.sisoft.babycare.model.VaccineChart;


public class ActivityBabyVaccineChart extends AppCompatActivity {
    final String TAG = "ActBabyVaccineChart";
    private static final int REQUEST_CODE = 20;
    String babyName, babyID;
    String gender;
    Button btn_Email;
    Button btn_back;
    Button btn_vaccine;
    final Calendar currentcal = Calendar.getInstance();
    final Calendar currentdate = Calendar.getInstance();
    DatabaseHelper dbhelper;
    String dob;
    String dudate;
    String gd1;
    RecyclerView rv ;
    GridView grid;
    String immuid;
    TextView txt_dudate31;
    TextView txtbabyName;
    String vname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baby_vaccine_chart);
        babyName = getIntent().getExtras().getString(AppConstant.keyBabyName);
        babyID = String.valueOf(getIntent().getExtras().getInt(AppConstant.keyBabyID));
        Log.d(TAG, "Immunization:onCreate:"+ "Baby Name:" + this.babyName);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Vaccine Chart");
        getSupportActionBar().setSubtitle(babyName);
        SimpleDateFormat var4 = new SimpleDateFormat(AppConstant.DATE_FORMAT);
        String var2 = var4.format(this.currentcal.getTime());
        try {
            this.currentdate.setTime(var4.parse(var2));
        } catch (ParseException var3) {
            var3.printStackTrace();
        }

        rv = this.findViewById(R.id.rv_vchart);

    }


    @Override
    protected void onResume() {
        super.onResume();
        loadGrid();
    }

    public void loadGrid() {
        dbhelper = new DatabaseHelper(this);

        try {
            ArrayList<VaccineChart> al_vc =  dbhelper.getVaccineChartList(babyID);
            Log.d(TAG, "Vaccine Chart Count:" + al_vc.size());
            AdapterBabyVaccineChart bvc = new AdapterBabyVaccineChart(this,al_vc);
            rv.setLayoutManager(new LinearLayoutManager(this));
            rv.setAdapter(bvc);
        } catch (Exception var3) {
            Toast.makeText(this.getApplicationContext(), "Exception:"+var3.getMessage(),Toast.LENGTH_LONG).show();
            Log.d(TAG,"loadGrid:"+var3.getMessage());
        }
    }



    public boolean onCreateOptionsMenu(Menu var1) {
        super.onCreateOptionsMenu(var1);
        this.getMenuInflater().inflate(R.menu.baby_option, var1);
        return super.onCreateOptionsMenu(var1);
    }

    public boolean onOptionsItemSelected(MenuItem var1) {
        Intent var2;
        switch(var1.getItemId()) {
            case R.id.email :
                this.sendEmail();
                return true;
            default:
                return super.onOptionsItemSelected(var1);
        }
    }


    public String createHtmlFile(Uri var1) throws IOException {
        Cursor cursor_baby;

        String strHeader;
        String strReportBody="";
        String strFooter = "";
        String strReport = "" ;

            try {
                cursor_baby = dbhelper.getbabyByName(this.babyName);
                if (cursor_baby.moveToFirst()) {
                    this.dob = cursor_baby.getString(2);
                    this.gender = cursor_baby.getString(3);

                    strHeader = "" + "<html>" + "<head>" + "<title>Baby Vaccine Chart</title>" + "</head>" +
                            "<body bgcolor=white>" +
                            strWidth("Baby Name:", 12) + this.babyName + "<br>" +
                            strWidth("DOB:", 12) + this.dob + "<br>" +
                            strWidth("Gender:",12) + this.gender + "<br>"+
                            "<p><b>" + strWidth("Vaccine", 20)+
                            strWidth("Due Date", 20)+
                            strWidth("Given Date", 20)+"</p>";

                    Cursor cursorBabyImmune = dbhelper.getVaccineChartByID(this.babyID);

                    if (cursorBabyImmune.moveToFirst()) {
                        do {
                            strReportBody = strReportBody +   strWidth(cursorBabyImmune.getString(2),20) +
                                    strWidth( cursorBabyImmune.getString(3),20) +
                                    strWidth(cursorBabyImmune.getString(4), 20) + "<br>";
                        } while (cursorBabyImmune.moveToNext());
                        strFooter = "<p>" + "<b>Generated by: Baby Immunization Tracker</b> <br>" +
                                "<b>Developed by : Sisoft Technologies Pvt.Ltd(www.sisoft.in)</b></p>" +
                                 "</body>" + "</html>";
                        strReport = strHeader + strReportBody + strFooter ;
                         Log.d("createHTMLFile", strReport);


                    }
                }
            }catch (Exception var12) {
                    Log.d("createHtmlFileExp",var12.getMessage());

                }
            return strReport ;
    }

    public String createTextFile() throws IOException {
        Cursor cursor_baby;
        String strHeader;
        String strReportBody="";
        String strFooter = "";
        String strReport = "" ;

        try {
            cursor_baby = this.dbhelper.getbabyByName(this.babyName);
            Log.d("Immunization","Rec Count:"+cursor_baby.getCount());
            if (cursor_baby.moveToFirst()) {
                this.dob = cursor_baby.getString(2);
                this.gender = cursor_baby.getString(3);
                strHeader = strWidth("Baby Name:", 12) + this.babyName + "\n" +
                        strWidth("DOB:", 12) + this.dob + "\n" +
                        strWidth("Gender:",12) + this.gender + "\n\n"+
                        strWidth("Vaccine", 30)+
                        strWidth("Due Date", 20)+
                        strWidth("Given Date", 20)+"\n";

                Cursor cursorBabyImmune = this.dbhelper.getVaccineChartByID(this.babyID);
                if (cursorBabyImmune.moveToFirst()) {
                    do {
                        strReportBody = strReportBody +   strWidth(cursorBabyImmune.getString(2),30) +
                                strWidth( cursorBabyImmune.getString(3),20) +
                                strWidth(cursorBabyImmune.getString(4), 20) + "\n";
                    } while (cursorBabyImmune.moveToNext());
                    strFooter = "\n" + "Generated by: Baby Immunization Tracker\n" +
                            "Developed by : Sisoft Technologies Pvt.Ltd(www.sisoft.in)\n" ;
                    strReport = strHeader + strReportBody + strFooter ;
                    Log.d("createTextFile", strReport);
                }
            }
        }catch (Exception var12) {
            Log.d("createTextFileExp",var12.getMessage());

        }
        return strReport ;
    }



    String strWidth(String s1, int wid)
    {
        s1 = s1.trim();
        int len = s1.length();
        for (int i=len ; i<wid; i++){
            s1=s1.concat(" ");
        }
        return s1 ;
    }


    public void onPause() {
        super.onPause();
        Log.d("Immunization", "on Pause");
    }


    public void sendEmail() {
        try {
           Uri fileUri = Uri.parse(this.babyName + ".html");
            String email_msg =   this.createTextFile();
            StringBuilder sb = new StringBuilder();
            Log.i(this.getClass().getSimpleName(), "Send Email task - start");
            Log.d("fileUri",fileUri.getPath());
            Intent intentEmail = new Intent("android.intent.action.SEND");
            intentEmail.setType("text/html");
            intentEmail.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intentEmail.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intentEmail.putExtra("android.intent.extra.EMAIL", new String[]{""});
            intentEmail.putExtra("android.intent.extra.SUBJECT", "Vaccine Chart: "+this.babyName);
           intentEmail.putExtra("android.intent.extra.TEXT", email_msg);
            this.startActivity(Intent.createChooser(intentEmail, "Send mail..."));
        } catch (Throwable var3) {
            Toast.makeText(this, "Request failed: " + var3.toString(), Toast.LENGTH_LONG).show();
            Log.d(TAG,"SendEmail:"+var3.toString());
        }
    }
}
