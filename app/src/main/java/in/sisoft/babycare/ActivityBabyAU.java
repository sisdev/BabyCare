package in.sisoft.babycare;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import in.sisoft.babycare.model.AppConstant;
import in.sisoft.babycare.model.Baby;
import in.sisoft.babycare.model.VaccineObject;
import in.sisoft.babycare.model.VaccineParsingData;

public class ActivityBabyAU extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button btn_dob;
    DatabaseHelper dbhelper;
    EditText edit_date;
    private int mDay;
    private int mHour;
    private int mMinute;
    private int mMonth;
    private int mYear;
    EditText note;
    EditText bname;
    Spinner spin_gender;
    Spinner spinblood;
    Spinner spinrh;

    SimpleDateFormat sdf;


    String babyID, babyName;
    String str_dob;
    private String time;
    Baby b1 ;
    String option;
    Intent i1 ;

    ArrayAdapter aa_gender, aa_blood_grp, aa_rh_factor ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baby_a_u);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        i1 = getIntent();
        option = i1.getStringExtra(AppConstant.keyBabyOption);
        if (option.equalsIgnoreCase("add"))
            getSupportActionBar().setTitle("Baby Add");
        else {
            getSupportActionBar().setTitle("Baby Update");
        }

        this.bname = findViewById(R.id.babyname);
        this.btn_dob = (Button)this.findViewById(R.id.btn_baby_DOB);
        this.spin_gender = (Spinner)this.findViewById(R.id.spinner);
        this.spinblood = (Spinner)this.findViewById(R.id.spinnerblood);
        this.spinrh = (Spinner)this.findViewById(R.id.spinnerrh);
        this.note = (EditText)this.findViewById(R.id.notetakn);

        b1 = new Baby() ; // Used in update and add both
