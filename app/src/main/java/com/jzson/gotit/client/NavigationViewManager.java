package com.jzson.gotit.client;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

import com.jzson.gotit.client.activities.MainActivity;

/**
 * Created by Iliya Gogolev on 6/26/15.
 */
public class NavigationViewManager {

    MainActivity mMainActivity;
    DrawerLayout mDrawerLayout;

    public NavigationViewManager(MainActivity mainActivity, DrawerLayout drawerLayout, NavigationView navigationView) {
        mMainActivity = mainActivity;
        mDrawerLayout = drawerLayout;
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();

                        NavigationViewManager.this.onNavigationItemSelected(menuItem.getItemId());
                        return true;
                    }
                }
        );
    }

    private void onNavigationItemSelected(int menuId) {
/*
        switch (menuId) {
            case R.id.nav_settings:
                NavUtils.showUserDetailsActivity(mMainActivity);

                Snackbar.make(mMainActivity.findViewById(R.id.main_content), "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        }).show();

                break;
            case R.id.nav_singout:
                ParseUser.logOut();
                NavUtils.showSigninActivity(mMainActivity);
                break;

        }*/

    }
}
