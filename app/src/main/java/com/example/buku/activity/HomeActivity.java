package com.example.buku.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager.widget.ViewPager;
import com.example.buku.R;
import com.example.buku.adapter.Session;
import com.example.buku.adapter.ViewPagerAdapter;
import com.example.buku.auth.SignInActivity;
import com.example.buku.databinding.ActivityHomeBinding;
import com.example.buku.fragment.CartFragment;
import com.example.buku.fragment.HistoryFragment;
import com.example.buku.fragment.HomeFragment;
import com.example.buku.fragment.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomeActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener, ViewPager.OnPageChangeListener {

    ViewPager pager;
    private ActivityHomeBinding binding;
    BottomNavigationView navView;

    //private String INTENT_NAMA = Session.getInstance(this).getuserName();
   // private String INTENT_EMAIL = Session.getInstance(this).getEMAIL_USER();
    private String INTENT_NOMOR_HAPE = Session.getInstance(this).getusernomer_Hape();

    private String INTENT_ID_USER = Session.getInstance(this).getKeyUserId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if(!Session.getInstance(this).isLoggedin()){
            finish();
            startActivity(new Intent(this, SignInActivity.class));
            return;
        }

//        //batas
//        dbsqlite = new SQLite(getApplicationContext());
//
//        // Fetching user details from SQLite
//        // Menyiapkan data user dari SQLite
//        HashMap<String, String> user = dbsqlite.getUserDetails();
//
//        // dan yang diambil adalah data nama dan email dari SQLite
//         nama = user.get("nama");
//         email = user.get("email");


        navView = findViewById(R.id.nav_view);
        navView.setOnItemSelectedListener(this);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        getSupportActionBar().hide();

        pager = findViewById(R.id.pager);
        pager.setOnPageChangeListener(this);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeFragment());
        adapter.addFragment(new CartFragment());
        adapter.addFragment(new HistoryFragment());
        adapter.addFragment(new ProfileFragment());
        pager.setAdapter(adapter);
    }

    //ambil data untuk nama, email, nohphc 
//    public String getUSERDATA_NAMA() {
//        return INTENT_NAMA;
//    }

//    public String getUSERDATA_EMAIL() {
//        return INTENT_EMAIL;
//    }

    public String getUSERDATA_NOHAPE() {
        return INTENT_NOMOR_HAPE;
    }

    public String getUSERDATA_ID(){
        return INTENT_ID_USER;
    }

    public void pencet(){ // ini method buat log out krn di profile manager adalah fragment
        Session.getInstance((this)).logout();
        finish();
        startActivity(new Intent(this, SignInActivity.class));

    }

    public void tombol_map(){

        startActivity(new Intent(this,MapsActivity.class));
    }

    public void tombol_webview(){

        startActivity(new Intent(this,WebViewActivity.class));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.navigation_home:
                pager.setCurrentItem(0);
                break;
            case R.id.navigation_cart:
                pager.setCurrentItem(1);
                break;
            case R.id.navigation_history:
                pager.setCurrentItem(2);
                break;
            case R.id.navigation_profile:
                pager.setCurrentItem(3);
                break;
        }
        return true;
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        switch (position) {
            case 0:
                navView.getMenu().findItem(R.id.navigation_home).setChecked(true);
                break;
            case 1:
                navView.getMenu().findItem(R.id.navigation_cart).setChecked(true);
                break;
            case 2:
                navView.getMenu().findItem(R.id.navigation_history).setChecked(true);
                break;
            case 3:
                navView.getMenu().findItem(R.id.navigation_profile).setChecked(true);
                break;
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}