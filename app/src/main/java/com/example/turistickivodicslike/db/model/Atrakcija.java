package com.example.turistickivodicslike.db.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "atrakcija")
public class Atrakcija {

    @DatabaseField(generatedId = true)
    private int mId;

    @DatabaseField(columnName = "mNaziv")
    private String mNaziv;

    @DatabaseField(columnName = "mOpis")
    private String mOpis;

    @DatabaseField(columnName = "mBrojTelefona")
    private String mBrojTelefona;

    @DatabaseField(columnName = "mAdresa")
    private String mAdresa;

    @DatabaseField(columnName = "mWebAdresa")
    private String mWebAdresa;

    @DatabaseField(columnName = "mRadnoVreme")
    private String mRadnoVreme;

    @DatabaseField(columnName = "mCena")
    private String mCena;

    @DatabaseField(columnName = "mKomentar")
    private String mKomentar;

    @ForeignCollectionField(columnName = "mSlike", eager = true)
    private ForeignCollection<Slike> mSlike;

    //ORMLite zahteva prazan konstuktur u klasama koje opisuju tabele u bazi!
    public Atrakcija() {
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmNaziv() {
        return mNaziv;
    }

    public void setmNaziv(String mNaziv) {
        this.mNaziv = mNaziv;
    }

    public String getmOpis() {
        return mOpis;
    }

    public void setmOpis(String mOpis) {
        this.mOpis = mOpis;
    }

    public String getmBrojTelefona() {
        return mBrojTelefona;
    }

    public void setmBrojTelefona(String mBrojTelefona) {
        this.mBrojTelefona = mBrojTelefona;
    }

    public String getmAdresa() {
        return mAdresa;
    }

    public void setmAdresa(String mAdresa) {
        this.mAdresa = mAdresa;
    }

    public String getmWebAdresa() {
        return mWebAdresa;
    }

    public void setmWebAdresa(String mWebAdresa) {
        this.mWebAdresa = mWebAdresa;
    }

    public String getmRadnoVreme() {
        return mRadnoVreme;
    }

    public void setmRadnoVreme(String mRadnoVreme) {
        this.mRadnoVreme = mRadnoVreme;
    }

    public String getmCena() {
        return mCena;
    }

    public void setmCena(String mCena) {
        this.mCena = mCena;
    }

    public String getmKomentar() {
        return mKomentar;
    }

    public void setmKomentar(String mKomentar) {
        this.mKomentar = mKomentar;
    }

    public ForeignCollection<Slike> getmSlike() {
        return mSlike;
    }

    public void setmSlike(ForeignCollection<Slike> mSlike) {
        this.mSlike = mSlike;
    }
}
