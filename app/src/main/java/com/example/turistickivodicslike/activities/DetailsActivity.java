package com.example.turistickivodicslike.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.turistickivodicslike.R;
import com.example.turistickivodicslike.adapters.SlikaAdapter;
import com.example.turistickivodicslike.db.DatabaseHelper;
import com.example.turistickivodicslike.db.model.Atrakcija;
import com.example.turistickivodicslike.db.model.Slike;
import com.example.turistickivodicslike.dialog.AboutDialog;
import com.example.turistickivodicslike.settings.SettingsActivity;
import com.example.turistickivodicslike.tools.Tools;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity implements SlikaAdapter.OnItemClickListener {

    private Toolbar toolbar;
    private ArrayList<String> drawerItems;
    private ListView drawerList;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private RelativeLayout drawerPane;

    private ImageView preview;
    private String imagePath = null;
    private static final int SELECT_PICTURE = 1;

    private Atrakcija atrakcija;
    private DatabaseHelper databaseHelper;

    private RecyclerView rec_list;
    private SlikaAdapter adapter;

    private SharedPreferences prefs;
    public static final String NOTIF_CHANNEL_ID = "notif_channel_007";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_details );

        showDetalji();

        setupToolbar();
        fillDataDrawer();
        setupDrawer();

        createNotificationChannel();
        prefs = PreferenceManager.getDefaultSharedPreferences( this );

    }

    private void refreshNekretnine() {
        int nekretninaId = getIntent().getExtras().getInt( MainActivity.ATRAKCIJA_ID );

        try {
            atrakcija = getDatabaseHelper().getAtrakcijaDao().queryForId( nekretninaId );

            TextView naziv = findViewById( R.id.detalji_naziv );
            TextView opis = findViewById( R.id.detalji_opis );
            TextView telefon = findViewById( R.id.detalji_telefon );
            TextView adresa = findViewById( R.id.detalji_adresa );
            TextView webAdresa = findViewById( R.id.detalji_webAdresa );
            TextView radnoVreme = findViewById( R.id.detalji_radnoVreme );
            TextView cena = findViewById( R.id.detalji_cena );


            naziv.setText( atrakcija.getmNaziv() );
            opis.setText( atrakcija.getmOpis() );
            telefon.setText( atrakcija.getmBrojTelefona() );
            adresa.setText( "Adresa: " + atrakcija.getmAdresa() );
            webAdresa.setText( "Web adresa: " + atrakcija.getmWebAdresa() );
            radnoVreme.setText( "Radno vreme: " + atrakcija.getmRadnoVreme() );
            cena.setText( "Cena: " + atrakcija.getmCena() + " dinara" );


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showDetalji() {
        int nekretninaId = getIntent().getExtras().getInt( MainActivity.ATRAKCIJA_ID );

        try {
            atrakcija = getDatabaseHelper().getAtrakcijaDao().queryForId( nekretninaId );

            TextView naziv = findViewById( R.id.detalji_naziv );
            TextView opis = findViewById( R.id.detalji_opis );
            TextView telefon = findViewById( R.id.detalji_telefon );
            TextView adresa = findViewById( R.id.detalji_adresa );
            TextView webAdresa = findViewById( R.id.detalji_webAdresa );
            TextView radnoVreme = findViewById( R.id.detalji_radnoVreme );
            TextView cena = findViewById( R.id.detalji_cena );

            TextView poruka = findViewById( R.id.detalji_poruka );

            naziv.setText( atrakcija.getmNaziv() );
            opis.setText( atrakcija.getmOpis() );
            telefon.setText( atrakcija.getmBrojTelefona() );
            adresa.setText( "Adresa: " + atrakcija.getmAdresa() );
            webAdresa.setText( "Web adresa: " + atrakcija.getmWebAdresa() );
            radnoVreme.setText( "Radno vreme: " + atrakcija.getmRadnoVreme() );
            cena.setText( "Cena: " + atrakcija.getmCena() + " dinara" );

            poruka.setText( "      Posaljite poruku  " + atrakcija.getmBrojTelefona() );

            telefon.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    Intent intent = new Intent( Intent.ACTION_DIAL );
//                    intent.setData( Uri.parse( "tel:" + atrakcija.getmBrojTelefona() ) );
//                    startActivity( intent );

                    startActivity( new Intent( Intent.ACTION_DIAL, Uri.parse( "tel:" + atrakcija.getmBrojTelefona() ) ) );
                }
            } );

            poruka.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    Intent intent = new Intent( Intent.ACTION_SENDTO );
//                    intent.setData( Uri.parse( "smsto:" + atrakcija.getmBrojTelefona() ) );
//                    startActivity( intent );

                    startActivity( new Intent( Intent.ACTION_SENDTO, Uri.parse( "smsto:" + atrakcija.getmBrojTelefona() ) ) );

                }
            } );

            webAdresa.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url;
                    if (atrakcija.getmWebAdresa().startsWith( "http://" )) {
                        url = atrakcija.getmWebAdresa();
                    } else {
                        url = "http://" + atrakcija.getmWebAdresa();
                    }
                    startActivity( new Intent( Intent.ACTION_VIEW, Uri.parse( url ) ) );

                }
            } );

            adresa.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mapIntent = new Intent( Intent.ACTION_VIEW, null );
                    mapIntent.setPackage( "com.google.android.apps.maps" );
                    startActivity( mapIntent );

                }
            } );

        } catch (SQLException e) {
            e.printStackTrace();
        }

        rec_list = findViewById( R.id.rvList );
    }

    private void deleteAtrakcija() {
        AlertDialog dialogDelete = new AlertDialog.Builder( this )
                .setTitle( "Brisanje atrakcije" )
                .setMessage( "Da li zelite da obrisete \"" + atrakcija.getmNaziv() + "\"?" )
                .setPositiveButton( "DA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        try {
                            List<Slike> slike = getDatabaseHelper().getSlikeDao().queryForEq( "atrakcija", atrakcija.getmId() );

                            getDatabaseHelper().getAtrakcijaDao().delete( atrakcija );

                            String tekstNotifikacije = "Atrakcija je obrisana";

                            boolean toast = prefs.getBoolean( getString( R.string.toast_key ), false );
                            boolean notif = prefs.getBoolean( getString( R.string.notif_key ), false );

                            if (toast) {
                                Toast.makeText( DetailsActivity.this, tekstNotifikacije, Toast.LENGTH_LONG ).show();

                            }
                            if (notif) {
                                NotificationManager notificationManager = (NotificationManager) getSystemService( Context.NOTIFICATION_SERVICE );
                                NotificationCompat.Builder builder = new NotificationCompat.Builder( DetailsActivity.this, NOTIF_CHANNEL_ID );
                                builder.setSmallIcon( android.R.drawable.ic_menu_delete );
                                builder.setContentTitle( "Notifikacija" );
                                builder.setContentText( tekstNotifikacije );

                                Bitmap bitmap = BitmapFactory.decodeResource( getResources(), R.mipmap.ic_launcher_foreground );


                                builder.setLargeIcon( bitmap );
                                notificationManager.notify( 1, builder.build() );

                            }

                            for (Slike slika : slike) {
                                getDatabaseHelper().getSlikeDao().delete( slika );
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        finish();
                    }
                } )
                .setNegativeButton( "NE", null )
                .show();
    }


    private void editAtrakcija() {
        final Dialog dialog = new Dialog( this );
        dialog.setContentView( R.layout.edit_layout );
        dialog.setCanceledOnTouchOutside( false );

        final EditText naziv = dialog.findViewById( R.id.edit_naziv );
        final EditText opis = dialog.findViewById( R.id.edit_opis );
        final EditText telefon = dialog.findViewById( R.id.edit_telefon );
        final EditText adresa = dialog.findViewById( R.id.edit_adresa );
        final EditText webAdresa = dialog.findViewById( R.id.edit_webAdresa );
        final EditText radnoVreme = dialog.findViewById( R.id.edit_RadnoVreme );
        final EditText cena = dialog.findViewById( R.id.edit_cena );


        naziv.setText( atrakcija.getmNaziv() );
        opis.setText( atrakcija.getmOpis() );
        telefon.setText( atrakcija.getmBrojTelefona() );
        adresa.setText( atrakcija.getmAdresa() );
        webAdresa.setText( atrakcija.getmWebAdresa() );
        radnoVreme.setText( atrakcija.getmRadnoVreme() );
        cena.setText( atrakcija.getmCena() );

        Button add = dialog.findViewById( R.id.edit_btn_save );
        add.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Tools.validateInput( naziv )
                        && Tools.validateInput( opis )
                        && Tools.validateInput( adresa )
                        && Tools.validateInput( telefon )
                        && Tools.validateInput( webAdresa )
                        && Tools.validateInput( radnoVreme )
                        && Tools.validateInput( cena )
                ) {


                    atrakcija.setmNaziv( naziv.getText().toString() );
                    atrakcija.setmOpis( opis.getText().toString() );
                    atrakcija.setmBrojTelefona( telefon.getText().toString() );
                    atrakcija.setmAdresa( adresa.getText().toString() );
                    atrakcija.setmWebAdresa( webAdresa.getText().toString() );
                    atrakcija.setmRadnoVreme( radnoVreme.getText().toString() );
                    atrakcija.setmCena( cena.getText().toString() );


                    try {
                        getDatabaseHelper().getAtrakcijaDao().update( atrakcija );

                        String tekstNotifikacije = "Atrakcija je izmenjena";

                        boolean toast = prefs.getBoolean( getString( R.string.toast_key ), false );
                        boolean notif = prefs.getBoolean( getString( R.string.notif_key ), false );

                        if (toast) {
                            Toast.makeText( DetailsActivity.this, tekstNotifikacije, Toast.LENGTH_LONG ).show();

                        }
                        if (notif) {
                            NotificationManager notificationManager = (NotificationManager) getSystemService( Context.NOTIFICATION_SERVICE );
                            NotificationCompat.Builder builder = new NotificationCompat.Builder( DetailsActivity.this, NOTIF_CHANNEL_ID );
                            builder.setSmallIcon( android.R.drawable.ic_menu_edit );
                            builder.setContentTitle( "Notifikacija" );
                            builder.setContentText( tekstNotifikacije );

                            Bitmap bitmap = BitmapFactory.decodeResource( getResources(), R.mipmap.ic_launcher_foreground );


                            builder.setLargeIcon( bitmap );
                            notificationManager.notify( 1, builder.build() );

                        }

                        refreshNekretnine();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    dialog.dismiss();

                }
            }

        } );

        Button cancel = dialog.findViewById( R.id.edit_btn_cancel );
        cancel.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        } );

        dialog.show();
    }

    private void reset() {
        imagePath = "";
        preview = null;
    }

    private void refresh() {

        List<Slike> listaSlika = null;
        try {
            listaSlika = getDatabaseHelper().getSlikeDao().queryBuilder()
                    .where()
                    .eq( "atrakcija", atrakcija.getmId() )
                    .query();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (adapter != null) {
            adapter.clear();
            adapter.addAll( listaSlika );
            adapter.notifyDataSetChanged();
        } else {
            adapter = new SlikaAdapter( DetailsActivity.this, listaSlika, DetailsActivity.this );

            rec_list.setHasFixedSize( true );


            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( DetailsActivity.this, LinearLayoutManager.HORIZONTAL, false );
            rec_list.setLayoutManager( layoutManager );

            rec_list.setAdapter( adapter );
        }

    }

    private void addSlike() {

        final Dialog dialog = new Dialog( this );
        dialog.setContentView( R.layout.add_slike );
        dialog.setTitle( "Unesite podatke" );
        dialog.setCanceledOnTouchOutside( false );

        Button chooseBtn = dialog.findViewById( R.id.btn_izaberi );
        chooseBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preview = dialog.findViewById( R.id.preview_image1 );
                selectPicture();
            }
        } );

        Button add = dialog.findViewById( R.id.add_atrakcija );
        add.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (preview == null || imagePath == null) {
                    Toast.makeText( DetailsActivity.this, "Slika mora biti izabrana", Toast.LENGTH_SHORT ).show();
                    return;
                }

                int position = getIntent().getExtras().getInt( "position" );
                try {
                    atrakcija = getDatabaseHelper().getAtrakcijaDao().queryForId( position );
                    Slike slike = new Slike();

                    slike.setmSlika( imagePath );
                    slike.setmAtrakcija( atrakcija );
                    getDatabaseHelper().getSlikeDao().create( slike );

                    refresh();
                    reset();

                } catch (SQLException e) {
                    e.printStackTrace();
                }


                dialog.dismiss();

            }

        } );

        Button cancel = dialog.findViewById( R.id.cancel );
        cancel.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        } );

        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.details_menu, menu );
        return super.onCreateOptionsMenu( menu );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_slika:
                addSlike();
                setTitle( "Dodavanje slike" );
                break;

            case R.id.edit:
                editAtrakcija();
                setTitle( "Izmena atrakcija" );
                break;

            case R.id.delete:
                deleteAtrakcija();
                setTitle( "Brisanje atrakcija" );
                break;
        }

        return super.onOptionsItemSelected( item );
    }

    private void setupDrawer() {
        drawerList = findViewById( R.id.left_drawer );
        drawerLayout = findViewById( R.id.drawer_layout );
        drawerPane = findViewById( R.id.drawerPane );
        drawerList.setAdapter( new ArrayAdapter<String>( this, android.R.layout.simple_list_item_1, drawerItems ) );

        drawerList.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String title = "Unknown";
                switch (i) {
                    case 0:
                        title = "Lista atrakcija";
                        finish();
                        break;
                    case 1:
                        Toast.makeText( getBaseContext(), "Prikaz podesavanja", Toast.LENGTH_SHORT );
                        startActivity( new Intent( DetailsActivity.this, SettingsActivity.class ) );
                        title = "Settings";
                        break;
                    case 2:
                        Toast.makeText( getBaseContext(), "Prikaz o aplikaciji", Toast.LENGTH_SHORT );
                        AboutDialog dialog = new AboutDialog( DetailsActivity.this );
                        dialog.show();
                        title = "O aplikaciji";
                        break;
                    default:
                        break;
                }
                setTitle( title );
                drawerLayout.closeDrawer( Gravity.LEFT );
            }
        } );

        drawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.app_name,
                R.string.app_name
        ) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle( "" );
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle( "" );
                invalidateOptionsMenu();
            }
        };
    }

    private void fillDataDrawer() {
        drawerItems = new ArrayList<>();
        drawerItems.add( "Lista atrakcija" );
        drawerItems.add( "Settings" );
        drawerItems.add( "O aplikaciji" );
    }

    public void setupToolbar() {
        toolbar = findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        toolbar.setTitleTextColor( Color.WHITE );
        toolbar.setSubtitle( "Detalji atrakcija" );
        toolbar.setLogo( R.drawable.heart );

        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled( true );
            actionBar.setHomeAsUpIndicator( R.drawable.drawer );
            actionBar.setHomeButtonEnabled( true );
            actionBar.show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        if (requestCode == SELECT_PICTURE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            if (selectedImage != null) {
                Cursor cursor = getContentResolver().query( selectedImage, filePathColumn, null, null, null );
                if (cursor != null) {
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex( filePathColumn[0] );
                    imagePath = cursor.getString( columnIndex );
                    cursor.close();

                    if (preview != null) {
                        preview.setImageBitmap( BitmapFactory.decodeFile( imagePath ) );
                    }
                }
            }
        }
    }


    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission( Manifest.permission.WRITE_EXTERNAL_STORAGE )
                    == PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission( Manifest.permission.READ_EXTERNAL_STORAGE )
                            == PackageManager.PERMISSION_GRANTED) {

                return true;
            } else {


                ActivityCompat.requestPermissions( this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE}, 1 );
                return false;
            }
        } else {

            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult( requestCode, permissions, grantResults );
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED
                && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

        }
    }

    private void selectPicture() {
        if (isStoragePermissionGranted()) {
            Intent i = new Intent( Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI );
            startActivityForResult( i, SELECT_PICTURE );
        }
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }

    }

    public DatabaseHelper getDatabaseHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper( this, DatabaseHelper.class );
        }
        return databaseHelper;
    }

    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "My Channel";
            String description = "Description of My Channel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel( NOTIF_CHANNEL_ID, name, importance );
            channel.setDescription( description );

            NotificationManager notificationManager = getSystemService( NotificationManager.class );
            notificationManager.createNotificationChannel( channel );
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    @Override
    public void onItemClick(int position) {

    }
}

