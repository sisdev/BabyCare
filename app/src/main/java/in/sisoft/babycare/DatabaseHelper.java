package in.sisoft.babycare;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import in.sisoft.babycare.model.Baby;
import in.sisoft.babycare.model.VaccineChart;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    static final String dbname = "babydb";

    static final String TableDoctor = "doctordet";
    static final String TableVaccineChart = "vaccine_chart";
    static final String TableBaby = "baby";

    public static final String colblood = "blood";
    public static final String coldob = "dob";
    public static final String colgen = "gender";
    public static final String colbabyid = "baby_id";
    public static final String colbabyname = "baby_name";
    public static final String colnote = "note";
    public static final String colrh = "rh";

    static final String colAddress = "Address";
    static final String colContactno = "Phone";
    static final String colDrName = "dr_name";
    static final String colDrID = "dr_id";
    static final String columail = "Mail";
    static final String columob = "Mobile";
    static final String coluspecialization = "Sepecialization";

    public static final String colvac_bname = "baby_name";
    public static final String colvac_dudate = "due_date";
    public static final String colvac_givdate = "given_date";
    public static final String colvac_id = "vaccine_id";
    public static final String colvac_vname = "vaccine_name";
    public static final String colvac_drname = "dr_name";
    public static final String colvacinfo = "info";
    static final String colvacnote = "note";

 //   static final String viewImmu = "viewImmunization";
 //   static final String viewVaccine = "viewvaccine";
 //   static final String viewbaby = "viewbaby";


    /**
     * Database Methods
     **/
    public DatabaseHelper(Context context) {
        super(context, dbname, (SQLiteDatabase.CursorFactory) null, DATABASE_VERSION);
    }


    public void onCreate(SQLiteDatabase sdb) {
        sdb.execSQL("CREATE TABLE " + TableBaby +
                " (baby_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "baby_name TEXT UNIQUE, " +
                "dob TEXT, " +
                "gender TEXT, " +
                "blood TEXT, " +
                "rh TEXT, " +
                "note TEXT);");

        sdb.execSQL("CREATE TABLE  " + TableVaccineChart +
                "(vaccine_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "baby_id INTEGER, " +
                "baby_name TEXT, " +
                "vaccine_name TEXT," +
                "due_date TEXT, " +
                "given_date TEXT, " +
                "dr_name TEXT," +
                "info TEXT, " +
                "note TEXT);");

    }

    public void onUpgrade(SQLiteDatabase sdb, int var2, int var3) {
        sdb.execSQL("DROP TABLE IF EXISTS baby");
        sdb.execSQL("DROP VIEW IF EXISTS viewbaby");
        sdb.execSQL("DROP VIEW IF EXISTS viewvaccine");
        this.onCreate(sdb);
        sdb.execSQL("alter Table Vaccine_Table Rename to "+TableVaccineChart);


        sdb.execSQL("alter table "+TableVaccineChart+" Add baby_id integer");
        ArrayList<Baby> alb = getBabyRecords();
        for (Baby b1: alb) {
            String[]arg = {String.valueOf(b1.getId()),b1.getName()};
            sdb.execSQL("update " + TableVaccineChart + " set baby_id = ? where baby_name= ?",arg );
        }
    }


    /**
     * Table - baby methods - Start
     **/


    public int addBaby(Baby var1) {
        SQLiteDatabase sdb = this.getWritableDatabase();
        ContentValues var3 = new ContentValues();
        var3.put("baby_name", var1.getName());
        var3.put("dob", var1.getDOB());
        var3.put("gender", var1.getgender());
        var3.put("blood", var1.getblood());
        var3.put("rh", var1.getrh());
        var3.put("note", var1.getnote());
        int babyId = (int) sdb.insert(TableBaby, (String) null, var3);
        sdb.close();
        return babyId ;
    }

    public int updateBaby(Baby var1) {
        SQLiteDatabase var3 = this.getWritableDatabase();
        ContentValues var4 = new ContentValues();
        var4.put(colbabyid, var1.getId());
        var4.put("baby_name", var1.getName());
        var4.put("dob", var1.getDOB());
        var4.put("gender", var1.getgender());
        var4.put("blood", var1.getblood());
        var4.put("rh", var1.getrh());
        var4.put("note", var1.getnote());
        int var2 = var3.update(TableBaby, var4, "baby_id=?", new String[]{String.valueOf(var1.getId())});
        var3.close();
        return var2;
    }


    public void deleteBaby(String  babyId) {
        SQLiteDatabase sdb = this.getWritableDatabase();
        sdb.delete(TableVaccineChart,"baby_id=?",new String[]{babyId});
        sdb.delete(TableBaby, "baby_id=?", new String[]{babyId});
        sdb.close();
    }

    public int countBaby() {
        SQLiteDatabase sdb = this.getWritableDatabase();
        Cursor c1 = sdb.rawQuery("SELECT count(baby_name) FROM "+TableBaby, new String[]{});
        c1.moveToFirst();
        int cnt = c1.getInt(0);
        sdb.close();
        return cnt;
    }


    public int countBabyName(String bname) {
        SQLiteDatabase sdb = this.getWritableDatabase();
        int cnt = sdb.rawQuery("SELECT baby_name FROM "+TableBaby+" WHERE baby_name=?", new String[]{bname}).getCount();
        sdb.close();
        return cnt;
    }

    public ArrayList<Baby> getBabyRecords() {
        ArrayList<Baby> al_baby = new ArrayList<>();
        SQLiteDatabase sdb = getReadableDatabase();
        Cursor c1=sdb.rawQuery("SELECT * FROM "+TableBaby, (String[]) null);
        if (c1.moveToFirst()){
            do {
                Baby b1 = new Baby();
                b1.setId(c1.getInt(0));
                b1.setName(c1.getString(1));
                b1.setDOB(c1.getString(2));
                b1.setgender(c1.getString(3));
                b1.setblood(c1.getString(4));
                b1.setRh(c1.getString(5));
                b1.setnote(c1.getString(6));
                al_baby.add(b1);
            } while (c1.moveToNext());
        }
        return al_baby;
    }



    public Cursor getbabyByName(String var1) {
        return this.getReadableDatabase().query(TableBaby, new String[]{"baby_id", "baby_name", "dob", "gender", "blood", "rh", "note"}, "baby_name=?", new String[]{var1}, (String) null, (String) null, (String) null);
    }

    public Baby getBabyByID(String bid) {
        SQLiteDatabase sdb = getReadableDatabase();
        String[] arg = new String[]{bid} ;
        Cursor c1 = sdb.query(TableBaby, new String[]{"baby_id", "baby_name", "dob", "gender", "blood", "rh", "note"}, "baby_id=?",arg , (String) null, (String) null, (String) null);
        Baby b1 = new Baby() ;
        if (c1.moveToFirst()){
            b1.setId(c1.getInt(0));
            b1.setName(c1.getString(1));
            b1.setDOB(c1.getString(2));
            b1.setgender(c1.getString(3));
            b1.setblood(c1.getString(4));
            b1.setRh(c1.getString(5));
            b1.setnote(c1.getString(6));
        }
        return b1;
    }



    /** Table - baby methods - End **/

    /**
     * Table - vaccine methods - Start
     **/
    public int createVaccineChart(int babyId, String var1, String var2, String var3, String var4) {
        SQLiteDatabase sdb = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(colbabyid,babyId);
        cv.put("baby_name", var1);
        cv.put("vaccine_name", var2);
        cv.put("due_date", var3);
        cv.put("given_date", var4);
        cv.put("dr_name", " ");
        cv.put("note", " ");
        int vcID=(int) sdb.insert(TableVaccineChart, (String) null, cv);
        sdb.close();
        return vcID ;
    }


    public void deleteImmunTable(Baby var1) {
        SQLiteDatabase var2 = this.getWritableDatabase();
        var2.delete(TableVaccineChart, "baby_id=?", new String[]{String.valueOf(var1.getId())});
        var2.close();
    }

