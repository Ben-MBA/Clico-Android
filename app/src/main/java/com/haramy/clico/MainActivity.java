package com.haramy.clico;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.haramy.clico.utils.Constants;
import com.haramy.clico.utils.Utils;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static int REQUEST_CODE_ACCOUNT_ACTIVITY = 1;

    private boolean mIsTraderMode;
    private boolean mIsSearchMode = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mIsTraderMode = Utils.getUserMode(MainActivity.this);

        setUpToolBarAndDrawer();
        setUpFragment();

    }

    private void setUpToolBarAndDrawer(){
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

    private void setUpFragment(){

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag("DealsFragment");

        if (fragment!=null && fragment instanceof DealsFragment){
            if (mIsSearchMode){
                if (mIsTraderMode){
                    ((DealsFragment)fragment)
                            .setFragmentMode(DealsFragment.MODE_TRADE_SEARCH);
                }else {
                    ((DealsFragment)fragment)
                            .setFragmentMode(DealsFragment.MODE_CLIENT_SEARCH);
                }
            }else {
                if (mIsTraderMode){
                    ((DealsFragment)fragment)
                            .setFragmentMode(DealsFragment.MODE_TRADE_SHARE);
                }else {
                    ((DealsFragment)fragment)
                            .setFragmentMode(DealsFragment.MODE_CLIENT_SHARE);
                }
            }
        } else { // 1st run, create a new one
            fragment = new DealsFragment();
            Bundle bundle = new Bundle();
            if (mIsSearchMode){
                if (mIsTraderMode){
                    bundle.putInt(Constants.EXTRA_FRAGMENT_MODE,
                            DealsFragment.MODE_TRADE_SEARCH);

                }else {
                    bundle.putInt(Constants.EXTRA_FRAGMENT_MODE,
                            DealsFragment.MODE_CLIENT_SEARCH);
                }
            }else {
                if (mIsTraderMode){
                    bundle.putInt(Constants.EXTRA_FRAGMENT_MODE,
                            DealsFragment.MODE_TRADE_SHARE);
                }else {
                    bundle.putInt(Constants.EXTRA_FRAGMENT_MODE,
                            DealsFragment.MODE_CLIENT_SHARE);
                }
            }
            fragment.setArguments(bundle);
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment, "DealsFragment").commit();
        }
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

        switch (id){
            case R.id.nav_search:
                mIsSearchMode = true;
                setUpFragment();
                break;
            case R.id.nav_share:
                mIsSearchMode = false;
                setUpFragment();
                break;
            case R.id.nav_manage:
                startActivityForResult(new Intent(MainActivity.this, AccountActivity.class),
                        REQUEST_CODE_ACCOUNT_ACTIVITY);
                break;
            case R.id.nav_switch:
                mIsTraderMode = ! mIsTraderMode;
                if (mIsTraderMode){
                    item.setTitle("Switch to client mode");
                } else {
                    item.setTitle("Switch to trader mode");
                }
                invalidateOptionsMenu();
                setUpFragment();
                Utils.setUserMode(MainActivity.this, mIsTraderMode);
                break;
            case R.id.nav_settings:
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // Update view, if mode was changed from account
        /*if (requestCode == REQUEST_CODE_ACCOUNT_ACTIVITY) {
            boolean mode = Utils.getUserMode(MainActivity.this);
            if (mode != mIsTraderMode){
                mIsTraderMode = mode;
                setUpFragment();
            }
        }*/
    }
}
