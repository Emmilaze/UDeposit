package com.app.udeposits;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.app.udeposits.core.deposit.DepositManager;
import com.app.udeposits.ui.gallery.GalleryFragment;
import com.app.udeposits.ui.gallery.GalleryViewModel;
import com.app.udeposits.ui.share.ShareFragment;
import com.app.udeposits.ui.share.ShareViewModel;
import com.app.udeposits.ui.slideshow.SlideshowFragment;
import com.app.udeposits.ui.slideshow.SlideshowViewModel;
import com.app.udeposits.ui.tools.ToolsFragment;
import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        new DepositManager().fillLists(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void onClickUAH(View view) {
        GalleryFragment.currency = "UAH";
        setDefaultFields();
        move(new GalleryFragment());
    }

    public void onClickUSD(View view) {
        GalleryFragment.currency = "USD";
        setDefaultFields();
        move(new GalleryFragment());
    }

    public void onClickEURO(View view) {
        GalleryFragment.currency = "EURO";
        setDefaultFields();
        move(new GalleryFragment());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void search(View view) {
        String[] fields = SlideshowViewModel.getFields(SlideshowFragment.root);
        GalleryViewModel.fields = fields;
        GalleryFragment.clicked = false;
        move(new GalleryFragment());
    }

    private void move(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(android.R.animator.fade_in,
                android.R.animator.fade_out).replace(R.id.nav_host_fragment, fragment);
        transaction.commit();
    }

    public void calculate(View view) {
        ToolsFragment.calculate();
    }

    public void getMoreInfo(View view) {
        if (!GalleryFragment.clicked) {
            Toast toast = Toast.makeText(GalleryFragment.root.getContext(),
                    "Выберите депозит из списка",
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM, 0, 0);
            toast.show();
        } else {
            move(new ShareFragment());
        }
    }

    public void goToCalc(View view) {
        if (!GalleryFragment.clicked) {
            Toast toast = Toast.makeText(GalleryFragment.root.getContext(),
                    "Выберите депозит из списка",
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM, 0, 0);
            toast.show();
        } else {
            move(new ToolsFragment());
        }
    }

    private void setDefaultFields() {
        GalleryViewModel.fields = null;
        GalleryFragment.clicked = false;
        ShareViewModel.search = null;
    }
}