/*
    public Cursor getVaccinebabyByName(String var1) {
        return this.getReadableDatabase().query(TableVaccineChart, new String[]{"vaccine_id", "baby_name", "vaccine_name", "due_date", "given_date", "dr_name", "note"}, "baby_name=?", new String[]{var1}, (String) null, (String) null, (String) null);
    }

 */

    public Cursor getVaccineChartByID(String babyID) {
        SQLiteDatabase sdb =  this.getReadableDatabase() ;
        Cursor c1 = sdb.query(TableVaccineChart, new String[]{"vaccine_id", "baby_name", "vaccine_name", "due_date", "given_date", "dr_name", "note"}, "baby_id=?", new String[]{babyID}, (String) null, (String) null, (String) null);
        return c1 ;
    }


    public ArrayList<VaccineChart> getVaccineChartList(String babyId) {
        SQLiteDatabase sdb = getReadableDatabase();
        ArrayList<VaccineChart> al_vc = new ArrayList<>() ;
        Cursor c1 = sdb.query(TableVaccineChart, new String[]{"vaccine_id", "baby_id", "baby_name", "vaccine_name", "due_date", "given_date", "dr_name", "note"}, "baby_id=?", new String[]{babyId}, (String) null, (String) null, (String) null);
        if (c1.moveToFirst()){
            do{
                VaccineChart vc = new VaccineChart();
                vc.setVchartID(c1.getInt(0));
                vc.setBabyID(c1.getInt(1));
                vc.setBname(c1.getString(2));
                vc.setVacname(c1.getString(3));
                vc.setVduedate(c1.getString(4));
                vc.setVgivendate(c1.getString(5));
                vc.setDocname(c1.getString(6));
                vc.setNote(c1.getString(7));
                al_vc.add(vc);
            }while (c1.moveToNext());
        }
        return al_vc ;
    }

    public VaccineChart getVaccineChartRecord(String vacId) {
        SQLiteDatabase sdb = getReadableDatabase();
        VaccineChart vc = new VaccineChart();
        Cursor c1 = sdb.query(TableVaccineChart, new String[]{"vaccine_id", "baby_id", "baby_name", "vaccine_name", "due_date", "given_date", "dr_name", "note"}, "vaccine_id=?", new String[]{vacId}, (String) null, (String) null, (String) null);
        if (c1.moveToFirst()){

                vc.setVchartID(c1.getInt(0));
                vc.setBabyID(c1.getInt(1));
                vc.setBname(c1.getString(2));
                vc.setVacname(c1.getString(3));
                vc.setVduedate(c1.getString(4));
                vc.setVgivendate(c1.getString(5));
                vc.setDocname(c1.getString(6));
                vc.setNote(c1.getString(7));

        }
        return vc ;
    }

    public int updateVaccineChartStatus(VaccineChart vc) {
        SQLiteDatabase var3 = this.getWritableDatabase();
        ContentValues var4 = new ContentValues();
        var4.put("given_date", vc.getVgivendate());
        var4.put("dr_name", vc.getDocname());
        var4.put("note", vc.getNote());
        int var2 = var3.update(TableVaccineChart, var4, "vaccine_id=?", new String[]{String.valueOf(vc.getVchartID())});
        var3.close();
        return var2;
    }

}

