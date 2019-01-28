package com.example.windowsv8.e_voting.activity.pemilih;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.windowsv8.e_voting.R;
import com.example.windowsv8.e_voting.activity.LoginActivity;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener{

    private TextView textViewName, textViewNpm;
    private ImageView imageView;
    private CardView cardView, cardView1, cardView2, cardView3, cardView4, cardView5;
    private View linearLayout, view;

    SharedPreferences sharedPreferences;
    public static final String shared_preference_user = "shared_preference_user";
    public static final String TAG_SESSION = "session_status";
    public final static String TAG_NPM = "npm";
    public final static String TAG_NAME = "name";
    public final static String TAG_IMAGE = "image";
    public final static String TAG_PROFILE = "profile";
    public final static String TAG_VERIF = "verifikasi";
    public final static String TAG_PILIH = "pilih";

    String npm, name, image, profile, verifikasi, memilih;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = findViewById(R.id.nav_view);

        view = navigationView.getHeaderView(0);
        textViewNpm = view.findViewById(R.id.textViewNpmMain);
        textViewName = view.findViewById(R.id.textViewNameMain);
        imageView = view.findViewById(R.id.imageViewMain);
        linearLayout = view.findViewById(R.id.linearLayoutHome);

        cardView = findViewById(R.id.cardViewHomeDaftarCalon);
        cardView1 = findViewById(R.id.cardViewHomeDaftarPemilih);
        cardView2 = findViewById(R.id.cardViewHomePemungutan);
        cardView3 = findViewById(R.id.cardViewHomePerhitungan);
        cardView4 = findViewById(R.id.cardViewHomeDaftarPanitia);
        cardView5 = findViewById(R.id.cardViewHomePetunjuk);

        sharedPreferences = getSharedPreferences(shared_preference_user, Context.MODE_PRIVATE);
        npm = sharedPreferences.getString(TAG_NPM, "npm");
        name = sharedPreferences.getString(TAG_NAME, "name");
        image = sharedPreferences.getString(TAG_IMAGE, "image");
        profile = sharedPreferences.getString(TAG_PROFILE, "profile");
        verifikasi = sharedPreferences.getString(TAG_VERIF, "verifikasi");
        memilih = sharedPreferences.getString(TAG_PILIH, "memilih");

        textViewNpm.setText(sharedPreferences.getString(TAG_NPM, "npm"));
        textViewName.setText(sharedPreferences.getString(TAG_NAME, "name"));
        Glide.with(this).load(profile).apply(new RequestOptions().circleCrop()).into(imageView);

        FirebaseMessaging.getInstance().subscribeToTopic("allDevices");

        //Log.e("verifikasi", sharedPreferences.getString(TAG_PILIH, null));

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        cardView.setOnClickListener(this);
        cardView1.setOnClickListener(this);
        cardView2.setOnClickListener(this);
        cardView3.setOnClickListener(this);
        cardView4.setOnClickListener(this);
        cardView5.setOnClickListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(TAG_SESSION, false);
            editor.putString(TAG_NPM, null);
            editor.putString(TAG_NAME, null);
            editor.putString(TAG_IMAGE, null);
            editor.putString(TAG_PROFILE, null);
            editor.putString(TAG_VERIF, null);
            editor.putString(TAG_PILIH, null);
            editor.apply();

            startActivity(new Intent(this, LoginActivity.class));
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cardViewHomeDaftarCalon:
                startActivity(new Intent(MainActivity.this, DaftarCalonActivity.class));
                finish();
                Toast.makeText(getApplicationContext(), "1", Toast.LENGTH_SHORT).show();
                break;

            case R.id.cardViewHomeDaftarPemilih:
                startActivity(new Intent(MainActivity.this, JumlahPemilihActivity.class));
                finish();
                Toast.makeText(getApplicationContext(),"2", Toast.LENGTH_SHORT).show();
                break;

            case R.id.cardViewHomePemungutan:
                if (verifikasi.equals("1")){
                    if (memilih.equals("0")){
                        startActivity(new Intent(MainActivity.this, PemilihanActivity.class));
                        finish();
                    }
                    else {
                        Snackbar.make(view, "Maaf, Anda sudah memilih sebelumnya", Snackbar.LENGTH_LONG).show();
                    }
                }
                else {
                    Snackbar.make(view, "Maaf, Tunggu sampai panitia verifikasi data anda", Snackbar.LENGTH_LONG).show();
                }
                Toast.makeText(getApplicationContext(),"3", Toast.LENGTH_SHORT).show();
                break;

            case R.id.cardViewHomePerhitungan:
                startActivity(new Intent(MainActivity.this, PerhitunganSuaraActivity.class));
                finish();
                Toast.makeText(getApplicationContext(),"4", Toast.LENGTH_SHORT).show();
                break;

            case R.id.cardViewHomeDaftarPanitia:
                startActivity(new Intent(MainActivity.this, DaftarAdminActivity.class));
                finish();
                Toast.makeText(getApplicationContext(),"5", Toast.LENGTH_SHORT).show();
                break;

            case R.id.cardViewHomePetunjuk:
                Toast.makeText(getApplicationContext(),"6", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
