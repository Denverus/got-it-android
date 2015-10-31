package com.jzson.gotit.client;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.widget.Toast;

import com.jzson.gotit.client.activities.MainActivity;
import com.jzson.gotit.client.activities.RegistrationActivity;
import com.jzson.gotit.client.fragments.FeedListFragment;
import com.jzson.gotit.client.fragments.FollowerListFragment;
import com.jzson.gotit.client.fragments.NotificationListFragment;

/**
 * Created by Denis on 6/26/15.
 */
public class NavigationViewManager {

    MainActivity mMainActivity;
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;

    public NavigationViewManager(MainActivity mainActivity, DrawerLayout drawerLayout, NavigationView navigationView) {
        mMainActivity = mainActivity;
        mDrawerLayout = drawerLayout;
        mNavigationView = navigationView;

        initMenuHandlers();
    }

    private void initMenuHandlers() {
        mNavigationView.setNavigationItemSelectedListener(
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
                NavUtils.showSettings(mMainActivity);
                break;
            case R.id.nav_teens:
                NavUtils.showTeensActivity(mMainActivity);
                break;
            case R.id.nav_check_ins:
                NavUtils.showCheckInList(mMainActivity);
                break;
            case R.id.nav_feeds:
                mMainActivity.setFragment(new FeedListFragment());
                break;
            case R.id.nav_followers:
                mMainActivity.setFragment(new FollowerListFragment());
                break;
            case R.id.nav_notifications:
                mMainActivity.setFragment(new NotificationListFragment());
                break;
            case R.id.nav_registration:
                Intent i = new Intent(mMainActivity, RegistrationActivity.class);
                mMainActivity.startActivity(i);
                break;
            case R.id.nav_singout:
                Toast.makeText(mMainActivity, "Not implemented", Toast.LENGTH_SHORT).show();;
                break;
            case R.id.nav_as_teen:
                AppApplication.getContext().setUserId(0);
                showTeenMenu(true);
                mMainActivity.updateDrawer();
                Toast.makeText(mMainActivity, "Switched to teen", Toast.LENGTH_SHORT).show();;
                break;
            case R.id.nav_as_follower:
                AppApplication.getContext().setUserId(5);
                showTeenMenu(false);
                mMainActivity.updateDrawer();
                Toast.makeText(mMainActivity, "Switched to followe", Toast.LENGTH_SHORT).show();;
                break;

        }
    }

    public void showTeenMenu(boolean isVisibleForTeen) {
        setMenuItemVisible(R.id.nav_check_ins, isVisibleForTeen);
        setMenuItemVisible(R.id.nav_followers, isVisibleForTeen);
    }
    private void setMenuItemVisible(int resourceId, boolean visible) {
        MenuItem menuItem1 = mNavigationView.getMenu().findItem(resourceId);
        menuItem1.setVisible(visible);
    }
}
