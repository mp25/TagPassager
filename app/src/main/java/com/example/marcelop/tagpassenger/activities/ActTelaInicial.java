package com.example.marcelop.tagpassenger.activities;


import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.marcelop.tagpassenger.R;
import com.example.marcelop.tagpassenger.fragments.TelaCadastroFragment;
import com.example.marcelop.tagpassenger.fragments.TelaChamadaFragment;
import com.example.marcelop.tagpassenger.fragments.TelaValidaFragment;
import com.example.marcelop.tagpassenger.fragments.TelaViagemFragment;

public class ActTelaInicial extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_tela_inicial);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.act_tela_inicial, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getFragmentManager();
        if (id == R.id.view_chamada) {
            fragmentManager.beginTransaction().replace(R.id.content_frame,new TelaChamadaFragment()).commit();

        } else if (id == R.id.view_validar) {
            fragmentManager.beginTransaction().replace(R.id.content_frame,new TelaValidaFragment()).addToBackStack(null).commit();
        } else if (id == R.id.view_cadastro) {
            fragmentManager.beginTransaction().replace(R.id.content_frame,new TelaCadastroFragment()).addToBackStack(null).commit();
        } else if (id == R.id.view_viagem_ida) {
            fragmentManager.beginTransaction().replace(R.id.content_frame,new TelaViagemFragment()).commit();
        } else if (id == R.id.view_viagem_volta) {
            fragmentManager.beginTransaction().replace(R.id.content_frame,new  TelaViagemFragment()).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
