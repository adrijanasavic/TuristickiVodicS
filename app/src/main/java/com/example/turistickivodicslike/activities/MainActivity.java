package com.example.turistickivodicslike.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.turistickivodicslike.R;
import com.example.turistickivodicslike.dialog.AboutDialog;
import com.example.turistickivodicslike.settings.SettingsActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ArrayList<String> drawerItems;
    private ListView drawerList;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private RelativeLayout drawerPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        setupToolbar();
        fillDataDrawer();
        setupDrawer();
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

                        break;
                    case 1:
                        Toast.makeText( getBaseContext(), "Prikaz podesavanja", Toast.LENGTH_SHORT );
                        startActivity( new Intent( MainActivity.this, SettingsActivity.class ) );
                        title = "Settings";
                        break;
                    case 2:
                        Toast.makeText( getBaseContext(), "Prikaz o aplikaciji", Toast.LENGTH_SHORT );
                        AboutDialog dialog = new AboutDialog( MainActivity.this );
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
        toolbar.setSubtitle( "Lista atrakcija" );
        toolbar.setLogo( R.drawable.heart );

        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled( true );
            actionBar.setHomeAsUpIndicator( R.drawable.drawer );
            actionBar.setHomeButtonEnabled( true );
            actionBar.show();
        }
    }

}
