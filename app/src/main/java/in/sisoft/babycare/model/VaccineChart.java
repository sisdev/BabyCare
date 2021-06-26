package in.sisoft.babycare.model;

public class VaccineChart {
    public int vchartID;
    public int babyID ;

        public String bname;
    public String vacname;
    public String vduedate;
    public String vgivendate;
    public String docname;
    public String note;


    public int getVchartID() {
        return vchartID;
    }

    public void setVchartID(int vchartID) {
        this.vchartID = vchartID;
    }

    public int getBabyID() {
        return babyID;
    }

    public void setBabyID(int babyID) {
        this.babyID = babyID;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public String getVacname() {
        return vacname;
    }

    public void setVacname(String vacname) {
        this.vacname = vacname;
    }

    public String getVduedate() {
        return vduedate;
    }

    public void setVduedate(String vduedate) {
        this.vduedate = vduedate;
    }

    public String getVgivendate() {
        return vgivendate;
    }

    public void setVgivendate(String vgivendate) {
        this.vgivendate = vgivendate;
    }

    public String getDocname() {
        return docname;
    }

    public void setDocname(String docname) {
        this.docname = docname;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


    public VaccineChart() {
    }

    public VaccineChart(int var1, String var2, String var3) {
        this.vchartID = var1;
        this.vacname = var2;
        this.vduedate = var3;
    }

    public VaccineChart(int var1, String var2, String var3, String var4, String var5, String var6) {
        this.vchartID = var1;
        this.bname = var2;
        this.docname = var3;
        this.vacname = var4;
        this.vgivendate = var5;
        this.note = var6;
    }

    public VaccineChart(int var1, String var2, String var3, String var4, String var5, String var6, String var7) {
        this.vchartID = var1;
        this.bname = var2;
        this.docname = var3;
        this.vacname = var4;
        this.vduedate = var5;
        this.vgivendate = var6;
        this.note = var7;
    }

    public VaccineChart(String var1) {
        this.bname = var1;
    }

    public VaccineChart(String var1, String var2, String var3, String var4, String var5, String var6) {
        this.bname = var1;
        this.docname = var2;
        this.vacname = var3;
        this.vduedate = var4;
        this.vgivendate = var5;
        this.note = var6;
    }

}