/*
        this.bname.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable var1) {
                babyName = bname.getText().toString();
                if (dbhelper.countBabyName(babyName) > 0) {
                    Toast.makeText(ActivityBabyAU.this.getApplicationContext(), babyName + ":This name already exists. Enter another name", Toast.LENGTH_LONG).show();
                   // ActivityBabyAU.this.bname.setFo;
                }

            }

            public void beforeTextChanged(CharSequence var1, int var2, int var3, int var4) {
            }

            public void onTextChanged(CharSequence var1, int var2, int var3, int var4) {
            }
        });

 */
        spin_gender.setOnItemSelectedListener(this);
        aa_gender = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,  AppConstant.gender_options);
        spin_gender.setAdapter(aa_gender);

        spinblood.setOnItemSelectedListener(this);
        aa_blood_grp = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item , AppConstant.blood_grp_options);
        spinblood.setAdapter(aa_blood_grp);

        spinrh.setOnItemSelectedListener(this);
        aa_rh_factor = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item , AppConstant.rh_options);
        spinrh.setAdapter(aa_rh_factor);

        sdf = new SimpleDateFormat(AppConstant.DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        btn_dob.setText(sdf.format(cal.getTime()).toString());

        this.btn_dob.setOnClickListener(new View.OnClickListener() {
            public void onClick(View var1) {
                ActivityBabyAU.this.show_calendar();
            }
        });



    }

    public void onStart() {
            super.onStart();
            this.dbhelper = new DatabaseHelper(this);
            if (option.equalsIgnoreCase("update")){
                babyName = i1.getStringExtra(AppConstant.keyBabyName);
                babyID = String.valueOf(i1.getExtras().getInt(AppConstant.keyBabyID));
                b1 = dbhelper.getBabyByID(babyID);
                bname.setText(b1.getName());
                btn_dob.setText(b1.getDOB());
                spinblood.setSelection(aa_blood_grp.getPosition(b1.getblood()));
                spin_gender.setSelection(aa_gender.getPosition(b1.getgender()));
                spinrh.setSelection(aa_rh_factor.getPosition(b1.getrh()));
                note.setText(b1.getnote());
                btn_dob.setOnClickListener(null);
            }
    }


    public void show_calendar() {
        final Calendar cal_current = Calendar.getInstance();
        this.mYear = cal_current.get(Calendar.YEAR);
        this.mMonth = cal_current.get(Calendar.MONTH);
        this.mDay = cal_current.get(Calendar.DATE);
        (new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker var1x, int yr, int mo, int dy) {
                cal_current.set(yr, mo, dy);
                String var5 = sdf.format(cal_current.getTime()).toString();
                btn_dob.setText(var5);
                mYear = cal_current.get(Calendar.YEAR);
                mMonth = cal_current.get(Calendar.MONTH);
                mDay = cal_current.get(Calendar.DATE);
            }
        }, this.mYear, this.mMonth, this.mDay)).show();
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
        if (item.getItemId() == android.R.id.home) {
            finish();                       // return to parent activity
            return true;
        }

        else if (item.getItemId() == R.id.opt_save) {
            addbaby() ;
            return true;
        }
        else{
            return super.onOptionsItemSelected(item);

        }
    }

    public void addbaby() {
        this.babyName = this.bname.getText().toString();
        String dob = btn_dob.getText().toString().trim();
        String gender = String.valueOf((String)this.spin_gender.getItemAtPosition((int)this.spin_gender.getSelectedItemId()));
        String bloodGrp = String.valueOf((String)this.spinblood.getItemAtPosition((int)this.spinblood.getSelectedItemId()));
        String rhFactor = String.valueOf((String)this.spinrh.getItemAtPosition((int)this.spinrh.getSelectedItemId()));
        String note = this.note.getText().toString();

        try {
            if (this.babyName.length() > 0 && dob.length() > 0 && gender.length() > 0 && bloodGrp.length() > 0 && rhFactor.length() > 0 ) {
                b1.setName(babyName);
                b1.setDOB(dob);
                b1.setgender(gender);
                b1.setblood(bloodGrp);
                b1.setRh(rhFactor);
                b1.setnote(note);
                Toast.makeText(this.getApplicationContext(), b1.getName() + b1.getblood() + b1.getgender() + b1.getDOB(), Toast.LENGTH_SHORT).show();

                if (option.equalsIgnoreCase("add")) {
                    if (dbhelper.countBabyName(babyName) > 0) {
                        Toast.makeText(ActivityBabyAU.this.getApplicationContext(), babyName + ":This name already exists. Enter another name", Toast.LENGTH_LONG).show();
                        // ActivityBabyAU.this.bname.setFo;
                    }

                    int babyId= this.dbhelper.addBaby(b1);
                    this.createBabyVaccinationChart(babyId,babyName,dob, gender);
                    this.ShowMemberAddedAlert(this);
                    AppConstant.setReminderAlarm(this);
                }
                else
                {
                    dbhelper.updateBaby(b1);
                }
                finish(); // return to previous screen ---
            } else {
                this.CatchError("Fill All Details");
            }
        } catch (Exception exception) {
            this.CatchError(exception.toString());
        } finally {
            ;
        }
    }

    public void createBabyVaccinationChart(int babyId, String babyName, String dob, String gender) {
        int n = 0 ;
        try {
            Log.d("Str Dob Date", dob);
            this.sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.UK);
            final Date parse = this.sdf.parse(dob);
            final Calendar instance = Calendar.getInstance();
            instance.setTime(parse);
            Log.d("DOB Date", this.sdf.format(instance.getTime()));
            final Date time = instance.getTime();
            final List<VaccineObject> parse2 = new VaccineParsingData().parse(this.getAssets().open("vaccine_chart.xml"));
            Log.d("Size:", String.valueOf(parse2.size()) + ":" + parse2.toString());
            while (true) {
                final Calendar instance2 = Calendar.getInstance();
                //  n = 0;
                if (n >= parse2.size()) {
                    return;
                }
                final VaccineObject vaccineObject = (VaccineObject) parse2.get(n);
                instance2.setTime(time);
                Log.d("At Start Vaccine Cal", this.sdf.format(instance2.getTime()));
                Log.d("At Start Vaccine Cal", String.valueOf(vaccineObject.getDay()) + ":" + vaccineObject.getMonth() + ":" + vaccineObject.getYear());
                instance2.add(Calendar.DATE, Integer.parseInt(vaccineObject.getDay()));
                instance2.add(Calendar.MONTH, Integer.parseInt(vaccineObject.getMonth()));
                instance2.add(Calendar.YEAR, Integer.parseInt(vaccineObject.getYear()));
                Log.d("End Vaccine Cal", this.sdf.format(instance2.getTime()));
                if (!vaccineObject.getVaccineName().endsWith("HPV(only for females)") || !gender.equalsIgnoreCase("Boy")) {
                    this.saveVaccineChart(instance2, babyId, this.babyName, vaccineObject.getVaccineName());
                }
                ++n;
                continue;
            }

        }

        catch (Exception ex) {
            ex.printStackTrace();
            return;
        }
    }


    private void saveVaccineChart(Calendar calVaccine, int babyId, String babyName, String vaccineName) {
        try {
            this.sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.UK);
            String vaccineDate = this.sdf.format(calVaccine.getTime()).toString();
            dbhelper.createVaccineChart(babyId, babyName, vaccineName, vaccineDate, "NA");
        } catch (Exception var6) {
            Toast.makeText(this.getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            this.CatchError(var6.toString());
        } finally {
            ;
        }
    }
    private void CatchError(String strError) {
        Dialog dlg = new Dialog(this);
        dlg.setTitle("Fill all details ");
        TextView textView = new TextView(this);
        textView.setText(strError);
        dlg.setContentView(textView);
        dlg.show();
    }

    public void ShowMemberAddedAlert(Context ctx) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ctx);
        alertBuilder.setTitle("Add new Baby ");
        alertBuilder.setIcon(R.drawable.babyadd);
        DialogListner var2 = new DialogListner();
        alertBuilder.setMessage("Baby Added successfully");
        alertBuilder.setPositiveButton("ok", var2);
        alertBuilder.create().show();
    }




    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public class DialogListner implements android.content.DialogInterface.OnClickListener {
        public DialogListner() {
        }

        public void onClick(DialogInterface var1, int var2) {
//            ActivityBabyAU.this.backToMember();
        }
    }



}
