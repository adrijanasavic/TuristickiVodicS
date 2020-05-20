package com.example.turistickivodicslike.db.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "slike")
public class Slike {

    @DatabaseField(generatedId = true)
    private int mId;

    @DatabaseField(columnName = "mSlika")
    private String mSlika;


    @DatabaseField(columnName = "atrakcija", foreign = true, foreignAutoRefresh = true)
    private Atrakcija mAtrakcija;


    public Slike() {
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmSlika() {
        return mSlika;
    }

    public void setmSlika(String mSlika) {
        this.mSlika = mSlika;
    }

    public Atrakcija getmAtrakcija() {
        return mAtrakcija;
    }

    public void setmAtrakcija(Atrakcija mAtrakcija) {
        this.mAtrakcija = mAtrakcija;
    }
}
