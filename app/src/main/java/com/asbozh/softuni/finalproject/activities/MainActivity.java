package com.asbozh.softuni.finalproject.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.asbozh.softuni.finalproject.R;
import com.asbozh.softuni.finalproject.fragments.HomeFragment;
import com.asbozh.softuni.finalproject.fragments.StatisticsFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final long DRAWER_CLOSE_DELAY_MS = 250;
    private static final String NAV_ITEM_ID = "navItemId";

    private Toolbar mToolbar;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private final Handler mDrawerActionHandler = new Handler();
    private int mNavItemId;

    HomeFragment homeFrag;
    AllRecordsActivity allRecsFrag;
    StatisticsFragment statsFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();
        if (savedInstanceState == null) {
            mNavItemId = R.id.nav_home;
        } else {
            mNavItemId = savedInstanceState.getInt(NAV_ITEM_ID);
        }

        initDrawer();

    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);
    }

    private void initDrawer() {
        mNavigationView = (NavigationView) findViewById(R.id.main_drawer);
        mNavigationView.setNavigationItemSelectedListener(this);
        mNavigationView.getMenu().findItem(mNavItemId).setChecked(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.openDrawer, R.string.closeDrawer);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        navigate(mNavItemId);
    }

    private void navigate(int mNavItemId) {

        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction;

        switch(mNavItemId) {
            case R.id.nav_home:
                homeFrag = new HomeFragment();
                transaction = manager.beginTransaction();
                transaction.replace(R.id.fragment_content, homeFrag, "HomeFragment");
                transaction.commit();
                mToolbar.setTitle(getString(R.string.app_name));
                break;
            case R.id.nav_all_records:
                startActivity(new Intent(this, AllRecordsActivity.class));
                break;
            case R.id.nav_statistics:
                statsFrag = new StatisticsFragment();
                transaction = manager.beginTransaction();
                transaction.replace(R.id.fragment_content, statsFrag, "StatisticsFragment");
                transaction.addToBackStack(null);
                transaction.commit();
                mToolbar.setTitle(getString(R.string.nav_statistics));
                break;
            case R.id.nav_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
            case R.id.nav_help:
                startActivity(new Intent(this, HelpActivity.class));
                break;
            case R.id.nav_about:
                startActivity(new Intent(this, AboutActivity.class));
                break;
        }

    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onNavigationItemSelected(final MenuItem menuItem) {
        if ((menuItem.getItemId() != R.id.nav_settings) && (menuItem.getItemId() != R.id.nav_help) && (menuItem.getItemId() != R.id.nav_about)) {
            menuItem.setChecked(true);
            mNavItemId = menuItem.getItemId();
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        mDrawerActionHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                navigate(menuItem.getItemId());
            }
        }, DRAWER_CLOSE_DELAY_MS);
        return true;
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(NAV_ITEM_ID, mNavItemId);
    }


    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else if (getFragmentManager().getBackStackEntryCount() > 0 && mNavItemId != R.id.nav_home) {
            FragmentManager.BackStackEntry entry = getFragmentManager().getBackStackEntryAt(0);
            getFragmentManager().popBackStack(entry.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
            mNavItemId = R.id.nav_home;
            mNavigationView.getMenu().findItem(mNavItemId).setChecked(true);
        }
        else {
            super.onBackPressed();
        }
    }
}

