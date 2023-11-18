package com.example.iclean.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.example.iclean.R;
import com.example.iclean.adapter.Session;
import com.example.iclean.adapter.ViewPagerAdapter;
import com.example.iclean.auth.SignInActivity;
import com.example.iclean.databinding.ActivityHomeBinding;
import com.example.iclean.fragment.CartFragment;
import com.example.iclean.fragment.HistoryFragment;
import com.example.iclean.fragment.HomeFragment;
import com.example.iclean.fragment.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomeActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener, ViewPager.OnPageChangeListener {

    // deklarasi variable
    ViewPager pager;
    private ActivityHomeBinding binding;
    BottomNavigationView navView;

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

        navView = findViewById(R.id.nav_view);
        navView.setOnItemSelectedListener(this);

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

    // BIAR INTENT BISA DIKIRIM
    public String getUSERDATA_NOHAPE() {
        return INTENT_NOMOR_HAPE;
    }
    public String getUSERDATA_ID(){
        return INTENT_ID_USER;
    }


    public void pencet(){ // Method untuk fragment logout dari  profile manager t
        Session.getInstance((this)).logout();
        finish();
        startActivity(new Intent(this, SignInActivity.class));

    }

    public void tombol_map(){ // Intent menuju map activity

        startActivity(new Intent(this,MapsActivity.class));
    }

    public void tombol_webview(){ // Intent menuju webciew activity

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