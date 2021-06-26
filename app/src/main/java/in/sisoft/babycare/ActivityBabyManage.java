package in.sisoft.babycare;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import in.sisoft.babycare.adapter.AdapterBabyDetails;
import in.sisoft.babycare.model.AppConstant;
import in.sisoft.babycare.model.Baby;

public class ActivityBabyManage extends AppCompatActivity {

    RecyclerView recyclerView ;
    DatabaseHelper dbhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baby_manage);
        recyclerView = (RecyclerView)findViewById(R.id.myrv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 =  new Intent(ActivityBabyManage.this, ActivityBabyAU.class);
                i1.putExtra(AppConstant.keyBabyOption,"add");
                startActivity( i1 ) ;
            }
        });
    }

    public void loadBaby() {
        this.dbhelper = new DatabaseHelper(this);

        try {
            ArrayList<Baby> alb = dbhelper.getBabyRecords();
            AdapterBabyDetails ca = new AdapterBabyDetails(this,alb);
            recyclerView.setAdapter(ca);
            /*
            if (alb.size()==0)
            {
                Intent i1 =  new Intent(this.getParent(), ActivityBabyAU.class);
                i1.putExtra(AppConstant.keyBabyOption,"add");
                startActivity( i1 ) ;
            }
             */
        } catch (Exception var3) {
            AlertDialog.Builder var2 = new AlertDialog.Builder(this);
            var2.setMessage(var3.toString());
            var2.show();
            Log.d("ActivityBabyManage", var3.toString());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadBaby();
    }
}