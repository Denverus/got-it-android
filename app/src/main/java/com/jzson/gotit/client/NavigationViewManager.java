package com.jzson.gotit.client;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.widget.Toast;

import com.jzson.gotit.client.activities.MainActivity;
import com.jzson.gotit.client.fragments.FeedbackListFragment;
import com.jzson.gotit.client.fragments.TeenListFragment;

/**
 * Created by Denis on 6/26/15.
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

        switch (menuId) {
            case R.id.nav_settings:
                Toast.makeText(mMainActivity, "Not implemented", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_teens:
                mMainActivity.setFragment(new TeenListFragment());
                //NavUtils.showTeensActivity(mMainActivity);
                break;
            case R.id.nav_feedbacks:
                mMainActivity.setFragment(new FeedbackListFragment());
                break;
            case R.id.nav_singout:
                Toast.makeText(mMainActivity, "Not implemented", Toast.LENGTH_SHORT).show();;
                break;

        }

    }
}
