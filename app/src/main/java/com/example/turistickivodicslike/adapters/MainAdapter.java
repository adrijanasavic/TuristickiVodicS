package com.example.turistickivodicslike.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.turistickivodicslike.R;
import com.example.turistickivodicslike.db.DatabaseHelper;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;


public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {

    private Context context;
    private DatabaseHelper databaseHelper;
    private OnItemClickListener listener;


    public MainAdapter(DatabaseHelper databaseHelper, OnItemClickListener listener) {
        this.databaseHelper = databaseHelper;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.single_item, parent, false );

        return new MyViewHolder( view, listener );
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        try {

            holder.tvNaziv.setText( getDatabaseHelper().getAtrakcijaDao().queryForAll().get( position ).getmNaziv() );
            holder.tvAdresa.setText( "Adresa: " + getDatabaseHelper().getAtrakcijaDao().queryForAll().get( position ).getmAdresa() );
            holder.tvTelefon.setText( "Telefon: " + getDatabaseHelper().getAtrakcijaDao().queryForAll().get( position ).getmBrojTelefona() );
            holder.tvRadnoVreme.setText( "Radno vreme: " + getDatabaseHelper().getAtrakcijaDao().queryForAll().get( position ).getmRadnoVreme() + "h" );
            holder.tvCena.setText( "Cena: " + getDatabaseHelper().getAtrakcijaDao().queryForAll().get( position ).getmCena() + "din." );

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        int list = 0;
        try {
            list = getDatabaseHelper().getAtrakcijaDao().queryForAll().size();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvNaziv;
        private TextView tvAdresa;
        private TextView tvTelefon;
        private TextView tvRadnoVreme;
        private TextView tvCena;

        private OnItemClickListener vhListener;

        public MyViewHolder(@NonNull View itemView, OnItemClickListener vhListener) {
            super( itemView );

            tvNaziv = itemView.findViewById( R.id.tvNaziv );
            tvAdresa = itemView.findViewById( R.id.tvAdresa );
            tvTelefon = itemView.findViewById( R.id.tvBrojTelefona );
            tvRadnoVreme = itemView.findViewById( R.id.tvRadnoVreme );
            tvCena = itemView.findViewById( R.id.tvCena );
            this.vhListener = vhListener;
            itemView.setOnClickListener( this );

        }

        @Override
        public void onClick(View v) {
            vhListener.onItemClick( getAdapterPosition() );
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public DatabaseHelper getDatabaseHelper() {
        if (databaseHelper == null) {

            databaseHelper = OpenHelperManager.getHelper( context, DatabaseHelper.class );
        }
        return databaseHelper;
    }
}
