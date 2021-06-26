package in.sisoft.babycare;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import in.sisoft.babycare.model.VaccineChart;
import in.sisoft.babycare.R;
import in.sisoft.babycare.model.AppConstant;

public class ActivityBabyVaccineUpdate extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String babyName;
    String vacId;
    String vacName;
    String vacDueDate ;
    String vacGivenDate ;
    String vacStatus ; // Due or Completed
    String vacNote ;
    String vacDrName ;

    Button btn_date_given;
    Button btn_savedate;
    int count = 0;
    DatabaseHelper dbhelper;
    String ddate;
    TextView tv_vacName, tv_babyName, tv_vacDueDate ;
    ArrayAdapter aa_vaccine_status ;

    EditText edit_docname,edit_note;
    Spinner spinVacStatus ;

    Boolean flag = false;
    private int mDay;
    private int mMonth;
    private int mYear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccine_update);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Update Vaccine Chart");


        Intent calling_intent = this.getIntent();
        this.vacId = String.valueOf(calling_intent.getExtras().getInt("id"));
        this.vacName = calling_intent.getExtras().getString("vname");
        this.babyName = calling_intent.getExtras().getString("bname");
        this.vacDueDate = calling_intent.getExtras().getString("dudate");

        tv_vacName = (TextView)this.findViewById(R.id.vac_name);
        tv_babyName = findViewById(R.id.baby_name);
        tv_vacDueDate = findViewById(R.id.vduedate);
        spinVacStatus = (Spinner)this.findViewById(R.id.spinnerstatus);

        spinVacStatus.setOnItemSelectedListener(this);
        aa_vaccine_status = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item , AppConstant.vaccine_status);
        spinVacStatus.setAdapter(aa_vaccine_status);

        this.edit_docname = (EditText)this.findViewById(R.id.edit_docname);
        this.edit_note = (EditText)this.findViewById(R.id.edit_vnote);
        this.btn_date_given = (Button)this.findViewById(R.id.btn_date_given);

        this.btn_date_given.setOnClickListener(new OnClickListener() {
            public void onClick(View var1) {
                ActivityBabyVaccineUpdate.this.updateDisplay();
            }
        });
    }

    public void onStart() {
            super.onStart();
            this.dbhelper = new DatabaseHelper(this);
            this.loadGivendate();
    }

    private void loadGivendate() {
        DatabaseHelper sdb = new DatabaseHelper(this);
        VaccineChart vc = sdb.getVaccineChartRecord(vacId);
        String givenDate = vc.getVgivendate() ;

        tv_vacName.setText(vc.getVacname());
        tv_babyName.setText(vc.getBname());
        tv_vacDueDate.setText(vc.getVduedate());
        if (!givenDate.equals("NA")){
            spinVacStatus.setSelection(aa_vaccine_status.getPosition("Completed"));
        }
        btn_date_given.setText(givenDate);
        edit_docname.setText(vc.getDocname());
        edit_note.setText(vc.getNote());

    }


    public void ShowMemberAddedAlert(Context context) {
        AlertDialog.Builder var3 = new AlertDialog.Builder(context);
        var3.setTitle("Add  Given date ");
        var3.setIcon(R.drawable.injec);
        ActivityBabyVaccineUpdate.DialogListner var2 = new ActivityBabyVaccineUpdate.DialogListner();
        var3.setMessage("Date Added successfully");
        var3.setPositiveButton("ok", var2);
        var3.create().show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.opt_save) {
            updateVaccineChartRecord() ;
            return true;
        }

        else if (item.getItemId()== android.R.id.home){
            finish();
            return true ;

        }
        return super.onOptionsItemSelected(item);
    }


    public void updateDisplay() {
        final Calendar cal_current = Calendar.getInstance();
        this.mYear = cal_current.get(Calendar.YEAR);
        this.mMonth = cal_current.get(Calendar.MONTH);
        this.mDay = cal_current.get(Calendar.DAY_OF_MONTH);
        (new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker var1x, int var2, int var3, int var4) {
                cal_current.set(var2, var3, var4);
                String var5 = (new SimpleDateFormat("dd-MMM-yyyy")).format(cal_current.getTime()).toString();
                btn_date_given.setText(var5);
            }
        }, this.mYear, this.mMonth, this.mDay)).show();
    }

    public void updateVaccineChartRecord() {
            try {
                DatabaseHelper dbHelper = new DatabaseHelper(this);
                int var1 = Integer.parseInt(this.vacId);
                String var9 = this.babyName;
                String vacDrName = this.edit_docname.getText().toString();
                String var5 = this.vacName;
                String vacGivenDate = this.btn_date_given.getText().toString();
                String vacNote = this.edit_note.getText().toString();
                if (var9.length() > 0 && vacDrName.length() > 0 && var5.length() > 0 && vacGivenDate.length() > 0 && vacNote.length() > 0) {
                    dbHelper.updateVaccineChartStatus(new VaccineChart(var1, var9, vacDrName, var5, vacGivenDate, vacNote));
                    Toast.makeText(this.getApplicationContext(), "Vaccine Chart Updated", Toast.LENGTH_LONG).show();
                    finish() ; // return to previous activity
                    return;
                }
                Toast.makeText(this.getApplicationContext(), "Enter Value first", Toast.LENGTH_LONG).show();
                return;
            } catch (Exception var8) {
                AlertDialog.Builder var3 = new AlertDialog.Builder(this);
                var3.setMessage("Error :" + var8);
                var3.show();
            }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
          vacStatus = adapterView.getItemAtPosition(pos).toString();
          if (vacStatus.equals("Due") || vacStatus.equals("Not Required")){
               btn_date_given.setText("NA");
               btn_date_given.setClickable(false);
          }
          else{
              btn_date_given.setClickable(true);
          }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public class DialogListner implements DialogInterface.OnClickListener {
        public DialogListner() {
        }

        public void onClick(DialogInterface var1, int var2) {
           // ActivityBabyVaccineUpdate.this.onbackclick();
        }
    }
}
