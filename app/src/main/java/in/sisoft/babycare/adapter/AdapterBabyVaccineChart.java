package in.sisoft.babycare.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import in.sisoft.babycare.DatabaseHelper;
import in.sisoft.babycare.model.VaccineChart;
import in.sisoft.babycare.ActivityBabyVaccineUpdate;
import in.sisoft.babycare.R;

// List of Babies

public class AdapterBabyVaccineChart extends RecyclerView.Adapter<VaccineChartViewHolder> {

    public static String TAG="AdapterBabyVaccineChart";
    Context ctx;
    DatabaseHelper dbhelper;
    Cursor cur1;
    ArrayList<VaccineChart> al_vc ;
    Calendar cal_today;

    public AdapterBabyVaccineChart(Context context, ArrayList<VaccineChart> a1) {
        ctx = context;
        al_vc = a1 ;
        Log.d(TAG, "Baby Vaccine Chart Count:" + a1.size());
        this.cal_today = Calendar.getInstance();

    }


    @NonNull
    @Override
    public VaccineChartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(ctx);
        View view = li.inflate(R.layout.card_vaccine_chart, parent, false);
        VaccineChartViewHolder myViewHolder = new VaccineChartViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull VaccineChartViewHolder holder, int position) {
        final VaccineChart vc = al_vc.get(position);
/*
        if (position % 2 == 1) {
            holder.line_vchart.setBackgroundColor(ctx.getResources().getColor(R.color.oddback));
        } else {
            holder.line_vchart.setBackgroundColor(ctx.getResources().getColor(R.color.evenback));
        }
*/

        String str_due_date, str_given_date;
        str_due_date = vc.getVduedate();
        str_given_date = vc.getVgivendate();

//        Log.d("OnBindView", babyId+":"+babyName);
        holder.vname.setText(vc.getVacname());
//        holder.babyName.setBackgroundColor(R.color.colorPromo);
        holder.vgivendate.setText(vc.getVgivendate());
        holder.vduedate.setText(vc.getVduedate());

        try {
            Date due_date = (new SimpleDateFormat("dd-MMM-yyyy")).parse(str_due_date);
            Calendar cal_cur = Calendar.getInstance();
            cal_cur.setTime(due_date);
            if (str_given_date.equalsIgnoreCase("NA") && this.cal_today.after(cal_cur)) {
                holder.vgivendate.setText("Over Due");
                holder.vgivendate.setTextColor(ctx.getResources().getColor(android.R.color.holo_red_light));
            } else {
                holder.vgivendate.setText(str_given_date);
                holder.vgivendate.setTextColor(ctx.getResources().getColor(android.R.color.holo_green_light));

            }
        } catch (Exception var9) {
            holder.vgivendate.setText(str_given_date);
            Log.d(TAG, var9.getMessage() + ":" + str_given_date);
        }


        holder.btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ctx);
                alertBuilder.setTitle("Updte Vaccination Status");
                alertBuilder.setMessage("Edit Vaccine Status ?");
                alertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent var3 = new Intent(ctx, ActivityBabyVaccineUpdate.class);
                        var3.putExtra("id", vc.getVchartID());
                        var3.putExtra("bname", vc.getBname());
                        var3.putExtra("vname", vc.getVacname());
                        var3.putExtra("dudate", vc.getVduedate());
                        ctx.startActivity(var3);
                        dialog.cancel();
                    } } );
                alertBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    } });
                AlertDialog ad = alertBuilder.create();
                ad.show() ;
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return al_vc.size();
    }

}

class VaccineChartViewHolder extends RecyclerView.ViewHolder {

    TextView vname, vduedate, vgivendate;
    Button btn_update ;
    View view ;
    LinearLayout line_vchart ;

    VaccineChartViewHolder(View itemview) {
        super(itemview);
        this.view=itemview;
        this.vname = itemview.findViewById(R.id.vname);
        this.vduedate = itemview.findViewById(R.id.vduedate);
        this.vgivendate = itemview.findViewById(R.id.vgivendate);
        this.btn_update = itemview.findViewById(R.id.vupdate);
        this.line_vchart = itemview.findViewById(R.id.line_vchart);
    }
}


