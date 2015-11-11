package org.coursera.capstone.gotit.client;

import android.content.Intent;
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
            case R.id.nav_registration:
                Intent i = new Intent(mMainActivity, RegistrationActivity.class);
                mMainActivity.startActivity(i);
                break;
            case R.id.nav_singout:
                Intent loginIntent = new Intent(mMainActivity, LoginScreenActivity.class);
                mMainActivity.startActivity(loginIntent);
                break;
            case R.id.nav_as_teen:
                switchToUser("user1");
                break;
            case R.id.nav_as_follower:
                switchToUser("user6");
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

    private void switchToUser(final String username) {
        ProviderFactory.init(ProviderFactory.INTERNAL_PROVIDER, null, username, null);

        CallableTask.invoke(mMainActivity, new ServiceCall<Person>() {

            @Override
            public Person call(ServiceApi svc) throws Exception {
                return svc.getPersonByUsername(username);
            }
        }, new TaskCallback<Person>() {

            @Override
            public void success(Person person) {
                if (person.isHasDiabetes()) {
                    showTeenMenu(true);
                    Toast.makeText(mMainActivity, "Switched to teen "+person.getName(), Toast.LENGTH_SHORT).show();;
                } else {
                    showTeenMenu(false);
                    Toast.makeText(mMainActivity, "Switched to follower "+person.getName(), Toast.LENGTH_SHORT).show();;
                }
                mMainActivity.updateDrawer();

                AppApplication.getContext().setCurrentUser(person);
                NavUtils.showTeensActivity(mMainActivity);
            }

            @Override
            public void error(Exception e) {
                Log.e(LoginScreenActivity.class.getName(), "Error logging in via OAuth.", e);

                Toast.makeText(
                        mMainActivity,
                        "Login failed, check your Internet connection and credentials.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
