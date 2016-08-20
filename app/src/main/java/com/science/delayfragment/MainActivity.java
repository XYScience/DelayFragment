package com.science.delayfragment;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.science.delayfragment.fragment.DrawerFragment;
import com.science.delayfragment.fragment.MainFragment;

import java.util.List;

/**
 * 延迟加载数据，即页面可见时，是否自动加载数据
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private MainFragment mMainFragment;
    private DrawerFragment mDrawerFragment1, mDrawerFragment2, mDrawerFragment3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mDrawerFragment1 = DrawerFragment.newInstance("Gallery");
        mDrawerFragment2 = DrawerFragment.newInstance("Slideshow");
        mDrawerFragment3 = DrawerFragment.newInstance("Tools");
        mMainFragment = new MainFragment();
        showFragment(mDrawerFragment3);
        showFragment(mDrawerFragment2);
        showFragment(mDrawerFragment1);
        showFragment(mMainFragment);
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

        if (id == R.id.nav_camera) {
            // Handle the camera action
            showFragment(mMainFragment);
        } else if (id == R.id.nav_gallery) {
            showFragment(mDrawerFragment1);
        } else if (id == R.id.nav_slideshow) {
            showFragment(mDrawerFragment2);
        } else if (id == R.id.nav_manage) {
            showFragment(mDrawerFragment3);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        hideFragment(fm);
        if (!fragment.isAdded()) {
            ft.add(R.id.content, fragment);
        } else {
            ft.show(fragment);
        }
        ft.commit();
    }

    private void hideFragment(FragmentManager fm) {
        List<Fragment> fragmentList = fm.getFragments();
        if (fragmentList != null) {
            for (Fragment fragment : fragmentList) {
                fm.beginTransaction().hide(fragment).commit();
            }
        }
    }
}
