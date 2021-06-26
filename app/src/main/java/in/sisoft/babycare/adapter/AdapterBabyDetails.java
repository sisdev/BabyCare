package in.sisoft.babycare.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.sisoft.babycare.ActivityBabyAU;
import in.sisoft.babycare.ActivityBabyVaccineChart;
import in.sisoft.babycare.DatabaseHelper;
import in.sisoft.babycare.model.AppConstant;
import in.sisoft.babycare.model.Baby;
import in.sisoft.babycare.R;

// List of Babies

public class AdapterBabyDetails extends RecyclerView.Adapter<MyViewHolder> {

    public static String TAG="AdapterBabyDetails";
    Context ctx;
    DatabaseHelper dbhelper;
//    Cursor cur1;
    ArrayList<Baby> alb ;

    public AdapterBabyDetails(Context context, ArrayList<Baby> lb) {
        ctx = context;
        alb = lb ;
        dbhelper = new DatabaseHelper(ctx);
  //      cur1 = this.dbhelper.getAllBaby();
        Log.d(TAG, "Baby Count:" + alb.size());
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(ctx);
        View view = li.inflate(R.layout.card_baby, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        Baby b1 = alb.get(position);
 //       cur1.moveToPosition(position);
        final int babyId = b1.getId() ;
        final String babyName = b1.getName();
        final String dob = b1.getDOB();
        final String gender = b1.getgender();
        final String bld_grp = b1.getblood();
        final String rh = b1.getrh();


        Log.d("OnBindView", babyId+":"+babyName);
        holder.babyName.setText(babyName+":"+gender);
//        holder.babyName.setBackgroundColor(R.color.colorPromo);
        holder.dob.setText(dob);
        holder.blood_grp.setText(bld_grp+" "+rh);

        holder.btn_vaccine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  Intent intent = new Intent(ctx, ActivityBabyVaccineChart.class);
                  intent.putExtra(AppConstant.keyBabyID,babyId);
                  intent.putExtra(AppConstant.keyBabyName,babyName);
                  ctx.startActivity(intent);
            }
        });
        holder.btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ctx, ActivityBabyAU.class);
                intent.putExtra(AppConstant.keyBabyID,babyId);
                intent.putExtra(AppConstant.keyBabyName,babyName);
                intent.putExtra(AppConstant.keyBabyOption,"update");
                ctx.startActivity(intent);
               // Toast.makeText(ctx,"Edit Baby Record", Toast.LENGTH_LONG).show();
            }
        });
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ctx);
                alertBuilder.setTitle(AppConstant.AppName);
                alertBuilder.setMessage("Delete Baby Record("+babyId+"):"+babyName);
                alertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strBabyId = String.valueOf(babyId);
                        dbhelper.deleteBaby(strBabyId);
                        alb.remove(position);
                        notifyDataSetChanged();
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
        return alb.size();
    }

}

class MyViewHolder extends RecyclerView.ViewHolder {

    TextView babyName;
    TextView dob, blood_grp;
    Button btn_edit, btn_vaccine, btn_delete ;



    MyViewHolder(View itemview) {
        super(itemview);
        this.babyName = (TextView) itemview.findViewById(R.id.babyname);
        this.dob = itemview.findViewById(R.id.date_birth);
        this.blood_grp = itemview.findViewById(R.id.bloog_grp);
        this.btn_edit = itemview.findViewById(R.id.btn_edit);
        this.btn_vaccine = itemview.findViewById(R.id.btn_vaccine);
        this.btn_delete = itemview.findViewById(R.id.btn_delete);
    }
}


