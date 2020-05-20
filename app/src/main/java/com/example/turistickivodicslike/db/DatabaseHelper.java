package com.example.turistickivodicslike.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.turistickivodicslike.db.model.Atrakcija;
import com.example.turistickivodicslike.db.model.Slike;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;


public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "baza.db";

    private static final int DATABASE_VERSION = 1;

    private Dao<Atrakcija, Integer> atrakcijaDao = null;
    private Dao<Slike, Integer> slikeDao = null;


    public DatabaseHelper(Context context) {
        super( context, DATABASE_NAME, null, DATABASE_VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {

        try {
            TableUtils.createTable( connectionSource, Atrakcija.class );
            TableUtils.createTable( connectionSource, Slike.class );
        } catch (SQLException e) {
            throw new RuntimeException( e );
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {


        try {
            TableUtils.dropTable( connectionSource, Atrakcija.class, true );
            TableUtils.dropTable( connectionSource, Slike.class, true );
        } catch (SQLException e) {
            throw new RuntimeException( e );
        }
    }

    public Dao<Atrakcija, Integer> getAtrakcijaDao() throws SQLException {
        if (atrakcijaDao == null) {
            atrakcijaDao = getDao( Atrakcija.class );
        }

        return atrakcijaDao;
    }

    public Dao<Slike, Integer> getSlikeDao() throws SQLException {
        if (slikeDao == null) {
            slikeDao = getDao( Slike.class );
        }

        return slikeDao;
    }

    @Override
    public void close() {
        atrakcijaDao = null;
        slikeDao = null;

        super.close();
    }
}
