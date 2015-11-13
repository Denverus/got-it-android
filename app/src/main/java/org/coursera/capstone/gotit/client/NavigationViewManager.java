package org.coursera.capstone.gotit.client;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import org.coursera.capstone.gotit.client.activities.LoginScreenActivity;
import org.coursera.capstone.gotit.client.activities.MainActivity;
import org.coursera.capstone.gotit.client.activities.RegistrationActivity;
import org.coursera.capstone.gotit.client.fragments.FeedListFragment;
import org.coursera.capstone.gotit.client.fragments.FollowerListFragment;
import org.coursera.capstone.gotit.client.fragments.NotificationListFragment;
import org.coursera.capstone.gotit.client.model.Person;
import org.coursera.capstone.gotit.client.provider.ProviderFactory;
import org.coursera.capstone.gotit.client.provider.ServiceApi;
import org.coursera.capstone.gotit.client.provider.ServiceCall;

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
            case R.id.nav_singout:
                resetSettings();
                Intent loginIntent = new Intent(mMainActivity, LoginScreenActivity.class);
                mMainActivity.startActivity(loginIntent);
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

    private void resetSettings() {
        SharedPreferences settings = mMainActivity.getSharedPreferences(LoginScreenActivity.PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.commit();
    }
}
