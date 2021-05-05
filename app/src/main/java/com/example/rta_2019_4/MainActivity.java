package com.example.rta_2019_4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.ActionBar;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private static final String permission_internet = Manifest.permission.INTERNET;
    private static final String permission_find_location = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String permission_coarse_location = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final String permission_netword_state = Manifest.permission.ACCESS_NETWORK_STATE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestPermission();

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPaper);

        ViewPageAdapter viewPageAdapter = new ViewPageAdapter(getSupportFragmentManager(),
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        viewPager.setAdapter(viewPageAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void requestPermission() {
        String[] perms = {permission_internet,permission_find_location
                ,permission_coarse_location,permission_netword_state};
        if (!EasyPermissions.hasPermissions(this, perms)) {
            EasyPermissions.requestPermissions(this,"Must allow to use this app",
                    RequestCode.REQUEST_CODE_PERMISSION,perms);
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}